package com.primax.srv.idao;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.CheckListProcesoEjecucionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ICheckListProcesoEjecucionDao extends IGenericDao<CheckListProcesoEjecucionEt, Long> {

	public void remove();

	public CheckListProcesoEjecucionEt getCheckListProcesoE(long id);

	public void guardarCheckListProcesoEjecucion(CheckListProcesoEjecucionEt checkListProcesoEjecucion, UsuarioEt usuario)
			throws EntidadNoGrabadaException;

}
