package com.primax.jpa.param;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.SequenceGenerator;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "NIVEL_ESFUERZO_ET")
@Audited

@NamedStoredProcedureQuery(name = "getLimpiarReporteProcesoCualitativo", procedureName = "fun_limpiar_rpt_proceso_cualitativo", resultClasses = ProcesoDetalleEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class NivelEsfuerzoEt extends EntityBase implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "sec_nivel_esfuerzo_et", sequenceName = "seq_nivel_esfuerzo_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_nivel_esfuerzo_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_nivel_esfuerzo")
	private Long idNivelEsfuerzo;

	@Column(name = "orden")
	private Long orden;

	@Column(name = "puntaje")
	private Long puntaje;

	@Column(name = "descripcion", length = 20)
	private String descripcion;

	public NivelEsfuerzoEt() {
		this.orden = 1L;
		this.puntaje = 1L;
		this.descripcion = "";
	}

	public Long getIdNivelEsfuerzo() {
		return idNivelEsfuerzo;
	}

	public void setIdNivelEsfuerzo(Long idNivelEsfuerzo) {
		this.idNivelEsfuerzo = idNivelEsfuerzo;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(Long puntaje) {
		this.puntaje = puntaje;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NivelEsfuerzoEt) {
			NivelEsfuerzoEt other = (NivelEsfuerzoEt) obj;

			if (this.idNivelEsfuerzo == null)
				return this == other;

			return this.idNivelEsfuerzo.equals(other.idNivelEsfuerzo) ? true : false;
		} else
			return false;
	}

}
