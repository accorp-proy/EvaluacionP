package com.primax.jpa.pla;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "PLAN_ACCION_ANIO_ET")

@NamedStoredProcedureQuery(name = "getGenerarOrgPlnAnio", procedureName = "fun_generar_org_pln_anio", resultClasses = PlanAccionAnioEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idAnio"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "fechaDesde"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "fechaHasta"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idZona"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idEstacion"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idEvaluacion"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class PlanAccionAnioEt extends EntityBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "sec_plan_accion_anio_et", sequenceName = "seq_plan_accion_anio_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_plan_accion_anio_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_plan_accion_anio")
	private Long idPlanAccionAnio;

	@Column(name = "anio")
	private Long anio;

	@Column(name = "fecha_ejecucion")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaEjecucion;

	public PlanAccionAnioEt() {
		this.anio = 0L;
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

	public Date getFechaEjecucion() {
		return fechaEjecucion;
	}

	public void setFechaEjecucion(Date fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof PlanAccionAnioEt) {

			PlanAccionAnioEt other = (PlanAccionAnioEt) obj;
			if (this.idPlanAccionAnio == null)
				return this == other;

			return this.idPlanAccionAnio.equals(other.idPlanAccionAnio);
		}
		return false;
	}

}
