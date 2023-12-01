package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.param.ProcesoEt;
import com.primax.srv.idao.IProcesoDao;

@Named
@ApplicationScoped
public class ProcesoConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		IProcesoDao iProcesoDao = EJB(EnumNaming.IProcesoDao);
		if (value != null) {
			Long id = Long.parseLong(value);
			ProcesoEt proceso = iProcesoDao.getProceso(id);
			iProcesoDao.remove();
			return proceso;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && !value.toString().isEmpty()) {
			ProcesoEt var = (ProcesoEt) value;
			return (var != null && var.getIdproceso() != null) ? var.getIdproceso().toString() : null;
		} else {
			return "";
		}
	}

}
