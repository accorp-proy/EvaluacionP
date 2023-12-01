package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ITipoChecKListDao extends IGenericDao<TipoChecKListEt, Long> {

	public void remove();

	public TipoChecKListEt getTipoChecList(long id);

	public List<TipoChecKListEt> getTipoChecList(String condicion) throws EntidadNoEncontradaException;

	public List<TipoChecKListEt> getTipoCheckListByEvaluacionControl() throws EntidadNoEncontradaException;

	public String generar(Long idZona, Long idTipoCheckList, Long idEvaluacion, Long idProceso, Long idUsuario);

	public void guardaTipoChecList(TipoChecKListEt tipoChecList, UsuarioEt usuario) throws EntidadNoGrabadaException;

	public List<TipoChecKListEt> getTipoCheckListByEvaluacion(EvaluacionEt evaluacion) throws EntidadNoEncontradaException;

}
