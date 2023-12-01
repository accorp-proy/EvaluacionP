package com.primax.srv.dao;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.ZonaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IZonaDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class ZonaDao extends GenericDao<ZonaEt, Long> implements IZonaDao {

	public ZonaDao() {
		super(ZonaEt.class);
	}

	private StringBuilder sql;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarZona(ZonaEt zona, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (zona.getIdZona() == null) {
			zona.audit(usuario, ActionAuditedEnum.NEW);
			crear(zona);
		} else {
			zona.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(zona);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String limpiarReporte(Long idUsuario) {
		StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("getLimpiarReporte");
		query.setParameter("idUsuario", idUsuario);
		String respuesta = (String) query.getOutputParameterValue("respuesta");
		return respuesta;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ZonaEt> getZonaList(String condicion) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM ZonaEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		if (condicion != null && !condicion.isEmpty()) {
			sql.append(" AND upper(o.nombreZona) like :condicion ");
		}
		sql.append(" ORDER BY o.orden ");
		TypedQuery<ZonaEt> query = em.createQuery(sql.toString(), ZonaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		if (condicion != null && !condicion.isEmpty()) {
			query.setParameter("condicion", "%" + condicion.toUpperCase() + "%");
		}
		List<ZonaEt> result = query.getResultList();
		return result;
	}

	public ZonaEt getZonaById(long id) {
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
