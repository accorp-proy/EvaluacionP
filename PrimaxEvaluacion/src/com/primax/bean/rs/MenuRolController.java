package com.primax.bean.rs;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.primax.bean.vs.MenuRolBean;
import com.primax.bean.vs.base.BaseBean;
import com.primax.jpa.sec.MenuEt;
import com.primax.jpa.sec.RolEt;

@Named("menuRolCtrl")
@RequestScoped
public class MenuRolController extends BaseBean implements Serializable {

	private static final long serialVersionUID = -7960632982409683136L;
	@Inject
	private MenuRolBean menuRolBean;

	@Override
	public void init() {

	}

	public void actualizaMenu(MenuEt evt) {
		if (evt.getMenuPadre() == null) {
			if (evt.isSeleccionado()) {
				for (MenuEt mnDep : evt.getMenuHijos()) {
					mnDep.setSeleccionado(true);
				}
			} else {
				for (MenuEt mnDep : evt.getMenuHijos()) {
					mnDep.setSeleccionado(false);
				}
			}
		} else {
			int hijosSeleccionados = 0;
			MenuEt menuPadre = evt.getMenuPadre();
			for (MenuEt menu : menuPadre.getMenuHijos()) {
				if (menu.isSeleccionado()) {
					hijosSeleccionados++;
				}
			}
			if (hijosSeleccionados < menuPadre.getMenuHijos().size()) {
				menuPadre.setSeleccionado(false);
			} else {
				menuPadre.setSeleccionado(true);
			}
		}

	}

	public MenuRolBean getMenuRolBean() {
		return menuRolBean;
	}

	public void setMenuRolBean(MenuRolBean menuRolBean) {
		this.menuRolBean = menuRolBean;
	}

	public void nuevo() {
		this.menuRolBean.setRolSeleccionado(new RolEt());
	}

	public void rolSeleccion(RolEt rolSeleccionado) {
		this.menuRolBean.setRolSeleccionado(rolSeleccionado);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub

	}

}
