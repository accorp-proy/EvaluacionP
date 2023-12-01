package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.ArqueoFondoSueltoEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IArqueoFondoSueltoDao extends IGenericDao<ArqueoFondoSueltoEt, Long> {

	public void remove();

	public List<ArqueoFondoSueltoEt> getArqueoFondoSuelto(String condicion);

	public void guardarArqueoFondoSuelto(ArqueoFondoSueltoEt arqueoFondoSuelto, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
