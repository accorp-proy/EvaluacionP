package com.primax.srv.idao;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.CheckListInformeCabeceraEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ICheckListInformeCabeceraDao extends IGenericDao<CheckListInformeCabeceraEt, Long> {

	public void remove();

	public Long getOrdenCab(Long idCheckList) throws EntidadNoEncontradaException;

	public void guardarCheckListInformeCabecera(CheckListInformeCabeceraEt checkListInformeCabecera, UsuarioEt usuario)
			throws EntidadNoGrabadaException;

}
