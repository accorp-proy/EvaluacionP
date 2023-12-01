package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.ZonaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IZonaDao extends IGenericDao<ZonaEt, Long> {

	public void remove();

	public ZonaEt getZonaById(long id);

	public String limpiarReporte(Long idUsuario);

	public List<ZonaEt> getZonaList(String condicion) throws EntidadNoEncontradaException;

	public void guardarZona(ZonaEt zona, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
