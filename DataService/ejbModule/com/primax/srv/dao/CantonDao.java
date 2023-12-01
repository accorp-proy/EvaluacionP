package com.primax.srv.dao;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.CantonEt;
import com.primax.jpa.param.ProvinciaEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ICantonDao;
import com.primax.srv.util.QUL;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class CantonDao extends GenericDao<CantonEt, Long> implements ICantonDao {

	public CantonDao() {
		super(CantonEt.class);
	}

	private StringBuilder sql;

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CantonEt> getCantonesAsociadosAgencia(ProvinciaEt provincia) {
		sql = new StringBuilder("select distinct o.canton from AgenciaEt o ");
		sql.append("where o.estado = :estado ");
		sql.append("and o.canton.estado =:estado ");
		sql.append("and o.canton.provincia.estado =:estado ");
		sql.append("and o.canton.provincia = :prov ");
		TypedQuery<CantonEt> query = em.createQuery(sql.toString(), CantonEt.class);
		query.setParameter("prov", provincia);
		query.setParameter("estado", EstadoEnum.ACT);
		List<CantonEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CantonEt> getCantones(EstadoEnum estado) {
		sql = new StringBuilder("from CantonEt o ");
		
		sql.append("where o.estado = :estado ");
		sql.append("order by o.nombreCanton asc ");
		TypedQuery<CantonEt> query = em.createQuery(sql.toString(), CantonEt.class);
		query.setParameter("estado", estado);
		List<CantonEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CantonEt> getCantonEtByCondicion(String condicion) {
		sql = new StringBuilder("from CantonEt c ");

		if (condicion != null && !condicion.isEmpty()) {
			sql.append("where (upper(c.nombreCanton)  like :nomCant or ''=:nomCant) ");
			sql.append("or ((c.provincia.nombreProvincia) like :nomCant or ''=:nomCant) ");
			sql.append("or (c.inecCanton like :inec or ''=:inec) ");
		}
		sql.append("order by c.provincia.nombreProvincia, c.nombreCanton ");

		TypedQuery<CantonEt> query = em.createQuery(sql.toString(), CantonEt.class);

		if (condicion != null && !condicion.isEmpty()) {
			query.setParameter("nomCant", "%" + condicion.toUpperCase().trim() + "%");
			query.setParameter("inec", "%" + condicion.toString().trim() + "%");
		}
		query.setMaxResults(30);
		List<CantonEt> result = query.getResultList();

		return result;

	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ProvinciaEt> getRetornaProvincia(String condicion) {
		sql = new StringBuilder("from ProvinciaEt o ");
		if (condicion != null && !condicion.isEmpty()) {
			sql.append("where (o.idProvincia= :idProv or 0= :idProv) ");
			sql.append("and (o.nombreProvincia like :nomProv or ''=:nomProv) ");
		}
		TypedQuery<ProvinciaEt> query = em.createQuery(sql.toString(), ProvinciaEt.class);
		if (condicion != null && !condicion.isEmpty()) {
			query.setParameter("idProv", QUL.toLong(condicion));
			query.setParameter("nomProv", "%" + QUL.getString(condicion.toUpperCase()) + "%");
		}
		List<ProvinciaEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CantonEt> getRetornaCanton(String condicion) {
		sql = new StringBuilder("from CantonEt c");
		if (condicion != null && !condicion.isEmpty()) {
			sql.append(" where c.provincia.idProvincia = :idCondicion or 0= :idCondicion");
			sql.append(" and c.estado = :estado ");
		}
		sql.append("order by c.nombreCanton ");

		TypedQuery<CantonEt> query = em.createQuery(sql.toString(), CantonEt.class);
		if (condicion != null && !condicion.isEmpty()) {
			query.setParameter("idCondicion", QUL.toLong(condicion));
			query.setParameter("estado", EstadoEnum.ACT);
		}

		List<CantonEt> result = query.getResultList();

		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CantonEt getCantonbyId(long id) {
		try {
			CantonEt canton = recuperar(id);
			return canton;
		} catch (EntidadNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CantonEt getInecCanton(Long codProv, String codCanton) {
		sql = new StringBuilder("from CantonEt c");
		sql.append(" where c.inecCanton= :canton ");
		sql.append(" and c.provincia.idProvincia = :prov ");
		sql.append(" and c.estado='ACT'");

		TypedQuery<CantonEt> query = em.createQuery(sql.toString(), CantonEt.class);
		query.setParameter("prov", codProv);
		query.setParameter("canton", codCanton.toString().trim());
		List<CantonEt> result = query.getResultList();
		return getUnique(result);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CantonEt getNombreCanton(String condicion) {
		sql = new StringBuilder("from CantonEt o ");
		sql.append(" where o.nombreCanton= :condicion ");
		TypedQuery<CantonEt> query = em.createQuery(sql.toString(), CantonEt.class);
		query.setParameter("condicion", condicion);
		List<CantonEt> result = query.getResultList();
		return getUnique(result);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CantonEt getCanton(String nombre) {
		List<CantonEt> result = getCantonEtByCondicion(nombre);
		return getUnique(result);
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String limpiarReporte(Long idUsuario) {
		StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("getReporteEvaluacionNivelRiesgo");
		query.setParameter("idUsuario", idUsuario);
		String respuesta = (String) query.getOutputParameterValue("respuesta");
		return respuesta;
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
