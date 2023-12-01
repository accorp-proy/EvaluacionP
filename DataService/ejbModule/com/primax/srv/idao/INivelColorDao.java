package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.NivelColorEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface INivelColorDao extends IGenericDao<NivelColorEt, Long> {

	public void remove();

	public NivelColorEt getNivelColorById(long id);

	public List<NivelColorEt> getNivelColorList(String condicion) throws EntidadNoEncontradaException;

	public void guardarNivelColor(NivelColorEt nivelColor, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
