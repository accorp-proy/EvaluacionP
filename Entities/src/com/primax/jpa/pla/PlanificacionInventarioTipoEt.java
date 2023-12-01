package com.primax.jpa.pla;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.param.TipoInventarioEt;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "PLANIFICACION_INVENTARIO_TIPO_ET")
@Audited
public class PlanificacionInventarioTipoEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_planificacion_inventario_tipo_et", sequenceName = "seq_planificacion_inventario_tipo_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_planificacion_inventario_tipo_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_planificacion_inventario_tipo")
	private Long idPlanificacionInventarioTipo;

	@ManyToOne
	@JoinColumn(name = "id_planificacion_inventario")
	private PlanificacionInventarioEt planificacionInventario;

	@ManyToOne
	@JoinColumn(name = "id_tipo_inventario")
	private TipoInventarioEt tipoInventario;

	@Column(name = "ejecutado")
	private boolean ejecutado;

	public PlanificacionInventarioTipoEt() {
		this.ejecutado = false;
	}

	public TipoInventarioEt getTipoInventario() {
		return tipoInventario;
	}

	public void setTipoInventario(TipoInventarioEt tipoInventario) {
		this.tipoInventario = tipoInventario;
	}

	

	public Long getIdPlanificacionInventarioTipo() {
		return idPlanificacionInventarioTipo;
	}

	public void setIdPlanificacionInventarioTipo(Long idPlanificacionInventarioTipo) {
		this.idPlanificacionInventarioTipo = idPlanificacionInventarioTipo;
	}

	public boolean isEjecutado() {
		return ejecutado;
	}

	public void setEjecutado(boolean ejecutado) {
		this.ejecutado = ejecutado;
	}

	public PlanificacionInventarioEt getPlanificacionInventario() {
		return planificacionInventario;
	}

	public void setPlanificacionInventario(PlanificacionInventarioEt planificacionInventario) {
		this.planificacionInventario = planificacionInventario;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PlanificacionInventarioTipoEt) {
			PlanificacionInventarioTipoEt other = (PlanificacionInventarioTipoEt) obj;

			if (this.idPlanificacionInventarioTipo == null)
				return this == other;

			if (this.idPlanificacionInventarioTipo.equals(other.idPlanificacionInventarioTipo))
				return true;
		}
		return false;

	}

}
