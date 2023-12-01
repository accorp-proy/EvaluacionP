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
import com.primax.jpa.pla.PlanAccionMesEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IPlanAccionMesDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class PlanAccionMesDao extends GenericDao<PlanAccionMesEt, Long> implements IPlanAccionMesDao {

	public PlanAccionMesDao() {
		super(PlanAccionMesEt.class);
	}
	
	private StringBuilder sql;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarPlanAccionMes(PlanAccionMesEt planAccionMes, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (planAccionMes.getIdPlanAccionMes() == null) {
			planAccionMes.audit(usuario, ActionAuditedEnum.NEW);
			crear(planAccionMes);
		} else {
			planAccionMes.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(planAccionMes);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String generar(Long idUsuario, Long anio, Long idPlanAccionAnio) {
		StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("getGenerarOrgPlnMes");
		query.setParameter("idUsuario", idUsuario);
		String respuesta = (String) query.getOutputParameterValue("respuesta");
		return respuesta;
	}
	
	@Override
	public List<PlanAccionMesEt> getPlanAccionMesList(Long idPlanAccionAnio,Long anio) {
		sql = new StringBuilder("FROM PlanAccionMesEt o ");
		sql.append(" WHERE o.estado = :estado ");
		sql.append(" AND o.anio     = :anio ");
		sql.append(" AND o.idPlanAccionAnio = :idPlanAccionAnio ");
		TypedQuery<PlanAccionMesEt> query = em.createQuery(sql.toString(), PlanAccionMesEt.class);
		query.setParameter("anio", anio);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("idPlanAccionAnio", idPlanAccionAnio);
		List<PlanAccionMesEt> result = query.getResultList();
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
