package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.srv.idao.IEvaluacionDao;

@Named
@ApplicationScoped
public class EvaluacionConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		IEvaluacionDao iEvaluacionDao = EJB(EnumNaming.IEvaluacionDao);
		if (value != null) {
			Long id = Long.parseLong(value);
			EvaluacionEt evaluacion = iEvaluacionDao.getEvaluacion(id);
			iEvaluacionDao.remove();
			return evaluacion;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && !value.toString().isEmpty()) {
			EvaluacionEt var = (EvaluacionEt) value;
			return (var != null && var.getIdEvaluacion() != null) ? var.getIdEvaluacion().toString() : null;
		} else {
			return "";
		}
	}

}
