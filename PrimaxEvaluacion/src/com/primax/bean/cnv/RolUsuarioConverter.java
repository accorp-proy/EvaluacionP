package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.sec.RolUsuarioEt;
import com.primax.srv.idao.IRolUsuarioDao;

@Named
@ApplicationScoped
public class RolUsuarioConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		IRolUsuarioDao irolUsuarioDao = EJB(EnumNaming.IRolUsuarioDao);
		if (value != null) {
			Long id = Long.parseLong(value);
			RolUsuarioEt rolUsr = irolUsuarioDao.getRolUsuarioById(id);
			irolUsuarioDao.remove();
			return rolUsr;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			return ((RolUsuarioEt) value).getIdRolUsuario().toString();
		} else {
			return "";
		}
	}

}
