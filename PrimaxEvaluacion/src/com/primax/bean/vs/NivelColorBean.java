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
import com.primax.jpa.param.NivelColorEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.INivelColorDao;

@Named("NivelColorBn")
@ViewScoped
public class NivelColorBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private INivelColorDao iNivelColorDao;

	@Inject
	private AppMain appMain;

	private String condicion;

	private List<NivelColorEt> nivelColores;
	private NivelColorEt nivelColorSeleccionado;

	@Override
	protected void init() {
		inicializarObj();
		buscar();
	}

	public void inicializarObj() {
		nivelColorSeleccionado = new NivelColorEt();
	}

	public void buscar() {
		try {
			nivelColores = iNivelColorDao.getNivelColorList(condicion);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}
	}

	public void guardar() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			iNivelColorDao.guardarNivelColor(nivelColorSeleccionado, usuario);
			showInfo("Información Grabada con Éxito ", FacesMessage.SEVERITY_INFO);
			RequestContext.getCurrentInstance().execute("PF('dialog_19_1').hide();");
			buscar();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}
	}

	public void nuevo() {
		nivelColorSeleccionado = new NivelColorEt();
	}

	public void modificar(NivelColorEt nivelColor) {
		nivelColorSeleccionado = nivelColor;
	}

	public SelectItem[] getEstadosActIna() {
		SelectItem[] items = new SelectItem[2];
		items[0] = new SelectItem(EstadoEnum.ACT, EstadoEnum.ACT.getDescripcion());
		items[1] = new SelectItem(EstadoEnum.INA, EstadoEnum.INA.getDescripcion());
		return items;
	}

	public List<NivelColorEt> getNivelColores() {
		return nivelColores;
	}

	public void setNivelColores(List<NivelColorEt> nivelColores) {
		this.nivelColores = nivelColores;
	}

	public NivelColorEt getNivelColorSeleccionado() {
		return nivelColorSeleccionado;
	}

	public void setNivelColorSeleccionado(NivelColorEt nivelColorSeleccionado) {
		this.nivelColorSeleccionado = nivelColorSeleccionado;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	@Override
	protected void onDestroy() {
		iNivelColorDao.remove();
	}

}
