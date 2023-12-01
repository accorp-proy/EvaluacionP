package com.primax.bean.vs.base;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.primax.bean.ss.AppMain;
import com.primax.jpa.enums.EstadoCheckListEnum;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.pla.CheckListEt;
import com.primax.srv.idao.ICheckListDao;
import com.primax.srv.idao.IEvaluacionDao;
import com.primax.srv.idao.ITipoChecKListDao;

@Named("BusquedaCheckBn")
@ViewScoped
public class BusquedaCheckBean extends BaseBean implements Serializable {

	/**
	 * Primax EC
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private ICheckListDao iCheckListDao;
	@EJB
	private IEvaluacionDao iEvaluacionDao;
	@EJB
	private ITipoChecKListDao iTipoChecKListDao;

	private List<CheckListEt> checkLists;
	private EvaluacionEt evaluacionSeleccionada;
	private List<CheckListEt> checkListSeleccionados;
	private TipoChecKListEt tipoChecKListEtSeleccionado;

	private int target;

	@Inject
	private AppMain appMain;

	@Override
	public void init() {
		target = 0;
		buscar();
	}

	public void buscar() {
		try {
			checkLists = iCheckListDao.getCheckListBusqueda(evaluacionSeleccionada, tipoChecKListEtSeleccionado,
					EstadoCheckListEnum.APROBADO);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
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

	public List<TipoChecKListEt> getTiposChecKList() {
		List<TipoChecKListEt> tipoChecList = new ArrayList<TipoChecKListEt>();
		try {
			if (evaluacionSeleccionada != null) {
				tipoChecList = iTipoChecKListDao.getTipoCheckListByEvaluacion(evaluacionSeleccionada);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTiposChecKList " + " " + e.getMessage());
		}
		return tipoChecList;
	}

	public String dateToFormatedDate(String formato, Date fecha) {
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		return sdf.format(fecha);
	}

	public AppMain getAppMain() {
		return appMain;
	}

	public void setAppMain(AppMain appMain) {
		this.appMain = appMain;
	}

	public EvaluacionEt getEvaluacionSeleccionada() {
		return evaluacionSeleccionada;
	}

	public void setEvaluacionSeleccionada(EvaluacionEt evaluacionSeleccionada) {
		this.evaluacionSeleccionada = evaluacionSeleccionada;
	}

	public TipoChecKListEt getTipoChecKListEtSeleccionado() {
		return tipoChecKListEtSeleccionado;
	}

	public void setTipoChecKListEtSeleccionado(TipoChecKListEt tipoChecKListEtSeleccionado) {
		this.tipoChecKListEtSeleccionado = tipoChecKListEtSeleccionado;
	}

	public List<CheckListEt> getCheckLists() {
		return checkLists;
	}

	public void setCheckLists(List<CheckListEt> checkLists) {
		this.checkLists = checkLists;
	}

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	public List<CheckListEt> getCheckListSeleccionados() {
		return checkListSeleccionados;
	}

	public void setCheckListSeleccionados(List<CheckListEt> checkListSeleccionados) {
		this.checkListSeleccionados = checkListSeleccionados;
	}

	@Override
	public void onDestroy() {
		iCheckListDao.remove();
		iEvaluacionDao.remove();
		iTipoChecKListDao.remove();
	}

}
