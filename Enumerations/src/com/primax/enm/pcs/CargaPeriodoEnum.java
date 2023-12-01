package com.primax.enm.pcs;

public enum CargaPeriodoEnum {

	MIN("MINUTO", 2), HOR("HORA", 1);

	private String descripcion;
	private int codigo;

	CargaPeriodoEnum(String descripcion, int codigo) {
		this.descripcion = descripcion;
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public int getCodigo() {
		return codigo;
	}

}
