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
import com.primax.jpa.sec.RolEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IRolEtDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class RolEtDao extends GenericDao<RolEt, Long> implements IRolEtDao {

	public RolEtDao() {
		super(RolEt.class);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<RolEt> getPersonaPorNombre(String nombre) {
		StringBuilder sql = new StringBuilder("from Persona o where o.nombrePersona= :nom");
		TypedQuery<RolEt> query = em.createQuery(sql.toString(), RolEt.class);

		query.setParameter("nom", nombre);
		List<RolEt> result = query.getResultList();
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public RolEt getRolbyId(Long id) {
		try {
			RolEt res = recuperar(id);
			return res;
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
