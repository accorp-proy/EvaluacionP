package com.primax.bean.vs;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
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

import org.primefaces.context.RequestContext;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.primefaces.model.TreeNode;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.enm.gen.MessageType;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.jpa.enums.EstadoCheckListEnum;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.AgenciaCheckListEt;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.CorreoEt;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.param.NivelEvaluacionEt;
import com.primax.jpa.param.ResponsableEt;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.param.ZonaEt;
import com.primax.jpa.param.ZonaUsuarioEt;
import com.primax.jpa.pla.CheckListEjecucionEt;
import com.primax.jpa.pla.CheckListEjecucionReporteEt;
import com.primax.jpa.pla.CheckListEt;
import com.primax.jpa.pla.CheckListKpiEjecucionEt;
import com.primax.jpa.pla.CheckListKpiEt;
import com.primax.jpa.pla.CheckListProcesoEjecucionEt;
import com.primax.jpa.pla.CheckListProcesoEt;
import com.primax.jpa.pla.PlanificacionAuditorEt;
import com.primax.jpa.pla.PlanificacionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IAgenciaCheckListDao;
import com.primax.srv.idao.IAgenciaDao;
import com.primax.srv.idao.ICheckListDao;
import com.primax.srv.idao.ICheckListEjecucionDao;
import com.primax.srv.idao.ICheckListProcesoDao;
import com.primax.srv.idao.ICorreoDao;
import com.primax.srv.idao.IEvaluacionDao;
import com.primax.srv.idao.INivelEvaluacionDao;
import com.primax.srv.idao.IPlanificacionDao;
import com.primax.srv.idao.IResponsableDao;
import com.primax.srv.idao.ITipoChecKListDao;
import com.primax.srv.idao.IUsuarioDao;
import com.primax.srv.idao.IZonaDao;
import com.primax.srv.util.msg.MessageCenter;
import com.primax.srv.util.msg.MessageFactory;

@Named("PlanificacionBn")
@ViewScoped
public class PlanificacionBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IZonaDao iZonaDao;
	@EJB
	private ICorreoDao iCorreoDao;
	@EJB
	private IUsuarioDao iUsuarioDao;
	@EJB
	private IAgenciaDao iAgenciaDao;
	@EJB
	private ICheckListDao iCheckListDao;
	@EJB
	private IEvaluacionDao iEvaluacionDao;
	@EJB
	private IResponsableDao iResponsableDao;
	@EJB
	private ITipoChecKListDao iTipoChecKListDao;
	@EJB
	private IPlanificacionDao iPlanificacionDao;
	@EJB
	private INivelEvaluacionDao iNivelEvaluacionDao;
	@EJB
	private IAgenciaCheckListDao iAgenciaCheckListDao;
	@EJB
	private ICheckListProcesoDao iCheckListProcesoDao;
	@EJB
	private ICheckListEjecucionDao iCheckListEjecucionDao;

	private TreeNode selectedNode;
	private boolean tooltip = true;
	private boolean bloqueo = true;
	private ZonaEt zonaSeleccionada;
	private ScheduleModel eventModel;
	private TreeNode[] selectedNodes;
	private UsuarioEt usuarioSeleccionado;
	private AgenciaEt estacionSeleccionada;
	private CheckListEt checkListSeleccionado;
	private EvaluacionEt evaluacionSeleccionada;
	private List<UsuarioEt> usuarioSeleccionados;
	private List<PlanificacionEt> planificaciones;
	private List<CheckListEt> checkListHabilitados;
	private TipoChecKListEt tipoChecKListSeleccionado;
	private PlanificacionEt planificacionSeleccionada;
	private NivelEvaluacionEt nivelEvaluacionSeleccionado;
	private AgenciaCheckListEt agenciaCheckListSeleccionado;
	private ScheduleEvent event = new DefaultScheduleEvent();
	private CheckListEjecucionEt checkListEjecucionSeleccionado;
	private TreeNode nodoPrincipal = new DefaultTreeNode(new CheckListEt(), null);
	private List<CheckListProcesoEjecucionEt> checkListProcesoEjecucionSeleccionados;

	@Inject
	private AppMain appMain;

	@Override
	protected void init() {
		inicializarObj();
		buscar();
	}

	public void inicializarObj() {
		zonaSeleccionada = null;
		usuarioSeleccionado = null;
		estacionSeleccionada = null;
		evaluacionSeleccionada = null;
		tipoChecKListSeleccionado = null;
		event = new DefaultScheduleEvent();
		usuarioSeleccionados = new ArrayList<>();
		checkListHabilitados = new ArrayList<>();
		planificacionSeleccionada = new PlanificacionEt();
		checkListProcesoEjecucionSeleccionados = new ArrayList<>();
		planificacionSeleccionada.setCheckListEjecucion(new ArrayList<>());
		planificacionSeleccionada.setPlanificacionAuditor(new ArrayList<>());
	}

	public void guardar() {
		String mensaje = "";
		try {
			planificacionSeleccionada.setAgencia(estacionSeleccionada);
			mensaje = validarGuardar();
			UsuarioEt usuario = appMain.getUsuario();
			if (mensaje.equals("")) {
				planificacionSeleccionada.setFechaFin(planificacionSeleccionada.getFechaPlanificacion());
				planificacionSeleccionada.setFechaInicio(planificacionSeleccionada.getFechaPlanificacion());
				if (planificacionSeleccionada.isEliminada()) {
					planificacionSeleccionada.setEstado(EstadoEnum.INA);
				} else {
					enviarEmail(planificacionSeleccionada);
				}
				iPlanificacionDao.guardarPlanificacion(planificacionSeleccionada, usuario);
				RequestContext.getCurrentInstance().execute("PF('dlg_pln_002').hide();");
				inicializarObj();
				buscar();
			} else {
				showInfo("Notificación", FacesMessage.SEVERITY_INFO, null, mensaje);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}
	}

	public void enviarEmail(PlanificacionEt planificacion) {
		String email = "";
		String email0 = "";
		String email1 = "";
		StringBuilder recipient = new StringBuilder();
		try {
			CorreoEt config = iCorreoDao.getCorreoExiste(1L);
			MessageFactory msg = new MessageFactory(MessageType.MAIL);
			MessageCenter msc = msg.getMessenger();
			msc.setSubject(planificacion.getAgencia().getNombreAgencia() + " :Visita de Control Interno");
			msc.setFrom("notificacionControlInterno@atimasa.com.ec");
			msc.setMessage(mensajeCorreo(planificacion));
			email0 = appMain.getUsuario().getPersonaUsuario().getEmail();
			for (PlanificacionAuditorEt planificacionAuditor : planificacionSeleccionada.getPlanificacionAuditor()) {
				if (planificacionAuditor.getUsuarioAuditor().getPersonaUsuario().getEmail() != null) {
					email = planificacionAuditor.getUsuarioAuditor().getPersonaUsuario().getEmail() + ",";
					recipient.append(email);
				}
			}
			email1 = recipient.toString() + email0;
			System.out.println(email1);
		    //msc.setRecipient("acorrea@accorp.com.ec,jeffersonmaji@hotmail.com");
			msc.setRecipient(email1);
			msc.setConfig(config);
			msc.sendMessage();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método enviarEmail " + " " + e.getMessage());
		}
	}

	public String validarGuardar() {
		String mensaje = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
		try {
			Date fecha = new Date();
			String fechaActualS = sdf.format(fecha);
			String fechaPlanificacionS = sdf.format(planificacionSeleccionada.getFechaPlanificacion());
			Date fechaActual = sdf.parse(fechaActualS);
			Date fechaPlanificacion = sdf.parse(fechaPlanificacionS);
			if (fechaPlanificacion.before(fechaActual)) {
				mensaje = "Fecha Planificación debe ser mayor o igual a la fecha Actual";
				return mensaje;
			}
			if (planificacionSeleccionada.getAgencia() == null) {
				mensaje = "Por favor seleccionar una estación para continuar";
				return mensaje;
			}
			if (usuarioSeleccionados.isEmpty()) {
				mensaje = "Por favor seleccionar auditores para continuar ";
				return mensaje;
			} else {
				planificacionSeleccionada.setPlanificacionAuditor(new ArrayList<>());
				for (UsuarioEt usuario : usuarioSeleccionados) {
					PlanificacionAuditorEt planificacionAuditor = new PlanificacionAuditorEt();
					planificacionAuditor.setUsuarioAuditor(usuario);
					planificacionAuditor.setPlanificacion(planificacionSeleccionada);
					planificacionSeleccionada.getPlanificacionAuditor().add(planificacionAuditor);
				}
			}
			if (planificacionSeleccionada.getCheckListEjecucion() == null
					|| planificacionSeleccionada.getCheckListEjecucion().isEmpty()) {
				mensaje = "Por favor agregar checkList de controles para continuar ";
				return mensaje;
			}
			for (CheckListEjecucionEt checkList : planificacionSeleccionada.getCheckListEjecucion()) {
				if (checkList.getEstadoCheckList().equals(EstadoCheckListEnum.EN_EJECUCION)
						|| checkList.getEstadoCheckList().equals(EstadoCheckListEnum.EJECUTADO)) {
					mensaje = "No puede modificar porque ya se encuentra realizando la visita de control ";
					return mensaje;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método validarGuardar " + " " + e.getMessage());
		}
		return mensaje;
	}

	public void guardarAuditor() {
		try {
			checkListSeleccionado.setUsuarioAsignado(usuarioSeleccionado);
			checkListSeleccionado.setNombreAuditor(usuarioSeleccionado.getNombreUsuario());
			RequestContext.getCurrentInstance().execute("PF('dlg_pln_004').hide();");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarAuditor " + " " + e.getMessage());
		}
	}

	public void anadirCheckList() {
		String codigoEstacion = "";
		CheckListEt checkList = null;
		CheckListEjecucionEt checkListEjecucion = null;
		try {
			if (usuarioSeleccionado == null) {
				showInfo("Notificación", FacesMessage.SEVERITY_ERROR, null,
						"Por favor seleccione un auditor para continuar.");
				return;
			}
			if (planificacionSeleccionada.getCheckListEjecucion() == null) {
				planificacionSeleccionada.setCheckListEjecucion(new ArrayList<>());
			}
			if (!planificacionSeleccionada.getCheckListEjecucion().isEmpty()) {
				for (CheckListEjecucionEt checkListEjecucionV : planificacionSeleccionada.getCheckListEjecucion()) {
					if (checkListEjecucionV.getIdCheckList() == agenciaCheckListSeleccionado.getCheckList()
							.getIdCheckList()) {
						showInfo("Notificación", FacesMessage.SEVERITY_ERROR, null,
								"Ya se encuentra agregado CheckList.");
						return;
					}
				}
			}
			List<ResponsableEt> responsables = iResponsableDao.getResponsableByAgencia1List(estacionSeleccionada);
			if (responsables == null || responsables.isEmpty()) {
				showInfo("Notificación", FacesMessage.SEVERITY_ERROR, null,
						"La estación seleccionada no tiene agregados responables");
				return;
			}
			codigoEstacion = estacionSeleccionada.getCodigoAgencia();
			checkList = agenciaCheckListSeleccionado.getCheckList();
			checkListEjecucion = new CheckListEjecucionEt();
			checkListEjecucion.setCalificacion(0L);
			checkListEjecucion.setModificado(false);
			checkListEjecucion.setOrden(checkList.getOrden());
			checkListEjecucion.setEvaluacion(checkList.getEvaluacion());
			checkListEjecucion.setIdCheckList(checkList.getIdCheckList());
			checkListEjecucion.setPlanificacion(planificacionSeleccionada);
			checkListEjecucion.setTipoCheckList(checkList.getTipoCheckList());
			checkListEjecucion.setTipoChecKList(checkList.getTipoChecKList());
			checkListEjecucion.setVisualizarPeso(checkList.isVisualizarPeso());
			checkListEjecucion.setEstadoCheckList(EstadoCheckListEnum.AGENDADA);
			checkListEjecucion.setNivelEvaluacion(checkList.getNivelEvaluacion());
			checkListEjecucion.setUsuarioAsignado(checkList.getUsuarioAsignado());
			checkListEjecucion.setCodigo(checkList.getCodigo() + "-" + codigoEstacion);
			checkListEjecucion.setVisualizarComentario(checkList.isVisualizarComentario());
			checkListEjecucion.setCheckListEjecucionFirma(new ArrayList<>());
			checkListEjecucion.setCheckListProcesoEjecucion(new ArrayList<>());
			checkListEjecucion.setCheckListEjecucionReporte(new ArrayList<>());
			checkListEjecucion.setDescripcion(
					checkList.getEvaluacion().getDescripcion() + "-" + checkList.getTipoChecKList().getDescripcion());
			checkListEjecucion.setUsuarioAsignado(usuarioSeleccionado);
			for (CheckListProcesoEt checkListProceso : checkList.getCheckListProceso()) {
				CheckListProcesoEjecucionEt checkListProcesoEjecucion = new CheckListProcesoEjecucionEt();
				checkListProcesoEjecucion.setVisualizar(true);
				checkListProcesoEjecucion.setOrden(checkListProceso.getOrden());
				checkListProcesoEjecucion.setProceso(checkListProceso.getProceso());
				checkListProcesoEjecucion.setCheckListEjecucion(checkListEjecucion);
				checkListProcesoEjecucion.setCheckListKpiEjecucion(new ArrayList<>());
				checkListProcesoEjecucion.setDescripcion(checkListProceso.getDescripcion());
				checkListProcesoEjecucion.setIdCheckListProcesoEjecucionA(checkListProceso.getIdCheckListProceso());
				checkListEjecucion.getCheckListProcesoEjecucion().add(checkListProcesoEjecucion);
				for (CheckListKpiEt checkListKpi : checkListProceso.getCheckListKpi()) {
					CheckListKpiEjecucionEt checkListKpiEjecucion = new CheckListKpiEjecucionEt();
					checkListKpiEjecucion.setSumar(true);
					checkListKpiEjecucion.setVisualizar(true);
					checkListKpiEjecucion.setOrden(checkListKpi.getOrden());
					checkListKpiEjecucion.setPuntaje(checkListKpi.getPuntaje());
					checkListKpiEjecucion.setEsfuerzo(checkListKpi.getEsfuerzo());
					checkListKpiEjecucion.setDescripcion(checkListKpi.getDescripcion());
					checkListKpiEjecucion.setProcesoDetalle(checkListKpi.getProcesoDetalle());
					checkListKpiEjecucion.setCheckListProcesoEjecucion(checkListProcesoEjecucion);
					checkListKpiEjecucion.setCriterioEvaluacion(checkListKpi.getCriterioEvaluacion());
					if (checkListKpi.getkPICritico() != null) {
						checkListKpiEjecucion.setkPICritico(checkListKpi.getkPICritico());
					}
					checkListKpiEjecucion.setVisualizarReporte(checkListKpi.getProcesoDetalle().isVisualizarReporte());
					checkListProcesoEjecucion.getCheckListKpiEjecucion().add(checkListKpiEjecucion);
				}
			}
			if (checkList.getEvaluacion().isCriterio()) {
				CheckListEjecucionReporteEt checkListEjecucionReporte = new CheckListEjecucionReporteEt();
				checkListEjecucionReporte.setCheckListEjecucion(checkListEjecucion);
				checkListEjecucionReporte.setNombre(checkList.getTipoChecKList().getNombre());
				checkListEjecucionReporte.setSubTitulo(checkList.getTipoChecKList().getSubTitulo());
				checkListEjecucionReporte.setFechaReporte(planificacionSeleccionada.getFechaPlanificacion());
				checkListEjecucionReporte.setFechaVerificacion(planificacionSeleccionada.getFechaPlanificacion());
				checkListEjecucionReporte.setCodigo(codigoReporte(planificacionSeleccionada.getFechaPlanificacion(),
						checkList.getTipoChecKList().getCodigo()));
				checkListEjecucionReporte.setCodigoReplace(checkListEjecucionReporte.getCodigo());
				checkListEjecucionReporte.setDescripcion(checkList.getTipoChecKList().getDetalle().replace("{estacion}",
						estacionSeleccionada.getNombreAgencia()));
				checkListEjecucion.getCheckListEjecucionReporte().add(checkListEjecucionReporte);
			}

			planificacionSeleccionada.getCheckListEjecucion().add(checkListEjecucion);
			RequestContext.getCurrentInstance().execute("PF('dlg_pln_004').hide();");
			usuarioSeleccionado = null;
			agenciaCheckListSeleccionado = null;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método anadirCheckList " + " " + e.getMessage());
		}
	}

	public String codigoReporte(Date fecha, String codigoCheck) {
		String diaS = "";
		String mesS = "";
		String codigo = "";
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fecha);
			int dia = calendar.get(Calendar.DAY_OF_MONTH);
			int mes = calendar.get(Calendar.MONTH) + 1;
			int anio = calendar.get(Calendar.YEAR);
			if (dia < 10) {
				diaS = "0" + dia;
			} else {
				diaS = "" + dia;
			}
			if (mes < 10) {
				mesS = "0" + mes;
			} else {
				mesS = "" + mes;
			}
			codigo = codigoCheck + "-" + diaS + "-" + mesS + "-" + anio + "-" + "R";

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método codigoReporte " + " " + e.getMessage());
		}
		return codigo;
	}

	public void mostrarDialogAuditor() {
		if (usuarioSeleccionados.isEmpty()) {
			showInfo("Notificación", FacesMessage.SEVERITY_ERROR, null,
					"Por favor seleccione un auditor para continuar.");
		} else if (agenciaCheckListSeleccionado == null) {
			showInfo("Notificación", FacesMessage.SEVERITY_ERROR, null,
					"Por favor seleccione un checkList para continuar.");
		} else {
			getRequestContext().execute("PF('dlg_pln_004').show()");
		}

	}

	public void eliminarCheckListEjecucion(CheckListEjecucionEt checkListEjecucion) {
		try {
			planificacionSeleccionada.getCheckListEjecucion().remove(checkListEjecucion);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método eliminarCheckListEjecucion " + " " + e.getMessage());
		}
	}

	public void checkListProcesoEjecucionSeleccionado(CheckListEjecucionEt checkListEjecucion) {
		checkListEjecucionSeleccionado = checkListEjecucion;
		checkListProcesoEjecucionSeleccionados = new ArrayList<>();
		for (CheckListProcesoEjecucionEt checkListProcesoEjecucion : checkListEjecucionSeleccionado
				.getCheckListProcesoEjecucion()) {
			if (checkListProcesoEjecucion.isVisualizar()) {
				checkListProcesoEjecucionSeleccionados.add(checkListProcesoEjecucion);
			}
		}
	}

	public void guardarProcesos() {
		try {
			for (CheckListProcesoEjecucionEt checkListProcesoEjecucion : checkListEjecucionSeleccionado
					.getCheckListProcesoEjecucion()) {
				if (!containsProceso(checkListProcesoEjecucionSeleccionados,
						checkListProcesoEjecucion.getDescripcion())) {
					checkListProcesoEjecucion.setVisualizar(false);
				}
			}
			RequestContext.getCurrentInstance().execute("PF('dlg_pln_006').hide();");
			checkListProcesoEjecucionSeleccionados = new ArrayList<CheckListProcesoEjecucionEt>();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarProcesos " + " " + e.getMessage());
		}
	}

	public boolean containsProceso(final List<CheckListProcesoEjecucionEt> list, final String descripcion) {
		return list.stream().filter(o -> o.getDescripcion().equals(descripcion)).findFirst().isPresent();
	}

	public void buscar() {
		try {
			inicializarCalendario();
			// inicializarCalendario(planificaciones);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}
	}

	public void buscarHabilitados() {
		List<AgenciaCheckListEt> agenciaCheckList = new ArrayList<>();
		try {
			if (nivelEvaluacionSeleccionado != null && estacionSeleccionada != null && evaluacionSeleccionada != null) {
				estacionSeleccionada.setAgenciaCheckList(new ArrayList<>());
				agenciaCheckList = iAgenciaCheckListDao.getAgenciaCheckListHabilitados(estacionSeleccionada,
						nivelEvaluacionSeleccionado, evaluacionSeleccionada, tipoChecKListSeleccionado);
				estacionSeleccionada.setAgenciaCheckList(agenciaCheckList);
			} else {
				agenciaCheckList = iAgenciaCheckListDao.getAgenciaCheckListHabilitados(estacionSeleccionada, null, null,
						null);
				estacionSeleccionada.setAgenciaCheckList(agenciaCheckList);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscarHabilitados " + " " + e.getMessage());
		}
	}

	public void inicializarCalendario() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			eventModel = new LazyScheduleModel() {
				private static final long serialVersionUID = 1L;

				@Override
				public void loadEvents(Date desde, Date hasta) {
					try {
						planificaciones = iPlanificacionDao.getPlanificacionList(evaluacionSeleccionada,
								tipoChecKListSeleccionado, desde, hasta);
						DefaultScheduleEvent scheduleEventAllDay;
						String estado = "";
						String codigo = "";
						String estacion = "";
						String responsable = "";
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

	public void cargarCheckList() {
		try {
			checkListHabilitados = iCheckListDao.getCheckListChild(estacionSeleccionada);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método cargarCheckList " + " " + e.getMessage());
		}
	}

	public void buscarSiguiente() {
		try {
			// Date date =

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscarSiguiente " + " " + e.getMessage());
		}
	}

	public void onDateSelect(SelectEvent selectEvent) {
		try {
			bloqueo = false;
			inicializarObj();
			Date date = (Date) selectEvent.getObject();
			Calendar calendar0 = Calendar.getInstance();
			Calendar calendar1 = Calendar.getInstance();
			int hour = calendar1.get(Calendar.HOUR);
			int minute = calendar1.get(Calendar.MINUTE);
			int second = calendar1.get(Calendar.SECOND);
			int m = calendar1.get(Calendar.AM_PM);
			calendar1.setTime(new Date());
			calendar0.setTime(date);
			int month = calendar1.get(Calendar.MONTH);
			calendar0.add(Calendar.DATE, 1);
			calendar0.set(Calendar.HOUR, hour);
			calendar0.set(Calendar.MINUTE, minute);
			calendar0.set(Calendar.SECOND, second);
			calendar0.set(Calendar.MONTH, month);
			calendar0.set(Calendar.AM_PM, m);
			planificacionSeleccionada = new PlanificacionEt();
			planificacionSeleccionada.setPlanificacionAuditor(new ArrayList<>());
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa ");
			String strDate = dateFormat.format(calendar0.getTime());
			date = dateFormat.parse(strDate);
			planificacionSeleccionada.setFechaPlanificacion(date);
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println("Error :Método onDateSelect " + " " + e.getMessage());
		}
	}

	public void onEventSelect(SelectEvent selectEvent) {
		event = (ScheduleEvent) selectEvent.getObject();
		planificacionSeleccionada = (PlanificacionEt) event.getData();
		estacionSeleccionada = planificacionSeleccionada.getAgencia();
		bloqueo = true;
		for (PlanificacionAuditorEt planificacionAuditor : planificacionSeleccionada.getPlanificacionAuditor()) {
			usuarioSeleccionados.add(planificacionAuditor.getUsuarioAuditor());
		}
	}

	public void onEventResize(ScheduleEntryResizeEvent event) {
		System.out.println("Error :Método cargarCheckList " + " ");
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

	public List<AgenciaEt> getEstacionList() {
		List<AgenciaEt> agencias = new ArrayList<AgenciaEt>();
		try {
			UsuarioEt usuario = iUsuarioDao.getUsuarioId(appMain.getUsuario().getIdUsuario());
			if (usuario.isAccesoZona()) {
				agencias = iAgenciaDao.getAgenciaAccesoZona(usuario, zonaSeleccionada);
			} else {
				agencias = iAgenciaDao.getAgenciasByZona(zonaSeleccionada);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getAgenciaList " + " " + e.getMessage());
		}
		return agencias;
	}

	public List<UsuarioEt> getListAuditor() {
		List<UsuarioEt> auditores = new ArrayList<UsuarioEt>();
		try {
			auditores = iUsuarioDao.getUsuarioAuditorList();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getListAuditor" + " " + e.getMessage());
		}
		return auditores;
	}

	public TreeNode createDocuments() {
		nodoPrincipal = new DefaultTreeNode(new CheckListEt(), null);
		for (CheckListEt checkList : checkListHabilitados) {
			TreeNode checkListN = new DefaultTreeNode(checkList, nodoPrincipal);
			checkListN.setSelected(false);
			for (CheckListProcesoEt checkListProceso : checkList.getCheckListProceso()) {
				TreeNode work = new DefaultTreeNode(checkListProceso, checkListN);
				work.setSelected(false);
			}
		}
		return nodoPrincipal;
	}

	public void cargarCheckListHabilitado() {
		this.checkListHabilitados = iCheckListDao.getCheckListChild(estacionSeleccionada);
		createDocuments();
	}

	@SuppressWarnings("unused")
	public void cargarCheckListSeleccionado() {
		for (int i = 0; i < selectedNodes.length; i++) {
			CheckListEt checkList = (CheckListEt) selectedNodes[i].getData();
			checkListSeleccionado = checkList;
			getRequestContext().execute("PF('dlg_pln_004').show()");
			break;
		}
		if (checkListSeleccionado == null) {
			showInfo("Notificación", FacesMessage.SEVERITY_ERROR, null,
					"Por favor seleccione checkList para continuar.");
			RequestContext.getCurrentInstance().execute("PF('dlg_pln_004').hide();");
		}
		if (usuarioSeleccionados.isEmpty()) {
			showInfo("Notificación", FacesMessage.SEVERITY_ERROR, null,
					"Por favor seleccione un auditor para continuar.");
			RequestContext.getCurrentInstance().execute("PF('dlg_pln_004').hide();");
		}
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

	public TipoChecKListEt getTipoChecKListSeleccionado() {
		return tipoChecKListSeleccionado;
	}

	public void setTipoChecKListSeleccionado(TipoChecKListEt tipoChecKListSeleccionado) {
		this.tipoChecKListSeleccionado = tipoChecKListSeleccionado;
	}

	public List<PlanificacionEt> getPlanificaciones() {
		return planificaciones;
	}

	public void setPlanificaciones(List<PlanificacionEt> planificaciones) {
		this.planificaciones = planificaciones;
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}

	public PlanificacionEt getPlanificacionSeleccionada() {
		return planificacionSeleccionada;
	}

	public void setPlanificacionSeleccionada(PlanificacionEt planificacionSeleccionada) {
		this.planificacionSeleccionada = planificacionSeleccionada;
	}

	public AgenciaEt getEstacionSeleccionada() {
		return estacionSeleccionada;
	}

	public void setEstacionSeleccionada(AgenciaEt estacionSeleccionada) {
		this.estacionSeleccionada = estacionSeleccionada;
	}

	public TreeNode getNodoPrincipal() {
		return nodoPrincipal;
	}

	public void setNodoPrincipal(TreeNode nodoPrincipal) {
		this.nodoPrincipal = nodoPrincipal;
	}

	public List<CheckListEt> getCheckListHabilitados() {
		return checkListHabilitados;
	}

	public void setCheckListHabilitados(List<CheckListEt> checkListHabilitados) {
		this.checkListHabilitados = checkListHabilitados;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public TreeNode[] getSelectedNodes() {
		return selectedNodes;
	}

	public void setSelectedNodes(TreeNode[] selectedNodes) {
		this.selectedNodes = selectedNodes;
	}

	public boolean isTooltip() {
		return tooltip;
	}

	public void setTooltip(boolean tooltip) {
		this.tooltip = tooltip;
	}

	public List<UsuarioEt> getUsuarioSeleccionados() {
		return usuarioSeleccionados;
	}

	public void setUsuarioSeleccionados(List<UsuarioEt> usuarioSeleccionados) {
		this.usuarioSeleccionados = usuarioSeleccionados;
	}

	public UsuarioEt getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	public void setUsuarioSeleccionado(UsuarioEt usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
	}

	public CheckListEt getCheckListSeleccionado() {
		return checkListSeleccionado;
	}

	public void setCheckListSeleccionado(CheckListEt checkListSeleccionado) {
		this.checkListSeleccionado = checkListSeleccionado;
	}

	public EvaluacionEt getEvaluacionSeleccionada() {
		return evaluacionSeleccionada;
	}

	public void setEvaluacionSeleccionada(EvaluacionEt evaluacionSeleccionada) {
		this.evaluacionSeleccionada = evaluacionSeleccionada;
	}

	public AgenciaCheckListEt getAgenciaCheckListSeleccionado() {
		return agenciaCheckListSeleccionado;
	}

	public void setAgenciaCheckListSeleccionado(AgenciaCheckListEt agenciaCheckListSeleccionado) {
		this.agenciaCheckListSeleccionado = agenciaCheckListSeleccionado;
	}

	public CheckListEjecucionEt getCheckListEjecucionSeleccionado() {
		return checkListEjecucionSeleccionado;
	}

	public void setCheckListEjecucionSeleccionado(CheckListEjecucionEt checkListEjecucionSeleccionado) {
		this.checkListEjecucionSeleccionado = checkListEjecucionSeleccionado;
	}

	public List<CheckListProcesoEjecucionEt> getCheckListProcesoEjecucionSeleccionados() {
		return checkListProcesoEjecucionSeleccionados;
	}

	public void setCheckListProcesoEjecucionSeleccionados(
			List<CheckListProcesoEjecucionEt> checkListProcesoEjecucionSeleccionados) {
		this.checkListProcesoEjecucionSeleccionados = checkListProcesoEjecucionSeleccionados;
	}

	public boolean isBloqueo() {
		return bloqueo;
	}

	public void setBloqueo(boolean bloqueo) {
		this.bloqueo = bloqueo;
	}

	public NivelEvaluacionEt getNivelEvaluacionSeleccionado() {
		return nivelEvaluacionSeleccionado;
	}

	public void setNivelEvaluacionSeleccionado(NivelEvaluacionEt nivelEvaluacionSeleccionado) {
		this.nivelEvaluacionSeleccionado = nivelEvaluacionSeleccionado;
	}

	public String mensajeCorreo(PlanificacionEt planificacion) {
		StringBuilder msj = new StringBuilder("Estimado Auditor <br/><br/> ");
		DateFormat fechaFormat = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat horaFormat = new SimpleDateFormat("HH:mm aa");
		try {
			String fechaS = fechaFormat.format(planificacion.getFechaPlanificacion());
			String horaS = horaFormat.format(planificacion.getFechaPlanificacion());
			String estacion = planificacion.getAgencia().getNombreAgencia();
			String tipoEstacion = planificacion.getAgencia().getTipoEstacion().getDescripcion();
			String categoria = planificacion.getAgencia().getTipoEstacion().getTipoCategoriaEstacion().get(0)
					.getCategoriaEstacion().getDescripcion();
			String inventario = planificacionSeleccionada.isInventario() == true ? "SI" : "NO";
			msj.append(
					"Usted ha sido designado para participar en una visita de control. A continuacion el detalle:  <br/> <br/> ");
			msj.append("EESS:" + "                  "
					+ "&ensp; &ensp; &ensp; &ensp; &ensp; &ensp; &ensp; &ensp; &ensp; &ensp; &ensp; &ensp; &ensp;"
					+ estacion + "<br/>");
			msj.append("FECHA PLANIFICADA:" + "     " + "&ensp; &ensp; &ensp; &ensp;" + fechaS + "<br/>");
			msj.append("HORA:" + "                  "
					+ "&ensp; &ensp; &ensp; &ensp; &ensp; &ensp; &ensp; &ensp; &ensp; &ensp; &ensp; &ensp;" + horaS
					+ "<br/>");
			msj.append("TIPO ESTACION:" + "         " + "&ensp; &ensp; &ensp; &ensp; &ensp; &ensp; &ensp;"
					+ tipoEstacion + "<br/>");
			msj.append("CATEGORIA DE ESTACION:" + " " + "&ensp;" + categoria + "<br/> <br/>");
			msj.append("RESPONSABLE:" + "           " + "&ensp; &ensp;"
					+ appMain.getUsuario().getPersonaUsuario().getNombreCompleto() + "<br/>");
			msj.append("PARTICIPANTES:       " + "<br/>");
			for (PlanificacionAuditorEt planificacionAuditor : planificacion.getPlanificacionAuditor()) {
				msj.append("* " + planificacionAuditor.getUsuarioAuditor().getPersonaUsuario().getNombreCompleto()
						+ "<br/>");
			}
			msj.append("<br/>");
			msj.append("INVENTARIO:" + "             " + inventario + "<br/> <br/>");
			msj.append("CHECKLIST AGENDADOS EN EL SISTEMA EVALUACION 360G: <br/> <br/> ");
			for (CheckListEjecucionEt checkListEjecucion : planificacion.getCheckListEjecucion()) {
				msj.append("* " + checkListEjecucion.getUsuarioAsignado().getPersonaUsuario().getNombreCompleto()
						+ " / " + checkListEjecucion.getCodigo() + "-" + checkListEjecucion.getDescripcion() + "<br/>");
			}
			msj.append("<br/> <br/>");
			msj.append(
					"Los checklist asignados podran ser visualizados en el sistema ingresando con sus credenciales y solo podran ser ejecutados en la fecha planificada <br/> <br/>");
			msj.append(
					"Los auditores que no tengan Checklist agendados colaboraran en actividades que se les asignara durante la visita. <br/> <br/> <br/>");
			msj.append("Gracias por su confianza, <br/>");
			msj.append(appMain.getUsuario().getPersonaUsuario().getNombreCompleto() + " <br/>");
			msj.append("Responsable de Control <br/>  <br/>");
			msj.append("(Las tildes han sido omitidas intencionalmente para evitar problemas de lectura).");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método mensajeCorreo " + " " + e.getMessage());
		}
		return msj.toString();
	}

	public ZonaEt getZonaSeleccionada() {
		return zonaSeleccionada;
	}

	public void setZonaSeleccionada(ZonaEt zonaSeleccionada) {
		this.zonaSeleccionada = zonaSeleccionada;
	}

	@Override
	protected void onDestroy() {
		iZonaDao.remove();
		iCorreoDao.remove();
		iUsuarioDao.remove();
		iAgenciaDao.remove();
		iCheckListDao.remove();
		iEvaluacionDao.remove();
		iResponsableDao.remove();
		iTipoChecKListDao.remove();
		iPlanificacionDao.remove();
		iNivelEvaluacionDao.remove();
		iAgenciaCheckListDao.remove();
		iCheckListProcesoDao.remove();
		iCheckListEjecucionDao.remove();
	}

}
