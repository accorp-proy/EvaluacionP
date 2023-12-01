package com.primax.enm.gen;

public enum DBTypeLib {

	PRIMAX("ecprpdloc"), ATIMASA("ecatpdloc");

	private String descripcion;

	DBTypeLib(String desc) {
		this.descripcion = desc;
	}

	public String getDescripcion() {
		return descripcion;
	}

}
