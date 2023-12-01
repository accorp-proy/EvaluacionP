package com.primax.ejb.lkp;

import javax.naming.Context;
import javax.naming.InitialContext;

public class BaseNaming {

	private Context context;

	private EnumNaming enumerador;
	private Object interfacing;

	@SuppressWarnings("unchecked")
	protected <T> T EJB(EnumNaming enumerador) {
		interfacing = null;
		this.enumerador = enumerador;
		getProxy();
		return (T) interfacing;
	}

	@SuppressWarnings("unchecked")
	private <T> void getProxy() {
		try {
			context = new InitialContext();
			interfacing = (T) context.lookup(enumerador.getNaming());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
