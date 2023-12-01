package com.primax.jpa.enums;

public enum TipoCheckListEnum {

	CRITERIO_GENERAL("CRITERIO GENERAL"),
	CRITERIO_ESPECIFICO("CRITERIO ESPECIFICO");

	private String estado;

	TipoCheckListEnum(String estadoDesc) {
		this.estado = estadoDesc;
	}

	public String getDescripcion() {
		return this.estado;
	}

}
