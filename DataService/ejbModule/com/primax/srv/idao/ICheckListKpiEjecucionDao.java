package com.primax.srv.idao;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.CriterioEvaluacionDetalleEt;
import com.primax.jpa.param.KPICriticoEt;
import com.primax.jpa.pla.CheckListEjecucionEt;
import com.primax.jpa.pla.CheckListKpiEjecucionEt;
import com.primax.jpa.pla.CheckListProcesoEjecucionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ICheckListKpiEjecucionDao extends IGenericDao<CheckListKpiEjecucionEt, Long> {

	public void remove();

	public CheckListKpiEjecucionEt getCheckListKpiEjecucion(long id);

	public void guardarCheckListKpiEjecucion(CheckListKpiEjecucionEt checkListKpiEjecucion, UsuarioEt usuario) throws EntidadNoGrabadaException;

	public boolean getExisteCondicionCritica(CheckListEjecucionEt checkListEjecucion, KPICriticoEt kPICritico, CriterioEvaluacionDetalleEt criterioEvaluacionDetalle);

	public boolean getExisteCondicionCriticaFoco(CheckListProcesoEjecucionEt checkListProcesoEjecucion, KPICriticoEt kPICritico, CriterioEvaluacionDetalleEt criterioEvaluacionDetalle);

}
