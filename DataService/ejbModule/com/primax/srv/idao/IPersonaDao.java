package com.primax.srv.idao;

import java.util.List;

import com.primax.enm.gen.TipoPersonaEnum;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.gen.PersonaEt;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IPersonaDao extends IGenericDao<PersonaEt, Long> {

	public void remove();

	public PersonaEt getPersonaById(Long idPersona);

	public List<PersonaEt> getPersonasUsuario(String txt);

	public PersonaEt getPersonasPorCedula(String cedula);

	public PersonaEt getPersonasPorCedulaR(String cedula);

	public List<PersonaEt> getPersonasCondicion(String txt);

	public PersonaEt getResponsableByAgencia(AgenciaEt agencia) throws EntidadNoEncontradaException;

	public void guardarPersona(PersonaEt persona, UsuarioEt usuario) throws EntidadNoGrabadaException;

	public void actualizarPersona(PersonaEt persona, UsuarioEt usuario) throws EntidadNoGrabadaException;

	public List<PersonaEt> getListPersonas(TipoPersonaEnum tipoPersonaEnum) throws EntidadNoEncontradaException;

	public List<PersonaEt> getListPersonaByTipo(TipoPersonaEnum tipoPersonaEnum) throws EntidadNoEncontradaException;

	public List<PersonaEt> getListPersonaByTipoId(TipoPersonaEnum tipoPersonaEnum) throws EntidadNoEncontradaException;

	public List<PersonaEt> getListPersonaByTipoFechaAct(TipoPersonaEnum tipoPersonaEnum) throws EntidadNoEncontradaException;

	public List<PersonaEt> getListPersonaByTipoFechaAct01(TipoPersonaEnum tipoPersonaEnum) throws EntidadNoEncontradaException;

	public List<PersonaEt> getListPersona(TipoPersonaEnum tipoPersonaEnum, String condicion) throws EntidadNoEncontradaException;

	public List<PersonaEt> getListPersonaCliente(TipoPersonaEnum tipoPersonaEnum, String condicion) throws EntidadNoEncontradaException;

}
