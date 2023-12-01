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

import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.jpa.enums.EstadoCheckListEnum;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.param.ResponsableEt;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.pla.CheckListEjecucionEt;
import com.primax.jpa.pla.PlanificacionEt;
import com.primax.jpa.sec.RolEt;
import com.primax.jpa.sec.RolUsuarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IAgenciaDao;
import com.primax.srv.idao.ICheckListEjecucionDao;
import com.primax.srv.idao.IEvaluacionDao;
import com.primax.srv.idao.IPlanificacionDao;
import com.primax.srv.idao.IResponsableDao;
import com.primax.srv.idao.IRolEtDao;
import com.primax.srv.idao.ITipoChecKListDao;

@Named("FirmaCheckListBn")
@ViewScoped
public class FirmaCheckListBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = -734535679991041247L;

	@EJB
	private IRolEtDao iRolEtDao;
	@EJB
	private IAgenciaDao iAgenciaDao;
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

	private List<PlanificacionEt> planificaciones;
	private List<CheckListEjecucionEt> checkListEjecuciones;
	private CheckListEjecucionEt checkListEjecucionSeleccionado;

	@Inject
	private AppMain appMain;

	/**
	 * Variables Calendario
	 */
	private ScheduleModel eventModel;
	private ScheduleEvent event = new DefaultScheduleEvent();

	private boolean tipoAuditor = false;
	private boolean tipoGerente = false;
	private AgenciaEt agenciaSeleccionada;
	private EvaluacionEt evaluacionSeleccionado;
	private EvaluacionEt evaluacionSeleccionada;
	private TipoChecKListEt tipoChecListSeleccionado;
	private PlanificacionEt planificacionSeleccionada;
	private TipoChecKListEt tipoChecKListSeleccionado;

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
		planificacionSeleccionada = new PlanificacionEt();
		event = new DefaultScheduleEvent();
		inicializarCalendario();

	}

	public void inicializarCalendario() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			eventModel = new LazyScheduleModel() {
				private static final long serialVersionUID = 1L;

				@Override
				public void loadEvents(Date desde, Date hasta) {
					try {
						UsuarioEt usuario = appMain.getUsuario();
						planificaciones = iPlanificacionDao.getPlanificacionList(evaluacionSeleccionada,
								tipoChecKListSeleccionado, desde, hasta, usuario);
						DefaultScheduleEvent scheduleEventAllDay;
						for (PlanificacionEt planificacion : planificaciones) {
							String tema = "schedule-blue";
							for (CheckListEjecucionEt checkListEjecucion : planificacion.getCheckListEjecucion()) {
								switch (checkListEjecucion.getEstadoCheckList().getDescripcion()) {
								case "AGENDADA":
									tema = "schedule-blue";
									break;
								case "EN_EJECUCION":
									tema = "schedule-green";
									break;
								case "EJECUTADO":
									tema = "schedule-red";
									break;
								case "NO_EJECUTADO":
									tema = "schedule-red";
									break;
								}
							}
							String strDate = dateFormat.format(planificacion.getFechaPlanificacion());
							Date fechaD = dateFormat.parse(strDate);
							scheduleEventAllDay = new DefaultScheduleEvent(
									planificacion.getAgencia().getNombreAgencia(), fechaD, fechaD, tema);
							scheduleEventAllDay.setData(planificacion);
							scheduleEventAllDay.setId(String.valueOf(planificacion.getIdPlanificacion()));
							scheduleEventAllDay.setDescription(planificacion.getAgencia().getNombreAgencia());
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

	public void onEventSelect(SelectEvent selectEvent) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		event = (ScheduleEvent) selectEvent.getObject();
		planificacionSeleccionada = (PlanificacionEt) event.getData();
		Date fechaEjecucion = planificacionSeleccionada.getFechaPlanificacion();
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
				checkListEjecucionSeleccionado.setFechaEjecucion(new Date());
				checkListEjecucionSeleccionado.setEstadoCheckList(EstadoCheckListEnum.EN_EJECUCION);
				iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucionSeleccionado, usuario);
				contex.getExternalContext().redirect(pagina);
			}
		} catch (Exception e) {
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

	public TipoChecKListEt getTipoChecListSeleccionado() {
		return tipoChecListSeleccionado;
	}

	public void setTipoChecListSeleccionado(TipoChecKListEt tipoChecListSeleccionado) {
		this.tipoChecListSeleccionado = tipoChecListSeleccionado;
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

	@Override
	protected void onDestroy() {
		iRolEtDao.remove();
		iAgenciaDao.remove();
		iEvaluacionDao.remove();
		iResponsableDao.remove();
		iTipoChecListDao.remove();
		iPlanificacionDao.remove();
		iCheckListEjecucionDao.remove();
	}

}
