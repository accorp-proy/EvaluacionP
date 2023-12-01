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
import com.primax.jpa.pla.ArqueoFondoSueltoEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IArqueoFondoSueltoDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class ArqueoFondoSueltoDao extends GenericDao<ArqueoFondoSueltoEt, Long> implements IArqueoFondoSueltoDao {

	public ArqueoFondoSueltoDao() {
		super(ArqueoFondoSueltoEt.class);
	}

	private StringBuilder sql;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarArqueoFondoSuelto(ArqueoFondoSueltoEt arqueoFondoSuelto, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (arqueoFondoSuelto.getIdArqueoFondoSuelto() == null) {
			arqueoFondoSuelto.audit(usuario, ActionAuditedEnum.NEW);
			crear(arqueoFondoSuelto);
		} else {
			arqueoFondoSuelto.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(arqueoFondoSuelto);
		}
		em.flush();
		em.clear();
	}

	public List<ArqueoFondoSueltoEt> getArqueoFondoSuelto(String condicion) {
		sql = new StringBuilder("FROM ArqueoFondoSueltoEt o ");
		sql.append("WHERE o.estado = :estado ");
		TypedQuery<ArqueoFondoSueltoEt> query = em.createQuery(sql.toString(), ArqueoFondoSueltoEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		List<ArqueoFondoSueltoEt> result = query.getResultList();
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
