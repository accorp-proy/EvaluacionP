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
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ITipoChecKListDao;
import com.primax.srv.util.QUL;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class TipoChecKListDao extends GenericDao<TipoChecKListEt, Long> implements ITipoChecKListDao {

	public TipoChecKListDao() {
		super(TipoChecKListEt.class);
	}

	private StringBuilder sql;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardaTipoChecList(TipoChecKListEt tipoChecKList, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (tipoChecKList.getIdTipoCheckList() == null) {
			tipoChecKList.audit(usuario, ActionAuditedEnum.NEW);
			crear(tipoChecKList);
		} else {
			tipoChecKList.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(tipoChecKList);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<TipoChecKListEt> getTipoChecList(String condicion) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM TipoChecKListEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		if (condicion != null && !condicion.isEmpty()) {
			sql.append(" AND o.descripcion like :condicion ");
		}
		sql.append(" ORDER BY o.idTipoCheckList ");
		TypedQuery<TipoChecKListEt> query = em.createQuery(sql.toString(), TipoChecKListEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		if (condicion != null && !condicion.isEmpty()) {
			query.setParameter("condicion", "%" + QUL.getString(condicion) + "%");
		}
		List<TipoChecKListEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<TipoChecKListEt> getTipoCheckListByEvaluacion(EvaluacionEt evaluacion) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM TipoChecKListEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		sql.append(" AND o.evaluacion = :evaluacion ");
		sql.append(" ORDER BY o.idTipoCheckList ");
		TypedQuery<TipoChecKListEt> query = em.createQuery(sql.toString(), TipoChecKListEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("evaluacion", evaluacion);
		List<TipoChecKListEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<TipoChecKListEt> getTipoCheckListByEvaluacionControl() throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM TipoChecKListEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		sql.append(" AND o.evaluacion.idEvaluacion = 2 ");
		sql.append(" ORDER BY o.idTipoCheckList ");
		TypedQuery<TipoChecKListEt> query = em.createQuery(sql.toString(), TipoChecKListEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		List<TipoChecKListEt> result = query.getResultList();
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public TipoChecKListEt getTipoChecList(long id) {
		try {
			TipoChecKListEt tipoChecKList = recuperar(id);
			return tipoChecKList;
		} catch (EntidadNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String generar(Long idZona, Long idTipoCheckList, Long idEvaluacion, Long idProceso, Long idUsuario) {
		StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("getGenerarReporteUltimaVisita0");
		query.setParameter("idZona", idZona);
		query.setParameter("idTipoCheckList", idTipoCheckList);
		query.setParameter("idEvaluacion", idEvaluacion);
		query.setParameter("idProceso", idProceso);
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
