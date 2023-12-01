package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.CheckListEjecucionEt;
import com.primax.jpa.pla.CheckListEjecucionFirmaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ICheckListEjecucionFirmaDao extends IGenericDao<CheckListEjecucionFirmaEt, Long> {

	public void remove();

	public CheckListEjecucionFirmaEt getCheckListEjecucionFirma(long id);

	public void eliminarCheckListEjecucionFirma(List<CheckListEjecucionFirmaEt> checkListEjecucionFirmas, UsuarioEt usuario)
			throws EntidadNoGrabadaException;

	public void eliminarCheckListEjecucionFirmaIndividual(CheckListEjecucionFirmaEt checkListEjecucionFirma, UsuarioEt usuario)
			throws EntidadNoGrabadaException;

	public void guardarCheckListEjecucionFirma(CheckListEjecucionFirmaEt checkListEjecucionFirma, UsuarioEt usuario) throws EntidadNoGrabadaException;

	public List<CheckListEjecucionFirmaEt> getCheckListEjecucionFirmaByChekListList(CheckListEjecucionEt checkListEjecucion)
			throws EntidadNoEncontradaException;
}
