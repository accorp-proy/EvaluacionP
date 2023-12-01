package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.CheckListEt;
import com.primax.jpa.pla.CheckListProcesoEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ICheckListProcesoDao extends IGenericDao<CheckListProcesoEt, Long> {

	public void remove();

	public CheckListProcesoEt getCheckListProceso(long id);

	public List<CheckListProcesoEt> getChilds(CheckListEt checkList);

	public void guardarCheckListProceso(CheckListProcesoEt checkListProceso, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
