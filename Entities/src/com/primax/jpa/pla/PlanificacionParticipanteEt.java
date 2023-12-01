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
@Table(name = "PLANIFICACION_PARTICIPANTE_ET")
@Audited
public class PlanificacionParticipanteEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_planificacion_participante_et", sequenceName = "seq_planificacion_participante_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_planificacion_participante_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_planificacion_participante")
	private Long idPlanificacionParticipante;

	@ManyToOne
	@JoinColumn(name = "id_planificacion_inventario")
	private PlanificacionInventarioEt planificacionInventario;

	@ManyToOne
	@JoinColumn(name = "id_usuario_participante")
	private UsuarioEt usuarioParticipante;

	public UsuarioEt getUsuarioParticipante() {
		return usuarioParticipante;
	}

	public void setUsuarioParticipante(UsuarioEt usuarioParticipante) {
		this.usuarioParticipante = usuarioParticipante;
	}

	public PlanificacionInventarioEt getPlanificacionInventario() {
		return planificacionInventario;
	}

	public void setPlanificacionInventario(PlanificacionInventarioEt planificacionInventario) {
		this.planificacionInventario = planificacionInventario;
	}

	public Long getIdPlanificacionParticipante() {
		return idPlanificacionParticipante;
	}

	public void setIdPlanificacionParticipante(Long idPlanificacionParticipante) {
		this.idPlanificacionParticipante = idPlanificacionParticipante;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PlanificacionParticipanteEt) {
			PlanificacionParticipanteEt other = (PlanificacionParticipanteEt) obj;

			if (this.idPlanificacionParticipante == null)
				return this == other;

			if (this.idPlanificacionParticipante.equals(other.idPlanificacionParticipante))
				return true;
		}
		return false;

	}

}
