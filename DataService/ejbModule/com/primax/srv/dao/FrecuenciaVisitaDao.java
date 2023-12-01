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
import com.primax.jpa.param.FrecuenciaVisitaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IFrecuenciaVisitaDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class FrecuenciaVisitaDao extends GenericDao<FrecuenciaVisitaEt, Long> implements IFrecuenciaVisitaDao {

	public FrecuenciaVisitaDao() {
		super(FrecuenciaVisitaEt.class);
	}

	private StringBuilder sql;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarFrecuenciaVisita(FrecuenciaVisitaEt frecuenciaVisita, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (frecuenciaVisita.getIdFrecuenciaVisita() == null) {
			frecuenciaVisita.audit(usuario, ActionAuditedEnum.NEW);
			crear(frecuenciaVisita);
		} else {
			frecuenciaVisita.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(frecuenciaVisita);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<FrecuenciaVisitaEt> getFrecuenciaVisitaList(String condicion) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM FrecuenciaVisitaEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		if (condicion != null && !condicion.isEmpty()) {
			sql.append(" AND upper(o.nombre) like :condicion ");
		}
		sql.append(" ORDER BY o.orden ");
		TypedQuery<FrecuenciaVisitaEt> query = em.createQuery(sql.toString(), FrecuenciaVisitaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		if (condicion != null && !condicion.isEmpty()) {
			query.setParameter("condicion", "%" + condicion.toUpperCase() + "%");
		}
		List<FrecuenciaVisitaEt> result = query.getResultList();
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public FrecuenciaVisitaEt getFrecuenciaVisitaById(long id) {
		try {
			FrecuenciaVisitaEt frecuenciaVisita = recuperar(id);
			frecuenciaVisita.getFrecuenciaVisitaDetalle().size();
			return frecuenciaVisita;
		} catch (EntidadNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String limpiarReporte(Long idUsuario) {
		StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("getReporteOrgPlnAnio");
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
