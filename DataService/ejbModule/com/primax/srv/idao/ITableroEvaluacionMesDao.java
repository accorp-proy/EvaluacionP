package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.jpa.pla.TableroEvaluacionMesEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ITableroEvaluacionMesDao extends IGenericDao<TableroEvaluacionMesEt, Long> {

	public void remove();

	public List<TableroEvaluacionMesEt> getTablaList(UsuarioEt usuario) throws EntidadNoEncontradaException;

}
