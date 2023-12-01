package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.CriterioEvaluacionDetalleEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ICriterioEvaluacionDetalleDao extends IGenericDao<CriterioEvaluacionDetalleEt, Long> {

	public void remove();

	public CriterioEvaluacionDetalleEt getCriterioEvaluacionDetalle(long id);

	public List<CriterioEvaluacionDetalleEt> getCriterioEvaluacionDetalleList(String condicion) throws EntidadNoEncontradaException;

	public void guardarCriterioEvaluacionDetalle(CriterioEvaluacionDetalleEt criterioEvaluacionDetalle, UsuarioEt usuario)
			throws EntidadNoGrabadaException;

}
