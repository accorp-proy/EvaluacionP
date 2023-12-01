package com.primax.web.err;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class ErrorHandler {

	public String getStatusCode() {
		String err =
				String.valueOf((Integer) FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
						.get("javax.servlet.error.status_code"));
		if (err.equals("401"))
			err = err + "[ No Autorizado para visualizar el recurso solicitado (Access is Denied) ]";
		return err;
	}

	public String getMessage() {
		String err = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("javax.servlet.error.message");
		return err;
	}

	public String getExceptionType() {
		Object err = FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("javax.servlet.error.exception_type");
		return err != null ? err.toString() : "Excepcion Desconocida";
	}

	public String getException() {
		Object err = FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("javax.servlet.error.exception");
		if (err != null) {
			String valErr = null;
			if (err instanceof Exception) {
				Exception errEx = (Exception) err;
				valErr = errEx.getMessage();
			}

			if (err instanceof StackOverflowError) {
				StackOverflowError errEx = (StackOverflowError) err;
				valErr = errEx.getMessage();
			}
			return valErr;
		}
		return "Excepcion Desconocida";
	}

	public String getRequestURI() {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("javax.servlet.error.request_uri");
	}

	public String getServletName() {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("javax.servlet.error.servlet_name");
	}

}
