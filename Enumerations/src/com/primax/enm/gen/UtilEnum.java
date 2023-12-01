package com.primax.enm.gen;

import java.util.Date;

public enum UtilEnum {

	FECHA_REGISTRO(1);

	private int id;

	private UtilEnum(int enumd) {
		this.id = enumd;
	}

	@SuppressWarnings("unchecked")
	public <T> T getValue() {
		switch (id) {
		case 1:
			return (T) new Date();
		default:
			return null;
		}
	}

}
