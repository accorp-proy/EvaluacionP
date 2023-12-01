package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IEvaluacionDao extends IGenericDao<EvaluacionEt, Long> {

	public void remove();

	public EvaluacionEt getEvaluacion(long id);

	public String limpiarReporte(Long idUsuario);

	public List<EvaluacionEt> getEvaluacionList(String condicion) throws EntidadNoEncontradaException;

	public List<EvaluacionEt> getEvaluacionByCriterio(boolean criterio) throws EntidadNoEncontradaException;

	public void guardarEvaluacion(EvaluacionEt evaluacion, UsuarioEt usuario) throws EntidadNoGrabadaException;
}
