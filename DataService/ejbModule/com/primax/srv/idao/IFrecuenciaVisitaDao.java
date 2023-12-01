package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.FrecuenciaVisitaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IFrecuenciaVisitaDao extends IGenericDao<FrecuenciaVisitaEt, Long> {

	public void remove();

	public String limpiarReporte(Long idUsuario);

	public FrecuenciaVisitaEt getFrecuenciaVisitaById(long id);

	public List<FrecuenciaVisitaEt> getFrecuenciaVisitaList(String condicion) throws EntidadNoEncontradaException;

	public void guardarFrecuenciaVisita(FrecuenciaVisitaEt frecuenciaVisita, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
