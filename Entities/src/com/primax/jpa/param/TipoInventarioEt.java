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
@Table(name = "TIPO_INVENTARIO_ET")
@Audited

@NamedStoredProcedureQuery(name = "getReporteEvaluacionPlanificacionInv", procedureName = "fun_limpiar_rpt_planificacion_inventario", resultClasses = ProcesoDetalleEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class TipoInventarioEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -4838501690442140136L;

	@Id
	@SequenceGenerator(name = "sec_tipo_inventario_et", sequenceName = "seq_tipo_inventario_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_tipo_inventario_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_tipo_inventario")
	private Long idTipoInventario;

	@Column(name = "codigo", length = 10)
	private String codigo;

	@Column(name = "descripcion", length = 100)
	@OrderBy("ORDER BY descripcion")
	private String descripcion;

	public TipoInventarioEt() {
		this.codigo = "";
		this.descripcion = "";
	}

	public Long getIdTipoInventario() {
		return idTipoInventario;
	}

	public void setIdTipoInventario(Long idTipoInventario) {
		this.idTipoInventario = idTipoInventario;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof TipoInventarioEt) {

			TipoInventarioEt other = (TipoInventarioEt) obj;
			if (this.idTipoInventario == null)
				return this == other;

			return this.idTipoInventario.equals(other.idTipoInventario);
		}
		return false;
	}

}
