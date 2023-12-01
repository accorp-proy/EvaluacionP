package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.jpa.param.AgenciaEt;

@Named
@ApplicationScoped
public class AgenciaDualConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		@SuppressWarnings("unchecked")
		DualListModel<AgenciaEt> model = (DualListModel<AgenciaEt>) ((PickList) component).getValue();
		if (value != null) {
			Long id = Long.parseLong(value);
			for (AgenciaEt agencia : model.getSource()) {
				if (agencia.getIdAgencia().equals(id)) {
					agencia.getAgenciaCheckList().size();
					return agencia;
				}
			}
			for (AgenciaEt agencia : model.getTarget()) {
				if (agencia.getIdAgencia().equals(id)) {
					agencia.getAgenciaCheckList().size();
					return agencia;
				}
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && !value.toString().isEmpty()) {
			AgenciaEt var = (AgenciaEt) value;
			String varRetorna = (var != null && var.getIdAgencia() != null) ? var.getIdAgencia().toString() : null;
			return varRetorna;
		} else {
			return "";
		}
	}

}
