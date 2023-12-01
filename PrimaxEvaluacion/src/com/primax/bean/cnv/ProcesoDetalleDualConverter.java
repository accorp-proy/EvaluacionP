package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.jpa.param.ProcesoDetalleEt;

@Named
@ApplicationScoped
public class ProcesoDetalleDualConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		@SuppressWarnings("unchecked")
		DualListModel<ProcesoDetalleEt> model = (DualListModel<ProcesoDetalleEt>) ((PickList) component).getValue();
		if (value != null) {
			Long id = Long.parseLong(value);
			for (ProcesoDetalleEt dimensionDetalle : model.getSource()) {
				if (dimensionDetalle.getIdProcesoDetalle().equals(id)) {
					return dimensionDetalle;
				}
			}
			for (ProcesoDetalleEt dimensionDetalle : model.getTarget()) {
				if (dimensionDetalle.getIdProcesoDetalle().equals(id)) {
					return dimensionDetalle;
				}
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && !value.toString().isEmpty()) {
			ProcesoDetalleEt var = (ProcesoDetalleEt) value;
			String varRetorna = (var != null && var.getIdProcesoDetalle() != null)
					? var.getIdProcesoDetalle().toString() : null;
			return varRetorna;
		} else {
			return "";
		}
	}

}
