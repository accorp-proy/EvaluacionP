package com.primax.bean.vs;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.jpa.enums.EstadoCheckListEnum;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.param.ResponsableEt;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.param.TipoInventarioEt;
import com.primax.jpa.pla.CheckListEjecucionEt;
import com.primax.jpa.pla.CheckListEjecucionFirmaEt;
import com.primax.jpa.pla.PlanificacionEt;
import com.primax.jpa.pla.PlanificacionInventarioEt;
import com.primax.jpa.pla.PlanificacionInventarioTipoEt;
import com.primax.jpa.pla.PlanificacionResponsableEt;
import com.primax.jpa.sec.RolEt;
import com.primax.jpa.sec.RolUsuarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IAgenciaDao;
import com.primax.srv.idao.ICheckListEjecucionDao;
import com.primax.srv.idao.ICheckListEjecucionFirmaDao;
import com.primax.srv.idao.IEvaluacionDao;
import com.primax.srv.idao.IPlanificacionDao;
import com.primax.srv.idao.IPlanificacionInventarioDao;
import com.primax.srv.idao.IPlanificacionInventarioTipoDao;
import com.primax.srv.idao.IResponsableDao;
import com.primax.srv.idao.IRolEtDao;
import com.primax.srv.idao.ITipoChecKListDao;
import com.primax.srv.idao.IUsuarioDao;

import org.primefaces.context.RequestContext;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@Named("dashboardBn")
@ViewScoped
public class DashboardBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = -734535679991041247L;

	@EJB
	private IRolEtDao iRolEtDao;
	@EJB
	private IAgenciaDao iAgenciaDao;
	@EJB
	private IUsuarioDao iUsuarioDao;
	@EJB
	private IEvaluacionDao iEvaluacionDao;
	@EJB
	private IResponsableDao iResponsableDao;
	@EJB
	private ITipoChecKListDao iTipoChecListDao;
	@EJB
	private IPlanificacionDao iPlanificacionDao;
	@EJB
	private ICheckListEjecucionDao iCheckListEjecucionDao;
	@EJB
	private ICheckListEjecucionFirmaDao iCheckListEjecucionFirmaDao;
	@EJB
	private IPlanificacionInventarioDao iPlanificacionInventarioDao;
	@EJB
	private IPlanificacionInventarioTipoDao iPlanificacionInventarioTipoDao;

	@Inject
	private AppMain appMain;

	/**
	 * Variables Calendario
	 */
	private ScheduleModel eventModel;
	private ScheduleEvent event = new DefaultScheduleEvent();

	private String titulo = "";
	private boolean visualizar = false;
	private boolean tipoAuditor = false;
	private boolean tipoGerente = false;
	private AgenciaEt agenciaSeleccionada;
	private Date fechaPlanificacion = null;
	private AgenciaEt estacionSeleccionada;
	private EvaluacionEt evaluacionSeleccionado;
	private EvaluacionEt evaluacionSeleccionada;
	private List<PlanificacionEt> planificaciones;
	private PlanificacionEt planificacionSeleccionada;
	private TipoChecKListEt tipoChecKListSeleccionado;
	private List<CheckListEjecucionEt> checkListEjecuciones;
	private List<TipoInventarioEt> tipoInventarioSeleccionados;
	private CheckListEjecucionEt checkListEjecucionSeleccionado;
	private List<PlanificacionInventarioEt> planificacionesIventario;
	private PlanificacionInventarioEt planificacionInventarioSeleccionada;

	@Override
	protected void init() {
		inicializarObj();
	}

	public void inicializarObj() {
		List<RolUsuarioEt> rolUsuario = null;
		RolEt rol = iRolEtDao.getRolbyId(9L);
		UsuarioEt usuario = appMain.getUsuario();
		rolUsuario = usuario.getRolesUsario();
		if (containsRol(rolUsuario, rol)) {
			tipoGerente = true;
			buscar();
		} else {
			tipoAuditor = true;
		}
		tipoInventarioSeleccionados = new ArrayList<>();
		planificacionSeleccionada = new PlanificacionEt();
		planificacionInventarioSeleccionada = new PlanificacionInventarioEt();
		event = new DefaultScheduleEvent();
		iniCalendarControl();

	}

	public void visualizarCal(boolean visualizar) {
		try {
			if (visualizar) {
				iniCalendarInventario();
				titulo = "CONTROL DE INVENTARIO";
			} else {
				iniCalendarControl();
				titulo = "CONTROL DE EVALUACIÓN";
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método visualizarCal " + " " + e.getMessage());
		}
	}

	public void iniCalendarControl() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			eventModel = new LazyScheduleModel() {
				private static final long serialVersionUID = 1L;

				@Override
				public void loadEvents(Date desde, Date hasta) {
					try {
						String estado = "";
						String codigo = "";
						String estacion = "";
						String responsable = "";
						UsuarioEt usuario = appMain.getUsuario();
						planificaciones = iPlanificacionDao.getPlanificacionList(evaluacionSeleccionada,
								tipoChecKListSeleccionado, desde, hasta, usuario);
						DefaultScheduleEvent scheduleEventAllDay;
						for (PlanificacionEt planificacion : planificaciones) {
							String leyenda0 = "";
							String leyenda1 = "";
							String tema = "schedule-agendada";
							for (CheckListEjecucionEt checkListE : planificacion.getCheckListEjecucion()) {
								codigo = "EVALUACIÓN:" + " " + checkListE.getCodigo() + "<br/>";
								estado = "ESTADO:" + " " + checkListE.getEstadoCheckList().getDescripcion() + "<br/>";
								estacion = checkListE.getPlanificacion().getAgencia().getNombreAgencia() + "<br/>";
								responsable = "RESPONSABLE:" + " "
										+ checkListE.getUsuarioAsignado().getPersonaUsuario().getNombreCompleto()
										+ "<br/>";
								if (leyenda0.equals("")) {
									leyenda0 = estacion + codigo + responsable + estado;
								} else {
									leyenda1 = estacion + codigo + responsable + estado;
									leyenda0 += "<br/>" + leyenda1;
								}
								switch (checkListE.getEstadoCheckList().getDescripcion()) {
									case "AGENDADA":
										tema = "schedule-agendada";
										break;
									case "EN EJECUCION":
										tema = "schedule-en-ejecucion";
										break;
									case "EJECUTADO":
										tema = "schedule-ejecutado";
										break;
									case "NO EJECUTADO":
										tema = "schedule-no-ejecutado";
										break;
									case "INCONCLUSO":
										tema = "schedule-inconcluso";
										break;
								}
							}
							String strDate = dateFormat.format(planificacion.getFechaPlanificacion());
							Date fechaD = dateFormat.parse(strDate);
							scheduleEventAllDay = new DefaultScheduleEvent(
									planificacion.getAgencia().getNombreAgencia(), fechaD, fechaD, tema);
							scheduleEventAllDay.setData(planificacion);
							scheduleEventAllDay.setId(String.valueOf(planificacion.getIdPlanificacion()));
							scheduleEventAllDay.setDescription(leyenda0);
							scheduleEventAllDay.setAllDay(true);
							eventModel.addEvent(scheduleEventAllDay);
						}
					} catch (EntidadNoEncontradaException | ParseException e) {
						e.printStackTrace();
						System.out.println("Error :Método cargarCheckList " + " " + e.getMessage());
					}
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método inicializarCalendario " + " " + e.getMessage());
		}
	}

	public void iniCalendarInventario() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			eventModel = new LazyScheduleModel() {
				private static final long serialVersionUID = 1L;

				@Override
				public void loadEvents(Date desde, Date hasta) {
					try {

						UsuarioEt usuario = appMain.getUsuario();
						planificacionesIventario = iPlanificacionInventarioDao.getPlanificacionInventarioList(desde,
								hasta, usuario);
						DefaultScheduleEvent scheduleEventAllDay;
						String estado = "";
						String estacion = "";
						String inventario = "";
						String responsable = "RESPONSABLE:" + " ";
						for (PlanificacionInventarioEt planificacion : planificacionesIventario) {
							String leyenda0 = "";
							String tema = "schedule-agendada";
							estado = "ESTADO:" + " " + planificacion.getEstadoInventario().getDescripcion() + "<br/>";
							estacion = planificacion.getAgencia().getNombreAgencia() + "<br/>";
							for (PlanificacionResponsableEt responsableC : planificacion
									.getPlanificacionResponsable()) {
								responsable += responsableC.getUsuarioResponsable().getPersonaUsuario()
										.getNombreCompleto() + "<br/>";
								;
							}
							leyenda0 = estacion + responsable + estado + inventario;
							switch (planificacion.getEstadoInventario().getDescripcion()) {
								case "AGENDADA":
									tema = "schedule-agendada";
									break;
								case "EN EJECUCION":
									tema = "schedule-en-ejecucion";
									break;
								case "EJECUTADO":
									tema = "schedule-ejecutado";
									break;
								case "NO EJECUTADO":
									tema = "schedule-no-ejecutado";
									break;
								case "INCONCLUSO":
									tema = "schedule-inconcluso";
									break;
							}

							String strDate = dateFormat.format(planificacion.getFechaPlanificacion());
							Date fechaD = dateFormat.parse(strDate);
							scheduleEventAllDay = new DefaultScheduleEvent(
									planificacion.getAgencia().getNombreAgencia(), fechaD, fechaD, tema);
							scheduleEventAllDay.setData(planificacion);
							scheduleEventAllDay.setId(String.valueOf(planificacion.getIdPlanificacionInventario()));
							scheduleEventAllDay.setDescription(leyenda0);
							scheduleEventAllDay.setAllDay(true);
							eventModel.addEvent(scheduleEventAllDay);
						}

					} catch (EntidadNoEncontradaException | ParseException e) {
						e.printStackTrace();
						System.out.println("Error :Método cargarCheckList " + " " + e.getMessage());
					}
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método inicializarCalendario " + " " + e.getMessage());
		}
	}

//	public void onEventSelect(SelectEvent selectEvent) {
//		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//		event = (ScheduleEvent) selectEvent.getObject();
//		planificacionSeleccionada = (PlanificacionEt) event.getData();
//		Date fechaEjecucion = planificacionSeleccionada.getFechaPlanificacion();
//		String fechaActual = dateFormat.format(new Date());
//		String fechaEjecucionS = dateFormat.format(fechaEjecucion.getTime());
//		if (!fechaActual.equals(fechaEjecucionS)) {
//			showInfo("CheckList solo puede ser ejecutado en la fecha planificada.", FacesMessage.SEVERITY_INFO, null,
//					"");
//		}
//	}
	public void onEventSelect(SelectEvent selectEvent) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		event = (ScheduleEvent) selectEvent.getObject();
		PlanificacionInventarioEt planificacion = null;
		Date fechaEjecucion = null;
		if (visualizar) {
			planificacion = (PlanificacionInventarioEt) event.getData();
			planificacionInventarioSeleccionada = iPlanificacionInventarioDao
					.getPlanificacionInventarioById(planificacion.getIdPlanificacionInventario());
			fechaEjecucion = planificacionInventarioSeleccionada.getFechaPlanificacion();
			estacionSeleccionada = planificacionInventarioSeleccionada.getAgencia();
			for (PlanificacionInventarioTipoEt planificacionInv : planificacionInventarioSeleccionada
					.getPlanificacionInventarioTipo()) {
				if (planificacionInv.isEjecutado()) {
					tipoInventarioSeleccionados.add(planificacionInv.getTipoInventario());
				}
			}
		} else {
			planificacionSeleccionada = (PlanificacionEt) event.getData();
			estacionSeleccionada = planificacionSeleccionada.getAgencia();
			fechaEjecucion = planificacionSeleccionada.getFechaPlanificacion();
		}
		fechaPlanificacion = fechaEjecucion;
		String fechaActual = dateFormat.format(new Date());
		String fechaEjecucionS = dateFormat.format(fechaEjecucion.getTime());
		if (!fechaActual.equals(fechaEjecucionS)) {
			showInfo("CheckList solo puede ser ejecutado en la fecha planificada.", FacesMessage.SEVERITY_INFO, null,
					"");
		}
	}

	public boolean validarFecha() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaEjecucion = planificacionSeleccionada.getFechaPlanificacion();
		String fechaActual = dateFormat.format(new Date());
		String fechaEjecucionS = dateFormat.format(fechaEjecucion.getTime());
		if (!fechaActual.equals(fechaEjecucionS)) {
			return true;
		} else {
			return false;
		}
	}

	public void modificarCheckListEjecucion(CheckListEjecucionEt checkListEjecucion) {
		try {
			checkListEjecucionSeleccionado = checkListEjecucion;
			if (checkListEjecucionSeleccionado.getEstadoCheckList().equals(EstadoCheckListEnum.EN_EJECUCION)) {
				redireccionar();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método modificar " + " " + e.getMessage());
		}
	}

	public void redireccionar() {
		String pagina = "";
		try {
			UsuarioEt usuario = appMain.getUsuario();
			List<CheckListEjecucionEt> checkListEjecutando = iCheckListEjecucionDao.getCheckEjecutando(usuario);
			if (checkListEjecutando != null && !checkListEjecutando.isEmpty()) {
				for (CheckListEjecucionEt checkListEjecucionC : checkListEjecutando) {
					checkListEjecucionC.setEjecutando(false);
					iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucionC, usuario);
				}
			}
			if (checkListEjecucionSeleccionado != null) {
				checkListEjecucionSeleccionado.setEjecutando(true);
				FacesContext contex = FacesContext.getCurrentInstance();
				EvaluacionEt evaluacion = checkListEjecucionSeleccionado.getEvaluacion();
				if (evaluacion != null) {
					String codigo = evaluacion.isCriterio() == true ? "1" : "2";
					switch (codigo) {
						case "1":
							pagina = "/PrimaxEvaluacion/pages/ejecucion/eje_001.xhtml";
							break;
						case "2":
							pagina = "/PrimaxEvaluacion/pages/ejecucion/eje_002.xhtml";
							break;
						default:
							break;
					}
				}
				if (checkListEjecucionSeleccionado.getEstadoCheckList().equals(EstadoCheckListEnum.AGENDADA)) {
					List<ResponsableEt> responsables = iResponsableDao.getResponsableByAgencia1List(
							checkListEjecucionSeleccionado.getPlanificacion().getAgencia());
					if (responsables != null && !responsables.isEmpty()) {
						iCheckListEjecucionFirmaDao.eliminarCheckListEjecucionFirma(
								checkListEjecucionSeleccionado.getCheckListEjecucionFirma(), usuario);
						iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucionSeleccionado, usuario);
						for (ResponsableEt responsable : responsables) {
							if (responsable.getTipoCargo().getDescripcion().equals("GERENTE")
									|| responsable.getTipoCargo().getDescripcion().equals("SOPORTE OPERATIVO")) {
								CheckListEjecucionFirmaEt checkListEjecucionFirma = new CheckListEjecucionFirmaEt();
								Long orden = responsable.getTipoCargo().getDescripcion().equals("GERENTE") ? 2L : 1L;
								checkListEjecucionFirma.setOrden(orden);
								checkListEjecucionFirma.setFirmado(false);
								checkListEjecucionFirma.setIdPersona(responsable.getPersona().getIdPersona());
								checkListEjecucionFirma.setCheckListEjecucion(checkListEjecucionSeleccionado);
								checkListEjecucionFirma.setCargo(responsable.getTipoCargo().getDescripcion());
								checkListEjecucionFirma.setNombre(responsable.getPersona().getNombreCompleto());
								checkListEjecucionFirma
										.setIdentificacion(responsable.getPersona().getIdentificacionPersona());
								if (responsable.getPersona().getFirma() != null) {
									checkListEjecucionFirma.setContieneFirma(true);
									checkListEjecucionFirma.setFirma(responsable.getPersona().getFirma());
								} else {
									checkListEjecucionFirma.setContieneFirma(false);
								}
								if (checkListEjecucionSeleccionado.getCheckListEjecucionFirma() == null
										|| checkListEjecucionSeleccionado.getCheckListEjecucionFirma().isEmpty()) {
									checkListEjecucionSeleccionado.setCheckListEjecucionFirma(new ArrayList<>());
								}
								checkListEjecucionSeleccionado.getCheckListEjecucionFirma()
										.add(checkListEjecucionFirma);
							}
						}
						CheckListEjecucionFirmaEt checkListEjecucionFirma = new CheckListEjecucionFirmaEt();
						UsuarioEt usuarioA = checkListEjecucionSeleccionado.getUsuarioAsignado();
						checkListEjecucionFirma.setOrden(0L);
						checkListEjecucionFirma.setFirmado(false);
						checkListEjecucionFirma.setCargo("Verificador/Control Interno");
						checkListEjecucionFirma.setCheckListEjecucion(checkListEjecucionSeleccionado);
						checkListEjecucionFirma.setIdPersona(usuarioA.getPersonaUsuario().getIdPersona());
						checkListEjecucionFirma.setNombre(usuarioA.getPersonaUsuario().getNombreCompleto());
						checkListEjecucionFirma
								.setIdentificacion(usuarioA.getPersonaUsuario().getIdentificacionPersona());
						if (usuarioA.getPersonaUsuario().getFirma() != null) {
							checkListEjecucionFirma.setContieneFirma(true);
							checkListEjecucionFirma.setFirma(usuarioA.getPersonaUsuario().getFirma());
						} else {
							checkListEjecucionFirma.setContieneFirma(true);
						}
						checkListEjecucionSeleccionado.getCheckListEjecucionFirma().add(checkListEjecucionFirma);
					}
				}
				checkListEjecucionSeleccionado.setFechaEjecucion(new Date());
				checkListEjecucionSeleccionado.setEstadoCheckList(EstadoCheckListEnum.EN_EJECUCION);
				iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucionSeleccionado, usuario);
				contex.getExternalContext().redirect(pagina);
			}
		} catch (

		Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método redireccionar " + " " + e.getMessage());
		}
	}

	public void guardar() {
		try {
			redireccionarGerencia();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}

	}

	public void guardarInv() {
		Long check0 = 0L;
		Long check1 = 0L;
		try {
			UsuarioEt usuario = appMain.getUsuario();
			for (TipoInventarioEt tipoInv : tipoInventarioSeleccionados) {
				PlanificacionInventarioTipoEt tipo = iPlanificacionInventarioTipoDao
						.getPlanificacionInv(planificacionInventarioSeleccionada, tipoInv);
				tipo.setEjecutado(true);
				iPlanificacionInventarioTipoDao.guardarPlanificacionInv(tipo, usuario);
				planificacionInventarioSeleccionada.setEstadoInventario(EstadoCheckListEnum.EN_EJECUCION);
			}
			check0 = iPlanificacionInventarioTipoDao.getPlaInvList(planificacionInventarioSeleccionada);
			check1 = (long) planificacionInventarioSeleccionada.getPlanificacionInventarioTipo().size();
			if (check0 == check1) {
				planificacionInventarioSeleccionada.setEstadoInventario(EstadoCheckListEnum.EJECUTADO);
			}
			iPlanificacionInventarioDao.guardarPlanificacionInventario(planificacionInventarioSeleccionada, usuario);
			RequestContext.getCurrentInstance().execute("PF('dlg_pln_003').hide();");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarInv " + " " + e.getMessage());
		}
	}

	public void redireccionarGerencia() {
		String pagina = "";
		try {
			UsuarioEt usuario = appMain.getUsuario();
			ResponsableEt responsable = iResponsableDao.getResponsableEstacion(usuario.getPersonaUsuario());
			List<CheckListEjecucionEt> checkListEjecutando = iCheckListEjecucionDao
					.getCheckListIngresandoPlanAccion(responsable.getAgencia());
			if (checkListEjecutando != null && !checkListEjecutando.isEmpty()) {
				for (CheckListEjecucionEt checkListEjecucionC : checkListEjecutando) {
					checkListEjecucionC.setPlanAccion(false);
					iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucionC, usuario);
				}
			}
			checkListEjecucionSeleccionado.setPlanAccion(true);
			FacesContext contex = FacesContext.getCurrentInstance();
			EvaluacionEt evaluacion = checkListEjecucionSeleccionado.getEvaluacion();
			if (evaluacion != null) {
				String codigo = evaluacion.isCriterio() == true ? "1" : "2";
				switch (codigo) {
					case "1":
						pagina = "/PrimaxEvaluacion/pages/gerencia/ger_002.xhtml";
						break;
					case "2":
						pagina = "/PrimaxEvaluacion/pages/gerencia/ger_003.xhtml";
						break;
					default:
						break;
				}
				iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucionSeleccionado, usuario);
				contex.getExternalContext().redirect(pagina);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método redireccionar" + " " + " " + e.getMessage());
		}

	}

	public void buscar() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			ResponsableEt responsable = iResponsableDao.getResponsableEstacion(usuario.getPersonaUsuario());
			checkListEjecuciones = iCheckListEjecucionDao.getCheckListPlanAccion(responsable.getAgencia());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}
	}

	public void modificar(CheckListEjecucionEt checkListEjecucion) {
		checkListEjecucionSeleccionado = checkListEjecucion;
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
			if (evaluacionSeleccionado != null) {
				tipoChecList = iTipoChecListDao.getTipoCheckListByEvaluacion(evaluacionSeleccionado);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTiposChecKList " + " " + e.getMessage());
		}
		return tipoChecList;
	}

	public List<AgenciaEt> getAgenciaList() {
		List<AgenciaEt> agencias = new ArrayList<AgenciaEt>();
		try {
			agencias = iAgenciaDao.getAgencias(EstadoEnum.ACT);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getAgenciaList " + " " + e.getMessage());
		}
		return agencias;
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

	public Date sumarRestarDiasFecha(Date fecha, int dias) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.DAY_OF_YEAR, dias);
		return calendar.getTime();
	}

	public Date sumarRestarMesesFecha(Date fecha, int meses) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.MONTH, meses);
		return calendar.getTime();
	}

	public boolean containsRol(final List<RolUsuarioEt> list, final RolEt rol) {
		return list.stream().filter(o -> o.getRol().equals(rol)).findFirst().isPresent();
	}

	public AppMain getAppMain() {
		return appMain;
	}

	public void setAppMain(AppMain appMain) {
		this.appMain = appMain;
	}

	public LocalDateTime getRandomDateTime(LocalDateTime base) {
		LocalDateTime dateTime = base.withMinute(0).withSecond(0).withNano(0);
		return dateTime.plusDays(((int) (Math.random() * 30)));
	}

	public void onEventResize(ScheduleEntryResizeEvent event) {

	}

	public PlanificacionEt getPlanificacionSeleccionada() {
		return planificacionSeleccionada;
	}

	public void setPlanificacionSeleccionada(PlanificacionEt planificacionSeleccionada) {
		this.planificacionSeleccionada = planificacionSeleccionada;
	}

	public AgenciaEt getAgenciaSeleccionada() {
		return agenciaSeleccionada;
	}

	public void setAgenciaSeleccionada(AgenciaEt agenciaSeleccionada) {
		this.agenciaSeleccionada = agenciaSeleccionada;
	}

	public EvaluacionEt getEvaluacionSeleccionado() {
		return evaluacionSeleccionado;
	}

	public void setEvaluacionSeleccionado(EvaluacionEt evaluacionSeleccionado) {
		this.evaluacionSeleccionado = evaluacionSeleccionado;
	}

	public CheckListEjecucionEt getCheckListEjecucionSeleccionado() {
		return checkListEjecucionSeleccionado;
	}

	public void setCheckListEjecucionSeleccionado(CheckListEjecucionEt checkListEjecucionSeleccionado) {
		this.checkListEjecucionSeleccionado = checkListEjecucionSeleccionado;
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}

	public boolean isTipoAuditor() {
		return tipoAuditor;
	}

	public void setTipoAuditor(boolean tipoAuditor) {
		this.tipoAuditor = tipoAuditor;
	}

	public boolean isTipoGerente() {
		return tipoGerente;
	}

	public void setTipoGerente(boolean tipoGerente) {
		this.tipoGerente = tipoGerente;
	}

	public List<CheckListEjecucionEt> getCheckListEjecuciones() {
		return checkListEjecuciones;
	}

	public void setCheckListEjecuciones(List<CheckListEjecucionEt> checkListEjecuciones) {
		this.checkListEjecuciones = checkListEjecuciones;
	}

	public PlanificacionInventarioEt getPlanificacionInventarioSeleccionada() {
		return planificacionInventarioSeleccionada;
	}

	public void setPlanificacionInventarioSeleccionada(PlanificacionInventarioEt planificacionInventarioSeleccionada) {
		this.planificacionInventarioSeleccionada = planificacionInventarioSeleccionada;
	}

	public boolean isVisualizar() {
		return visualizar;
	}

	public void setVisualizar(boolean visualizar) {
		visualizarCal(visualizar);
		this.visualizar = visualizar;
	}

	public List<TipoInventarioEt> getTipoInventarioSeleccionados() {
		return tipoInventarioSeleccionados;
	}

	public void setTipoInventarioSeleccionados(List<TipoInventarioEt> tipoInventarioSeleccionados) {
		this.tipoInventarioSeleccionados = tipoInventarioSeleccionados;
	}

	public AgenciaEt getEstacionSeleccionada() {
		return estacionSeleccionada;
	}

	public void setEstacionSeleccionada(AgenciaEt estacionSeleccionada) {
		this.estacionSeleccionada = estacionSeleccionada;
	}

	public EvaluacionEt getEvaluacionSeleccionada() {
		return evaluacionSeleccionada;
	}

	public void setEvaluacionSeleccionada(EvaluacionEt evaluacionSeleccionada) {
		this.evaluacionSeleccionada = evaluacionSeleccionada;
	}

	public Date getFechaPlanificacion() {
		return fechaPlanificacion;
	}

	public void setFechaPlanificacion(Date fechaPlanificacion) {
		this.fechaPlanificacion = fechaPlanificacion;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Override
	protected void onDestroy() {
		iRolEtDao.remove();
		iUsuarioDao.remove();
		iAgenciaDao.remove();
		iEvaluacionDao.remove();
		iResponsableDao.remove();
		iTipoChecListDao.remove();
		iPlanificacionDao.remove();
		iCheckListEjecucionDao.remove();
		iPlanificacionInventarioDao.remove();
		iCheckListEjecucionFirmaDao.remove();
		iPlanificacionInventarioTipoDao.remove();
	}

}
