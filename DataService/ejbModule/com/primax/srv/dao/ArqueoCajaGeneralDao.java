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
import com.primax.jpa.pla.ArqueoCajaGeneralEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IArqueoCajaGeneralDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class ArqueoCajaGeneralDao extends GenericDao<ArqueoCajaGeneralEt, Long> implements IArqueoCajaGeneralDao {

	public ArqueoCajaGeneralDao() {
		super(ArqueoCajaGeneralEt.class);
	}

	private StringBuilder sql;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarArqueoCajaGeneral(ArqueoCajaGeneralEt arqueoCajaGeneral, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (arqueoCajaGeneral.getIdArqueoCajaGeneral() == null) {
			arqueoCajaGeneral.audit(usuario, ActionAuditedEnum.NEW);
			crear(arqueoCajaGeneral);
		} else {
			arqueoCajaGeneral.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(arqueoCajaGeneral);
		}
		em.flush();
		em.clear();
	}

	public List<ArqueoCajaGeneralEt> getArqueoCajaGeneral(String condicion) {
		sql = new StringBuilder("FROM ArqueoCajaGeneralEt o ");
		sql.append("WHERE o.estado = :estado ");
		TypedQuery<ArqueoCajaGeneralEt> query = em.createQuery(sql.toString(), ArqueoCajaGeneralEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		List<ArqueoCajaGeneralEt> result = query.getResultList();
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
