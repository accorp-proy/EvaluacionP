package com.primax.srv.idao;

import java.util.Date;

import com.primax.jpa.sec.UsuarioEt;
import com.primax.jpa.sec.UsuarioHistorialEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IUsuarioHistorialDao extends IGenericDao<UsuarioHistorialEt, Long> {

	public String getLoginUsuarioActividadMensual(UsuarioEt usuario, Date fechaDesde, Date fechaHasta);

	public void remove();

}