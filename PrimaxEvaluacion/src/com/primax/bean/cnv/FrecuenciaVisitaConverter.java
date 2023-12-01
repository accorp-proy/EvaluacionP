package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.param.FrecuenciaVisitaEt;
import com.primax.srv.idao.IFrecuenciaVisitaDao;

@Named
@ApplicationScoped
public class FrecuenciaVisitaConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		IFrecuenciaVisitaDao iFrecuenciaVisitaDao = EJB(EnumNaming.IFrecuenciaVisitaDao);
		if (value != null) {
			Long id = Long.parseLong(value);
			FrecuenciaVisitaEt frecuenciaVisita = iFrecuenciaVisitaDao.getFrecuenciaVisitaById(id);
			iFrecuenciaVisitaDao.remove();
			return frecuenciaVisita;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && !value.toString().isEmpty()) {
			FrecuenciaVisitaEt var = (FrecuenciaVisitaEt) value;
			return (var != null && var.getIdFrecuenciaVisita() != null) ? var.getIdFrecuenciaVisita().toString() : null;
		} else {
			return "";
		}
	}

}
