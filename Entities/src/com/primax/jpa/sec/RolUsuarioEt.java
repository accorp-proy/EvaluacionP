package com.primax.jpa.sec;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.primax.jpa.base.EntityBase;

@Entity
@Table(name = "ROL_USUARIO_ET")
@Audited
public class RolUsuarioEt extends EntityBase {

	@Id
	@SequenceGenerator(name = "sec_rol_usuario_et", sequenceName = "seq_rol_usuario_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_rol_usuario_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_rol_usuario")
	private Long idRolUsuario;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private UsuarioEt usuario;

	@OneToOne
	@JoinColumn(name = "id_rol")
	private RolEt rol;

	public Long getIdRolUsuario() {
		return idRolUsuario;
	}

	public void setIdRolUsuario(Long idRolUsuario) {
		this.idRolUsuario = idRolUsuario;
	}

	public UsuarioEt getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioEt usuario) {
		this.usuario = usuario;
	}

	public RolEt getRol() {
		return rol;
	}

	public void setRol(RolEt rol) {
		this.rol = rol;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RolUsuarioEt) {

			RolUsuarioEt other = (RolUsuarioEt) obj;

			if (this.idRolUsuario == null) {
				if (this == other) {
					return true;
				} else {
					return false;
				}
			}
			return this.idRolUsuario.equals(other.idRolUsuario) ? true : false;
		}
		return false;
	}

}
