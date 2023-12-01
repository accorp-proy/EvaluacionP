package com.primax.jpa.sec;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;

import com.primax.jpa.base.EntityBase;

@Entity
@Table(name = "politica_seguridad_et")
@Audited
public class PoliticaSeguridadEt extends EntityBase implements Serializable {

	public PoliticaSeguridadEt() {

	}

	public PoliticaSeguridadEt(PoliticaSeguridadEt old) {
		this.estado = old.estado;
		intentosSesion = old.intentosSesion;
		longitudMaxContrasena = old.longitudMaxContrasena;
		longitudMinContrasena = old.longitudMinContrasena;
		longitudNombreUsuario = old.longitudNombreUsuario;
		mayusculas = old.mayusculas;
		minusculas = old.minusculas;
		numeros = old.numeros;
		recordatorioContrasena = old.recordatorioContrasena;
		renovacionContrasena = old.renovacionContrasena;
		periodoRenovacionContrasena = old.periodoRenovacionContrasena;
		simbolos = old.simbolos;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6064737917708001761L;

	@Id
	@Column(name = "id_politica_seguridad")
	@GeneratedValue(generator = "sec_politica_seguridad", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "sec_politica_seguridad", allocationSize = 1, initialValue = 1, sequenceName = "seq_politica_seguridad")
	private Long idPoliticaSeguridad;

	@Column(name = "longitud_nombre_usuario")
	private Long longitudNombreUsuario;

	@Column(name = "intentos_sesion")
	private Long intentosSesion;

	@Column(name = "longitud_min_contrasena")
	private Long longitudMinContrasena;

	@Column(name = "longitud_max_contrasena")
	private Long longitudMaxContrasena;

	@Column(name = "recordatorio_contrasena")
	private Long recordatorioContrasena;

	@Column(name = "periodo_renovacion_contrasena")
	private Long periodoRenovacionContrasena;

	@Column(name = "renovacion_contrasena")
	@Temporal(TemporalType.DATE)
	private Date renovacionContrasena;

	@Column(name = "simbolos")
	private Boolean simbolos;

	@Column(name = "numeros")
	private Boolean numeros;

	@Column(name = "minusculas")
	private Boolean minusculas;

	@Column(name = "mayusculas")
	private Boolean mayusculas;

	public Long getIdPoliticaSeguridad() {
		return idPoliticaSeguridad;
	}

	public void setIdPoliticaSeguridad(Long idPoliticaSeguridad) {
		this.idPoliticaSeguridad = idPoliticaSeguridad;
	}

	public Long getLongitudNombreUsuario() {
		return longitudNombreUsuario;
	}

	public void setLongitudNombreUsuario(Long longitudNombreUsuario) {
		this.longitudNombreUsuario = longitudNombreUsuario;
	}

	public Long getIntentosSesion() {
		return intentosSesion;
	}

	public void setIntentosSesion(Long intentosSesion) {
		this.intentosSesion = intentosSesion;
	}

	public Long getLongitudMinContrasena() {
		return longitudMinContrasena;
	}

	public void setLongitudMinContrasena(Long longitudMinContrasena) {
		this.longitudMinContrasena = longitudMinContrasena;
	}

	public Long getLongitudMaxContrasena() {
		return longitudMaxContrasena;
	}

	public void setLongitudMaxContrasena(Long longitudMaxContrasena) {
		this.longitudMaxContrasena = longitudMaxContrasena;
	}

	public Long getRecordatorioContrasena() {
		return recordatorioContrasena;
	}

	public void setRecordatorioContrasena(Long recordatorioContrasena) {
		this.recordatorioContrasena = recordatorioContrasena;
	}

	public Date getRenovacionContrasena() {
		return renovacionContrasena;
	}

	public void setRenovacionContrasena(Date renovacionContrasena) {
		this.renovacionContrasena = renovacionContrasena;
	}

	public Boolean getSimbolos() {
		return simbolos;
	}

	public void setSimbolos(Boolean simbolos) {
		this.simbolos = simbolos;
	}

	public Boolean getNumeros() {
		return numeros;
	}

	public void setNumeros(Boolean numeros) {
		this.numeros = numeros;
	}

	public Boolean getMinusculas() {
		return minusculas;
	}

	public void setMinusculas(Boolean minusculas) {
		this.minusculas = minusculas;
	}

	public Boolean getMayusculas() {
		return mayusculas;
	}

	public void setMayusculas(Boolean mayusculas) {
		this.mayusculas = mayusculas;
	}

	public Long getPeriodoRenovacionContrasena() {
		return periodoRenovacionContrasena;
	}

	public void setPeriodoRenovacionContrasena(Long periodoRenovacionContrasena) {
		this.periodoRenovacionContrasena = periodoRenovacionContrasena;
	}

	@Override
	public boolean equals(Object obj) {
		PoliticaSeguridadEt other = (PoliticaSeguridadEt) obj;
		if (other == null)
			return false;

		if (this.idPoliticaSeguridad.equals(other.idPoliticaSeguridad))
			return true;

		return false;
	}

}
