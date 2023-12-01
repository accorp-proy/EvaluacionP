package com.primax.jpa.enums;

public enum EstadoMillasLatamEnum {

	GEN("GENERADO"), UTI("UTILIZADO");

	private String estado;	

	EstadoMillasLatamEnum(String estadoDesc) {
		this.estado = estadoDesc;
	}

	public String getDescripcion() {
		return this.estado;
	}

}
