package com.primax.util.cons;

public enum StaticParameter {

	Regex_Digit("(?=.*\\d)"),
	Regex_Upper("(?=.*[A-Z])"),
	Regex_Lower("(?=.*[a-z])"),
	Regex_Symbol("(?=.*[@#$%*&/\\?¿])"),
	Regex_Any(".+");

	private String id;

	private StaticParameter(String value) {
		this.id = value;
	}

	public String getDescription() {
		return id;
	}

}
