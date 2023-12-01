package com.primax.srv.dao;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IEvaluacionDao;
import com.primax.srv.util.QUL;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class EvaluacionDao extends GenericDao<EvaluacionEt, Long> implements IEvaluacionDao {

	public EvaluacionDao() {
		super(EvaluacionEt.class);
	}

	private StringBuilder sql;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarEvaluacion(EvaluacionEt evaluacion, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (evaluacion.getIdEvaluacion() == null) {
			evaluacion.audit(usuario, ActionAuditedEnum.NEW);
			crear(evaluacion);
		} else {
			evaluacion.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(evaluacion);
		}
		em.flush();
		em.clear();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public EvaluacionEt getEvaluacion(long id) {
		try {
			EvaluacionEt evaluacion = recuperar(id);
			return evaluacion;
		} catch (EntidadNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<EvaluacionEt> getEvaluacionList(String condicion) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM EvaluacionEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		if (condicion != null && !condicion.isEmpty()) {
			sql.append(" AND o.descripcion like :condicion ");
		}
		sql.append(" ORDER BY o.idEvaluacion ");
		TypedQuery<EvaluacionEt> query = em.createQuery(sql.toString(), EvaluacionEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		if (condicion != null && !condicion.isEmpty()) {
			query.setParameter("condicion", "%" + QUL.getString(condicion) + "%");
		}
		List<EvaluacionEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<EvaluacionEt> getEvaluacionByCriterio(boolean criterio) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM EvaluacionEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		sql.append(" AND o.criterio  = :criterio ");
		sql.append(" ORDER BY o.idEvaluacion ");
		TypedQuery<EvaluacionEt> query = em.createQuery(sql.toString(), EvaluacionEt.class);
		query.setParameter("criterio", criterio);
		query.setParameter("estado", EstadoEnum.ACT);
		List<EvaluacionEt> result = query.getResultList();
		return result;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String limpiarReporte(Long idUsuario) {
		StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("getReporteEvaluacionConsolidado");
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
