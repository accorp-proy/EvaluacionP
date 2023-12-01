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

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.ProvinciaEt;
import com.primax.jpa.sec.RolEt;
import com.primax.jpa.sec.RolUsuarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IRolUsuarioDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class RolUsuarioDao extends GenericDao<RolUsuarioEt, Long> implements IRolUsuarioDao {

	public RolUsuarioDao() {
		super(RolUsuarioEt.class);
	}

	private StringBuilder sql;

	@Override
	public List<UsuarioEt> getUsuariosPorRol(RolEt rol) {
		sql = new StringBuilder("select o.usuario from RolUsuarioEt o ");
		sql.append("where o.rol = :rol ");
		sql.append("and o.estado = :estado ");
		sql.append("and o.rol.estado= :estado ");
		sql.append("and o.usuario.estado = :estado ");
		TypedQuery<UsuarioEt> query = em.createQuery(sql.toString(), UsuarioEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("rol", rol);
		List<UsuarioEt> result = query.getResultList();
		return result;
	}

	public List<UsuarioEt> getUsuariobyRolAndProvincia(RolEt rol, ProvinciaEt provincia) {

		sql = new StringBuilder("select o.usuario from RolUsuarioEt o  ");
		sql.append("where o.rol = :rol ");
		sql.append("and o.usuario.oficina.provinciaOficina = :prov ");
		sql.append("and o.estado = :estado ");
		sql.append("and o.rol.estado= :estado ");
		sql.append("and o.usuario.estado = :estado ");
		TypedQuery<UsuarioEt> query = em.createQuery(sql.toString(), UsuarioEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("rol", rol);
		query.setParameter("prov", provincia);
		List<UsuarioEt> result = query.getResultList();
		return result;
	}

	@Override
	public RolUsuarioEt getRolUsuarioById(Long id) {
		try {
			return recuperar(id);
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
