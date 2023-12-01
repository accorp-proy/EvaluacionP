package com.primax.bean.vs.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
import com.primax.enm.gen.RutaFileEnum;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.param.NivelEvaluacionDetalleEt;
import com.primax.jpa.param.NivelEvaluacionEt;
import com.primax.jpa.param.ParametrosGeneralesEt;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.param.ZonaEt;
import com.primax.jpa.param.ZonaUsuarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IAgenciaDao;
import com.primax.srv.idao.IEvaluacionDao;
import com.primax.srv.idao.IGeneralUtilsDao;
import com.primax.srv.idao.INivelEsfuerzoDao;
import com.primax.srv.idao.INivelEvaluacionDao;
import com.primax.srv.idao.IParametrolGeneralDao;
import com.primax.srv.idao.IReporteProcesoConsolidadoDao;
import com.primax.srv.idao.IReporteProcesoCualitativoDao;
import com.primax.srv.idao.ITipoChecKListDao;
import com.primax.srv.idao.IUsuarioDao;
import com.primax.srv.idao.IZonaDao;

@Named("rptNivelEsfuerzoProcesoBn")
@ViewScoped
public class rptNivelEsfuerzoProcesoBean extends BaseBean implements Serializable {

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
	private IGeneralUtilsDao iGeneralUtilsDao;
	@EJB
	private INivelEsfuerzoDao iNivelEsfuerzoDao;
	@EJB
	private ITipoChecKListDao iTipoChecKListDao;
	@EJB
	private INivelEvaluacionDao iNivelEvaluacionDao;
	@EJB
	private IParametrolGeneralDao iParametrolGeneralDao;
	@EJB
	private IReporteProcesoCualitativoDao iReporteProcesoCualitativoDao;
	@EJB
	private IReporteProcesoConsolidadoDao iReporteProcesoConsolidadoDao;

	private List<String> tipos;
	private String tipoSeleccionado;
	private ZonaEt zonaSeleccionada;
	private AgenciaEt estacionSeleccionada;
	private EvaluacionEt evaluacionSeleccionada;
	private ParametrosGeneralesEt anioSeleccionado;
	private TipoChecKListEt tipoChecKListSeleccionado;
	private NivelEvaluacionEt nivelEvaluacionSeleccionado;
	private List<ParametrosGeneralesEt> mesesSeleccionados;
	private NivelEvaluacionDetalleEt nivelEvaluacionDetalleSeleccionado;

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Inject
	private AppMain appMain;

	@Override
	public void init() {
		// generarReporte02();
		limpiarReporte();
		tipo();
		tipoSeleccionado = "Cualitativo";
	}

	public void tipo() {
		try {
			tipos = new ArrayList<String>();
			tipos.add("Cualitativo");
			tipos.add("Cuantitativo");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método tipo " + " " + e.getMessage());
		}
	}

	public void limpiarReporte() {
		try {
			iNivelEvaluacionDao.limpiarReporte(appMain.getUsuario().getIdUsuario());
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
		boolean tipo;
		Long idEvaluacion = 0L;
		Long idTipoCheckList = 0L;
		Long idNivelEvaluacion = 0L;
		Long idNivelEvaluacionD = 0L;
		try {
			UsuarioEt usuario = appMain.getUsuario();
			tipo = tipoSeleccionado.equals("Cualitativo") ? true : false;
			if (tipo) {
				iNivelEsfuerzoDao.limpiarReporte(usuario.getIdUsuario());
			} else {
				iNivelEvaluacionDao.limpiarReporte(usuario.getIdUsuario());
			}
			if (zonaSeleccionada != null) {
				idZona = zonaSeleccionada.getIdZona();
			}
			if (evaluacionSeleccionada != null) {
				idEvaluacion = evaluacionSeleccionada.getIdEvaluacion();
			}
			if (tipoChecKListSeleccionado != null) {
				idTipoCheckList = tipoChecKListSeleccionado.getIdTipoCheckList();
			}
			if (nivelEvaluacionSeleccionado != null) {
				idNivelEvaluacion = nivelEvaluacionSeleccionado.getIdNivelEvaluacion();
			}
			if (nivelEvaluacionDetalleSeleccionado != null) {
				idNivelEvaluacionD = nivelEvaluacionDetalleSeleccionado.getIdNivelEvaluacionDetalle();
			}
			if (tipo) {
				for (ParametrosGeneralesEt parametrosGenerales : mesesSeleccionados) {
					int mes = Integer.parseInt(parametrosGenerales.getValorLista());
					Date fechaDesde = getFechaDesde(mes, anio);
					Date fechaHasta = getFechaHasta(mes, anio);
					iReporteProcesoCualitativoDao.generar(fechaDesde, fechaHasta, idZona, idEvaluacion, idTipoCheckList,
							idNivelEvaluacion, idNivelEvaluacionD, usuario.getIdUsuario());
				}
			} else {
				for (ParametrosGeneralesEt parametrosGenerales : mesesSeleccionados) {
					int mes = Integer.parseInt(parametrosGenerales.getValorLista());
					Date fechaDesde = getFechaDesde(mes, anio);
					Date fechaHasta = getFechaHasta(mes, anio);
					iReporteProcesoConsolidadoDao.generar(fechaDesde, fechaHasta, idZona, idEvaluacion, idTipoCheckList,
							idNivelEvaluacion, idNivelEvaluacionD, usuario.getIdUsuario());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método generar " + " " + e.getMessage());
		}
	}

	public void guardarNivelE() {
		try {
			if (nivelEvaluacionSeleccionado != null) {
				guardarImagenes(nivelEvaluacionSeleccionado);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarNivelE " + " " + e.getMessage());
		}
	}

	public void guardarImagenes(NivelEvaluacionEt nivelEvaluacion) {
		try {
			for (NivelEvaluacionDetalleEt nivelEvaluacionD : nivelEvaluacion.getNivelEvaluacionDetalle()) {
				if (nivelEvaluacionD.getImgPathServer() != null) {
					String pathServerImg = nivelEvaluacionD.getImgPathServer() + nivelEvaluacionD.getImgNombre();
					InputStream inputStreamImg = getImg(pathServerImg);
					String ruta = iGeneralUtilsDao.creaRuta(
							nivelEvaluacionD.getNivelEvaluacion().getIdNivelEvaluacion(),
							RutaFileEnum.RUTA_PROYECTO_DEPLOYED.getDescripcion() + File.separator + "resources"
									+ File.separator + "images" + File.separator + "nivelEvaluacion");
					iGeneralUtilsDao.copyFile(nivelEvaluacionD.getImgNombre(), inputStreamImg, ruta);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarImagenes " + " " + e.getMessage());
		}
	}

	public InputStream getImg(String pathImg) {
		String path = pathImg;
		System.out.println(path);
		File arch = new File(path);
		InputStream img = null;
		try {
			img = new FileInputStream(arch);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return img;
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

	public List<String> getTipos() {
		return tipos;
	}

	public void setTipos(List<String> tipos) {
		this.tipos = tipos;
	}

	public String getTipoSeleccionado() {
		return tipoSeleccionado;
	}

	public void setTipoSeleccionado(String tipoSeleccionado) {
		this.tipoSeleccionado = tipoSeleccionado;
	}

	@Override
	public void onDestroy() {
		iZonaDao.remove();
		iUsuarioDao.remove();
		iAgenciaDao.remove();
		iEvaluacionDao.remove();
		iNivelEsfuerzoDao.remove();
		iTipoChecKListDao.remove();
		iNivelEvaluacionDao.remove();
		iParametrolGeneralDao.remove();
		iReporteProcesoConsolidadoDao.remove();
		iReporteProcesoCualitativoDao.remove();

	}

}
