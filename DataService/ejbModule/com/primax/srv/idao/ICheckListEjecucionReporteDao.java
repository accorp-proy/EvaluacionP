package com.primax.srv.idao;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.CheckListEjecucionReporteEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ICheckListEjecucionReporteDao extends IGenericDao<CheckListEjecucionReporteEt, Long> {
	
	public void remove();

	public void guardarCheckListEjecucionReporte(CheckListEjecucionReporteEt checkListEjecucionReporte, UsuarioEt usuario)
			throws EntidadNoGrabadaException;

}
