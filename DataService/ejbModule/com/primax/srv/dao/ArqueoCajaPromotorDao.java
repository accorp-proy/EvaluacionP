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
import com.primax.jpa.pla.ArqueoCajaPromotorEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IArqueoCajaPromotorDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class ArqueoCajaPromotorDao extends GenericDao<ArqueoCajaPromotorEt, Long> implements IArqueoCajaPromotorDao {

	public ArqueoCajaPromotorDao() {
		super(ArqueoCajaPromotorEt.class);
	}

	private StringBuilder sql;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarArqueoCajaPromotor(ArqueoCajaPromotorEt arqueoCajaPromotor, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (arqueoCajaPromotor.getIdArqueoCajaPromotor() == null) {
			arqueoCajaPromotor.audit(usuario, ActionAuditedEnum.NEW);
			crear(arqueoCajaPromotor);
		} else {
			arqueoCajaPromotor.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(arqueoCajaPromotor);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ArqueoCajaPromotorEt> getArqueoCajaPromotor(String condicion) {
		sql = new StringBuilder("FROM ArqueoCajaPromotorEt o ");
		sql.append("WHERE o.estado = :estado ");
		TypedQuery<ArqueoCajaPromotorEt> query = em.createQuery(sql.toString(), ArqueoCajaPromotorEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		List<ArqueoCajaPromotorEt> result = query.getResultList();
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
