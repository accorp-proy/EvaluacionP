package com.primax.srv.dao;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.primax.jpa.sec.UsuarioEt;
import com.primax.jpa.sec.UsuarioHistorialEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IUsuarioHistorialDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class UsuarioHistorialDao extends GenericDao<UsuarioHistorialEt, Long> implements IUsuarioHistorialDao {

	public UsuarioHistorialDao() {
		super(UsuarioHistorialEt.class);
	}

	private StringBuilder sql;

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String getLoginUsuarioActividadMensual(UsuarioEt usuario, Date fechaDesde, Date fechaHasta) {
		sql = new StringBuilder("select count(*) from UsuarioHistorialEt o ");
		sql.append("where o.usuario = :usuario ");
		sql.append("and  (o.fechaLogin between :fechaDesde  and :fechaHasta ) ");

		Long count =
				((Long) em.createQuery(sql.toString()).setParameter("usuario", usuario).setParameter("fechaDesde", fechaDesde)
						.setParameter("fechaHasta", fechaHasta).getSingleResult());

		String conteo = Long.toString(count);

		return conteo;

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
