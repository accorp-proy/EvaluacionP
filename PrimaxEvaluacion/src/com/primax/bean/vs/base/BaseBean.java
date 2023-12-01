package com.primax.bean.vs.base;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.primax.ejb.lkp.BaseNaming;

import org.primefaces.context.RequestContext;

public abstract class BaseBean extends BaseNaming {

	protected void showInfo(String texto, Severity severidad) {
		FacesMessage face = new FacesMessage(texto);
		if (severidad != null)
			face.setSeverity(severidad);
		FacesContext.getCurrentInstance().addMessage(null, face);
	}

	protected void showInfo(String texto, Severity severidad, String forMessage, String details) {
		FacesMessage face = new FacesMessage(texto, details);
		face.setSeverity(severidad);
		FacesContext.getCurrentInstance().addMessage(forMessage, face);
	}

	protected ExternalContext getContextE() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	protected static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	protected static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	protected static RequestContext getRequestContext() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		return requestContext;
	}

	protected static String getUserName() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		return session.getAttribute("username").toString();
	}

	protected static String getUserId() {
		HttpSession session = getSession();
		if (session != null)
			return (String) session.getAttribute("userid");
		else
			return null;
	}

	@PostConstruct
	protected void postContruct() {
		init();
	}

	protected abstract void init();

	@PreDestroy
	protected void finalize() {
		onDestroy();
	}

	protected abstract void onDestroy();
}
