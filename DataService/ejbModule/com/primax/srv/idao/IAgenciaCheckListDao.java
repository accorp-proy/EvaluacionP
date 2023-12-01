package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.AgenciaCheckListEt;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.param.NivelEvaluacionEt;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.pla.CheckListEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IAgenciaCheckListDao extends IGenericDao<AgenciaCheckListEt, Long> {

	public void remove();

	public AgenciaCheckListEt getAgenciaCheckList(long id);

	public AgenciaCheckListEt getAgenciaCheckList(AgenciaEt agencia, CheckListEt checkList);

	public List<AgenciaCheckListEt> getAgenciaCheckListByAgenciaList(AgenciaEt agencia) throws EntidadNoEncontradaException;

	public void guardaAgenciaCheckList(AgenciaCheckListEt agenciaCheckList, UsuarioEt usuario) throws EntidadNoGrabadaException;

	public List<AgenciaCheckListEt> getAgenciaCheckListHabilitados(AgenciaEt agencia, NivelEvaluacionEt nivelEvaluacion, EvaluacionEt evaluacion, TipoChecKListEt tipoChecKList)
			throws EntidadNoEncontradaException;

}
