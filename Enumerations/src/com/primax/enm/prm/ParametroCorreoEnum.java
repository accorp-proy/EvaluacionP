package com.primax.enm.prm;

public enum ParametroCorreoEnum {

	TLS("mail.smtp.starttls.enable", "mail.smtps.starttls.enable", "TLS"),
	AUTH("mail.smtp.auth", "mail.smtps.auth", "UTILIZA AUTENTICACION"),
	TRUSTTLS("mail.smtp.ssl.trust", "mail.smtps.ssl.trust", "CONFIAR DESTINOS SSL"),
	TIMEOUT("mail.smtp.timeout", "mail.smtps.timeout", "TIMEOUT");

	private String smtp;
	private String smtps;
	private String descripcion;

	ParametroCorreoEnum(String parametro, String parametro_2, String descripcion) {
		this.smtp = parametro;
		this.smtps = parametro_2;
		this.descripcion = descripcion;
	}

	public String getSmtp() {
		return smtp;
	}

	public String getSmtps() {
		return smtps;
	}

	public String getDescripcion() {
		return descripcion;
	}

}
