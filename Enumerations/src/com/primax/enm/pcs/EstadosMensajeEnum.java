package com.primax.enm.pcs;

public enum EstadosMensajeEnum {

	ENC("ENCOLADO", 1), REC("RECIBIDO", 2), BNC("BOUNCED", 3), ERR("ERROR", 4), ENV("ENVIADO", 5);

	private String label;

	private int value;

	private EstadosMensajeEnum(String label, int value) {
		this.label = label;
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public int getValue() {
		return value;
	}

}
