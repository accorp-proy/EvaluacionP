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
import com.primax.jpa.param.TipoInventarioEt;
import com.primax.jpa.pla.PlanificacionInventarioEt;
import com.primax.jpa.pla.PlanificacionInventarioTipoEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IPlanificacionInventarioTipoDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class PlanificacionInventarioTipoDao extends GenericDao<PlanificacionInventarioTipoEt, Long> implements IPlanificacionInventarioTipoDao {

	public PlanificacionInventarioTipoDao() {
		super(PlanificacionInventarioTipoEt.class);
	}

	private StringBuilder sql;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarPlanificacionInv(PlanificacionInventarioTipoEt planificacionInv, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (planificacionInv.getIdPlanificacionInventarioTipo() == null) {
			planificacionInv.audit(usuario, ActionAuditedEnum.NEW);
			crear(planificacionInv);
		} else {
			planificacionInv.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(planificacionInv);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PlanificacionInventarioTipoEt getPlanificacionInv(PlanificacionInventarioEt planificacionInv, TipoInventarioEt tipoInv)
			throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM PlanificacionInventarioTipoEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		sql.append(" AND o.tipoInventario  = :tipoInventario ");
		sql.append(" AND o.planificacionInventario  = :planificacionInventario ");
		TypedQuery<PlanificacionInventarioTipoEt> query = em.createQuery(sql.toString(), PlanificacionInventarioTipoEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("tipoInventario", tipoInv);
		query.setParameter("planificacionInventario", planificacionInv);
		List<PlanificacionInventarioTipoEt> result = query.getResultList();
		return getUnique(result);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long getPlaInvList(PlanificacionInventarioEt planificacionInv) throws EntidadNoEncontradaException {
		Long ejecutada = 0L;
		sql = new StringBuilder("FROM PlanificacionInventarioTipoEt o ");
		sql.append(" WHERE o.estado  = :estado ");
		sql.append(" AND o.ejecutado = 'true' ");
		sql.append(" AND o.planificacionInventario  = :planificacionInventario ");
		TypedQuery<PlanificacionInventarioTipoEt> query = em.createQuery(sql.toString(), PlanificacionInventarioTipoEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("planificacionInventario", planificacionInv);
		List<PlanificacionInventarioTipoEt> result = query.getResultList();
		if (getUnique(result) != null) {
			ejecutada = (long) result.size();
		}
		return ejecutada;
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
