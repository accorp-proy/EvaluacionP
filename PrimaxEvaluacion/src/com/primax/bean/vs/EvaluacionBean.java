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
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IEvaluacionDao;

@Named("EvaluacionBn")
@ViewScoped
public class EvaluacionBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IEvaluacionDao iEvaluacionDao;

	@Inject
	private AppMain appMain;

	private String condicion;
	private List<String> criterios;
	private String criterioSeleccionado;
	private List<EvaluacionEt> evaluaciones;
	private EvaluacionEt evaluacionSeleccionado;

	@Override
	protected void init() {
		inicializarObj();
		buscar();
	}

	public void inicializarObj() {
		evaluacionSeleccionado = new EvaluacionEt();
		criterios = new ArrayList<String>();
		criterios.add("General");
		criterios.add("Específico");
	}

	public void buscar() {
		try {
			evaluaciones = iEvaluacionDao.getEvaluacionList(condicion);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}
	}

	public void guardar() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			if (criterioSeleccionado.equals("General")) {
				evaluacionSeleccionado.setCriterio(true);
			} else {
				evaluacionSeleccionado.setCriterio(false);
			}
			iEvaluacionDao.guardarEvaluacion(evaluacionSeleccionado, usuario);
			showInfo("Información Grabada con Éxito ", FacesMessage.SEVERITY_INFO);
			RequestContext.getCurrentInstance().execute("PF('dialog_14_1').hide();");
			buscar();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}
	}

	public void nuevo() {
		evaluacionSeleccionado = new EvaluacionEt();
		evaluacionSeleccionado.setCriterio(true);
	}

	public void modificar(EvaluacionEt evaluacion) {
		if (evaluacion.isCriterio()) {
			criterioSeleccionado = "General";
		} else {
			criterioSeleccionado = "Específico";
		}
		evaluacionSeleccionado = evaluacion;
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

	public EvaluacionEt getEvaluacionSeleccionado() {
		return evaluacionSeleccionado;
	}

	public void setEvaluacionSeleccionado(EvaluacionEt evaluacionSeleccionado) {
		this.evaluacionSeleccionado = evaluacionSeleccionado;
	}

	public List<EvaluacionEt> getEvaluaciones() {
		return evaluaciones;
	}

	public void setEvaluaciones(List<EvaluacionEt> evaluaciones) {
		this.evaluaciones = evaluaciones;
	}

	public List<String> getCriterios() {
		return criterios;
	}

	public void setCriterios(List<String> criterios) {
		this.criterios = criterios;
	}

	public String getCriterioSeleccionado() {
		return criterioSeleccionado;
	}

	public void setCriterioSeleccionado(String criterioSeleccionado) {
		this.criterioSeleccionado = criterioSeleccionado;
	}

	@Override
	protected void onDestroy() {
		iEvaluacionDao.remove();
	}

}
