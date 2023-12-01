package com.primax.jpa.enums;

public enum EstadoChequesEnum {

	EDP("ESPERA_DEPOSITO"), DEP("DEPOSITADO"), EFE("EFECTIVIZADO"), INV("INVALIDADO");

	private String estado;	

	EstadoChequesEnum(String estadoDesc) {
		this.estado = estadoDesc;
	}

	public String getDescripcion() {
		return this.estado;
	}

}
