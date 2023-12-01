package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.jpa.pla.TableroEstacionZonaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ITableroEstacionZonaDao extends IGenericDao<TableroEstacionZonaEt, Long> {

	public void remove();

	public List<TableroEstacionZonaEt> getTablaList(UsuarioEt usuario) throws EntidadNoEncontradaException;

}
