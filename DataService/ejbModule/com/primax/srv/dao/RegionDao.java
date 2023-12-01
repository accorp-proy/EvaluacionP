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

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.RegionEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IRegionDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class RegionDao extends GenericDao<RegionEt, Long> implements IRegionDao {

	public RegionDao() {
		super(RegionEt.class);
	}

	private StringBuilder sql;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardar(RegionEt region) throws EntidadNoGrabadaException {
		if (region.getIdRegion() == null) {
			crear(region);
		} else {
			actualizar(region);
		}
		em.flush();
		em.clear();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<RegionEt> getRegiones(EstadoEnum estado) {
		sql = new StringBuilder("FROM RegionEt o ");
		sql.append("WHERE o.estado = :estado ");
		TypedQuery<RegionEt> query = em.createQuery(sql.toString(), RegionEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		List<RegionEt> result = query.getResultList();
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<RegionEt> getRegionesCondicion(String condicion) {
		sql = new StringBuilder("FROM RegionEt o ");
		sql.append("WHERE o.estado = :estado ");
		if (condicion != null)
			sql.append("AND o.descRegion like :desc ");
		TypedQuery<RegionEt> query = em.createQuery(sql.toString(), RegionEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		if (condicion != null) {
			query.setParameter("desc", "%" + condicion + "%");
		} else {
			query.setMaxResults(20);
		}
		List<RegionEt> result = query.getResultList();
		return result;
	}

	@Override
	public RegionEt getRegionById(long id) {
		try {
			RegionEt region = recuperar(id);
			region.getProvincias().size();
			return region;
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
