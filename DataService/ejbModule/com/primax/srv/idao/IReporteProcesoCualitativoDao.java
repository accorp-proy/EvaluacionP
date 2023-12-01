package com.primax.srv.idao;

import java.util.Date;

import com.primax.jpa.pla.ReporteProcesoCualitativoEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IReporteProcesoCualitativoDao extends IGenericDao<ReporteProcesoCualitativoEt, Long> {

	public void remove();

	public String generar(Date fechaDesde, Date fechaHasta, Long idZona, Long idEvaluacion, Long idTipoCheckList, Long idNivelEvaluacion, Long idNivelEvaluacionD, Long idUsuario);

}
