package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.TipoInventarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ITipoInventarioDao extends IGenericDao<TipoInventarioEt, Long> {

	public void remove();

	public String limpiarReporte(Long idUsuario);

	public TipoInventarioEt getTipoInventario(long id);

	public List<TipoInventarioEt> getTipoInventarioList(String condicion) throws EntidadNoEncontradaException;

	public void guardarTipoInventario(TipoInventarioEt tipoInventario, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
