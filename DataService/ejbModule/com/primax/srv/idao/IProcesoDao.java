package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.ProcesoEt;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IProcesoDao extends IGenericDao<ProcesoEt, Long> {

	public void remove();

	public ProcesoEt getProceso(long id);

	public String limpiarReporte(Long idUsuario);

	public void guardarProceso(ProcesoEt proceso, UsuarioEt usuario) throws EntidadNoGrabadaException;

	public List<ProcesoEt> getProcesoByTipoList(TipoChecKListEt tipoChecKList) throws EntidadNoEncontradaException;

	public List<ProcesoEt> getProcesoList(TipoChecKListEt tipoChecKList, String condicion) throws EntidadNoEncontradaException;

}
