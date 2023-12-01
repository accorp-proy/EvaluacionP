package com.primax.bean.vs;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.param.NivelEvaluacionDetalleEt;
import com.primax.jpa.param.NivelEvaluacionEt;
import com.primax.jpa.param.ParametrosGeneralesEt;
import com.primax.jpa.param.TipoEstacionEt;
import com.primax.jpa.param.ZonaEt;
import com.primax.jpa.pla.TableroCabeceraEt;
import com.primax.jpa.pla.TableroDetalleEt;
import com.primax.jpa.pla.TableroEstacionZonaEt;
import com.primax.jpa.pla.TableroEvaluacionMesEt;
import com.primax.jpa.pla.TableroEvaluacionZonaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.jpa.param.ZonaUsuarioEt;
import com.primax.srv.idao.IAgenciaDao;
import com.primax.srv.idao.IEvaluacionDao;
import com.primax.srv.idao.INivelEvaluacionDao;
import com.primax.srv.idao.IParametrolGeneralDao;
import com.primax.srv.idao.ITableroCabeceraDao;
import com.primax.srv.idao.ITableroDetalleDao;
import com.primax.srv.idao.ITableroDetalleEstacionDao;
import com.primax.srv.idao.ITableroEstacionZonaDao;
import com.primax.srv.idao.ITableroEvaluacionMesDao;
import com.primax.srv.idao.ITableroEvaluacionZonaDao;
import com.primax.srv.idao.ITipoEstacionDao;
import com.primax.srv.idao.IUsuarioDao;
import com.primax.srv.idao.IZonaDao;

@Named("TableroControlBn")
@ViewScoped
public class TableroControlBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IZonaDao iZonaDao;
	@EJB
	private IUsuarioDao iUsuarioDao;
	@EJB
	private IAgenciaDao iAgenciaDao;
	@EJB
	private IEvaluacionDao iEvaluacionDao;
	@EJB
	private ITipoEstacionDao iTipoEstacionDao;
	@EJB
	private ITableroDetalleDao iTableroDetalleDao;
	@EJB
	private INivelEvaluacionDao iNivelEvaluacionDao;
	@EJB
	private ITableroCabeceraDao iTableroCabeceraDao;
	@EJB
	private IParametrolGeneralDao iParametrolGeneralDao;
	@EJB
	private ITableroEstacionZonaDao iTableroEstacionZonaDao;
	@EJB
	private ITableroEvaluacionMesDao iTableroEvaluacionMesDao;
	@EJB
	private ITableroEvaluacionZonaDao iTableroEvaluacionZonaDao;
	@EJB
	private ITableroDetalleEstacionDao iTableroDetalleEstacionDao;

	@Inject
	private AppMain appMain;

	private Long nivelT = 0L;
	private Long nivelE = 0L;
	private Long nivelC = 0L;
	private Long nivelF = 0L;
	private Long nivel01T = 0L;
	private Long nivel02T = 0L;
	private Long nivel03T = 0L;
	private Long nivel04T = 0L;
	private Long nivel05T = 0L;
	private Long nivel06T = 0L;
	private Long nivel01C = 0L;
	private Long nivel02C = 0L;
	private Long nivel03C = 0L;
	private Long nivel04C = 0L;
	private Long nivel05C = 0L;
	private Long nivel06C = 0L;
	private Long nivel01E = 0L;
	private Long nivel02E = 0L;
	private Long nivel03E = 0L;
	private Long nivel04E = 0L;
	private Long nivel05E = 0L;
	private Long nivel06E = 0L;
	private Long nivel01F = 0L;
	private Long nivel02F = 0L;
	private Long nivel03F = 0L;
	private Long nivel04F = 0L;
	private Long nivel05F = 0L;
	private Long nivel06F = 0L;
	private String nivel01 = "0";
	private String nivel02 = "0";
	private String nivel03 = "0";
	private String nivel04 = "0";
	private String nivel05 = "0";
	private String nivel06 = "0";
	private String nivel07 = "0";
	private String nivel08 = "0";
	private String nivel09 = "0";
	private String nivel10 = "0";
	private String porcentajeC = "";
	private String porcentajeE = "";
	private TableroCabeceraEt tableroC;
	private List<TableroDetalleEt> tabla3;
	private AgenciaEt estacionSeleccionada;
	private List<ZonaEt> zonaSeleccionadas;
	private List<TableroEstacionZonaEt> tabla2;
	private List<TableroEvaluacionMesEt> tabla4;
	private List<TableroEvaluacionZonaEt> tabla1;
	private ParametrosGeneralesEt anioSeleccionado;
	private List<EvaluacionEt> evaluacionSeleccionadas;
	private NivelEvaluacionEt nivelEvaluacionSeleccionado;
	private List<TipoEstacionEt> tipoEstacionSeleccionados;
	private List<ParametrosGeneralesEt> mesesSeleccionados;

	@Override
	public void init() {
		inicialzarObj();
		filtrarModelD();
	}

	public void inicialzarObj() {
		tableroC = null;
		tabla1 = new ArrayList<>();
		tabla2 = new ArrayList<>();
		tabla3 = new ArrayList<>();
		tabla4 = new ArrayList<>();
		estacionSeleccionada = null;
		nivelEvaluacionSeleccionado = null;
		zonaSeleccionadas = new ArrayList<>();
		mesesSeleccionados = new ArrayList<>();
		evaluacionSeleccionadas = new ArrayList<>();
		tipoEstacionSeleccionados = new ArrayList<>();
	}

	public void filtrarModelD() {
		Long idUsuario = 0L;
		try {
			UsuarioEt usuario = appMain.getUsuario();
			idUsuario = usuario.getIdUsuario();
			NivelEvaluacionEt nivelEvaluacion = iNivelEvaluacionDao.getNivelEvaluacion(1L);
			mostrarColumna(nivelEvaluacion);
			iTableroDetalleDao.limpiarTablero(idUsuario);
			iTableroDetalleEstacionDao.generar(primerDiaMes(), ultimoDiaMes(), 0L, 0L, 0L, 0L, 1L, idUsuario);
			iTableroEvaluacionZonaDao.generar(idUsuario);
			tabla1 = iTableroEvaluacionZonaDao.getTablaList(usuario);
			tabla2 = iTableroEstacionZonaDao.getTablaList(usuario);
			tabla3 = iTableroDetalleDao.getTablaList(usuario);
			tabla4 = iTableroEvaluacionMesDao.getTablaList(usuario);
			tableroC = iTableroCabeceraDao.getTablaCabecera(usuario);
			mostrarTotal(tabla1, tabla2);
			if (tableroC == null) {
				tableroC = new TableroCabeceraEt();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método filtrarModelD " + " " + e.getMessage());
		}
	}

	public void filtrarModel() {
		int anio = 0;
		Long idZona = 0L;
		Long idUsuario = 0L;
		Long idAgencia = 0L;
		String mensaje = "";
		Long idEvaluacion = 0L;
		Long idTipoEstacion = 0L;
		Long idNivelEvaluacion = 0L;
		try {
			limoiarObj();
			UsuarioEt usuario = appMain.getUsuario();
			idUsuario = usuario.getIdUsuario();
			mensaje = validarFiltrar();
			iTableroDetalleDao.limpiarTablero(idUsuario);
			if (mensaje.equals("")) {
				anio = Integer.parseInt((anioSeleccionado.getValorLista()));
				idNivelEvaluacion = nivelEvaluacionSeleccionado.getIdNivelEvaluacion();
				mostrarColumna(nivelEvaluacionSeleccionado);
				if (estacionSeleccionada != null) {
					idAgencia = estacionSeleccionada.getIdAgencia();
				}
				for (TipoEstacionEt tipoEstacion : tipoEstacionSeleccionados) {
					idTipoEstacion = tipoEstacion.getIdTipoEstacion();
					for (ZonaEt zona : zonaSeleccionadas) {
						idZona = zona.getIdZona();
						for (EvaluacionEt evaluacion : evaluacionSeleccionadas) {
							idEvaluacion = evaluacion.getIdEvaluacion();
							for (ParametrosGeneralesEt parametrosGenerales : mesesSeleccionados) {
								int mes = Integer.parseInt(parametrosGenerales.getValorLista());
								Date fechaDesde = getFechaDesde(mes, anio);
								Date fechaHasta = getFechaHasta(mes, anio);
								iTableroDetalleEstacionDao.generar(fechaDesde, fechaHasta, idTipoEstacion, idZona,
										idAgencia, idEvaluacion, idNivelEvaluacion, idUsuario);
							}
						}
					}
				}
				iTableroEvaluacionZonaDao.generar(idUsuario);
				tabla1 = iTableroEvaluacionZonaDao.getTablaList(usuario);
				tabla2 = iTableroEstacionZonaDao.getTablaList(usuario);
				tabla3 = iTableroDetalleDao.getTablaList(usuario);
				tabla4 = iTableroEvaluacionMesDao.getTablaList(usuario);
				tableroC = iTableroCabeceraDao.getTablaCabecera(usuario);
				mostrarTotal(tabla1, tabla2);
			} else {
				showInfo("Notificación", FacesMessage.SEVERITY_INFO, null, mensaje);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método filtrarModel " + " " + e.getMessage());
		}
	}

	public void limoiarObj() {
		try {
			nivel01C = 0L;
			nivel02C = 0L;
			nivel03C = 0L;
			nivel04C = 0L;
			nivel05C = 0L;
			nivel06C = 0L;
			nivel01F = 0L;
			nivel02F = 0L;
			nivel03F = 0L;
			nivel04F = 0L;
			nivel05F = 0L;
			nivel06F = 0L;
			nivel01 = "0";
			nivel02 = "0";
			nivel03 = "0";
			nivel04 = "0";
			nivel05 = "0";
			nivel06 = "0";
			nivel07 = "0";
			nivel08 = "0";
			nivel09 = "0";
			nivel10 = "0";
			porcentajeC = "";
			porcentajeE = "";
			tabla1 = new ArrayList<>();
			tabla2 = new ArrayList<>();
			tabla3 = new ArrayList<>();
			tabla4 = new ArrayList<>();
			tableroC = new TableroCabeceraEt();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método limoiarObj " + " " + e.getMessage());
		}
	}

	public void mostrarTotal(List<TableroEvaluacionZonaEt> tablaT1, List<TableroEstacionZonaEt> tablaT2) {
		Double a = 0.0D;

		try {
			nivel01T = tablaT1.stream().mapToLong(p -> p.getNivel01()).sum();
			nivel02T = tablaT1.stream().mapToLong(p -> p.getNivel02()).sum();
			nivel03T = tablaT1.stream().mapToLong(p -> p.getNivel03()).sum();
			nivel04T = tablaT1.stream().mapToLong(p -> p.getNivel04()).sum();
			nivel05T = tablaT1.stream().mapToLong(p -> p.getNivel05()).sum();
			nivel06T = tablaT1.stream().mapToLong(p -> p.getNivel06()).sum();
			nivelT = tablaT1.stream().mapToLong(p -> p.getTotal()).sum();
			if (nivel01T != 0) {
				a = (double) nivel01T / nivelT;
				nivel01C = (long) precision(a * 100, 0);
			}
			porcentajeC += nivel01C + ",";
			if (nivel02T != 0) {
				a = (double) nivel02T / nivelT;
				nivel02C = (long) precision(a * 100, 0);
			}
			porcentajeC += nivel02C + ",";
			if (nivel03T != 0) {
				a = (double) nivel03T / nivelT;
				nivel03C = (long) precision(a * 100, 0);
			}
			porcentajeC += nivel03C + ",";
			if (nivel04T != 0) {
				a = (double) nivel04T / nivelT;
				nivel04C = (long) precision(a * 100, 0);
			}
			porcentajeC += nivel04C + ",";
			if (nivel05T != 0) {
				a = (double) nivel05T / nivelT;
				nivel05C = (long) precision(a * 100, 0);
			}
			porcentajeC += nivel05C + ",";
			if (nivel06T != 0) {
				a = (double) nivel06T / nivelT;
				nivel06C = (long) precision(a * 100, 0);
			}
			porcentajeC += nivel06C;
			nivel01E = tablaT2.stream().mapToLong(p -> p.getNivel01()).sum();
			nivel02E = tablaT2.stream().mapToLong(p -> p.getNivel02()).sum();
			nivel03E = tablaT2.stream().mapToLong(p -> p.getNivel03()).sum();
			nivel04E = tablaT2.stream().mapToLong(p -> p.getNivel04()).sum();
			nivel05E = tablaT2.stream().mapToLong(p -> p.getNivel05()).sum();
			nivel06E = tablaT2.stream().mapToLong(p -> p.getNivel06()).sum();
			nivelE = tablaT2.stream().mapToLong(p -> p.getTotal()).sum();
			if (nivel01E != 0) {
				a = (double) nivel01E / nivelE;
				nivel01F = (long) precision(a * 100, 0);
			}
			porcentajeE += nivel01F + ",";
			if (nivel02E != 0) {
				a = (double) nivel02E / nivelE;
				nivel02F = (long) precision(a * 100, 0);
			}
			porcentajeE += nivel02F + ",";
			if (nivel03E != 0) {
				a = (double) nivel03E / nivelE;
				nivel03F = (long) precision(a * 100, 0);
			}
			porcentajeE += nivel03F + ",";
			if (nivel04E != 0) {
				a = (double) nivel04E / nivelE;
				nivel04F = (long) precision(a * 100, 0);
			}
			porcentajeE += nivel04F + ",";
			if (nivel05E != 0) {
				a = (double) nivel05E / nivelE;
				nivel05F = (long) precision(a * 100, 0);
			}
			porcentajeE += nivel05F + ",";
			if (nivel06E != 0) {
				a = (double) nivel06E / nivelE;
				nivel06F = (long) precision(a * 100, 0);
			}
			porcentajeE += nivel06F;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método mostrarTotal" + " " + e.getMessage());
		}
	}

	public double precision(double numero, int digitos) {
		double resultado;
		resultado = numero * Math.pow(10, digitos);
		resultado = Math.round(resultado);
		resultado = resultado / Math.pow(10, digitos);
		return resultado;
	}

	public void mostrarColumna(NivelEvaluacionEt nivel) {
		try {
			for (NivelEvaluacionDetalleEt det : nivel.getNivelEvaluacionDetalle()) {
				if (det.getOrden() == 1L) {
					nivel01 = det.getDescripcion();
				} else if (det.getOrden() == 2L) {
					nivel02 = det.getDescripcion();
				} else if (det.getOrden() == 3L) {
					nivel03 = det.getDescripcion();
				} else if (det.getOrden() == 4L) {
					nivel04 = det.getDescripcion();
				} else if (det.getOrden() == 5L) {
					nivel05 = det.getDescripcion();
				} else if (det.getOrden() == 6L) {
					nivel06 = det.getDescripcion();
				} else if (det.getOrden() == 7L) {
					nivel07 = det.getDescripcion();
				} else if (det.getOrden() == 8L) {
					nivel08 = det.getDescripcion();
				} else if (det.getOrden() == 9L) {
					nivel09 = det.getDescripcion();
				} else if (det.getOrden() == 10L) {
					nivel10 = det.getDescripcion();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método mostrarColumna " + " " + e.getMessage());
		}
	}

	public String validarFiltrar() {
		String mensaje = "";
		try {
			if (anioSeleccionado == null) {
				mensaje = "Por Favor Seleccionar Año";
			}
			if (mesesSeleccionados == null) {
				mensaje = "Por Favor Seleccionar Mes";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método validarFiltrar " + " " + e.getMessage());
		}

		return mensaje;
	}

	public List<ParametrosGeneralesEt> getAnioList() {
		List<ParametrosGeneralesEt> parAnio = new ArrayList<ParametrosGeneralesEt>();
		try {
			ParametrosGeneralesEt parG = iParametrolGeneralDao.getObjPadre("106");
			parAnio = iParametrolGeneralDao.getListaHIjos(parG);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getAnioList " + " " + e.getMessage());
		}
		return parAnio;
	}

	public List<ParametrosGeneralesEt> getMesList() {
		List<ParametrosGeneralesEt> parMes = new ArrayList<ParametrosGeneralesEt>();
		try {
			ParametrosGeneralesEt parG = iParametrolGeneralDao.getObjPadre("108");
			parMes = iParametrolGeneralDao.getListaHIjos(parG);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getMesList " + " " + e.getMessage());
		}
		return parMes;
	}

	public List<AgenciaEt> getAgenciaList() {
		List<AgenciaEt> agencias = new ArrayList<AgenciaEt>();
		try {
			iAgenciaDao.clear();
			if (zonaSeleccionadas != null) {
				for (TipoEstacionEt tipoEstacion : tipoEstacionSeleccionados) {
					for (ZonaEt zona : zonaSeleccionadas) {
						agencias.addAll(iAgenciaDao.getAgenciasPorZonas(zona, tipoEstacion));
					}
				}
			} else {
				agencias = iAgenciaDao.getAgencias(EstadoEnum.ACT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getAgenciaList " + " " + e.getMessage());
		}
		return agencias;
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

	public List<TipoEstacionEt> getTipoEstacionList() {
		List<TipoEstacionEt> tipoEstaciones = new ArrayList<>();
		try {
			tipoEstaciones = iTipoEstacionDao.getTipoEstacionList(null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTipoEstacionList " + " " + e.getMessage());
		}
		return tipoEstaciones;
	}

	public List<ZonaEt> getZonaList() {
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

	public static String dateMonth(Date date) {
		String result = "";
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = 0;

		try {
			month = calendar.get(Calendar.MONTH);
		} catch (Exception ex) {
		}
		switch (month) {
		case 0: {
			result = "ENERO";
			break;
		}
		case 1: {
			result = "FEBRERO";
			break;
		}
		case 2: {
			result = "MARZO";
			break;
		}
		case 3: {
			result = "ABRIL";
			break;
		}
		case 4: {
			result = "MAYO";
			break;
		}
		case 5: {
			result = "JUNIO";
			break;
		}
		case 6: {
			result = "JULIO";
			break;
		}
		case 7: {
			result = "AGOSTO";
			break;
		}
		case 8: {
			result = "SEPTIEMBRE";
			break;
		}
		case 9: {
			result = "OCTUBRE";
			break;
		}
		case 10: {
			result = "NOVIEMBRE";
			break;
		}
		case 11: {
			result = "DICIEMBRE";
			break;
		}
		default: {
			result = "Error";
			break;
		}
		}
		return result;
	}

	public Date primerDiaMes() {
		int mes = 0;
		int anio = 0;
		Calendar fechDesde = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			int primerDiaMes = fechDesde.getActualMinimum(Calendar.DAY_OF_MONTH);
			anio = fechDesde.get(Calendar.YEAR);
			mes = fechDesde.get(Calendar.MONTH);
			fechDesde.set(anio, mes, primerDiaMes);
			String fechDesdeS = df.format(fechDesde.getTime());
			System.out.println("Fecha Primer Día" + " " + fechDesdeS);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método primerDiaMes " + " " + e.getMessage());
		}
		return fechDesde.getTime();

	}

	public Date ultimoDiaMes() {
		int mes = 0;
		int anio = 0;
		Calendar fechHasta = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Calendar calendario = Calendar.getInstance();
			int ultimoDiaMes = calendario.getActualMaximum(Calendar.DAY_OF_MONTH);
			anio = calendario.get(Calendar.YEAR);
			mes = calendario.get(Calendar.MONTH);
			fechHasta.set(anio, mes, ultimoDiaMes);
			String fechaHastaS = df.format(fechHasta.getTime());
			System.out.println("Fecha Ultimo Día" + " " + fechaHastaS);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método ultimoDiaMes " + " " + e.getMessage());
		}
		return fechHasta.getTime();
	}

	public ParametrosGeneralesEt getAnioSeleccionado() {
		return anioSeleccionado;
	}

	public void setAnioSeleccionado(ParametrosGeneralesEt anioSeleccionado) {
		this.anioSeleccionado = anioSeleccionado;
	}

	public AgenciaEt getEstacionSeleccionada() {
		return estacionSeleccionada;
	}

	public void setEstacionSeleccionada(AgenciaEt estacionSeleccionada) {
		this.estacionSeleccionada = estacionSeleccionada;
	}

	public List<ParametrosGeneralesEt> getMesesSeleccionados() {
		return mesesSeleccionados;
	}

	public void setMesesSeleccionados(List<ParametrosGeneralesEt> mesesSeleccionados) {
		this.mesesSeleccionados = mesesSeleccionados;
	}

	public NivelEvaluacionEt getNivelEvaluacionSeleccionado() {
		return nivelEvaluacionSeleccionado;
	}

	public void setNivelEvaluacionSeleccionado(NivelEvaluacionEt nivelEvaluacionSeleccionado) {
		this.nivelEvaluacionSeleccionado = nivelEvaluacionSeleccionado;
	}

	public List<TableroDetalleEt> getTabla3() {
		return tabla3;
	}

	public void setTabla3(List<TableroDetalleEt> tabla3) {
		this.tabla3 = tabla3;
	}

	public List<TableroEstacionZonaEt> getTabla2() {
		return tabla2;
	}

	public void setTabla2(List<TableroEstacionZonaEt> tabla2) {
		this.tabla2 = tabla2;
	}

	public List<TableroEvaluacionMesEt> getTabla4() {
		return tabla4;
	}

	public void setTabla4(List<TableroEvaluacionMesEt> tabla4) {
		this.tabla4 = tabla4;
	}

	public List<TableroEvaluacionZonaEt> getTabla1() {
		return tabla1;
	}

	public void setTabla1(List<TableroEvaluacionZonaEt> tabla1) {
		this.tabla1 = tabla1;
	}

	public String getNivel01() {
		return nivel01;
	}

	public void setNivel01(String nivel01) {
		this.nivel01 = nivel01;
	}

	public String getNivel02() {
		return nivel02;
	}

	public void setNivel02(String nivel02) {
		this.nivel02 = nivel02;
	}

	public String getNivel03() {
		return nivel03;
	}

	public void setNivel03(String nivel03) {
		this.nivel03 = nivel03;
	}

	public String getNivel04() {
		return nivel04;
	}

	public void setNivel04(String nivel04) {
		this.nivel04 = nivel04;
	}

	public String getNivel05() {
		return nivel05;
	}

	public void setNivel05(String nivel05) {
		this.nivel05 = nivel05;
	}

	public String getNivel06() {
		return nivel06;
	}

	public void setNivel06(String nivel06) {
		this.nivel06 = nivel06;
	}

	public String getNivel07() {
		return nivel07;
	}

	public void setNivel07(String nivel07) {
		this.nivel07 = nivel07;
	}

	public String getNivel08() {
		return nivel08;
	}

	public void setNivel08(String nivel08) {
		this.nivel08 = nivel08;
	}

	public String getNivel09() {
		return nivel09;
	}

	public void setNivel09(String nivel09) {
		this.nivel09 = nivel09;
	}

	public String getNivel10() {
		return nivel10;
	}

	public void setNivel10(String nivel10) {
		this.nivel10 = nivel10;
	}

	public Long getNivel02T() {
		return nivel02T;
	}

	public void setNivel02T(Long nivel02t) {
		nivel02T = nivel02t;
	}

	public Long getNivel03T() {
		return nivel03T;
	}

	public void setNivel03T(Long nivel03t) {
		nivel03T = nivel03t;
	}

	public Long getNivel04T() {
		return nivel04T;
	}

	public void setNivel04T(Long nivel04t) {
		nivel04T = nivel04t;
	}

	public Long getNivel05T() {
		return nivel05T;
	}

	public void setNivel05T(Long nivel05t) {
		nivel05T = nivel05t;
	}

	public Long getNivel06T() {
		return nivel06T;
	}

	public void setNivel06T(Long nivel06t) {
		nivel06T = nivel06t;
	}

	public Long getNivel01T() {
		return nivel01T;
	}

	public void setNivel01T(Long nivel01t) {
		nivel01T = nivel01t;
	}

	public Long getNivelT() {
		return nivelT;
	}

	public void setNivelT(Long nivelT) {
		this.nivelT = nivelT;
	}

	public Long getNivelE() {
		return nivelE;
	}

	public void setNivelE(Long nivelE) {
		this.nivelE = nivelE;
	}

	public Long getNivel01E() {
		return nivel01E;
	}

	public void setNivel01E(Long nivel01e) {
		nivel01E = nivel01e;
	}

	public Long getNivel02E() {
		return nivel02E;
	}

	public void setNivel02E(Long nivel02e) {
		nivel02E = nivel02e;
	}

	public Long getNivel03E() {
		return nivel03E;
	}

	public void setNivel03E(Long nivel03e) {
		nivel03E = nivel03e;
	}

	public Long getNivel04E() {
		return nivel04E;
	}

	public void setNivel04E(Long nivel04e) {
		nivel04E = nivel04e;
	}

	public Long getNivel05E() {
		return nivel05E;
	}

	public void setNivel05E(Long nivel05e) {
		nivel05E = nivel05e;
	}

	public Long getNivel06E() {
		return nivel06E;
	}

	public void setNivel06E(Long nivel06e) {
		nivel06E = nivel06e;
	}

	public TableroCabeceraEt getTableroC() {
		return tableroC;
	}

	public void setTableroC(TableroCabeceraEt tableroC) {
		this.tableroC = tableroC;
	}

	public List<ZonaEt> getZonaSeleccionadas() {
		return zonaSeleccionadas;
	}

	public void setZonaSeleccionadas(List<ZonaEt> zonaSeleccionadas) {
		this.zonaSeleccionadas = zonaSeleccionadas;
	}

	public List<EvaluacionEt> getEvaluacionSeleccionadas() {
		return evaluacionSeleccionadas;
	}

	public void setEvaluacionSeleccionadas(List<EvaluacionEt> evaluacionSeleccionadas) {
		this.evaluacionSeleccionadas = evaluacionSeleccionadas;
	}

	public Long getNivelC() {
		return nivelC;
	}

	public void setNivelC(Long nivelC) {
		this.nivelC = nivelC;
	}

	public Long getNivelF() {
		return nivelF;
	}

	public void setNivelF(Long nivelF) {
		this.nivelF = nivelF;
	}

	public Long getNivel01C() {
		return nivel01C;
	}

	public void setNivel01C(Long nivel01c) {
		nivel01C = nivel01c;
	}

	public Long getNivel02C() {
		return nivel02C;
	}

	public void setNivel02C(Long nivel02c) {
		nivel02C = nivel02c;
	}

	public Long getNivel03C() {
		return nivel03C;
	}

	public void setNivel03C(Long nivel03c) {
		nivel03C = nivel03c;
	}

	public Long getNivel04C() {
		return nivel04C;
	}

	public void setNivel04C(Long nivel04c) {
		nivel04C = nivel04c;
	}

	public Long getNivel05C() {
		return nivel05C;
	}

	public void setNivel05C(Long nivel05c) {
		nivel05C = nivel05c;
	}

	public Long getNivel06C() {
		return nivel06C;
	}

	public void setNivel06C(Long nivel06c) {
		nivel06C = nivel06c;
	}

	public Long getNivel01F() {
		return nivel01F;
	}

	public void setNivel01F(Long nivel01f) {
		nivel01F = nivel01f;
	}

	public Long getNivel02F() {
		return nivel02F;
	}

	public void setNivel02F(Long nivel02f) {
		nivel02F = nivel02f;
	}

	public Long getNivel03F() {
		return nivel03F;
	}

	public void setNivel03F(Long nivel03f) {
		nivel03F = nivel03f;
	}

	public Long getNivel04F() {
		return nivel04F;
	}

	public void setNivel04F(Long nivel04f) {
		nivel04F = nivel04f;
	}

	public Long getNivel05F() {
		return nivel05F;
	}

	public void setNivel05F(Long nivel05f) {
		nivel05F = nivel05f;
	}

	public Long getNivel06F() {
		return nivel06F;
	}

	public void setNivel06F(Long nivel06f) {
		nivel06F = nivel06f;
	}

	public List<TipoEstacionEt> getTipoEstacionSeleccionados() {
		return tipoEstacionSeleccionados;
	}

	public void setTipoEstacionSeleccionados(List<TipoEstacionEt> tipoEstacionSeleccionados) {
		this.tipoEstacionSeleccionados = tipoEstacionSeleccionados;
	}

	public String getPorcentajeC() {
		return porcentajeC;
	}

	public void setPorcentajeC(String porcentajeC) {
		this.porcentajeC = porcentajeC;
	}

	public String getPorcentajeE() {
		return porcentajeE;
	}

	public void setPorcentajeE(String porcentajeE) {
		this.porcentajeE = porcentajeE;
	}

	@Override
	public void onDestroy() {
		iZonaDao.remove();
		iZonaDao.remove();
		iAgenciaDao.remove();
		iEvaluacionDao.remove();
		iTipoEstacionDao.remove();
		iTableroDetalleDao.remove();
		iTableroCabeceraDao.remove();
		iNivelEvaluacionDao.remove();
		iParametrolGeneralDao.remove();
		iTableroEstacionZonaDao.remove();
		iTableroEvaluacionMesDao.remove();
		iTableroEvaluacionZonaDao.remove();
		iTableroDetalleEstacionDao.remove();
	}

}
