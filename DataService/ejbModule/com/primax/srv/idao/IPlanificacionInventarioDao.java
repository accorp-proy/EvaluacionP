package com.primax.srv.idao;

import java.util.Date;
import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.PlanificacionInventarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IPlanificacionInventarioDao extends IGenericDao<PlanificacionInventarioEt, Long> {

	public void remove();

	public PlanificacionInventarioEt getPlanificacionInventarioById(long id);

	public List<PlanificacionInventarioEt> getPlanificacionInventarioList(Date fechaDesde, Date fechaHasta) throws EntidadNoEncontradaException;

	public void guardarPlanificacionInventario(PlanificacionInventarioEt planificacionInventario, UsuarioEt usuario) throws EntidadNoGrabadaException;

	public List<PlanificacionInventarioEt> getPlanificacionInventarioList(Date fechaDesde, Date fechaHasta, UsuarioEt usuario)
			throws EntidadNoEncontradaException;

}
