package com.primax.enm.gen;

public enum ParametrosGeneralesEnum {

	
	ZONAS("1"),
	EMPRESA("171"),
	FORMAPAGO("1"),
	ETIQUETA("176"),
	COLOR_MATRIZ("26"),
	TIPO_CREDITO("18"),
	TIPO_CLIENTE("13"),
	TIPO_ACUERDO_PAGO("21"),
	TIPO_IDENTIFICACION("8"),
	CONFIGURACION_CORREOS("59");
	
	private String descripcion;

	private ParametrosGeneralesEnum(final String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Long getValue() {
		return Long.parseLong(descripcion);
	}

}
