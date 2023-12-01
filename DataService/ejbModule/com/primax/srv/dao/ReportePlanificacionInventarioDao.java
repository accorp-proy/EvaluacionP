package com.primax.srv.dao;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TemporalType;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.ReportePlanificacionInventarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IReportePlanificacionInventarioDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class ReportePlanificacionInventarioDao extends GenericDao<ReportePlanificacionInventarioEt, Long>
		implements IReportePlanificacionInventarioDao {

	public ReportePlanificacionInventarioDao() {
		super(ReportePlanificacionInventarioEt.class);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarReportePlanificacionInv(ReportePlanificacionInventarioEt reportePlanificacionInv, UsuarioEt usuario)
			throws EntidadNoGrabadaException {
		if (reportePlanificacionInv.getIdReportePlanificacionInventario() == null) {
			reportePlanificacionInv.audit(usuario, ActionAuditedEnum.NEW);
			crear(reportePlanificacionInv);
		} else {
			reportePlanificacionInv.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(reportePlanificacionInv);
		}
		em.flush();
		em.clear();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ReportePlanificacionInventarioEt getReportePlanificacionInv(long id) {
		try {
			ReportePlanificacionInventarioEt reportePlanificacionInv = recuperar(id);
			return reportePlanificacionInv;
		} catch (EntidadNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String generar(Date fechaDesde, Date fechaHasta, Long idZona, Long idEstacion, Long idTipoInv, Long idUsuario) {
		StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("getGenerarReportePlanificacionInv");
		query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
		query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
		query.setParameter("idZona", idZona);
		query.setParameter("idEstacion", idEstacion);
		query.setParameter("idTipoInv", idTipoInv);
		query.setParameter("idUsuario", idUsuario);
		String respuesta = (String) query.getOutputParameterValue("respuesta");
		return respuesta;
	}

	@Remove
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void remove() {
		System.out.println("Finalizado Statefull Bean : " + this.getClass().getCanonicalName());
	}

	@PreDestroy
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void detached() {
		System.out.println("Terminado Statefull Bean : " + this.getClass().getCanonicalName());
	}

}
