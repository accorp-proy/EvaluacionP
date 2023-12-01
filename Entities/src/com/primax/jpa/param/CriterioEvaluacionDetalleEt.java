package com.primax.jpa.param;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "CRITERIO_EVALUACION_DETALLE_ET")
@Audited
public class CriterioEvaluacionDetalleEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_criterio_evaluacion_detalle_et", sequenceName = "seq_criterio_evaluacion_detalle_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_criterio_evaluacion_detalle_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_criterio_evaluacion_detalle")
	private Long idCriterioEvaluacionDetalle;

	@Column(name = "nombre", length = 100)
	@OrderBy("order by nombre")
	private String nombre;

	@Column(name = "puntaje")
	private Long puntaje;

	@Column(name = "color", length = 20)
	private String color;

	@Column(name = "comentario", length = 1000)
	private String comentario;

	@ManyToOne
	@JoinColumn(name = "id_criterio_evaluacion")
	private CriterioEvaluacionEt criterioEvaluacion;

	@Column(name = "orden")
	private Long orden;

	public CriterioEvaluacionDetalleEt() {
		this.orden = 0L;
		this.puntaje = 1L;
		this.color = "#FFFFFF";
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public Long getIdCriterioEvaluacionDetalle() {
		return idCriterioEvaluacionDetalle;
	}

	public void setIdCriterioEvaluacionDetalle(Long idCriterioEvaluacionDetalle) {
		this.idCriterioEvaluacionDetalle = idCriterioEvaluacionDetalle;
	}

	public CriterioEvaluacionEt getCriterioEvaluacion() {
		return criterioEvaluacion;
	}

	public void setCriterioEvaluacion(CriterioEvaluacionEt criterioEvaluacion) {
		this.criterioEvaluacion = criterioEvaluacion;
	}

	public Long getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(Long puntaje) {
		this.puntaje = puntaje;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CriterioEvaluacionDetalleEt) {
			CriterioEvaluacionDetalleEt other = (CriterioEvaluacionDetalleEt) obj;
			if (this.idCriterioEvaluacionDetalle == null)
				return this == other;

			if (this.idCriterioEvaluacionDetalle.equals(other.idCriterioEvaluacionDetalle))
				return true;
		}
		return false;

	}

}
