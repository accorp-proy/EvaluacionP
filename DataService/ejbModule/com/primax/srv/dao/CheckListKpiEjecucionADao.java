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
import com.primax.jpa.pla.CheckListKpiEjecucionAEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ICheckListKpiEjecucionADao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class CheckListKpiEjecucionADao extends GenericDao<CheckListKpiEjecucionAEt, Long> implements ICheckListKpiEjecucionADao {

	public CheckListKpiEjecucionADao() {
		super(CheckListKpiEjecucionAEt.class);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardaCheckListKpiEjecucion(CheckListKpiEjecucionAEt checkListKpiEjecucionA, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (checkListKpiEjecucionA.getIdCheckListKpiEjecucionA() == null) {
			checkListKpiEjecucionA.audit(usuario, ActionAuditedEnum.NEW);
			crear(checkListKpiEjecucionA);
		} else {
			checkListKpiEjecucionA.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(checkListKpiEjecucionA);
		}
		em.flush();
		em.clear();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CheckListKpiEjecucionAEt getCheckListKpiEjecucionA(long id) {
		try {
			CheckListKpiEjecucionAEt checkListKpiEjecucionA = recuperar(id);
			return checkListKpiEjecucionA;
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
