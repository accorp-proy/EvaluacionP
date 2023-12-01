package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.param.CantonEt;
import com.primax.srv.idao.ICantonDao;

@Named
@ApplicationScoped
public class CantonConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		ICantonDao icantonDao = EJB(EnumNaming.ICantonDao);
		if (value != null) {
			Long id = Long.parseLong(value);
			CantonEt canton = icantonDao.getCantonbyId(id);
			icantonDao.remove();
			return canton;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			CantonEt var = (CantonEt) value;
			return (var != null && var.getIdCanton() != null) ? var.getIdCanton().toString() : null;
		} else {
			return "";
		}
	}

}
