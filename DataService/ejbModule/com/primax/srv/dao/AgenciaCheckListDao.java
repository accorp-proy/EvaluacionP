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
import com.primax.jpa.param.AgenciaCheckListEt;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.param.NivelEvaluacionEt;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.pla.CheckListEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IAgenciaCheckListDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class AgenciaCheckListDao extends GenericDao<AgenciaCheckListEt, Long> implements IAgenciaCheckListDao {

	public AgenciaCheckListDao() {
		super(AgenciaCheckListEt.class);
	}

	private StringBuilder sql;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardaAgenciaCheckList(AgenciaCheckListEt agenciaCheckList, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (agenciaCheckList.getIdAgenciaCheckList() == null) {
			agenciaCheckList.audit(usuario, ActionAuditedEnum.NEW);
			crear(agenciaCheckList);
		} else {
			agenciaCheckList.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(agenciaCheckList);
		}
		em.flush();
		em.clear();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public AgenciaCheckListEt getAgenciaCheckList(long id) {
		try {
			AgenciaCheckListEt agenciaCheckList = recuperar(id);
			return agenciaCheckList;
		} catch (EntidadNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AgenciaCheckListEt> getAgenciaCheckListByAgenciaList(AgenciaEt agencia) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM AgenciaCheckListEt o ");
		sql.append(" WHERE o.estado   = :estado  ");
		sql.append(" AND   o.agencia  = :agencia ");
		sql.append(" ORDER BY  o.idAgenciaCheckList   ");
		TypedQuery<AgenciaCheckListEt> query = em.createQuery(sql.toString(), AgenciaCheckListEt.class);
		query.setParameter("agencia", agencia);
		query.setParameter("estado", EstadoEnum.ACT);
		List<AgenciaCheckListEt> result = query.getResultList();
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public AgenciaCheckListEt getAgenciaCheckList(AgenciaEt agencia, CheckListEt checkList) {
		sql = new StringBuilder("FROM AgenciaCheckListEt o ");
		sql.append("where o.agencia= :agencia ");
		sql.append("and o.checkList= :checkList ");
		TypedQuery<AgenciaCheckListEt> rolMen = em.createQuery(sql.toString(), AgenciaCheckListEt.class);
		rolMen.setParameter("agencia", agencia);
		rolMen.setParameter("checkList", checkList);
		List<AgenciaCheckListEt> result_0 = rolMen.getResultList();
		AgenciaCheckListEt result = getUnique(result_0);
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AgenciaCheckListEt> getAgenciaCheckListHabilitados(AgenciaEt agencia, NivelEvaluacionEt nivelEvaluacion, EvaluacionEt evaluacion, TipoChecKListEt tipoChecKList)
			throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM AgenciaCheckListEt o ");
		sql.append(" WHERE o.estado   = :estado  ");
		sql.append(" AND   o.agencia  = :agencia ");
		if (nivelEvaluacion != null) {
			sql.append(" AND o.checkList.nivelEvaluacion = :nivelEvaluacion ");
		}
		if (evaluacion != null) {
			sql.append(" AND o.checkList.evaluacion = :evaluacion ");
		}
		if (tipoChecKList != null) {
			sql.append(" AND o.checkList.tipoChecKList = :tipoChecKList ");
		}
		sql.append(" ORDER BY  o.idAgenciaCheckList   ");
		TypedQuery<AgenciaCheckListEt> query = em.createQuery(sql.toString(), AgenciaCheckListEt.class);
		query.setParameter("agencia", agencia);
		query.setParameter("estado", EstadoEnum.ACT);
		if (nivelEvaluacion != null) {
			query.setParameter("nivelEvaluacion", nivelEvaluacion);
		}
		if (evaluacion != null) {
			query.setParameter("evaluacion", evaluacion);
		}
		if (tipoChecKList != null) {
			query.setParameter("tipoChecKList", tipoChecKList);
		}
		List<AgenciaCheckListEt> result = query.getResultList();
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
