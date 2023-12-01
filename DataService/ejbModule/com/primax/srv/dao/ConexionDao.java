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

import com.primax.enm.gen.DBTypeLib;
import com.primax.jpa.param.ConexionEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IConexionDao;



@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class ConexionDao extends GenericDao<ConexionEt, Long> implements IConexionDao {

	public ConexionDao() {
		super(ConexionEt.class);
	}

	private StringBuilder sql;

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ConexionEt getConexionById(Long value) {
		sql = new StringBuilder("from ConexionEt o ");
		sql.append("where (o.idConexion = :idCondicion or 0= :idCondicion) ");
		TypedQuery<ConexionEt> query = em.createQuery(sql.toString(), ConexionEt.class);
		query.setParameter("idCondicion", value);
		List<ConexionEt> result = query.getResultList();
		return getUnique(result);
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ConexionEt getConexionByLib(DBTypeLib lib) {
		sql = new StringBuilder("from ConexionEt o ");
		sql.append("where o.tipoLibDb = :lib ");
		TypedQuery<ConexionEt> query = em.createQuery(sql.toString(), ConexionEt.class);
		query.setParameter("lib", lib);
		List<ConexionEt> result = query.getResultList();
		return getUnique(result);
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
