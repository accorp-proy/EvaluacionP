package com.primax.srv.idao;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.SeguimientoPlanAccionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ISeguimientoPlanAccionDao extends IGenericDao<SeguimientoPlanAccionEt, Long> {

	public void remove();

	public String generar(Long idCheck0, Long idCheck1, Long idUsuario);

	public void guardarSeguimientoPlanAccion(SeguimientoPlanAccionEt seguimientoPlanAccion, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
