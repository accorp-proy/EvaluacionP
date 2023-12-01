package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.KPICriticoEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IKPICriticoDao extends IGenericDao<KPICriticoEt, Long> {

	public void remove();

	public KPICriticoEt getKPICritico(long id);

	public String limpiarReporte(Long idUsuario);

	public List<KPICriticoEt> getKPICriticoList(String condicion) throws EntidadNoEncontradaException;

	public void guardarKPICritico(KPICriticoEt kPICritico, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
