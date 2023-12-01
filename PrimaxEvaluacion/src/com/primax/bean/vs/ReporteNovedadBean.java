package com.primax.bean.vs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.param.ProcesoDetalleEt;
import com.primax.jpa.param.ProcesoEt;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IEvaluacionDao;
import com.primax.srv.idao.IProcesoDao;
import com.primax.srv.idao.IProcesoDetalleDao;
import com.primax.srv.idao.ITipoChecKListDao;

@Named("ReporteNovedadBn")
@ViewScoped
public class ReporteNovedadBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IProcesoDao iProcesoDao;
	@EJB
	private IEvaluacionDao iEvaluacionDao;
	@EJB
	private ITipoChecKListDao iTipoChecKListDao;
	@EJB
	private IProcesoDetalleDao iProcesoDetalleDao;

	private ProcesoEt procesoSeleccionado;
	private ProcesoDetalleEt kpiSeleccionado;
	private EvaluacionEt evaluacionSeleccionado;
	private TipoChecKListEt tipoChecKListSeleccionado;

	@Inject
	private AppMain appMain;

	@Override
	protected void init() {
		tipoChecKListSeleccionado = null;
	}

	public void guardarKPI() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			iProcesoDetalleDao.guardaProcesoDetalle(kpiSeleccionado, usuario);
			RequestContext.getCurrentInstance().execute("PF('dialog_15_1').hide();");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarKPI " + " " + e.getMessage());
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
		List<TipoChecKListEt> tipoChecKList = new ArrayList<TipoChecKListEt>();
		try {
			if (evaluacionSeleccionado != null) {
				tipoChecKList = iTipoChecKListDao.getTipoCheckListByEvaluacion(evaluacionSeleccionado);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTiposChecKList " + " " + e.getMessage());
		}
		return tipoChecKList;
	}

	public List<ProcesoEt> getProcesoList() {
		List<ProcesoEt> procesos = new ArrayList<ProcesoEt>();
		try {
			if (tipoChecKListSeleccionado != null) {
				procesos = iProcesoDao.getProcesoByTipoList(tipoChecKListSeleccionado);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getProcesoList " + " " + e.getMessage());
		}
		return procesos;
	}

	public void kpiSeleccionadoModificar(ProcesoDetalleEt kpi) {
		kpiSeleccionado = kpi;
	}

	public TipoChecKListEt getTipoChecKListSeleccionado() {
		return tipoChecKListSeleccionado;
	}

	public void setTipoChecKListSeleccionado(TipoChecKListEt tipoChecKListSeleccionado) {
		this.tipoChecKListSeleccionado = tipoChecKListSeleccionado;
	}

	public ProcesoEt getProcesoSeleccionado() {
		return procesoSeleccionado;
	}

	public void setProcesoSeleccionado(ProcesoEt procesoSeleccionado) {
		this.procesoSeleccionado = procesoSeleccionado;
	}

	public ProcesoDetalleEt getKpiSeleccionado() {
		return kpiSeleccionado;
	}

	public void setKpiSeleccionado(ProcesoDetalleEt kpiSeleccionado) {
		this.kpiSeleccionado = kpiSeleccionado;
	}

	public EvaluacionEt getEvaluacionSeleccionado() {
		return evaluacionSeleccionado;
	}

	public void setEvaluacionSeleccionado(EvaluacionEt evaluacionSeleccionado) {
		this.evaluacionSeleccionado = evaluacionSeleccionado;
	}

	@Override
	protected void onDestroy() {
		iProcesoDao.remove();
		iEvaluacionDao.remove();
		iTipoChecKListDao.remove();
		iProcesoDetalleDao.remove();

	}

}
