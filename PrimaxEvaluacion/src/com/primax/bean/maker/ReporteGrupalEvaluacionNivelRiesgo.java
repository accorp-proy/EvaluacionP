package com.primax.bean.maker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import com.primax.bean.vs.base.BaseReport;
import com.primax.bean.vs.base.DataSourceConnection;
import com.primax.exc.gen.EntidadNoEncontradaException;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxExporterConfiguration;

public class ReporteGrupalEvaluacionNivelRiesgo extends BaseReport {

	public ByteArrayOutputStream getReport(Map<String, String[]> params, String localPath, ServletContext servlet,
			boolean isexcel) throws NumberFormatException, EntidadNoEncontradaException {

		String nombreReporte = "";
		ByteArrayOutputStream printStream = null;
		Map<String, Object> paramRpt = new HashMap<String, Object>();
		try {
			Long idUsuario = Long.parseLong(params.get("p1")[0]);
			paramRpt.put("par_id_usuario", idUsuario);
			paramRpt.put("SUBREPORT_DIR", localPath + File.separator + "reporte" + File.separator);
			if (isexcel) {
				nombreReporte = "rptEvaluacionNivelRiesgoExcel.jasper";
			} else {
				nombreReporte = "rptEvaluacionNivelRiesgoPdf.jasper";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getReport " + " " + e.getMessage());
		}
		Connection cn = null;

		try {
			cn = DataSourceConnection.getJNDIConnection();
			JasperPrint jprint = JasperFillManager
					.fillReport(localPath + File.separator + "reporte" + File.separator + nombreReporte, paramRpt, cn);
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
}
