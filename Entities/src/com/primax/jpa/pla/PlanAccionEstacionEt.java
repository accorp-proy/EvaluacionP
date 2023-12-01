package com.primax.jpa.pla;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "PLAN_ACCION_ESTACION_ET")
public class PlanAccionEstacionEt extends EntityBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "sec_plan_accion_estacion_et", sequenceName = "seq_plan_accion_estacion_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_plan_accion_estacion_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_plan_accion_estacion")
	private Long idPlanAccionEstacion;

	@Column(name = "id_plan_accion_anio")
	private Long idPlanAccionAnio;

	@Column(name = "agencia", length = 50)
	private String agencia;

	@Column(name = "id_zona")
	private Long idZona;

	@Column(name = "id_agencia")
	private Long idAgencia;

	@Column(name = "anio")
	private Long anio;

	@Column(name = "mes")
	private Long mes;

	public PlanAccionEstacionEt() {
		this.agencia = "";
		this.idAgencia = 0L;
		this.idPlanAccionAnio = 0L;
	}

	public Long getIdPlanAccionAnio() {
		return idPlanAccionAnio;
	}

	public void setIdPlanAccionAnio(Long idPlanAccionAnio) {
		this.idPlanAccionAnio = idPlanAccionAnio;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public Long getIdZona() {
		return idZona;
	}

	public void setIdZona(Long idZona) {
		this.idZona = idZona;
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

	public Long getIdPlanAccionEstacion() {
		return idPlanAccionEstacion;
	}

	public void setIdPlanAccionEstacion(Long idPlanAccionEstacion) {
		this.idPlanAccionEstacion = idPlanAccionEstacion;
	}

	public Long getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(Long idAgencia) {
		this.idAgencia = idAgencia;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof PlanAccionEstacionEt) {

			PlanAccionEstacionEt other = (PlanAccionEstacionEt) obj;
			if (this.idPlanAccionEstacion == null)
				return this == other;

			return this.idPlanAccionEstacion.equals(other.idPlanAccionEstacion);
		}
		return false;
	}

}
