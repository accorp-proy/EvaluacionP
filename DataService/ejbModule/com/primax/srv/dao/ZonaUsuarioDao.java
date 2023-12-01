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
import com.primax.jpa.param.ZonaEt;
import com.primax.jpa.param.ZonaUsuarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IZonaUsuarioDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class ZonaUsuarioDao extends GenericDao<ZonaUsuarioEt, Long> implements IZonaUsuarioDao {

	public ZonaUsuarioDao() {
		super(ZonaUsuarioEt.class);
	}

	private StringBuilder sql;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarZonaUsuario(ZonaUsuarioEt zonaUsuario, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (zonaUsuario.getIdZonaUsuario() == null) {
			zonaUsuario.audit(usuario, ActionAuditedEnum.NEW);
			crear(zonaUsuario);
		} else {
			zonaUsuario.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(zonaUsuario);
		}
		em.flush();
		em.clear();
	}

	public ZonaUsuarioEt getZonaUsuarioById(long id) {
		try {
			return this.recuperar(id);
		} catch (EntidadNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ZonaUsuarioEt getZonaExiste(ZonaEt zona, UsuarioEt usuario) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM ZonaUsuarioEt o ");
		sql.append(" WHERE o.usuario = :usuario   ");
		sql.append(" AND   o.zona    = :zona ");
		TypedQuery<ZonaUsuarioEt> query = em.createQuery(sql.toString(), ZonaUsuarioEt.class);
		query.setParameter("zona", zona);
		query.setParameter("usuario", usuario);
		List<ZonaUsuarioEt> result = query.getResultList();
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
