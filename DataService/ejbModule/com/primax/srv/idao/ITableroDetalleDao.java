package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.jpa.pla.TableroDetalleEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ITableroDetalleDao extends IGenericDao<TableroDetalleEt, Long> {

	public void remove();

	public String limpiarTablero(Long idUsuario);

	public List<TableroDetalleEt> getTablaList(UsuarioEt usuario) throws EntidadNoEncontradaException;

}
