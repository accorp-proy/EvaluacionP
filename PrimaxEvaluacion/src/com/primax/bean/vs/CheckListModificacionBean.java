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
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.enm.gen.MessageType;
import com.primax.jpa.enums.EstadoCheckListEnum;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.enums.EstadoPlanAccionEnum;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.CorreoEt;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.param.NivelEvaluacionEt;
import com.primax.jpa.param.ParametrosGeneralesEt;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.param.ZonaEt;
import com.primax.jpa.param.ZonaUsuarioEt;
import com.primax.jpa.pla.CheckListEjecucionEt;
import com.primax.jpa.pla.PlanificacionEt;
import com.primax.jpa.sec.RolEt;
import com.primax.jpa.sec.RolUsuarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IAgenciaDao;
import com.primax.srv.idao.ICheckListEjecucionDao;
import com.primax.srv.idao.ICorreoDao;
import com.primax.srv.idao.IEvaluacionDao;
import com.primax.srv.idao.INivelEvaluacionDao;
import com.primax.srv.idao.IParametrolGeneralDao;
import com.primax.srv.idao.IPlanificacionDao;
import com.primax.srv.idao.IRolEtDao;
import com.primax.srv.idao.ITipoChecKListDao;
import com.primax.srv.idao.IUsuarioDao;
import com.primax.srv.idao.IZonaDao;
import com.primax.srv.util.msg.MessageCenter;
import com.primax.srv.util.msg.MessageFactory;

@Named("CheckListModificacionBn")
@ViewScoped
public class CheckListModificacionBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IZonaDao iZonaDao;
	@EJB
	private IRolEtDao iRolEtDao;
	@EJB
	private ICorreoDao iCorreoDao;
	@EJB
	private IAgenciaDao iAgenciaDao;
	@EJB
	private IUsuarioDao iUsuarioDao;
	@EJB
	private IEvaluacionDao iEvaluacionDao;
	@EJB
	private IPlanificacionDao iPlanificacionDao;
	@EJB
	private ITipoChecKListDao iTipoChecKListDao;
	@EJB
	private INivelEvaluacionDao iNivelEvaluacionDao;
	@EJB
	private IParametrolGeneralDao iParametrolGeneralDao;
	@EJB
	private ICheckListEjecucionDao iCheckListEjecucionDao;

	@Inject
	private AppMain appMain;

	private ZonaEt zonaSeleccionada;
	private Date fDesde = new Date();
	private Date fHasta = new Date();
	private AgenciaEt estacionSeleccionada;
	private EvaluacionEt evaluacionSeleccionada;
	private TipoChecKListEt tipoChecKListSeleccionado;
	private NivelEvaluacionEt nivelEvaluacionSeleccionado;
	private List<CheckListEjecucionEt> checkListEjecuciones;
	private CheckListEjecucionEt checkListEjecucionSeleccionado;

	@Override
	protected void init() {
		inicializarObj();
		buscar();
	}

	public void inicializarObj() {
		fDesde = primerDiaMes();
		fHasta = ultimoDiaMes();
		zonaSeleccionada = null;
		estacionSeleccionada = null;
		nivelEvaluacionSeleccionado = null;

	}

	public void buscar() {
		List<RolUsuarioEt> rolUsuario = null;
		try {
			RolEt rol = iRolEtDao.getRolbyId(8L);
			UsuarioEt usuario = appMain.getUsuario();
			rolUsuario = usuario.getRolesUsario();
			if (usuario.isAccesoZona()) {
				if (containsRol(rolUsuario, rol)) {
					checkListEjecuciones = iCheckListEjecucionDao.getCheckListEjecucionAccesoZonaList(zonaSeleccionada,
							estacionSeleccionada, evaluacionSeleccionada, tipoChecKListSeleccionado,
							nivelEvaluacionSeleccionado, fDesde, fHasta, null, EstadoCheckListEnum.EJECUTADO);
				} else {
					checkListEjecuciones = iCheckListEjecucionDao.getCheckListEjecucionAccesoZonaList(zonaSeleccionada,
							estacionSeleccionada, evaluacionSeleccionada, tipoChecKListSeleccionado,
							nivelEvaluacionSeleccionado, fDesde, fHasta, usuario, EstadoCheckListEnum.EJECUTADO);
				}
			} else {
				if (containsRol(rolUsuario, rol)) {
					checkListEjecuciones = iCheckListEjecucionDao.getCheckListEjecucionList(zonaSeleccionada,
							estacionSeleccionada, evaluacionSeleccionada, tipoChecKListSeleccionado,
							nivelEvaluacionSeleccionado, fDesde, fHasta, null, EstadoCheckListEnum.EJECUTADO);
				} else {
					checkListEjecuciones = iCheckListEjecucionDao.getCheckListEjecucionList(zonaSeleccionada,
							estacionSeleccionada, evaluacionSeleccionada, tipoChecKListSeleccionado,
							nivelEvaluacionSeleccionado, fDesde, fHasta, usuario, EstadoCheckListEnum.EJECUTADO);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}
	}

	public void guardarM() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			checkListEjecucionSeleccionado.setModificado(true);
			checkListEjecucionSeleccionado.setEstadoCheckList(EstadoCheckListEnum.EN_EJECUCION);
			iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucionSeleccionado, usuario);
			showInfo("CheckList se ecuentra habilitado para ser modificado ", FacesMessage.SEVERITY_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarM " + " " + e.getMessage());
		}
	}

	public void guardarE() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			checkListEjecucionSeleccionado.setEstado(EstadoEnum.ELI);
			enviarEmail(checkListEjecucionSeleccionado.getPlanificacion());
			PlanificacionEt planificacion = checkListEjecucionSeleccionado.getPlanificacion();
			planificacion.setEstado(EstadoEnum.ELI);
			iPlanificacionDao.guardarPlanificacion(planificacion, usuario);
			iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucionSeleccionado, usuario);
			showInfo("CheckList eliminado con Éxito ", FacesMessage.SEVERITY_INFO);
			buscar();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarE " + " " + e.getMessage());
		}
	}

	public void modificar(CheckListEjecucionEt checkList) {
		String mensaje = "";
		try {
			mensaje = validar(checkList);
			if (mensaje.equals("")) {
				checkListEjecucionSeleccionado = checkList;
			} else {
				showInfo(mensaje, FacesMessage.SEVERITY_ERROR);
				RequestContext.getCurrentInstance().execute("PF('dlg_conf_0').hide();");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método modificar " + " " + e.getMessage());
		}
	}

	public void eliminar(CheckListEjecucionEt checkList) {
		String mensaje = "";
		try {
			mensaje = validar(checkList);
			if (mensaje.equals("")) {
				checkListEjecucionSeleccionado = checkList;
			} else {
				showInfo(mensaje, FacesMessage.SEVERITY_ERROR);
				RequestContext.getCurrentInstance().execute("PF('dlg_conf_1').hide();");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método eliminar " + " " + e.getMessage());
		}

	}

	public String validar(CheckListEjecucionEt checkList) {
		String mensaje = "";
		try {
			ParametrosGeneralesEt par = iParametrolGeneralDao.getParametrosGeneralById(156L);
			Long hora = calcularHora(checkList.getFechaEjecucion(), new Date());
			Long horaM = Long.parseLong(par.getValorLista());
			if (hora > horaM) {
				mensaje = "Checklist solo puede ser modificado o eliminado dentro de las " + horaM
						+ " horas luego de su ejecución";
				return mensaje;
			}
			if (checkList.isModificado()) {
				mensaje = "CheckList solo se puede modificar una sola vez ";
				return mensaje;
			}
			if (checkList.getEstadoPlanAccion().equals(EstadoPlanAccionEnum.INGRESADO)) {
				mensaje = "CheckList no puede ser modificado o eliminado si ya ingreso el plan de acción ";
				return mensaje;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método validar " + " " + e.getMessage());
		}
		return mensaje;
	}

	public Long calcularHora(Date d1, Date d2) {
		Long hora = 0L;
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		try {
			String dateStart = dateFormat.format(d1);
			String dateStop = dateFormat.format(d2);
			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);
			long diff = d2.getTime() - d1.getTime();
			long diffSeconds = diff / 1000;
			long diffMinutes = diff / (60 * 1000);
			hora = diff / (60 * 60 * 1000);
			System.out.println("Time in seconds: " + diffSeconds + " seconds.");
			System.out.println("Time in minutes: " + diffMinutes + " minutes.");
			System.out.println("Time in hours: " + hora + " hours.");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método validar " + " " + e.getMessage());
		}
		return hora;
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
				checkListEjecucionSeleccionado.setModificado(true);
				FacesContext contex = FacesContext.getCurrentInstance();
				EvaluacionEt evaluacion = checkListEjecucionSeleccionado.getEvaluacion();
				if (evaluacion != null) {
					String codigo = evaluacion.isCriterio() == true ? "1" : "2";
					switch (codigo) {
					case "1":
						pagina = "/PrimaxEvaluacionV2/pages/ejecucion/eje_001.xhtml";
						break;
					case "2":
						pagina = "/PrimaxEvaluacionV2/pages/ejecucion/eje_002.xhtml";
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
		} catch (

		Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método redireccionar " + " " + e.getMessage());
		}
	}

	public void enviarEmail(PlanificacionEt planificacion) {
		// String email = "";
		String email0 = "";
		String email1 = "";
		StringBuilder recipient = new StringBuilder();
		try {
			CorreoEt config = iCorreoDao.getCorreoExiste(1L);
			MessageFactory msg = new MessageFactory(MessageType.MAIL);
			MessageCenter msc = msg.getMessenger();
			msc.setSubject(planificacion.getAgencia().getNombreAgencia() + " :Visita de Control Interno");
			msc.setFrom("notificacionControlInterno@atimasa.com.ec");
			msc.setMessage("Se ha eliminado CheckList Prueba");
			email0 = appMain.getUsuario().getPersonaUsuario().getEmail();
			email1 = recipient.toString() + email0;
			// System.out.println(email1);
			// msc.setRecipient("acorrea@accorp.com.ec,jeffersonmaji@hotmail.com");
			msc.setRecipient(email1);
			msc.setConfig(config);
			msc.sendMessage();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método enviarEmail " + " " + e.getMessage());
		}
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

	public boolean containsRol(final List<RolUsuarioEt> list, final RolEt rol) {
		return list.stream().filter(o -> o.getRol().equals(rol)).findFirst().isPresent();
	}

	public Date getfDesde() {
		return fDesde;
	}

	public void setfDesde(Date fDesde) {
		this.fDesde = fDesde;
	}

	public Date getfHasta() {
		return fHasta;
	}

	public void setfHasta(Date fHasta) {
		this.fHasta = fHasta;
	}

	public AgenciaEt getEstacionSeleccionada() {
		return estacionSeleccionada;
	}

	public void setEstacionSeleccionada(AgenciaEt estacionSeleccionada) {
		this.estacionSeleccionada = estacionSeleccionada;
	}

	public List<CheckListEjecucionEt> getCheckListEjecuciones() {
		return checkListEjecuciones;
	}

	public void setCheckListEjecuciones(List<CheckListEjecucionEt> checkListEjecuciones) {
		this.checkListEjecuciones = checkListEjecuciones;
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

	public AppMain getAppMain() {
		return appMain;
	}

	public void setAppMain(AppMain appMain) {
		this.appMain = appMain;
	}

	public CheckListEjecucionEt getCheckListEjecucionSeleccionado() {
		return checkListEjecucionSeleccionado;
	}

	public void setCheckListEjecucionSeleccionado(CheckListEjecucionEt checkListEjecucionSeleccionado) {
		this.checkListEjecucionSeleccionado = checkListEjecucionSeleccionado;
	}

	public ZonaEt getZonaSeleccionada() {
		return zonaSeleccionada;
	}

	public void setZonaSeleccionada(ZonaEt zonaSeleccionada) {
		this.zonaSeleccionada = zonaSeleccionada;
	}

	public NivelEvaluacionEt getNivelEvaluacionSeleccionado() {
		return nivelEvaluacionSeleccionado;
	}

	public void setNivelEvaluacionSeleccionado(NivelEvaluacionEt nivelEvaluacionSeleccionado) {
		this.nivelEvaluacionSeleccionado = nivelEvaluacionSeleccionado;
	}

	@Override
	protected void onDestroy() {
		iZonaDao.remove();
		iRolEtDao.remove();
		iUsuarioDao.remove();
		iAgenciaDao.remove();
		iCorreoDao.remove();
		iEvaluacionDao.remove();
		iTipoChecKListDao.remove();
		iNivelEvaluacionDao.remove();
		iParametrolGeneralDao.remove();
		iCheckListEjecucionDao.remove();
	}

}
