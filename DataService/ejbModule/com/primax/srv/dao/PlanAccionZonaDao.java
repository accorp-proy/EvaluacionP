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
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.pla.PlanAccionZonaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IPlanAccionZonaDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class PlanAccionZonaDao extends GenericDao<PlanAccionZonaEt, Long> implements IPlanAccionZonaDao {

	public PlanAccionZonaDao() {
		super(PlanAccionZonaEt.class);
	}

	private StringBuilder sql;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarPlanAccionMes(PlanAccionZonaEt planAccionZona, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (planAccionZona.getIdPlanAccionZona() == null) {
			planAccionZona.audit(usuario, ActionAuditedEnum.NEW);
			crear(planAccionZona);
		} else {
			planAccionZona.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(planAccionZona);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String generar(Long idUsuario, Long anio, Long idPlanAccionAnio, Long mes) {
		StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("getGenerarOrgPlnZona");
		query.setParameter("idUsuario", idUsuario);
		String respuesta = (String) query.getOutputParameterValue("respuesta");
		return respuesta;
	}

	@Override
	public List<PlanAccionZonaEt> getPlanAccionZonaList(Long idPlanAccionAnio, Long anio, Long mes) {
		sql = new StringBuilder("FROM PlanAccionZonaEt o ");
		sql.append(" WHERE o.estado = :estado ");
		sql.append(" AND o.anio = :anio ");
		sql.append(" AND o.mes  = :mes  ");
		sql.append(" AND o.idPlanAccionAnio = :idPlanAccionAnio ");
		TypedQuery<PlanAccionZonaEt> query = em.createQuery(sql.toString(), PlanAccionZonaEt.class);
		query.setParameter("mes", mes);
		query.setParameter("anio", anio);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("idPlanAccionAnio", idPlanAccionAnio);
		List<PlanAccionZonaEt> result = query.getResultList();
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
