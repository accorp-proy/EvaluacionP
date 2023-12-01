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
import com.primax.jpa.pla.CheckListEjecucionEt;
import com.primax.jpa.pla.CheckListEjecucionFirmaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ICheckListEjecucionFirmaDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class CheckListEjecucionFirmaDao extends GenericDao<CheckListEjecucionFirmaEt, Long> implements ICheckListEjecucionFirmaDao {

	public CheckListEjecucionFirmaDao() {
		super(CheckListEjecucionFirmaEt.class);
	}

	private StringBuilder sql;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarCheckListEjecucionFirma(CheckListEjecucionFirmaEt checkListEjecucionFirma, UsuarioEt usuario)
			throws EntidadNoGrabadaException {
		if (checkListEjecucionFirma.getIdCheckListEjecucionFirma() == null) {
			checkListEjecucionFirma.audit(usuario, ActionAuditedEnum.NEW);
			crear(checkListEjecucionFirma);
		} else {
			checkListEjecucionFirma.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(checkListEjecucionFirma);
		}
		em.flush();
		em.clear();
	}

	public CheckListEjecucionFirmaEt getCheckListEjecucionFirma(long id) {
		try {
			CheckListEjecucionFirmaEt checkListEjecucionFirma = recuperar(id);
			return checkListEjecucionFirma;
		} catch (EntidadNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void eliminarCheckListEjecucionFirma(List<CheckListEjecucionFirmaEt> checkListEjecucionFirmas, UsuarioEt usuario)
			throws EntidadNoGrabadaException {
		for (CheckListEjecucionFirmaEt checkList : checkListEjecucionFirmas) {
			checkList.setEstado(EstadoEnum.INA);
			guardarCheckListEjecucionFirma(checkList, usuario);
		}
	}
	@Override
	public void eliminarCheckListEjecucionFirmaIndividual(CheckListEjecucionFirmaEt checkListEjecucionFirma, UsuarioEt usuario)
			throws EntidadNoGrabadaException {
		checkListEjecucionFirma.setEstado(EstadoEnum.INA);
		guardarCheckListEjecucionFirma(checkListEjecucionFirma, usuario);

	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CheckListEjecucionFirmaEt> getCheckListEjecucionFirmaByChekListList(CheckListEjecucionEt checkListEjecucion)
			throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM CheckListEjecucionFirmaEt o     ");
		sql.append(" WHERE o.estado = :estado  ");
		sql.append(" AND o.firmado = 'false'   ");
		sql.append(" AND o.checkListEjecucion = :checkListEjecucion ");
		sql.append(" ORDER BY o.orden ");
		TypedQuery<CheckListEjecucionFirmaEt> query = em.createQuery(sql.toString(), CheckListEjecucionFirmaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("checkListEjecucion", checkListEjecucion);
		List<CheckListEjecucionFirmaEt> result = query.getResultList();
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
