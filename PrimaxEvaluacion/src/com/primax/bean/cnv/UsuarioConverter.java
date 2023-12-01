package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IUsuarioDao;

@Named
@ApplicationScoped
public class UsuarioConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		IUsuarioDao iUsuario = EJB(EnumNaming.IUsuarioDao);
		if (value != null) {
			Long id = Long.parseLong(value);
			UsuarioEt usuario = iUsuario.getUsuarioId(id);
			iUsuario.remove();
			return usuario;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && ((UsuarioEt) value).getIdUsuario() != null) {
			return ((UsuarioEt) value).getIdUsuario().toString();
		} else {
			return "";
		}
	}

}
