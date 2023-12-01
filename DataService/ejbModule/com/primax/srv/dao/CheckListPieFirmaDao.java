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
import com.primax.jpa.pla.CheckListPieFirmaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ICheckListPieFirmaDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class CheckListPieFirmaDao extends GenericDao<CheckListPieFirmaEt, Long> implements ICheckListPieFirmaDao {

	public CheckListPieFirmaDao() {
		super(CheckListPieFirmaEt.class);
	}

	private StringBuilder sql;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarCheckListPieFirma(CheckListPieFirmaEt checkListPieFirma, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (checkListPieFirma.getIdCheckListPieFirma() == null) {
			checkListPieFirma.audit(usuario, ActionAuditedEnum.NEW);
			crear(checkListPieFirma);
		} else {
			checkListPieFirma.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(checkListPieFirma);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long getOrdenPie(Long idCheckList) throws EntidadNoEncontradaException {
		Long orden = 0L;
		try {

			sql = new StringBuilder(" SELECT  (MAX(o.orden) + 1)  AS id  FROM  CheckListPieFirmaEt o where o.checkList.idCheckList = :idCheckList and o.estado='ACT' ");
			orden = ((Long) em.createQuery(sql.toString()).setParameter("idCheckList", idCheckList).getSingleResult());
			if (orden == null) {
				orden = 1L;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getOrdenPie " + " " + e.getMessage());
		}
		return orden;
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
