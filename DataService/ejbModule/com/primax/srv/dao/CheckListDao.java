package com.primax.srv.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoCheckListEnum;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.enums.TipoCheckListEnum;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.param.NivelEvaluacionEt;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.pla.CheckListEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ICheckListDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class CheckListDao extends GenericDao<CheckListEt, Long> implements ICheckListDao {

	public CheckListDao() {
		super(CheckListEt.class);
	}

	private StringBuilder sql;
	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarCheckList(CheckListEt checkList, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (checkList.getIdCheckList() == null) {
			checkList.audit(usuario, ActionAuditedEnum.NEW);
			crear(checkList);
		} else {
			checkList.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(checkList);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CheckListEt> getCheckList(NivelEvaluacionEt nivelEvaluacion, EvaluacionEt evaluacion, TipoChecKListEt tipoChecKList, Date fechaDesde, Date fechaHasta, EstadoCheckListEnum estadoCheckList)
			throws EntidadNoEncontradaException {
		
		String SfechaDesde = format.format(fechaDesde);
		String SfechaHasta = format.format(fechaHasta);
		
		sql = new StringBuilder("FROM CheckListEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		sql.append(" AND to_char(o.fechaRegistro,'yyyy-mm-dd') BETWEEN :fechaDesde AND :fechaHasta ");
		if (nivelEvaluacion != null) {
			sql.append(" AND o.nivelEvaluacion = :nivelEvaluacion ");
		}
		if (evaluacion != null) {
			sql.append(" AND o.evaluacion = :evaluacion ");
		}
		if (tipoChecKList != null) {
			sql.append(" AND o.tipoChecKList = :tipoChecKList ");
		}
		if (estadoCheckList != null && !estadoCheckList.getDescripcion().equals("Todos")) {
			sql.append(" AND o.estadoCheckList = :estadoCheckList ");
		}
		sql.append(" ORDER BY o.fechaRegistro");
		TypedQuery<CheckListEt> query = em.createQuery(sql.toString(), CheckListEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("fechaDesde", SfechaDesde);
		query.setParameter("fechaHasta", SfechaHasta);
		if (nivelEvaluacion != null) {
			query.setParameter("nivelEvaluacion", nivelEvaluacion);
		}
		if (evaluacion != null) {
			query.setParameter("evaluacion", evaluacion);
		}
		if (tipoChecKList != null) {
			query.setParameter("tipoChecKList", tipoChecKList);
		}
		if (estadoCheckList != null && !estadoCheckList.getDescripcion().equals("Todos")) {
			query.setParameter("estadoCheckList", estadoCheckList);
		}
		List<CheckListEt> result = query.getResultList();
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CheckListEt> getCheckListChild0() {
		sql = new StringBuilder("FROM CheckListEt o ");
		sql.append("WHERE o.estado = :estado ");
		sql.append("ORDER BY o.idCheckList");
		TypedQuery<CheckListEt> query = em.createQuery(sql.toString(), CheckListEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		List<CheckListEt> result = query.getResultList();
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CheckListEt> getCheckListChild(AgenciaEt agencia) {
		sql = new StringBuilder("SELECT o.checkList FROM AgenciaCheckListEt o ");
		sql.append("WHERE o.estado = :estado ");
		sql.append(" AND o.agencia = :agencia ");
		sql.append("ORDER BY o.idAgenciaCheckList");
		TypedQuery<CheckListEt> query = em.createQuery(sql.toString(), CheckListEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("agencia", agencia);
		List<CheckListEt> result = query.getResultList();
		for (int i = 0; i < result.size(); i++) {
			result.get(i).getCheckListProceso().size();
		}
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CheckListEt> getCheckListBusqueda(EvaluacionEt evaluacion, TipoChecKListEt tipoChecKList, EstadoCheckListEnum estadoCheckListEnum)
			throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM CheckListEt o ");
		sql.append(" WHERE o.estado        = :estado   ");
		sql.append(" AND o.estadoCheckList = :estadoCheckList ");
		if (evaluacion != null) {
			sql.append(" AND o.evaluacion = :evaluacion ");
		}
		if (tipoChecKList != null) {
			sql.append(" AND o.tipoChecKList = :tipoChecKList ");
		}
		TypedQuery<CheckListEt> query = em.createQuery(sql.toString(), CheckListEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("estadoCheckList", estadoCheckListEnum);
		if (evaluacion != null) {
			query.setParameter("evaluacion", evaluacion);
		}
		if (tipoChecKList != null) {
			query.setParameter("tipoChecKList", tipoChecKList);
		}
		List<CheckListEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CheckListEt getCheckPendiente(TipoCheckListEnum tipoCheckList, UsuarioEt usuario) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM CheckListEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		sql.append(" AND   o.estadoCheckList = :estadoCheckList ");
		sql.append(" AND   o.tipoCheckList   = :tipoCheckList   ");
		sql.append(" AND   o.usuarioRegistra = :usuario ");
		sql.append(" ORDER BY  o.fechaRegistro desc ");
		TypedQuery<CheckListEt> query = em.createQuery(sql.toString(), CheckListEt.class);
		query.setParameter("usuario", usuario);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("tipoCheckList", tipoCheckList);
		query.setParameter("estadoCheckList", EstadoCheckListEnum.PLANTILLA);
		List<CheckListEt> result = query.getResultList();
		CheckListEt consultado = getUnique(result);
		if (consultado != null) {
			consultado.getCheckListProceso().size();
		}
		return consultado;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CheckListEt getCheck(TipoCheckListEnum tipoCheckList, String codigo) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM CheckListEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		sql.append(" AND   o.codigo  = :codigo    ");
		sql.append(" AND   o.estadoCheckList = :estadoCheckList ");
		sql.append(" AND   o.tipoCheckList   = :tipoCheckList   ");
		TypedQuery<CheckListEt> query = em.createQuery(sql.toString(), CheckListEt.class);
		query.setParameter("codigo", codigo);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("tipoCheckList", tipoCheckList);
		query.setParameter("estadoCheckList", EstadoCheckListEnum.APROBADO);
		List<CheckListEt> result = query.getResultList();
		CheckListEt consultado = getUnique(result);
		if (consultado != null) {
			consultado.getCheckListProceso().size();
		}
		return consultado;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CheckListEt getCheckList(long id) {
		try {
			CheckListEt checkList = recuperar(id);
			checkList.getCheckListProceso().size();
			return checkList;
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
