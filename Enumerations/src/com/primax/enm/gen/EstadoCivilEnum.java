package com.primax.enm.gen;

public enum EstadoCivilEnum {

	SOL("Soltero/a"), UDH("Unión de Hecho"), CAC("Casado/a"), DIV("Divorciado/a"), VIU("Viudo/a");

	private String descripcion;

	EstadoCivilEnum(String desc) {
		this.descripcion = desc;
	}

	public String getDescripcion() {
		return descripcion;
	}

}
