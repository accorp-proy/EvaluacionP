package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.ArqueoCajaGeneralEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IArqueoCajaGeneralDao extends IGenericDao<ArqueoCajaGeneralEt, Long> {

	public void remove();

	public List<ArqueoCajaGeneralEt> getArqueoCajaGeneral(String condicion);

	public void guardarArqueoCajaGeneral(ArqueoCajaGeneralEt arqueoCajaGeneral, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
