package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.CategoriaEstacionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ICategoriaEstacionDao extends IGenericDao<CategoriaEstacionEt, Long> {

	public void remove();

	public CategoriaEstacionEt getCategoriaEstacion(long id);

	public List<CategoriaEstacionEt> getCategoriaEstacionList(String condicion) throws EntidadNoEncontradaException;

	public void guardarSubformatoNegocio(CategoriaEstacionEt categoriaEstacion, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
