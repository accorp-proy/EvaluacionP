package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.srv.idao.ITipoChecKListDao;

@Named
@ApplicationScoped
public class TipoCheckListConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		ITipoChecKListDao iTipoChecListDao = EJB(EnumNaming.ITipoChecListDao);
		if (value != null) {
			Long id = Long.parseLong(value);
			TipoChecKListEt tipoChecKList = iTipoChecListDao.getTipoChecList(id);
			iTipoChecListDao.remove();
			return tipoChecKList;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && !value.toString().isEmpty()) {
			TipoChecKListEt var = (TipoChecKListEt) value;
			return (var != null && var.getIdTipoCheckList() != null) ? var.getIdTipoCheckList().toString() : null;
		} else {
			return "";
		}
	}

}
