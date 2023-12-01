package com.primax.srv.idao;

import java.util.Date;

import com.primax.jpa.pla.ReporteProcesoConsolidadoEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IReporteProcesoConsolidadoDao extends IGenericDao<ReporteProcesoConsolidadoEt, Long> {

	public void remove();

	public String generar(Date fechaDesde, Date fechaHasta, Long idZona, Long idEvaluacion, Long idTipoCheckList, Long idNivelEvaluacion,Long idNivelEvaluacionD, Long idUsuario);

}
