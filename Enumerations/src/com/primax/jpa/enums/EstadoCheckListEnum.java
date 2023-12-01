package com.primax.jpa.enums;

public enum EstadoCheckListEnum {

	ESTADO_CHECK("Todos"),
	PLANTILLA("PLANTILLA"),
	GENERADO("GENERADO"), 
	APROBADO("APROBADO"), 
	AGENDADA("AGENDADA"),
	EN_EJECUCION("EN EJECUCION"),
	INCONCLUSO("INCONCLUSO"),
	EJECUTADO("EJECUTADO"),
	NO_EJECUTADO("NO EJECUTADO");

	private String estado;

	EstadoCheckListEnum(String estadoDesc) {
		this.estado = estadoDesc;
	}

	public String getDescripcion() {
		return this.estado;
	}

}
