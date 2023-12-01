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
import com.primax.jpa.pla.CheckListCriterioDetalleEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ICheckListCriterioDetalleDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class CheckListCriterioDetalleDao extends GenericDao<CheckListCriterioDetalleEt, Long> implements ICheckListCriterioDetalleDao {

	public CheckListCriterioDetalleDao() {
		super(CheckListCriterioDetalleEt.class);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarCheckListCriterioDetalle(CheckListCriterioDetalleEt checkListCriterioDetalle, UsuarioEt usuario)
			throws EntidadNoGrabadaException {
		if (checkListCriterioDetalle.getIdCheckListCriterioDetalle() == null) {
			checkListCriterioDetalle.audit(usuario, ActionAuditedEnum.NEW);
			crear(checkListCriterioDetalle);
		} else {
			checkListCriterioDetalle.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(checkListCriterioDetalle);
		}
		em.flush();
		em.clear();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CheckListCriterioDetalleEt getCheckListCriterioDetalle(long id) {
		try {
			CheckListCriterioDetalleEt checkListCriterioDetalle = recuperar(id);
			return checkListCriterioDetalle;
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
