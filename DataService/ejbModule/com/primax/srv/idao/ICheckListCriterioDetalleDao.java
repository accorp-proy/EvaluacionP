package com.primax.srv.idao;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.CheckListCriterioDetalleEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ICheckListCriterioDetalleDao extends IGenericDao<CheckListCriterioDetalleEt, Long> {

	public void remove();

	public CheckListCriterioDetalleEt getCheckListCriterioDetalle(long id);

	public void guardarCheckListCriterioDetalle(CheckListCriterioDetalleEt checkListCriterioDetalle, UsuarioEt usuario)
			throws EntidadNoGrabadaException;

}
