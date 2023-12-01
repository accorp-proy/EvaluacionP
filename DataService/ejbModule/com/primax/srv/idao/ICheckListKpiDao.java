package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.CheckListKpiEt;
import com.primax.jpa.pla.CheckListProcesoEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ICheckListKpiDao extends IGenericDao<CheckListKpiEt, Long> {

	public void remove();

	public CheckListKpiEt getCheckListKpi(long id);

	public List<CheckListKpiEt> getCheckListByVisualizar(CheckListProcesoEt proceso);

	public void guardarCheckListKpi(CheckListKpiEt checkListKpi, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
