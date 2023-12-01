package com.primax.srv.idao;

import java.util.Date;

import com.primax.jpa.pla.ReporteEvaluacionNivelRiesgoEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IReporteEvaluacionNivelRiesgoDao extends IGenericDao<ReporteEvaluacionNivelRiesgoEt, Long> {

	public void remove();

	public String generar(Date fechaDesde, Date fechaHasta, Long idZona, Long idEvaluacion, Long idNivelEvaluacion, Long idNivelEvaluacionD, Long idUsuario) ;

}
