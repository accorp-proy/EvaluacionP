package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.param.AgenciaEt;
import com.primax.srv.idao.IAgenciaDao;

@Named
@ApplicationScoped
public class AgenciaConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		IAgenciaDao iAgenciaDao = EJB(EnumNaming.IAgenciaDao);
		if (value != null) {
			Long id = Long.parseLong(value);
			AgenciaEt agencia = iAgenciaDao.getAgenciaById(id);
			iAgenciaDao.remove();
			return agencia;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && !value.toString().isEmpty()) {
			AgenciaEt var = (AgenciaEt) value;
			return (var != null && var.getIdAgencia() != null) ? var.getIdAgencia().toString() : null;
		} else {
			return "";
		}
	}

}
