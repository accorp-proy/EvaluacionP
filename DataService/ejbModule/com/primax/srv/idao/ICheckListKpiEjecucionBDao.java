package com.primax.srv.idao;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.CheckListKpiEjecucionBEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ICheckListKpiEjecucionBDao extends IGenericDao<CheckListKpiEjecucionBEt, Long> {

	public void remove();

	public CheckListKpiEjecucionBEt getCheckListKpiEjecucionB(long id);

	public void guardaCheckListKpiEjecucionB(CheckListKpiEjecucionBEt checkListKpiEjecucionB, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
