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
import com.primax.jpa.param.ZonaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IZonaDao;

@Named("ZonaBn")
@ViewScoped
public class ZonaBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IZonaDao iZonaDao;

	@Inject
	private AppMain appMain;

	private String condicion;

	private List<ZonaEt> zonas;
	private ZonaEt zonaSeleccionado;

	@Override
	protected void init() {
		inicializarObj();
		buscar();
	}

	public void inicializarObj() {
		zonaSeleccionado = new ZonaEt();
	}

	public void buscar() {
		try {
			zonas = iZonaDao.getZonaList(condicion);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}
	}

	public void guardar() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			iZonaDao.guardarZona(zonaSeleccionado, usuario);
			showInfo("Información Grabada con Éxito ", FacesMessage.SEVERITY_INFO);
			RequestContext.getCurrentInstance().execute("PF('dialog_18_1').hide();");
			buscar();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}
	}

	public void nuevo() {
		zonaSeleccionado = new ZonaEt();
	}

	public void modificar(ZonaEt zona) {
		zonaSeleccionado = zona;
	}

	public SelectItem[] getEstadosActIna() {
		SelectItem[] items = new SelectItem[2];
		items[0] = new SelectItem(EstadoEnum.ACT, EstadoEnum.ACT.getDescripcion());
		items[1] = new SelectItem(EstadoEnum.INA, EstadoEnum.INA.getDescripcion());
		return items;
	}

	public List<ZonaEt> getZonas() {
		return zonas;
	}

	public void setZonas(List<ZonaEt> zonas) {
		this.zonas = zonas;
	}

	public ZonaEt getZonaSeleccionado() {
		return zonaSeleccionado;
	}

	public void setZonaSeleccionado(ZonaEt zonaSeleccionado) {
		this.zonaSeleccionado = zonaSeleccionado;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	@Override
	protected void onDestroy() {
		iZonaDao.remove();
	}

}
