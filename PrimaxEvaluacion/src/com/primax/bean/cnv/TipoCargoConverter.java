package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.param.TipoCargoEt;
import com.primax.srv.idao.ITipoCargoDao;

@Named
@ApplicationScoped
public class TipoCargoConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		ITipoCargoDao iTipoCargoDao = EJB(EnumNaming.ITipoCargoDao);
		if (value != null) {
			Long id = Long.parseLong(value);
			TipoCargoEt tipoCargo = iTipoCargoDao.getTipoCargo(id);
			iTipoCargoDao.remove();
			return tipoCargo;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && !value.toString().isEmpty()) {
			TipoCargoEt var = (TipoCargoEt) value;
			return (var != null && var.getIdTipoCargo() != null) ? var.getIdTipoCargo().toString() : null;
		} else {
			return "";
		}
	}

}
