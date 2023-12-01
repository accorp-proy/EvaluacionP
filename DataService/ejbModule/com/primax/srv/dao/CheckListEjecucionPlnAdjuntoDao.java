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
import com.primax.jpa.pla.CheckListEjecucionPlnAdjuntoEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ICheckListEjecucionPlnAdjuntoDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class CheckListEjecucionPlnAdjuntoDao extends GenericDao<CheckListEjecucionPlnAdjuntoEt, Long> implements ICheckListEjecucionPlnAdjuntoDao {

	public CheckListEjecucionPlnAdjuntoDao() {
		super(CheckListEjecucionPlnAdjuntoEt.class);
	}

	private StringBuilder sql;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarCheckListEjecucionPlnAdjunto(CheckListEjecucionPlnAdjuntoEt checkListEjecucionPlnAdjunto, UsuarioEt usuario)
			throws EntidadNoGrabadaException {
		if (checkListEjecucionPlnAdjunto.getIdCheckListEjecucionPlnAdjunto() == null) {
			checkListEjecucionPlnAdjunto.audit(usuario, ActionAuditedEnum.NEW);
			crear(checkListEjecucionPlnAdjunto);
		} else {
			checkListEjecucionPlnAdjunto.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(checkListEjecucionPlnAdjunto);
		}
		em.flush();
		em.clear();
	}

	@Override
	public List<CheckListEjecucionPlnAdjuntoEt> getAdjuntoList(Long idCheckListEjecucion) {
		sql = new StringBuilder("FROM CheckListEjecucionPlnAdjuntoEt o ");
		sql.append(" WHERE o.estado = :estado ");
		sql.append(" AND o.checkListEjecucion.idCheckListEjecucion = :idCheckListEjecucion ");
		sql.append(" ORDER BY o.idCheckListEjecucionPlnAdjunto ");
		TypedQuery<CheckListEjecucionPlnAdjuntoEt> query = em.createQuery(sql.toString(), CheckListEjecucionPlnAdjuntoEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("idCheckListEjecucion", idCheckListEjecucion);
		List<CheckListEjecucionPlnAdjuntoEt> result = query.getResultList();
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
