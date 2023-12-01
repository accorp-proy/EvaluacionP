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
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.TipoEstacionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ITipoEstacionDao;
import com.primax.srv.util.QUL;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class TipoEstacionDao extends GenericDao<TipoEstacionEt, Long> implements ITipoEstacionDao {

	public TipoEstacionDao() {
		super(TipoEstacionEt.class);
	}

	private StringBuilder sql;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarTipoEstacion(TipoEstacionEt tipoEstacion, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (tipoEstacion.getIdTipoEstacion() == null) {
			tipoEstacion.audit(usuario, ActionAuditedEnum.NEW);
			crear(tipoEstacion);
		} else {
			tipoEstacion.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(tipoEstacion);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<TipoEstacionEt> getTipoEstacionList(String condicion) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM TipoEstacionEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		if (condicion != null && !condicion.isEmpty()) {
			sql.append(" AND o.nombreFormato like :condicion ");
		}
		sql.append(" ORDER BY o.idTipoEstacion ");
		TypedQuery<TipoEstacionEt> query = em.createQuery(sql.toString(), TipoEstacionEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		if (condicion != null && !condicion.isEmpty()) {
			query.setParameter("condicion", "%" + QUL.getString(condicion) + "%");
		}
		List<TipoEstacionEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<TipoEstacionEt> getTipoEstacionList0(TipoEstacionEt tipoEstacion) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM TipoEstacionEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		if (tipoEstacion.getIdTipoEstacion() == 1L) {
			sql.append(" AND o.idTipoEstacion <> :idTipoEstacion ");
		} else {
			sql.append(" AND o.idTipoEstacion =  :idTipoEstacion ");
		}
		sql.append(" ORDER BY o.idTipoEstacion ");
		TypedQuery<TipoEstacionEt> query = em.createQuery(sql.toString(), TipoEstacionEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		if (tipoEstacion.getIdTipoEstacion() == 1L) {
			query.setParameter("idTipoEstacion", 1L);
		} else {
			query.setParameter("idTipoEstacion", tipoEstacion.getIdTipoEstacion());
		}
		List<TipoEstacionEt> result = query.getResultList();
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public TipoEstacionEt getTipoEstacion(long id) {
		try {
			TipoEstacionEt tipoEstacion = recuperar(id);
			tipoEstacion.getTipoCategoriaEstacion().size();
			return tipoEstacion;
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
