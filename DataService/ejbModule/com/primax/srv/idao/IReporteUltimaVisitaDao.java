package com.primax.srv.idao;

import java.util.Date;
import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.jpa.pla.ReporteUltimaVisitaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IReporteUltimaVisitaDao extends IGenericDao<ReporteUltimaVisitaEt, Long> {

	public void remove();

	public ReporteUltimaVisitaEt getReporteUltimaVisita(UsuarioEt usuario) throws EntidadNoEncontradaException;

	public List<ReporteUltimaVisitaEt> getReporteUltimaVisitaList(UsuarioEt usuario) throws EntidadNoEncontradaException;

	public String generar(Date fechaDesde, Date fechaHasta, Long idZona, Long idTipoCheckList, Long idEvaluacion, Long idAgencia, Long idProceso, Long idUsuario);

}
