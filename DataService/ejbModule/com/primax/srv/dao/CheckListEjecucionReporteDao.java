package com.primax.srv.dao;

import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.CheckListEjecucionReporteEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ICheckListEjecucionReporteDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class CheckListEjecucionReporteDao extends GenericDao<CheckListEjecucionReporteEt, Long> implements ICheckListEjecucionReporteDao {

	public CheckListEjecucionReporteDao() {
		super(CheckListEjecucionReporteEt.class);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarCheckListEjecucionReporte(CheckListEjecucionReporteEt checkListEjecucionReporte, UsuarioEt usuario)
			throws EntidadNoGrabadaException {
		if (checkListEjecucionReporte.getIdCheckListEjecucionReporte() == null) {
			checkListEjecucionReporte.audit(usuario, ActionAuditedEnum.NEW);
			crear(checkListEjecucionReporte);
		} else {
			checkListEjecucionReporte.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(checkListEjecucionReporte);
		}
		em.flush();
		em.clear();
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
