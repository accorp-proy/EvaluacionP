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
import com.primax.jpa.param.NivelEsfuerzoEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.INivelEsfuerzoDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class NivelEsfuerzoDao extends GenericDao<NivelEsfuerzoEt, Long> implements INivelEsfuerzoDao {

	public NivelEsfuerzoDao() {
		super(NivelEsfuerzoEt.class);
	}

	private StringBuilder sql;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarNivelEsfuerzo(NivelEsfuerzoEt nivelEsfuerzo, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (nivelEsfuerzo.getIdNivelEsfuerzo() == null) {
			nivelEsfuerzo.audit(usuario, ActionAuditedEnum.NEW);
			crear(nivelEsfuerzo);
		} else {
			nivelEsfuerzo.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(nivelEsfuerzo);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<NivelEsfuerzoEt> getNivelEsfuerzoList(String condicion) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM NivelEsfuerzoEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		if (condicion != null && !condicion.isEmpty()) {
			sql.append(" AND upper(o.descripcion) like :condicion ");
		}
		sql.append(" ORDER BY o.orden ");
		TypedQuery<NivelEsfuerzoEt> query = em.createQuery(sql.toString(), NivelEsfuerzoEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		if (condicion != null && !condicion.isEmpty()) {
			query.setParameter("condicion", "%" + condicion.toUpperCase() + "%");
		}
		List<NivelEsfuerzoEt> result = query.getResultList();
		return result;
	}

	public NivelEsfuerzoEt getNivelEsfuerzoById(long id) {
		try {
			return this.recuperar(id);
		} catch (EntidadNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String limpiarReporte(Long idUsuario) {
		StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("getLimpiarReporteProcesoCualitativo");
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
