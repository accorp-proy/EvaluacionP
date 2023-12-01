package com.primax.srv.dao;

import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.gen.PersonaContactoEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IPersonaContactoDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class PersonaContactoDao extends GenericDao<PersonaContactoEt, Long> implements IPersonaContactoDao {

	public PersonaContactoDao() {
		super(PersonaContactoEt.class);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarPersonaContacto(PersonaContactoEt personaContacto, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (personaContacto.getIdPersonaContacto() == null) {
			personaContacto.audit(usuario, ActionAuditedEnum.NEW);
			crear(personaContacto);
		} else {
			personaContacto.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(personaContacto);
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
