package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.param.NivelColorEt;
import com.primax.srv.idao.INivelColorDao;

@Named
@ApplicationScoped
public class NivelColorConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		INivelColorDao iNivelColorDao = EJB(EnumNaming.INivelColorDao);
		if (value != null) {
			Long id = Long.parseLong(value);
			NivelColorEt nivelColor = iNivelColorDao.getNivelColorById(id);
			iNivelColorDao.remove();
			return nivelColor;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && !value.toString().isEmpty()) {
			NivelColorEt var = (NivelColorEt) value;
			return (var != null && var.getIdNivelColor() != null) ? var.getIdNivelColor().toString() : null;
		} else {
			return "";
		}
	}

}
