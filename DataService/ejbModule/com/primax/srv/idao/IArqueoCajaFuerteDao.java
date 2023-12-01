package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.ArqueoCajaFuerteEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IArqueoCajaFuerteDao extends IGenericDao<ArqueoCajaFuerteEt, Long> {

	public void remove();

	public List<ArqueoCajaFuerteEt> getArqueoCajaFuerte(String condicion);

	public void guardarArqueoCajaFuerte(ArqueoCajaFuerteEt arqueoCajaFuerte, UsuarioEt usuario) throws EntidadNoGrabadaException;
}
