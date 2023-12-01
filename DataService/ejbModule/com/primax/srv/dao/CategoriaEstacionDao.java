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
import com.primax.jpa.param.CategoriaEstacionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ICategoriaEstacionDao;
import com.primax.srv.util.QUL;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class CategoriaEstacionDao extends GenericDao<CategoriaEstacionEt, Long> implements ICategoriaEstacionDao {

	public CategoriaEstacionDao() {
		super(CategoriaEstacionEt.class);
	}

	private StringBuilder sql;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarSubformatoNegocio(CategoriaEstacionEt categoriaEstacion, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (categoriaEstacion.getIdCategoriaEstacion() == null) {
			categoriaEstacion.audit(usuario, ActionAuditedEnum.NEW);
			crear(categoriaEstacion);
		} else {
			categoriaEstacion.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(categoriaEstacion);
		}
		em.flush();
		em.clear();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CategoriaEstacionEt getCategoriaEstacion(long id) {
		try {
			CategoriaEstacionEt categoriaEstacion = recuperar(id);
			return categoriaEstacion;
		} catch (EntidadNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CategoriaEstacionEt> getCategoriaEstacionList(String condicion) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM CategoriaEstacionEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		if (condicion != null && !condicion.isEmpty()) {
			sql.append(" AND o.descripcion like :condicion ");
		}
		TypedQuery<CategoriaEstacionEt> query = em.createQuery(sql.toString(), CategoriaEstacionEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		if (condicion != null && !condicion.isEmpty()) {
			query.setParameter("condicion", "%" + QUL.getString(condicion) + "%");
		}
		List<CategoriaEstacionEt> result = query.getResultList();
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
