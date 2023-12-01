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
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.param.ParametrosGeneralesEt;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.param.ZonaEt;
import com.primax.jpa.param.ZonaUsuarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IAgenciaDao;
import com.primax.srv.idao.IEvaluacionDao;
import com.primax.srv.idao.INivelEvaluacionDao;
import com.primax.srv.idao.IParametrolGeneralDao;
import com.primax.srv.idao.ITipoChecKListDao;
import com.primax.srv.idao.IUsuarioDao;
import com.primax.srv.idao.IZonaDao;

@Named("rptPerfilRiesgoBn")
@ViewScoped
public class rptPerfilRiesgoBean extends BaseBean implements Serializable {

	/**
	 * Primax
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IZonaDao iZonaDao;
	@EJB
	private IAgenciaDao iAgenciaDao;
	@EJB
	private IUsuarioDao iUsuarioDao;
	@EJB
	private IEvaluacionDao iEvaluacionDao;
	@EJB
	private ITipoChecKListDao iTipoChecKListDao;
	@EJB
	private INivelEvaluacionDao iNivelEvaluacionDao;
	@EJB
	private IParametrolGeneralDao iParametrolGeneralDao;

	private ZonaEt zonaSeleccionada;
	private AgenciaEt estacionSeleccionada;
	private EvaluacionEt evaluacionSeleccionada;
	private ParametrosGeneralesEt anioSeleccionado;
	private TipoChecKListEt tipoChecKListSeleccionado;
	private List<ParametrosGeneralesEt> mesesSeleccionados;

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Inject
	private AppMain appMain;

	@Override
	public void init() {
		// generarReporte02();
		limpiarReporte();
	}

	public void limpiarReporte() {
		try {
			iZonaDao.limpiarReporte(appMain.getUsuario().getIdUsuario());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método limpiarReporte " + " " + e.getMessage());
		}
	}

	public void generarReporte01() {
		try {
			if (anioSeleccionado != null) {
				generar01(Integer.parseInt(anioSeleccionado.getValorLista()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método generarReporte " + " " + e.getMessage());
		}
	}

	public void generar01(int anio) {
		Long idZona = 0L;
		Long idEvaluacion = 0L;
		Long idNivelEvaluacion = 0L;
		Long idNivelEvaluacionD = 0L;
		try {
			UsuarioEt usuario = appMain.getUsuario();
			// iProvinciaDao.limpiarReporte(usuario.getIdUsuario());
			if (zonaSeleccionada != null) {
				idZona = zonaSeleccionada.getIdZona();
			}
			if (evaluacionSeleccionada != null) {
				idEvaluacion = evaluacionSeleccionada.getIdEvaluacion();
			}

			for (ParametrosGeneralesEt parametrosGenerales : mesesSeleccionados) {
				int mes = Integer.parseInt(parametrosGenerales.getValorLista());
				Date fechaDesde = getFechaDesde(mes, anio);
				Date fechaHasta = getFechaHasta(mes, anio);
				// iReporteEvaluacionPuntajeDao.generar(fechaDesde, fechaHasta,
				// idZona, idEvaluacion, idNivelEvaluacion,
				// idNivelEvaluacionD, usuario.getIdUsuario());
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

	public List<ParametrosGeneralesEt> getTipoAnioVariacionList() {
		List<ParametrosGeneralesEt> parametrosGenerales = new ArrayList<ParametrosGeneralesEt>();
		try {
			if (anioSeleccionado != null) {
				ParametrosGeneralesEt parametrosGeneral = iParametrolGeneralDao.getObjPadre("126");
				parametrosGenerales = iParametrolGeneralDao.getListAnioVariacion(parametrosGeneral,
						Long.parseLong(anioSeleccionado.getValorLista()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTipoAnioVariacionList " + " " + e.getMessage());
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

	public List<AgenciaEt> getEstacionList() {
		List<AgenciaEt> agencias = new ArrayList<AgenciaEt>();
		try {
			agencias = iAgenciaDao.getAgencias(EstadoEnum.ACT);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getAgenciaList " + " " + e.getMessage());
		}
		return agencias;
	}

	public List<ZonaEt> getTipoZonaList() {
		List<ZonaEt> zonas = new ArrayList<>();
		try {
			UsuarioEt usuario = iUsuarioDao.getUsuarioId(appMain.getUsuario().getIdUsuario());
			if (usuario.isAccesoZona() && !usuario.getZonaUsuario().isEmpty()) {
				for (ZonaUsuarioEt zonaUsuario : usuario.getZonaUsuario()) {
					zonas.add(zonaUsuario.getZona());
				}
			} else {
				zonas = iZonaDao.getZonaList(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTipoZonaList " + " " + e.getMessage());
		}
		return zonas;
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

	public Date getFechaDesde(int mes, int anio) {
		Calendar calDesd = Calendar.getInstance();
		calDesd.setTime(new Date());
		calDesd.set(Calendar.MONTH, mes);
		calDesd.set(Calendar.YEAR, anio);
		calDesd.set(Calendar.DAY_OF_MONTH, 1);
		// System.out.println("Fecha Desde" + " " + calDesd.getTime());
		return calDesd.getTime();

	}

	public Date getFechaHasta(int mes, int anio) {
		Calendar calHast = Calendar.getInstance();
		calHast.setTime(new Date());
		calHast.set(Calendar.MONTH, mes);
		calHast.set(Calendar.YEAR, anio);
		int ultimoDiaMes = calHast.getActualMaximum(Calendar.DAY_OF_MONTH);
		calHast.set(anio, mes, ultimoDiaMes);
		// System.out.println("Fecha Hasta" + " " + calHast.getTime());
		return calHast.getTime();
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

	public ZonaEt getZonaSeleccionada() {
		return zonaSeleccionada;
	}

	public void setZonaSeleccionada(ZonaEt zonaSeleccionada) {
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

	public EvaluacionEt getEvaluacionSeleccionada() {
		return evaluacionSeleccionada;
	}

	public void setEvaluacionSeleccionada(EvaluacionEt evaluacionSeleccionada) {
		this.evaluacionSeleccionada = evaluacionSeleccionada;
	}

	public TipoChecKListEt getTipoChecKListSeleccionado() {
		return tipoChecKListSeleccionado;
	}

	public void setTipoChecKListSeleccionado(TipoChecKListEt tipoChecKListSeleccionado) {
		this.tipoChecKListSeleccionado = tipoChecKListSeleccionado;
	}

	public AgenciaEt getEstacionSeleccionada() {
		return estacionSeleccionada;
	}

	public void setEstacionSeleccionada(AgenciaEt estacionSeleccionada) {
		this.estacionSeleccionada = estacionSeleccionada;
	}

	@Override
	public void onDestroy() {
		iZonaDao.remove();
		iUsuarioDao.remove();
		iAgenciaDao.remove();
		iEvaluacionDao.remove();
		iTipoChecKListDao.remove();
		iNivelEvaluacionDao.remove();
		iParametrolGeneralDao.remove();

	}

}
