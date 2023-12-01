package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.PlanAccionChekListEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IPlanAccionChekListDao extends IGenericDao<PlanAccionChekListEt, Long> {

	public void remove();

	public void guardarPlanAccionChekList(PlanAccionChekListEt planAccionChekList, UsuarioEt usuario) throws EntidadNoGrabadaException;

	public List<PlanAccionChekListEt> getPlanAccionChekListList(Long idPlanAccionAnio, Long anio, Long mes, Long idZona, Long idAgencia);

}
