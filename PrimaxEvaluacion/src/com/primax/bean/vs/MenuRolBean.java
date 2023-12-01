package com.primax.bean.vs;

import java.io.Serializable;
import java.util.ArrayList;
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
import com.primax.jpa.sec.RolEt;
import com.primax.jpa.sec.RolMenuEt;
import com.primax.srv.idao.IMenuEtDao;
import com.primax.srv.idao.IRolEtDao;
import com.primax.srv.idao.IRolMenuDao;

@Named("MenuRolBn")
@ViewScoped
public class MenuRolBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 891053855006526981L;

	private RolEt rolSeleccionado;

	private List<RolMenuEt> rolMenu;
	private RolMenuEt rolMenuSeleccionado;
	private MenuEt menuSeleccion;
	private List<MenuEt> menuesSistema;
	private TreeNode[] selectedNodes;
	private TreeNode nodoPrincipal = new DefaultTreeNode(new MenuEt(), null);

	@EJB
	private IMenuEtDao iMenuDao;
	@EJB
	private IRolEtDao iRolEtDao;
	@EJB
	private IRolMenuDao iRolMenuDao;

	@Inject
	private AppMain appMain;

	@Override
	public void init() {
		rolSeleccionado = new RolEt();
		menuesSistema = iMenuDao.getModulesChild(EstadoEnum.ACT);
		rolMenu = null;
	}

	@Override
	public void onDestroy() {
		iRolEtDao.remove();
		iRolMenuDao.remove();
		iMenuDao.remove();
	}

	/* PROCEDIMIENTOS */

	public List<MenuEt> getMenuesSistema() {
		return menuesSistema;
	}

	public List<RolEt> getRolesSistema() {
		return iRolEtDao.obtenerTodos();
	}

	public void cargarRolMenu() {
		getArbol();
		this.rolMenu = iRolMenuDao.getMenusByRol(rolSeleccionado);
		for (int i = 0; i < rolMenu.size(); i++) {
			cargarNodosSelect(nodoPrincipal, rolMenu.get(i));
		}
	}

	public void cargarNodosSelect(TreeNode nodo, RolMenuEt rolMenu) {
		if (nodo.getData().equals(rolMenu)) {
			nodo.setSelected(true);
		}
		if (nodo.getChildCount() > 0) {
			for (int j = 0; j < nodo.getChildCount(); j++) {
				cargarNodosSelect(nodo.getChildren().get(j), rolMenu);
			}
		}
	}

	/* GETTERS SETTERS */

	public RolEt getRolSeleccionado() {
		return rolSeleccionado;
	}

	public void setRolSeleccionado(RolEt rolSeleccionado) {
		this.rolSeleccionado = rolSeleccionado;
	}

	public AppMain getAppMain() {
		return appMain;
	}

	public void setAppMain(AppMain appMain) {
		this.appMain = appMain;
	}

	public List<RolMenuEt> getRolMenu() {
		return rolMenu;
	}

	public void setRolMenu(List<RolMenuEt> rolMenu) {
		this.rolMenu = rolMenu;
	}

	public RolMenuEt getRolMenuSeleccionado() {
		return rolMenuSeleccionado;
	}

	public void setRolMenuSeleccionado(RolMenuEt rolMenuSeleccionado) {
		this.rolMenuSeleccionado = rolMenuSeleccionado;
	}

	public MenuEt getMenuSeleccion() {
		return menuSeleccion;
	}

	public void setMenuSeleccion(MenuEt menuSeleccion) {
		this.menuSeleccion = menuSeleccion;
	}

	public TreeNode[] getSelectedNodes() {
		return selectedNodes;
	}

	public void setSelectedNodes(TreeNode[] selectedNodes) {
		this.selectedNodes = selectedNodes;
	}

	public TreeNode getNodoPrincipal() {
		return nodoPrincipal;
	}

	public void setNodoPrincipal(TreeNode nodoPrincipal) {
		this.nodoPrincipal = nodoPrincipal;
	}

	public void preGuardar() {
		for (int i = 0; i < selectedNodes.length; i++) {
			RolMenuEt rmen =  (RolMenuEt) selectedNodes[i].getData();
			if (!verificarExiste(rmen.getMenu())) {
				rmen.setRol(rolSeleccionado);
				rolSeleccionado.getRolMenu().add(rmen);
			}
			if (selectedNodes[i].getParent() != null
					&& ( (RolMenuEt)selectedNodes[i].getParent().getData()).getIdRolMenu() != null) {
				RolMenuEt rolMenuPadre =  (RolMenuEt) selectedNodes[i].getParent().getData();
				if (!verificarExiste(rolMenuPadre.getMenu())) {
					rolMenuPadre.setRol(rolSeleccionado);
					rolSeleccionado.getRolMenu().add(rolMenuPadre);
				}
			}
		}
	}

	public boolean verificarExiste(MenuEt menu) {
		boolean res = false;
		for (RolMenuEt rm : rolSeleccionado.getRolMenu()) {
			if (rm.getMenu().equals(menu)) {
				res = true;
			}
		}
		return res;
	}

	public void guardarRolesAccesos() {
		rolSeleccionado.setRolMenu(new ArrayList<RolMenuEt>());
		preGuardar();
		try {
			iRolMenuDao.invalidarAccesos(rolSeleccionado, appMain.getUsuario());
			iRolMenuDao.guardarRolesMenu(rolSeleccionado, appMain.getUsuario());
			init();
			showInfo("Datos de rol guardado", FacesMessage.SEVERITY_INFO);
		} catch (EntidadNoGrabadaException e) {
			showInfo("Error al guardar Dato", FacesMessage.SEVERITY_INFO);
			e.printStackTrace();
		}
	}

	public void nuevo() {
		rolSeleccionado = new RolEt();
	}

	public void edit(RolEt rol) {
		rolSeleccionado = rol;
	}

	public void editAcesos(RolEt rol) {
		rolSeleccionado = rol;
		iRolMenuDao.clear();
		cargarRolMenu();
	}

	public void guardarSoloRol() {
		try {
			if (this.rolSeleccionado.getIdRol() == null) {
				iRolEtDao.crear(rolSeleccionado);
			} else {
				iRolEtDao.actualizar(rolSeleccionado);
			}
			showInfo("Rol guardado", FacesMessage.SEVERITY_INFO);
		} catch (EntidadNoGrabadaException e) {
			e.printStackTrace();
			showInfo(e.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}

	public void getArbol() {
		nodoPrincipal = new DefaultTreeNode(new RolMenuEt(), null);
		for (MenuEt padre : menuesSistema) {
			RolMenuEt rm = new RolMenuEt();
			rm = iRolMenuDao.getRolMenu(rolSeleccionado, padre);
			if (rm == null || rm.getIdRolMenu() == null) {
				rm = new RolMenuEt();
				rm.setMenu(padre);
				rm.setRol(rolSeleccionado);
			}
			nodoRecursivo(nodoPrincipal, rm);
		}
	}

	public void nodoRecursivo(TreeNode nodoPadre, RolMenuEt padre) {
		TreeNode nodoHijo = new DefaultTreeNode(padre, nodoPadre);
		List<MenuEt> menuHijos = iMenuDao.getChilds(padre.getMenu(), EstadoEnum.ACT);
		if (menuHijos != null && menuHijos.size() > 0) {
			for (MenuEt hijo : menuHijos) {
				RolMenuEt rm = new RolMenuEt();
				rm = iRolMenuDao.getRolMenu(rolSeleccionado, hijo);
				if (rm == null || rm.getIdRolMenu() == null) {
					rm = new RolMenuEt();
					rm.setMenu(hijo);
					rm.setRol(rolSeleccionado);
				}
				nodoRecursivo(nodoHijo, rm);
			}
		}
	}

}
