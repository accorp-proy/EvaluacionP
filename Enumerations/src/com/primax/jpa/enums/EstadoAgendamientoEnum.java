package com.primax.jpa.enums;

public enum EstadoAgendamientoEnum {

	ESP("EN_ESPERA"), ACT("ACTIVO"), INA("INACTIVO");

	private String estado;	

	EstadoAgendamientoEnum(String estadoDesc) {
		this.estado = estadoDesc;
	}

	public String getDescripcion() {
		return this.estado;
	}

}
