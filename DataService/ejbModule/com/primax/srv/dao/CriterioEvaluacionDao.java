package com.primax.srv.dao;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.CriterioEvaluacionEt;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.param.ProcesoDetalleEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ICriterioEvaluacionDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class CriterioEvaluacionDao extends GenericDao<CriterioEvaluacionEt, Long> implements ICriterioEvaluacionDao {

	public CriterioEvaluacionDao() {
		super(CriterioEvaluacionEt.class);
	}

	private StringBuilder sql;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarCriterioEvaluacion(CriterioEvaluacionEt criterioEvaluacion, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (criterioEvaluacion.getIdCriterioEvaluacion() == null) {
			criterioEvaluacion.audit(usuario, ActionAuditedEnum.NEW);
			crear(criterioEvaluacion);
		} else {
			criterioEvaluacion.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(criterioEvaluacion);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CriterioEvaluacionEt> getCriterioEvaluacionList(EvaluacionEt evaluacion, TipoChecKListEt tipoChecKList, String condicion)
			throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM CriterioEvaluacionEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		if (evaluacion != null) {
			sql.append(" AND o.evaluacion = :evaluacion ");
		}
		if (tipoChecKList != null) {
			sql.append(" AND o.tipoChecKList = :tipoChecKList ");
		}
		if (condicion != null && !condicion.isEmpty()) {
			sql.append(" AND upper(o.nombre) like :condicion ");
		}
		sql.append(" ORDER BY o.idCriterioEvaluacion ");
		TypedQuery<CriterioEvaluacionEt> query = em.createQuery(sql.toString(), CriterioEvaluacionEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		if (evaluacion != null) {
			query.setParameter("evaluacion", evaluacion);
		}
		if (tipoChecKList != null) {
			query.setParameter("tipoChecKList", tipoChecKList);
		}
		if (condicion != null && !condicion.isEmpty()) {
			query.setParameter("condicion", "%" + condicion.toUpperCase() + "%");
		}
		List<CriterioEvaluacionEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CriterioEvaluacionEt> getCriterioEvaluacionBPMList() throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM CriterioEvaluacionEt o ");
		sql.append(" WHERE o.estado  = :estado    ");
		sql.append(" AND o.procesoDetalle is null ");
		sql.append(" ORDER BY o.idCriterioEvaluacion ");
		TypedQuery<CriterioEvaluacionEt> query = em.createQuery(sql.toString(), CriterioEvaluacionEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		List<CriterioEvaluacionEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CriterioEvaluacionEt getCriterioEvaluacionByProcesoDetalle(ProcesoDetalleEt procesoDetalle) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM CriterioEvaluacionEt o ");
		sql.append(" WHERE o.estado  = :estado    ");
		sql.append(" AND o.procesoDetalle = :procesoDetalle ");
		TypedQuery<CriterioEvaluacionEt> query = em.createQuery(sql.toString(), CriterioEvaluacionEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("procesoDetalle", procesoDetalle);
		List<CriterioEvaluacionEt> result = query.getResultList();
		CriterioEvaluacionEt criterioEvaluacion = null;
		criterioEvaluacion = getUnique(result);
		if (criterioEvaluacion != null) {
			criterioEvaluacion.getCriterioEvaluacionDetalle().size();
		}
		return criterioEvaluacion;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CriterioEvaluacionEt getCriterioEvaluacion(long id) {
		try {
			CriterioEvaluacionEt criterioEvaluacion = recuperar(id);
			if (criterioEvaluacion.getCriterioEvaluacionDetalle() != null) {
				criterioEvaluacion.getCriterioEvaluacionDetalle().size();
			}
			return criterioEvaluacion;
		} catch (EntidadNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
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
