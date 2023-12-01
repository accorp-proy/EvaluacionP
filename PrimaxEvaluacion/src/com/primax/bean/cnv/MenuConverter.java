package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.sec.MenuEt;
import com.primax.srv.idao.IMenuEtDao;

@Named
@ApplicationScoped
public class MenuConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		IMenuEtDao iMenuDao = EJB(EnumNaming.IMenuEtDao);
		if (value != null) {
			Long id = Long.valueOf(value);
			MenuEt mod = iMenuDao.getModulobyId(id);
			iMenuDao.remove();
			return mod;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			return ((MenuEt) value).getIdMenu().toString();
		} else {
			return "";
		}

	}

}
