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
import com.primax.jpa.param.TipoInventarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.ITipoInventarioDao;

@Named("TipoInventarioBn")
@ViewScoped
public class TipoInventarioBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private ITipoInventarioDao iTipoInventarioDao;

	@Inject
	private AppMain appMain;

	private String condicion;
	private List<TipoInventarioEt> tipoInventarios;
	private TipoInventarioEt tipoInventarioSeleccionado;

	@Override
	protected void init() {
		inicializarObj();
		buscar();
	}

	public void inicializarObj() {
		tipoInventarioSeleccionado = new TipoInventarioEt();
	}

	public void buscar() {
		try {
			tipoInventarios = iTipoInventarioDao.getTipoInventarioList(condicion);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}
	}

	public void guardar() {
		String mensaje = "";
		try {
			mensaje = validacionGuardar(tipoInventarioSeleccionado);
			if (!mensaje.equals("")) {
				showInfo(mensaje, FacesMessage.SEVERITY_ERROR, null, null);
				return;
			}
			UsuarioEt usuario = appMain.getUsuario();
			iTipoInventarioDao.guardarTipoInventario(tipoInventarioSeleccionado, usuario);
			showInfo("Información Grabada con Exito ", FacesMessage.SEVERITY_INFO);
			RequestContext.getCurrentInstance().execute("PF('dialog_22_1').hide();");
			buscar();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}
	}

	public String validacionGuardar(TipoInventarioEt tipoInventario) {
		String mensaje = "";
		try {
			if (tipoInventario.getDescripcion() == null) {
				mensaje = "Por favor ingresar descripción";
				return mensaje;
			}
			if (tipoInventario.getCodigo() == null) {
				mensaje = "Por favor ingresar código";
				return mensaje;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método validacionGuardar " + " " + e.getMessage());
		}
		return mensaje;
	}

	public void nuevo() {
		tipoInventarioSeleccionado = new TipoInventarioEt();
	}

	public void modificar(TipoInventarioEt tipoInventario) {
		tipoInventarioSeleccionado = tipoInventario;
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

	public List<TipoInventarioEt> getTipoInventarios() {
		return tipoInventarios;
	}

	public void setTipoInventarios(List<TipoInventarioEt> tipoInventarios) {
		this.tipoInventarios = tipoInventarios;
	}

	public TipoInventarioEt getTipoInventarioSeleccionado() {
		return tipoInventarioSeleccionado;
	}

	public void setTipoInventarioSeleccionado(TipoInventarioEt tipoInventarioSeleccionado) {
		this.tipoInventarioSeleccionado = tipoInventarioSeleccionado;
	}

	@Override
	protected void onDestroy() {
		iTipoInventarioDao.remove();
	}

}
