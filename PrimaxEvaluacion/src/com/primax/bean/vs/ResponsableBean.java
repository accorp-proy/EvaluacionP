package com.primax.bean.vs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.jpa.gen.PersonaEt;
import com.primax.jpa.gen.UbicacionEt;
import com.primax.jpa.param.CantonEt;
import com.primax.jpa.param.ProvinciaEt;
import com.primax.jpa.param.ResponsableEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IPersonaDao;
import com.primax.srv.idao.IProvinciaDao;
import com.primax.srv.idao.IResponsableDao;

@Named("ResponsableBn")
@ViewScoped
public class ResponsableBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IPersonaDao iPersonaDao;
	@EJB
	private IProvinciaDao iProvinciaDao;
	@EJB
	private IResponsableDao iResponsableDao;

	@Inject
	private AppMain appMain;

	private String condicion;
	private List<ResponsableEt> responsables;
	private ResponsableEt responsableSeleccionado;

	@Override
	protected void init() {
		buscar();
		inicializar();
	}

	public void inicializar() {
		try {
			responsableSeleccionado = new ResponsableEt();
			responsableSeleccionado.setPersona(new PersonaEt());
			responsableSeleccionado.getPersona().setUbicacion(new UbicacionEt());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método inicializar " + " " + e.getMessage());
		}

	}

	public void buscar() {
		try {
			responsables = iResponsableDao.getResponsableList(condicion);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}
	}

	public void guardar() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			iPersonaDao.guardarPersona(responsableSeleccionado.getPersona(), usuario);
			iResponsableDao.guardarResponsable(responsableSeleccionado, usuario);
			showInfo("Información Grabada con Éxito ", FacesMessage.SEVERITY_INFO);
			RequestContext.getCurrentInstance().execute("PF('dlg_per_003').hide();");
			buscar();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}

	}

	public void modificar(ResponsableEt responsable) {
		responsableSeleccionado = responsable;
	}

	public List<ProvinciaEt> getListaProvincia() {
		List<ProvinciaEt> provincias = new ArrayList<ProvinciaEt>();
		try {
			provincias = iProvinciaDao.getProvinciasByCondicion(null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getListaProvincia " + " " + e.getMessage());
		}
		return provincias;
	}

	public List<CantonEt> getListaCanton() {
		List<CantonEt> cantones = new ArrayList<CantonEt>();
		try {
			if (responsableSeleccionado.getPersona().getUbicacion().getProvincia() != null) {
				cantones = responsableSeleccionado.getPersona().getUbicacion().getProvincia().getCantones();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getListaCanton " + " " + e.getMessage());
		}
		return cantones;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public List<ResponsableEt> getResponsables() {
		return responsables;
	}

	public void setResponsables(List<ResponsableEt> responsables) {
		this.responsables = responsables;
	}

	public ResponsableEt getResponsableSeleccionado() {
		return responsableSeleccionado;
	}

	public void setResponsableSeleccionado(ResponsableEt responsableSeleccionado) {
		this.responsableSeleccionado = responsableSeleccionado;
	}

	@Override
	protected void onDestroy() {
		iPersonaDao.remove();
		iProvinciaDao.remove();
		iResponsableDao.remove();
	}

}
