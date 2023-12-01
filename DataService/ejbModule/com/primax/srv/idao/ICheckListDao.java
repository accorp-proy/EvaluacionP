package com.primax.srv.idao;

import java.util.Date;
import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoCheckListEnum;
import com.primax.jpa.enums.TipoCheckListEnum;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.param.NivelEvaluacionEt;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.pla.CheckListEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ICheckListDao extends IGenericDao<CheckListEt, Long> {

	public void remove();

	public CheckListEt getCheckList(long id);

	public List<CheckListEt> getCheckListChild0();

	public List<CheckListEt> getCheckListChild(AgenciaEt agencia);

	public void guardarCheckList(CheckListEt checkList, UsuarioEt usuario) throws EntidadNoGrabadaException;

	public CheckListEt getCheck(TipoCheckListEnum tipoCheckList, String codigo) throws EntidadNoEncontradaException;

	public CheckListEt getCheckPendiente(TipoCheckListEnum tipoCheckList, UsuarioEt usuario) throws EntidadNoEncontradaException;

	public List<CheckListEt> getCheckList(NivelEvaluacionEt nivelEvaluacion, EvaluacionEt evaluacion, TipoChecKListEt tipoChecKList, Date fechaDesde, Date fechaHasta, EstadoCheckListEnum estadoCheckListEnum)
			throws EntidadNoEncontradaException;

	public List<CheckListEt> getCheckListBusqueda(EvaluacionEt evaluacion, TipoChecKListEt tipoChecKList, EstadoCheckListEnum estadoCheckListEnum)
			throws EntidadNoEncontradaException;

}
