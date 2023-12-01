package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.param.RegionEt;
import com.primax.srv.idao.IRegionDao;

@Named
@ApplicationScoped
public class RegionConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		IRegionDao iRegionDao = EJB(EnumNaming.IRegionDao);
		if (value != null) {
			Long id = Long.parseLong(value);
			RegionEt region = iRegionDao.getRegionById(id);
			iRegionDao.remove();
			return region;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			RegionEt var = (RegionEt) value;
			return (var != null && var.getIdRegion() != null) ? var.getIdRegion().toString() : null;
		} else {
			return "";
		}
	}

}
