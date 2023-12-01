package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.ParametrosGeneralesEt;
import com.primax.jpa.pla.CheckListEtiquetaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ICheckListEtiquetaDao extends IGenericDao<CheckListEtiquetaEt, Long> {

	public void remove();

	public List<CheckListEtiquetaEt> getCheckListEtiquetaList() throws EntidadNoEncontradaException;

	public List<CheckListEtiquetaEt> getCheckListEtiquetaList(ParametrosGeneralesEt parSeccion) throws EntidadNoEncontradaException;

	public void guardarCheckListEtiqueta(CheckListEtiquetaEt checkListEtiqueta, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
