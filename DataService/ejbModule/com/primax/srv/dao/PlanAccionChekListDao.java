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
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.pla.PlanAccionChekListEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IPlanAccionChekListDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class PlanAccionChekListDao extends GenericDao<PlanAccionChekListEt, Long> implements IPlanAccionChekListDao {

	public PlanAccionChekListDao() {
		super(PlanAccionChekListEt.class);
	}

	private StringBuilder sql;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarPlanAccionChekList(PlanAccionChekListEt planAccionChekList, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (planAccionChekList.getIdCheckListEjecucion() == null) {
			planAccionChekList.audit(usuario, ActionAuditedEnum.NEW);
			crear(planAccionChekList);
		} else {
			planAccionChekList.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(planAccionChekList);
		}
		em.flush();
		em.clear();
	}

	@Override
	public List<PlanAccionChekListEt> getPlanAccionChekListList(Long idPlanAccionAnio, Long anio, Long mes, Long idZona,Long idAgencia) {
		sql = new StringBuilder("FROM PlanAccionChekListEt o ");
		sql.append(" WHERE o.estado = :estado ");
		sql.append(" AND o.anio = :anio ");
		sql.append(" AND o.mes  = :mes ");
		sql.append(" AND o.idZona = :idZona ");
		sql.append(" AND o.idAgencia = :idAgencia ");
		sql.append(" AND o.idPlanAccionAnio = :idPlanAccionAnio ");
		TypedQuery<PlanAccionChekListEt> query = em.createQuery(sql.toString(), PlanAccionChekListEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("anio", anio);
		query.setParameter("mes", mes);
		query.setParameter("idZona", idZona);
		query.setParameter("idAgencia", idAgencia);
		query.setParameter("idPlanAccionAnio", idPlanAccionAnio);
		List<PlanAccionChekListEt> result = query.getResultList();
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
