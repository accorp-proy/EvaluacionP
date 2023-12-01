package com.primax.bean.vs.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import com.primax.ejb.lkp.BaseNaming;

public class BaseReport extends BaseNaming {

	public InputStream getLogo(ServletContext ctx) {
		String path = ctx.getRealPath("/resources/ultima-layout/images/logo.png");
		File arch = new File(path);
		InputStream img = null;
		try {
			img = new FileInputStream(arch);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return img;
	}

	public InputStream getLogoAtimasa(ServletContext ctx) {
		String path = ctx.getRealPath("/resources/ultima-layout/images/atimasa.png");
		File arch = new File(path);
		InputStream img = null;
		try {
			img = new FileInputStream(arch);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return img;
	}

	public String rutaLogo(ServletContext ctx) {
		String path = "";
		try {
			path = ctx.getRealPath("/resources/ultima-layout/images/atimasa.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}

	public static InputStream getLogoPrimax() {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		// System.out.println(servletContext.getRealPath("/"));
		String path = (servletContext.getRealPath("/") + "/resources/ultima-layout/images/logo.png");
		File arch = new File(path);
		InputStream img = null;
		try {
			img = new FileInputStream(arch);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return img;
	}

	public static InputStream getLogoAtimasa() {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		// System.out.println(servletContext.getRealPath("/"));
		String path = (servletContext.getRealPath("/") + "/resources/ultima-layout/images/atimasa.png");
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
