package com.primax.bean.vs;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.ProvinciaEt;
import com.primax.srv.idao.IProvinciaDao;

@Named("provinciaBn")
@ViewScoped
public class ProvinciaBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 4152483712363035833L;

	@EJB
	private IProvinciaDao provinciaDao;

	private ProvinciaEt provinciaSeleccionada;
	private List<ProvinciaEt> provincias;
	private String textoBusquedaProvincia;

	@Inject
	private AppMain appMain;

	@PostConstruct
	public void init() {
		provinciaSeleccionada = new ProvinciaEt();
		provincias = provinciaDao.getProvinciasByCondicion(null);
	}

	public void modificaProvincia(ProvinciaEt provinciaPulsada) {
		this.provinciaSeleccionada = provinciaPulsada;
	}

	public ProvinciaEt getProvinciaSeleccionada() {
		return provinciaSeleccionada;
	}

	public void setProvinciaSeleccionada(ProvinciaEt provinciaSeleccionada) {
		this.provinciaSeleccionada = provinciaSeleccionada;
	}

	public List<ProvinciaEt> getProvincias() {
		return provincias;
	}

	public void buscar() {
		provincias = provinciaDao.getProvinciasByCondicion(textoBusquedaProvincia);
	}

	public void nuevaProvincia() {
		provinciaSeleccionada = new ProvinciaEt();
	}

	public void setProvincias(List<ProvinciaEt> provincias) {
		this.provincias = provincias;
	}

	public String getTextoBusquedaProvincia() {
		return textoBusquedaProvincia;
	}

	public void setTextoBusquedaProvincia(String textoBusquedaProvincia) {
		this.textoBusquedaProvincia = textoBusquedaProvincia;
	}

	public AppMain getAppMain() {
		return appMain;
	}

	public void setAppMain(AppMain appMain) {
		this.appMain = appMain;
	}

	public void guardar() {
		String mensaje = "";
		try {
			mensaje = validacionGuardar(provinciaSeleccionada);
			if (!mensaje.equals("")) {
				showInfo(mensaje, FacesMessage.SEVERITY_ERROR, null, null);
				return;
			}
			if (provinciaSeleccionada.getIdProvincia() == null) {
				provinciaSeleccionada.setUsuarioRegistra(appMain.getUsuario());
				provinciaDao.crear(provinciaSeleccionada);
			} else {
				provinciaDao.actualizar(provinciaSeleccionada);
			}
			provincias = provinciaDao.getProvinciasByCondicion(null);
			showInfo("Dato Guardado", FacesMessage.SEVERITY_INFO);
			RequestContext.getCurrentInstance().execute("PF('dialog_03_1').hide();");
		} catch (EntidadNoGrabadaException e) {
			e.printStackTrace();
		}

	}

	public String validacionGuardar(ProvinciaEt provincia) {
		String mensaje = "";
		try {
			if (provincia.getNombreProvincia() == null) {
				mensaje = "Por favor ingresar nombre";
				return mensaje;
			}
			if (provincia.getInecProvincia() == null) {
				mensaje = "Por favor ingresar código Inec";
				return mensaje;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método validacionGuardar " + " " + e.getMessage());
		}
		return mensaje;
	}

	public SelectItem[] getEstadosActIna() {
		SelectItem[] items = new SelectItem[2];
		items[0] = new SelectItem(EstadoEnum.ACT, EstadoEnum.ACT.getDescripcion());
		items[1] = new SelectItem(EstadoEnum.INA, EstadoEnum.INA.getDescripcion());
		return items;
	}

	@Override
	public void onDestroy() {
		provinciaDao.remove();
	}

}
