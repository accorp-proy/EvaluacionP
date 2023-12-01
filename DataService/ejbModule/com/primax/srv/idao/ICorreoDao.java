package com.primax.srv.idao;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.CorreoEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ICorreoDao extends IGenericDao<CorreoEt, Long> {

	public void remove();

	public CorreoEt getCorreo(long id);

	public CorreoEt getCorreoExiste(Long idCorreo) throws EntidadNoEncontradaException;

	public void guardarCorreo(CorreoEt correo, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
