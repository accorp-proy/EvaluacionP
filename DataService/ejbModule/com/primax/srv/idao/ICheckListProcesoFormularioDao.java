package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.CheckListProcesoEt;
import com.primax.jpa.pla.CheckListProcesoFormularioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ICheckListProcesoFormularioDao extends IGenericDao<CheckListProcesoFormularioEt, Long> {

	public void remove();

	public List<CheckListProcesoFormularioEt> getCheckListFormularioByProceso(CheckListProcesoEt checkListProceso);

	public void guardarCheckListProcesoFormulario(CheckListProcesoFormularioEt checkListProcesoFormulario, UsuarioEt usuario)
			throws EntidadNoGrabadaException;

}
