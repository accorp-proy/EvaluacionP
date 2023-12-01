package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.CriterioEvaluacionEt;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.param.ProcesoDetalleEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ICriterioEvaluacionDao extends IGenericDao<CriterioEvaluacionEt, Long> {

	public void remove();

	public CriterioEvaluacionEt getCriterioEvaluacion(long id);

	public List<CriterioEvaluacionEt> getCriterioEvaluacionBPMList() throws EntidadNoEncontradaException;

	public List<CriterioEvaluacionEt> getCriterioEvaluacionList(EvaluacionEt evaluacion, TipoChecKListEt tipoChecKList, String condicion)
			throws EntidadNoEncontradaException;

	public void guardarCriterioEvaluacion(CriterioEvaluacionEt criterioEvaluacion, UsuarioEt usuario) throws EntidadNoGrabadaException;

	public CriterioEvaluacionEt getCriterioEvaluacionByProcesoDetalle(ProcesoDetalleEt procesoDetalle) throws EntidadNoEncontradaException;

}
