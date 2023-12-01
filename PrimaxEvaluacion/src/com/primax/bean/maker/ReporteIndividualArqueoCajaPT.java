package com.primax.bean.maker;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import com.primax.bean.as.WebAppUtil;
import com.primax.bean.util.SignatureConvert;
import com.primax.bean.vs.base.BaseReport;
import com.primax.bean.vs.base.DataSourceConnection;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.enm.gen.RutaFileEnum;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.jpa.pla.CheckListKpiEjecucionEt;
import com.primax.jpa.pla.CheckListKpiEjecucionFirmaEt;
import com.primax.srv.idao.ICheckListKpiEjecucionDao;
import com.primax.srv.idao.IGeneralUtilsDao;
import com.primax.srv.idao.IResponsableDao;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxExporterConfiguration;

public class ReporteIndividualArqueoCajaPT extends BaseReport {

	public ByteArrayOutputStream getReport(Map<String, String[]> params, String localPath, ServletContext servlet,
			boolean isexcel) throws NumberFormatException, EntidadNoEncontradaException {

		String nombreAuditor = "";
		String nombreGerente = "";
		String firmaAuditor = null;
		String firmaGerente = null;
		String nombreVendedor = "";
		String nombreEstacion = "";
		String firmaVendedor = null;
		ByteArrayOutputStream printStream = null;
		String cargoEstacion = "Gerente de Estación";
		Map<String, Object> paramRpt = new HashMap<String, Object>();
		IResponsableDao iResponsableDao = EJB(EnumNaming.IResponsableDao);
		ICheckListKpiEjecucionDao iCheckListKpiEjecucionDao = EJB(EnumNaming.ICheckListKpiEjecucionDao);
		try {
			Long id = Long.parseLong(params.get("p2")[0]);
			CheckListKpiEjecucionEt checkListKpiEjecucion = iCheckListKpiEjecucionDao.getCheckListKpiEjecucion(id);
			for (CheckListKpiEjecucionFirmaEt checkListKpiEjecucionFirma : checkListKpiEjecucion
					.getCheckListKpiEjecucionFirma()) {
				if (checkListKpiEjecucionFirma.getOrden() == 0L) {
					if (checkListKpiEjecucionFirma.isFirmado()) {
						firmaVendedor = generarRutaFirma(checkListKpiEjecucionFirma);
					}
					nombreVendedor = checkListKpiEjecucionFirma.getNombre();
				}
				if (checkListKpiEjecucionFirma.getOrden() == 1L) {
					if (checkListKpiEjecucionFirma.isFirmado()) {
						firmaAuditor = generarRutaFirma(checkListKpiEjecucionFirma);
					}
					nombreAuditor = checkListKpiEjecucionFirma.getNombre();
				}
				if (checkListKpiEjecucionFirma.getOrden() == 2L) {
					if (checkListKpiEjecucionFirma.isFirmado()) {
						firmaGerente = generarRutaFirma(checkListKpiEjecucionFirma);
					}
					nombreGerente = checkListKpiEjecucionFirma.getNombre();
				}
			}
			String tipoEstacion = params.get("p4")[0];
			String nombreUsuario = params.get("p1")[0];
			paramRpt.put("par_usuario", nombreUsuario);
			paramRpt.put("logo", getLogoAtimasa(servlet));
			paramRpt.put("par_tipo_estacion", tipoEstacion);
			paramRpt.put("par_firma_auditor", firmaAuditor);
			paramRpt.put("par_firma_gerente", firmaGerente);
			paramRpt.put("par_nombre_auditor", nombreAuditor);
			paramRpt.put("par_cargo_estacion", cargoEstacion);
			paramRpt.put("par_firma_vendedor", firmaVendedor);
			paramRpt.put("par_nombre_gerente", nombreGerente);
			paramRpt.put("par_nombre_vendedor", nombreVendedor);
			paramRpt.put("par_id_check_list_kpi_ejecucion", id);
			paramRpt.put("par_nombre_estacion", nombreEstacion);
			paramRpt.put("SUBREPORT_DIR", localPath + File.separator + "ejecucion" + File.separator);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getReport " + " " + e.getMessage());
		}
		Connection cn = null;

		try {
			cn = DataSourceConnection.getJNDIConnection();
			JasperPrint jprint = JasperFillManager.fillReport(
					localPath + File.separator + "ejecucion" + File.separator + "rptArqueoCajaPistaTienda.jasper",
					paramRpt, cn);
			printStream = new ByteArrayOutputStream();
			if (isexcel) {
				printStream = new ByteArrayOutputStream();
				JRXlsxExporter xlsx = new JRXlsxExporter();
				xlsx.setExporterOutput(new SimpleOutputStreamExporterOutput(printStream));
				xlsx.setExporterInput(new SimpleExporterInput(jprint));
				SimpleXlsxExporterConfiguration conf = new SimpleXlsxExporterConfiguration();
				xlsx.setConfiguration(conf);
				xlsx.exportReport();
			} else {
				printStream = new ByteArrayOutputStream();
				JasperExportManager.exportReportToPdfStream(jprint, printStream);
			}
		} catch (Exception e) {
			System.err.println("Error:No fue posible elaborar el reporte :" + e.getMessage());
		} finally {
			try {
				cn.close();
				iResponsableDao.remove();
				iCheckListKpiEjecucionDao.remove();
			} catch (SQLException e) {
				System.err.println("Error en cerrar conexion :" + e.getMessage());
			}
		}
		return printStream;
	}

	public Date getFechaMesAnterior(Date fechaActual) {
		Calendar calendar = Calendar.getInstance();
		int anio = 0, mes = 0, dia = 1;
		Calendar mesAnterior = null;
		Calendar fechaMesAnterior = null;
		try {
			calendar.setTime(fechaActual);
			anio = calendar.get(Calendar.YEAR);
			mes = calendar.get(Calendar.MONTH);
			mesAnterior = new GregorianCalendar(anio, mes - 1, dia);
			anio = mesAnterior.get(Calendar.YEAR);
			mes = mesAnterior.get(Calendar.MONTH);
			dia = mesAnterior.getActualMaximum(Calendar.DAY_OF_MONTH);
			fechaMesAnterior = new GregorianCalendar(anio, mes, dia);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getFechaMesAnterior " + " " + e.getMessage());
		}
		return fechaMesAnterior.getTime();
	}

	public String generarRutaFirma(CheckListKpiEjecucionFirmaEt check) {
		Long id = 0L;
		Long idF = 0L;
		String pathTemp = "";
		byte[] bytesF = null;
		String pathFirma = "";
		InputStream inputStreamImg = null;
		String pathP = WebAppUtil.getWebPath();
		SignatureConvert signatureConvert = new SignatureConvert();
		IGeneralUtilsDao iGUtilsDao = EJB(EnumNaming.IGeneralUtilsDao);
		String pathImgTemp = RutaFileEnum.RUTA_IMAGEN_TEMPORAL.getDescripcion();
		try {
			id = check.getCheckListKpiEjecucion().getIdCheckListKpiEjecucion();
			idF = check.getIdCheckListKpiEjecucionFirma();
			bytesF = signatureConvert.redrawSignature(signatureConvert.extractSignature(check.getFirma()));
			inputStreamImg = new ByteArrayInputStream(bytesF);
			pathTemp = iGUtilsDao.creaRuta(id, pathP + File.separatorChar + pathImgTemp + File.separatorChar + "FIRMA");
			iGUtilsDao.copyFile(idF + ".png", inputStreamImg, pathTemp);
			pathFirma = pathTemp + idF + ".png";
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método generarRutaFirma " + " " + e.getMessage());
		}
		return pathFirma;
	}
}
