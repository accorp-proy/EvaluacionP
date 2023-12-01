package com.primax.srv.idao;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.ZonaEt;
import com.primax.jpa.param.ZonaUsuarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IZonaUsuarioDao extends IGenericDao<ZonaUsuarioEt, Long> {

	public void remove();

	public ZonaUsuarioEt getZonaUsuarioById(long id);

	public ZonaUsuarioEt getZonaExiste(ZonaEt zona, UsuarioEt usuario) throws EntidadNoEncontradaException;

	public void guardarZonaUsuario(ZonaUsuarioEt zonaUsuario, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
