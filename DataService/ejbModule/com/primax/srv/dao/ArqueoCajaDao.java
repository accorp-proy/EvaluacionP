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
import com.primax.jpa.pla.ArqueoCajaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IArqueoCajaDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class ArqueoCajaDao extends GenericDao<ArqueoCajaEt, Long> implements IArqueoCajaDao {

	public ArqueoCajaDao() {
		super(ArqueoCajaEt.class);
	}

	private StringBuilder sql;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarArqueoCajaEt(ArqueoCajaEt arqueoCaja, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (arqueoCaja.getIdArqueoCaja() == null) {
			arqueoCaja.audit(usuario, ActionAuditedEnum.NEW);
			crear(arqueoCaja);
		} else {
			arqueoCaja.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(arqueoCaja);
		}
		em.flush();
		em.clear();
	}

	public List<ArqueoCajaEt> getArqueoCajaEt(String condicion) {
		sql = new StringBuilder("FROM ArqueoCajaEt o ");
		sql.append("WHERE o.estado = :estado ");
		TypedQuery<ArqueoCajaEt> query = em.createQuery(sql.toString(), ArqueoCajaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		List<ArqueoCajaEt> result = query.getResultList();
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
