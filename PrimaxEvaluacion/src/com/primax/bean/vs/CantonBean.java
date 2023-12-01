package com.primax.bean.vs;

import java.io.Serializable;
import java.util.ArrayList;
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
import com.primax.jpa.param.CantonEt;
import com.primax.jpa.param.ProvinciaEt;
import com.primax.srv.idao.ICantonDao;
import com.primax.srv.idao.IProvinciaDao;

@Named("cantonBn")
@ViewScoped
public class CantonBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = -4553203972483090230L;

	@EJB
	private IProvinciaDao iProvinciaDao;
	@EJB
	private ICantonDao iCantonDao;

	@Override
	public void onDestroy() {
		iProvinciaDao.remove();
		iCantonDao.remove();
	}

	private ProvinciaEt provinciaSeleccionada = new ProvinciaEt();
	private List<CantonEt> cantones = new ArrayList<>();
	private CantonEt cantonSeleccionado = new CantonEt();

	private String textoBusqueda;

	@Inject
	private AppMain appMain;

	@PostConstruct
	public void init() {
		buscar();
	}

	public void buscar() {
		cantones = iCantonDao.getCantonEtByCondicion(textoBusqueda);
	}

	public void modificaCanton(CantonEt canton) {
		cantonSeleccionado = canton;
		provinciaSeleccionada = canton.getProvincia();
	}

	public void nuevoCanton() {
		cantonSeleccionado = new CantonEt();
	}

	public String getTextoBusqueda() {
		return textoBusqueda;
	}

	public void setTextoBusqueda(String textoBusqueda) {
		this.textoBusqueda = textoBusqueda;
	}

	public ProvinciaEt getProvinciaSeleccionada() {
		return provinciaSeleccionada;
	}

	public void setProvinciaSeleccionada(ProvinciaEt provinciaSeleccionada) {
		this.provinciaSeleccionada = provinciaSeleccionada;
	}

	public List<CantonEt> getCantones() {
		return cantones;
	}

	public void setCantones(List<CantonEt> cantones) {
		this.cantones = cantones;
	}

	public CantonEt getCantonSeleccionado() {
		return cantonSeleccionado;
	}

	public void setCantonSeleccionado(CantonEt cantonSeleccionado) {
		this.cantonSeleccionado = cantonSeleccionado;
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
			mensaje = validacionGuardar(cantonSeleccionado);
			if (!mensaje.equals("")) {
				showInfo(mensaje, FacesMessage.SEVERITY_ERROR, null, null);
				return;
			}
			cantonSeleccionado.setProvincia(provinciaSeleccionada);
			if (cantonSeleccionado.getIdCanton() == null) {
				cantonSeleccionado.setUsuarioRegistra(appMain.getUsuario());
				iCantonDao.crear(cantonSeleccionado);
			} else {
				iCantonDao.actualizar(cantonSeleccionado);
			}
			buscar();
			showInfo("Dato Guardado", FacesMessage.SEVERITY_INFO);
			RequestContext.getCurrentInstance().execute("PF('dialog_04_1').hide();");
		} catch (EntidadNoGrabadaException e) {
			e.printStackTrace();
		}
	}

	public String validacionGuardar(CantonEt canton) {
		String mensaje = "";
		try {
			if (canton.getNombreCanton() == null) {
				mensaje = "Por favor ingresar nombre";
				return mensaje;
			}
			if (canton.getInecCanton() == null) {
				mensaje = "Por favor ingresar código Inec";
				return mensaje;
			}
			if (provinciaSeleccionada == null) {
				mensaje = "Por favor seleccionar provincia";
				return mensaje;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método validacionGuardar " + " " + e.getMessage());
		}
		return mensaje;
	}

	public List<ProvinciaEt> getListaProvincias() {
		return iProvinciaDao.getProvinciasByCondicion(null);
	}

	public SelectItem[] getEstadosActIna() {
		SelectItem[] items = new SelectItem[2];
		items[0] = new SelectItem(EstadoEnum.ACT, EstadoEnum.ACT.getDescripcion());
		items[1] = new SelectItem(EstadoEnum.INA, EstadoEnum.INA.getDescripcion());
		return items;
	}
}
