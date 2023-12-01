package com.primax.bean.as;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;

@Named("webAppUtil")
@ApplicationScoped
public class WebAppUtil {

	private String layout = "blue";
	private String theme = "blue";
	private boolean overlayMenu = true;
	private boolean darkMenu = false;
	private boolean orientationRTL = false;

	public static String getWebPath() {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		return servletContext.getRealPath("");
	}

	public static ServletContext getServletContext() {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		return servletContext;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public boolean isOverlayMenu() {
		return overlayMenu;
	}

	public void setOverlayMenu(boolean overlayMenu) {
		this.overlayMenu = overlayMenu;
	}

	public boolean isDarkMenu() {
		return darkMenu;
	}

	public void setDarkMenu(boolean darkMenu) {
		this.darkMenu = darkMenu;
	}

	public boolean isOrientationRTL() {
		return orientationRTL;
	}

	public void setOrientationRTL(boolean orientationRTL) {
		this.orientationRTL = orientationRTL;
	}

}
