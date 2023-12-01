package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.param.NivelEvaluacionEt;
import com.primax.srv.idao.INivelEvaluacionDao;

@Named
@ApplicationScoped
public class NivelEvaluacionConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		INivelEvaluacionDao iNivelEvaluacionDao = EJB(EnumNaming.INivelEvaluacionDao);
		if (value != null) {
			Long id = Long.parseLong(value);
			NivelEvaluacionEt nivelEvaluacion = iNivelEvaluacionDao.getNivelEvaluacion(id);
			iNivelEvaluacionDao.remove();
			return nivelEvaluacion;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && !value.toString().isEmpty()) {
			NivelEvaluacionEt var = (NivelEvaluacionEt) value;
			return (var != null && var.getIdNivelEvaluacion() != null) ? var.getIdNivelEvaluacion().toString() : null;
		} else {
			return "";
		}
	}

}
