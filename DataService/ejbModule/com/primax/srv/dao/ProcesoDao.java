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
import com.primax.jpa.param.ProcesoEt;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IProcesoDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class ProcesoDao extends GenericDao<ProcesoEt, Long> implements IProcesoDao {

	public ProcesoDao() {
		super(ProcesoEt.class);
	}

	private StringBuilder sql;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarProceso(ProcesoEt proceso, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (proceso.getIdproceso() == null) {
			proceso.audit(usuario, ActionAuditedEnum.NEW);
			crear(proceso);
		} else {
			proceso.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(proceso);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ProcesoEt> getProcesoList(TipoChecKListEt tipoChecKList, String condicion) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM ProcesoEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		if (tipoChecKList != null) {
			sql.append(" AND o.tipoChecKList = :tipoChecKList ");
		}
		if (condicion != null && !condicion.isEmpty()) {
			sql.append(" AND upper(o.nombreDimension) like :condicion ");
		}
		sql.append(" ORDER BY o.orden ");
		TypedQuery<ProcesoEt> query = em.createQuery(sql.toString(), ProcesoEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		if (tipoChecKList != null) {
			query.setParameter("tipoChecKList", tipoChecKList);
		}
		if (condicion != null && !condicion.isEmpty()) {
			query.setParameter("condicion", "%" + condicion.toUpperCase() + "%");
		}
		List<ProcesoEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ProcesoEt> getProcesoByTipoList(TipoChecKListEt tipoChecKList) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM ProcesoEt o ");
		sql.append(" WHERE o.estado      = :estado   ");
		sql.append(" AND o.tipoChecKList = :tipoChecKList ");
		sql.append(" ORDER BY o.orden");
		TypedQuery<ProcesoEt> query = em.createQuery(sql.toString(), ProcesoEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("tipoChecKList", tipoChecKList);
		List<ProcesoEt> result = query.getResultList();
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ProcesoEt getProceso(long id) {
		try {
			ProcesoEt proceso = recuperar(id);
			proceso.getProcesoDetalle().size();
			return proceso;
		} catch (EntidadNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String limpiarReporte(Long idUsuario) {
		StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("getReporteTipoEvaluacion");
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
