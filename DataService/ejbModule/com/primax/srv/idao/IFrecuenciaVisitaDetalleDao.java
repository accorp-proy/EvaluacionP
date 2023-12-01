package com.primax.srv.idao;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.FrecuenciaVisitaDetalleEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IFrecuenciaVisitaDetalleDao extends IGenericDao<FrecuenciaVisitaDetalleEt, Long> {

	public void remove();

	public FrecuenciaVisitaDetalleEt getFrecuenciaVisitaDetalleById(long id);

	public void guardarFrecuenciaVisitaDetalle(FrecuenciaVisitaDetalleEt frecuenciaVisitaDetalle, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
