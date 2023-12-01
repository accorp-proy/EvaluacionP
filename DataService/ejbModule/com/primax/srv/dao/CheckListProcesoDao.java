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
import com.primax.jpa.pla.CheckListEt;
import com.primax.jpa.pla.CheckListProcesoEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ICheckListProcesoDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class CheckListProcesoDao extends GenericDao<CheckListProcesoEt, Long> implements ICheckListProcesoDao {

	public CheckListProcesoDao() {
		super(CheckListProcesoEt.class);
	}

	private StringBuilder sql;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarCheckListProceso(CheckListProcesoEt checkListProceso, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (checkListProceso.getIdCheckListProceso() == null) {
			checkListProceso.audit(usuario, ActionAuditedEnum.NEW);
			crear(checkListProceso);
		} else {
			checkListProceso.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(checkListProceso);
		}
		em.flush();
		em.clear();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CheckListProcesoEt getCheckListProceso(long id) {
		try {
			CheckListProcesoEt checkListProceso = recuperar(id);
			checkListProceso.getCheckListKpi().size();
			return checkListProceso;
		} catch (EntidadNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<CheckListProcesoEt> getChilds(CheckListEt checkList) {
		sql = new StringBuilder("from CheckListProcesoEt o ");
		sql.append("where o.checkList= :checkList ");
		sql.append("and o.estado = :estado ");
		sql.append("ORDER BY o.orden");
		TypedQuery<CheckListProcesoEt> query = em.createQuery(sql.toString(), CheckListProcesoEt.class);
		query.setParameter("checkList", checkList);
		query.setParameter("estado", EstadoEnum.ACT);
		List<CheckListProcesoEt> result = query.getResultList();
		for (int i = 0; i < result.size(); i++) {
			result.get(i).getCheckListKpi().size();
		}
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
