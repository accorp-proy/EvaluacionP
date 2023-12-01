package com.primax.srv.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoCheckListEnum;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.enums.EstadoPlanAccionEnum;
import com.primax.jpa.enums.TipoCheckListEnum;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.param.NivelEvaluacionEt;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.param.ZonaEt;
import com.primax.jpa.param.ZonaUsuarioEt;
import com.primax.jpa.pla.CheckListEjecucionEt;
import com.primax.jpa.pla.CheckListKpiEjecucionEt;
import com.primax.jpa.pla.CheckListProcesoEjecucionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ICheckListEjecucionDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class CheckListEjecucionDao extends GenericDao<CheckListEjecucionEt, Long> implements ICheckListEjecucionDao {

	public CheckListEjecucionDao() {
		super(CheckListEjecucionEt.class);
	}

	private StringBuilder sql;
	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarCheckListEjecucion(CheckListEjecucionEt checkListEjecucion, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (checkListEjecucion.getIdCheckListEjecucion() == null) {
			checkListEjecucion.audit(usuario, ActionAuditedEnum.NEW);
			crear(checkListEjecucion);
		} else {
			checkListEjecucion.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(checkListEjecucion);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String generarActNivelRiesgo(Long idNivelEvaluacion, Long idCheckListEjecucion) {
		StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("getActNivelRiego");
		query.setParameter("idNivelEvaluacion", idNivelEvaluacion);
		query.setParameter("idCheckListEjecucion", idCheckListEjecucion);
		String respuesta = (String) query.getOutputParameterValue("respuesta");
		return respuesta;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CheckListEjecucionEt> getCheckListEjecucionList(ZonaEt zona, AgenciaEt estacion, EvaluacionEt evaluacion, TipoChecKListEt tipoChecKList, NivelEvaluacionEt nivelEvaluacion, Date fechaDesde, Date fechaHasta, UsuarioEt usuario, EstadoCheckListEnum estadoCheckList)
			throws EntidadNoEncontradaException {

		String SfechaDesde = format.format(fechaDesde);
		String SfechaHasta = format.format(fechaHasta);

		sql = new StringBuilder("FROM CheckListEjecucionEt o  ");
		sql.append(" WHERE o.estado             = :estado ");
		sql.append(" AND o.planificacion.estado = :estado ");
		sql.append(" AND to_char(o.planificacion.fechaPlanificacion,'yyyy-mm-dd') BETWEEN :fechaDesde AND :fechaHasta ");
		if (usuario != null) {
			sql.append(" AND o.usuarioAsignado = :usuario ");
		}
		if (zona != null) {
			sql.append(" AND o.planificacion.agencia.zona = :zona ");
		}
		if (estacion != null) {
			sql.append(" AND o.planificacion.agencia = :estacion ");
		}
		if (evaluacion != null) {
			sql.append(" AND o.evaluacion = :evaluacion ");
		}
		if (tipoChecKList != null) {
			sql.append(" AND o.tipoChecKList = :tipoChecKList ");
		}
		if (nivelEvaluacion != null) {
			sql.append(" AND o.nivelEvaluacion = :nivelEvaluacion ");
		}
		if (estadoCheckList != null && !estadoCheckList.getDescripcion().equals("Todos")) {
			sql.append(" AND o.estadoCheckList = :estadoCheckList ");
		}
		sql.append(" ORDER BY o.planificacion.fechaPlanificacion ");
		TypedQuery<CheckListEjecucionEt> query = em.createQuery(sql.toString(), CheckListEjecucionEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("fechaDesde", SfechaDesde);
		query.setParameter("fechaHasta", SfechaHasta);
		if (usuario != null) {
			query.setParameter("usuario", usuario);
		}
		if (zona != null) {
			query.setParameter("zona", zona);
		}
		if (estacion != null) {
			query.setParameter("estacion", estacion);
		}
		if (evaluacion != null) {
			query.setParameter("evaluacion", evaluacion);
		}
		if (tipoChecKList != null) {
			query.setParameter("tipoChecKList", tipoChecKList);
		}
		if (nivelEvaluacion != null) {
			query.setParameter("nivelEvaluacion", nivelEvaluacion);
		}
		if (estadoCheckList != null && !estadoCheckList.getDescripcion().equals("Todos")) {
			query.setParameter("estadoCheckList", estadoCheckList);
		}
		List<CheckListEjecucionEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CheckListEjecucionEt> getCheckListEjecucionAccesoZonaList(ZonaEt zona, AgenciaEt estacion, EvaluacionEt evaluacion, TipoChecKListEt tipoChecKList, NivelEvaluacionEt nivelEvaluacion, Date fechaDesde, Date fechaHasta, UsuarioEt usuario, EstadoCheckListEnum estadoCheckList)
			throws EntidadNoEncontradaException {

		String SfechaDesde = format.format(fechaDesde);
		String SfechaHasta = format.format(fechaHasta);

		sql = new StringBuilder("FROM CheckListEjecucionEt o ");
		sql.append(" WHERE o.estado = :estado ");
		sql.append(" AND o.planificacion.estado = :estado ");
		sql.append(" AND to_char(o.planificacion.fechaPlanificacion,'yyyy-mm-dd') BETWEEN :fechaDesde AND :fechaHasta ");
		if (zona != null) {
			sql.append(" AND o.planificacion.agencia.zona = :zona ");
		}
		if (usuario != null) {
			sql.append(" AND o.usuarioAsignado = :usuario ");
			sql.append(" AND o.planificacion.agencia.zona in (:zonas) ");
		}
		if (estacion != null) {
			sql.append(" AND o.planificacion.agencia = :estacion ");
		}
		if (evaluacion != null) {
			sql.append(" AND o.evaluacion = :evaluacion ");
		}
		if (tipoChecKList != null) {
			sql.append(" AND o.tipoChecKList = :tipoChecKList ");
		}
		if (nivelEvaluacion != null) {
			sql.append(" AND o.nivelEvaluacion = :nivelEvaluacion ");
		}
		if (estadoCheckList != null && !estadoCheckList.getDescripcion().equals("Todos")) {
			sql.append(" AND o.estadoCheckList = :estadoCheckList ");
		}
		sql.append(" ORDER BY o.planificacion.fechaPlanificacion ");
		TypedQuery<CheckListEjecucionEt> query = em.createQuery(sql.toString(), CheckListEjecucionEt.class);
		List<ZonaEt> zonas = new ArrayList<ZonaEt>();
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("fechaDesde", SfechaDesde);
		query.setParameter("fechaHasta", SfechaHasta);
		if (zona != null) {
			query.setParameter("zona", zona);
		}
		if (usuario != null) {
			for (ZonaUsuarioEt zonaUsuario : usuario.getZonaUsuario()) {
				zonas.add(zonaUsuario.getZona());
			}
			query.setParameter("zonas", zonas);
			query.setParameter("usuario", usuario);
		}
		if (estacion != null) {
			query.setParameter("estacion", estacion);
		}
		if (evaluacion != null) {
			query.setParameter("evaluacion", evaluacion);
		}
		if (tipoChecKList != null) {
			query.setParameter("tipoChecKList", tipoChecKList);
		}
		if (nivelEvaluacion != null) {
			query.setParameter("nivelEvaluacion", nivelEvaluacion);
		}
		if (estadoCheckList != null && !estadoCheckList.getDescripcion().equals("Todos")) {
			query.setParameter("estadoCheckList", estadoCheckList);
		}
		List<CheckListEjecucionEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CheckListEjecucionEt> getCheckListCambioEstadoList(EstadoCheckListEnum estadoCheckList) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM CheckListEjecucionEt o     ");
		sql.append(" WHERE o.estado        = :estado   ");
		sql.append(" AND o.estadoCheckList = :estadoCheckList ");
		sql.append(" ORDER BY o.planificacion.fechaPlanificacion ");
		TypedQuery<CheckListEjecucionEt> query = em.createQuery(sql.toString(), CheckListEjecucionEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("estadoCheckList", estadoCheckList);
		List<CheckListEjecucionEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CheckListEjecucionEt> getCheckListEjecucionListPlanAccion(ZonaEt zona, AgenciaEt estacion, EvaluacionEt evaluacion, TipoChecKListEt tipoChecKList, NivelEvaluacionEt nivelEvaluacion, Date fechaDesde, Date fechaHasta, EstadoPlanAccionEnum estadoPlanAccion)
			throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM CheckListEjecucionEt o     ");
		sql.append(" WHERE o.estado        = :estado   ");
		sql.append(" AND date_trunc('day',o.planificacion.fechaPlanificacion) BETWEEN :fDesde AND :fHasta ");
		sql.append(" AND o.estadoCheckList = :estadoCheckList ");

		if (zona != null) {
			sql.append(" AND o.planificacion.agencia.zona = :zona ");
		}
		if (estacion != null) {
			sql.append(" AND o.planificacion.agencia = :estacion ");
		}
		if (evaluacion != null) {
			sql.append(" AND o.evaluacion = :evaluacion ");
		}
		if (tipoChecKList != null) {
			sql.append(" AND o.tipoChecKList = :tipoChecKList ");
		}
		if (nivelEvaluacion != null) {
			sql.append(" AND o.nivelEvaluacion = :nivelEvaluacion ");
		}
		if (estadoPlanAccion != null && !estadoPlanAccion.getDescripcion().equals("Todos")) {
			sql.append(" AND o.estadoPlanAccion = :estadoPlanAccion ");
		}
		sql.append(" ORDER BY o.planificacion.fechaPlanificacion ");
		TypedQuery<CheckListEjecucionEt> query = em.createQuery(sql.toString(), CheckListEjecucionEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("fDesde", fechaDesde, TemporalType.DATE);
		query.setParameter("fHasta", fechaHasta, TemporalType.DATE);
		query.setParameter("estadoCheckList", EstadoCheckListEnum.EJECUTADO);
		if (estadoPlanAccion != null && !estadoPlanAccion.getDescripcion().equals("Todos")) {
			query.setParameter("estadoPlanAccion", estadoPlanAccion);
		}
		if (zona != null) {
			query.setParameter("zona", zona);
		}
		if (estacion != null) {
			query.setParameter("estacion", estacion);
		}
		if (evaluacion != null) {
			query.setParameter("evaluacion", evaluacion);
		}
		if (tipoChecKList != null) {
			query.setParameter("tipoChecKList", tipoChecKList);
		}
		if (nivelEvaluacion != null) {
			query.setParameter("nivelEvaluacion", nivelEvaluacion);
		}
		List<CheckListEjecucionEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CheckListEjecucionEt> getCheckListEjecucionAccesoZonaListPlanAccion(AgenciaEt estacion, EvaluacionEt evaluacion, TipoChecKListEt tipoChecKList, NivelEvaluacionEt nivelEvaluacion, Date fechaDesde, Date fechaHasta, EstadoPlanAccionEnum estadoPlanAccion, UsuarioEt usuario)
			throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM CheckListEjecucionEt o  ");
		sql.append(" WHERE o.estado        = :estado   ");
		sql.append(" AND date_trunc('day',o.planificacion.fechaPlanificacion) BETWEEN :fDesde AND :fHasta ");
		sql.append(" AND o.estadoCheckList = :estadoCheckList ");
		if (estadoPlanAccion != null && !estadoPlanAccion.getDescripcion().equals("Todos")) {
			sql.append(" AND o.estadoPlanAccion = :estadoPlanAccion ");
		}
		if (!usuario.getZonaUsuario().isEmpty()) {
			sql.append(" AND o.planificacion.agencia.zona in (:zonas) ");
		}
		if (estacion != null) {
			sql.append(" AND o.planificacion.agencia = :estacion ");
		}
		if (evaluacion != null) {
			sql.append(" AND o.evaluacion = :evaluacion ");
		}
		if (tipoChecKList != null) {
			sql.append(" AND o.tipoChecKList = :tipoChecKList ");
		}
		if (nivelEvaluacion != null) {
			sql.append(" AND o.nivelEvaluacion = :nivelEvaluacion ");
		}
		sql.append(" ORDER BY o.planificacion.fechaPlanificacion ");
		TypedQuery<CheckListEjecucionEt> query = em.createQuery(sql.toString(), CheckListEjecucionEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		List<ZonaEt> zonas = new ArrayList<ZonaEt>();
		query.setParameter("fDesde", fechaDesde, TemporalType.DATE);
		query.setParameter("fHasta", fechaHasta, TemporalType.DATE);
		query.setParameter("estadoCheckList", EstadoCheckListEnum.EJECUTADO);
		if (estadoPlanAccion != null && !estadoPlanAccion.getDescripcion().equals("Todos")) {
			query.setParameter("estadoPlanAccion", estadoPlanAccion);
		}
		if (!usuario.getZonaUsuario().isEmpty()) {
			for (ZonaUsuarioEt zonaUsuario : usuario.getZonaUsuario()) {
				zonas.add(zonaUsuario.getZona());
			}
			query.setParameter("zonas", zonas);
		}
		if (estacion != null) {
			query.setParameter("estacion", estacion);
		}
		if (evaluacion != null) {
			query.setParameter("evaluacion", evaluacion);
		}
		if (tipoChecKList != null) {
			query.setParameter("tipoChecKList", tipoChecKList);
		}
		if (nivelEvaluacion != null) {
			query.setParameter("nivelEvaluacion", nivelEvaluacion);
		}
		List<CheckListEjecucionEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CheckListEjecucionEt getCheckListEjecucion(TipoCheckListEnum tipoCheckList, UsuarioEt usuario) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM CheckListEjecucionEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		sql.append(" AND o.usuarioAsignado = :usuario ");
		sql.append(" AND o.ejecutando      = :ejecutando ");
		sql.append(" AND o.tipoCheckList = :tipoCheckList ");
		sql.append(" AND o.estadoCheckList = :estadoCheckList ");
		sql.append(" ORDER BY  o.fechaRegistro desc ");
		TypedQuery<CheckListEjecucionEt> query = em.createQuery(sql.toString(), CheckListEjecucionEt.class);
		query.setParameter("usuario", usuario);
		query.setParameter("ejecutando", true);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("tipoCheckList", tipoCheckList);
		query.setParameter("estadoCheckList", EstadoCheckListEnum.EN_EJECUCION);
		List<CheckListEjecucionEt> result = query.getResultList();
		CheckListEjecucionEt consultado = getUnique(result);
		if (consultado != null) {
			consultado.getCheckListProcesoEjecucion().size();
		}
		return consultado;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CheckListEjecucionEt getCheckListEjecucion(AgenciaEt agencia, String codigo, Date fechaEjecucion) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM CheckListEjecucionEt o ");
		sql.append(" WHERE o.estado = :estado   ");
		sql.append(" AND date_trunc('day',o.fechaEjecucion) < :fechaEjecucion ");
		sql.append(" AND o.planificacion.agencia = :agencia ");
		sql.append(" AND o.codigo   = :codigo ");
		sql.append(" AND o.estadoPlanAccion = :estadoPlanAccion ");
		sql.append(" ORDER BY o.fechaEjecucion DESC ");
		TypedQuery<CheckListEjecucionEt> query = em.createQuery(sql.toString(), CheckListEjecucionEt.class);
		query.setParameter("codigo", codigo);
		query.setParameter("agencia", agencia);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("estadoPlanAccion", EstadoPlanAccionEnum.INGRESADO);
		query.setParameter("fechaEjecucion", fechaEjecucion, TemporalType.DATE);
		query.setMaxResults(1);
		List<CheckListEjecucionEt> result = query.getResultList();
		return getUnique(result);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CheckListEjecucionEt getCheckListEjecucionPlanAccion(TipoCheckListEnum tipoCheckList, AgenciaEt agencia)
			throws EntidadNoEncontradaException {
		List<CheckListKpiEjecucionEt> checkListKpiEjecuciones = new ArrayList<CheckListKpiEjecucionEt>();
		List<CheckListProcesoEjecucionEt> checkListProcesoEjecuciones = new ArrayList<CheckListProcesoEjecucionEt>();
		sql = new StringBuilder("FROM CheckListEjecucionEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		sql.append(" AND o.planAccion       = :planAccion ");
		sql.append(" AND o.planificacion.agencia = :agencia ");
		sql.append(" AND o.tipoCheckList    = :tipoCheckList ");
		sql.append(" AND o.estadoCheckList  = :estadoCheckList ");
		sql.append(" AND o.estadoPlanAccion = :estadoPlanAccion ");
		sql.append(" ORDER BY  o.fechaRegistro desc ");
		TypedQuery<CheckListEjecucionEt> query = em.createQuery(sql.toString(), CheckListEjecucionEt.class);
		query.setParameter("planAccion", true);
		query.setParameter("agencia", agencia);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("tipoCheckList", tipoCheckList);
		query.setParameter("estadoCheckList", EstadoCheckListEnum.EJECUTADO);
		query.setParameter("estadoPlanAccion", EstadoPlanAccionEnum.PENDIENTE);
		List<CheckListEjecucionEt> result = query.getResultList();
		CheckListEjecucionEt consultado = getUnique(result);
		if (consultado != null) {
			consultado.getCheckListProcesoEjecucion().size();
			for (CheckListProcesoEjecucionEt checkListProcesoEjecucion : consultado.getCheckListProcesoEjecucion()) {
				checkListProcesoEjecucion.getCheckListKpiEjecucion().size();
				for (CheckListKpiEjecucionEt checkListKpiEjecucion : checkListProcesoEjecucion.getCheckListKpiEjecucion()) {
					String color = checkListKpiEjecucion.getCriterioEvaluacionDetalle().getColor();
					if (color == null) {
						System.out.println("Ingreso");
					}
					if (color.equals("#8ED21E") || color.equals("#458F32")) {
						checkListKpiEjecuciones.add(checkListKpiEjecucion);
					}
				}
				checkListProcesoEjecucion.getCheckListKpiEjecucion().removeAll(checkListKpiEjecuciones);
				if (checkListProcesoEjecucion.getCheckListKpiEjecucion().isEmpty()) {
					checkListProcesoEjecuciones.add(checkListProcesoEjecucion);
				}
			}
			consultado.getCheckListProcesoEjecucion().removeAll(checkListProcesoEjecuciones);

		}
		return consultado;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CheckListEjecucionEt> getCheckEjecutando(UsuarioEt usuario) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM CheckListEjecucionEt o    ");
		sql.append(" WHERE o.estado        = :estado   ");
		sql.append(" AND o.usuarioAsignado = :usuario ");
		sql.append(" AND o.ejecutando      = :ejecutando ");
		TypedQuery<CheckListEjecucionEt> query = em.createQuery(sql.toString(), CheckListEjecucionEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("usuario", usuario);
		query.setParameter("ejecutando", true);
		List<CheckListEjecucionEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CheckListEjecucionEt> getCheckEjecutandoPlanAccion(UsuarioEt usuario) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM CheckListEjecucionEt o    ");
		sql.append(" WHERE o.estado        = :estado   ");
		sql.append(" AND o.usuarioAsignado = :usuario ");
		sql.append(" AND o.planAccion      = :planAccion ");
		TypedQuery<CheckListEjecucionEt> query = em.createQuery(sql.toString(), CheckListEjecucionEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("usuario", usuario);
		query.setParameter("planAccion", true);
		List<CheckListEjecucionEt> result = query.getResultList();
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CheckListEjecucionEt getCheckListEjecucion(long id) {
		try {
			CheckListEjecucionEt checkListEjecucion = recuperar(id);
			checkListEjecucion.getCheckListProcesoEjecucion().size();
			return checkListEjecucion;
		} catch (EntidadNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long calificacionActual(TipoChecKListEt tipoChecKList, AgenciaEt estacion) {
		Long calificacion = 1L;
		sql = new StringBuilder("FROM CheckListEjecucionEt o   ");
		sql.append(" WHERE o.estado        = :estado   ");
		sql.append(" AND o.tipoChecKList = :tipoChecKList ");
		sql.append(" AND o.planificacion.agencia = :estacion ");
		sql.append(" ORDER BY o.planificacion.fechaPlanificacion desc ");
		TypedQuery<CheckListEjecucionEt> query = em.createQuery(sql.toString(), CheckListEjecucionEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("tipoChecKList", tipoChecKList);
		query.setParameter("estacion", estacion);
		query.setMaxResults(1);
		List<CheckListEjecucionEt> result = query.getResultList();
		CheckListEjecucionEt checkListEjecucion = getUnique(result);
		if (checkListEjecucion != null && checkListEjecucion.getCalificacion() != null) {
			calificacion = checkListEjecucion.getCalificacion();
		}
		return calificacion;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CheckListEjecucionEt> getCheckListPlanAccion(AgenciaEt agencia) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM CheckListEjecucionEt o ");
		sql.append(" WHERE o.estado              = :estado   ");
		sql.append(" AND o.estadoCheckList       = :estadoCheckList ");
		sql.append(" AND o.estadoPlanAccion      = :estadoPlanAccion ");
		sql.append(" AND o.planificacion.agencia = :agencia ");
		TypedQuery<CheckListEjecucionEt> query = em.createQuery(sql.toString(), CheckListEjecucionEt.class);
		query.setParameter("agencia", agencia);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("estadoCheckList", EstadoCheckListEnum.EJECUTADO);
		query.setParameter("estadoPlanAccion", EstadoPlanAccionEnum.PENDIENTE);
		List<CheckListEjecucionEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CheckListEjecucionEt> getCheckListIngresandoPlanAccion(AgenciaEt agencia) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM CheckListEjecucionEt o ");
		sql.append(" WHERE o.estado              = :estado   ");
		sql.append(" AND o.estadoCheckList       = :estadoCheckList ");
		sql.append(" AND o.estadoPlanAccion      = :estadoPlanAccion ");
		sql.append(" AND o.planificacion.agencia = :agencia    ");
		sql.append(" AND o.planAccion            = :planAccion ");
		TypedQuery<CheckListEjecucionEt> query = em.createQuery(sql.toString(), CheckListEjecucionEt.class);
		query.setParameter("agencia", agencia);
		query.setParameter("planAccion", true);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("estadoCheckList", EstadoCheckListEnum.EJECUTADO);
		query.setParameter("estadoPlanAccion", EstadoPlanAccionEnum.PENDIENTE);
		List<CheckListEjecucionEt> result = query.getResultList();
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
