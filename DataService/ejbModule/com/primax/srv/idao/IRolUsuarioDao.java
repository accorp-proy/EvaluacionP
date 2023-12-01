package com.primax.srv.idao;

import java.util.List;

import com.primax.jpa.param.ProvinciaEt;
import com.primax.jpa.sec.RolEt;
import com.primax.jpa.sec.RolUsuarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;


public interface IRolUsuarioDao extends IGenericDao<RolUsuarioEt, Long> {

	public List<UsuarioEt> getUsuariosPorRol(RolEt rol);

	public RolUsuarioEt getRolUsuarioById(Long id);

	public List<UsuarioEt> getUsuariobyRolAndProvincia(RolEt rol, ProvinciaEt provincia);

	public void remove();

}
