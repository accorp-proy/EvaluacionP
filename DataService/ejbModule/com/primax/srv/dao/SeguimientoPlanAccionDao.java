package com.primax.srv.dao;

import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.StoredProcedureQuery;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.SeguimientoPlanAccionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ISeguimientoPlanAccionDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class SeguimientoPlanAccionDao extends GenericDao<SeguimientoPlanAccionEt, Long> implements ISeguimientoPlanAccionDao {

	public SeguimientoPlanAccionDao() {
		super(SeguimientoPlanAccionEt.class);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarSeguimientoPlanAccion(SeguimientoPlanAccionEt seguimientoPlanAccion, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (seguimientoPlanAccion.getIdSeguimientoPlanAccion() == null) {
			seguimientoPlanAccion.audit(usuario, ActionAuditedEnum.NEW);
			crear(seguimientoPlanAccion);
		} else {
			seguimientoPlanAccion.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(seguimientoPlanAccion);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String generar(Long idCheck0, Long idCheck1, Long idUsuario) {
		StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("getGenerarSeguimientoPlanAccion");
		query.setParameter("idCheck0", idCheck0);
		query.setParameter("idCheck1", idCheck1);
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
