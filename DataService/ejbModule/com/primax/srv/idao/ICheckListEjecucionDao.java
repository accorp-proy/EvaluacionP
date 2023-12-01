package com.primax.srv.idao;

import java.util.Date;
import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoCheckListEnum;
import com.primax.jpa.enums.EstadoPlanAccionEnum;
import com.primax.jpa.enums.TipoCheckListEnum;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.param.NivelEvaluacionEt;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.param.ZonaEt;
import com.primax.jpa.pla.CheckListEjecucionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ICheckListEjecucionDao extends IGenericDao<CheckListEjecucionEt, Long> {

	public void remove();

	public CheckListEjecucionEt getCheckListEjecucion(long id);

	public Long calificacionActual(TipoChecKListEt tipoChecKList, AgenciaEt estacion);

	public String generarActNivelRiesgo(Long idNivelEvaluacion, Long idCheckListEjecucion);

	public List<CheckListEjecucionEt> getCheckEjecutando(UsuarioEt usuario) throws EntidadNoEncontradaException;

	public List<CheckListEjecucionEt> getCheckListPlanAccion(AgenciaEt agencia) throws EntidadNoEncontradaException;

	public List<CheckListEjecucionEt> getCheckEjecutandoPlanAccion(UsuarioEt usuario) throws EntidadNoEncontradaException;

	public List<CheckListEjecucionEt> getCheckListIngresandoPlanAccion(AgenciaEt agencia) throws EntidadNoEncontradaException;

	public CheckListEjecucionEt getCheckListEjecucionPlanAccion(TipoCheckListEnum tipoCheckList, AgenciaEt agencia)
			throws EntidadNoEncontradaException;

	public void guardarCheckListEjecucion(CheckListEjecucionEt checkListEjecucion, UsuarioEt usuario) throws EntidadNoGrabadaException;

	public List<CheckListEjecucionEt> getCheckListCambioEstadoList(EstadoCheckListEnum estadoCheckList) throws EntidadNoEncontradaException;

	public CheckListEjecucionEt getCheckListEjecucion(TipoCheckListEnum tipoCheckList, UsuarioEt usuario) throws EntidadNoEncontradaException;

	public CheckListEjecucionEt getCheckListEjecucion(AgenciaEt agencia, String codigo, Date fechaEjecucion) throws EntidadNoEncontradaException;

	public List<CheckListEjecucionEt> getCheckListEjecucionListPlanAccion(ZonaEt zona, AgenciaEt estacion, EvaluacionEt evaluacion, TipoChecKListEt tipoChecKList, NivelEvaluacionEt nivelEvaluacion, Date fechaDesde, Date fechaHasta, EstadoPlanAccionEnum estadoPlanAccion)
			throws EntidadNoEncontradaException;

	public List<CheckListEjecucionEt> getCheckListEjecucionAccesoZonaListPlanAccion(AgenciaEt estacion, EvaluacionEt evaluacion, TipoChecKListEt tipoChecKList, NivelEvaluacionEt nivelEvaluacion, Date fechaDesde, Date fechaHasta, EstadoPlanAccionEnum estadoPlanAccion, UsuarioEt usuario)
			throws EntidadNoEncontradaException;

	public List<CheckListEjecucionEt> getCheckListEjecucionList(ZonaEt zona, AgenciaEt agencia, EvaluacionEt evaluacion, TipoChecKListEt tipoChecKList, NivelEvaluacionEt nivelEvaluacion, Date fechaDesde, Date fechaHasta, UsuarioEt usuario, EstadoCheckListEnum estadoCheckList)
			throws EntidadNoEncontradaException;

	public List<CheckListEjecucionEt> getCheckListEjecucionAccesoZonaList(ZonaEt zona, AgenciaEt estacion, EvaluacionEt evaluacion, TipoChecKListEt tipoChecKList, NivelEvaluacionEt nivelEvaluacion, Date fechaDesde, Date fechaHasta, UsuarioEt usuario, EstadoCheckListEnum estadoCheckList)
			throws EntidadNoEncontradaException;

}
