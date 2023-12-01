package com.primax.srv.idao;

import java.util.Date;

import com.primax.jpa.pla.ReporteEvaluacionConsolidadoEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IReporteEvaluacionConsolidadoDao extends IGenericDao<ReporteEvaluacionConsolidadoEt, Long> {

	public void remove();

	public String generar(Date fechaDesde, Date fechaHasta, Long idZona, Long idEvaluacion, Long idNivelEvaluacion, Long idNivelEvaluacionD, Long idUsuario) ;

}
