package com.primax.srv.dao;

import java.util.ArrayList;
import java.util.Collections;
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
import com.primax.enm.pcs.LugarEnum;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.CantonEt;
import com.primax.jpa.param.CategoriaEstacionEt;
import com.primax.jpa.param.ParametrosGeneralesEt;
import com.primax.jpa.param.ProvinciaEt;
import com.primax.jpa.param.TipoEstacionEt;
import com.primax.jpa.param.ZonaEt;
import com.primax.jpa.param.ZonaUsuarioEt;
import com.primax.jpa.pla.CheckListEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IAgenciaDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class AgenciaDao extends GenericDao<AgenciaEt, Long> implements IAgenciaDao {

	public AgenciaDao() {
		super(AgenciaEt.class);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarAgencia(AgenciaEt agencia, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (agencia.getIdAgencia() == null) {
			agencia.audit(usuario, ActionAuditedEnum.NEW);
			crear(agencia);
		} else {
			agencia.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(agencia);
		}
		em.flush();
		em.clear();
	}

	private StringBuilder sql;

	public List<AgenciaEt> getAgenciasCodigos(List<String> codigos) {
		if (codigos != null && !codigos.isEmpty()) {
			sql = new StringBuilder("FROM AgenciaEt o ");
			sql.append("WHERE o.estado = :estado ");
			sql.append("AND o.codigoAgencia in(:codes) ");
			TypedQuery<AgenciaEt> query = em.createQuery(sql.toString(), AgenciaEt.class);
			query.setParameter("estado", EstadoEnum.ACT);
			query.setParameter("codes", codigos);
			List<AgenciaEt> result = query.getResultList();
			return result;
		}
		return Collections.emptyList();
	}

	@Override
	public List<AgenciaEt> getAgenciasPorZonas(List<ParametrosGeneralesEt> zonas) {
		sql = new StringBuilder("FROM AgenciaEt o ");
		sql.append("WHERE o.estado = :estado ");
		sql.append("AND o.zona in (:zonas ) ");
		TypedQuery<AgenciaEt> query = em.createQuery(sql.toString(), AgenciaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("zonas", zonas);
		List<AgenciaEt> result = query.getResultList();
		return result;
	}

	public AgenciaEt getAgenciaById(long id) {
		try {
			return this.recuperar(id);
		} catch (EntidadNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public AgenciaEt getAgenciaCodigo(String codigo) {
		sql = new StringBuilder("FROM AgenciaEt o ");
		sql.append("WHERE o.estado = :estado ");
		sql.append("AND o.codigoAgencia = :codigo ");
		TypedQuery<AgenciaEt> query = em.createQuery(sql.toString(), AgenciaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("codigo", codigo);
		List<AgenciaEt> result = query.getResultList();
		return getUnique(result);
	}

	@Override
	public List<AgenciaEt> getAgenciasSinAsignacionCanton(CantonEt ciudad, List<ParametrosGeneralesEt> zonas) {
		sql = new StringBuilder("FROM AgenciaEt o ");
		sql.append("WHERE o not in (select q.agencia from AgenciaVisitaAgenciaEt q where q.estado = :estado ) ");
		sql.append("AND o.estado = :estado ");

		if (ciudad != null)
			sql.append("and o.canton = :canton ");

		if (zonas != null && !zonas.isEmpty()) {
			sql.append("and o.zona in (:zonas) ");
		}

		TypedQuery<AgenciaEt> query = em.createQuery(sql.toString(), AgenciaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		if (ciudad != null)
			query.setParameter("canton", ciudad);
		if (zonas != null && !zonas.isEmpty()) {
			query.setParameter("zonas", zonas);
		}
		List<AgenciaEt> result = query.getResultList();
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AgenciaEt> getAgencias(EstadoEnum estado) {
		sql = new StringBuilder("FROM AgenciaEt o ");
		sql.append("WHERE o.estado = :estado ");
		sql.append("ORDER BY o.nombreAgencia asc ");
		TypedQuery<AgenciaEt> query = em.createQuery(sql.toString(), AgenciaEt.class);
		query.setParameter("estado", estado);
		List<AgenciaEt> result = query.getResultList();
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AgenciaEt> getAgenciasByZona(ZonaEt zona) {
		sql = new StringBuilder("FROM AgenciaEt o ");
		sql.append("WHERE o.estado = :estado ");
		if (zona != null) {
			sql.append(" AND o.zona = :zona ");
		}
		sql.append("ORDER BY o.nombreAgencia asc ");
		TypedQuery<AgenciaEt> query = em.createQuery(sql.toString(), AgenciaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		if (zona != null) {
			query.setParameter("zona", zona);
		}
		List<AgenciaEt> result = query.getResultList();
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AgenciaEt> getAgenciaAccesoZona(UsuarioEt usuario, ZonaEt zona) {
		sql = new StringBuilder("FROM AgenciaEt o ");
		sql.append("WHERE o.estado = :estado ");
		if (!usuario.getZonaUsuario().isEmpty() && zona == null) {
			sql.append(" AND o.zona in (:zonas) ");
		}
		if (zona != null) {
			sql.append(" AND o.zona = :zona  ");
		}
		sql.append("ORDER BY o.nombreAgencia asc ");
		TypedQuery<AgenciaEt> query = em.createQuery(sql.toString(), AgenciaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		List<ZonaEt> zonas = new ArrayList<ZonaEt>();
		if (!usuario.getZonaUsuario().isEmpty() && zona == null) {
			for (ZonaUsuarioEt zonaUsuario : usuario.getZonaUsuario()) {
				zonas.add(zonaUsuario.getZona());
			}
			query.setParameter("zonas", zonas);
		}
		if (zona != null) {
			query.setParameter("zona", zona);
		}
		List<AgenciaEt> result = query.getResultList();
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AgenciaEt> getAgenciasCondicion(String txt) {
		sql = new StringBuilder("FROM AgenciaEt o ");
		sql.append("WHERE o.estado = :estado ");
		if (txt != null && !txt.isEmpty()) {
			sql.append("and(o.codigoAgencia = :txt ");
			sql.append("or upper(o.descripcionAgencia) like :txt_or ");
			sql.append("or upper(o.nombreAgencia) like :txt_or ");
			sql.append("or o.canton.nombreCanton like :txt_or ");
			sql.append("or o.zona.nombreZona like :txt_or ) ");
		}
		sql.append("order by o.codigoAgencia ");
		TypedQuery<AgenciaEt> query = em.createQuery(sql.toString(), AgenciaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		if (txt != null && !txt.isEmpty()) {
			query.setParameter("txt", txt.toUpperCase());
			query.setParameter("txt_or", "%" + txt.toUpperCase() + "%");
		}
		List<AgenciaEt> result = query.getResultList();
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AgenciaEt> getAgenciasCanton(CantonEt canton) {
		sql = new StringBuilder("from AgenciaEt o ");
		sql.append("where o.estado = :estado ");
		sql.append("and o.canton = :canton ");
		TypedQuery<AgenciaEt> query = em.createQuery(sql.toString(), AgenciaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("canton", canton);
		List<AgenciaEt> result = query.getResultList();
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AgenciaEt> getAgenciasProvincia(ProvinciaEt provincia) {
		sql = new StringBuilder("from AgenciaEt o ");
		sql.append("where o.estado = :estado ");
		sql.append("and o.canton.provincia = :prov ");
		TypedQuery<AgenciaEt> query = em.createQuery(sql.toString(), AgenciaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("prov", provincia);
		List<AgenciaEt> result = query.getResultList();
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AgenciaEt> getAgenciaByTipoList(TipoEstacionEt tipoEstacion, CategoriaEstacionEt categoriaEstacion) {
		List<CategoriaEstacionEt> categoriasEstacion = new ArrayList<CategoriaEstacionEt>();
		categoriasEstacion.add(categoriaEstacion);
		sql = new StringBuilder("SELECT distinct(o.agencia) FROM AgenciaCategoriaEt o ");
		sql.append("WHERE o.estado = :estado ");
		if (tipoEstacion != null) {
			sql.append("AND o.agencia.tipoEstacion = :tipoEstacion ");
		}
		if (categoriaEstacion != null) {
			sql.append("AND o.categoriaEstacion in (:categoriasEstacion) ");
		}
		sql.append("ORDER BY o.agencia.nombreAgencia asc ");
		TypedQuery<AgenciaEt> query = em.createQuery(sql.toString(), AgenciaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		if (tipoEstacion != null) {
			query.setParameter("tipoEstacion", tipoEstacion);
		}
		if (categoriaEstacion != null) {
			query.setParameter("categoriasEstacion", categoriasEstacion);
		}
		List<AgenciaEt> result = query.getResultList();
		for (int i = 0; i < result.size(); i++) {
			result.get(i).getAgenciaCheckList().size();
		}
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AgenciaEt> getAgenciaByTipoAList(TipoEstacionEt tipoEstacion, CategoriaEstacionEt categoriaEstacion, List<AgenciaEt> agencias) {
		List<CategoriaEstacionEt> categoriasEstacion = new ArrayList<CategoriaEstacionEt>();
		categoriasEstacion.add(categoriaEstacion);
		sql = new StringBuilder("SELECT distinct(o.agencia) FROM AgenciaCategoriaEt o ");
		sql.append("WHERE o.estado = :estado ");
		if (tipoEstacion != null) {
			sql.append("AND o.agencia.tipoEstacion = :tipoEstacion ");
		}
		if (categoriaEstacion != null) {
			sql.append("AND o.categoriaEstacion in (:categoriasEstacion) ");
		}
		sql.append("AND o.agencia not in (:agencias) ");
		sql.append("ORDER BY o.agencia.nombreAgencia asc ");
		TypedQuery<AgenciaEt> query = em.createQuery(sql.toString(), AgenciaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("agencias", agencias);
		if (tipoEstacion != null) {
			query.setParameter("tipoEstacion", tipoEstacion);
		}
		if (categoriaEstacion != null) {
			query.setParameter("categoriasEstacion", categoriasEstacion);
		}
		List<AgenciaEt> result = query.getResultList();
		for (int i = 0; i < result.size(); i++) {
			result.get(i).getAgenciaCheckList().size();
		}
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AgenciaEt> getAgenciaByCheckList(CheckListEt checkList) {
		sql = new StringBuilder("SELECT distinct(o.agencia) FROM AgenciaCheckListEt o ");
		sql.append("WHERE o.estado = :estado ");
		sql.append("AND o.checkList = :checkList ");
		sql.append("ORDER BY o.agencia.nombreAgencia asc ");
		TypedQuery<AgenciaEt> query = em.createQuery(sql.toString(), AgenciaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("checkList", checkList);
		List<AgenciaEt> result = query.getResultList();
		if (!result.isEmpty()) {
			for (int i = 0; i < result.size(); i++) {
				result.get(i).getAgenciaCheckList().size();

			}
		}
		return result;
	}

	@Override
	public AgenciaEt getAgeciaCodigoLugar(Long codigo, LugarEnum lugar) {
		sql = new StringBuilder("from AgenciaEt o ");
		sql.append("where o.estado = :estado ");
		if (lugar.equals(LugarEnum.PIS)) {
			sql.append("and o.codigoPista = :codigo ");
		} else {
			sql.append("and o.codigoTienda = :codigo ");
		}
		TypedQuery<AgenciaEt> query = em.createQuery(sql.toString(), AgenciaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("codigo", codigo);
		List<AgenciaEt> result = query.getResultList();
		return !result.isEmpty() ? result.get(0) : null;
	}

	@Override
	public List<AgenciaEt> getAgenciasPorZonas(ParametrosGeneralesEt zona) {
		sql = new StringBuilder("FROM AgenciaEt o ");
		sql.append("WHERE o.estado = :estado ");
		if (zona != null) {
			sql.append("AND o.zona = :zonas  ");
		}
		TypedQuery<AgenciaEt> query = em.createQuery(sql.toString(), AgenciaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		if (zona != null) {
			query.setParameter("zonas", zona);
		}
		List<AgenciaEt> result = query.getResultList();
		return result;
	}

	@Override
	public List<AgenciaEt> getAgenciasPorZonas(ZonaEt zona, TipoEstacionEt tipoEstacion) {
		sql = new StringBuilder("FROM AgenciaEt o ");
		sql.append("WHERE o.estado = :estado ");
		if (zona != null) {
			sql.append("AND o.zona = :zonas  ");
		}
		if (tipoEstacion != null) {
			sql.append("AND o.tipoEstacion = :tipoEstacion  ");
		}
		TypedQuery<AgenciaEt> query = em.createQuery(sql.toString(), AgenciaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		if (zona != null) {
			query.setParameter("zonas", zona);
		}
		if (tipoEstacion != null) {
			query.setParameter("tipoEstacion", tipoEstacion);
		}
		List<AgenciaEt> result = query.getResultList();
		return result;
	}

	@Override
	public List<AgenciaEt> getAgenciasMejores(ParametrosGeneralesEt zona) {
		sql = new StringBuilder("FROM AgenciaEt o ");
		sql.append("WHERE o.estado = :estado ");
		sql.append("AND   o.nivelRiesgo is not null ");
		if (zona != null) {
			sql.append("AND o.zona = :zonas  ");
		}
		sql.append("ORDER BY o.posicionPuntaje ");
		TypedQuery<AgenciaEt> query = em.createQuery(sql.toString(), AgenciaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		if (zona != null) {
			query.setParameter("zonas", zona);
		}
		query.setMaxResults(5);
		List<AgenciaEt> result = query.getResultList();

		return result;
	}

	@Override
	public List<AgenciaEt> getAgenciasPeores(ParametrosGeneralesEt zona) {
		sql = new StringBuilder("FROM AgenciaEt o ");
		sql.append("WHERE o.estado = :estado ");
		sql.append("AND   o.nivelRiesgo is not null ");
		sql.append(" AND o.posicionPuntaje  > 5 ");
		if (zona != null) {
			sql.append("AND o.zona = :zonas  ");
		}
		sql.append("ORDER BY o.posicionPuntaje ");
		TypedQuery<AgenciaEt> query = em.createQuery(sql.toString(), AgenciaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		if (zona != null) {
			query.setParameter("zonas", zona);
		}
		query.setMaxResults(5);
		List<AgenciaEt> result = query.getResultList();
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
