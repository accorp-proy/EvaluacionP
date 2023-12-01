package com.primax.bean.maker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import com.primax.bean.vs.base.BaseReport;
import com.primax.bean.vs.base.DataSourceConnection;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.ResponsableEt;
import com.primax.jpa.pla.CheckListEjecucionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IAgenciaDao;
import com.primax.srv.idao.ICheckListEjecucionDao;
import com.primax.srv.idao.IResponsableDao;
import com.primax.srv.idao.ISeguimientoPlanAccionDao;
import com.primax.srv.idao.IUsuarioDao;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxExporterConfiguration;

public class ReporteIndividualPlanAccionSeguimientoEspecifico extends BaseReport {

	public ByteArrayOutputStream getReport(Map<String, String[]> params, String localPath, ServletContext servlet,
			boolean isexcel) throws NumberFormatException, EntidadNoEncontradaException {
		String nombreGerente = "N/A";
		ByteArrayOutputStream printStream = null;
		String cargoEstacion = "Gerente de Estación";
		IAgenciaDao iAgenciaDao = EJB(EnumNaming.IAgenciaDao);
		IUsuarioDao iUsuarioDao = EJB(EnumNaming.IUsuarioDao);
		Map<String, Object> paramRpt = new HashMap<String, Object>();
		IResponsableDao iResponsableDao = EJB(EnumNaming.IResponsableDao);
		ICheckListEjecucionDao iCheckListEjecucionDao = EJB(EnumNaming.ICheckListEjecucionDao);
		ISeguimientoPlanAccionDao iSeguimientoPlanAccionDao = EJB(EnumNaming.ISeguimientoPlanAccionDao);
		try {
			Long idUsuario = Long.parseLong(params.get("p1")[0]);
			UsuarioEt usuario = iUsuarioDao.getUsuarioId(idUsuario);

			Long id = Long.parseLong(params.get("p2")[0]);
			Long idAgencia = Long.parseLong(params.get("p3")[0]);
			CheckListEjecucionEt checkListEjecucion = iCheckListEjecucionDao.getCheckListEjecucion(id);
			CheckListEjecucionEt checkListEjecucionA = iCheckListEjecucionDao.getCheckListEjecucion(
					checkListEjecucion.getPlanificacion().getAgencia(), checkListEjecucion.getCodigo(),
					checkListEjecucion.getFechaEjecucion());
			if (checkListEjecucionA != null) {
				iSeguimientoPlanAccionDao.generar(checkListEjecucionA.getIdCheckListEjecucion(), id, idUsuario);
				System.out.println("CheckList" + " " + checkListEjecucionA.getIdCheckListEjecucion());
			}
			AgenciaEt agencia = iAgenciaDao.getAgenciaById(idAgencia);
			if (agencia != null) {
				List<ResponsableEt> responsables = iResponsableDao.getResponsableByAgencia1List(agencia);
				if (responsables != null && !responsables.isEmpty()) {
					for (ResponsableEt responsable : responsables) {
						if (responsable.getTipoCargo().getDescripcion().equals("GERENTE")) {
							nombreGerente = responsable.getPersona().getNombreCompleto();
							cargoEstacion = responsable.getTipoCargo().getFirma() + " " + ":";
						}
					}
					if (nombreGerente != null && nombreGerente.equals("N/A")) {
						for (ResponsableEt responsable : responsables) {
							if (responsable.getTipoCargo().getDescripcion().equals("SOPORTE OPERATIVO")) {
								nombreGerente = responsable.getPersona().getNombreCompleto();
								cargoEstacion = responsable.getTipoCargo().getFirma() + " " + ":";
							}
						}
					}
				}
			}
			paramRpt.put("par_usuario", usuario.getNombreUsuario());
			paramRpt.put("par_id_usuario", usuario.getIdUsuario());
			paramRpt.put("par_cargo_estacion", cargoEstacion);
			paramRpt.put("par_nombre_gerente", nombreGerente);
			paramRpt.put("par_id_check_list_ejecucion", checkListEjecucionA.getIdCheckListEjecucion());
			// paramRpt.put("logo", getLogoAtimasa(servlet));
			paramRpt.put("SUBREPORT_DIR", localPath + File.separator + "planAccionSeguimiento" + File.separator);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getReport " + " " + e.getMessage());
		}
		Connection cn = null;

		try {
			cn = DataSourceConnection.getJNDIConnection();
			JasperPrint jprint = JasperFillManager.fillReport(localPath + File.separator + "planAccionSeguimiento"
					+ File.separator + "rptSeguimientoPlanAccionEspecifico.jasper", paramRpt, cn);
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
				iUsuarioDao.remove();
				iAgenciaDao.remove();
				iResponsableDao.remove();
				iCheckListEjecucionDao.remove();
				iSeguimientoPlanAccionDao.remove();
			} catch (SQLException e) {
				System.err.println("Error en cerrar conexion :" + e.getMessage());
			}
		}
		return printStream;
	}

}
