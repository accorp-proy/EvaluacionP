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
@Table(name = "PLAN_ACCION_CHECK_LIST_ET")

@NamedStoredProcedureQuery(name = "getGenerarOrgPlnCheckList", procedureName = "fun_generar_org_pln_check", resultClasses = PlanAccionChekListEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "anio"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idPlanAccionAnio"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "mes"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idZona"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class PlanAccionChekListEt extends EntityBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "sec_plan_accion_check_list_et", sequenceName = "seq_plan_accion_check_list_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_plan_accion_check_list_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_plan_accion_check_list")
	private Long idPlanAccionCheckList;

	@Column(name = "id_plan_accion_anio")
	private Long idPlanAccionAnio;

	@Column(name = "id_check_list_ejecucion")
	private Long idCheckListEjecucion;

	@Column(name = "descripcion", length = 300)
	private String descripcion;

	@Column(name = "id_zona")
	private Long idZona;

	@Column(name = "anio")
	private Long anio;

	@Column(name = "mes")
	private Long mes;

	@Column(name = "id_agencia")
	private Long idAgencia;

	public PlanAccionChekListEt() {
		this.descripcion = "";
		this.idPlanAccionAnio = 0L;
		this.idCheckListEjecucion = 0L;
	}

	public Long getIdPlanAccionCheckList() {
		return idPlanAccionCheckList;
	}

	public void setIdPlanAccionCheckList(Long idPlanAccionCheckList) {
		this.idPlanAccionCheckList = idPlanAccionCheckList;
	}

	public Long getIdCheckListEjecucion() {
		return idCheckListEjecucion;
	}

	public void setIdCheckListEjecucion(Long idCheckListEjecucion) {
		this.idCheckListEjecucion = idCheckListEjecucion;
	}

	public Long getIdPlanAccionAnio() {
		return idPlanAccionAnio;
	}

	public void setIdPlanAccionAnio(Long idPlanAccionAnio) {
		this.idPlanAccionAnio = idPlanAccionAnio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

		if (obj instanceof PlanAccionChekListEt) {

			PlanAccionChekListEt other = (PlanAccionChekListEt) obj;
			if (this.idPlanAccionCheckList == null)
				return this == other;

			return this.idPlanAccionCheckList.equals(other.idPlanAccionCheckList);
		}
		return false;
	}

}
