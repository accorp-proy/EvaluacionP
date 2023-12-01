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
import com.primax.jpa.param.AgenciaCategoriaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IAgenciaCategoriaDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class AgenciaCategoriaDao extends GenericDao<AgenciaCategoriaEt, Long> implements IAgenciaCategoriaDao {

	public AgenciaCategoriaDao() {
		super(AgenciaCategoriaEt.class);
	}

	private StringBuilder sql;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarAgenciaCategoria(AgenciaCategoriaEt agenciaCategoria, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (agenciaCategoria.getIdAgenciaCategoria() == null) {
			agenciaCategoria.audit(usuario, ActionAuditedEnum.NEW);
			crear(agenciaCategoria);
		} else {
			agenciaCategoria.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(agenciaCategoria);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AgenciaCategoriaEt> getAgenciaCategoriaList(String condicion) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM AgenciaCategoriaEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		TypedQuery<AgenciaCategoriaEt> query = em.createQuery(sql.toString(), AgenciaCategoriaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		List<AgenciaCategoriaEt> result = query.getResultList();
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public AgenciaCategoriaEt getAgenciaCategoria(long id) {
		try {
			AgenciaCategoriaEt agenciaCategoria = recuperar(id);
			return agenciaCategoria;
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
