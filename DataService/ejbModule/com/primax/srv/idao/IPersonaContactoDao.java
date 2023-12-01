package com.primax.srv.idao;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.gen.PersonaContactoEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IPersonaContactoDao extends IGenericDao<PersonaContactoEt, Long> {

	public void guardarPersonaContacto(PersonaContactoEt personaContacto, UsuarioEt usuario) throws EntidadNoGrabadaException;

	public void remove();

}
