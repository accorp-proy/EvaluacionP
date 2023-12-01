package com.primax.srv.dao;

import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.CheckListCriterioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ICheckListCriterioDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class CheckListCriterioDao extends GenericDao<CheckListCriterioEt, Long> implements ICheckListCriterioDao {

	public CheckListCriterioDao() {
		super(CheckListCriterioEt.class);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarCheckListCriterio(CheckListCriterioEt checkListCriterio, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (checkListCriterio.getIdCheckListCriterio() == null) {
			checkListCriterio.audit(usuario, ActionAuditedEnum.NEW);
			crear(checkListCriterio);
		} else {
			checkListCriterio.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(checkListCriterio);
		}
		em.flush();
		em.clear();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CheckListCriterioEt getCheckListCriterioById(long id) {
		try {
			CheckListCriterioEt checkListCriterio = recuperar(id);
			if (checkListCriterio.getCheckListCriterioDetalle() != null) {
				checkListCriterio.getCheckListCriterioDetalle().size();
			}
			return checkListCriterio;
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
