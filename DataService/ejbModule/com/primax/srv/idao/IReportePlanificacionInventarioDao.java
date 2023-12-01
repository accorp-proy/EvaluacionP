package com.primax.srv.idao;

import java.util.Date;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.ReportePlanificacionInventarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IReportePlanificacionInventarioDao extends IGenericDao<ReportePlanificacionInventarioEt, Long> {

	public void remove();

	public ReportePlanificacionInventarioEt getReportePlanificacionInv(long id);

	public void guardarReportePlanificacionInv(ReportePlanificacionInventarioEt reportePlanificacionInv, UsuarioEt usuario)
			throws EntidadNoGrabadaException;

	public String generar(Date fechaDesde, Date fechaHasta, Long idZona, Long idEstacion, Long idTipoInv, Long idUsuario);

}
