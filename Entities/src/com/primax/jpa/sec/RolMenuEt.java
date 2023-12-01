package com.primax.jpa.sec;

import java.util.List;

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

@Entity
@Table(name = "ROL_MENU_ET")
@Audited
public class RolMenuEt extends EntityBase {

	@Id
	@SequenceGenerator(name = "sec_rol_menu_et", sequenceName = "seq_rol_menu_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_rol_menu_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_rol_menu")
	private Long idRolMenu;

	@ManyToOne
	@JoinColumn(name = "id_menu")
	private MenuEt menu;

	@ManyToOne
	@JoinColumn(name = "id_rol")
	private RolEt rol;

	@Column(name = "consulta")
	private Boolean consulta;
	
	@Transient
	private List<RolMenuEt> rolMenuRec;

	public Long getIdRolMenu() {
		return idRolMenu;
	}

	public void setIdRolMenu(Long idRolMenu) {
		this.idRolMenu = idRolMenu;
	}

	public MenuEt getMenu() {
		return menu;
	}

	public void setMenu(MenuEt menu) {
		this.menu = menu;
	}

	public RolEt getRol() {
		return rol;
	}

	public void setRol(RolEt rol) {
		this.rol = rol;
	}

	public List<RolMenuEt> getRolMenuRec() {
		return rolMenuRec;
	}

	public void setRolMenuRec(List<RolMenuEt> rolMenuRec) {
		this.rolMenuRec = rolMenuRec;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	public Boolean getConsulta() {
		return consulta;
	}

	public void setConsulta(Boolean consulta) {
		this.consulta = consulta;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RolMenuEt) {

			RolMenuEt other = (RolMenuEt) obj;

			if (this.idRolMenu == null)
				return this == other;

			return this.idRolMenu.equals(other.idRolMenu);
		}
		return false;
	}

}
