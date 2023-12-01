package com.primax.jpa.enums;

public enum EstadoEnum {

	ACT("ACTIVO", 0, 1), INA("INACTIVO", 1, 1), ELI("ELIMINADO", 2, 2), SUS("BLOQUEADO", 3, 3), HIS("HISTORICO", 4, 4);

	private String estado;
	private int indice;
	private int grupo;

	EstadoEnum(String estadoDesc, int indx, int grupo) {
		this.estado = estadoDesc;
		this.indice = indx;
		this.grupo = grupo;
	}

	public String getDescripcion() {
		return this.estado;
	}

	public int getIndice() {
		return this.indice;
	}

	public int getGrupo() {
		return this.grupo;
	}

}
