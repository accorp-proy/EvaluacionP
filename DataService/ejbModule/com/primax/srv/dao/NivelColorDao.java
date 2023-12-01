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
import com.primax.jpa.param.NivelColorEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.INivelColorDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class NivelColorDao extends GenericDao<NivelColorEt, Long> implements INivelColorDao {

	public NivelColorDao() {
		super(NivelColorEt.class);
	}

	private StringBuilder sql;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarNivelColor(NivelColorEt nivelColor, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (nivelColor.getIdNivelColor() == null) {
			nivelColor.audit(usuario, ActionAuditedEnum.NEW);
			crear(nivelColor);
		} else {
			nivelColor.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(nivelColor);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<NivelColorEt> getNivelColorList(String condicion) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM NivelColorEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		if (condicion != null && !condicion.isEmpty()) {
			sql.append(" AND upper(o.descripcion) like :condicion ");
		}
		sql.append(" ORDER BY o.orden ");
		TypedQuery<NivelColorEt> query = em.createQuery(sql.toString(), NivelColorEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		if (condicion != null && !condicion.isEmpty()) {
			query.setParameter("condicion", "%" + condicion.toUpperCase() + "%");
		}
		List<NivelColorEt> result = query.getResultList();
		return result;
	}

	public NivelColorEt getNivelColorById(long id) {
		try {
			return this.recuperar(id);
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
