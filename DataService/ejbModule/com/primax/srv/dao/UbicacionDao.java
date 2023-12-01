package com.primax.srv.dao;

import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.gen.UbicacionEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IUbicacionDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class UbicacionDao extends GenericDao<UbicacionEt, Long> implements IUbicacionDao {

	public UbicacionDao() {
		super(UbicacionEt.class);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarUbicacion(UbicacionEt ubi) throws EntidadNoGrabadaException {
		if (ubi.getIdUbicacion() == null) {
			this.crear(ubi);
		} else {
			this.actualizar(ubi);
		}
		em.flush();
		em.clear();
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
