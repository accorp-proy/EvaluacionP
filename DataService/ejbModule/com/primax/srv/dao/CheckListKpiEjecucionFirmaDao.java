package com.primax.srv.dao;

import java.util.List;
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
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.pla.CheckListKpiEjecucionFirmaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ICheckListKpiEjecucionFirmaDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class CheckListKpiEjecucionFirmaDao extends GenericDao<CheckListKpiEjecucionFirmaEt, Long> implements ICheckListKpiEjecucionFirmaDao {

	public CheckListKpiEjecucionFirmaDao() {
		super(CheckListKpiEjecucionFirmaEt.class);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarCheckListKpiEjecucionFirma(CheckListKpiEjecucionFirmaEt checkListKpiEjecucionFirma, UsuarioEt usuario)
			throws EntidadNoGrabadaException {
		if (checkListKpiEjecucionFirma.getIdCheckListKpiEjecucionFirma() == null) {
			checkListKpiEjecucionFirma.audit(usuario, ActionAuditedEnum.NEW);
			crear(checkListKpiEjecucionFirma);
		} else {
			checkListKpiEjecucionFirma.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(checkListKpiEjecucionFirma);
		}
		em.flush();
		em.clear();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CheckListKpiEjecucionFirmaEt getCheckListKpiEjecucionFirma(long id) {
		try {
			CheckListKpiEjecucionFirmaEt checkListKpiEjecucionFirma = recuperar(id);
			return checkListKpiEjecucionFirma;
		} catch (EntidadNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void eliminarCheckListEjecucionFirma(List<CheckListKpiEjecucionFirmaEt> checkListKpiEjecucionFirmas, UsuarioEt usuario)
			throws EntidadNoGrabadaException {
		for (CheckListKpiEjecucionFirmaEt checkList : checkListKpiEjecucionFirmas) {
			checkList.setEstado(EstadoEnum.INA);
			guardarCheckListKpiEjecucionFirma(checkList, usuario);
		}
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
