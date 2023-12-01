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

import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "SEGUIMIENTO_PLAN_ACCION_ET")
@Audited

@NamedStoredProcedureQuery(name = "getGenerarSeguimientoPlanAccion", procedureName = "fun_generar_seguimiento_plan_accion", resultClasses = ReporteTipoEvaluacionEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idCheck0"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idCheck1"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class SeguimientoPlanAccionEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_seguimiento_plan_accion_et", sequenceName = "seq_seguimiento_plan_accion_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_seguimiento_plan_accion_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_seguimiento_plan_accion")
	private Long idSeguimientoPlanAccion;

	@Column(name = "orden_kpi")
	private Long ordenKpi;

	@Column(name = "descripcion_kpi", length = 1000)
	private String descripcionKpi;

	@Column(name = "orden_proceso")
	private Long ordenProceso;

	@Column(name = "descripcion_proceso", length = 50)
	private String descripcionProceso;

	@Column(name = "condicion")
	private Long condicion;

	@Column(name = "porcentaje_cumplimiento")
	private Long porcentajeCumplimiento;

	@Column(name = "id_check_list_ejecucion_0")
	private Long idCheckListEjecucion0;

	@Column(name = "id_check_list_ejecucion_1")
	private Long idCheckListEjecucion1;

	@Column(name = "comentario_plan_accion", length = 500)
	private String comentarioPlanAccion;

	@Column(name = "criterio_evaluacion", length = 100)
	private String criterioEvaluacion;

	public SeguimientoPlanAccionEt() {
		this.condicion = 0L;
		this.comentarioPlanAccion = "";
	}

	public String getDescripcionProceso() {
		return descripcionProceso;
	}

	public void setDescripcionProceso(String descripcionProceso) {
		this.descripcionProceso = descripcionProceso;
	}

	public String getDescripcionKpi() {
		return descripcionKpi;
	}

	public void setDescripcionKpi(String descripcionKpi) {
		this.descripcionKpi = descripcionKpi;
	}

	public Long getOrdenProceso() {
		return ordenProceso;
	}

	public void setOrdenProceso(Long ordenProceso) {
		this.ordenProceso = ordenProceso;
	}

	public String getCriterioEvaluacion() {
		return criterioEvaluacion;
	}

	public void setCriterioEvaluacion(String criterioEvaluacion) {
		this.criterioEvaluacion = criterioEvaluacion;
	}

	public String getComentarioPlanAccion() {
		return comentarioPlanAccion;
	}

	public void setComentarioPlanAccion(String comentarioPlanAccion) {
		this.comentarioPlanAccion = comentarioPlanAccion;
	}

	public Long getIdSeguimientoPlanAccion() {
		return idSeguimientoPlanAccion;
	}

	public void setIdSeguimientoPlanAccion(Long idSeguimientoPlanAccion) {
		this.idSeguimientoPlanAccion = idSeguimientoPlanAccion;
	}

	public Long getIdCheckListEjecucion0() {
		return idCheckListEjecucion0;
	}

	public void setIdCheckListEjecucion0(Long idCheckListEjecucion0) {
		this.idCheckListEjecucion0 = idCheckListEjecucion0;
	}

	public Long getIdCheckListEjecucion1() {
		return idCheckListEjecucion1;
	}

	public void setIdCheckListEjecucion1(Long idCheckListEjecucion1) {
		this.idCheckListEjecucion1 = idCheckListEjecucion1;
	}

	public Long getCondicion() {
		return condicion;
	}

	public void setCondicion(Long condicion) {
		this.condicion = condicion;
	}

	public Long getOrdenKpi() {
		return ordenKpi;
	}

	public void setOrdenKpi(Long ordenKpi) {
		this.ordenKpi = ordenKpi;
	}

	public Long getPorcentajeCumplimiento() {
		return porcentajeCumplimiento;
	}

	public void setPorcentajeCumplimiento(Long porcentajeCumplimiento) {
		this.porcentajeCumplimiento = porcentajeCumplimiento;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SeguimientoPlanAccionEt) {
			SeguimientoPlanAccionEt other = (SeguimientoPlanAccionEt) obj;
			if (this.idSeguimientoPlanAccion == null)
				return this == other;

			if (this.idSeguimientoPlanAccion.equals(other.idSeguimientoPlanAccion))
				return true;
		}
		return false;

	}

}
