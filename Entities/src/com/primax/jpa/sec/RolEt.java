package com.primax.jpa.sec;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

import com.primax.jpa.base.EntityBase;

@Entity
@Table(name = "ROL_ET")
@Audited
public class RolEt extends EntityBase {

	@Id
	@SequenceGenerator(name = "sec_rol_et", sequenceName = "seq_rol_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_rol_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_rol")
	private Long idRol;

	@Column(name = "nombre_rol")
	private String nomRol;

	public Long getIdRol() {
		return idRol;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	public String getNomRol() {
		return nomRol;
	}

	public void setNomRol(String nomRol) {
		this.nomRol = nomRol;
	}

	@Transient
	private List<RolMenuEt> rolMenu;

	public List<RolMenuEt> getRolMenu() {
		return rolMenu;
	}

	public void setRolMenu(List<RolMenuEt> rolMenu) {
		this.rolMenu = rolMenu;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RolEt) {
			RolEt other = (RolEt) obj;

			if (this.idRol == null)
				return this == other;

			return this.idRol.equals(other.idRol) ? true : false;
		} else
			return false;
	}

}
