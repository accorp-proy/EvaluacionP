package com.primax.srv.idao;

import java.util.List;

import com.primax.jpa.sec.RolEt;
import com.primax.srv.dao.base.IGenericDao;


public interface IRolEtDao extends IGenericDao<RolEt, Long> {

	public List<RolEt> getPersonaPorNombre(String nombre);

	public RolEt getRolbyId(Long id);

	public void remove();
}
