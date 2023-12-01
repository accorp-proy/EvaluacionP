package com.primax.jpa.enums;

public enum EstadoPlanAccionEnum {

	ESTADO_PLAN_A("Todos"),
	PENDIENTE("PENDIENTE"),
	INGRESADO("INGRESADO");

	private String estado;

	EstadoPlanAccionEnum(String estadoDesc) {
		this.estado = estadoDesc;
	}

	public String getDescripcion() {
		return this.estado;
	}

}
