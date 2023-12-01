package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.NivelEvaluacionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface INivelEvaluacionDao extends IGenericDao<NivelEvaluacionEt, Long> {

	public void remove();

	public String limpiarReporte(Long idUsuario);

	public NivelEvaluacionEt getNivelEvaluacion(long id);

	public List<NivelEvaluacionEt> getNivelEvaluacionList(String condicion) throws EntidadNoEncontradaException;

	public void guardarNivelEvaluacion(NivelEvaluacionEt nivelEvaluacion, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
