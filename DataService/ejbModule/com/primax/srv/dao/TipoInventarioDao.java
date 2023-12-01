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
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.TipoInventarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ITipoInventarioDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class TipoInventarioDao extends GenericDao<TipoInventarioEt, Long> implements ITipoInventarioDao {

	public TipoInventarioDao() {
		super(TipoInventarioEt.class);
	}

	private StringBuilder sql;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarTipoInventario(TipoInventarioEt tipoInventario, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (tipoInventario.getIdTipoInventario() == null) {
			tipoInventario.audit(usuario, ActionAuditedEnum.NEW);
			crear(tipoInventario);
		} else {
			tipoInventario.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(tipoInventario);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<TipoInventarioEt> getTipoInventarioList(String condicion) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM TipoInventarioEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		sql.append(" ORDER BY o.idTipoInventario  ");
		TypedQuery<TipoInventarioEt> query = em.createQuery(sql.toString(), TipoInventarioEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		List<TipoInventarioEt> result = query.getResultList();
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public TipoInventarioEt getTipoInventario(long id) {
		try {
			TipoInventarioEt tipoInventario = recuperar(id);
			return tipoInventario;
		} catch (EntidadNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String limpiarReporte(Long idUsuario) {
		StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("getReporteEvaluacionPlanificacionInv");
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
