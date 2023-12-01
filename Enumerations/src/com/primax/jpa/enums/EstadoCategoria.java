package com.primax.jpa.enums;

public enum EstadoCategoria {
	
	N("N/A"),
	D("MALO"),
	AAA("TOP"), 
	B("BUENO"),
	R("NUEVO"),
	C("NORMAL"),
	P("PREPAGO"),
	E("EMPLEADO"),
	A("EXCELENTE");

	private String estado;

	EstadoCategoria(String estadoDesc) {
		this.estado = estadoDesc;
	}

	public String getDescripcion() {
		return this.estado;
	}

}
