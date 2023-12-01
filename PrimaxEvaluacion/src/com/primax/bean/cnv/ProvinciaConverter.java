package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.param.ProvinciaEt;
import com.primax.srv.idao.IProvinciaDao;

@Named
@ApplicationScoped
public class ProvinciaConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		IProvinciaDao iProvinciaDao = EJB(EnumNaming.IProvinciaDao);
		if (value != null) {
			Long id = Long.parseLong(value);
			ProvinciaEt provincia = iProvinciaDao.getProvincia(id);
			iProvinciaDao.remove();
			return provincia;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && !value.toString().isEmpty()) {
			ProvinciaEt var = (ProvinciaEt) value;
			return (var != null && var.getIdProvincia() != null) ? var.getIdProvincia().toString() : null;
		} else {
			return "";
		}
	}

}
