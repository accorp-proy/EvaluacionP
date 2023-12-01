package com.primax.bean.vs;

import java.io.Serializable;
import java.util.ArrayList;
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
import com.primax.enm.gen.ParametrosGeneralesEnum;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.ParametrosGeneralesEt;
import com.primax.jpa.pla.CheckListEtiquetaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.ICheckListEtiquetaDao;
import com.primax.srv.idao.IParametrolGeneralDao;

@Named("CheckListEtiquetaBn")
@ViewScoped
public class CheckListEtiquetaBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private ICheckListEtiquetaDao iCheckListEtiquetaDao;
	@EJB
	private IParametrolGeneralDao iParametrolGeneralDao;

	@Inject
	private AppMain appMain;

	private String condicion;

	private List<CheckListEtiquetaEt> checkListEtiquetas;
	private CheckListEtiquetaEt checkListEtiquetaSeleccionado;

	@Override
	protected void init() {
		inicializarObj();
		buscar();
	}

	public void inicializarObj() {
		checkListEtiquetaSeleccionado = new CheckListEtiquetaEt();
	}

	public void buscar() {
		try {
			checkListEtiquetas = iCheckListEtiquetaDao.getCheckListEtiquetaList();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}
	}

	public void guardar() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			iCheckListEtiquetaDao.guardarCheckListEtiqueta(checkListEtiquetaSeleccionado, usuario);
			showInfo("Información Grabada con Exito ", FacesMessage.SEVERITY_INFO);
			RequestContext.getCurrentInstance().execute("PF('dialog_23_1').hide();");
			buscar();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}
	}

	public void nuevo() {
		checkListEtiquetaSeleccionado = new CheckListEtiquetaEt();
	}

	public void modificar(CheckListEtiquetaEt checkListEtiqueta) {
		checkListEtiquetaSeleccionado = checkListEtiqueta;
	}

	public SelectItem[] getEstadosActIna() {
		SelectItem[] items = new SelectItem[2];
		items[0] = new SelectItem(EstadoEnum.ACT, EstadoEnum.ACT.getDescripcion());
		items[1] = new SelectItem(EstadoEnum.INA, EstadoEnum.INA.getDescripcion());
		return items;
	}
	
	public List<ParametrosGeneralesEt> getParSeccionList() {
		ParametrosGeneralesEt objPadre;
		List<ParametrosGeneralesEt> parEmpresas = new ArrayList<ParametrosGeneralesEt>();
		try {
			objPadre = iParametrolGeneralDao.getObjPadre(ParametrosGeneralesEnum.ETIQUETA.getDescripcion());
			parEmpresas = iParametrolGeneralDao.getListaHIjos(objPadre);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getParSeccionList " + " " + e.getMessage());
		}
		return parEmpresas;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public List<CheckListEtiquetaEt> getCheckListEtiquetas() {
		return checkListEtiquetas;
	}

	public void setCheckListEtiquetas(List<CheckListEtiquetaEt> checkListEtiquetas) {
		this.checkListEtiquetas = checkListEtiquetas;
	}

	public CheckListEtiquetaEt getCheckListEtiquetaSeleccionado() {
		return checkListEtiquetaSeleccionado;
	}

	public void setCheckListEtiquetaSeleccionado(CheckListEtiquetaEt checkListEtiquetaSeleccionado) {
		this.checkListEtiquetaSeleccionado = checkListEtiquetaSeleccionado;
	}

	@Override
	protected void onDestroy() {
		iCheckListEtiquetaDao.remove();
		iParametrolGeneralDao.remove();
	}

}
