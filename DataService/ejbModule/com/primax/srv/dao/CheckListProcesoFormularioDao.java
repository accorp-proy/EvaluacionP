package com.primax.srv.dao;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.pla.CheckListProcesoEt;
import com.primax.jpa.pla.CheckListProcesoFormularioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ICheckListProcesoFormularioDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class CheckListProcesoFormularioDao extends GenericDao<CheckListProcesoFormularioEt, Long> implements ICheckListProcesoFormularioDao {

	public CheckListProcesoFormularioDao() {
		super(CheckListProcesoFormularioEt.class);
	}
	
	private StringBuilder sql;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarCheckListProcesoFormulario(CheckListProcesoFormularioEt checkListProcesoFormulario, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (checkListProcesoFormulario.getIdCheckListProcesoFormulario() == null) {
			checkListProcesoFormulario.audit(usuario, ActionAuditedEnum.NEW);
			crear(checkListProcesoFormulario);
		} else {
			checkListProcesoFormulario.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(checkListProcesoFormulario);
		}
		em.flush();
		em.clear();
	}
	
	@Override
	public List<CheckListProcesoFormularioEt> getCheckListFormularioByProceso(CheckListProcesoEt checkListProceso) {
		sql = new StringBuilder("FROM CheckListProcesoFormularioEt o ");
		sql.append("WHERE o.estado = :estado ");
		sql.append("AND o.checkListProceso = :checkListProceso ");
		sql.append("ORDER BY o.idCheckListProcesoFormulario ");
		TypedQuery<CheckListProcesoFormularioEt> query = em.createQuery(sql.toString(), CheckListProcesoFormularioEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("checkListProceso", checkListProceso);
		List<CheckListProcesoFormularioEt> result = query.getResultList();
		return result;
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
