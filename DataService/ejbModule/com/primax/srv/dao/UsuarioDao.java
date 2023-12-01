package com.primax.srv.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.gen.PersonaEt;
import com.primax.jpa.param.ProvinciaEt;
import com.primax.jpa.sec.RolUsuarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IPersonaDao;
import com.primax.srv.idao.IRolUsuarioDao;
import com.primax.srv.idao.IUsuarioDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class UsuarioDao extends GenericDao<UsuarioEt, Long> implements IUsuarioDao {

	@EJB
	private IRolUsuarioDao iRolUsuarioDao;

	@EJB
	private IPersonaDao iPersonaDao;

	private StringBuilder sql;

	public UsuarioDao() {
		super(UsuarioEt.class);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public UsuarioEt getUsuarioPorEmailNombre(String user) {
		StringBuilder sql = new StringBuilder("from UsuarioEt o ");
		sql.append("where o.estado = :estado ");
		sql.append("and (o.nombreUsuario = :txt or o.personaUsuario.email = :txt) ");
		TypedQuery<UsuarioEt> query = em.createQuery(sql.toString(), UsuarioEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("txt", user != null ? user.toUpperCase() : user);
		List<UsuarioEt> result = query.getResultList();
		return getUnique(result);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public boolean existeUsuarioCodigo(Long codigo) {
		StringBuilder sql = new StringBuilder("select count(o) from UsuarioEt o ");
		sql.append("where o.estado = :estado ");
		sql.append("and o.personaUsuario.codigoCuenta = :codigo ");
		TypedQuery<Long> query = em.createQuery(sql.toString(), Long.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("codigo", codigo);
		Long result = query.getSingleResult();
		if (result.equals(0L))
			return false;
		else
			return true;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public UsuarioEt validarUsuario(String user, String pass) {
		StringBuilder sql = new StringBuilder("from UsuarioEt o ");
		sql.append("where o.nombreUsuario = :user ");
		sql.append("and o.pwdUsuario = :pass ");
		sql.append("and o.estado = :estado ");
		TypedQuery<UsuarioEt> query = em.createQuery(sql.toString(), UsuarioEt.class);
		query.setParameter("user", user);
		query.setParameter("pass", pass);
		query.setParameter("estado", EstadoEnum.ACT);
		List<UsuarioEt> result = query.getResultList();
		return getUnique(result);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public UsuarioEt getUsuarioById(String nombre) {
		sql = new StringBuilder("select distinct(o) from UsuarioEt o left outer join fetch o.rolesUsario p ");
		sql.append("where o.nombreUsuario=:nombre ");
		sql.append("and o.estado=:estado ");
		sql.append("and p.estado=:estado ");
		TypedQuery<UsuarioEt> query = em.createQuery(sql.toString(), UsuarioEt.class);
		query.setParameter("nombre", nombre);
		query.setParameter("estado", EstadoEnum.ACT);
		List<UsuarioEt> resultUsuario = query.getResultList();
		return getUnique(resultUsuario);
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public UsuarioEt getUsuarioByIdPersona(PersonaEt persona) {
		sql = new StringBuilder("FROM UsuarioEt o ");
		sql.append(" WHERE o.personaUsuario = :persona ");
		sql.append(" AND o.estado = :estado ");
		TypedQuery<UsuarioEt> query = em.createQuery(sql.toString(), UsuarioEt.class);
		query.setParameter("persona", persona);
		query.setParameter("estado", EstadoEnum.ACT);
		List<UsuarioEt> resultUsuario = query.getResultList();
		return getUnique(resultUsuario);
	}
	

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public UsuarioEt getUsuarioByNombreAll(String nombre) {
		sql = new StringBuilder("select distinct(o) from UsuarioEt o left outer join fetch o.rolesUsario p ");
		sql.append("where o.nombreUsuario=:nombre ");
		TypedQuery<UsuarioEt> query = em.createQuery(sql.toString(), UsuarioEt.class);
		query.setParameter("nombre", nombre);
		List<UsuarioEt> resultUsuario = query.getResultList();
		return getUnique(resultUsuario);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<UsuarioEt> getUsuariosByCondicion(String condicion) {
		sql = new StringBuilder("select distinct(o) from UsuarioEt o ");

		sql.append("where (upper(o.nombreUsuario) like :cond or :cond is null) or ");
		sql.append("(upper(o.personaUsuario.nombreCompleto) like :cond or :cond is null) or ");
		sql.append("(upper(o.personaUsuario.identificacionPersona) like :cond or :cond is null) ");

		sql.append("order by o.nombreUsuario ");
		TypedQuery<UsuarioEt> query = em.createQuery(sql.toString(), UsuarioEt.class);

		query.setParameter("cond", condicion == null ? null : "%" + condicion.toUpperCase() + "%");

		List<UsuarioEt> result = query.getResultList();

		for (UsuarioEt user : result) {
			sql = new StringBuilder("from RolUsuarioEt o ");
			sql.append("where o.usuario= :user ");
			sql.append("and o.estado= :estado ");
			TypedQuery<RolUsuarioEt> query_0 = em.createQuery(sql.toString(), RolUsuarioEt.class);
			query_0.setParameter("user", user);
			query_0.setParameter("estado", EstadoEnum.ACT);
			user.setRolesUsario(query_0.getResultList());
		}

		return result;
	}

	public void guardarNuevaContrasena(UsuarioEt usuario) throws Exception {

		if (usuario.getIdUsuario() != null) {
			this.actualizar(usuario);
		}

	}

	@Override
	public void crearUsuarioSistema(UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (usuario.getIdUsuario() != null) {
			iPersonaDao.actualizar(usuario.getPersonaUsuario());
			actualizar(usuario);
		} else {
			iPersonaDao.actualizar(usuario.getPersonaUsuario());
			crear(usuario);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public UsuarioEt comprobarExisteUsuario(String nombre) {
		sql = new StringBuilder("from UsuarioEt o ");
		sql.append("where o.nombreUsuario= :name ");
		TypedQuery<UsuarioEt> query = em.createQuery(sql.toString(), UsuarioEt.class);
		query.setParameter("name", nombre);
		UsuarioEt user = getUnique(query.getResultList());
		return user;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<UsuarioEt> getUsuariosbyProvincia(ProvinciaEt provincia, String condicion) {
		sql = new StringBuilder("from UsuarioEt o ");

		sql.append("Where o.estado = :estado ");

		if (provincia != null) {
			sql.append("and o.oficina.provinciaOficina= :prov ");
		}

		if (condicion != null && !condicion.isEmpty()) {

			sql.append(
					" and ( (o.personaUsuario.primerApellido ||' '||  o.personaUsuario.segundoApellido ||' '||  o.personaUsuario.primerNombre ||' '|| o.personaUsuario.segundoNombre  like :condicion or '' =:condicion )  ");
			sql.append(" or (o.personaUsuario.identificacionPersona like :condicion or '' =:condicion))  ");

		}

		TypedQuery<UsuarioEt> query = em.createQuery(sql.toString(), UsuarioEt.class);

		query.setParameter("estado", EstadoEnum.ACT);

		if (provincia != null) {
			query.setParameter("prov", provincia);
		}
		if (condicion != null && !condicion.isEmpty()) {

			query.setParameter("condicion", "%" + condicion.toUpperCase() + "%");

		}
		query.setMaxResults(20);

		List<UsuarioEt> result = query.getResultList();
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public UsuarioEt getUsuarioId(long id) {
		try {
			UsuarioEt us = recuperar(id);
			return us;
		} catch (EntidadNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<UsuarioEt> returnUltimoAcceso(Date fecha) {
		if (fecha != null) {
			try {
				sql = new StringBuilder("from UsuarioEt u ");
				sql.append(
						"where ((u.fechaRegistro >= :fecha or u.fechaRegistro is null) or  (u.fechaModificacion >= :fecha or u.fechaModificacion is null)) ");
				sql.append("and u.estado = 'ACT' ");
				TypedQuery<UsuarioEt> query = em.createQuery(sql.toString(), UsuarioEt.class);

				query.setParameter("fecha", fecha);

				List<UsuarioEt> result = query.getResultList();

				return result;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String getUsuarioActividadMensual(UsuarioEt usuario) {

		String formatFecha = null;

		Date ultimoAcceso = null;
		try {

			sql = new StringBuilder("from UsuarioEt o ");
			sql.append("where o.personaUsuario = :persona ");
			sql.append("and o.estado = :estado ");

			TypedQuery<UsuarioEt> query = em.createQuery(sql.toString(), UsuarioEt.class);
			query.setParameter("persona", usuario.getPersonaUsuario());
			query.setParameter("estado", EstadoEnum.ACT);

			List<UsuarioEt> result = query.getResultList();

			if (!result.isEmpty()) {
				ultimoAcceso = result.get(0).getUltimoAcceso();
			}

			if (ultimoAcceso != null) {
				DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				formatFecha = fechaHora.format(ultimoAcceso);
			} else {
				formatFecha = " ";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return formatFecha;

	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<UsuarioEt> getUsuariosOrdenados() {
		sql = new StringBuilder("from UsuarioEt o ");
		sql.append(" order by o.personaUsuario.primerApellido, o.personaUsuario.segundoApellido, o.personaUsuario.primerNombre ");
		TypedQuery<UsuarioEt> query = em.createQuery(sql.toString(), UsuarioEt.class);

		List<UsuarioEt> result = query.getResultList();
		return result;

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardar(UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (usuario.getIdUsuario() == null) {
			if (usuario.getPersonaUsuario().getIdPersona() == null) {
				crearObject(usuario.getPersonaUsuario());
			} else {
				actualizarObject(usuario.getPersonaUsuario());
			}
			crear(usuario);
		} else {
			if (usuario.getPersonaUsuario().getIdPersona() == null) {
				crearObject(usuario.getPersonaUsuario());
			} else {
				actualizarObject(usuario.getPersonaUsuario());
			}

			actualizar(usuario);
		}
		em.flush();
		em.clear();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<UsuarioEt> getUsuarioAuditorList() {
		StringBuilder sql = new StringBuilder("SELECT o.usuario FROM RolUsuarioEt o ");
		sql.append("WHERE o.estado = :estado ");
		sql.append("AND o.rol.idRol  in (7,8) ");
		sql.append("ORDER BY o.usuario.idUsuario ");
		TypedQuery<UsuarioEt> query = em.createQuery(sql.toString(), UsuarioEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		List<UsuarioEt> result = query.getResultList();
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
