package com.primax.srv.dao;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TemporalType;

import com.primax.jpa.pla.ReporteProcesoConsolidadoEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IReporteProcesoConsolidadoDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class ReporteProcesoConsolidadoDao extends GenericDao<ReporteProcesoConsolidadoEt, Long> implements IReporteProcesoConsolidadoDao {

	public ReporteProcesoConsolidadoDao() {
		super(ReporteProcesoConsolidadoEt.class);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String generar(Date fechaDesde, Date fechaHasta, Long idZona, Long idEvaluacion, Long idTipoCheckList, Long idNivelEvaluacion, Long idNivelEvaluacionD, Long idUsuario) {
		StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("getGenerarReporteProcesoConsolidado");
		query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
		query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
		query.setParameter("idZona", idZona);
		query.setParameter("idEvaluacion", idEvaluacion);
		query.setParameter("idTipoCheckList", idTipoCheckList);
		query.setParameter("idNivelEvaluacion", idNivelEvaluacion);
		query.setParameter("idNivelEvaluacionD", idNivelEvaluacionD);
		query.setParameter("idUsuario", idUsuario);
		String respuesta = (String) query.getOutputParameterValue("respuesta");
		return respuesta;
	}

	@Remove
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void remove() {
		System.out.println("Finalizado Statefull Bean : " + this.getClass().getCanonicalName());
	}

	@PreDestroy
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void detached() {
		System.out.println("Terminado Statefull Bean : " + this.getClass().getCanonicalName());
	}

}
