package com.primax.srv.idao;

import java.util.Date;
import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.pla.PlanificacionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IPlanificacionDao extends IGenericDao<PlanificacionEt, Long> {

	public void remove();

	public void guardarPlanificacion(PlanificacionEt planificacion, UsuarioEt usuario) throws EntidadNoGrabadaException;

	public List<PlanificacionEt> getPlanificacionList(EvaluacionEt evaluacion, TipoChecKListEt tipoChecKList, Date fechaDesde, Date fechaHasta)

			throws EntidadNoEncontradaException;

	public List<PlanificacionEt> getPlanificacionList(AgenciaEt estacion, Date fechaDesde, Date fechaHasta) throws EntidadNoEncontradaException;

	public List<PlanificacionEt> getPlanificacionList(EvaluacionEt evaluacion, TipoChecKListEt tipoChecKList, Date fechaDesde, Date fechaHasta, UsuarioEt usuario)
			throws EntidadNoEncontradaException;

}
