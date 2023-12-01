package com.primax.jpa.param;

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
@Table(name = "FRECUENCIA_VISITA_DETALLE_ET")
@Audited
public class FrecuenciaVisitaDetalleEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_frecuencia_visita_detalle_et", sequenceName = "seq_frecuencia_visita_detalle_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_frecuencia_visita_detalle_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_frecuencia_visita_detalle")
	private Long idFrecuenciaVisitaDetalle;

	@Column(name = "descripcion", length = 100)
	private String descripcion;

	@Column(name = "orden")
	private Long orden;

	@Column(name = "menor")
	private Long menor;

	@Column(name = "mayor")
	private Long mayor;

	@Column(name = "color", length = 20)
	private String color;

	@ManyToOne
	@JoinColumn(name = "id_frecuencia_visita")
	private FrecuenciaVisitaEt frecuenciaVisita;

	public FrecuenciaVisitaDetalleEt() {
		this.color = "";
		this.orden = 0L;
		this.menor = 1L;
		this.mayor = 1L;
		this.descripcion = "";
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public Long getMenor() {
		return menor;
	}

	public void setMenor(Long menor) {
		this.menor = menor;
	}

	public Long getMayor() {
		return mayor;
	}

	public void setMayor(Long mayor) {
		this.mayor = mayor;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Long getIdFrecuenciaVisitaDetalle() {
		return idFrecuenciaVisitaDetalle;
	}

	public void setIdFrecuenciaVisitaDetalle(Long idFrecuenciaVisitaDetalle) {
		this.idFrecuenciaVisitaDetalle = idFrecuenciaVisitaDetalle;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public FrecuenciaVisitaEt getFrecuenciaVisita() {
		return frecuenciaVisita;
	}

	public void setFrecuenciaVisita(FrecuenciaVisitaEt frecuenciaVisita) {
		this.frecuenciaVisita = frecuenciaVisita;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FrecuenciaVisitaDetalleEt) {
			FrecuenciaVisitaDetalleEt other = (FrecuenciaVisitaDetalleEt) obj;
			if (this.idFrecuenciaVisitaDetalle == null)
				return this == other;

			if (this.idFrecuenciaVisitaDetalle.equals(other.idFrecuenciaVisitaDetalle))
				return true;
		}
		return false;

	}

}
