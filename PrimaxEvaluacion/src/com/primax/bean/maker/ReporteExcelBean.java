package com.primax.bean.maker;

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
import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.exc.gen.EntidadNoEncontradaException;

@Named("reporteExcelBn")
@ViewScoped
public class ReporteExcelBean extends BaseBean implements Serializable {

	/**
	 * 
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
			String p8, String p9, String p10) {

		excel = true;
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

		case "0":
			/* Reporte Comparativo */
			ReporteComparativo reporteComparativo = new ReporteComparativo();
			try {
				exportIt(reporteComparativo.getReport(params, WebAppUtil.getServletContext().getRealPath(reportPath),
						WebAppUtil.getServletContext(), excel), "Comparativo" + "_" + p2 + "_" + p1);
			} catch (NumberFormatException | EntidadNoEncontradaException e) {
				e.printStackTrace();
			}
			break;
		}
	}

	public void exportIt(ByteArrayOutputStream outputStream, String nombre) {

		String nombreArchivo = nombre + ".xlsx";
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
