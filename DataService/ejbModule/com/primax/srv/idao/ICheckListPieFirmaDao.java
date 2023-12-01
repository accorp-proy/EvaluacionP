package com.primax.srv.idao;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.CheckListPieFirmaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ICheckListPieFirmaDao extends IGenericDao<CheckListPieFirmaEt, Long> {

	public void remove();

	public Long getOrdenPie(Long idCheckList) throws EntidadNoEncontradaException;

	public void guardarCheckListPieFirma(CheckListPieFirmaEt checkListPieFirma, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
