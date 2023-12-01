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
import com.primax.jpa.pla.CheckListInformeCabeceraEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ICheckListInformeCabeceraDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class CheckListInformeCabeceraDao extends GenericDao<CheckListInformeCabeceraEt, Long> implements ICheckListInformeCabeceraDao {

	public CheckListInformeCabeceraDao() {
		super(CheckListInformeCabeceraEt.class);
	}
	
	private StringBuilder sql;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarCheckListInformeCabecera(CheckListInformeCabeceraEt checkListInformeCabecera, UsuarioEt usuario)
			throws EntidadNoGrabadaException {
		if (checkListInformeCabecera.getIdCheckListInformeCabecera() == null) {
			checkListInformeCabecera.audit(usuario, ActionAuditedEnum.NEW);
			crear(checkListInformeCabecera);
		} else {
			checkListInformeCabecera.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(checkListInformeCabecera);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long getOrdenCab(Long idCheckList) throws EntidadNoEncontradaException {
		Long orden = 0L;
		try {

			sql = new StringBuilder(" SELECT  (MAX(o.orden) + 1)  AS id  FROM  CheckListInformeCabeceraEt o where o.checkList.idCheckList = :idCheckList and o.estado='ACT' ");
			orden = ((Long) em.createQuery(sql.toString()).setParameter("idCheckList", idCheckList).getSingleResult());
			if (orden == null) {
				orden = 1L;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getOrdenCab " + " " + e.getMessage());
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
