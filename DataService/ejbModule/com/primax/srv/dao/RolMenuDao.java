package com.primax.srv.dao;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.sec.MenuEt;
import com.primax.jpa.sec.RolEt;
import com.primax.jpa.sec.RolMenuEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IMenuEtDao;
import com.primax.srv.idao.IRolMenuDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class RolMenuDao extends GenericDao<RolMenuEt, Long> implements IRolMenuDao {

	private StringBuilder sql;

	@EJB
	private IMenuEtDao iMenuDao;

	public RolMenuDao() {
		super(RolMenuEt.class);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<RolMenuEt> getMenusByRol(RolEt rol) {
		sql = new StringBuilder("select o from RolMenuEt o ");
		sql.append("where o.rol = :rol ");
		sql.append("and o.menu.estado =:estado ");
		sql.append("and o.rol.estado =:estado ");
		sql.append("and o.estado =:estado ");
		TypedQuery<RolMenuEt> result = em.createQuery(sql.toString(), RolMenuEt.class);
		result.setParameter("rol", rol);
		result.setParameter("estado", EstadoEnum.ACT);
		List<RolMenuEt> rolesMenu = result.getResultList();
		return rolesMenu;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public RolMenuEt getRolMenu(RolEt rol, MenuEt menu) {
		sql = new StringBuilder("select o from RolMenuEt o ");
		sql.append("where o.menu= :men ");
		sql.append("and o.rol= :rol ");
		TypedQuery<RolMenuEt> rolMen = em.createQuery(sql.toString(), RolMenuEt.class);
		rolMen.setParameter("men", menu);
		rolMen.setParameter("rol", rol);
		List<RolMenuEt> result_0 = rolMen.getResultList();
		RolMenuEt result = getUnique(result_0);
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private void preparePadre(RolEt rol, MenuEt menu) throws EntidadNoGrabadaException {
		sql = new StringBuilder("select o from RolMenuEt o ");
		sql.append("where o.menu= :men ");
		sql.append("and o.rol= :rol ");
		TypedQuery<RolMenuEt> rolMen = em.createQuery(sql.toString(), RolMenuEt.class);
		rolMen.setParameter("men", menu.getMenuPadre());
		rolMen.setParameter("rol", rol);
		List<RolMenuEt> result_0 = rolMen.getResultList();
		RolMenuEt result = getUnique(result_0);
		if (result != null) {
			result.setEstado(EstadoEnum.ACT);
			actualizar(result);
		} else {
			RolMenuEt rolMenNew = new RolMenuEt();
			rolMenNew.setMenu(menu.getMenuPadre());
			rolMenNew.setRol(rol);
			crear(rolMenNew);
		}

	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarRolesMenu(RolEt rol, UsuarioEt user) throws EntidadNoGrabadaException {
		for (RolMenuEt rolMen : rol.getRolMenu()) {
			RolMenuEt rolSave = getRolMenu(rolMen.getRol(), rolMen.getMenu());
			if (rolSave != null) {
				rolSave.setEstado(EstadoEnum.ACT);
				rolSave.audit(user, ActionAuditedEnum.UPD);
				rolSave.setConsulta(rolMen.getConsulta());
				actualizar(rolSave);
			} else {
				rolMen.audit(user, ActionAuditedEnum.NEW);
				crear(rolMen);
				preparePadre(rolMen.getRol(), rolMen.getMenu());
			}
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void invalidarAccesos(RolEt rol, UsuarioEt user) throws EntidadNoGrabadaException {
		sql = new StringBuilder("select o from RolMenuEt o ");
		sql.append("where o.rol= :rol ");
		sql.append("and o.estado= :estado ");
		TypedQuery<RolMenuEt> rolMen = em.createQuery(sql.toString(), RolMenuEt.class);
		rolMen.setParameter("rol", rol);
		rolMen.setParameter("estado", EstadoEnum.ACT);
		List<RolMenuEt> result_0 = rolMen.getResultList();
		for (RolMenuEt rolMenu : result_0) {
			rolMenu.setEstado(EstadoEnum.INA);
			actualizar(rolMenu);
		}
		em.flush();
		em.clear();
	}

	@Remove
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void remove() {
		iMenuDao.remove();
		System.out.println("Finalizado Statefull Bean : " + this.getClass().getCanonicalName());
	}

	@PreDestroy
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void detached() {
		System.out.println("Terminado Statefull Bean : " + this.getClass().getCanonicalName());
	}

}
