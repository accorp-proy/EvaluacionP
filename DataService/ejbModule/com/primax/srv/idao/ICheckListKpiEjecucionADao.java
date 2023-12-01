package com.primax.srv.idao;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.CheckListKpiEjecucionAEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ICheckListKpiEjecucionADao extends IGenericDao<CheckListKpiEjecucionAEt, Long> {

	public void remove();

	public CheckListKpiEjecucionAEt getCheckListKpiEjecucionA(long id);

	public void guardaCheckListKpiEjecucion(CheckListKpiEjecucionAEt checkListKpiEjecucionA, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
