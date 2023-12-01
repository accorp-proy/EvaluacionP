package com.primax.jpa.pla;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import com.primax.jpa.enums.EstadoCheckListEnum;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.ZonaEt;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "PLANIFICACION_INVENTARIO_ET")
@Audited
public class PlanificacionInventarioEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_planificacion_inventario_et", sequenceName = "seq_planificacion_inventario_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_planificacion_inventario_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_planificacion_inventario")
	private Long idPlanificacionInventario;

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

	@ManyToOne
	@JoinColumn(name = "id_agencia")
	private AgenciaEt agencia;

	@ManyToOne
	@JoinColumn(name = "id_zona")
	private ZonaEt zona;

	@Transient
	private boolean eliminada;

	public boolean isEliminada() {
		return eliminada;
	}

	public void setEliminada(boolean eliminada) {
		this.eliminada = eliminada;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "estado_inventario")
	private EstadoCheckListEnum estadoInventario;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "planificacionInventario", fetch = FetchType.LAZY)
	@OrderBy("idPlanificacionInventarioTipo asc")
	@Where(clause = "estado = 'ACT'")
	private List<PlanificacionInventarioTipoEt> planificacionInventarioTipo;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "planificacionInventario", fetch = FetchType.LAZY)
	@OrderBy("idPlanificacionResponsable asc")
	@Where(clause = "estado = 'ACT'")
	private List<PlanificacionResponsableEt> planificacionResponsable;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "planificacionInventario", fetch = FetchType.LAZY)
	@OrderBy("idPlanificacionParticipante asc")
	@Where(clause = "estado = 'ACT'")
	private List<PlanificacionParticipanteEt> planificacionParticipante;

	public PlanificacionInventarioEt() {
		this.notificacion = false;
	}

	public Long getIdPlanificacionInventario() {
		return idPlanificacionInventario;
	}

	public void setIdPlanificacionInventario(Long idPlanificacionInventario) {
		this.idPlanificacionInventario = idPlanificacionInventario;
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

	public List<PlanificacionResponsableEt> getPlanificacionResponsable() {
		return planificacionResponsable;
	}

	public void setPlanificacionResponsable(List<PlanificacionResponsableEt> planificacionResponsable) {
		this.planificacionResponsable = planificacionResponsable;
	}

	public List<PlanificacionParticipanteEt> getPlanificacionParticipante() {
		return planificacionParticipante;
	}

	public void setPlanificacionParticipante(List<PlanificacionParticipanteEt> planificacionParticipante) {
		this.planificacionParticipante = planificacionParticipante;
	}

	public EstadoCheckListEnum getEstadoInventario() {
		return estadoInventario;
	}

	public void setEstadoInventario(EstadoCheckListEnum estadoInventario) {
		this.estadoInventario = estadoInventario;
	}

	public ZonaEt getZona() {
		return zona;
	}

	public void setZona(ZonaEt zona) {
		this.zona = zona;
	}

	public List<PlanificacionInventarioTipoEt> getPlanificacionInventarioTipo() {
		return planificacionInventarioTipo;
	}

	public void setPlanificacionInventarioTipo(List<PlanificacionInventarioTipoEt> planificacionInventarioTipo) {
		this.planificacionInventarioTipo = planificacionInventarioTipo;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PlanificacionInventarioEt) {
			PlanificacionInventarioEt other = (PlanificacionInventarioEt) obj;

			if (this.idPlanificacionInventario == null)
				return this == other;

			if (this.idPlanificacionInventario.equals(other.idPlanificacionInventario))
				return true;
		}
		return false;

	}

}
