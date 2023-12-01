package com.primax.srv.idao;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.CheckListKpiEjecucionCEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ICheckListKpiEjecucionCDao extends IGenericDao<CheckListKpiEjecucionCEt, Long> {

	public void remove();

	public String limpiarReporte(Long idCheckListProcesoEjecucion);

	public void guardaCheckListKpiEjecucionC(CheckListKpiEjecucionCEt checkListKpiEjecucionC, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
