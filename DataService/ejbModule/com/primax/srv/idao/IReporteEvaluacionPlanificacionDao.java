package com.primax.srv.idao;

import java.util.Date;

import com.primax.jpa.enums.EstadoCheckListEnum;
import com.primax.jpa.pla.ReporteEvaluacionPlanificacionEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IReporteEvaluacionPlanificacionDao extends IGenericDao<ReporteEvaluacionPlanificacionEt, Long> {

	public void remove();

	public String generar(Date fechaDesde, Date fechaHasta, Long idZona, Long idEvaluacion, Long idFrecuenciaVisita, EstadoCheckListEnum estadoCheckList, Long idUsuario);

}
