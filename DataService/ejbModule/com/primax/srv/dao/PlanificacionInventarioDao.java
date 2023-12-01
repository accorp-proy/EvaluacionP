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
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.pla.PlanificacionInventarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IPlanificacionInventarioDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class PlanificacionInventarioDao extends GenericDao<PlanificacionInventarioEt, Long> implements IPlanificacionInventarioDao {

	public PlanificacionInventarioDao() {
		super(PlanificacionInventarioEt.class);
	}

	private StringBuilder sql;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarPlanificacionInventario(PlanificacionInventarioEt planificacionInventario, UsuarioEt usuario)
			throws EntidadNoGrabadaException {
		if (planificacionInventario.getIdPlanificacionInventario() == null) {
			planificacionInventario.audit(usuario, ActionAuditedEnum.NEW);
			crear(planificacionInventario);
		} else {
			planificacionInventario.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(planificacionInventario);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PlanificacionInventarioEt> getPlanificacionInventarioList(Date fechaDesde, Date fechaHasta) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM PlanificacionInventarioEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		sql.append(" AND date_trunc('day',o.fechaPlanificacion) BETWEEN :fDesde AND :fHasta ");
		sql.append(" ORDER BY o.fechaPlanificacion  ");
		TypedQuery<PlanificacionInventarioEt> query = em.createQuery(sql.toString(), PlanificacionInventarioEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("fDesde", fechaDesde, TemporalType.DATE);
		query.setParameter("fHasta", fechaHasta, TemporalType.DATE);
		List<PlanificacionInventarioEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PlanificacionInventarioEt> getPlanificacionInventarioList(Date fechaDesde, Date fechaHasta, UsuarioEt usuario)
			throws EntidadNoEncontradaException {
		sql = new StringBuilder("SELECT DISTINCT(o.planificacionInventario) FROM PlanificacionResponsableEt o ");
		sql.append(" WHERE o.usuarioResponsable  = :usuario   ");
		sql.append(" AND date_trunc('day',o.planificacionInventario.fechaPlanificacion) BETWEEN :fDesde AND :fHasta ");
		sql.append(" AND o.estado  = :estado ");
		sql.append(" AND o.planificacionInventario.estado = :estado ");
		TypedQuery<PlanificacionInventarioEt> query = em.createQuery(sql.toString(), PlanificacionInventarioEt.class);
		query.setParameter("usuario", usuario);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("fDesde", fechaDesde, TemporalType.DATE);
		query.setParameter("fHasta", fechaHasta, TemporalType.DATE);
		List<PlanificacionInventarioEt> result = query.getResultList();
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PlanificacionInventarioEt getPlanificacionInventarioById(long id) {
		try {
			PlanificacionInventarioEt planificacionInventario = recuperar(id);
			planificacionInventario.getPlanificacionResponsable().size();
			planificacionInventario.getPlanificacionParticipante().size();
			planificacionInventario.getPlanificacionInventarioTipo().size();
			return planificacionInventario;
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
