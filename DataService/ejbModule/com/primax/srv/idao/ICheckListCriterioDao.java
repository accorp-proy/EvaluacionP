package com.primax.srv.idao;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.CheckListCriterioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ICheckListCriterioDao extends IGenericDao<CheckListCriterioEt, Long> {

	public void remove();

	public CheckListCriterioEt getCheckListCriterioById(long id);

	public void guardarCheckListCriterio(CheckListCriterioEt checkListCriterio, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
