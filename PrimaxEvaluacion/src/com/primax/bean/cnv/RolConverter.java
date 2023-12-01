package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.sec.RolEt;
import com.primax.srv.idao.IRolEtDao;

@Named
@ApplicationScoped
public class RolConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		IRolEtDao iRolDao = EJB(EnumNaming.IRolDao);
		if (value != null) {
			Long id = Long.parseLong(value);
			RolEt rol = iRolDao.getRolbyId(id);
			iRolDao.remove();
			return rol;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			return ((RolEt) value).getIdRol().toString();
		} else {
			return "";
		}
	}

}
