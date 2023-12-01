package com.primax.jpa.pla;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "PLANIFICACION_ET")
@Audited
public class PlanificacionEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_planificacion_et", sequenceName = "seq_planificacion_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_planificacion_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_planificacion")
	private Long idPlanificacion;

	@Column(name = "fecha_planificacion")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaPlanificacion;

	@Column(name = "fecha_inicio")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaInicio;

	@Column(name = "fecha_fin")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaFin;

	@Column(name = "notificacion")
	private boolean notificacion;

	@Column(name = "inventario")
	private boolean inventario;

	@Transient
	private boolean eliminada;

	@ManyToOne
	@JoinColumn(name = "id_agencia")
	private AgenciaEt agencia;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "planificacion", fetch = FetchType.LAZY)
	@OrderBy("orden asc")
	@Where(clause = "estado = 'ACT'")
	private List<CheckListEjecucionEt> checkListEjecucion;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "planificacion", fetch = FetchType.LAZY)
	@OrderBy("idPlanificacionAuditor asc")
	@Where(clause = "estado = 'ACT'")
	private List<PlanificacionAuditorEt> planificacionAuditor;

	public PlanificacionEt() {
		this.eliminada = false;
		this.inventario = false;
		this.notificacion = false;
	}

	public Long getIdPlanificacion() {
		return idPlanificacion;
	}

	public void setIdPlanificacion(Long idPlanificacion) {
		this.idPlanificacion = idPlanificacion;
	}

	public Date getFechaPlanificacion() {
		return fechaPlanificacion;
	}

	public void setFechaPlanificacion(Date fechaPlanificacion) {
		this.fechaPlanificacion = fechaPlanificacion;
	}

	public boolean isNotificacion() {
		return notificacion;
	}

	public void setNotificacion(boolean notificacion) {
		this.notificacion = notificacion;
	}

	public List<CheckListEjecucionEt> getCheckListEjecucion() {
		return checkListEjecucion;
	}

	public void setCheckListEjecucion(List<CheckListEjecucionEt> checkListEjecucion) {
		this.checkListEjecucion = checkListEjecucion;
	}

	public AgenciaEt getAgencia() {
		return agencia;
	}

	public void setAgencia(AgenciaEt agencia) {
		this.agencia = agencia;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public boolean isInventario() {
		return inventario;
	}

	public void setInventario(boolean inventario) {
		this.inventario = inventario;
	}

	public List<PlanificacionAuditorEt> getPlanificacionAuditor() {
		return planificacionAuditor;
	}

	public void setPlanificacionAuditor(List<PlanificacionAuditorEt> planificacionAuditor) {
		this.planificacionAuditor = planificacionAuditor;
	}

	public boolean isEliminada() {
		return eliminada;
	}

	public void setEliminada(boolean eliminada) {
		this.eliminada = eliminada;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PlanificacionEt) {
			PlanificacionEt other = (PlanificacionEt) obj;

			if (this.idPlanificacion == null)
				return this == other;

			if (this.idPlanificacion.equals(other.idPlanificacion))
				return true;
		}
		return false;

	}

}
