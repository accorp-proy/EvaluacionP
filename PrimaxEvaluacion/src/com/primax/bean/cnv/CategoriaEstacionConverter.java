package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.param.CategoriaEstacionEt;
import com.primax.srv.idao.ICategoriaEstacionDao;

@Named
@ApplicationScoped
public class CategoriaEstacionConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		ICategoriaEstacionDao iCategoriaEstacionDao = EJB(EnumNaming.ICategoriaEstacionDao);
		if (value != null) {
			Long id = Long.parseLong(value);
			CategoriaEstacionEt categoriaEstacion = iCategoriaEstacionDao.getCategoriaEstacion(id);
			iCategoriaEstacionDao.remove();
			return categoriaEstacion;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && !value.toString().isEmpty()) {
			CategoriaEstacionEt var = (CategoriaEstacionEt) value;
			return (var != null && var.getIdCategoriaEstacion() != null) ? var.getIdCategoriaEstacion().toString()
					: null;
		} else {
			return "";
		}
	}

}
