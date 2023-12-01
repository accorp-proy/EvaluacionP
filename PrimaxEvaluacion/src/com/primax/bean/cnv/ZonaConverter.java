package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.param.ZonaEt;
import com.primax.srv.idao.IZonaDao;

@Named
@ApplicationScoped
public class ZonaConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		IZonaDao iZonaDao = EJB(EnumNaming.IZonaDao);
		if (value != null) {
			Long id = Long.parseLong(value);
			ZonaEt zona = iZonaDao.getZonaById(id);
			iZonaDao.remove();
			return zona;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && !value.toString().isEmpty()) {
			ZonaEt var = (ZonaEt) value;
			return (var != null && var.getIdZona() != null) ? var.getIdZona().toString() : null;
		} else {
			return "";
		}
	}

}
