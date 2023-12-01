package com.primax.exc.gen;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class EntidadNoEncontradaException extends Exception {

	private static final long serialVersionUID = 1L;

	public EntidadNoEncontradaException() {
		super();
	}

	public EntidadNoEncontradaException(final String arg0, final Throwable arg1) {
		super(arg0, arg1);
	}

	public EntidadNoEncontradaException(final String arg0) {
		super(arg0);
	}

	public EntidadNoEncontradaException(final Throwable arg0) {
		super(arg0);
	}
}