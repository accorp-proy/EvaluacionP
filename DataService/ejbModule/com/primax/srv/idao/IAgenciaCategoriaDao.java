package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.AgenciaCategoriaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IAgenciaCategoriaDao extends IGenericDao<AgenciaCategoriaEt, Long> {

	public void remove();

	public AgenciaCategoriaEt getAgenciaCategoria(long id);

	public List<AgenciaCategoriaEt> getAgenciaCategoriaList(String condicion) throws EntidadNoEncontradaException;

	public void guardarAgenciaCategoria(AgenciaCategoriaEt agenciaCategoria, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
