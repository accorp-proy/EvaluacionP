package com.primax.jpa.gen;

import java.io.Serializable;

public class PlanAccionOrganizacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id0;
	private Long id1;
	private String nombre;
	private String tipo;

	public PlanAccionOrganizacion(Long id0, Long id1, String nombreP, String tipoP) {
		this.id0 = id0;
		this.id1 = id1;
		this.tipo = tipoP;
		this.nombre = nombreP;

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getId0() {
		return id0;
	}

	public void setId0(Long id0) {
		this.id0 = id0;
	}

	public Long getId1() {
		return id1;
	}

	public void setId1(Long id1) {
		this.id1 = id1;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
