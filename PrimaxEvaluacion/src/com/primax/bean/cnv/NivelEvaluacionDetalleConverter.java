package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.param.NivelEvaluacionDetalleEt;
import com.primax.srv.idao.INivelEvaluacionDetalleDao;

@Named
@ApplicationScoped
public class NivelEvaluacionDetalleConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		INivelEvaluacionDetalleDao iNivelEvaluacionDetalleDao = EJB(EnumNaming.INivelEvaluacionDetalleDao);
		if (value != null) {
			Long id = Long.parseLong(value);
			NivelEvaluacionDetalleEt nivelEvaluacionDetalle = iNivelEvaluacionDetalleDao.getNivelEvaluacionDetalle(id);
			iNivelEvaluacionDetalleDao.remove();
			return nivelEvaluacionDetalle;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && !value.toString().isEmpty()) {
			NivelEvaluacionDetalleEt var = (NivelEvaluacionDetalleEt) value;
			return (var != null && var.getIdNivelEvaluacionDetalle() != null) ? var.getIdNivelEvaluacionDetalle().toString() : null;
		} else {
			return "";
		}
	}

}
