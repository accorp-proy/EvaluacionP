package com.primax.jpa.pla;

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

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "PLAN_ACCION_ZONA_ET")

@NamedStoredProcedureQuery(name = "getGenerarOrgPlnZona", procedureName = "fun_generar_org_pln_zona", resultClasses = PlanAccionZonaEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "anio"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idPlanAccionAnio"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "mes"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class PlanAccionZonaEt extends EntityBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "sec_plan_accion_zona_et", sequenceName = "seq_plan_accion_zona_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_plan_accion_zona_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_plan_accion_zona")
	private Long idPlanAccionZona;

	@Column(name = "id_plan_accion_anio")
	private Long idPlanAccionAnio;

	@Column(name = "id_zona")
	private Long idZona;

	@Column(name = "zona", length = 20)
	private String zona;

	@Column(name = "anio")
	private Long anio;

	@Column(name = "mes")
	private Long mes;

	public PlanAccionZonaEt() {
		this.mes = 0L;
		this.zona = "";
		this.anio = 0L;
		this.idZona = 0L;
		this.idPlanAccionAnio = 0L;
	}

	public Long getIdPlanAccionZona() {
		return idPlanAccionZona;
	}

	public void setIdPlanAccionZona(Long idPlanAccionZona) {
		this.idPlanAccionZona = idPlanAccionZona;
	}

	public Long getIdZona() {
		return idZona;
	}

	public void setIdZona(Long idZona) {
		this.idZona = idZona;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public Long getIdPlanAccionAnio() {
		return idPlanAccionAnio;
	}

	public void setIdPlanAccionAnio(Long idPlanAccionAnio) {
		this.idPlanAccionAnio = idPlanAccionAnio;
	}

	public Long getAnio() {
		return anio;
	}

	public void setAnio(Long anio) {
		this.anio = anio;
	}

	public Long getMes() {
		return mes;
	}

	public void setMes(Long mes) {
		this.mes = mes;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof PlanAccionZonaEt) {

			PlanAccionZonaEt other = (PlanAccionZonaEt) obj;
			if (this.idPlanAccionZona == null)
				return this == other;

			return this.idPlanAccionZona.equals(other.idPlanAccionZona);
		}
		return false;
	}

}
