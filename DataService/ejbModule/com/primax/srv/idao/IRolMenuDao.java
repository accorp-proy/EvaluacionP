package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.sec.MenuEt;
import com.primax.jpa.sec.RolEt;
import com.primax.jpa.sec.RolMenuEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IRolMenuDao extends IGenericDao<RolMenuEt, Long> {

	public List<RolMenuEt> getMenusByRol(RolEt rol);

	public void guardarRolesMenu(RolEt rol, UsuarioEt user) throws EntidadNoGrabadaException;

	public void remove();

	public void invalidarAccesos(RolEt rolSeleccionado, UsuarioEt user) throws EntidadNoGrabadaException;
	
	public RolMenuEt getRolMenu(RolEt rol, MenuEt menu);
}
