package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.gen.PersonaEt;
import com.primax.srv.idao.IPersonaDao;

@Named
@ApplicationScoped
public class PersonaConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		IPersonaDao iPersonaDao = EJB(EnumNaming.IPersonaDao);
		if (value != null) {
			Long id = Long.parseLong(value);
			PersonaEt persona = iPersonaDao.getPersonaById(id);
			iPersonaDao.remove();
			return persona;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && !value.toString().isEmpty()) {
			PersonaEt var = (PersonaEt) value;
			return (var != null && var.getIdPersona() != null) ? var.getIdPersona().toString() : null;
		} else {
			return "";
		}
	}

}
