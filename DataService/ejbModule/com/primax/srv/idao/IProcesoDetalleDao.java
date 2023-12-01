package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.ProcesoDetalleEt;
import com.primax.jpa.param.ProcesoEt;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IProcesoDetalleDao extends IGenericDao<ProcesoDetalleEt, Long> {

	public void remove();

	public ProcesoDetalleEt getProcesoDetalle(long id);

	public List<ProcesoDetalleEt> getProcesoDetalleList(String condicion) throws EntidadNoEncontradaException;

	public List<ProcesoDetalleEt> getProcesoDetalleByProcesolList(ProcesoEt proceso) throws EntidadNoEncontradaException;

	public void guardaProcesoDetalle(ProcesoDetalleEt procesoDetalle, UsuarioEt usuario) throws EntidadNoGrabadaException;

	public List<ProcesoDetalleEt> getProcesoDetalleByTipoChecKlList(TipoChecKListEt tipoChecKList) throws EntidadNoEncontradaException;

}
