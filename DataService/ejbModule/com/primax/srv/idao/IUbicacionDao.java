package com.primax.srv.idao;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.gen.UbicacionEt;
import com.primax.srv.dao.base.IGenericDao;


public interface IUbicacionDao extends IGenericDao<UbicacionEt, Long> {

	public void guardarUbicacion(UbicacionEt ubi) throws EntidadNoGrabadaException;

	public void remove();

}
