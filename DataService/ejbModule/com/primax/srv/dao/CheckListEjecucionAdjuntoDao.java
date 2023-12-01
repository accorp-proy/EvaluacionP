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
import com.primax.jpa.pla.CheckListEjecucionAdjuntoEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ICheckListEjecucionAdjuntoDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class CheckListEjecucionAdjuntoDao extends GenericDao<CheckListEjecucionAdjuntoEt, Long> implements ICheckListEjecucionAdjuntoDao {

	public CheckListEjecucionAdjuntoDao() {
		super(CheckListEjecucionAdjuntoEt.class);
	}

	private StringBuilder sql;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarCheckListEjecucionAdjunto(CheckListEjecucionAdjuntoEt checkListEjecucionAdjunto, UsuarioEt usuario)
			throws EntidadNoGrabadaException {
		if (checkListEjecucionAdjunto.getIdCheckListEjecucionAdjunto() == null) {
			checkListEjecucionAdjunto.audit(usuario, ActionAuditedEnum.NEW);
			crear(checkListEjecucionAdjunto);
		} else {
			checkListEjecucionAdjunto.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(checkListEjecucionAdjunto);
		}
		em.flush();
		em.clear();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CheckListEjecucionAdjuntoEt getCheckListEjecucionAdjunto(long id) {
		try {
			CheckListEjecucionAdjuntoEt checkListEjecucionAdjunto = recuperar(id);
			return checkListEjecucionAdjunto;
		} catch (EntidadNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CheckListEjecucionAdjuntoEt> getAdjunto(Long idCheckListEjecucion) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM CheckListEjecucionAdjuntoEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		sql.append(" AND o.checkListEjecucion.idCheckListEjecucion = :idCheckListEjecucion ");
		sql.append(" ORDER BY  o.idCheckListEjecucionAdjunto desc ");
		TypedQuery<CheckListEjecucionAdjuntoEt> query = em.createQuery(sql.toString(), CheckListEjecucionAdjuntoEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("idCheckListEjecucion", idCheckListEjecucion);
		List<CheckListEjecucionAdjuntoEt> result = query.getResultList();
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
