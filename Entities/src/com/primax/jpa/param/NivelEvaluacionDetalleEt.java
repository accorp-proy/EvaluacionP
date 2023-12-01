package com.primax.jpa.param;

import java.io.InputStream;
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
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "NIVEL_EVALUACION_DETALLE_ET")
@Audited
public class NivelEvaluacionDetalleEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_nivel_evaluacion_detalle_et", sequenceName = "seq_nivel_evaluacion_detalle_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_nivel_evaluacion_detalle_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_nivel_evaluacion_detalle")
	private Long idNivelEvaluacionDetalle;

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

	@Column(name = "img_path_server", length = 500)
	private String imgPathServer;

	@Column(name = "img_path", length = 500)
	private String imgPath;

	@Column(name = "img_nombre", length = 100)
	private String imgNombre;

	@Transient
	private InputStream file;

	@ManyToOne
	@JoinColumn(name = "id_nivel_evaluacion")
	private NivelEvaluacionEt nivelEvaluacion;

	public NivelEvaluacionDetalleEt() {
		this.color = "";
		this.orden = 0L;
		this.menor = 1L;
		this.mayor = 1L;
		this.descripcion = "";
		this.imgNombre = "default.png";
		this.imgPathServer = "/ControlInterno/nivelEvaluacion/0/";
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public Long getIdNivelEvaluacionDetalle() {
		return idNivelEvaluacionDetalle;
	}

	public void setIdNivelEvaluacionDetalle(Long idNivelEvaluacionDetalle) {
		this.idNivelEvaluacionDetalle = idNivelEvaluacionDetalle;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public NivelEvaluacionEt getNivelEvaluacion() {
		return nivelEvaluacion;
	}

	public void setNivelEvaluacion(NivelEvaluacionEt nivelEvaluacion) {
		this.nivelEvaluacion = nivelEvaluacion;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getImgNombre() {
		return imgNombre;
	}

	public void setImgNombre(String imgNombre) {
		this.imgNombre = imgNombre;
	}

	public InputStream getFile() {
		return file;
	}

	public void setFile(InputStream file) {
		this.file = file;
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

	public String getImgPathServer() {
		return imgPathServer;
	}

	public void setImgPathServer(String imgPathServer) {
		this.imgPathServer = imgPathServer;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NivelEvaluacionDetalleEt) {
			NivelEvaluacionDetalleEt other = (NivelEvaluacionDetalleEt) obj;
			if (this.idNivelEvaluacionDetalle == null)
				return this == other;

			if (this.idNivelEvaluacionDetalle.equals(other.idNivelEvaluacionDetalle))
				return true;
		}
		return false;

	}

}
