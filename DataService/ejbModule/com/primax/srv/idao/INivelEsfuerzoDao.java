package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.NivelEsfuerzoEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface INivelEsfuerzoDao extends IGenericDao<NivelEsfuerzoEt, Long> {

	public void remove();

	public String limpiarReporte(Long idUsuario);

	public NivelEsfuerzoEt getNivelEsfuerzoById(long id);

	public List<NivelEsfuerzoEt> getNivelEsfuerzoList(String condicion) throws EntidadNoEncontradaException;

	public void guardarNivelEsfuerzo(NivelEsfuerzoEt nivelEsfuerzo, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
