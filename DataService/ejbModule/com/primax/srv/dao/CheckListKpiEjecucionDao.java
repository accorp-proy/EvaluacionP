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
import com.primax.jpa.param.CriterioEvaluacionDetalleEt;
import com.primax.jpa.param.KPICriticoEt;
import com.primax.jpa.pla.CheckListEjecucionEt;
import com.primax.jpa.pla.CheckListKpiEjecucionEt;
import com.primax.jpa.pla.CheckListProcesoEjecucionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ICheckListKpiEjecucionDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class CheckListKpiEjecucionDao extends GenericDao<CheckListKpiEjecucionEt, Long> implements ICheckListKpiEjecucionDao {

	public CheckListKpiEjecucionDao() {
		super(CheckListKpiEjecucionEt.class);
	}

	private StringBuilder sql;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarCheckListKpiEjecucion(CheckListKpiEjecucionEt checkListKpiEjecucion, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (checkListKpiEjecucion.getIdCheckListKpiEjecucion() == null) {
			checkListKpiEjecucion.audit(usuario, ActionAuditedEnum.NEW);
			crear(checkListKpiEjecucion);
		} else {
			checkListKpiEjecucion.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(checkListKpiEjecucion);
		}
		em.flush();
		em.clear();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CheckListKpiEjecucionEt getCheckListKpiEjecucion(long id) {
		try {
			CheckListKpiEjecucionEt checkListKpiEjecucion = recuperar(id);
			return checkListKpiEjecucion;
		} catch (EntidadNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public boolean getExisteCondicionCritica(CheckListEjecucionEt checkListEjecucion, KPICriticoEt kPICritico, CriterioEvaluacionDetalleEt criterioEvaluacionDetalle) {
		sql = new StringBuilder("FROM CheckListKpiEjecucionEt o ");
		sql.append(" WHERE o.estado = :estado ");
		sql.append(" AND   o.kPICritico = :kPICritico ");
		sql.append(" AND   o.checkListProcesoEjecucion.checkListEjecucion = :checkListEjecucion ");
		sql.append(" AND   o.criterioEvaluacionDetalle = :criterioEvaluacionDetalle ");
		sql.append(" ORDER BY o.idCheckListKpiEjecucion");
		TypedQuery<CheckListKpiEjecucionEt> query = em.createQuery(sql.toString(), CheckListKpiEjecucionEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("kPICritico", kPICritico);
		query.setParameter("checkListEjecucion", checkListEjecucion);
		query.setParameter("criterioEvaluacionDetalle", criterioEvaluacionDetalle);
		List<CheckListKpiEjecucionEt> result = query.getResultList();
		CheckListKpiEjecucionEt existe = getUnique(result);
		boolean condicion = existe == null ? false : true;
		return condicion;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public boolean getExisteCondicionCriticaFoco(CheckListProcesoEjecucionEt checkListProcesoEjecucion, KPICriticoEt kPICritico, CriterioEvaluacionDetalleEt criterioEvaluacionDetalle) {
		sql = new StringBuilder("FROM CheckListKpiEjecucionEt o ");
		sql.append(" WHERE o.estado = :estado ");
		sql.append(" AND   o.kPICritico = :kPICritico ");
		sql.append(" AND   o.checkListProcesoEjecucion = :checkListProcesoEjecucion ");
		sql.append(" AND   o.criterioEvaluacionDetalle = :criterioEvaluacionDetalle ");
		sql.append(" ORDER BY o.idCheckListKpiEjecucion");
		TypedQuery<CheckListKpiEjecucionEt> query = em.createQuery(sql.toString(), CheckListKpiEjecucionEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("kPICritico", kPICritico);
		query.setParameter("checkListProcesoEjecucion", checkListProcesoEjecucion);
		query.setParameter("criterioEvaluacionDetalle", criterioEvaluacionDetalle);
		List<CheckListKpiEjecucionEt> result = query.getResultList();
		CheckListKpiEjecucionEt existe = getUnique(result);
		boolean condicion = existe == null ? false : true;
		return condicion;
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
