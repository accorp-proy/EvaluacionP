package com.primax.jpa.gen;

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
@Table(name = "PERSONA_CONTACTO_ET")
@Audited
public class PersonaContactoEt extends EntityBase implements Serializable{

	private static final long serialVersionUID = 2292239524785915468L;

	@Id
	@Column(name = "id_persona_contacto")
	@SequenceGenerator(name = "sec_persona_contacto", allocationSize = 1, initialValue = 1, sequenceName = "seq_persona_contacto")
	@GeneratedValue(generator = "sec_persona_contacto", strategy = GenerationType.SEQUENCE)
	private Long idPersonaContacto;

	@ManyToOne
	@JoinColumn(name = "id_persona")
	private PersonaEt personaContacto;

	@Column(name = "identificacion", length = 15)
	private String identificacion;

	@Column(name = "nombre_completo", length = 100)
	private String nombreCompleto;

	@Column(name = "direccion", length = 200)
	private String direccion;

	@Column(name = "email", length = 100)
	private String email;

	@Column(name = "telfono", length = 50)
	private String telefono;

	@Column(name = "celular", length = 50)
	private String celular;

	public Long getIdPersonaContacto() {
		return idPersonaContacto;
	}

	public void setIdPersonaContacto(Long idPersonaContacto) {
		this.idPersonaContacto = idPersonaContacto;
	}

	public PersonaEt getPersonaContacto() {
		return personaContacto;
	}

	public void setPersonaContacto(PersonaEt personaContacto) {
		this.personaContacto = personaContacto;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PersonaContactoEt) {
			PersonaContactoEt other = (PersonaContactoEt) obj;
			if (this.idPersonaContacto == null)
				return this == other;
			return this.idPersonaContacto.equals(other.idPersonaContacto);
		} else
			return false;
	}

}
