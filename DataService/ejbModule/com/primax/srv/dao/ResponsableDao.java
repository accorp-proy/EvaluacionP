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
import com.primax.jpa.gen.PersonaEt;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.ResponsableEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IResponsableDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class ResponsableDao extends GenericDao<ResponsableEt, Long> implements IResponsableDao {

	public ResponsableDao() {
		super(ResponsableEt.class);
	}

	private StringBuilder sql;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarResponsable(ResponsableEt responsable, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (responsable.getIdResponsable() == null) {
			responsable.audit(usuario, ActionAuditedEnum.NEW);
			crear(responsable);
		} else {
			responsable.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(responsable);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ResponsableEt> getResponsableList(String condicion) throws EntidadNoEncontradaException {

		sql = new StringBuilder("FROM ResponsableEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		if (condicion != null && !condicion.isEmpty()) {
			sql.append(" AND ( upper(o.persona.nombreCompleto) like :nombre ");
			sql.append(" OR  o.persona.identificacionPersona = :txt )");
		}
		TypedQuery<ResponsableEt> query = em.createQuery(sql.toString(), ResponsableEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		if (condicion != null && !condicion.isEmpty()) {
			query.setParameter("nombre", "%" + condicion.toUpperCase() + "%");
			query.setParameter("txt", condicion);
		} else {
			query.setMaxResults(20);
		}
		List<ResponsableEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PersonaEt> getResponsableByAgenciaList(AgenciaEt agencia) throws EntidadNoEncontradaException {
		sql = new StringBuilder("SELECT o.persona FROM ResponsableEt o ");
		sql.append(" WHERE o.estado   = :estado  ");
		sql.append(" AND   o.agencia  = :agencia ");
		sql.append(" ORDER BY  o.idResponsable   ");
		TypedQuery<PersonaEt> query = em.createQuery(sql.toString(), PersonaEt.class);
		query.setParameter("agencia", agencia);
		query.setParameter("estado", EstadoEnum.ACT);
		List<PersonaEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ResponsableEt> getResponsableByAgencia1List(AgenciaEt agencia) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM ResponsableEt o ");
		sql.append(" WHERE o.estado   = :estado  ");
		sql.append(" AND   o.agencia  = :agencia ");
		sql.append(" ORDER BY  o.idResponsable   ");
		TypedQuery<ResponsableEt> query = em.createQuery(sql.toString(), ResponsableEt.class);
		query.setParameter("agencia", agencia);
		query.setParameter("estado", EstadoEnum.ACT);
		List<ResponsableEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ResponsableEt getResponsableByCargo(AgenciaEt agencia, Long idTipoCargo) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM ResponsableEt o ");
		sql.append(" WHERE o.estado   = :estado  ");
		sql.append(" AND   o.agencia  = :agencia ");
		sql.append(" AND   o.tipoCargo.idTipoCargo  = :idTipoCargo ");
		sql.append(" ORDER BY  o.idResponsable   ");
		TypedQuery<ResponsableEt> query = em.createQuery(sql.toString(), ResponsableEt.class);
		query.setParameter("agencia", agencia);
		query.setParameter("idTipoCargo", idTipoCargo);
		query.setParameter("estado", EstadoEnum.ACT);
		List<ResponsableEt> result = query.getResultList();
		return getUnique(result);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ResponsableEt getResponsableByAgencia(AgenciaEt agencia) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM ResponsableEt o ");
		sql.append(" WHERE o.estado   = :estado  ");
		sql.append(" AND   o.agencia  = :agencia ");
		sql.append(" AND   o.tipoCargo.idTipoCargo  = 1 ");
		TypedQuery<ResponsableEt> query = em.createQuery(sql.toString(), ResponsableEt.class);
		query.setParameter("agencia", agencia);
		query.setParameter("estado", EstadoEnum.ACT);
		List<ResponsableEt> result = query.getResultList();
		return getUnique(result);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ResponsableEt getResponsable(long id) {
		try {
			ResponsableEt responsable = recuperar(id);
			return responsable;
		} catch (EntidadNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ResponsableEt getResponsableEstacion(PersonaEt persona) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM ResponsableEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		sql.append(" AND o.persona   = :persona ");
		TypedQuery<ResponsableEt> query = em.createQuery(sql.toString(), ResponsableEt.class);
		query.setParameter("persona", persona);
		query.setParameter("estado", EstadoEnum.ACT);
		List<ResponsableEt> result = query.getResultList();
		ResponsableEt consultado = getUnique(result);
		return consultado;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String getResponsableEstacionS(PersonaEt persona) throws EntidadNoEncontradaException {
		String estacion = "";
		sql = new StringBuilder("FROM ResponsableEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		sql.append(" AND o.persona   = :persona ");
		TypedQuery<ResponsableEt> query = em.createQuery(sql.toString(), ResponsableEt.class);
		query.setParameter("persona", persona);
		query.setParameter("estado", EstadoEnum.ACT);
		List<ResponsableEt> result = query.getResultList();
		ResponsableEt consultado = getUnique(result);
		if (consultado != null) {
			estacion = consultado.getAgencia().getNombreAgencia();
		}
		return estacion;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ResponsableEt> getResponsableByAgencia2List(AgenciaEt agencia) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM ResponsableEt o ");
		sql.append(" WHERE o.estado   = :estado  ");
		sql.append(" AND   o.agencia  = :agencia ");
		sql.append(" ORDER BY  o.tipoCargo.idTipoCargo  ");
		TypedQuery<ResponsableEt> query = em.createQuery(sql.toString(), ResponsableEt.class);
		query.setParameter("agencia", agencia);
		query.setParameter("estado", EstadoEnum.ACT);
		List<ResponsableEt> result = query.getResultList();
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
