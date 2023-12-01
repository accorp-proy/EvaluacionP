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
import com.primax.jpa.pla.CheckListKpiEjecucionBEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ICheckListKpiEjecucionBDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class CheckListKpiEjecucionBDao extends GenericDao<CheckListKpiEjecucionBEt, Long> implements ICheckListKpiEjecucionBDao {

	public CheckListKpiEjecucionBDao() {
		super(CheckListKpiEjecucionBEt.class);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardaCheckListKpiEjecucionB(CheckListKpiEjecucionBEt checkListKpiEjecucionB, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (checkListKpiEjecucionB.getIdCheckListKpiEjecucionB() == null) {
			checkListKpiEjecucionB.audit(usuario, ActionAuditedEnum.NEW);
			crear(checkListKpiEjecucionB);
		} else {
			checkListKpiEjecucionB.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(checkListKpiEjecucionB);
		}
		em.flush();
		em.clear();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CheckListKpiEjecucionBEt getCheckListKpiEjecucionB(long id) {
		try {
			CheckListKpiEjecucionBEt checkListKpiEjecucionB = recuperar(id);
			return checkListKpiEjecucionB;
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
