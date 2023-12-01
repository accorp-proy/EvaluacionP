package com.primax.exc.gen;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class EntidadNoBorradaException extends Exception {

	private static final long serialVersionUID = 1L;

	public EntidadNoBorradaException() {
		super();
	}

	public EntidadNoBorradaException(final String arg0, final Throwable arg1) {
		super(arg0, arg1);
	}

	public EntidadNoBorradaException(final String arg0) {
		super(arg0);
	}

	public EntidadNoBorradaException(final Throwable arg0) {
		super(arg0);
	}
}