package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.param.NivelEsfuerzoEt;
import com.primax.srv.idao.INivelEsfuerzoDao;

@Named
@ApplicationScoped
public class NivelEsfuerzoConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		INivelEsfuerzoDao iNivelEsfuerzoDao = EJB(EnumNaming.INivelEsfuerzoDao);
		if (value != null) {
			Long id = Long.parseLong(value);
			NivelEsfuerzoEt nivelEsfuerzo = iNivelEsfuerzoDao.getNivelEsfuerzoById(id);
			iNivelEsfuerzoDao.remove();
			return nivelEsfuerzo;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && !value.toString().isEmpty()) {
			NivelEsfuerzoEt var = (NivelEsfuerzoEt) value;
			return (var != null && var.getIdNivelEsfuerzo() != null) ? var.getIdNivelEsfuerzo().toString() : null;
		} else {
			return "";
		}
	}

}
