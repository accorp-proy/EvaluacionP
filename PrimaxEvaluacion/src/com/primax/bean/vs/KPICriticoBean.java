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
import com.primax.jpa.param.KPICriticoEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IKPICriticoDao;

@Named("KPICriticoBn")
@ViewScoped
public class KPICriticoBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IKPICriticoDao iKPICriticoDao;

	@Inject
	private AppMain appMain;

	private String condicion;
	private List<KPICriticoEt> kPICriticos;
	private KPICriticoEt kKPICriticoSeleccionado;

	@Override
	protected void init() {
		inicializarObj();
		buscar();
	}

	public void inicializarObj() {
		kKPICriticoSeleccionado = new KPICriticoEt();
	}

	public void buscar() {
		try {
			kPICriticos = iKPICriticoDao.getKPICriticoList(condicion);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}
	}

	public void guardar() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			iKPICriticoDao.guardarKPICritico(kKPICriticoSeleccionado, usuario);
			showInfo("Información Grabada con Éxito ", FacesMessage.SEVERITY_INFO);
			RequestContext.getCurrentInstance().execute("PF('dialog_12_1').hide();");
			buscar();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}
	}

	public void nuevo() {
		kKPICriticoSeleccionado = new KPICriticoEt();
	}

	public void modificar(KPICriticoEt kPICritico) {
		kKPICriticoSeleccionado = kPICritico;
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

	public List<KPICriticoEt> getkPICriticos() {
		return kPICriticos;
	}

	public void setkPICriticos(List<KPICriticoEt> kPICriticos) {
		this.kPICriticos = kPICriticos;
	}

	public KPICriticoEt getkKPICriticoSeleccionado() {
		return kKPICriticoSeleccionado;
	}

	public void setkKPICriticoSeleccionado(KPICriticoEt kKPICriticoSeleccionado) {
		this.kKPICriticoSeleccionado = kKPICriticoSeleccionado;
	}

	@Override
	protected void onDestroy() {
		iKPICriticoDao.remove();
	}

}
