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
import com.primax.jpa.param.TipoCargoEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.ITipoCargoDao;

@Named("TipoCargoBn")
@ViewScoped
public class TipoCargoBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private ITipoCargoDao iTipoCargoDao;

	@Inject
	private AppMain appMain;

	private String condicion;
	private List<TipoCargoEt> tipoCargos;
	private TipoCargoEt tipoCargoSeleccionado;

	@Override
	protected void init() {
		inicializarObj();
		buscar();
	}

	public void inicializarObj() {
		tipoCargoSeleccionado = new TipoCargoEt();
	}

	public void buscar() {
		try {
			tipoCargos = iTipoCargoDao.getTipoCargoList(condicion);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}
	}

	public void guardar() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			iTipoCargoDao.guardarTipoCargo(tipoCargoSeleccionado, usuario);
			showInfo("Información Grabada con Éxito ", FacesMessage.SEVERITY_INFO);
			RequestContext.getCurrentInstance().execute("PF('dialog_09_1').hide();");
			buscar();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}
	}

	public void nuevo() {
		tipoCargoSeleccionado = new TipoCargoEt();
	}

	public void modificar(TipoCargoEt tipoCargo) {
		tipoCargoSeleccionado = tipoCargo;
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

	public List<TipoCargoEt> getTipoCargos() {
		return tipoCargos;
	}

	public void setTipoCargos(List<TipoCargoEt> tipoCargos) {
		this.tipoCargos = tipoCargos;
	}

	public TipoCargoEt getTipoCargoSeleccionado() {
		return tipoCargoSeleccionado;
	}

	public void setTipoCargoSeleccionado(TipoCargoEt tipoCargoSeleccionado) {
		this.tipoCargoSeleccionado = tipoCargoSeleccionado;
	}

	@Override
	protected void onDestroy() {
		iTipoCargoDao.remove();
	}

}
