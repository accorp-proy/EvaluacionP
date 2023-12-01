package com.primax.srv.idao;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.jpa.pla.TableroCabeceraEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ITableroCabeceraDao extends IGenericDao<TableroCabeceraEt, Long> {

	public void remove();

	public TableroCabeceraEt getTablaCabecera(UsuarioEt usuario) throws EntidadNoEncontradaException;

}
