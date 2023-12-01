package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.PlanAccionZonaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IPlanAccionZonaDao extends IGenericDao<PlanAccionZonaEt, Long> {

	public void remove();

	public String generar(Long idUsuario, Long anio, Long idPlanAccionAnio, Long mes);

	public List<PlanAccionZonaEt> getPlanAccionZonaList(Long idPlanAccionAnio, Long anio, Long mes);

	public void guardarPlanAccionMes(PlanAccionZonaEt planAccionZona, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
