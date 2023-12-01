package com.primax.enm.gen;

public enum EntidadWSEnum {

    SRI("Servicio Rentas Internas"),
    REGCIV("Registro Civil"),
    REGCIV_NOM("Registro Civil-Consulta Nombre"),
    SNAP("Secretaria Nacional de la Administracion Publica"),
    DINARDAP("Direccion Nacional de Registro de Datos Publicos"),
    SENECYT_CED("Titulos de personas");

    private String id;

    EntidadWSEnum(String value) {
	this.id = value;
    }

    public String getValue() {
	return this.id;
    }

}
