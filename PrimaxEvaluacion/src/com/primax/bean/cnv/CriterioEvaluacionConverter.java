package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.param.CriterioEvaluacionEt;
import com.primax.srv.idao.ICriterioEvaluacionDao;

@Named
@ApplicationScoped
public class CriterioEvaluacionConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		ICriterioEvaluacionDao iCriterioEvaluacionDao = EJB(EnumNaming.ICriterioEvaluacionDao);
		if (value != null) {
			Long id = Long.parseLong(value);
			CriterioEvaluacionEt criterioEvaluacion = iCriterioEvaluacionDao.getCriterioEvaluacion(id);
			iCriterioEvaluacionDao.remove();
			return criterioEvaluacion;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && !value.toString().isEmpty()) {
			CriterioEvaluacionEt var = (CriterioEvaluacionEt) value;
			return (var != null && var.getIdCriterioEvaluacion() != null) ? var.getIdCriterioEvaluacion().toString() : null;
		} else {
			return "";
		}
	}

}
