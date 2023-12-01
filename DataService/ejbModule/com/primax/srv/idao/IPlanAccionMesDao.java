package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.PlanAccionMesEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IPlanAccionMesDao extends IGenericDao<PlanAccionMesEt, Long> {

	public void remove();

	public String generar(Long idUsuario, Long anio, Long idPlanAccionAnio);

	public List<PlanAccionMesEt> getPlanAccionMesList(Long idPlanAccionAnio,Long anio) ;

	public void guardarPlanAccionMes(PlanAccionMesEt planAccionMes, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
