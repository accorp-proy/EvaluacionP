package com.primax.enm.gen;

public enum DatosDinarDap {

    CNE("InformacionCNE"),
    InfoCivil("InformacionCivil");

    private String value;

    private DatosDinarDap(String id) {
	value = id;
    }

    public String getDescripcion() {
	return this.value;
    }
}
