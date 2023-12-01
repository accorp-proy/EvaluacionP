package com.primax.srv.dao;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;

import com.primax.enm.gen.TipoPersonaEnum;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.gen.PersonaEt;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IPersonaDao;
import com.primax.srv.idao.IUbicacionDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class PersonaDao extends GenericDao<PersonaEt, Long> implements IPersonaDao {

	public PersonaDao() {
		super(PersonaEt.class);
	}

	@EJB
	private IUbicacionDao iUbicacionDao;

	private StringBuilder sql;

	public List<PersonaEt> getPersonasCondicion(String txt) {
		sql = new StringBuilder("from PersonaEt o ");
		sql.append(" WHERE  o.estado =  :estado ");
		if (txt != null && !txt.isEmpty()) {
			sql.append("AND (upper(o.nombreCompleto) like :nombre ");
			sql.append("or o.identificacionPersona = :txt )");
		}
		TypedQuery<PersonaEt> query = em.createQuery(sql.toString(), PersonaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		if (txt != null && !txt.isEmpty()) {
			query.setParameter("nombre", "%" + txt.toUpperCase() + "%");
			query.setParameter("txt", txt);
		} else {
			query.setMaxResults(20);
		}
		List<PersonaEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PersonaEt> getListPersona(TipoPersonaEnum tipoPersonaEnum, String condicion) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM PersonaEt  o ");
		sql.append(" WHERE o.estado      = :estado ");
		sql.append(" AND   o.tipoPersona = :tipoPersonaEnum ");
		if (condicion != null && !condicion.isEmpty()) {
			sql.append(" AND ( (UPPER(o.nombreCompleto)|| UPPER(o.nombreComercial)) LIKE :condicion ");
			sql.append(" OR  o.identificacionPersona  = :condicion1 ) ");
		}
		TypedQuery<PersonaEt> query = em.createQuery(sql.toString(), PersonaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("tipoPersonaEnum", tipoPersonaEnum);
		if (condicion != null && !condicion.isEmpty()) {
			query.setParameter("condicion", "%" + condicion.toUpperCase() + "%");
			query.setParameter("condicion1", condicion);
		} else {
			query.setMaxResults(100);
		}
		List<PersonaEt> result = query.getResultList();

		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PersonaEt> getListPersonaCliente(TipoPersonaEnum tipoPersonaEnum, String condicion) throws EntidadNoEncontradaException {
		sql = new StringBuilder("SELECT p FROM ClienteEt o INNER JOIN  o.persona p ");
		sql.append(" WHERE o.persona = p  ");
		sql.append(" AND   o.estado  = :estado ");
		sql.append(" AND   o.persona.tipoPersona = :tipoPersonaEnum ");
		sql.append(" AND  (o.persona.idPersona <> 56  )  ");
		sql.append(" AND  (o.estadoJDE = 'C'  ) ");
		if (condicion != null && !condicion.isEmpty()) {
			sql.append(" AND ( (UPPER(o.persona.nombreCompleto)|| UPPER(o.persona.nombreComercial)) LIKE :condicion ");
			sql.append(" OR  o.persona.identificacionPersona  = :condicion1 ) ");
			boolean isNumero = this.isNumeric(condicion);
			if (isNumero) {
				sql.append(" OR  o.idClienteJDE  = :condicion2 ");
			}
		}
		sql.append(" GROUP BY p ");
		sql.append(" ORDER BY p ");
		TypedQuery<PersonaEt> query = em.createQuery(sql.toString(), PersonaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("tipoPersonaEnum", tipoPersonaEnum);
		if (condicion != null && !condicion.isEmpty()) {
			query.setParameter("condicion", "%" + condicion.toUpperCase() + "%");
			query.setParameter("condicion1", condicion);
			boolean isNumero = this.isNumeric(condicion);
			if (isNumero) {
				query.setParameter("condicion2", Long.parseLong(condicion));
			}
		} else {
			query.setMaxResults(20);
		}
		List<PersonaEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PersonaEt> getListPersonaByTipo(TipoPersonaEnum tipoPersonaEnum) throws EntidadNoEncontradaException {
		sql = new StringBuilder("SELECT p FROM ClienteEt o INNER JOIN  o.persona p ");
		sql.append(" WHERE o.persona = p  ");
		sql.append(" AND   o.estado  = :estado ");
		sql.append(" AND   o.persona.tipoPersona = :tipoPersonaEnum ");
		sql.append(" AND   (o.persona.idPersona <> 56  )   ");
		sql.append(" AND   (o.fechaUltimoCorte is null )  ");
		sql.append(" GROUP BY p ");
		sql.append(" ORDER BY p ");
		TypedQuery<PersonaEt> query = em.createQuery(sql.toString(), PersonaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("tipoPersonaEnum", tipoPersonaEnum);
		query.setMaxResults(50);
		List<PersonaEt> result = query.getResultList();
		return result;
	}
	// sql.append(" AND o.persona.idPersona <> 56 ");
	// sql.append(" AND o.persona.idPersona in (122,15,20) ");

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PersonaEt> getListPersonaByTipoId(TipoPersonaEnum tipoPersonaEnum) throws EntidadNoEncontradaException {
		sql = new StringBuilder("SELECT p FROM ClienteEt o INNER JOIN  o.persona p ");
		sql.append(" WHERE o.persona = p  ");
		sql.append(" AND   o.estado  = :estado ");
		sql.append(" AND   o.persona.tipoPersona = :tipoPersonaEnum ");
		sql.append(" AND   (o.persona.idPersona <> 56  )   ");
		sql.append(" AND   (o.fechaUltimoCorte is null )  ");
		sql.append(" GROUP BY p ");
		sql.append(" ORDER BY p ");
		TypedQuery<PersonaEt> query = em.createQuery(sql.toString(), PersonaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("tipoPersonaEnum", tipoPersonaEnum);
		List<PersonaEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PersonaEt> getListPersonaByTipoFechaAct(TipoPersonaEnum tipoPersonaEnum) throws EntidadNoEncontradaException {
		sql = new StringBuilder("SELECT p FROM ClienteEt o INNER JOIN  o.persona p ");
		sql.append(" WHERE o.persona = p  ");
		sql.append(" AND   o.estado  = :estado ");
		sql.append(" AND   o.persona.tipoPersona = :tipoPersonaEnum ");
		sql.append(" AND   (o.persona.idPersona <> 56  )   ");
		sql.append(" AND   (o.fechaUltimaActualizacion is null ) ");
		sql.append(" GROUP BY p ");
		sql.append(" ORDER BY p ");
		TypedQuery<PersonaEt> query = em.createQuery(sql.toString(), PersonaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("tipoPersonaEnum", tipoPersonaEnum);
		List<PersonaEt> result = query.getResultList();
		return result;
	}
	//
	// sql.append(" AND (o.persona.idPersona in
	// (16,17,19,20,22,25,26,28,29,30,31,32,40,42)) ");

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PersonaEt> getListPersonaByTipoFechaAct01(TipoPersonaEnum tipoPersonaEnum) throws EntidadNoEncontradaException {
		sql = new StringBuilder("SELECT p FROM ClienteEt o INNER JOIN  o.persona p ");
		sql.append(" WHERE o.persona = p  ");
		sql.append(" AND   o.estado  = :estado ");
		sql.append(" AND   o.persona.tipoPersona = :tipoPersonaEnum ");
		sql.append(" AND   (o.persona.idPersona <> 56  )   ");
		sql.append(" AND   (o.fechaUltimaActualizacion is not null ) ");
		sql.append(" GROUP BY p ");
		sql.append(" ORDER BY p ");
		TypedQuery<PersonaEt> query = em.createQuery(sql.toString(), PersonaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("tipoPersonaEnum", tipoPersonaEnum);
		List<PersonaEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PersonaEt> getListPersonas(TipoPersonaEnum tipoPersonaEnum) throws EntidadNoEncontradaException {
		sql = new StringBuilder("SELECT p FROM ClienteEt o INNER JOIN  o.persona p ");
		sql.append(" WHERE o.persona = p  ");
		sql.append(" AND   o.persona.tipoPersona = :tipoPersonaEnum ");
		sql.append(" AND   (o.persona.idPersona <> 56  )   ");
		sql.append(" GROUP BY p ");
		sql.append(" ORDER BY p ");
		TypedQuery<PersonaEt> query = em.createQuery(sql.toString(), PersonaEt.class);
		query.setParameter("tipoPersonaEnum", tipoPersonaEnum);
		List<PersonaEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarPersona(PersonaEt persona, UsuarioEt usuario) throws EntidadNoGrabadaException {
		try {

			if (persona.getUbicacion() != null) {
				iUbicacionDao.guardarUbicacion(persona.getUbicacion());
				persona.setUbicacion(persona.getUbicacion());
			}
			if (persona.getIdPersona() == null) {
				this.crear(persona);
			} else {
				this.actualizar(persona);
			}

			em.flush();
			em.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizarPersona(PersonaEt persona, UsuarioEt usuario) throws EntidadNoGrabadaException {

		try {

			iUbicacionDao.actualizar(persona.getUbicacion());
			persona.setUbicacion(persona.getUbicacion());
			this.actualizar(persona);
			em.flush();
			em.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<PersonaEt> getPersonasUsuario(String txt) {
		sql = new StringBuilder("select o from PersonaEt o ");
		if (txt == null || txt.isEmpty()) {
			sql.append("WHERE o.estado = :estado ");
		} else {
			sql.append("WHERE (upper(o.nombreCompleto) like :txt ");
			sql.append("OR o.identificacionPersona like :txt )");
			sql.append("AND o.estado = :estado ");
		}
		TypedQuery<PersonaEt> query = em.createQuery(sql.toString(), PersonaEt.class);
		if (txt != null && !txt.isEmpty()) {
			query.setParameter("txt", "%" + txt.toUpperCase() + "%");
		} else {
			query.setMaxResults(20);
		}
		query.setParameter("estado", EstadoEnum.ACT);
		List<PersonaEt> result = query.getResultList();
		return result;
	}

	public PersonaEt getPersonasPorCedula(String cedula) {
		sql = new StringBuilder("from PersonaEt o ");
		sql.append("where o.identificacionPersona = :cedula ");
		sql.append("AND o.estado = :estado ");
		TypedQuery<PersonaEt> query = em.createQuery(sql.toString(), PersonaEt.class);
		query.setParameter("cedula", cedula);
		query.setParameter("estado", EstadoEnum.ACT);
		List<PersonaEt> result = query.getResultList();
		return result.size() > 0 ? result.get(0) : null;
	}

	public PersonaEt getPersonasPorCedulaR(String cedula) {
		sql = new StringBuilder("SELECT o.persona from ResponsableEt o ");
		sql.append("where o.persona.identificacionPersona = :cedula ");
		sql.append("AND o.estado = :estado ");
		TypedQuery<PersonaEt> query = em.createQuery(sql.toString(), PersonaEt.class);
		query.setParameter("cedula", cedula);
		query.setParameter("estado", EstadoEnum.ACT);
		List<PersonaEt> result = query.getResultList();
		return result.size() > 0 ? result.get(0) : null;
	}

	public PersonaEt getPersonaById(Long idPersona) {
		sql = new StringBuilder("FROM PersonaEt o ");
		sql.append("WHERE o.idPersona = :idPersona ");
		sql.append("AND   o.estado = :estado ");
		TypedQuery<PersonaEt> query = em.createQuery(sql.toString(), PersonaEt.class);
		query.setParameter("idPersona", idPersona);
		query.setParameter("estado", EstadoEnum.ACT);
		List<PersonaEt> result = query.getResultList();
		return result.size() > 0 ? result.get(0) : null;
	}

	public boolean isNumeric(String cadena) {
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PersonaEt getResponsableByAgencia(AgenciaEt agencia) throws EntidadNoEncontradaException {
		sql = new StringBuilder("SELECT o.persona FROM ResponsableEt o ");
		sql.append(" WHERE o.estado   = :estado  ");
		sql.append(" AND   o.agencia  = :agencia ");
		sql.append(" AND   o.tipoCargo.idTipoCargo  = 1 ");
		TypedQuery<PersonaEt> query = em.createQuery(sql.toString(), PersonaEt.class);
		query.setParameter("agencia", agencia);
		query.setParameter("estado", EstadoEnum.ACT);
		List<PersonaEt> result = query.getResultList();
		return getUnique(result);
	}

	@Remove
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void remove() {
		iUbicacionDao.remove();
		System.out.println("Finalizado Statefull Bean : " + this.getClass().getCanonicalName());
	}

	@PreDestroy
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void detached() {
		System.out.println("Terminado Statefull Bean : " + this.getClass().getCanonicalName());
	}
}
