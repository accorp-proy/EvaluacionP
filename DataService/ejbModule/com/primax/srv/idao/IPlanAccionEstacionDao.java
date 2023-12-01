package com.primax.srv.idao;

import java.util.List;

import com.primax.jpa.pla.PlanAccionEstacionEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IPlanAccionEstacionDao extends IGenericDao<PlanAccionEstacionEt, Long> {

	public void remove();

	public List<PlanAccionEstacionEt> getPlanAccionMesList(Long idPlanAccionAnio, Long anio, Long mes, Long idZona);

}
