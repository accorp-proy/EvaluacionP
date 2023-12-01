package com.primax.jpa.enums;

public enum EstadoMigracionFacturaConsumoEnum {

	ESP("ESPERA"), PRO("PROCESO"), TER("TERMINADO"), PROC("PROCESANDO");

	private String estado;

	EstadoMigracionFacturaConsumoEnum(String estadoDesc) {
		this.estado = estadoDesc;		
	}

	public String getDescripcion() {
		return this.estado;
	}
}
