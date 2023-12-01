package com.primax.enm.pcs;

public enum LugarEnum {

	PIS("pista"), TIE("tienda");

	private String dsc;

	LugarEnum(String dsc) {
		this.dsc = dsc;
	}

	public String getDescripcion() {
		return this.dsc;
	}

}
