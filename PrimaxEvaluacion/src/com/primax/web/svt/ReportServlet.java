package com.primax.web.svt;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.primax.bean.maker.ReporteGrupalEvaluacionConsolidado;
import com.primax.bean.maker.ReporteGrupalEvaluacionNivelRiesgo;
import com.primax.bean.maker.ReporteGrupalEvaluacionPlanificacion;
import com.primax.bean.maker.ReporteGrupalEvaluacionPuntaje;
import com.primax.bean.maker.ReporteGrupalEvaluacionVariacion;
import com.primax.bean.maker.ReporteGrupalPlanificacionInventario;
import com.primax.bean.maker.ReporteGrupalProcesoConsolidado;
import com.primax.bean.maker.ReporteGrupalTipoEvaluacion;
import com.primax.bean.maker.ReporteIndividualArqueoCajaPT;

@WebServlet("/reporte/*")
public class ReportServlet extends HttpServlet {

	private static final long serialVersionUID = -6803373446806901358L;

	private String reportPath;

	@Override
	public void init() throws ServletException {
		this.reportPath = File.separator + "pages" + File.separator + "jasper";
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String reqRep = request.getParameter("rptid");
		if (reqRep == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		@SuppressWarnings("unchecked")
		Map<String, String[]> params = request.getParameterMap();
		String path = getServletContext().getRealPath(reportPath);
		switch (reqRep) {
			case "4":
				try {
					ReporteIndividualArqueoCajaPT reporteGrupalEjecutivo = new ReporteIndividualArqueoCajaPT();
					exportIt(reporteGrupalEjecutivo.getReport(params, path, getServletContext(), false), response);
				} catch (Exception e) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
					e.printStackTrace();
				}
				break;

			case "12":
				try {
					ReporteGrupalEvaluacionPuntaje reporteGrupalEvaluacionPuntaje = new ReporteGrupalEvaluacionPuntaje();
					exportIt(reporteGrupalEvaluacionPuntaje.getReport(params, path, getServletContext(), false),
							response);
				} catch (Exception e) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
					e.printStackTrace();
				}
				break;
			case "13":
				try {
					ReporteGrupalEvaluacionNivelRiesgo reporteGrupalEvaluacionNivelRiesgo = new ReporteGrupalEvaluacionNivelRiesgo();
					exportIt(reporteGrupalEvaluacionNivelRiesgo.getReport(params, path, getServletContext(), false),
							response);
				} catch (Exception e) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
					e.printStackTrace();
				}
				break;
			case "14":
				try {
					ReporteGrupalEvaluacionConsolidado reporteGrupalEvaluacionConsolidado = new ReporteGrupalEvaluacionConsolidado();
					exportIt(reporteGrupalEvaluacionConsolidado.getReport(params, path, getServletContext(), false),
							response);
				} catch (Exception e) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
					e.printStackTrace();
				}
				break;

			case "17":
				try {
					ReporteGrupalTipoEvaluacion reporteGrupalTipoEvaluacion = new ReporteGrupalTipoEvaluacion();
					exportIt(reporteGrupalTipoEvaluacion.getReport(params, path, getServletContext(), false), response);
				} catch (Exception e) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
					e.printStackTrace();
				}
				break;

			case "18":
				try {
					ReporteGrupalEvaluacionPlanificacion reporteGrupalEvaluacionPlanificacion = new ReporteGrupalEvaluacionPlanificacion();
					exportIt(reporteGrupalEvaluacionPlanificacion.getReport(params, path, getServletContext(), false),
							response);
				} catch (Exception e) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
					e.printStackTrace();
				}
				break;

			case "19":
				try {
					ReporteGrupalEvaluacionVariacion reporteGrupalEvaluacionVariacion = new ReporteGrupalEvaluacionVariacion();
					exportIt(reporteGrupalEvaluacionVariacion.getReport(params, path, getServletContext(), false),
							response);
				} catch (Exception e) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
					e.printStackTrace();
				}
				break;
			case "21":
				try {
					ReporteGrupalEvaluacionVariacion reporteGrupalEvaluacionVariacion = new ReporteGrupalEvaluacionVariacion();
					exportIt(reporteGrupalEvaluacionVariacion.getReport(params, path, getServletContext(), false),
							response);
				} catch (Exception e) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
					e.printStackTrace();
				}
				break;
			case "22":
				try {
					ReporteGrupalEvaluacionVariacion reporteGrupalEvaluacionVariacion = new ReporteGrupalEvaluacionVariacion();
					exportIt(reporteGrupalEvaluacionVariacion.getReport(params, path, getServletContext(), false),
							response);
				} catch (Exception e) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
					e.printStackTrace();
				}
				break;
			case "23":
				try {
					/* Reporte 011 Consolidado Proceso */
					ReporteGrupalProcesoConsolidado reporteGrupalConsolidadoProceso = new ReporteGrupalProcesoConsolidado();
					exportIt(reporteGrupalConsolidadoProceso.getReport(params, path, getServletContext(), false),
							response);
				} catch (Exception e) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
					e.printStackTrace();
				}
				break;
			case "24":
				try {
					ReporteGrupalPlanificacionInventario reporteGrupalPlanificacionInv = new ReporteGrupalPlanificacionInventario();
					exportIt(reporteGrupalPlanificacionInv.getReport(params, path, getServletContext(), false),
							response);
				} catch (Exception e) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
					e.printStackTrace();
				}
				break;
		}
	}

	public void exportIt(ByteArrayOutputStream stream, HttpServletResponse response) {
		response.reset();
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=Reporte.pdf");
		BufferedOutputStream output = null;
		if (stream == null)
			return;
		try {
			output = new BufferedOutputStream(response.getOutputStream());
			output.write(stream.toByteArray());
			output.close();
		} catch (IOException e) {
			System.err.println("Error @ Export Report " + e.getMessage());
		}
	}
}
