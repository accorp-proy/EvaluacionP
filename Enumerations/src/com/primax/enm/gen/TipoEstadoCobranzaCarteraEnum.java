package com.primax.enm.gen;

public enum TipoEstadoCobranzaCarteraEnum {

	NOTYPE(""),
	CORRIENTE("CORRIENTE"),
	CORRIENTE_FUTURA("CORRIENTE_FUTURA"),
	VENCIDA("VENCIDA"),
	PAGADA("PAGADA");

	private String descripcion;

	TipoEstadoCobranzaCarteraEnum(String id) {
		descripcion = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

}
