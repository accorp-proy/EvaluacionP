package com.primax.enm.gen;

public enum TipoGestionCarteraEnum {

	NOTYPE(""),
	EMAIL("EMAIL"),
	LLAMADA("LLAMADA"),
	VISITA("VISITA"),
	SMS("SMS");

	private String descripcion;

	TipoGestionCarteraEnum(String id) {
		descripcion = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

}
