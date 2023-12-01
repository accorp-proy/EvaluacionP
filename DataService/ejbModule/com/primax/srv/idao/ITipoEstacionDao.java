package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.TipoEstacionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ITipoEstacionDao extends IGenericDao<TipoEstacionEt, Long> {

	public void remove();

	public TipoEstacionEt getTipoEstacion(long id);

	public List<TipoEstacionEt> getTipoEstacionList0(TipoEstacionEt tipoEstacion) throws EntidadNoEncontradaException;

	public List<TipoEstacionEt> getTipoEstacionList(String condicion) throws EntidadNoEncontradaException;

	public void guardarTipoEstacion(TipoEstacionEt tipoEstacion, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
