package com.primax.bean.vs.base;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.primax.bean.ss.AppMain;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.param.NivelEvaluacionDetalleEt;
import com.primax.jpa.param.NivelEvaluacionEt;
import com.primax.jpa.param.ParametrosGeneralesEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IEvaluacionDao;
import com.primax.srv.idao.IParametrolGeneralDao;
import com.primax.srv.idao.IProvinciaDao;
import com.primax.srv.idao.IReporteEvaluacionNivelRiesgoDao;

@Named("BusquedasBn")
@ViewScoped
public class BusquedaBean2 extends BaseBean implements Serializable {

	/**
	 * Primax
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IProvinciaDao iProvinciaDao;
	@EJB
	private IEvaluacionDao iEvaluacionDao;
	@EJB
	private IParametrolGeneralDao iParametrolGeneralDao;
	@EJB
	private IReporteEvaluacionNivelRiesgoDao iReporteEvaluacionNivelRiesgoDao;

	private EvaluacionEt evaluacionSeleccionada;
	private ParametrosGeneralesEt zonaSeleccionada;
	private ParametrosGeneralesEt anioSeleccionado;
	private ParametrosGeneralesEt nivelRiesgoSeleccionado;
	private List<ParametrosGeneralesEt> mesesSeleccionados;
	private NivelEvaluacionEt nivelEvaluacionSeleccionado;
	private NivelEvaluacionDetalleEt nivelEvaluacionDetalleSeleccionado;

	@Inject
	private AppMain appMain;

	@Override
	public void init() {
		evaluacionSeleccionada = null;
		zonaSeleccionada = null;
		anioSeleccionado = null;
		nivelRiesgoSeleccionado = null;
		mesesSeleccionados = new ArrayList<>();
		generarReporte02();
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

	public void generarReporte02() {
		try {
			if (anioSeleccionado != null) {
				generar02(Integer.parseInt(anioSeleccionado.getValorLista()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método generarReporte " + " " + e.getMessage());
		}
	}

	public void generar02(int anio) {
		Long idZona = 0L;
		Long idEvaluacion = 0L;
		Long idNivelEvaluacion = 0L;
		Long idNivelEvaluacionD = 0L;
		try {
			UsuarioEt usuario = appMain.getUsuario();
			iProvinciaDao.limpiarReporte(usuario.getIdUsuario());
			if (zonaSeleccionada != null) {
				idZona = Long.parseLong(zonaSeleccionada.getValorLista());
			}
			if (evaluacionSeleccionada != null) {
				idEvaluacion = evaluacionSeleccionada.getIdEvaluacion();
			}
			if (nivelEvaluacionSeleccionado != null) {
				idNivelEvaluacion = nivelEvaluacionSeleccionado.getIdNivelEvaluacion();
			}
			if (nivelEvaluacionDetalleSeleccionado != null) {
				idNivelEvaluacionD = nivelEvaluacionDetalleSeleccionado.getIdNivelEvaluacionDetalle();
			}
			for (ParametrosGeneralesEt parametrosGenerales : mesesSeleccionados) {
				int mes = Integer.parseInt(parametrosGenerales.getValorLista());
				Date fechaDesde = getFechaDesde(mes, anio);
				Date fechaHasta = getFechaHasta(mes, anio);
				iReporteEvaluacionNivelRiesgoDao.generar(fechaDesde, fechaHasta, idZona, idEvaluacion,
						idNivelEvaluacion, idNivelEvaluacionD, usuario.getIdUsuario());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método generar " + " " + e.getMessage());
		}
	}

	public List<ParametrosGeneralesEt> getTipoAnioList() {
		List<ParametrosGeneralesEt> parametrosGenerales = new ArrayList<ParametrosGeneralesEt>();
		try {
			ParametrosGeneralesEt parametrosGeneral = iParametrolGeneralDao.getObjPadre("106");
			parametrosGenerales = iParametrolGeneralDao.getListaHIjos(parametrosGeneral);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTipoAnioList " + " " + e.getMessage());
		}
		return parametrosGenerales;
	}

	public List<ParametrosGeneralesEt> getTipoMesList() {
		List<ParametrosGeneralesEt> parametrosGenerales = new ArrayList<ParametrosGeneralesEt>();
		try {
			ParametrosGeneralesEt parametrosGeneral = iParametrolGeneralDao.getObjPadre("108");
			parametrosGenerales = iParametrolGeneralDao.getListaHIjos(parametrosGeneral);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTipoMesList " + " " + e.getMessage());
		}
		return parametrosGenerales;
	}

	public List<ParametrosGeneralesEt> getTipoZonaList() {
		List<ParametrosGeneralesEt> parametrosGenerales = new ArrayList<ParametrosGeneralesEt>();
		try {
			ParametrosGeneralesEt parametrosGeneral = iParametrolGeneralDao.getObjPadre("1");
			parametrosGenerales = iParametrolGeneralDao.getListaHIjos(parametrosGeneral);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTipoZonaList " + " " + e.getMessage());
		}
		return parametrosGenerales;
	}

	public List<ParametrosGeneralesEt> getNivelRiesgoList() {
		List<ParametrosGeneralesEt> parametrosGenerales = new ArrayList<ParametrosGeneralesEt>();
		try {
			ParametrosGeneralesEt parametrosGeneral = iParametrolGeneralDao.getObjPadre("121");
			parametrosGenerales = iParametrolGeneralDao.getListaHIjos(parametrosGeneral);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getNivelRiesgoList " + " " + e.getMessage());
		}
		return parametrosGenerales;
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

	public Date getFechaDesde(int mes, int anio) {
		Calendar calDesd = Calendar.getInstance();
		calDesd.setTime(new Date());
		calDesd.set(Calendar.MONTH, mes);
		calDesd.set(Calendar.YEAR, anio);
		calDesd.set(Calendar.DAY_OF_MONTH, 1);
		System.out.println("Fecha Desde" + " " + calDesd.getTime());
		return calDesd.getTime();

	}

	public Date getFechaHasta(int mes, int anio) {
		Calendar calHast = Calendar.getInstance();
		calHast.setTime(new Date());
		calHast.set(Calendar.MONTH, mes);
		calHast.set(Calendar.YEAR, anio);
		int ultimoDiaMes = calHast.getActualMaximum(Calendar.DAY_OF_MONTH);
		calHast.set(anio, mes, ultimoDiaMes);
		System.out.println("Fecha Hasta" + " " + calHast.getTime());
		return calHast.getTime();
	}

	public ParametrosGeneralesEt getZonaSeleccionada() {
		return zonaSeleccionada;
	}

	public void setZonaSeleccionada(ParametrosGeneralesEt zonaSeleccionada) {
		this.zonaSeleccionada = zonaSeleccionada;
	}

	public ParametrosGeneralesEt getAnioSeleccionado() {
		return anioSeleccionado;
	}

	public void setAnioSeleccionado(ParametrosGeneralesEt anioSeleccionado) {
		this.anioSeleccionado = anioSeleccionado;
	}

	public List<ParametrosGeneralesEt> getMesesSeleccionados() {
		return mesesSeleccionados;
	}

	public void setMesesSeleccionados(List<ParametrosGeneralesEt> mesesSeleccionados) {
		this.mesesSeleccionados = mesesSeleccionados;
	}

	public ParametrosGeneralesEt getNivelRiesgoSeleccionado() {
		return nivelRiesgoSeleccionado;
	}

	public void setNivelRiesgoSeleccionado(ParametrosGeneralesEt nivelRiesgoSeleccionado) {
		this.nivelRiesgoSeleccionado = nivelRiesgoSeleccionado;
	}

	public EvaluacionEt getEvaluacionSeleccionada() {
		return evaluacionSeleccionada;
	}

	public void setEvaluacionSeleccionada(EvaluacionEt evaluacionSeleccionada) {
		this.evaluacionSeleccionada = evaluacionSeleccionada;
	}

	public NivelEvaluacionEt getNivelEvaluacionSeleccionado() {
		return nivelEvaluacionSeleccionado;
	}

	public void setNivelEvaluacionSeleccionado(NivelEvaluacionEt nivelEvaluacionSeleccionado) {
		this.nivelEvaluacionSeleccionado = nivelEvaluacionSeleccionado;
	}

	public NivelEvaluacionDetalleEt getNivelEvaluacionDetalleSeleccionado() {
		return nivelEvaluacionDetalleSeleccionado;
	}

	public void setNivelEvaluacionDetalleSeleccionado(NivelEvaluacionDetalleEt nivelEvaluacionDetalleSeleccionado) {
		this.nivelEvaluacionDetalleSeleccionado = nivelEvaluacionDetalleSeleccionado;
	}

	@Override
	public void onDestroy() {
		iProvinciaDao.remove();
		iEvaluacionDao.remove();
		iParametrolGeneralDao.remove();
		iReporteEvaluacionNivelRiesgoDao.remove();
	}

}
