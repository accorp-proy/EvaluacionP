package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.param.ParametrosGeneralesEt;
import com.primax.srv.idao.IParametrolGeneralDao;

@Named
@ApplicationScoped
public class ParametrosConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		IParametrolGeneralDao iparametrosDao = EJB(EnumNaming.IParametroDao);
		if (value != null) {
			Long id = Long.valueOf(value);
			ParametrosGeneralesEt param = iparametrosDao.getParametrosGeneralById(id);
			iparametrosDao.remove();
			return param;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent componet, Object value) {
		if (value != null && !value.toString().isEmpty()) {
			ParametrosGeneralesEt var = (ParametrosGeneralesEt) value;
			return (var != null && var.getIdParametroGeneral() != null) ? var.getIdParametroGeneral().toString() : null;
		} else {
			return "";
		}
	}

}
