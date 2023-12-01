package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.CheckListKpiEjecucionFirmaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ICheckListKpiEjecucionFirmaDao extends IGenericDao<CheckListKpiEjecucionFirmaEt, Long> {

	public void remove();

	public CheckListKpiEjecucionFirmaEt getCheckListKpiEjecucionFirma(long id);

	public void eliminarCheckListEjecucionFirma(List<CheckListKpiEjecucionFirmaEt> checkListKpiEjecucionFirmas, UsuarioEt usuario)
			throws EntidadNoGrabadaException;

	public void guardarCheckListKpiEjecucionFirma(CheckListKpiEjecucionFirmaEt checkListKpiEjecucionFirma, UsuarioEt usuario)
			throws EntidadNoGrabadaException;

}
