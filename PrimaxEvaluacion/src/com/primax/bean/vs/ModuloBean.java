package com.primax.bean.vs;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.sec.MenuEt;
import com.primax.srv.idao.IMenuEtDao;

@Named("ModuloBn")
@ViewScoped
public class ModuloBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = -1672574878732702934L;

	@EJB
	private IMenuEtDao iMenuDao;

	private MenuEt menuSeleccionado = new MenuEt();
	private MenuEt menuPadre = new MenuEt();
	private TreeNode selectedNode;

	@Inject
	private AppMain appMain;

	public void nuevaOpcion() {
		menuSeleccionado = new MenuEt();
	}

	public void nuevoHijo() {
		if (selectedNode != null && selectedNode.getData() != null) {
			menuPadre = (MenuEt) selectedNode.getData();
			menuSeleccionado = new MenuEt();
			menuSeleccionado.setMenuPadre(menuPadre);
			getRequestContext().execute("PF('dialog_02').show()");
		} else {
			showInfo("Notificación", FacesMessage.SEVERITY_ERROR, null,
					"Por favor seleccione una opcion para continuar.");
		}
	}

	public void cargarOpcion() {
		if (selectedNode != null && selectedNode.getData() != null) {
			menuSeleccionado = (MenuEt) selectedNode.getData();
			getRequestContext().execute("PF('dialog_02').show()");
		} else {
			showInfo("Notificación", FacesMessage.SEVERITY_ERROR, null,
					"Por favor seleccione una opcion para continuar.");
		}
	}

	public void guardarModulo() {
		try {
			iMenuDao.guardarMenu(menuSeleccionado);
			showInfo("Notificación", FacesMessage.SEVERITY_INFO, null, "Opción guardada exitosamente");
		} catch (EntidadNoGrabadaException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void init() {
	}

	@Override
	public void onDestroy() {
		iMenuDao.remove();
	}

	public TreeNode getArbol() {
		TreeNode nodoPrincipal = new DefaultTreeNode(new MenuEt(), null);
		for (MenuEt padre : iMenuDao.getModulesChild(EstadoEnum.ACT, EstadoEnum.INA)) {
			nodoRecursivo(nodoPrincipal, padre);
		}
		return nodoPrincipal;
	}

	public void nodoRecursivo(TreeNode nodoPadre, MenuEt padre) {
		TreeNode nodoHijo = new DefaultTreeNode(padre, nodoPadre);
		List<MenuEt> menuHijos = iMenuDao.getChilds(padre, EstadoEnum.ACT, EstadoEnum.INA);
		if (menuHijos != null && menuHijos.size() > 0) {
			for (MenuEt hijo : menuHijos) {
				nodoRecursivo(nodoHijo, hijo);
			}
		}
	}

	public MenuEt getMenuSeleccionado() {
		return menuSeleccionado;
	}

	public void setMenuSeleccionado(MenuEt menuSeleccionado) {
		this.menuSeleccionado = menuSeleccionado;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public MenuEt getMenuPadre() {
		return menuPadre;
	}

	public void setMenuPadre(MenuEt menuPadre) {
		this.menuPadre = menuPadre;
	}

	public AppMain getAppMain() {
		return appMain;
	}

	public void setAppMain(AppMain appMain) {
		this.appMain = appMain;
	}

}
