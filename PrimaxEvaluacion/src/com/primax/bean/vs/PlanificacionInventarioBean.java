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
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.CorreoEt;
import com.primax.jpa.param.TipoInventarioEt;
import com.primax.jpa.param.ZonaEt;
import com.primax.jpa.param.ZonaUsuarioEt;
import com.primax.jpa.pla.CheckListEt;
import com.primax.jpa.pla.CheckListProcesoEjecucionEt;
import com.primax.jpa.pla.PlanificacionInventarioEt;
import com.primax.jpa.pla.PlanificacionInventarioTipoEt;
import com.primax.jpa.pla.PlanificacionParticipanteEt;
import com.primax.jpa.pla.PlanificacionResponsableEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IAgenciaDao;
import com.primax.srv.idao.ICorreoDao;
import com.primax.srv.idao.IPlanificacionInventarioDao;
import com.primax.srv.idao.ITipoInventarioDao;
import com.primax.srv.idao.IUsuarioDao;
import com.primax.srv.idao.IZonaDao;
import com.primax.srv.util.msg.MessageCenter;
import com.primax.srv.util.msg.MessageFactory;

@Named("PlanificacionInventarioBn")
@ViewScoped
public class PlanificacionInventarioBean extends BaseBean implements Serializable {

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
	private ITipoInventarioDao iTipoInventarioDao;
	@EJB
	private IPlanificacionInventarioDao iPlanificacionDao;

	private TreeNode selectedNode;
	private boolean tooltip = true;
	private boolean bloqueo = true;
	private ZonaEt zonaSeleccionada;
	private ScheduleModel eventModel;
	private TreeNode[] selectedNodes;
	private UsuarioEt usuarioSeleccionado;
	private AgenciaEt estacionSeleccionada;
	private UsuarioEt responsableSeleccionado;
	private List<UsuarioEt> participanteSeleccionados;
	private List<PlanificacionInventarioEt> planificaciones;
	private ScheduleEvent event = new DefaultScheduleEvent();
	private List<TipoInventarioEt> tipoInventarioSeleccionados;
	private PlanificacionInventarioEt planificacionSeleccionada;
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
		event = new DefaultScheduleEvent();
		responsableSeleccionado = new UsuarioEt();
		participanteSeleccionados = new ArrayList<>();
		tipoInventarioSeleccionados = new ArrayList<>();
		planificacionSeleccionada = new PlanificacionInventarioEt();
		planificacionSeleccionada.setPlanificacionResponsable(new ArrayList<>());
		planificacionSeleccionada.setPlanificacionParticipante(new ArrayList<>());
		planificacionSeleccionada.setPlanificacionInventarioTipo(new ArrayList<>());
	}

	public void guardar() {
		String mensaje = "";
		try {

			planificacionSeleccionada.setAgencia(estacionSeleccionada);
			planificacionSeleccionada.setZona(zonaSeleccionada);
			planificacionSeleccionada.setEstadoInventario(EstadoCheckListEnum.AGENDADA);
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
				iPlanificacionDao.guardarPlanificacionInventario(planificacionSeleccionada, usuario);
				RequestContext.getCurrentInstance().execute("PF('dlg_pln_012').hide();");
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

	public void enviarEmail(PlanificacionInventarioEt planificacion) {

		String email = "";
		String email0 = "";
		String email1 = "";
		StringBuilder recipient = new StringBuilder();
		try {
			CorreoEt config = iCorreoDao.getCorreoExiste(1L);
			MessageFactory msg = new MessageFactory(MessageType.MAIL);
			MessageCenter msc = msg.getMessenger();
			msc.setSubject(planificacion.getAgencia().getNombreAgencia() + " :Visita de Control Interno Inventario");
			msc.setFrom("notificacionControlInterno@atimasa.com.ec");
			msc.setMessage(mensajeCorreo(planificacion));
			email0 = appMain.getUsuario().getPersonaUsuario().getEmail();
			for (PlanificacionResponsableEt plnResponsable : planificacionSeleccionada.getPlanificacionResponsable()) {
				if (plnResponsable.getUsuarioResponsable().getPersonaUsuario().getEmail() != null) {
					email = plnResponsable.getUsuarioResponsable().getPersonaUsuario().getEmail() + ",";
					recipient.append(email);
				}
			}
			email1 = recipient.toString() + email0;
			System.out.println(email1);
			// msc.setRecipient("acorrea@accorp.com.ec,jeffersonmaji@hotmail.com");
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

			if (responsableSeleccionado == null || responsableSeleccionado.getIdUsuario() == null) {
				mensaje = "Por favor seleccionar responsable para continuar ";
				return mensaje;
			} else {
				planificacionSeleccionada.setPlanificacionResponsable(new ArrayList<>());
				PlanificacionResponsableEt responsable = new PlanificacionResponsableEt();
				responsable.setUsuarioResponsable(responsableSeleccionado);
				responsable.setPlanificacionInventario(planificacionSeleccionada);
				planificacionSeleccionada.getPlanificacionResponsable().add(responsable);

			}
			if (participanteSeleccionados.isEmpty()) {
				mensaje = "Por favor seleccionar participantes para continuar ";
				return mensaje;
			} else {
				planificacionSeleccionada.setPlanificacionParticipante(new ArrayList<>());
				for (UsuarioEt usuario : participanteSeleccionados) {
					PlanificacionParticipanteEt participante = new PlanificacionParticipanteEt();
					participante.setUsuarioParticipante(usuario);
					participante.setPlanificacionInventario(planificacionSeleccionada);
					planificacionSeleccionada.getPlanificacionParticipante().add(participante);
				}
			}
			if (tipoInventarioSeleccionados.isEmpty()) {
				mensaje = "Por favor seleccionar tipos inventario para continuar ";
				return mensaje;
			} else {
				planificacionSeleccionada.setPlanificacionInventarioTipo(new ArrayList<>());
				for (TipoInventarioEt tipoInventario : tipoInventarioSeleccionados) {
					PlanificacionInventarioTipoEt inventarioTipo = new PlanificacionInventarioTipoEt();
					inventarioTipo.setTipoInventario(tipoInventario);
					inventarioTipo.setPlanificacionInventario(planificacionSeleccionada);
					planificacionSeleccionada.getPlanificacionInventarioTipo().add(inventarioTipo);
				}
			}

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
		} catch (

		Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método validarGuardar " + " " + e.getMessage());
		}
		return mensaje;
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

	public void inicializarCalendario() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			eventModel = new LazyScheduleModel() {
				private static final long serialVersionUID = 1L;

				@Override
				public void loadEvents(Date desde, Date hasta) {
					try {
						planificaciones = iPlanificacionDao.getPlanificacionInventarioList(desde, hasta);
						DefaultScheduleEvent scheduleEventAllDay;
						String estado = "";
						String estacion = "";
						String inventario = "";
						for (PlanificacionInventarioEt planificacion : planificaciones) {
							String leyenda0 = "";
							String tema = "schedule-agendada";
							String responsable = "RESPONSABLE:" + "<br/>";
							String participante = "PARTICIPANTE:" + "<br/>";
							estado = "ESTADO:" + " " + planificacion.getEstadoInventario().getDescripcion() + "<br/>";
							estacion = planificacion.getAgencia().getNombreAgencia() + "<br/>";
							for (PlanificacionResponsableEt responsableC : planificacion
									.getPlanificacionResponsable()) {
								responsable += responsableC.getUsuarioResponsable().getPersonaUsuario()
										.getNombreCompleto() + "<br/>";
							}
							for (PlanificacionParticipanteEt participanteC : planificacion
									.getPlanificacionParticipante()) {
								participante += participanteC.getUsuarioParticipante().getPersonaUsuario()
										.getNombreCompleto() + "<br/>";
							}
							leyenda0 = estacion + responsable + participante + estado + inventario;
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
							responsable = "";
							participante = "";
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

	public void buscarSiguiente() {
		try {

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
			planificacionSeleccionada = new PlanificacionInventarioEt();
			planificacionSeleccionada.setPlanificacionParticipante(new ArrayList<>());
			planificacionSeleccionada.setPlanificacionResponsable(new ArrayList<>());
			planificacionSeleccionada.setPlanificacionInventarioTipo(new ArrayList<>());
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
		planificacionSeleccionada = (PlanificacionInventarioEt) event.getData();
		estacionSeleccionada = planificacionSeleccionada.getAgencia();
		zonaSeleccionada = planificacionSeleccionada.getZona();
		bloqueo = true;
		for (PlanificacionResponsableEt planificacionResponsable : planificacionSeleccionada
				.getPlanificacionResponsable()) {
			responsableSeleccionado = planificacionResponsable.getUsuarioResponsable();
		}
		for (PlanificacionParticipanteEt planificacionParticipante : planificacionSeleccionada
				.getPlanificacionParticipante()) {
			participanteSeleccionados.add(planificacionParticipante.getUsuarioParticipante());
		}
		for (PlanificacionInventarioTipoEt planificacionInv : planificacionSeleccionada
				.getPlanificacionInventarioTipo()) {
			if (planificacionInv.isEjecutado()) {
				tipoInventarioSeleccionados.add(planificacionInv.getTipoInventario());
			}
		}
	}

	public void onEventResize(ScheduleEntryResizeEvent event) {
		System.out.println("Error :Método cargarCheckList " + " ");
	}

	public List<TipoInventarioEt> getTipoInventarioList() {
		List<TipoInventarioEt> tipoInventario = new ArrayList<TipoInventarioEt>();
		try {
			tipoInventario = iTipoInventarioDao.getTipoInventarioList(null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTipoInventarioList " + " " + e.getMessage());
		}
		return tipoInventario;
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

	public UsuarioEt getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	public void setUsuarioSeleccionado(UsuarioEt usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
	}

	public UsuarioEt getResponsableSeleccionado() {
		return responsableSeleccionado;
	}

	public void setResponsableSeleccionado(UsuarioEt responsableSeleccionado) {
		this.responsableSeleccionado = responsableSeleccionado;
	}

	public List<UsuarioEt> getParticipanteSeleccionados() {
		return participanteSeleccionados;
	}

	public void setParticipanteSeleccionados(List<UsuarioEt> participanteSeleccionados) {
		this.participanteSeleccionados = participanteSeleccionados;
	}

	public List<PlanificacionInventarioEt> getPlanificaciones() {
		return planificaciones;
	}

	public void setPlanificaciones(List<PlanificacionInventarioEt> planificaciones) {
		this.planificaciones = planificaciones;
	}

	public PlanificacionInventarioEt getPlanificacionSeleccionada() {
		return planificacionSeleccionada;
	}

	public void setPlanificacionSeleccionada(PlanificacionInventarioEt planificacionSeleccionada) {
		this.planificacionSeleccionada = planificacionSeleccionada;
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

	public String mensajeCorreo(PlanificacionInventarioEt planificacion) {
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
			msj.append(
					"Usted ha sido designado para participar en un Inventario. A continuación el detalle:  <br/> <br/> ");
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
			msj.append("RESPONSABLES:      " + "<br/>");
			for (PlanificacionResponsableEt responsable : planificacion.getPlanificacionResponsable()) {
				msj.append(
						"* " + responsable.getUsuarioResponsable().getPersonaUsuario().getNombreCompleto() + "<br/>");
			}
			msj.append("<br/>");
			msj.append("PARTICIPANTES:    " + "<br/>");
			for (PlanificacionParticipanteEt participante : planificacion.getPlanificacionParticipante()) {
				msj.append(
						"* " + participante.getUsuarioParticipante().getPersonaUsuario().getNombreCompleto() + "<br/>");
			}
			msj.append("<br/>");
			msj.append("INVENTARIOS AGENDADOS EN EL SISTEMA EVALUACION 360G: <br/> <br/>");
			for (PlanificacionInventarioTipoEt tipo : planificacion.getPlanificacionInventarioTipo()) {
				msj.append("* " + tipo.getTipoInventario().getDescripcion() + "<br/>");
			}
			msj.append("<br/> <br/>");
			msj.append(
					"Los inventarios asignados podran ser visualizados en el sistema ingresando con sus credenciales y solo podran ser ejecutados en la fecha planificada <br/> <br/>");
			msj.append(
					"Los auditores que no tengan Inventarios agendados colaboraran en actividades que se les asignara durante la visita. <br/> <br/> <br/>");
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

	public List<TipoInventarioEt> getTipoInventarioSeleccionados() {
		return tipoInventarioSeleccionados;
	}

	public void setTipoInventarioSeleccionados(List<TipoInventarioEt> tipoInventarioSeleccionados) {
		this.tipoInventarioSeleccionados = tipoInventarioSeleccionados;
	}

	@Override
	protected void onDestroy() {
		iZonaDao.remove();
		iCorreoDao.remove();
		iUsuarioDao.remove();
		iAgenciaDao.remove();
		iPlanificacionDao.remove();
		iTipoInventarioDao.remove();
	}

}
