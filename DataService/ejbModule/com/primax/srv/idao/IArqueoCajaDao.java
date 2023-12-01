package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.ArqueoCajaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IArqueoCajaDao extends IGenericDao<ArqueoCajaEt, Long> {

	public void remove();

	public List<ArqueoCajaEt> getArqueoCajaEt(String condicion);

	public void guardarArqueoCajaEt(ArqueoCajaEt arqueoCaja, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
