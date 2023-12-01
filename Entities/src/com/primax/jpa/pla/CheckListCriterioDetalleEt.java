package com.primax.jpa.pla;

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
@Table(name = "CHECK_LIST_CRITERIO_DETALLE_ET")
@Audited
public class CheckListCriterioDetalleEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_check_list_criterio_detalle_et", sequenceName = "seq_check_list_criterio_detalle_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_check_list_criterio_detalle_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_check_list_criterio_detalle")
	private Long idCheckListCriterioDetalle;

	@Column(name = "nombre", length = 100)
	@OrderBy("order by nombre")
	private String nombre;

	@Column(name = "puntaje")
	private Long puntaje;

	@Column(name = "color", length = 20)
	private String color;

	@ManyToOne
	@JoinColumn(name = "id_check_list_criterio")
	private CheckListCriterioEt checkListCriterio;

	@Column(name = "orden")
	private Long orden;

	public CheckListCriterioDetalleEt() {
		this.orden = 0L;
		this.puntaje = 1L;
		this.color = "#FFFFFF";
	}

	public Long getIdCheckListCriterioDetalle() {
		return idCheckListCriterioDetalle;
	}

	public void setIdCheckListCriterioDetalle(Long idCheckListCriterioDetalle) {
		this.idCheckListCriterioDetalle = idCheckListCriterioDetalle;
	}

	public CheckListCriterioEt getCheckListCriterio() {
		return checkListCriterio;
	}

	public void setCheckListCriterio(CheckListCriterioEt checkListCriterio) {
		this.checkListCriterio = checkListCriterio;
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

	public Long getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(Long puntaje) {
		this.puntaje = puntaje;
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
		if (obj instanceof CheckListCriterioDetalleEt) {
			CheckListCriterioDetalleEt other = (CheckListCriterioDetalleEt) obj;
			if (this.idCheckListCriterioDetalle == null)
				return this == other;

			if (this.idCheckListCriterioDetalle.equals(other.idCheckListCriterioDetalle))
				return true;
		}
		return false;

	}

}
