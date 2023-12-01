package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.CheckListEjecucionAdjuntoEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ICheckListEjecucionAdjuntoDao extends IGenericDao<CheckListEjecucionAdjuntoEt, Long> {

	public void remove();

	public CheckListEjecucionAdjuntoEt getCheckListEjecucionAdjunto(long id);

	public List<CheckListEjecucionAdjuntoEt> getAdjunto(Long idCheckListEjecucion) throws EntidadNoEncontradaException;

	public void guardarCheckListEjecucionAdjunto(CheckListEjecucionAdjuntoEt checkListEjecucionAdjunto, UsuarioEt usuario)
			throws EntidadNoGrabadaException;

}
