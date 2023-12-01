package com.primax.bean.as;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.enm.gen.EstadoCivilEnum;
import com.primax.enm.gen.NivelEstudioEnum;
import com.primax.enm.gen.TipoEstadoCobranzaCarteraEnum;
import com.primax.jpa.enums.EstadoChequesEnum;
import com.primax.jpa.enums.EstadoEnum;

@Named("collectorEM")
@ApplicationScoped
public class CollectEnum extends BaseNaming {

	public SelectItem[] getEstados() {
		List<EstadoEnum> estados = new ArrayList<>();
		for (EstadoEnum est : EstadoEnum.values()) {
			if (est.getGrupo() == 1)
				estados.add(est);
		}
		SelectItem[] items = new SelectItem[estados.size()];
		int i = 0;
		for (EstadoEnum g : estados) {
			items[i++] = new SelectItem(g, g.getDescripcion());
		}
		return items;
	}

	public SelectItem[] getEstadosUsuarios() {
		List<EstadoEnum> estados = new ArrayList<>();

		estados.add(EstadoEnum.ACT);
		estados.add(EstadoEnum.SUS);
		estados.add(EstadoEnum.INA);

		SelectItem[] items = new SelectItem[estados.size()];
		int i = 0;
		for (EstadoEnum g : estados) {
			items[i++] = new SelectItem(g, g.getDescripcion());
		}
		return items;
	}

	public SelectItem[] getEstadosCompleto() {
		SelectItem[] items = new SelectItem[EstadoEnum.values().length];
		int i = 0;
		for (EstadoEnum g : EstadoEnum.values()) {
			items[i++] = new SelectItem(g, g.getDescripcion());
		}
		return items;
	}

	public SelectItem[] getEstadosCiviles() {
		SelectItem[] items = new SelectItem[EstadoCivilEnum.values().length];
		int i = 0;
		for (EstadoCivilEnum e : EstadoCivilEnum.values()) {
			items[i++] = new SelectItem(e, e.getDescripcion());
		}
		return items;
	}

	public SelectItem[] getNivelEstudio() {
		SelectItem[] items = new SelectItem[NivelEstudioEnum.values().length];
		int i = 0;
		for (NivelEstudioEnum n : NivelEstudioEnum.values()) {
			items[i++] = new SelectItem(n, n.getDescripcion());
		}
		return items;
	}

	public SelectItem[] getEstadosFacturaCartera() {
		SelectItem[] items = new SelectItem[TipoEstadoCobranzaCarteraEnum.values().length - 1];
		int i = 0;
		for (TipoEstadoCobranzaCarteraEnum n : TipoEstadoCobranzaCarteraEnum.values()) {
			if (!n.getDescripcion().equals("")) {
				items[i++] = new SelectItem(n, n.getDescripcion());
			}
		}
		return items;
	}

	public SelectItem[] getEstadosCheque() {
		SelectItem[] items = new SelectItem[EstadoChequesEnum.values().length];
		int i = 0;
		for (EstadoChequesEnum ec : EstadoChequesEnum.values()) {
			items[i++] = new SelectItem(ec, ec.getDescripcion());
		}
		return items;
	}

}
