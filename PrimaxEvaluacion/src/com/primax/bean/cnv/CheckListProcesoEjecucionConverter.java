package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.pla.CheckListProcesoEjecucionEt;
import com.primax.srv.idao.ICheckListProcesoEjecucionDao;

@Named
@ApplicationScoped
public class CheckListProcesoEjecucionConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		ICheckListProcesoEjecucionDao iCheckListProcesoEjecucionDao = EJB(EnumNaming.ICheckListProcesoEjecucionDao);
		if (value != null) {
			Long id = Long.parseLong(value);
			CheckListProcesoEjecucionEt checkListProcesoEjecucion = iCheckListProcesoEjecucionDao
					.getCheckListProcesoE(id);
			iCheckListProcesoEjecucionDao.remove();
			return checkListProcesoEjecucion;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && !value.toString().isEmpty()) {
			CheckListProcesoEjecucionEt var = (CheckListProcesoEjecucionEt) value;
			return (var != null && var.getIdCheckListProcesoEjecucion() != null)
					? var.getIdCheckListProcesoEjecucion().toString() : null;
		} else {
			return "";
		}
	}

}
