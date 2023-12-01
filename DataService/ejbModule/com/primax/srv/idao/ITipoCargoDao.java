package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.TipoCargoEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ITipoCargoDao extends IGenericDao<TipoCargoEt, Long> {

	public void remove();

	public TipoCargoEt getTipoCargo(long id);

	public String limpiarReporte(Long idUsuario);

	public List<TipoCargoEt> getTipoCargoList(String condicion) throws EntidadNoEncontradaException;

	public void guardarTipoCargo(TipoCargoEt tipoCargo, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
