package com.primax.srv.dao;

import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.StoredProcedureQuery;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.CheckListKpiEjecucionCEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ICheckListKpiEjecucionCDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class CheckListKpiEjecucionCDao extends GenericDao<CheckListKpiEjecucionCEt, Long> implements ICheckListKpiEjecucionCDao {

	public CheckListKpiEjecucionCDao() {
		super(CheckListKpiEjecucionCEt.class);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardaCheckListKpiEjecucionC(CheckListKpiEjecucionCEt checkListKpiEjecucionC, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (checkListKpiEjecucionC.getIdCheckListKpiEjecucionC() == null) {
			checkListKpiEjecucionC.audit(usuario, ActionAuditedEnum.NEW);
			crear(checkListKpiEjecucionC);
		} else {
			checkListKpiEjecucionC.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(checkListKpiEjecucionC);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String limpiarReporte(Long idCheckListProcesoEjecucion) {
		StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("getLimpiarKpiEjecucion");
		query.setParameter("idCheckListProcesoEjecucion", idCheckListProcesoEjecucion);
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
