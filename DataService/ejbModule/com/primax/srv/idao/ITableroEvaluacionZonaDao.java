package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.jpa.pla.TableroEvaluacionZonaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ITableroEvaluacionZonaDao extends IGenericDao<TableroEvaluacionZonaEt, Long> {

	public void remove();

	public String generar(Long idUsuario);

	public List<TableroEvaluacionZonaEt> getTablaList(UsuarioEt usuario) throws EntidadNoEncontradaException;

}
