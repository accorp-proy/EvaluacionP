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
import com.primax.jpa.param.CriterioEvaluacionDetalleEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ICriterioEvaluacionDetalleDao;
import com.primax.srv.util.QUL;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class CriterioEvaluacionDetalleDao extends GenericDao<CriterioEvaluacionDetalleEt, Long> implements ICriterioEvaluacionDetalleDao {

	public CriterioEvaluacionDetalleDao() {
		super(CriterioEvaluacionDetalleEt.class);
	}

	private StringBuilder sql;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarCriterioEvaluacionDetalle(CriterioEvaluacionDetalleEt criterioEvaluacionDetalle, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (criterioEvaluacionDetalle.getIdCriterioEvaluacionDetalle() == null) {
			criterioEvaluacionDetalle.audit(usuario, ActionAuditedEnum.NEW);
			crear(criterioEvaluacionDetalle);
		} else {
			criterioEvaluacionDetalle.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(criterioEvaluacionDetalle);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CriterioEvaluacionDetalleEt> getCriterioEvaluacionDetalleList(String condicion) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM CriterioEvaluacionDetalleEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		if (condicion != null && !condicion.isEmpty()) {
			sql.append(" AND o.nombre like :condicion ");
		}
		TypedQuery<CriterioEvaluacionDetalleEt> query = em.createQuery(sql.toString(), CriterioEvaluacionDetalleEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		if (condicion != null && !condicion.isEmpty()) {
			query.setParameter("condicion", "%" + QUL.getString(condicion) + "%");
		}
		List<CriterioEvaluacionDetalleEt> result = query.getResultList();
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CriterioEvaluacionDetalleEt getCriterioEvaluacionDetalle(long id) {
		try {
			CriterioEvaluacionDetalleEt criterioEvaluacionDetalle = recuperar(id);
			return criterioEvaluacionDetalle;
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
