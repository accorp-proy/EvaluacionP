package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.param.TipoInventarioEt;
import com.primax.srv.idao.ITipoInventarioDao;

@Named
@ApplicationScoped
public class TipoInventarioConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		ITipoInventarioDao iTipoInventarioDao = EJB(EnumNaming.ITipoInventarioDao);
		if (value != null) {
			Long id = Long.parseLong(value);
			TipoInventarioEt tipoInventario = iTipoInventarioDao.getTipoInventario(id);
			iTipoInventarioDao.remove();
			return tipoInventario;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && !value.toString().isEmpty()) {
			TipoInventarioEt var = (TipoInventarioEt) value;
			return (var != null && var.getIdTipoInventario() != null) ? var.getIdTipoInventario().toString() : null;
		} else {
			return "";
		}
	}

}
