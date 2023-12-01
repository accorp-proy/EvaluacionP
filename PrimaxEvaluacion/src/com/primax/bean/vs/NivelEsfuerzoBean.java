package com.primax.bean.vs;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.NivelEsfuerzoEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.INivelEsfuerzoDao;

@Named("NivelEsfuerzoBn")
@ViewScoped
public class NivelEsfuerzoBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private INivelEsfuerzoDao iNivelEsfuerzoDao;

	@Inject
	private AppMain appMain;

	private String condicion;

	private List<NivelEsfuerzoEt> nivelesEsfuerzo;
	private NivelEsfuerzoEt nivelEsfuerzoSeleccionado;

	@Override
	protected void init() {
		inicializarObj();
		buscar();
	}

	public void inicializarObj() {
		nivelEsfuerzoSeleccionado = new NivelEsfuerzoEt();
	}

	public void buscar() {
		try {
			nivelesEsfuerzo = iNivelEsfuerzoDao.getNivelEsfuerzoList(condicion);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}
	}

	public void guardar() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			iNivelEsfuerzoDao.guardarNivelEsfuerzo(nivelEsfuerzoSeleccionado, usuario);
			showInfo("Información Grabada con Éxito ", FacesMessage.SEVERITY_INFO);
			RequestContext.getCurrentInstance().execute("PF('dialog_20_1').hide();");
			buscar();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}
	}

	public void nuevo() {
		nivelEsfuerzoSeleccionado = new NivelEsfuerzoEt();
	}

	public void modificar(NivelEsfuerzoEt nivelEsfuerzo) {
		nivelEsfuerzoSeleccionado = nivelEsfuerzo;
	}

	public SelectItem[] getEstadosActIna() {
		SelectItem[] items = new SelectItem[2];
		items[0] = new SelectItem(EstadoEnum.ACT, EstadoEnum.ACT.getDescripcion());
		items[1] = new SelectItem(EstadoEnum.INA, EstadoEnum.INA.getDescripcion());
		return items;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public List<NivelEsfuerzoEt> getNivelesEsfuerzo() {
		return nivelesEsfuerzo;
	}

	public void setNivelesEsfuerzo(List<NivelEsfuerzoEt> nivelesEsfuerzo) {
		this.nivelesEsfuerzo = nivelesEsfuerzo;
	}

	public NivelEsfuerzoEt getNivelEsfuerzoSeleccionado() {
		return nivelEsfuerzoSeleccionado;
	}

	public void setNivelEsfuerzoSeleccionado(NivelEsfuerzoEt nivelEsfuerzoSeleccionado) {
		this.nivelEsfuerzoSeleccionado = nivelEsfuerzoSeleccionado;
	}

	@Override
	protected void onDestroy() {
		iNivelEsfuerzoDao.remove();
	}

}
