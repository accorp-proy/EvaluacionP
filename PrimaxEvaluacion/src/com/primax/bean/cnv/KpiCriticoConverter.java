package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.param.KPICriticoEt;
import com.primax.srv.idao.IKPICriticoDao;

@Named
@ApplicationScoped
public class KpiCriticoConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		IKPICriticoDao iKPICriticoDao = EJB(EnumNaming.IKPICriticoDao);
		if (value != null) {
			Long id = Long.parseLong(value);
			KPICriticoEt kPICritico = iKPICriticoDao.getKPICritico(id);
			iKPICriticoDao.remove();
			return kPICritico;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && !value.toString().isEmpty()) {
			KPICriticoEt var = (KPICriticoEt) value;
			return (var != null && var.getIdkpiCritico() != null) ? var.getIdkpiCritico().toString() : null;
		} else {
			return "";
		}
	}

}
