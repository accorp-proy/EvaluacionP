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

import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.pla.PlanAccionEstacionEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IPlanAccionEstacionDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class PlanAccionEstacionDao extends GenericDao<PlanAccionEstacionEt, Long> implements IPlanAccionEstacionDao {

	public PlanAccionEstacionDao() {
		super(PlanAccionEstacionEt.class);
	}

	private StringBuilder sql;

	@Override
	public List<PlanAccionEstacionEt> getPlanAccionMesList(Long idPlanAccionAnio, Long anio, Long mes, Long idZona) {
		sql = new StringBuilder("FROM PlanAccionEstacionEt o ");
		sql.append(" WHERE o.estado = :estado ");
		sql.append(" AND o.anio = :anio ");
		sql.append(" AND o.mes  = :mes  ");
		sql.append(" AND o.idZona = :idZona ");
		sql.append(" AND o.idPlanAccionAnio = :idPlanAccionAnio ");
		TypedQuery<PlanAccionEstacionEt> query = em.createQuery(sql.toString(), PlanAccionEstacionEt.class);
		query.setParameter("anio", anio);
		query.setParameter("mes", mes);
		query.setParameter("idZona", idZona);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("idPlanAccionAnio", idPlanAccionAnio);
		List<PlanAccionEstacionEt> result = query.getResultList();
		return result;
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
