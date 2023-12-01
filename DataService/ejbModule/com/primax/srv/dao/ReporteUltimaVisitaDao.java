package com.primax.srv.dao;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.pla.ReporteUltimaVisitaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IReporteUltimaVisitaDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class ReporteUltimaVisitaDao extends GenericDao<ReporteUltimaVisitaEt, Long> implements IReporteUltimaVisitaDao {

	public ReporteUltimaVisitaDao() {
		super(ReporteUltimaVisitaEt.class);
	}

	private StringBuilder sql;

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ReporteUltimaVisitaEt> getReporteUltimaVisitaList(UsuarioEt usuario) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM ReporteUltimaVisitaEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		sql.append(" AND o.usuarioRegistra = :usuario ");
		sql.append(" ORDER BY o.idReporteUltimaVisita DESC ");
		TypedQuery<ReporteUltimaVisitaEt> query = em.createQuery(sql.toString(), ReporteUltimaVisitaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("usuario", usuario);
		List<ReporteUltimaVisitaEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ReporteUltimaVisitaEt getReporteUltimaVisita(UsuarioEt usuario) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM ReporteUltimaVisitaEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		sql.append(" AND o.usuarioRegistra = :usuario ");
		sql.append(" ORDER BY o.idReporteUltimaVisita DESC ");
		TypedQuery<ReporteUltimaVisitaEt> query = em.createQuery(sql.toString(), ReporteUltimaVisitaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("usuario", usuario);
		query.setMaxResults(1);
		List<ReporteUltimaVisitaEt> result = query.getResultList();
		return getUnique(result);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String generar(Date fechaDesde, Date fechaHasta, Long idZona, Long idTipoCheckList, Long idEvaluacion, Long idAgencia, Long idProceso, Long idUsuario) {
		StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("getGenerarReporteUltimaVisita");
		query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
		query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
		query.setParameter("idZona", idZona);
		query.setParameter("idTipoCheckList", idTipoCheckList);
		query.setParameter("idEvaluacion", idEvaluacion);
		query.setParameter("idAgencia", idAgencia);
		query.setParameter("idProceso", idProceso);
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
