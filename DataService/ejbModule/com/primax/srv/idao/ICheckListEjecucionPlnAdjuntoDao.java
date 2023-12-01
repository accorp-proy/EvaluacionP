package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.CheckListEjecucionPlnAdjuntoEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ICheckListEjecucionPlnAdjuntoDao extends IGenericDao<CheckListEjecucionPlnAdjuntoEt, Long> {

	public void remove();

	public List<CheckListEjecucionPlnAdjuntoEt> getAdjuntoList(Long idCheckListEjecucion);

	public void guardarCheckListEjecucionPlnAdjunto(CheckListEjecucionPlnAdjuntoEt checkListEjecucionPlnAdjunto, UsuarioEt usuario)
			throws EntidadNoGrabadaException;

}
