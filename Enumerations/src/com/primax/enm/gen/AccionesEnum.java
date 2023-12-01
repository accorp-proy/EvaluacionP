package com.primax.enm.gen;

public enum AccionesEnum {

	NO_TYPE("NONE"), // 0
	BUSCAR("Buscar"), // 1
	GRABAR("Grabar"), // 2
	EDITAR("Editar"), // 3
	ELIMINAR("Eliminar"), // 4
	NUEVO("Nuevo"), // 5
	EXPORTAR("Exportar"), // 6
	ANULAR("Anular");// 7

	private AccionesEnum(final String descripcion) {
		this.descripcion = descripcion;

	}

	private String descripcion;

	public String getDescripcion() {
		return descripcion;
	}

}