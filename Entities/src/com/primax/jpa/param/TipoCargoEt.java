package com.primax.jpa.param;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OrderBy;
import javax.persistence.ParameterMode;
import javax.persistence.SequenceGenerator;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "TIPO_CARGO_ET")
@Audited

@NamedStoredProcedureQuery(name = "getReporteEvaluacionPlanificacion", procedureName = "fun_limpiar_rpt_evaluacion_planificacion", resultClasses = ProcesoDetalleEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class TipoCargoEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -4838501690442140136L;

	@Id
	@SequenceGenerator(name = "sec_tipo_cargo_et", sequenceName = "seq_tipo_cargo_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_tipo_cargo_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_tipo_cargo")
	private Long idTipoCargo;

	@Column(name = "descripcion", length = 100)
	@OrderBy("ORDER BY descripcion")
	private String descripcion;

	@Column(name = "firma", length = 100)
	private String firma;

	public Long getIdTipoCargo() {
		return idTipoCargo;
	}

	public void setIdTipoCargo(Long idTipoCargo) {
		this.idTipoCargo = idTipoCargo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof TipoCargoEt) {

			TipoCargoEt other = (TipoCargoEt) obj;
			if (this.idTipoCargo == null)
				return this == other;

			return this.idTipoCargo.equals(other.idTipoCargo);
		}
		return false;
	}

}
