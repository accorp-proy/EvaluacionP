package com.primax.web.flt;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.primax.bean.ss.AppMain;
import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.sec.MenuEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IMenuEtDao;

public class AccessFilter extends BaseNaming implements Filter {

	private static final String rootPage = "/pages/main.jsf";
	private static final String logPage = "/login.jsf";
	private static final String permanent_1 = "/ckeditor";
	private static final String permanent_2 = "/ckfinder";

	@Inject
	private AppMain appMain;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {
			HttpServletRequest reqt = (HttpServletRequest) request;

			HttpServletResponse resp = (HttpServletResponse) response;

			UsuarioEt user = null;
			if (appMain != null)
				user = appMain.getUsuario();

			String uri = reqt.getRequestURI().toString().replace(reqt.getContextPath(), "");

			if (uri.equals(logPage)) {
				resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
				resp.setHeader("Pragma", "no-cache");
				resp.setDateHeader("Expires", 0);
				chain.doFilter(request, response);
			} else if (uri.equals(rootPage) && user != null) {
				chain.doFilter(request, response);
			} else if (uri.equals(rootPage) && user == null) {
				resp.sendRedirect(reqt.getContextPath() + logPage);
			} else if (!uri.equals(rootPage) && user == null) {
				resp.sendRedirect(reqt.getContextPath() + logPage);
			} else if (uri.startsWith(permanent_1) || uri.startsWith(permanent_2) && user != null) {
				chain.doFilter(request, response);
			} else {
				IMenuEtDao menuDao = EJB(EnumNaming.IMenuEtDao);
				MenuEt menu = null;
				String realUrl = uri.replace(".jsf", ".xhtml");
				menu = menuDao.getMenuUrl(realUrl);
				if (menu == null) {
					resp.sendRedirect(reqt.getContextPath() + "/info/404.xhtml");
				} else if (menuDao.verificaMenu(realUrl, user)) {
					chain.doFilter(request, response);
				} else {
					resp.sendRedirect(reqt.getContextPath() + "/info/access.xhtml");
				}
				menuDao.remove();
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("Init Filter");
	}

	@Override
	public void destroy() {
		System.out.println("Destoy Filter");
	}

}
