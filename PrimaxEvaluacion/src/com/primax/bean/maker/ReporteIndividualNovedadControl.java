package com.primax.bean.maker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import com.primax.bean.as.WebAppUtil;
import com.primax.bean.vs.base.BaseReport;
import com.primax.bean.vs.base.DataSourceConnection;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.enm.gen.PersonaEnum;
import com.primax.enm.gen.RutaFileEnum;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.ResponsableEt;
import com.primax.jpa.pla.CheckListEjecucionAdjuntoEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IAgenciaDao;
import com.primax.srv.idao.ICheckListEjecucionAdjuntoDao;
import com.primax.srv.idao.IGeneralUtilsDao;
import com.primax.srv.idao.IResponsableDao;
import com.primax.srv.idao.IUsuarioDao;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

public class ReporteIndividualNovedadControl extends BaseReport {

	public ByteArrayOutputStream getReport(Map<String, String[]> params, String localPath, ServletContext servlet,
			boolean isDocx) throws NumberFormatException, EntidadNoEncontradaException {

		String nombreGerente = "";
		String nombreReporte = "";
		String nombreCarpeta = "";
		String jefeOperacionT = "";
		String jefeOperacionP = "";
		String nombreSoporteO = "";
		String nombreSoporteA = "";
		String categoriaEstacion0 = "";
		String categoriaEstacion1 = "";
		ByteArrayOutputStream printStream = null;
		String cargoEstacion = "Administrador";
		IAgenciaDao iAgenciaDao = EJB(EnumNaming.IAgenciaDao);
		Map<String, Object> paramRpt = new HashMap<String, Object>();
		IUsuarioDao iUsuarioDao = EJB(EnumNaming.IUsuarioDao);
		IResponsableDao iResponsableDao = EJB(EnumNaming.IResponsableDao);
		IGeneralUtilsDao iGeneralUtilsDao = EJB(EnumNaming.IGeneralUtilsDao);
		ICheckListEjecucionAdjuntoDao iCheckListEjecucionAdjuntoDao = EJB(EnumNaming.ICheckListEjecucionAdjuntoDao);
		try {
			UsuarioEt usuario = iUsuarioDao.getUsuarioId(1L);
			String nombreUsuario = params.get("p1")[0];
			Long idCheckList = Long.parseLong(params.get("p2")[0]);
			Long idAgencia = Long.parseLong(params.get("p3")[0]);
			Long idEvaluacion = Long.parseLong(params.get("p4")[0]);
			Long idTipoCheckList = Long.parseLong(params.get("p5")[0]);
			AgenciaEt agencia = iAgenciaDao.getAgenciaById(idAgencia);
			String tipoEstaccion = agencia.getTipoEstacion() == null ? "" : agencia.getTipoEstacion().getDescripcion();
			if (idEvaluacion == 2) {
				if (tipoEstaccion.equals("PISTA Y TIENDA")) {
					nombreCarpeta = "novedadEstacion";
					nombreReporte = "rptNovedadControl.jasper";
					categoriaEstacion1 = "ESTACIÓN DE SERVICIOS";
					categoriaEstacion0 = "Estaciones de Servicios";
				} else if (tipoEstaccion.equals("PISTA")) {
					nombreCarpeta = "novedadPista";
					nombreReporte = "rptNovedadControl.jasper";
					categoriaEstacion1 = "ESTACIÓN DE SERVICIOS";
					categoriaEstacion0 = "Estaciones de Servicios";
				} else if (tipoEstaccion.equals("TIENDA")) {
					nombreCarpeta = "novedadTienda";
					categoriaEstacion1 = "TIENDA LISTO";
					categoriaEstacion0 = "Tiendas Listo";
					nombreReporte = "rptNovedadControl.jasper";
				}
			}
			if (idEvaluacion == 4) {
				if (tipoEstaccion.equals("PISTA Y TIENDA")) {
					nombreCarpeta = "novedadSupervisionE";
					nombreReporte = "rptNovedadControl.jasper";
					categoriaEstacion1 = "ESTACIÓN DE SERVICIOS";
					categoriaEstacion0 = "Estaciones de Servicios";
				} else if (tipoEstaccion.equals("PISTA")) {
					nombreCarpeta = "novedadSupervisionP";
					nombreReporte = "rptNovedadControl.jasper";
					categoriaEstacion1 = "ESTACIÓN DE SERVICIOS";
					categoriaEstacion0 = "Estaciones de Servicios";
				} else if (tipoEstaccion.equals("TIENDA")) {
					nombreCarpeta = "novedadSupervisionT";
					categoriaEstacion1 = "TIENDA LISTO";
					categoriaEstacion0 = "Tiendas Listo";
					nombreReporte = "rptNovedadControl.jasper";
				}
				if (idTipoCheckList == 23) {
					nombreCarpeta = "novedadSupervisionV";
				}
			}

			if (agencia != null) {
				List<ResponsableEt> responsables = iResponsableDao.getResponsableByAgencia1List(agencia);
				if (responsables != null && !responsables.isEmpty()) {
					for (ResponsableEt responsable : responsables) {
						if (responsable.getTipoCargo().getDescripcion().equals("GERENTE")) {
							nombreGerente = responsable.getPersona().getNombreCompleto();
							cargoEstacion = responsable.getTipoCargo().getFirma();
						}
					}
					if (nombreGerente != null && nombreGerente.equals("N/A")) {
						for (ResponsableEt responsable : responsables) {
							if (responsable.getTipoCargo().getDescripcion().equals("RESPONSABLE TIENDA")) {
								nombreGerente = responsable.getPersona().getNombreCompleto();
								cargoEstacion = responsable.getTipoCargo().getFirma();
							}
						}
					}
					for (ResponsableEt responsable : responsables) {
						if (responsable.getTipoCargo().getDescripcion().contains("SOPORTE OPERATIVO")) {
							nombreSoporteO = responsable.getPersona().getNombreCompleto();
						}
					}
					for (ResponsableEt responsable : responsables) {
						if (responsable.getTipoCargo().getDescripcion().contains("SOPORTE ADMINISTRATIVO")) {
							nombreSoporteA = responsable.getPersona().getNombreCompleto();
						}
					}
					for (ResponsableEt responsable : responsables) {
						if (responsable.getTipoCargo().getDescripcion().contains("JEFE DE OPERACIONES TIENDA")) {
							jefeOperacionT = responsable.getPersona().getNombreCompleto();
						}
					}
					for (ResponsableEt responsable : responsables) {
						if (responsable.getTipoCargo().getDescripcion().contains("JEFE DE OPERACIONES PISTA")) {
							jefeOperacionP = responsable.getPersona().getNombreCompleto();
						}
					}
				}
			}
			String rutaTemp = "";
			String rutaLogo = "";
			String nombreArchivo = "";
			List<CheckListEjecucionAdjuntoEt> adjuntos = iCheckListEjecucionAdjuntoDao.getAdjunto(idCheckList);
			for (CheckListEjecucionAdjuntoEt adjunto : adjuntos) {
				nombreArchivo = adjunto.getNombreAdjunto();
				if (nombreArchivo.toLowerCase().contains(".png") || nombreArchivo.toLowerCase().contains(".jpg")) {
					String pathProyecto = WebAppUtil.getWebPath();
					String pathImagenTemp = RutaFileEnum.RUTA_IMAGEN_TEMPORAL.getDescripcion();
					rutaTemp = iGeneralUtilsDao.creaRuta(adjunto.getIdCheckListEjecucionAdjunto(),
							pathProyecto + File.separatorChar + pathImagenTemp + File.separatorChar
									+ PersonaEnum.USUARIO.getDescripcion());

					String pathControl = RutaFileEnum.RUTA_CONTROL_INTERNO.getDescripcion() + File.separatorChar
							+ idCheckList + File.separatorChar + nombreArchivo;
					InputStream inputStreamImg = getImg(pathControl);
					iGeneralUtilsDao.copyFile(nombreArchivo, inputStreamImg, rutaTemp);
					adjunto.setImagenPath(rutaTemp + File.separatorChar + nombreArchivo);
					iCheckListEjecucionAdjuntoDao.guardarCheckListEjecucionAdjunto(adjunto, usuario);
				}
			}
			rutaLogo = rutaLogo(servlet);
			paramRpt.put("logo", rutaLogo);
			paramRpt.put("par_usuario", nombreUsuario);
			paramRpt.put("par_id_check_list", idCheckList);
			paramRpt.put("par_cargo_estacion", cargoEstacion);
			paramRpt.put("par_gerente_estacion", nombreGerente);
			paramRpt.put("par_jefe_operativo_t", jefeOperacionT);
			paramRpt.put("par_jefe_operativo_p", jefeOperacionP);
			paramRpt.put("par_soporte_estacion_o", nombreSoporteO);
			paramRpt.put("par_soporte_estacion_a", nombreSoporteA);
			paramRpt.put("par_categoria_estacion_0", categoriaEstacion0);
			paramRpt.put("par_categoria_estacion_1", categoriaEstacion1);
			paramRpt.put("SUBREPORT_DIR", localPath + File.separator + nombreCarpeta + File.separator);
			System.out.println("Path  " + " " + localPath);
			System.out.println("Path  Sub-Report" + " " + localPath + File.separator + nombreCarpeta + File.separator);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getReport " + " " + e.getMessage());
		}
		Connection cn = null;
		try {
			cn = DataSourceConnection.getJNDIConnection();
			JasperPrint jprint = JasperFillManager.fillReport(
					localPath + File.separator + nombreCarpeta + File.separator + nombreReporte, paramRpt, cn);
			printStream = new ByteArrayOutputStream();
			if (isDocx) {
				JRDocxExporter exporter = new JRDocxExporter();
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(printStream));
				exporter.setExporterInput(new SimpleExporterInput(jprint));
				exporter.exportReport();
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
				iCheckListEjecucionAdjuntoDao.remove();
			} catch (SQLException e) {
				System.err.println("Error en cerrar conexion :" + e.getMessage());
			}
		}
		return printStream;
	}

	public InputStream getImg(String pathImg) {
		String path = pathImg;
		System.out.println(path);
		File arch = new File(path);
		InputStream img = null;
		try {
			img = new FileInputStream(arch);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return img;
	}

}
