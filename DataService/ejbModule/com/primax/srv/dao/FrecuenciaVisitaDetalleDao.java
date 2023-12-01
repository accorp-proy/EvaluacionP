package com.primax.srv.dao;

import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.FrecuenciaVisitaDetalleEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IFrecuenciaVisitaDetalleDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class FrecuenciaVisitaDetalleDao extends GenericDao<FrecuenciaVisitaDetalleEt, Long> implements IFrecuenciaVisitaDetalleDao {

	public FrecuenciaVisitaDetalleDao() {
		super(FrecuenciaVisitaDetalleEt.class);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarFrecuenciaVisitaDetalle(FrecuenciaVisitaDetalleEt frecuenciaVisitaDetalle, UsuarioEt usuario)
			throws EntidadNoGrabadaException {
		if (frecuenciaVisitaDetalle.getIdFrecuenciaVisitaDetalle() == null) {
			frecuenciaVisitaDetalle.audit(usuario, ActionAuditedEnum.NEW);
			crear(frecuenciaVisitaDetalle);
		} else {
			frecuenciaVisitaDetalle.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(frecuenciaVisitaDetalle);
		}
		em.flush();
		em.clear();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public FrecuenciaVisitaDetalleEt getFrecuenciaVisitaDetalleById(long id) {
		try {
			FrecuenciaVisitaDetalleEt frecuenciaVisitaDetalle = recuperar(id);
			return frecuenciaVisitaDetalle;
		} catch (EntidadNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
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
