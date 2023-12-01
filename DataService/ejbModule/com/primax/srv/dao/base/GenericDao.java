package com.primax.srv.dao.base;

import java.io.Serializable;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.exc.gen.EntidadNoBorradaException;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;

public class GenericDao<T, P extends Serializable> extends BaseNaming implements IGenericDao<T, P> {

	@PersistenceContext(unitName = "primax", type = PersistenceContextType.EXTENDED)
	protected EntityManager em;

	private final Class<T> tipo;

	public GenericDao(final Class<T> type) {
		this.tipo = type;
	}

	@Override
	public void crearObject(Object o) throws EntidadNoGrabadaException {
		try {
			em.persist(o);
		} catch (final PersistenceException e) {
			throw new PersistenceException("Error al grabar: ".concat(o.toString()), e);
		}
	}

	@Override
	public void crear(T o) throws EntidadNoGrabadaException {
		try {
			em.persist(o);
		} catch (final PersistenceException e) {
			throw new PersistenceException("Error al grabar: ".concat(o.toString()), e);
		}
	}

	@Override
	public void actualizarObject(Object o) throws EntidadNoGrabadaException {
		try {
			em.merge(o);
		} catch (final PersistenceException e) {
			throw new EntidadNoGrabadaException("Error al actualizar: ".concat(o.toString()), e);
		}
	}

	@Override
	public void actualizar(final T o) throws EntidadNoGrabadaException {
		try {
			em.merge(o);
		} catch (final PersistenceException e) {
			throw new EntidadNoGrabadaException("Error al actualizar: ".concat(o.toString()), e);
		}
	}

	@Override
	public void eliminar(T o) throws EntidadNoBorradaException {
		em.merge(o);
		em.remove(o);
	}

	@Override
	public void eliminarObject(Object o) throws EntidadNoBorradaException {
		em.merge(o);
		em.remove(o);
	}

	@Override
	public T recuperar(final P id) throws EntidadNoEncontradaException {
		final T entidad = em.find(tipo, id);
		if (entidad == null) {
			final StringBuffer msg = new StringBuffer();
			msg.append(tipo.getSimpleName());
			msg.append('[');
			msg.append(id.toString());
			msg.append("] no encontrada.");
			throw new EntidadNoEncontradaException(msg.toString());
		}
		return entidad;
	}

	public int contar(EstadoEnum estado) {
		StringBuilder sql = new StringBuilder("select count(m) from " + tipo.getSimpleName() + " m ");
		sql.append(" where m.estado=:estado ");
		int count = ((Long) em.createQuery(sql.toString()).setParameter("estado", estado).getSingleResult()).intValue();
		return count;
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<T> obtenerTodos() {
		final String className = tipo.getSimpleName();
		final StringBuffer sql = new StringBuffer();
		sql.append("from ").append(className);
		final Query query = em.createQuery(sql.toString());
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public T getUnique(List<?> result) {
		if (!result.isEmpty()) {
			return (T) result.get(0);
		}
		return null;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void clear() {
		em.clear();
	}

}
