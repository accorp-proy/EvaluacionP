package com.primax.srv.idao;

import com.primax.enm.gen.DBTypeLib;
import com.primax.jpa.param.ConexionEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IConexionDao extends IGenericDao<ConexionEt, Long> {

	public ConexionEt getConexionById(Long value);
	
	public ConexionEt getConexionByLib(DBTypeLib lib);

	public void remove();

}
