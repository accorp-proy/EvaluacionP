package com.primax.srv.idao;

import java.util.Date;

import com.primax.jpa.pla.ReporteTipoEvaluacionEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IReporteTipoEvaluacionDao extends IGenericDao<ReporteTipoEvaluacionEt, Long> {

	public void remove();

	public String generar(Date fechaDesde, Date fechaHasta, Long idZona, Long idTipoCheckList, Long idEvaluacion, Long idAgencia, Long idNivelEvaluacion, Long idNivelEvaluacionD, Long idUsuario);

}
