package com.primax.srv.idao;

import java.util.Date;

import com.primax.jpa.pla.TableroDetalleEstacionEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ITableroDetalleEstacionDao extends IGenericDao<TableroDetalleEstacionEt, Long> {

	public void remove();

	public String generar(Date fechaDesde, Date fechaHasta, Long idTipoEstacion, Long idZona, Long idAgencia, Long idEvaluacion, Long idNivelEvaluacion, Long idUsuario);

}
