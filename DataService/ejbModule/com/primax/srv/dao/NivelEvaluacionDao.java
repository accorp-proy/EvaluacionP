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
import com.primax.jpa.param.NivelEvaluacionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.INivelEvaluacionDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class NivelEvaluacionDao extends GenericDao<NivelEvaluacionEt, Long> implements INivelEvaluacionDao {

	public NivelEvaluacionDao() {
		super(NivelEvaluacionEt.class);
	}

	private StringBuilder sql;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarNivelEvaluacion(NivelEvaluacionEt nivelEvaluacion, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (nivelEvaluacion.getIdNivelEvaluacion() == null) {
			nivelEvaluacion.audit(usuario, ActionAuditedEnum.NEW);
			crear(nivelEvaluacion);
		} else {
			nivelEvaluacion.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(nivelEvaluacion);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<NivelEvaluacionEt> getNivelEvaluacionList(String condicion) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM NivelEvaluacionEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		if (condicion != null && !condicion.isEmpty()) {
			sql.append(" AND upper(o.descripcion) like :condicion ");
		}
		sql.append(" ORDER BY o.orden ");
		TypedQuery<NivelEvaluacionEt> query = em.createQuery(sql.toString(), NivelEvaluacionEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		if (condicion != null && !condicion.isEmpty()) {
			query.setParameter("condicion", "%" + condicion.toUpperCase() + "%");
		}
		List<NivelEvaluacionEt> result = query.getResultList();
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public NivelEvaluacionEt getNivelEvaluacion(long id) {
		try {
			NivelEvaluacionEt nivelEvaluacion = recuperar(id);
			nivelEvaluacion.getNivelEvaluacionDetalle().size();
			return nivelEvaluacion;
		} catch (EntidadNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String limpiarReporte(Long idUsuario) {
		StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("getLimpiarReporteProcesoConsolidado");
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
