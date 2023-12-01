package com.primax.srv.idao;

import java.util.Date;
import java.util.List;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.PlanAccionAnioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IPlanAccionAnioDao extends IGenericDao<PlanAccionAnioEt, Long> {

	public void remove();

	public List<PlanAccionAnioEt> getPlanAccionAnioList();

	public String generar(Long anio, Date fechaDesde, Date fechaHasta, Long idZona,Long idEstacion, Long idEvaluacion, Long idUsuario);

	public void guardarPlanAccionAnio(PlanAccionAnioEt planAccionAnio, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
