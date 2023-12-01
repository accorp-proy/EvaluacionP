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
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.ParametrosGeneralesEt;
import com.primax.jpa.pla.CheckListEtiquetaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ICheckListEtiquetaDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class CheckListEtiquetaDao extends GenericDao<CheckListEtiquetaEt, Long> implements ICheckListEtiquetaDao {

	public CheckListEtiquetaDao() {
		super(CheckListEtiquetaEt.class);
	}

	private StringBuilder sql;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarCheckListEtiqueta(CheckListEtiquetaEt checkListEtiqueta, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (checkListEtiqueta.getIdCheckListEtiqueta() == null) {
			checkListEtiqueta.audit(usuario, ActionAuditedEnum.NEW);
			crear(checkListEtiqueta);
		} else {
			checkListEtiqueta.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(checkListEtiqueta);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CheckListEtiquetaEt> getCheckListEtiquetaList() throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM CheckListEtiquetaEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		sql.append(" ORDER BY o.orden ");
		TypedQuery<CheckListEtiquetaEt> query = em.createQuery(sql.toString(), CheckListEtiquetaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		List<CheckListEtiquetaEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CheckListEtiquetaEt> getCheckListEtiquetaList(ParametrosGeneralesEt parSeccion) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM CheckListEtiquetaEt o ");
		sql.append(" WHERE o.estado     = :estado   ");
		sql.append(" AND   o.parSeccion = :parSeccion   ");
		sql.append(" ORDER BY o.orden ");
		TypedQuery<CheckListEtiquetaEt> query = em.createQuery(sql.toString(), CheckListEtiquetaEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("parSeccion", parSeccion);
		List<CheckListEtiquetaEt> result = query.getResultList();
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
