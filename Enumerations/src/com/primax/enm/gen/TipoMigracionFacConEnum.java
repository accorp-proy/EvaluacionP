package com.primax.enm.gen;

public enum TipoMigracionFacConEnum {

	FAC("FACTURAS"),
	CON("CONSUMOS");

	private String descripcion;

	TipoMigracionFacConEnum(String id) {
		descripcion = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

}
