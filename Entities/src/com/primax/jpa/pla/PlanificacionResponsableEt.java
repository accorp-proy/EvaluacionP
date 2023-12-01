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
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "PLANIFICACION_RESPONSABLE_ET")
@Audited
public class PlanificacionResponsableEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_planificacion_responsable_et", sequenceName = "seq_planificacion_responsable_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_planificacion_responsable_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_planificacion_responsable")
	private Long idPlanificacionResponsable;

	@ManyToOne
	@JoinColumn(name = "id_planificacion_inventario")
	private PlanificacionInventarioEt planificacionInventario;

	@ManyToOne
	@JoinColumn(name = "id_usuario_responsable")
	private UsuarioEt usuarioResponsable;

	public Long getIdPlanificacionResponsable() {
		return idPlanificacionResponsable;
	}

	public void setIdPlanificacionResponsable(Long idPlanificacionResponsable) {
		this.idPlanificacionResponsable = idPlanificacionResponsable;
	}

	public PlanificacionInventarioEt getPlanificacionInventario() {
		return planificacionInventario;
	}

	public void setPlanificacionInventario(PlanificacionInventarioEt planificacionInventario) {
		this.planificacionInventario = planificacionInventario;
	}

	public UsuarioEt getUsuarioResponsable() {
		return usuarioResponsable;
	}

	public void setUsuarioResponsable(UsuarioEt usuarioResponsable) {
		this.usuarioResponsable = usuarioResponsable;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PlanificacionResponsableEt) {
			PlanificacionResponsableEt other = (PlanificacionResponsableEt) obj;

			if (this.idPlanificacionResponsable == null)
				return this == other;

			if (this.idPlanificacionResponsable.equals(other.idPlanificacionResponsable))
				return true;
		}
		return false;

	}

}
