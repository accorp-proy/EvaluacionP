package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.NivelEvaluacionDetalleEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface INivelEvaluacionDetalleDao extends IGenericDao<NivelEvaluacionDetalleEt, Long> {

	public void remove();

	public NivelEvaluacionDetalleEt getNivelEvaluacionDetalle(long id);

	public List<NivelEvaluacionDetalleEt> getNivelEvaluacionDetalleList(String condicion) throws EntidadNoEncontradaException;

	public void guardaNivelEvaluacionDetalle(NivelEvaluacionDetalleEt nivelEvaluacionDetalle, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
