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
@Table(name = "PLAN_ACCION_MES_ET")

@NamedStoredProcedureQuery(name = "getGenerarOrgPlnMes", procedureName = "fun_generar_org_pln_mes", resultClasses = PlanAccionMesEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "anio"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idPlanAccionAnio"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class PlanAccionMesEt extends EntityBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "sec_plan_accion_mes_et", sequenceName = "seq_plan_accion_mes_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_plan_accion_mes_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_plan_accion_mes")
	private Long idPlanAccionMes;

	@Column(name = "id_plan_accion_anio")
	private Long idPlanAccionAnio;

	@Column(name = "mes_numero")
	private Long mesNumero;

	@Column(name = "mes_letra", length = 20)
	private String mesLetra;

	@Column(name = "anio")
	private Long anio;

	public PlanAccionMesEt() {
		this.anio = 0L;
		this.mesLetra = "";
		this.mesNumero = 0L;
		this.idPlanAccionAnio = 0L;
	}

	public Long getIdPlanAccionMes() {
		return idPlanAccionMes;
	}

	public void setIdPlanAccionMes(Long idPlanAccionMes) {
		this.idPlanAccionMes = idPlanAccionMes;
	}

	public Long getMesNumero() {
		return mesNumero;
	}

	public void setMesNumero(Long mesNumero) {
		this.mesNumero = mesNumero;
	}

	public String getMesLetra() {
		return mesLetra;
	}

	public void setMesLetra(String mesLetra) {
		this.mesLetra = mesLetra;
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

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof PlanAccionMesEt) {

			PlanAccionMesEt other = (PlanAccionMesEt) obj;
			if (this.idPlanAccionMes == null)
				return this == other;

			return this.idPlanAccionMes.equals(other.idPlanAccionMes);
		}
		return false;
	}

}
