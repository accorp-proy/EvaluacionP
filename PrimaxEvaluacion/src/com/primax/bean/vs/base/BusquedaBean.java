package com.primax.bean.vs.base;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.primax.bean.ss.AppMain;
import com.primax.jpa.enums.EstadoCheckListEnum;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.param.FrecuenciaVisitaEt;
import com.primax.jpa.param.NivelEvaluacionDetalleEt;
import com.primax.jpa.param.NivelEvaluacionEt;
import com.primax.jpa.param.ParametrosGeneralesEt;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.param.TipoInventarioEt;
import com.primax.jpa.param.ZonaEt;
import com.primax.jpa.param.ZonaUsuarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IAgenciaDao;
import com.primax.srv.idao.ICantonDao;
import com.primax.srv.idao.IEvaluacionDao;
import com.primax.srv.idao.IFrecuenciaVisitaDao;
import com.primax.srv.idao.IKPICriticoDao;
import com.primax.srv.idao.INivelEvaluacionDao;
import com.primax.srv.idao.IParametrolGeneralDao;
import com.primax.srv.idao.IProcesoDao;
import com.primax.srv.idao.IProvinciaDao;
import com.primax.srv.idao.IReporteEvaluacionConsolidadoDao;
import com.primax.srv.idao.IReporteEvaluacionNivelRiesgoDao;
import com.primax.srv.idao.IReporteEvaluacionPlanificacionDao;
import com.primax.srv.idao.IReporteEvaluacionPuntajeDao;
import com.primax.srv.idao.IReporteEvaluacionVariacionDao;
import com.primax.srv.idao.IReportePlanificacionInventarioDao;
import com.primax.srv.idao.IReporteTipoEvaluacionDao;
import com.primax.srv.idao.ITipoCargoDao;
import com.primax.srv.idao.ITipoChecKListDao;
import com.primax.srv.idao.ITipoInventarioDao;
import com.primax.srv.idao.IUsuarioDao;
import com.primax.srv.idao.IZonaDao;

@Named("BusquedaBn")
@ViewScoped
public class BusquedaBean extends BaseBean implements Serializable {

	/**
	 * Primax
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IZonaDao iZonaDao;
	@EJB
	private ICantonDao iCantonDao;
	@EJB
	private IAgenciaDao iAgenciaDao;
	@EJB
	private IProcesoDao iProcesoDao;
	@EJB
	private IUsuarioDao iUsuarioDao;
	@EJB
	private IProvinciaDao iProvinciaDao;
	@EJB
	private ITipoCargoDao iTipoCargoDao;
	@EJB
	private IKPICriticoDao iKPICriticoDao;
	@EJB
	private IEvaluacionDao iEvaluacionDao;
	@EJB
	private ITipoChecKListDao iTipoChecKListDao;
	@EJB
	private ITipoInventarioDao iTipoInventarioDao;
	@EJB
	private INivelEvaluacionDao iNivelEvaluacionDao;
	@EJB
	private IFrecuenciaVisitaDao iFrecuenciaVisitaDao;
	@EJB
	private IParametrolGeneralDao iParametrolGeneralDao;
	@EJB
	private IReporteTipoEvaluacionDao iReporteTipoEvaluacionDao;
	@EJB
	private IReporteEvaluacionPuntajeDao iReporteEvaluacionPuntajeDao;
	@EJB
	private IReporteEvaluacionVariacionDao iReporteEvaluacionVariacionDao;
	@EJB
	private IReporteEvaluacionNivelRiesgoDao iReporteEvaluacionNivelRiesgoDao;
	@EJB
	private IReporteEvaluacionConsolidadoDao iReporteEvaluacionConsolidadoDao;
	@EJB
	private IReporteEvaluacionPlanificacionDao iReporteEvaluacionPlanificacionDao;
	@EJB
	private IReportePlanificacionInventarioDao iReportePlanificacionInventarioDao;

	private ZonaEt zonaSeleccionada;
	private AgenciaEt estacionSeleccionada;
	private EvaluacionEt evaluacionSeleccionada;
	private ParametrosGeneralesEt anioSeleccionado;
	private TipoChecKListEt tipoChecKListSeleccionado;
	private TipoInventarioEt tipoInventarioSeleccionado;
	private NivelEvaluacionEt nivelEvaluacionSeleccionado;
	private List<ParametrosGeneralesEt> mesesSeleccionados;
	private EstadoCheckListEnum estadoCheckListSeleccionado;
	private FrecuenciaVisitaEt frecuenciaVisitaSeleccionado;
	private NivelEvaluacionDetalleEt nivelEvaluacionDetalleSeleccionado;

	private List<ParametrosGeneralesEt> anioVariacionSeleccionados;

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Inject
	private AppMain appMain;

	@Override
	public void init() {
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

	public void generarReporte03() {
		try {
			if (anioSeleccionado != null) {
				generar03(Integer.parseInt(anioSeleccionado.getValorLista()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método generarReporte " + " " + e.getMessage());
		}
	}

	public void generarReporte04() {
		try {
			if (anioSeleccionado != null) {
				generar04(Integer.parseInt(anioSeleccionado.getValorLista()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método generarReporte " + " " + e.getMessage());
		}
	}

	public void generarReporte05() {
		try {
			if (anioSeleccionado != null) {
				generar05(Integer.parseInt(anioSeleccionado.getValorLista()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método generarReporte " + " " + e.getMessage());
		}
	}

	public void generarReporte06() {
		try {
			if (anioSeleccionado != null) {
				generar06(Integer.parseInt(anioSeleccionado.getValorLista()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método generarReporte " + " " + e.getMessage());
		}
	}

	public void generarReporte07() {
		try {
			if (anioSeleccionado != null) {
				generar07(Integer.parseInt(anioSeleccionado.getValorLista()));
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
			iProvinciaDao.limpiarReporte(usuario.getIdUsuario());
			if (zonaSeleccionada != null) {
				idZona = zonaSeleccionada.getIdZona();
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
				iReporteEvaluacionPuntajeDao.generar(fechaDesde, fechaHasta, idZona, idEvaluacion, idNivelEvaluacion,
						idNivelEvaluacionD, usuario.getIdUsuario());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método generar " + " " + e.getMessage());
		}
	}

	public void generar02(int anio) {
		Long idZona = 0L;
		Long idEvaluacion = 0L;
		Long idNivelEvaluacion = 0L;
		Long idNivelEvaluacionD = 0L;
		try {
			UsuarioEt usuario = appMain.getUsuario();
			iCantonDao.limpiarReporte(usuario.getIdUsuario());
			if (zonaSeleccionada != null) {
				idZona = zonaSeleccionada.getIdZona();
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

	public void generar03(int anio) {
		Long idZona = 0L;
		Long idEvaluacion = 0L;
		Long idNivelEvaluacion = 0L;
		Long idNivelEvaluacionD = 0L;
		try {
			UsuarioEt usuario = appMain.getUsuario();
			iEvaluacionDao.limpiarReporte(usuario.getIdUsuario());
			if (zonaSeleccionada != null) {
				idZona = zonaSeleccionada.getIdZona();
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
				iReporteEvaluacionConsolidadoDao.generar(fechaDesde, fechaHasta, idZona, idEvaluacion,
						idNivelEvaluacion, idNivelEvaluacionD, usuario.getIdUsuario());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método generar " + " " + e.getMessage());
		}
	}

	public void generar04(int anio) {
		Long idZona = 0L;
		Long idAgencia = 0L;
		Long idEvaluacion = 0L;
		Long idTipoCheckList = 0L;
		Long idNivelEvaluacion = 0L;
		Long idNivelEvaluacionD = 0L;
		try {
			UsuarioEt usuario = appMain.getUsuario();
			iProcesoDao.limpiarReporte(usuario.getIdUsuario());
			if (zonaSeleccionada != null) {
				idZona = zonaSeleccionada.getIdZona();
			}
			if (tipoChecKListSeleccionado != null) {
				idTipoCheckList = tipoChecKListSeleccionado.getIdTipoCheckList();
			}
			if (evaluacionSeleccionada != null) {
				idEvaluacion = evaluacionSeleccionada.getIdEvaluacion();
			}
			if (estacionSeleccionada != null) {
				idAgencia = estacionSeleccionada.getIdAgencia();
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
				iReporteTipoEvaluacionDao.generar(fechaDesde, fechaHasta, idZona, idTipoCheckList, idEvaluacion,
						idAgencia, idNivelEvaluacion, idNivelEvaluacionD, usuario.getIdUsuario());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método generar " + " " + e.getMessage());
		}
	}

	public void generar05(int anio) {
		Long idZona = 0L;
		Long idEvaluacion = 0L;
		Long idFrecuenciaVisita = 0L;
		try {
			UsuarioEt usuario = appMain.getUsuario();
			iTipoCargoDao.limpiarReporte(usuario.getIdUsuario());
			if (zonaSeleccionada != null) {
				idZona = zonaSeleccionada.getIdZona();
			}
			if (evaluacionSeleccionada != null) {
				idEvaluacion = evaluacionSeleccionada.getIdEvaluacion();
			}
			if (frecuenciaVisitaSeleccionado != null) {
				idFrecuenciaVisita = frecuenciaVisitaSeleccionado.getIdFrecuenciaVisita();
			}
			for (ParametrosGeneralesEt parametrosGenerales : mesesSeleccionados) {
				int mes = Integer.parseInt(parametrosGenerales.getValorLista());
				Date fechaDesde = getFechaDesde(mes, anio);
				Date fechaHasta = getFechaHasta(mes, anio);
				iReporteEvaluacionPlanificacionDao.generar(fechaDesde, fechaHasta, idZona, idEvaluacion,
						idFrecuenciaVisita, estadoCheckListSeleccionado, usuario.getIdUsuario());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método generar " + " " + e.getMessage());
		}
	}

	public void generar06(int anio) {
		Long idZona = 0L;
		Long idUsuario = 0L;
		Long idEvaluacion = 0L;
		try {
			UsuarioEt usuario = appMain.getUsuario();
			idUsuario = usuario.getIdUsuario();
			iTipoCargoDao.limpiarReporte(idUsuario);
			iKPICriticoDao.limpiarReporte(idUsuario);
			if (zonaSeleccionada != null) {
				idZona = zonaSeleccionada.getIdZona();
			}
			if (evaluacionSeleccionada != null) {
				idEvaluacion = evaluacionSeleccionada.getIdEvaluacion();
			}
			ParametrosGeneralesEt parametrosGeneral = iParametrolGeneralDao.getObjPadre("108");
			List<ParametrosGeneralesEt> meses = iParametrolGeneralDao.getListaHIjos(parametrosGeneral);
			for (ParametrosGeneralesEt anioVariacion : anioVariacionSeleccionados) {
				int anioV = Integer.parseInt(anioVariacion.getValorLista());
				for (ParametrosGeneralesEt mesesVariacion : meses) {
					int mes = Integer.parseInt(mesesVariacion.getValorLista());
					Date fechaDesde = getFechaDesde(mes, anioV);
					Date fechaHasta = getFechaHasta(mes, anioV);
					iReporteEvaluacionPlanificacionDao.generar(fechaDesde, fechaHasta, idZona, idEvaluacion, 0L,
							EstadoCheckListEnum.EJECUTADO, idUsuario);
					if (mes == 11) {
						iReporteEvaluacionVariacionDao.generar(Long.valueOf(anioV), usuario.getIdUsuario());
						iTipoCargoDao.limpiarReporte(idUsuario);
					}
				}

			}
			for (ParametrosGeneralesEt parametrosGenerales : meses) {
				int mes = Integer.parseInt(parametrosGenerales.getValorLista());
				Date fechaDesde = getFechaDesde(mes, anio);
				Date fechaHasta = getFechaHasta(mes, anio);
				iReporteEvaluacionPlanificacionDao.generar(fechaDesde, fechaHasta, idZona, idEvaluacion, 0L,
						EstadoCheckListEnum.EJECUTADO, idUsuario);
			}
			iReporteEvaluacionVariacionDao.generar(Long.valueOf(anio), usuario.getIdUsuario());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método generar " + " " + e.getMessage());
		}
	}

	public void generar07(int anio) {
		Long idZona = 0L;
		Long idEstacion = 0L;
		Long idTipoInventario = 0L;
		try {
			UsuarioEt usuario = appMain.getUsuario();
			iTipoInventarioDao.limpiarReporte(usuario.getIdUsuario());
			if (zonaSeleccionada != null) {
				idZona = zonaSeleccionada.getIdZona();
			}
			if (estacionSeleccionada != null) {
				idEstacion = estacionSeleccionada.getIdAgencia();
			}
			if (tipoInventarioSeleccionado != null) {
				idTipoInventario = tipoInventarioSeleccionado.getIdTipoInventario();
			}
			for (ParametrosGeneralesEt parametrosGenerales : mesesSeleccionados) {
				int mes = Integer.parseInt(parametrosGenerales.getValorLista());
				Date fechaDesde = getFechaDesde(mes, anio);
				Date fechaHasta = getFechaHasta(mes, anio);
				iReportePlanificacionInventarioDao.generar(fechaDesde, fechaHasta, idZona,idEstacion, idTipoInventario,
						usuario.getIdUsuario());
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

	public List<NivelEvaluacionEt> getNivelEvaluacionList() {
		List<NivelEvaluacionEt> nivelEvaluaciones = new ArrayList<NivelEvaluacionEt>();
		try {
			nivelEvaluaciones = iNivelEvaluacionDao.getNivelEvaluacionList(null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getNivelEvaluacionList " + " " + e.getMessage());
		}
		return nivelEvaluaciones;
	}

	public List<NivelEvaluacionDetalleEt> getNivelEvaluacionDetalleList() {
		List<NivelEvaluacionDetalleEt> nivelEvaluacionDetalles = new ArrayList<NivelEvaluacionDetalleEt>();
		try {
			if (nivelEvaluacionSeleccionado != null) {
				nivelEvaluacionDetalles = nivelEvaluacionSeleccionado.getNivelEvaluacionDetalle();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getNivelEvaluacionDetalleList " + " " + e.getMessage());
		}
		return nivelEvaluacionDetalles;
	}

	public List<FrecuenciaVisitaEt> getFrecuenciaVisitaList() {
		List<FrecuenciaVisitaEt> frecuenciaVisitas = new ArrayList<>();
		try {
			frecuenciaVisitas = iFrecuenciaVisitaDao.getFrecuenciaVisitaList(null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getFrecuenciaVisitaList " + " " + e.getMessage());
		}
		return frecuenciaVisitas;
	}

	public List<TipoInventarioEt> getTipoInventarioList() {
		List<TipoInventarioEt> tipoInventarios = new ArrayList<TipoInventarioEt>();
		try {
			tipoInventarios = iTipoInventarioDao.getTipoInventarioList(null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTipoInventarioList " + " " + e.getMessage());
		}
		return tipoInventarios;
	}

	public SelectItem[] getEstadoCheckList() {
		SelectItem[] items = new SelectItem[4];
		items[0] = new SelectItem(EstadoCheckListEnum.ESTADO_CHECK, EstadoCheckListEnum.ESTADO_CHECK.getDescripcion());
		items[1] = new SelectItem(EstadoCheckListEnum.EJECUTADO, EstadoCheckListEnum.EJECUTADO.getDescripcion());
		items[2] = new SelectItem(EstadoCheckListEnum.AGENDADA, EstadoCheckListEnum.AGENDADA.getDescripcion());
		items[3] = new SelectItem(EstadoCheckListEnum.NO_EJECUTADO, EstadoCheckListEnum.NO_EJECUTADO.getDescripcion());
		return items;
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

	public EstadoCheckListEnum getEstadoCheckListSeleccionado() {
		return estadoCheckListSeleccionado;
	}

	public void setEstadoCheckListSeleccionado(EstadoCheckListEnum estadoCheckListSeleccionado) {
		this.estadoCheckListSeleccionado = estadoCheckListSeleccionado;
	}

	public TipoChecKListEt getTipoChecKListSeleccionado() {
		return tipoChecKListSeleccionado;
	}

	public void setTipoChecKListSeleccionado(TipoChecKListEt tipoChecKListSeleccionado) {
		this.tipoChecKListSeleccionado = tipoChecKListSeleccionado;
	}

	public List<ParametrosGeneralesEt> getAnioVariacionSeleccionados() {
		return anioVariacionSeleccionados;
	}

	public void setAnioVariacionSeleccionados(List<ParametrosGeneralesEt> anioVariacionSeleccionados) {
		this.anioVariacionSeleccionados = anioVariacionSeleccionados;
	}

	public AgenciaEt getEstacionSeleccionada() {
		return estacionSeleccionada;
	}

	public void setEstacionSeleccionada(AgenciaEt estacionSeleccionada) {
		this.estacionSeleccionada = estacionSeleccionada;
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

	public FrecuenciaVisitaEt getFrecuenciaVisitaSeleccionado() {
		return frecuenciaVisitaSeleccionado;
	}

	public void setFrecuenciaVisitaSeleccionado(FrecuenciaVisitaEt frecuenciaVisitaSeleccionado) {
		this.frecuenciaVisitaSeleccionado = frecuenciaVisitaSeleccionado;
	}

	public TipoInventarioEt getTipoInventarioSeleccionado() {
		return tipoInventarioSeleccionado;
	}

	public void setTipoInventarioSeleccionado(TipoInventarioEt tipoInventarioSeleccionado) {
		this.tipoInventarioSeleccionado = tipoInventarioSeleccionado;
	}

	@Override
	public void onDestroy() {
		iCantonDao.remove();
		iProcesoDao.remove();
		iAgenciaDao.remove();
		iProvinciaDao.remove();
		iTipoCargoDao.remove();
		iEvaluacionDao.remove();
		iKPICriticoDao.remove();
		iTipoChecKListDao.remove();
		iTipoInventarioDao.remove();
		iParametrolGeneralDao.remove();
		iReporteTipoEvaluacionDao.remove();
		iReporteEvaluacionPuntajeDao.remove();
		iReporteEvaluacionVariacionDao.remove();
		iReporteEvaluacionNivelRiesgoDao.remove();
		iReporteEvaluacionConsolidadoDao.remove();
		iReporteEvaluacionPlanificacionDao.remove();
	}

}
