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
import com.primax.jpa.param.CorreoEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ICorreoDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class CorreoDao extends GenericDao<CorreoEt, Long> implements ICorreoDao {

	public CorreoDao() {
		super(CorreoEt.class);
	}
	
	private StringBuilder sql;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarCorreo(CorreoEt correo, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (correo.getIdCorreo() == null) {
			correo.audit(usuario, ActionAuditedEnum.NEW);
			crear(correo);
		} else {
			correo.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(correo);
		}
		em.flush();
		em.clear();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CorreoEt getCorreo(long id) {
		try {
			CorreoEt correo = recuperar(id);
			return correo;
		} catch (EntidadNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CorreoEt getCorreoExiste(Long idCorreo) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM CorreoEt o ");
		sql.append(" WHERE o.estado      = :estado   ");
		sql.append(" AND   o.idCorreo    = :idCorreo ");
		TypedQuery<CorreoEt> query = em.createQuery(sql.toString(), CorreoEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("idCorreo", idCorreo);
		List<CorreoEt> result = query.getResultList();
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
