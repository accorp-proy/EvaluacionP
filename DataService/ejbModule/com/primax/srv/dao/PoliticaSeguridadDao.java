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

import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.sec.PoliticaSeguridadEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IPoliticaSeguridadDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class PoliticaSeguridadDao extends GenericDao<PoliticaSeguridadEt, Long> implements IPoliticaSeguridadDao {

	public PoliticaSeguridadDao() {
		super(PoliticaSeguridadEt.class);
	}

	public StringBuilder sql;

	public PoliticaSeguridadEt getpoliticaSeguridad() {

		sql = new StringBuilder("from PoliticaSeguridadEt p ");
		sql.append("where p.estado= :estado");

		TypedQuery<PoliticaSeguridadEt> query = em.createQuery(sql.toString(), PoliticaSeguridadEt.class);

		query.setParameter("estado", EstadoEnum.ACT);
		List<PoliticaSeguridadEt> resultado = query.getResultList();

		return getUnique(resultado);
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
