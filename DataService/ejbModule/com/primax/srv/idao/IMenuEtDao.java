package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.sec.MenuEt;
import com.primax.jpa.sec.RolMenuEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IMenuEtDao extends IGenericDao<MenuEt, Long> {

	public MenuEt getModulobyId(long id);

	public List<MenuEt> getModules();

	public List<RolMenuEt> getMenuModuloUsuario(UsuarioEt usuario);

	public List<RolMenuEt> getModules(UsuarioEt usuario);

	public List<RolMenuEt> getMenues(MenuEt modulo, UsuarioEt usuario);

	public boolean verificaMenu(String URI, UsuarioEt usuario);

	public List<MenuEt> getMenuesbyCondicion(String condicion);

	public List<MenuEt> getOpcionbyCondicion(String condicion);

	public List<MenuEt> getModulesChild(EstadoEnum... estado);

	public List<MenuEt> getChilds(MenuEt menu, EstadoEnum... estado);

	public void guardarMenu(MenuEt menu) throws EntidadNoGrabadaException;

	public MenuEt getMenuUrl(String url);

	public void remove();

}
