package com.primax.enm.gen;

public enum ResponsableEnum {
	
	
	GERENTE_SOCIAL("10"),
	ADMINISTRADOR_AGENCIA("5");
	
	
	
	private String descripcion;

    private ResponsableEnum(final String descripcion) {
	this.descripcion = descripcion;
    }

    public String getDescripcion() {
    	
	return descripcion;
    }

}
