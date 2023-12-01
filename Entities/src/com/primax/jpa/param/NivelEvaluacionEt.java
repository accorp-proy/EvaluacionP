package com.primax.jpa.param;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.ParameterMode;
import javax.persistence.SequenceGenerator;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "NIVEL_EVALUACION_ET")
@Audited

@NamedStoredProcedureQuery(name = "getLimpiarReporteProcesoConsolidado", procedureName = "fun_limpiar_rpt_proceso_consolidado", resultClasses = ProcesoDetalleEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class NivelEvaluacionEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -4838501690442140136L;

	@Id
	@SequenceGenerator(name = "sec_nivel_evaluacion_et", sequenceName = "seq_nivel_evaluacion_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_nivel_evaluacion_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_nivel_evaluacion")
	private Long idNivelEvaluacion;

	@Column(name = "orden")
	private Long orden;

	@Column(name = "descripcion", length = 80)
	private String descripcion;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "nivelEvaluacion", fetch = FetchType.LAZY)
	@OrderBy("idNivelEvaluacionDetalle ")
	@Where(clause = "estado = 'ACT'")
	private List<NivelEvaluacionDetalleEt> nivelEvaluacionDetalle;

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public List<NivelEvaluacionDetalleEt> getNivelEvaluacionDetalle() {
		return nivelEvaluacionDetalle;
	}

	public void setNivelEvaluacionDetalle(List<NivelEvaluacionDetalleEt> nivelEvaluacionDetalle) {
		this.nivelEvaluacionDetalle = nivelEvaluacionDetalle;
	}

	public Long getIdNivelEvaluacion() {
		return idNivelEvaluacion;
	}

	public void setIdNivelEvaluacion(Long idNivelEvaluacion) {
		this.idNivelEvaluacion = idNivelEvaluacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof NivelEvaluacionEt) {

			NivelEvaluacionEt other = (NivelEvaluacionEt) obj;
			if (this.idNivelEvaluacion == null)
				return this == other;

			return this.idNivelEvaluacion.equals(other.idNivelEvaluacion);
		}
		return false;
	}

}
