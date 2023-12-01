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

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.ParametrosGeneralesEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IParametrolGeneralDao;
import com.primax.srv.util.QUL;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class ParametroGeneralDao extends GenericDao<ParametrosGeneralesEt, Long> implements IParametrolGeneralDao {

	public ParametroGeneralDao() {
		super(ParametrosGeneralesEt.class);
	}

	private StringBuilder sql;
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarParametro(ParametrosGeneralesEt parametro, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (parametro.getIdParametroGeneral() == null) {
			parametro.audit(usuario, ActionAuditedEnum.NEW);
			crear(parametro);
		} else {
			parametro.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(parametro);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ParametrosGeneralesEt> getListaParametro(String condicion, Long nivel) {
		sql = new StringBuilder("from ParametrosGeneralesEt p ");
		sql.append("where p.nivel = :nivel ");

		if (condicion != null && !condicion.isEmpty()) {
			sql.append(" and (p.idParametroGeneral = :idCondicion or 0= :idCondicion) ");
			sql.append("and (p.nombreLista like :nomLista or ''= :nomLista) ");
		}

		TypedQuery<ParametrosGeneralesEt> query = em.createQuery(sql.toString(), ParametrosGeneralesEt.class);
		query.setParameter("nivel", nivel);
		if (condicion != null && !condicion.isEmpty()) {
			query.setParameter("idCondicion", QUL.toLong(condicion));
			query.setParameter("nomLista", "%" + QUL.getString(condicion).toUpperCase() + "%");

		}

		List<ParametrosGeneralesEt> result = query.getResultList();

		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ParametrosGeneralesEt> getListaHIjos(ParametrosGeneralesEt par) {
		sql = new StringBuilder("from ParametrosGeneralesEt p ");
		sql.append("where p.nivel = 2 ");
		if (par != null) {
			sql.append(" and (p.parametroPadre = :idCondicion ) ");
		}
		sql.append(" and p.estado='ACT' ");
		sql.append("order by p.orden");
		TypedQuery<ParametrosGeneralesEt> query = em.createQuery(sql.toString(), ParametrosGeneralesEt.class);
		if (par != null) {
			query.setParameter("idCondicion", par);
		}
		List<ParametrosGeneralesEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ParametrosGeneralesEt> getListAnioVariacion(ParametrosGeneralesEt par, Long anio) {
		sql = new StringBuilder("from ParametrosGeneralesEt p ");
		sql.append(" WHERE p.nivel = 2 ");
		sql.append(" AND p.orden < :anio ");
		sql.append(" AND p.parametroPadre = :idCondicion  ");
		sql.append(" AND p.estado = :estado ");
		sql.append(" ORDER BY p.orden");
		TypedQuery<ParametrosGeneralesEt> query = em.createQuery(sql.toString(), ParametrosGeneralesEt.class);
		query.setParameter("anio", anio);
		query.setParameter("idCondicion", par);
		query.setParameter("estado", EstadoEnum.ACT);
		List<ParametrosGeneralesEt> result = query.getResultList();
		query.setMaxResults(2);
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ParametrosGeneralesEt getListaHIjosUnique(ParametrosGeneralesEt par) {
		sql = new StringBuilder("from ParametrosGeneralesEt p ");
		sql.append("where p.nivel = 2 ");
		if (par != null) {
			sql.append(" and (p.parametroPadre = :idCondicion ) ");
		}
		sql.append(" and p.estado='ACT' ");
		sql.append("order by p.idParametroGeneral");
		TypedQuery<ParametrosGeneralesEt> query = em.createQuery(sql.toString(), ParametrosGeneralesEt.class);
		if (par != null) {
			query.setParameter("idCondicion", par);
		}
		List<ParametrosGeneralesEt> result = query.getResultList();
		return getUnique(result);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ParametrosGeneralesEt getObjPadre(String condicion) {
		sql = new StringBuilder("from ParametrosGeneralesEt p ");
		sql.append("where p.nivel = 1 ");

		if (condicion != null && !condicion.isEmpty()) {
			sql.append(" and (p.idParametroGeneral = :idCondicion or 0= :idCondicion) ");
			sql.append("and (p.nombreLista like :nomLista or ''= :nomLista) ");
		}
		TypedQuery<ParametrosGeneralesEt> query = em.createQuery(sql.toString(), ParametrosGeneralesEt.class);
		if (condicion != null && !condicion.isEmpty()) {
			query.setParameter("idCondicion", QUL.toLong(condicion));
			query.setParameter("nomLista", "%" + QUL.getString(condicion).toUpperCase() + "%");
		}
		List<ParametrosGeneralesEt> result = query.getResultList();

		return getUnique(result);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ParametrosGeneralesEt getParametrosGeneralById(Long value) {
		sql = new StringBuilder("from ParametrosGeneralesEt p ");
		if (value != null) {
			sql.append("where (p.idParametroGeneral = :idCondicion or 0= :idCondicion) ");
		}
		TypedQuery<ParametrosGeneralesEt> query = em.createQuery(sql.toString(), ParametrosGeneralesEt.class);
		if (value != null) {
			query.setParameter("idCondicion", value);
		}
		List<ParametrosGeneralesEt> result = query.getResultList();
		return getUnique(result);

	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ParametrosGeneralesEt getTipoFiscalizador(String tipo) {
		List<ParametrosGeneralesEt> tipoFisc = getListaParametro(tipo, 1L);
		return getUnique(tipoFisc);
	}

	@Override
	public List<ParametrosGeneralesEt> getParametrosFechaActualizacion() {
		try {
			sql = new StringBuilder("from ParametrosGeneralesEt u ");
			sql.append("where u.estado = 'ACT' order by idParametroGeneral ");

			TypedQuery<ParametrosGeneralesEt> query = em.createQuery(sql.toString(), ParametrosGeneralesEt.class);

			List<ParametrosGeneralesEt> result = query.getResultList();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String limpiarReporte(Long idUsuario) {
		StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("getReporteUltimaVisita");
		query.setParameter("idUsuario", idUsuario);
		String respuesta = (String) query.getOutputParameterValue("respuesta");
		return respuesta;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ParametrosGeneralesEt> getSemestreAnio(ParametrosGeneralesEt par, Long semestre) {
		sql = new StringBuilder("from ParametrosGeneralesEt p ");
		sql.append("where p.nivel = 2 ");
		sql.append(" and (p.parametroPadre = :idCondicion ) ");
		if (semestre == 1L) {
			sql.append(" and (p.idParametroGeneral <  115 ) ");
		} else {
			sql.append(" and (p.idParametroGeneral >= 115 ) ");
		}
		sql.append(" and p.estado='ACT' ");
		sql.append("order by p.orden");
		TypedQuery<ParametrosGeneralesEt> query = em.createQuery(sql.toString(), ParametrosGeneralesEt.class);
		if (par != null) {
			query.setParameter("idCondicion", par);
		}
		List<ParametrosGeneralesEt> result = query.getResultList();
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
