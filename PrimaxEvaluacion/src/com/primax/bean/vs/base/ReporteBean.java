package com.primax.bean.vs.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.StreamedContent;

import com.primax.bean.as.WebAppUtil;
import com.primax.bean.maker.ReporteGrupalEvaluacionConsolidado;
import com.primax.bean.maker.ReporteGrupalEvaluacionNivelRiesgo;
import com.primax.bean.maker.ReporteGrupalEvaluacionPlanificacion;
import com.primax.bean.maker.ReporteGrupalEvaluacionPuntaje;
import com.primax.bean.maker.ReporteGrupalEvaluacionVariacion;
import com.primax.bean.maker.ReporteGrupalPlanificacionInventario;
import com.primax.bean.maker.ReporteGrupalProcesoConsolidado;
import com.primax.bean.maker.ReporteGrupalTableroControl;
import com.primax.bean.maker.ReporteGrupalTipoEvaluacion;
import com.primax.bean.maker.ReporteIndividualArqueoCajaChicaGeneral;
import com.primax.bean.maker.ReporteIndividualArqueoCajaFondoSuelto;
import com.primax.bean.maker.ReporteIndividualArqueoCajaPT;
import com.primax.bean.maker.ReporteIndividualArqueoConsolidado;
import com.primax.bean.maker.ReporteIndividualCheckListEjecutado;
import com.primax.bean.maker.ReporteIndividualCheckListEjecutadoEspecifico;
import com.primax.bean.maker.ReporteIndividualMatrizEjecutado;
import com.primax.bean.maker.ReporteIndividualNovedadCCTV;
import com.primax.bean.maker.ReporteIndividualNovedadControl;
import com.primax.bean.maker.ReporteIndividualPlanAccionEspecifico;
import com.primax.bean.maker.ReporteIndividualPlanAccionGeneral;
import com.primax.bean.maker.ReporteIndividualPlanAccionSeguimientoEspecifico;
import com.primax.bean.maker.ReportePlantillaCriterioEspecifico;
import com.primax.bean.maker.ReportePlantillaCriterioGeneral;
import com.primax.bean.maker.ReportePlantillaMatrizEspecifico;
import com.primax.bean.maker.ReporteProcesoDetalle;
import com.primax.bean.ss.AppMain;
import com.primax.exc.gen.EntidadNoEncontradaException;

@Named("ReporteBn")
@ViewScoped
public class ReporteBean extends BaseBean implements Serializable {

	/**
	 * Primax
	 */
	private static final long serialVersionUID = 1L;

	private boolean excel;

	private String reportPath;

	private Map<String, String[]> params;

	private StreamedContent download;

	@Inject
	private AppMain appMain;

	public void imprimirPdf() {
		excel = false;
	}

	public void imprimirExcel(Long idRep, String p1, String p2, String p3, String p4, String p5, String p6, String p7,
			String p8, String p9, String p10, boolean condicion) {

		excel = condicion;
		params = new HashMap<String, String[]>();

		String idreqRep = idRep.toString();

		params.put("p1", new String[] { p1 == null ? "" : p1 });
		params.put("p2", new String[] { p2 == null ? "" : p2 });
		params.put("p3", new String[] { p3 == null ? "" : p3 });
		params.put("p4", new String[] { p4 == null ? "" : p4 });
		params.put("p5", new String[] { p5 == null ? "" : p5 });
		params.put("p6", new String[] { p6 == null ? "" : p6 });
		params.put("p7", new String[] { p7 == null ? "" : p7 });
		params.put("p8", new String[] { p8 == null ? "" : p8 });
		params.put("p9", new String[] { p9 == null ? "" : p9 });
		params.put("p10", new String[] { p10 == null ? "" : p10 });

		switch (idreqRep) {

		case "1":
			/* Reporte Plantilla Criterio General y Especifico */
			try {
				if (p5.endsWith("2")) {
					ReportePlantillaCriterioEspecifico reportePlantillaCriterioEspecifico = new ReportePlantillaCriterioEspecifico();
					exportIt(reportePlantillaCriterioEspecifico.getReport(params,
							WebAppUtil.getServletContext().getRealPath(reportPath), WebAppUtil.getServletContext(),
							excel), "CheckList" + "_" + p3 + "_" + p4);
				} else {
					ReportePlantillaCriterioGeneral reportePlantillaCriterioGeneral = new ReportePlantillaCriterioGeneral();
					exportIt(reportePlantillaCriterioGeneral.getReport(params,
							WebAppUtil.getServletContext().getRealPath(reportPath), WebAppUtil.getServletContext(),
							excel), "CheckList" + "_" + p3 + "_" + p4);
				}

			} catch (NumberFormatException | EntidadNoEncontradaException e) {
				e.printStackTrace();
			}
			break;

		case "2":
			/* Reporte Plantilla Matriz de Riesgo */
			ReportePlantillaMatrizEspecifico reportePlantillaMatrizEspecifico = new ReportePlantillaMatrizEspecifico();
			try {
				exportIt(reportePlantillaMatrizEspecifico.getReport(params,
						WebAppUtil.getServletContext().getRealPath(reportPath), WebAppUtil.getServletContext(), excel),
						"Matriz" + "_" + p2 + "_" + p3);
			} catch (NumberFormatException | EntidadNoEncontradaException e) {
				e.printStackTrace();
			}
			break;
		case "3":
			/* Reporte Proceso Detalle */
			ReporteProcesoDetalle reporteProcesoDetalle = new ReporteProcesoDetalle();
			try {
				exportIt(reporteProcesoDetalle.getReport(params, WebAppUtil.getServletContext().getRealPath(reportPath),
						WebAppUtil.getServletContext(), excel), "ReporteDetalleKPIs");
			} catch (NumberFormatException | EntidadNoEncontradaException e) {
				e.printStackTrace();
			}
			break;

		case "4":
			/* Reporte Individual Caja Chica Pista/Tienda */
			ReporteIndividualArqueoCajaPT reporteIndividualArqueoCajaPT = new ReporteIndividualArqueoCajaPT();
			try {
				exportIt(reporteIndividualArqueoCajaPT.getReport(params,
						WebAppUtil.getServletContext().getRealPath(reportPath), WebAppUtil.getServletContext(), excel),
						"ArqueoVisitaControl_Interno" + "_" + p4);
			} catch (NumberFormatException | EntidadNoEncontradaException e) {
				e.printStackTrace();
			}
			break;

		case "5":
			/* Reporte Individual Caja Chica Fondo Suelto */
			ReporteIndividualArqueoCajaFondoSuelto reporteIndividualArqueoCajaFondoSuelto = new ReporteIndividualArqueoCajaFondoSuelto();
			try {
				exportIt(reporteIndividualArqueoCajaFondoSuelto.getReport(params,
						WebAppUtil.getServletContext().getRealPath(reportPath), WebAppUtil.getServletContext(), excel),
						"ArqueoDisponibilidadFondoSuelto");
			} catch (NumberFormatException | EntidadNoEncontradaException e) {
				e.printStackTrace();
			}
			break;
		case "6":
			/* Reporte Individual CheckList Ejecutado General */
			ReporteIndividualCheckListEjecutado reporteIndividualCheckListEjecutado = new ReporteIndividualCheckListEjecutado();
			try {
				exportIt(reporteIndividualCheckListEjecutado.getReport(params,
						WebAppUtil.getServletContext().getRealPath(reportPath), WebAppUtil.getServletContext(), excel),
						"CheckList" + "_" + p4 + "_" + p3);
			} catch (NumberFormatException | EntidadNoEncontradaException e) {
				e.printStackTrace();
			}
			break;
		case "7":
			/* Reporte Individual CheckList Ejecutado Especifico */
			ReporteIndividualCheckListEjecutadoEspecifico reporteIndividualCheckListEjecutadoEspecifico = new ReporteIndividualCheckListEjecutadoEspecifico();
			try {
				exportIt(reporteIndividualCheckListEjecutadoEspecifico.getReport(params,
						WebAppUtil.getServletContext().getRealPath(reportPath), WebAppUtil.getServletContext(), excel),
						"CheckList" + "_" + p4 + "_" + p3);
			} catch (NumberFormatException | EntidadNoEncontradaException e) {
				e.printStackTrace();
			}
			break;
		case "8":
			/* Reporte Individual Matriz Riego */
			ReporteIndividualMatrizEjecutado reporteIndividualMatrizEjecutado = new ReporteIndividualMatrizEjecutado();
			try {
				exportIt(reporteIndividualMatrizEjecutado.getReport(params,
						WebAppUtil.getServletContext().getRealPath(reportPath), WebAppUtil.getServletContext(), excel),
						"MatrizRiesgo" + "_" + p4 + "_" + p3);
			} catch (NumberFormatException | EntidadNoEncontradaException e) {
				e.printStackTrace();
			}
			break;
		case "9":
			/* Reporte Individual Caja Chica General */
			ReporteIndividualArqueoCajaChicaGeneral reporteIndividualArqueoCajaChicaGeneral = new ReporteIndividualArqueoCajaChicaGeneral();
			try {
				exportIt(reporteIndividualArqueoCajaChicaGeneral.getReport(params,
						WebAppUtil.getServletContext().getRealPath(reportPath), WebAppUtil.getServletContext(), excel),
						"ArqueoCajaChicaEstaciónServicio");
			} catch (NumberFormatException | EntidadNoEncontradaException e) {
				e.printStackTrace();
			}
			break;
		case "10":
			/* Reporte Individual Arqueo Consolidado */
			ReporteIndividualArqueoConsolidado reporteIndividualArqueoConsolidado = new ReporteIndividualArqueoConsolidado();
			try {
				exportIt(reporteIndividualArqueoConsolidado.getReport(params,
						WebAppUtil.getServletContext().getRealPath(reportPath), WebAppUtil.getServletContext(), excel),
						"ArqueoConsolidado");
			} catch (NumberFormatException | EntidadNoEncontradaException e) {
				e.printStackTrace();
			}
			break;
		case "11":
			/* Reporte Novedades Control */
			ReporteIndividualNovedadControl reporteIndividualNovedadControl = new ReporteIndividualNovedadControl();
			try {
				exportWord(reporteIndividualNovedadControl.getReport(params,
						WebAppUtil.getServletContext().getRealPath(reportPath), WebAppUtil.getServletContext(), excel),
						"Reporte de Novedades");
			} catch (NumberFormatException | EntidadNoEncontradaException e) {
				e.printStackTrace();
			}
			break;
		case "12":
			/* Reporte 01 Control Interno */
			ReporteGrupalEvaluacionPuntaje reporteGrupalEvaluacionPuntaje = new ReporteGrupalEvaluacionPuntaje();
			try {
				exportIt(reporteGrupalEvaluacionPuntaje.getReport(params,
						WebAppUtil.getServletContext().getRealPath(reportPath), WebAppUtil.getServletContext(), excel),
						"Reporte_Evaluacion_Puntaje_Estacion");
			} catch (NumberFormatException | EntidadNoEncontradaException e) {
				e.printStackTrace();
			}
			break;
		case "13":
			/* Reporte 01 Control Interno */
			ReporteGrupalEvaluacionNivelRiesgo reporteGrupalEvaluacionNivelRiesgo = new ReporteGrupalEvaluacionNivelRiesgo();
			try {
				exportIt(reporteGrupalEvaluacionNivelRiesgo.getReport(params,
						WebAppUtil.getServletContext().getRealPath(reportPath), WebAppUtil.getServletContext(), excel),
						"Reporte_Evaluacion_Nivel_Riesgo");
			} catch (NumberFormatException | EntidadNoEncontradaException e) {
				e.printStackTrace();
			}
			break;
		case "14":
			/* Reporte 03 Control Interno */
			ReporteGrupalEvaluacionConsolidado reporteGrupalEvaluacionConsolidado = new ReporteGrupalEvaluacionConsolidado();
			try {
				exportIt(reporteGrupalEvaluacionConsolidado.getReport(params,
						WebAppUtil.getServletContext().getRealPath(reportPath), WebAppUtil.getServletContext(), excel),
						"Reporte_Consolidado_Nivel_Riesgo");
			} catch (NumberFormatException | EntidadNoEncontradaException e) {
				e.printStackTrace();
			}
			break;
		case "15":
			/* Reporte Plan Acción Criterio General */
			ReporteIndividualPlanAccionGeneral reporteIndividualPlanAccionGeneral = new ReporteIndividualPlanAccionGeneral();
			try {
				exportIt(reporteIndividualPlanAccionGeneral.getReport(params,
						WebAppUtil.getServletContext().getRealPath(reportPath), WebAppUtil.getServletContext(), excel),
						"Reporte_Plan_Acción");
			} catch (NumberFormatException | EntidadNoEncontradaException e) {
				e.printStackTrace();
			}
			break;
		case "16":
			/* Reporte Plan Acción Criterio Especifico */
			ReporteIndividualPlanAccionEspecifico reporteIndividualPlanAccionEspecifico = new ReporteIndividualPlanAccionEspecifico();
			try {
				exportIt(reporteIndividualPlanAccionEspecifico.getReport(params,
						WebAppUtil.getServletContext().getRealPath(reportPath), WebAppUtil.getServletContext(), excel),
						"Reporte_Plan_Acción");
			} catch (NumberFormatException | EntidadNoEncontradaException e) {
				e.printStackTrace();
			}
			break;

		case "17":
			/* Reporte Tipo Evaluación */
			ReporteGrupalTipoEvaluacion reporteGrupalTipoEvaluacion = new ReporteGrupalTipoEvaluacion();
			try {
				exportIt(reporteGrupalTipoEvaluacion.getReport(params,
						WebAppUtil.getServletContext().getRealPath(reportPath), WebAppUtil.getServletContext(), excel),
						"Reporte_Tipo_Evaluación");
			} catch (NumberFormatException | EntidadNoEncontradaException e) {
				e.printStackTrace();
			}
			break;

		case "18":
			/* Reporte Planificación y Complimiento */
			ReporteGrupalEvaluacionPlanificacion reporteGrupalEvaluacionPlanificacion = new ReporteGrupalEvaluacionPlanificacion();
			try {
				exportIt(reporteGrupalEvaluacionPlanificacion.getReport(params,
						WebAppUtil.getServletContext().getRealPath(reportPath), WebAppUtil.getServletContext(), excel),
						"Reporte_Panificacion_Cumplimiento");
			} catch (NumberFormatException | EntidadNoEncontradaException e) {
				e.printStackTrace();
			}
			break;
		case "19":
			/* Reporte Variación de Visitas */
			ReporteGrupalEvaluacionVariacion reporteGrupalEvaluacionVariacion = new ReporteGrupalEvaluacionVariacion();
			try {
				exportIt(reporteGrupalEvaluacionVariacion.getReport(params,
						WebAppUtil.getServletContext().getRealPath(reportPath), WebAppUtil.getServletContext(), excel),
						"Reporte_Variación_Visitas");
			} catch (NumberFormatException | EntidadNoEncontradaException e) {
				e.printStackTrace();
			}
			break;
		case "20":
			/* Reporte Plan Acción Seguimiento Criterio Especifico */
			ReporteIndividualPlanAccionSeguimientoEspecifico reporteIndividualPlanAccionSeguimientoEspecifico = new ReporteIndividualPlanAccionSeguimientoEspecifico();
			try {
				exportIt(reporteIndividualPlanAccionSeguimientoEspecifico.getReport(params,
						WebAppUtil.getServletContext().getRealPath(reportPath), WebAppUtil.getServletContext(), excel),
						"Reporte_Plan_Acción");
			} catch (NumberFormatException | EntidadNoEncontradaException e) {
				e.printStackTrace();
			}
			break;
		case "21":
			/* Reporte Novedades CCTV */
			ReporteIndividualNovedadCCTV reporteIndividualNovedadCCTV = new ReporteIndividualNovedadCCTV();
			try {
				exportWord(reporteIndividualNovedadCCTV.getReport(params,
						WebAppUtil.getServletContext().getRealPath(reportPath), WebAppUtil.getServletContext(), excel),
						"Reporte de Novedades CTTV");
			} catch (NumberFormatException | EntidadNoEncontradaException e) {
				e.printStackTrace();
			}
			break;

		case "22":
			/* Reporte 010 Control Interno */
			ReporteGrupalEvaluacionPuntaje reporteGrupalEvaluacionPuntaje0 = new ReporteGrupalEvaluacionPuntaje();
			try {
				exportIt(reporteGrupalEvaluacionPuntaje0.getReport(params,
						WebAppUtil.getServletContext().getRealPath(reportPath), WebAppUtil.getServletContext(), excel),
						"Reporte_Evaluacion_Puntaje_Estacion");
			} catch (NumberFormatException | EntidadNoEncontradaException e) {
				e.printStackTrace();
			}
			break;

		case "23":
			/* Reporte 011 Consolidado Proceso */
			ReporteGrupalProcesoConsolidado reporteGrupalConsolidadoProceso = new ReporteGrupalProcesoConsolidado();
			try {
				exportIt(reporteGrupalConsolidadoProceso.getReport(params,
						WebAppUtil.getServletContext().getRealPath(reportPath), WebAppUtil.getServletContext(), excel),
						"Reporte_Consolidado_Proceso_Estacion");
			} catch (NumberFormatException | EntidadNoEncontradaException e) {
				e.printStackTrace();
			}
			break;
		case "24":
			/* Reporte Planificación y Cumplimiento Inventario */
			ReporteGrupalPlanificacionInventario reporteGrupalPlanificacionInventario = new ReporteGrupalPlanificacionInventario();
			try {
				exportIt(reporteGrupalPlanificacionInventario.getReport(params,
						WebAppUtil.getServletContext().getRealPath(reportPath), WebAppUtil.getServletContext(), excel),
						"Reporte_Planificacion_Inventario");
			} catch (NumberFormatException | EntidadNoEncontradaException e) {
				e.printStackTrace();
			}
			break;
		case "25":
			/* Reporte Tablero Control */
			ReporteGrupalTableroControl reporteGrupalTableroControl = new ReporteGrupalTableroControl();
			try {
				exportIt(reporteGrupalTableroControl.getReport(params,
						WebAppUtil.getServletContext().getRealPath(reportPath), WebAppUtil.getServletContext(), excel),
						"Reporte_Tablero_Control");
			} catch (NumberFormatException | EntidadNoEncontradaException e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}

	}

	public void exportIt(ByteArrayOutputStream outputStream, String nombre) {
		String nombreArchivo = "";
		if (excel) {
			nombreArchivo = nombre + ".xlsx";
		} else {
			nombreArchivo = nombre + ".pdf";
		}
		ByteArrayInputStream stream = new ByteArrayInputStream(outputStream.toByteArray());
		appMain.fileDownload(stream, nombreArchivo);
	}

	public void exportWord(ByteArrayOutputStream outputStream, String nombre) {
		String nombreArchivo = "";
		if (excel) {
			nombreArchivo = nombre + ".docx";
		} else {
			nombreArchivo = nombre + ".pdf";
		}
		ByteArrayInputStream stream = new ByteArrayInputStream(outputStream.toByteArray());
		appMain.fileDownload(stream, nombreArchivo);
	}

	public boolean isExcel() {
		return excel;
	}

	public void setExcel(boolean excel) {
		this.excel = excel;
	}

	public AppMain getAppMain() {
		return appMain;
	}

	public void setAppMain(AppMain appMain) {
		this.appMain = appMain;
	}

	@Override
	public void init() {
		this.reportPath = File.separator + "pages" + File.separator + "jasper";
	}

	public StreamedContent getDownload() {
		return download;
	}

	public void setDownload(StreamedContent download) {
		this.download = download;
	}

	public String formattedDate(Date date, String formato) {
		if (date != null && formato != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(formato);
			return sdf.format(date);
		}
		return "";
	}

	@Override
	public void onDestroy() {

	}

}
