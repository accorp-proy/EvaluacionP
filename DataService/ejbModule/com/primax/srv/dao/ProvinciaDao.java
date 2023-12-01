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
import com.primax.jpa.param.ProvinciaEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IProvinciaDao;
import com.primax.srv.util.QUL;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class ProvinciaDao extends GenericDao<ProvinciaEt, Long> implements IProvinciaDao {

	public ProvinciaDao() {
		super(ProvinciaEt.class);
	}

	private StringBuilder sql;

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ProvinciaEt> getProvinciasAsociadasAgencia() {
		sql = new StringBuilder("select distinct o.canton.provincia from AgenciaEt o ");
		sql.append("where o.estado = :estado ");
		sql.append("and o.canton.estado = :estado ");
		sql.append("and o.canton.provincia.estado = :estado ");
		sql.append("order by o.canton.provincia.nombreProvincia ");
		TypedQuery<ProvinciaEt> query = em.createQuery(sql.toString(), ProvinciaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		List<ProvinciaEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ProvinciaEt> getProvinciasByCondicion(String condicion) {
		sql = new StringBuilder("select o from ProvinciaEt o ");
		sql.append("where o.estado= :estado ");

		if (condicion != null && !condicion.isEmpty()) {
			sql.append("and (o.idProvincia= :idProv or 0= :idProv) ");
			sql.append("and (o.nombreProvincia like :nomProv or ''=:nomProv) ");
		}

		sql.append("order by o.nombreProvincia ");
		TypedQuery<ProvinciaEt> query = em.createQuery(sql.toString(), ProvinciaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);

		if (condicion != null && !condicion.isEmpty()) {
			query.setParameter("idProv", QUL.toLong(condicion));
			query.setParameter("nomProv", "%" + QUL.getString(condicion.toUpperCase()) + "%");
		}

		List<ProvinciaEt> result = query.getResultList();

		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ProvinciaEt getProvincia(long id) {
		try {
			ProvinciaEt provincia = recuperar(id);
			provincia.getCantones().size();
			return provincia;
		} catch (EntidadNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ProvinciaEt getInecProvincia(String cod) {
		sql = new StringBuilder("from ProvinciaEt p ");
		sql.append(" where p.inecProvincia = :condicion");
		sql.append(" and p.estado='ACT'");
		TypedQuery<ProvinciaEt> query = em.createQuery(sql.toString(), ProvinciaEt.class);
		query.setParameter("condicion", (cod.trim()));
		List<ProvinciaEt> result = query.getResultList();
		return getUnique(result);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ProvinciaEt getProvincia(String nombre) {
		List<ProvinciaEt> result = getProvinciasByCondicion(nombre);
		return getUnique(result);
	}

	public void clear() {
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String limpiarReporte(Long idUsuario) {
		StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("getReporte");
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
