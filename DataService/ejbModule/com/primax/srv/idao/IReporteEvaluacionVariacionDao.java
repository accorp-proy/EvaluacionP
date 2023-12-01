package com.primax.srv.idao;

import com.primax.jpa.pla.ReporteEvaluacionVariacionEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IReporteEvaluacionVariacionDao extends IGenericDao<ReporteEvaluacionVariacionEt, Long> {

	public void remove();

	public String generar(Long anio, Long idUsuario);

}
