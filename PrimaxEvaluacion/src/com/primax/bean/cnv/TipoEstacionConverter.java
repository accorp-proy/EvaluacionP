package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.param.TipoEstacionEt;
import com.primax.srv.idao.ITipoEstacionDao;

@Named
@ApplicationScoped
public class TipoEstacionConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		ITipoEstacionDao iTipoEstacionDao = EJB(EnumNaming.ITipoEstacionDao);
		if (value != null) {
			Long id = Long.parseLong(value);
			TipoEstacionEt tipoEstacion = iTipoEstacionDao.getTipoEstacion(id);
			iTipoEstacionDao.remove();
			return tipoEstacion;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && !value.toString().isEmpty()) {
			TipoEstacionEt var = (TipoEstacionEt) value;
			return (var != null && var.getIdTipoEstacion() != null) ? var.getIdTipoEstacion().toString() : null;
		} else {
			return "";
		}
	}

}
