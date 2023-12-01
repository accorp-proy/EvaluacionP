package com.primax.srv.util;

public class QUL {

    public static long toLong(String texto) {
	long numero = 0;
	try {
	    numero = Long.parseLong(texto);
	    return numero;
	} catch (Exception e) {
	    return numero;
	}
    }

    public static String getString(String texto) {
	try {
	    Long.parseLong(texto);
	    return "";
	} catch (Exception e) {
	    return texto;
	}
    }

}
