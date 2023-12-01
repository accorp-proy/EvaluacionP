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
@Table(name = "PLANIFICACION_AUDITOR_ET")
@Audited
public class PlanificacionAuditorEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_planificacion_auditor_et", sequenceName = "seq_planificacion_auditor_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_planificacion_auditor_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_planificacion_auditor")
	private Long idPlanificacionAuditor;

	@ManyToOne
	@JoinColumn(name = "id_planificacion")
	private PlanificacionEt planificacion;

	@ManyToOne
	@JoinColumn(name = "id_usuario_auditor")
	private UsuarioEt usuarioAuditor;

	public Long getIdPlanificacionAuditor() {
		return idPlanificacionAuditor;
	}

	public void setIdPlanificacionAuditor(Long idPlanificacionAuditor) {
		this.idPlanificacionAuditor = idPlanificacionAuditor;
	}

	public PlanificacionEt getPlanificacion() {
		return planificacion;
	}

	public void setPlanificacion(PlanificacionEt planificacion) {
		this.planificacion = planificacion;
	}

	public UsuarioEt getUsuarioAuditor() {
		return usuarioAuditor;
	}

	public void setUsuarioAuditor(UsuarioEt usuarioAuditor) {
		this.usuarioAuditor = usuarioAuditor;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PlanificacionAuditorEt) {
			PlanificacionAuditorEt other = (PlanificacionAuditorEt) obj;

			if (this.idPlanificacionAuditor == null)
				return this == other;

			if (this.idPlanificacionAuditor.equals(other.idPlanificacionAuditor))
				return true;
		}
		return false;

	}

}
