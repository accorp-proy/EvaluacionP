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

import com.primax.jpa.sec.UsuarioEt;

import com.primax.jpa.enums.EstadoEnum;
import com.primax.srv.dao.base.GenericDao;
import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.jpa.param.NivelEvaluacionDetalleEt;
import com.primax.srv.idao.INivelEvaluacionDetalleDao;
import com.primax.srv.util.QUL;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class NivelEvaluacionDetalleDao extends GenericDao<NivelEvaluacionDetalleEt, Long> implements INivelEvaluacionDetalleDao {

	public NivelEvaluacionDetalleDao() {
		super(NivelEvaluacionDetalleEt.class);
	}

	private StringBuilder sql;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardaNivelEvaluacionDetalle(NivelEvaluacionDetalleEt nivelEvaluacionDetalle, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (nivelEvaluacionDetalle.getIdNivelEvaluacionDetalle() == null) {
			nivelEvaluacionDetalle.audit(usuario, ActionAuditedEnum.NEW);
			crear(nivelEvaluacionDetalle);
		} else {
			nivelEvaluacionDetalle.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(nivelEvaluacionDetalle);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<NivelEvaluacionDetalleEt> getNivelEvaluacionDetalleList(String condicion) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM NivelEvaluacionDetalleEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		if (condicion != null && !condicion.isEmpty()) {
			sql.append(" AND o.nombreNivelDetalle like :condicion ");
		}
		TypedQuery<NivelEvaluacionDetalleEt> query = em.createQuery(sql.toString(), NivelEvaluacionDetalleEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		if (condicion != null && !condicion.isEmpty()) {
			query.setParameter("condicion", "%" + QUL.getString(condicion) + "%");
		}
		List<NivelEvaluacionDetalleEt> result = query.getResultList();
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public NivelEvaluacionDetalleEt getNivelEvaluacionDetalle(long id) {
		try {
			NivelEvaluacionDetalleEt nivelEvaluacionDetalle = recuperar(id);
			return nivelEvaluacionDetalle;
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
