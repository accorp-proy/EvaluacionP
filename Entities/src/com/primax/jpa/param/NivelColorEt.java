package com.primax.jpa.param;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "NIVEL_COLOR_ET")
@Audited

public class NivelColorEt extends EntityBase {

	@Id
	@SequenceGenerator(name = "sec_nivel_color_et", sequenceName = "seq_nivel_color_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_nivel_color_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_nivel_color")
	private Long idNivelColor;

	@Column(name = "orden")
	private Long orden;

	@Column(name = "descripcion", length = 20)
	private String descripcion;

	@Column(name = "color", length = 20)
	private String color;

	@Column(name = "nombre_img", length = 20)
	private String nombreImg;

	public NivelColorEt() {
		this.orden = 1L;
		this.color = "";
		this.nombreImg = "";
		this.descripcion = "";
	}

	public Long getIdNivelColor() {
		return idNivelColor;
	}

	public void setIdNivelColor(Long idNivelColor) {
		this.idNivelColor = idNivelColor;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getNombreImg() {
		return nombreImg;
	}

	public void setNombreImg(String nombreImg) {
		this.nombreImg = nombreImg;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NivelColorEt) {
			NivelColorEt other = (NivelColorEt) obj;

			if (this.idNivelColor == null)
				return this == other;

			return this.idNivelColor.equals(other.idNivelColor) ? true : false;
		} else
			return false;
	}

}
