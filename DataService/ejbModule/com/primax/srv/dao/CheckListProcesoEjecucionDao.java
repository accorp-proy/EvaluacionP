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
import com.primax.jpa.pla.CheckListProcesoEjecucionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ICheckListProcesoEjecucionDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class CheckListProcesoEjecucionDao extends GenericDao<CheckListProcesoEjecucionEt, Long> implements ICheckListProcesoEjecucionDao {

	public CheckListProcesoEjecucionDao() {
		super(CheckListProcesoEjecucionEt.class);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarCheckListProcesoEjecucion(CheckListProcesoEjecucionEt checkListProcesoEjecucion, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (checkListProcesoEjecucion.getIdCheckListProcesoEjecucion() == null) {
			checkListProcesoEjecucion.audit(usuario, ActionAuditedEnum.NEW);
			crear(checkListProcesoEjecucion);
		} else {
			checkListProcesoEjecucion.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(checkListProcesoEjecucion);
		}
		em.flush();
		em.clear();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CheckListProcesoEjecucionEt getCheckListProcesoE(long id) {
		try {
			CheckListProcesoEjecucionEt checkListProcesoEjecucion = recuperar(id);
			checkListProcesoEjecucion.getCheckListKpiEjecucion();
			return checkListProcesoEjecucion;
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
