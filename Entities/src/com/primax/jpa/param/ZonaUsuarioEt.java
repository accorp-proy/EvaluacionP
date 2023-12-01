package com.primax.jpa.param;

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

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "ZONA_USUARIO_ET")
@Audited
public class ZonaUsuarioEt extends EntityBase {

	@Id
	@SequenceGenerator(name = "sec_zona_usuario_et", sequenceName = "seq_zona_usuario_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_zona_usuario_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_zona_usuario")
	private Long idZonaUsuario;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private UsuarioEt usuario;

	@OneToOne
	@JoinColumn(name = "id_zona")
	private ZonaEt zona;

	public UsuarioEt getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioEt usuario) {
		this.usuario = usuario;
	}

	public Long getIdZonaUsuario() {
		return idZonaUsuario;
	}

	public void setIdZonaUsuario(Long idZonaUsuario) {
		this.idZonaUsuario = idZonaUsuario;
	}

	public ZonaEt getZona() {
		return zona;
	}

	public void setZona(ZonaEt zona) {
		this.zona = zona;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ZonaUsuarioEt) {

			ZonaUsuarioEt other = (ZonaUsuarioEt) obj;

			if (this.idZonaUsuario == null) {
				if (this == other) {
					return true;
				} else {
					return false;
				}
			}
			return this.idZonaUsuario.equals(other.idZonaUsuario) ? true : false;
		}
		return false;
	}

}
