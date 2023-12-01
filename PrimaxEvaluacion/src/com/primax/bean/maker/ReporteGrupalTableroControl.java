package com.primax.bean.maker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
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

public class ReporteGrupalTableroControl extends BaseReport {

	public ByteArrayOutputStream getReport(Map<String, String[]> params, String localPath, ServletContext servlet,
			boolean isexcel) throws NumberFormatException, EntidadNoEncontradaException {

		ByteArrayOutputStream printStream = null;
		Map<String, Object> paramRpt = new HashMap<String, Object>();

		try {
			String porcentaje = "";
			String param = params.get("p8")[0];
			String[] stringarray = param.split(",");
			for (int i = 0; i < stringarray.length; i++) {
				porcentaje = stringarray[i] + " %";
				// System.out.println(porcentaje);
				if (i == 0) {
					paramRpt.put("nivel_01_c", porcentaje);
				}
				if (i == 1) {
					paramRpt.put("nivel_02_c", porcentaje);
				}
				if (i == 2) {
					paramRpt.put("nivel_03_c", porcentaje);
				}
				if (i == 3) {
					paramRpt.put("nivel_04_c", porcentaje);
				}
				if (i == 4) {
					paramRpt.put("nivel_05_c", porcentaje);
				}
				if (i == 5) {
					paramRpt.put("nivel_06_c", porcentaje);
				}
			}
			porcentaje = "";
			param = params.get("p9")[0];
			stringarray = param.split(",");
			for (int i = 0; i < stringarray.length; i++) {
				porcentaje = stringarray[i] + " %";
				// System.out.println(porcentaje);
				if (i == 0) {
					paramRpt.put("nivel_01_e", porcentaje);
				}
				if (i == 1) {
					paramRpt.put("nivel_02_e", porcentaje);
				}
				if (i == 2) {
					paramRpt.put("nivel_03_e", porcentaje);
				}
				if (i == 3) {
					paramRpt.put("nivel_04_e", porcentaje);
				}
				if (i == 4) {
					paramRpt.put("nivel_05_e", porcentaje);
				}
				if (i == 5) {
					paramRpt.put("nivel_06_e", porcentaje);
				}
			}
			Long idUsuario = Long.parseLong(params.get("p1")[0]);
			paramRpt.put("p_id_usuario", idUsuario);
			paramRpt.put("nivel_01", params.get("p2")[0]);
			paramRpt.put("nivel_02", params.get("p3")[0]);
			paramRpt.put("nivel_03", params.get("p4")[0]);
			paramRpt.put("nivel_04", params.get("p5")[0]);
			paramRpt.put("nivel_05", params.get("p6")[0]);
			paramRpt.put("nivel_06", params.get("p7")[0]);
			paramRpt.put("SUBREPORT_DIR", localPath + File.separator + "tableroControl" + File.separator);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getReport " + " " + e.getMessage());
		}
		Connection cn = null;

		try {
			cn = DataSourceConnection.getJNDIConnection();
			JasperPrint jprint = JasperFillManager.fillReport(
					localPath + File.separator + "tableroControl" + File.separator + "rptTableroControl.jasper",
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
			} catch (SQLException e) {
				System.err.println("Error en cerrar conexion :" + e.getMessage());
			}
		}
		return printStream;
	}

}
