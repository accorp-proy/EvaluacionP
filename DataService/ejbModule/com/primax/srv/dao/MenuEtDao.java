package com.primax.srv.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.google.common.collect.ImmutableSet;
import com.primax.bean.util.Functions;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.sec.MenuEt;
import com.primax.jpa.sec.RolEt;
import com.primax.jpa.sec.RolMenuEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IMenuEtDao;
import com.primax.srv.util.QUL;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class MenuEtDao extends GenericDao<MenuEt, Long> implements IMenuEtDao {

	private StringBuilder sql;

	public MenuEtDao() {
		super(MenuEt.class);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public MenuEt getMenuUrl(String url) {
		sql = new StringBuilder("from MenuEt o ");
		sql.append("where o.menuPadre is not null ");
		sql.append("and o.estado = :estado ");
		sql.append("and o.urlMenu = :url ");
		TypedQuery<MenuEt> query = em.createQuery(sql.toString(), MenuEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("url", url);
		List<MenuEt> result = query.getResultList();
		return getUnique(result);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarMenu(MenuEt menu) throws EntidadNoGrabadaException {
		if (menu.getIdMenu() == null) {
			crear(menu);
		} else {
			actualizar(menu);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<RolMenuEt> getMenuModuloUsuario(UsuarioEt usuario) {
		List<RolMenuEt> modulos = getModules(usuario);
		for (RolMenuEt modulo : modulos) {
			modulo.setRolMenuRec(getMenues(modulo.getMenu(), usuario));
		}
		return modulos;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<RolMenuEt> getModules(UsuarioEt usuario) {
		sql = new StringBuilder("select o.rol from RolUsuarioEt o ");
		sql.append("where o.usuario=:usuario ");
		sql.append("and o.estado=:estado ");
		TypedQuery<RolEt> queryRol = em.createQuery(sql.toString(), RolEt.class);
		queryRol.setParameter("usuario", usuario);
		queryRol.setParameter("estado", EstadoEnum.ACT);

		List<RolEt> roles = queryRol.getResultList();

		if (roles.isEmpty())
			return Collections.emptyList();

		sql = new StringBuilder("select o from RolMenuEt o inner join o.menu m ");
		sql.append("where o.rol in :roles ");
		sql.append("and o.estado=:estado ");
		sql.append("and m.estado=:estado ");
		sql.append("and o.menu.menuPadre is null ");
		sql.append("order by m.ordenMenu ");
		TypedQuery<RolMenuEt> queryMenu = em.createQuery(sql.toString(), RolMenuEt.class);

		queryMenu.setParameter("roles", roles);
		queryMenu.setParameter("estado", EstadoEnum.ACT);

		List<RolMenuEt> result = queryMenu.getResultList();

		result = result.stream().filter(Functions.distinctByKey(RolMenuEt::getMenu)).collect(Collectors.toList());

		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<RolMenuEt> getMenues(MenuEt modulo, UsuarioEt usuario) {

		List<RolEt> roles = new ArrayList<>();
		for (int i = 0; i < usuario.getRolesUsario().size(); i++) {
			roles.add(usuario.getRolesUsario().get(i).getRol());
		}

		sql = new StringBuilder("select o from RolMenuEt o ");
		sql.append("where o.menu.menuPadre= :modulo ");
		sql.append("and o.rol in (:roles) ");
		sql.append("and o.estado=:estado ");
		sql.append("and o.menu.estado= :estado ");
		sql.append("order by o.menu.ordenMenu ");
		TypedQuery<RolMenuEt> query = em.createQuery(sql.toString(), RolMenuEt.class);
		query.setParameter("modulo", modulo);
		query.setParameter("roles", roles);
		query.setParameter("estado", EstadoEnum.ACT);

		List<RolMenuEt> resultRolMenu = query.getResultList();

		List<RolMenuEt> withoutDuplicates = ImmutableSet.copyOf(resultRolMenu).asList();

		for (RolMenuEt rolMenu : withoutDuplicates) {
			rolMenu.setRolMenuRec(getMenues(rolMenu.getMenu(), usuario));
		}
		return withoutDuplicates;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public boolean verificaMenu(String URI, UsuarioEt usuario) {

		List<RolEt> roles = new ArrayList<>();
		for (int i = 0; i < usuario.getRolesUsario().size(); i++) {
			roles.add(usuario.getRolesUsario().get(i).getRol());
		}

		sql = new StringBuilder("select count(o.menu) from RolMenuEt o ");
		sql.append("where o.rol in(:roles) ");
		sql.append("and o.menu.urlMenu= :uri ");
		sql.append("and o.estado= :estado ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("roles", roles);
		query.setParameter("uri", URI);
		query.setParameter("estado", EstadoEnum.ACT);
		Long result = (Long) query.getSingleResult();
		return result >= 1;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<MenuEt> getMenuesbyCondicion(String condicion) {
		sql = new StringBuilder("from MenuEt o ");
		sql.append("where o.menuPadre is null ");
		if (condicion != null && !condicion.isEmpty()) {
			sql.append("and (o.idMenu= :idMen or 0=:idMen) ");
			sql.append("and (upper(o.descMenu) like :desc or :desc='') ");
		}

		TypedQuery<MenuEt> query = em.createQuery(sql.toString(), MenuEt.class);
		if (condicion != null && !condicion.isEmpty()) {
			query.setParameter("idMen", QUL.toLong(condicion));
			query.setParameter("desc", "%" + QUL.getString(condicion.toUpperCase()) + "%");
		}
		List<MenuEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<MenuEt> getOpcionbyCondicion(String condicion) {
		sql = new StringBuilder("select o from MenuEt o ");
		sql.append("where o.menuPadre is not null ");
		if (condicion != null && !condicion.isEmpty()) {
			sql.append("and (o.idMenu= :idMen or 0=:idMen) ");
			sql.append("and (upper(o.descMenu) like :desc or :desc='') ");
		}

		TypedQuery<MenuEt> query = em.createQuery(sql.toString(), MenuEt.class);
		if (condicion != null && !condicion.isEmpty()) {
			query.setParameter("idMen", QUL.toLong(condicion));
			query.setParameter("desc", "%" + QUL.getString(condicion.toUpperCase()) + "%");
		}
		List<MenuEt> result = query.getResultList();
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<MenuEt> getModules() {
		sql = new StringBuilder("from MenuEt o ");
		sql.append("where o.menuPadre is null ");
		sql.append("and o.estado= :estado ");
		TypedQuery<MenuEt> query = em.createQuery(sql.toString(), MenuEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		List<MenuEt> result = query.getResultList();
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<MenuEt> getModulesChild(EstadoEnum... estado) {
		sql = new StringBuilder("select o from MenuEt o ");
		sql.append("where o.menuPadre is null ");
		sql.append("and o.estado in(:estado)");
		sql.append("ORDER BY o.ordenMenu");
		TypedQuery<MenuEt> query = em.createQuery(sql.toString(), MenuEt.class);
		query.setParameter("estado", Arrays.asList(estado));
		List<MenuEt> result = query.getResultList();
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<MenuEt> getChilds(MenuEt menu, EstadoEnum... estado) {
		sql = new StringBuilder("from MenuEt o ");
		sql.append("where o.menuPadre= :padre ");
		sql.append("and o.estado in (:estado) ");
		sql.append("ORDER BY o.ordenMenu");
		TypedQuery<MenuEt> menues = em.createQuery(sql.toString(), MenuEt.class);
		menues.setParameter("padre", menu);
		menues.setParameter("estado", Arrays.asList(estado));
		return menues.getResultList();
	}

	@Override
	public MenuEt getModulobyId(long id) {
		try {
			MenuEt menu = recuperar(id);
			return menu;
		} catch (EntidadNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
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
