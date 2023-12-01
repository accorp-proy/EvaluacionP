package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.param.CriterioEvaluacionDetalleEt;
import com.primax.srv.idao.ICriterioEvaluacionDetalleDao;

@Named
@ApplicationScoped
public class CriterioEvaluacionDetalleConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		ICriterioEvaluacionDetalleDao iCriterioEvaluacionDetalleDao = EJB(EnumNaming.ICriterioEvaluacionDetalleDao);
		if (value != null) {
			Long id = Long.parseLong(value);
			CriterioEvaluacionDetalleEt criterioEvaluacionDetalle = iCriterioEvaluacionDetalleDao
					.getCriterioEvaluacionDetalle(id);
			iCriterioEvaluacionDetalleDao.remove();
			return criterioEvaluacionDetalle;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && !value.toString().isEmpty()) {
			CriterioEvaluacionDetalleEt var = (CriterioEvaluacionDetalleEt) value;
			return (var != null && var.getIdCriterioEvaluacionDetalle() != null)
					? var.getIdCriterioEvaluacionDetalle().toString() : null;
		} else {
			return "";
		}
	}

}
