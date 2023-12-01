package com.primax.jpa.sec;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

import com.primax.jpa.base.EntityBase;

@Entity
@Table(name = "MENU_ET")
@Audited
public class MenuEt extends EntityBase {

	@Id
	@SequenceGenerator(name = "sec_menu_et", sequenceName = "seq_menu_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_menu_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_menu")
	private Long idMenu;

	@Column(name = "descripcion_menu")
	private String descMenu;

	@Column(name = "icono_menu")
	private String icoMenu;

	@Column(name = "url_menu")
	private String urlMenu;

	@ManyToOne
	@JoinColumn(name = "id_menu_padre")
	private MenuEt menuPadre;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "menuPadre", fetch = FetchType.LAZY)
	private List<MenuEt> menuHijos;

	@Column(name = "menu_orden", length = 4)
	private Integer ordenMenu;

	@Transient
	private boolean seleccionado;

	public Long getIdMenu() {
		return idMenu;
	}

	public void setIdMenu(Long idMenu) {
		this.idMenu = idMenu;
	}

	public String getDescMenu() {
		return descMenu;
	}

	public void setDescMenu(String descMenu) {
		this.descMenu = descMenu;
	}

	public String getIcoMenu() {
		return icoMenu;
	}

	public void setIcoMenu(String icoMenu) {
		this.icoMenu = icoMenu;
	}

	public String getUrlMenu() {
		return urlMenu;
	}

	public void setUrlMenu(String urlMenu) {
		this.urlMenu = urlMenu;
	}

	public MenuEt getMenuPadre() {
		return menuPadre;
	}

	public void setMenuPadre(MenuEt menuPadre) {
		this.menuPadre = menuPadre;
	}

	public List<MenuEt> getMenuHijos() {
		return menuHijos;
	}

	public void setMenuHijos(List<MenuEt> menuHijos) {
		this.menuHijos = menuHijos;
	}

	public Integer getOrdenMenu() {
		return ordenMenu;
	}

	public void setOrdenMenu(Integer ordenMenu) {
		this.ordenMenu = ordenMenu;
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MenuEt) {
			MenuEt other = (MenuEt) obj;
			if (this.idMenu == null)
				return this == other;

			return other.idMenu.equals(this.idMenu) ? true : false;
		}
		return false;
	}

	@Override
	public String toString() {
		return this.descMenu != null ? descMenu : "";
	}

}
