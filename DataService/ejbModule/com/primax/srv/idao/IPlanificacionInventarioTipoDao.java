package com.primax.srv.idao;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.TipoInventarioEt;
import com.primax.jpa.pla.PlanificacionInventarioEt;
import com.primax.jpa.pla.PlanificacionInventarioTipoEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IPlanificacionInventarioTipoDao extends IGenericDao<PlanificacionInventarioTipoEt, Long> {

	public void remove();

	public Long getPlaInvList(PlanificacionInventarioEt planificacionInv) throws EntidadNoEncontradaException;

	public void guardarPlanificacionInv(PlanificacionInventarioTipoEt planificacionInv, UsuarioEt usuario) throws EntidadNoGrabadaException;

	public PlanificacionInventarioTipoEt getPlanificacionInv(PlanificacionInventarioEt planificacionInv, TipoInventarioEt tipoInv)
			throws EntidadNoEncontradaException;

}
