package com.primax.bean.vs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.ConexionEt;
import com.primax.srv.idao.IConexionDao;


@Named("conexionBn")
@ViewScoped
public class ConexionBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = -2498046553560301553L;

	@EJB
	private IConexionDao iConexionDao;
	
	private ConexionEt conexionSeleccionada = new ConexionEt();
	private List<ConexionEt> listaConexiones = new ArrayList<>();

	@Inject
	private AppMain appMain;

	public void init() {
		listaConexiones = iConexionDao.obtenerTodos();
	}
	
	public void cargarConexion(ConexionEt con){
		conexionSeleccionada = con;
	}
	
	public void actualizarConexion(){
		try {
			iConexionDao.actualizar(conexionSeleccionada);
			showInfo("Conexion actualizada correctamente", FacesMessage.SEVERITY_INFO);
		} catch (EntidadNoGrabadaException e) {
			e.printStackTrace();
		}
	}

	public ConexionEt getConexionSeleccionada() {
		return conexionSeleccionada;
	}

	public void setConexionSeleccionada(ConexionEt conexionSeleccionada) {
		this.conexionSeleccionada = conexionSeleccionada;
	}

	public List<ConexionEt> getListaConexiones() {
		return listaConexiones;
	}

	public void setListaConexiones(List<ConexionEt> listaConexiones) {
		this.listaConexiones = listaConexiones;
	}

	public AppMain getAppMain() {
		return appMain;
	}

	public void setAppMain(AppMain appMain) {
		this.appMain = appMain;
	}

	@Override
	public void onDestroy() {
		iConexionDao.remove();
	}
}
