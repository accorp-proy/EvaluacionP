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
import com.primax.jpa.pla.CheckListKpiEt;
import com.primax.jpa.pla.CheckListProcesoEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ICheckListKpiDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class CheckListKpiDao extends GenericDao<CheckListKpiEt, Long> implements ICheckListKpiDao {

	public CheckListKpiDao() {
		super(CheckListKpiEt.class);
	}

	private StringBuilder sql;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarCheckListKpi(CheckListKpiEt checkListKpi, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (checkListKpi.getIdCheckListKpi() == null) {
			checkListKpi.audit(usuario, ActionAuditedEnum.NEW);
			crear(checkListKpi);
		} else {
			checkListKpi.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(checkListKpi);
		}
		em.flush();
		em.clear();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CheckListKpiEt> getCheckListByVisualizar(CheckListProcesoEt proceso) {
		sql = new StringBuilder("FROM CheckListKpiEt o ");
		sql.append("WHERE o.estado = :estado ");
		sql.append("AND o.checkListProceso  = :proceso ");
		sql.append("AND o.visualizarReporte = :visualizarReporte ");
		sql.append("ORDER BY o.idCheckListKpi");
		TypedQuery<CheckListKpiEt> query = em.createQuery(sql.toString(), CheckListKpiEt.class);
		query.setParameter("proceso", proceso);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("visualizarReporte", true);
		List<CheckListKpiEt> result = query.getResultList();
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CheckListKpiEt getCheckListKpi(long id) {
		try {
			CheckListKpiEt checkListKpi = recuperar(id);
			return checkListKpi;
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
