package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.gen.PersonaEt;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.ResponsableEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IResponsableDao extends IGenericDao<ResponsableEt, Long> {

	public void remove();

	public ResponsableEt getResponsable(long id);

	public String getResponsableEstacionS(PersonaEt persona) throws EntidadNoEncontradaException;

	public ResponsableEt getResponsableEstacion(PersonaEt persona) throws EntidadNoEncontradaException;

	public ResponsableEt getResponsableByAgencia(AgenciaEt agencia) throws EntidadNoEncontradaException;

	public List<ResponsableEt> getResponsableList(String condicion) throws EntidadNoEncontradaException;

	public List<PersonaEt> getResponsableByAgenciaList(AgenciaEt agencia) throws EntidadNoEncontradaException;

	public void guardarResponsable(ResponsableEt responsable, UsuarioEt usuario) throws EntidadNoGrabadaException;

	public List<ResponsableEt> getResponsableByAgencia2List(AgenciaEt agencia) throws EntidadNoEncontradaException;

	public List<ResponsableEt> getResponsableByAgencia1List(AgenciaEt agencia) throws EntidadNoEncontradaException;

	public ResponsableEt getResponsableByCargo(AgenciaEt agencia, Long idTipoCargo) throws EntidadNoEncontradaException;

}
