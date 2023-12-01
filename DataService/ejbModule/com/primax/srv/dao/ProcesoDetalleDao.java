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
import com.primax.jpa.param.ProcesoDetalleEt;
import com.primax.jpa.param.ProcesoEt;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IProcesoDetalleDao;
import com.primax.srv.util.QUL;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class ProcesoDetalleDao extends GenericDao<ProcesoDetalleEt, Long> implements IProcesoDetalleDao {

	public ProcesoDetalleDao() {
		super(ProcesoDetalleEt.class);
	}

	private StringBuilder sql;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardaProcesoDetalle(ProcesoDetalleEt procesoDetalle, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (procesoDetalle.getIdProcesoDetalle() == null) {
			procesoDetalle.audit(usuario, ActionAuditedEnum.NEW);
			crear(procesoDetalle);
		} else {
			procesoDetalle.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(procesoDetalle);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ProcesoDetalleEt> getProcesoDetalleList(String condicion) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM ProcesoDetalleEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		if (condicion != null && !condicion.isEmpty()) {
			sql.append(" AND o.nombreProcesoDetalle like :condicion ");
		}
		TypedQuery<ProcesoDetalleEt> query = em.createQuery(sql.toString(), ProcesoDetalleEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		if (condicion != null && !condicion.isEmpty()) {
			query.setParameter("condicion", "%" + QUL.getString(condicion) + "%");
		}
		List<ProcesoDetalleEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ProcesoDetalleEt> getProcesoDetalleByTipoChecKlList(TipoChecKListEt tipoChecKList) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM ProcesoDetalleEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		sql.append(" AND o.proceso.tipoChecKList = :tipoChecKList ");
		sql.append(" ORDER BY o.orden ");
		TypedQuery<ProcesoDetalleEt> query = em.createQuery(sql.toString(), ProcesoDetalleEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("tipoChecKList", tipoChecKList);
		List<ProcesoDetalleEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ProcesoDetalleEt> getProcesoDetalleByProcesolList(ProcesoEt proceso) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM ProcesoDetalleEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		sql.append(" AND o.proceso   = :proceso ");
		sql.append(" ORDER BY o.orden ");
		TypedQuery<ProcesoDetalleEt> query = em.createQuery(sql.toString(), ProcesoDetalleEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("proceso", proceso);
		List<ProcesoDetalleEt> result = query.getResultList();
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ProcesoDetalleEt getProcesoDetalle(long id) {
		try {
			ProcesoDetalleEt procesoDetalle = recuperar(id);
			return procesoDetalle;
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
