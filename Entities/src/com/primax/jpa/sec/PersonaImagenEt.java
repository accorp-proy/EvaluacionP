package com.primax.jpa.sec;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.primax.jpa.base.EntityBase;
import com.primax.jpa.gen.PersonaEt;

@Entity
@Table(name = "PERSONA_IMAGEN_ET")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class PersonaImagenEt extends EntityBase {

	@Id
	@SequenceGenerator(name = "sec_img_usuario_et", sequenceName = "seq_img_usuario_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_img_usuario_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_usuario_img")
	private Long idUsuarioImg;

	@Column(length = 200, name = "path_imagen")
	private String ImgPath;

	@Column(name = "nombre_imagen")
	private String nombreImagen;

	@Basic(fetch = FetchType.LAZY, optional = true)
	@Column(name = "img_usuario")
	private byte[] imgUsuario;

	@ManyToOne
	@JoinColumn(name = "idPersona")
	private PersonaEt persona;

	public PersonaEt getPersona() {
		return persona;
	}

	public void setPersona(PersonaEt persona) {
		this.persona = persona;
	}

	public Long getIdUsuarioImg() {
		return idUsuarioImg;
	}

	public void setIdUsuarioImg(Long idUsuarioImg) {
		this.idUsuarioImg = idUsuarioImg;
	}

	public String getImgPath() {
		return ImgPath;
	}

	public void setImgPath(String imgPath) {
		ImgPath = imgPath;
	}

	public String getNombreImagen() {
		return nombreImagen;
	}

	public void setNombreImagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
	}

	public byte[] getImgUsuario() {
		return imgUsuario;
	}

	public void setImgUsuario(byte[] imgUsuario) {
		this.imgUsuario = imgUsuario;
	}

}
