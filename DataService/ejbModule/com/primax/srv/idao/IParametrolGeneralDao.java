package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.ParametrosGeneralesEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IParametrolGeneralDao extends IGenericDao<ParametrosGeneralesEt, Long> {

	public void remove();

	public String limpiarReporte(Long idUsuario);

	public ParametrosGeneralesEt getObjPadre(String condiction);

	public ParametrosGeneralesEt getTipoFiscalizador(String tipo);

	public ParametrosGeneralesEt getParametrosGeneralById(Long value);

	public List<ParametrosGeneralesEt> getParametrosFechaActualizacion();

	public List<ParametrosGeneralesEt> getListaHIjos(ParametrosGeneralesEt par);

	public ParametrosGeneralesEt getListaHIjosUnique(ParametrosGeneralesEt par);

	public List<ParametrosGeneralesEt> getListaParametro(String condicion, Long nivel);

	public List<ParametrosGeneralesEt> getSemestreAnio(ParametrosGeneralesEt par, Long semestre);

	public List<ParametrosGeneralesEt> getListAnioVariacion(ParametrosGeneralesEt par, Long anio);

	public void guardarParametro(ParametrosGeneralesEt parametro, UsuarioEt usuario) throws EntidadNoGrabadaException;
}
