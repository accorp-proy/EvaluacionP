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
@Table(name = "EVALUACION_ET")
@Audited


@NamedStoredProcedureQuery(name = "getReporteEvaluacionConsolidado", procedureName = "fun_limpiar_rpt_evaluacion_consolidado", resultClasses = ProcesoDetalleEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class EvaluacionEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -4838501690442140136L;

	@Id
	@SequenceGenerator(name = "sec_evaluacion_et", sequenceName = "seq_evaluacion_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_evaluacion_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_evaluacion")
	private Long idEvaluacion;

	@Column(name = "descripcion", length = 50)
	private String descripcion;

	@Column(name = "acronimo", length = 10)
	private String acronimo;

	/**
	 * Criterio General true Criterio Especifico false
	 * 
	 */
	@Column(name = "criterio")
	private boolean criterio;

	public EvaluacionEt() {
		this.criterio = true;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getIdEvaluacion() {
		return idEvaluacion;
	}

	public void setIdEvaluacion(Long idEvaluacion) {
		this.idEvaluacion = idEvaluacion;
	}

	public String getAcronimo() {
		return acronimo;
	}

	public void setAcronimo(String acronimo) {
		this.acronimo = acronimo;
	}

	public boolean isCriterio() {
		return criterio;
	}

	public void setCriterio(boolean criterio) {
		this.criterio = criterio;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof EvaluacionEt) {

			EvaluacionEt other = (EvaluacionEt) obj;
			if (this.idEvaluacion == null)
				return this == other;

			return this.idEvaluacion.equals(other.idEvaluacion);
		}
		return false;
	}

}
