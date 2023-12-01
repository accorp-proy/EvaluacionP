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
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.ITipoChecKListDao;
import com.primax.srv.idao.IEvaluacionDao;

@Named("TipoCheckListBn")
@ViewScoped
public class TipoCheckListBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IEvaluacionDao iEvaluacionDao;
	@EJB
	private ITipoChecKListDao iTipoChecListDao;

	@Inject
	private AppMain appMain;

	private boolean general;
	private String condicion;
	private List<TipoChecKListEt> tiposChecList;
	private EvaluacionEt evaluacionSeleccionado;
	private TipoChecKListEt tipoChecListSeleccionado;

	@Override
	protected void init() {
		inicializarObj();
		buscar();
	}

	public void inicializarObj() {
		tipoChecListSeleccionado = new TipoChecKListEt();
	}

	public void buscar() {
		try {
			tiposChecList = iTipoChecListDao.getTipoChecList(condicion);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}
	}

	public void guardar() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			tipoChecListSeleccionado.setEvaluacion(evaluacionSeleccionado);
			iTipoChecListDao.guardaTipoChecList(tipoChecListSeleccionado, usuario);
			showInfo("Información Grabada con Éxito ", FacesMessage.SEVERITY_INFO);
			RequestContext.getCurrentInstance().execute("PF('dialog_11_1').hide();");
			buscar();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}
	}

	public void nuevo() {
		tipoChecListSeleccionado = new TipoChecKListEt();
	}

	public void modificar(TipoChecKListEt tipoChecList) {
		tipoChecListSeleccionado = tipoChecList;
		evaluacionSeleccionado = tipoChecList.getEvaluacion();
		if (tipoChecList.getEvaluacion().isCriterio()) {
			general = true;
		}else {
			general = false;
		}
	}

	public List<EvaluacionEt> getEvaluacionList() {
		List<EvaluacionEt> evaluaciones = new ArrayList<EvaluacionEt>();
		try {
			evaluaciones = iEvaluacionDao.getEvaluacionList(null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getEvaluacionList " + " " + e.getMessage());
		}
		return evaluaciones;
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

	public List<TipoChecKListEt> getTiposChecList() {
		return tiposChecList;
	}

	public void setTiposChecList(List<TipoChecKListEt> tiposChecList) {
		this.tiposChecList = tiposChecList;
	}

	public TipoChecKListEt getTipoChecListSeleccionado() {
		return tipoChecListSeleccionado;
	}

	public void setTipoChecListSeleccionado(TipoChecKListEt tipoChecListSeleccionado) {
		this.tipoChecListSeleccionado = tipoChecListSeleccionado;
	}

	public EvaluacionEt getEvaluacionSeleccionado() {
		return evaluacionSeleccionado;
	}

	public void setEvaluacionSeleccionado(EvaluacionEt evaluacionSeleccionado) {
		this.evaluacionSeleccionado = evaluacionSeleccionado;
	}

	public boolean isGeneral() {
		return general;
	}

	public void setGeneral(boolean general) {
		this.general = general;
	}

	@Override
	protected void onDestroy() {
		iEvaluacionDao.remove();
		iTipoChecListDao.remove();
	}

}
