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
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.pla.ArqueoCajaFuerteEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IArqueoCajaFuerteDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class ArqueoCajaFuerteDao extends GenericDao<ArqueoCajaFuerteEt, Long> implements IArqueoCajaFuerteDao {

	public ArqueoCajaFuerteDao() {
		super(ArqueoCajaFuerteEt.class);
	}

	private StringBuilder sql;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarArqueoCajaFuerte(ArqueoCajaFuerteEt arqueoCajaFuerte, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (arqueoCajaFuerte.getIdArqueoCajaFuerte() == null) {
			arqueoCajaFuerte.audit(usuario, ActionAuditedEnum.NEW);
			crear(arqueoCajaFuerte);
		} else {
			arqueoCajaFuerte.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(arqueoCajaFuerte);
		}
		em.flush();
		em.clear();
	}

	public List<ArqueoCajaFuerteEt> getArqueoCajaFuerte(String condicion) {
		sql = new StringBuilder("FROM ArqueoCajaFuerteEt o ");
		sql.append("WHERE o.estado = :estado ");
		TypedQuery<ArqueoCajaFuerteEt> query = em.createQuery(sql.toString(), ArqueoCajaFuerteEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		List<ArqueoCajaFuerteEt> result = query.getResultList();
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
