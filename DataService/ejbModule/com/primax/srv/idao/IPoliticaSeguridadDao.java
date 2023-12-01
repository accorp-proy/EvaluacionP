package com.primax.srv.idao;

import com.primax.jpa.sec.PoliticaSeguridadEt;
import com.primax.srv.dao.base.IGenericDao;


public interface IPoliticaSeguridadDao extends IGenericDao<PoliticaSeguridadEt, Long> {

	public PoliticaSeguridadEt getpoliticaSeguridad();

	public void remove();
}
