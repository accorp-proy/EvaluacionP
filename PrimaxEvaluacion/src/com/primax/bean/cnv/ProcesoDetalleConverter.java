package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.param.ProcesoDetalleEt;
import com.primax.srv.idao.IProcesoDetalleDao;

@Named
@ApplicationScoped
public class ProcesoDetalleConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		IProcesoDetalleDao iProcesoDetalleDao = EJB(EnumNaming.IProcesoDetalleDao);
		if (value != null) {
			Long id = Long.parseLong(value);
			ProcesoDetalleEt procesoDetalle = iProcesoDetalleDao.getProcesoDetalle(id);
			iProcesoDetalleDao.remove();
			return procesoDetalle;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && !value.toString().isEmpty()) {
			ProcesoDetalleEt var = (ProcesoDetalleEt) value;
			return (var != null && var.getIdProcesoDetalle() != null) ? var.getIdProcesoDetalle().toString() : null;
		} else {
			return "";
		}
	}

}
