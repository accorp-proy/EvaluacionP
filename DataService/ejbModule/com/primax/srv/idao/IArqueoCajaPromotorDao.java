package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.ArqueoCajaPromotorEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IArqueoCajaPromotorDao extends IGenericDao<ArqueoCajaPromotorEt, Long> {

	public void remove();

	public List<ArqueoCajaPromotorEt> getArqueoCajaPromotor(String condicion);

	public void guardarArqueoCajaPromotor(ArqueoCajaPromotorEt arqueoCajaPromotor, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
