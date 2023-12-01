package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.param.ResponsableEt;
import com.primax.srv.idao.IResponsableDao;

@Named
@ApplicationScoped
public class ResponsableConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		IResponsableDao iResponsableDao = EJB(EnumNaming.IResponsableDao);
		if (value != null) {
			Long id = Long.parseLong(value);
			ResponsableEt responsable = iResponsableDao.getResponsable(id);
			iResponsableDao.remove();
			return responsable;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && !value.toString().isEmpty()) {
			ResponsableEt var = (ResponsableEt) value;
			return (var != null && var.getIdResponsable() != null) ? var.getIdResponsable().toString() : null;
		} else {
			return "";
		}
	}

}
