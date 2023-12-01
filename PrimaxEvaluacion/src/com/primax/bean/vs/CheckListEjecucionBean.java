package com.primax.bean.vs;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.enm.gen.MessageType;
import com.primax.enm.gen.RutaFileEnum;
import com.primax.enm.gen.UtilEnum;
import com.primax.enm.msg.Mensajes;
import com.primax.jpa.enums.EstadoCheckListEnum;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.enums.TipoCheckListEnum;
import com.primax.jpa.param.CorreoEt;
import com.primax.jpa.param.CriterioEvaluacionDetalleEt;
import com.primax.jpa.param.KPICriticoEt;
import com.primax.jpa.pla.CheckListEjecucionAdjuntoEt;
import com.primax.jpa.pla.CheckListEjecucionEt;
import com.primax.jpa.pla.CheckListEjecucionReporteEt;
import com.primax.jpa.pla.CheckListKpiEjecucionEt;
import com.primax.jpa.pla.CheckListProcesoEjecucionEt;
import com.primax.jpa.pla.PlanificacionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.ICheckListEjecucionAdjuntoDao;
import com.primax.srv.idao.ICheckListEjecucionDao;
import com.primax.srv.idao.ICheckListEjecucionReporteDao;
import com.primax.srv.idao.ICheckListKpiEjecucionDao;
import com.primax.srv.idao.ICorreoDao;
import com.primax.srv.idao.ICriterioEvaluacionDetalleDao;
import com.primax.srv.idao.IGeneralUtilsDao;
import com.primax.srv.idao.IKPICriticoDao;
import com.primax.srv.util.msg.MessageCenter;
import com.primax.srv.util.msg.MessageFactory;

@Named("CheckListEjecucionBn")
@ViewScoped
public class CheckListEjecucionBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private ICorreoDao iCorreoDao;
	@EJB
	private IKPICriticoDao iKPICriticoDao;
	@EJB
	private IGeneralUtilsDao iGeneralUtilsDao;
	@EJB
	private ICheckListEjecucionDao iCheckListEjecucionDao;
	@EJB
	private ICheckListKpiEjecucionDao iCheckListKpiEjecucionDao;
	@EJB
	private ICriterioEvaluacionDetalleDao iCriterioEvaluacionDetalleDao;
	@EJB
	private ICheckListEjecucionAdjuntoDao iCheckListEjecucionAdjuntoDao;
	@EJB
	private ICheckListEjecucionReporteDao iCheckListEjecucionReporteDao;

	private Long totalPuntaje = 0L;
	private String descripcion = "";
	private boolean validarGuardar = false;
	private Long totalPuntajeCalificacion = 0L;
	private CheckListEjecucionEt checkListEjecucion;
	private CriterioEvaluacionDetalleEt criterioEvaluacionDetalleSeleccionado;
	private CheckListEjecucionReporteEt checkListEjecucionReporteSeleccionado;
	private List<CheckListEjecucionAdjuntoEt> checkListEjecucionAdjuntoEliminado;

	@Inject
	private AppMain appMain;

	@Override
	protected void init() {
		inicializarObj();
		buscar();
	}

	public void buscar() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			checkListEjecucion = iCheckListEjecucionDao.getCheckListEjecucion(TipoCheckListEnum.CRITERIO_GENERAL,
					usuario);
			if (checkListEjecucion != null) {
				if (checkListEjecucion == null) {
					checkListEjecucion.setCheckListEjecucionAdjunto(new ArrayList<>());
				}
				if (checkListEjecucion.isVisualizarComentario()) {
					descripcion = "Observación";
				}
				mostrarTotal(checkListEjecucion);
				mostrarTotalCalificacion(checkListEjecucion);
			}
			if (checkListEjecucion.getCheckListEjecucionReporte() != null
					&& !checkListEjecucion.getCheckListEjecucionReporte().isEmpty()) {
				for (CheckListEjecucionReporteEt reporte : checkListEjecucion.getCheckListEjecucionReporte()) {
					checkListEjecucionReporteSeleccionado = reporte;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}

	}

	public void inicializarObj() {
		criterioEvaluacionDetalleSeleccionado = null;
		checkListEjecucionAdjuntoEliminado = new ArrayList<>();
	}

	public void guardarReporte() {
		String codigo = "";
		String secuencial = "";
		try {
			UsuarioEt usuario = appMain.getUsuario();
			if (checkListEjecucionReporteSeleccionado.getSecuencial() < 10) {
				secuencial = "00" + checkListEjecucionReporteSeleccionado.getSecuencial();
			} else if (checkListEjecucionReporteSeleccionado.getSecuencial() > 10
					&& checkListEjecucionReporteSeleccionado.getSecuencial() < 100) {
				secuencial = "0" + checkListEjecucionReporteSeleccionado.getSecuencial();
			} else {
				secuencial = "" + checkListEjecucionReporteSeleccionado.getSecuencial();
			}
			codigo = checkListEjecucionReporteSeleccionado.getCodigo() + secuencial;
			checkListEjecucionReporteSeleccionado.setCodigoReplace(codigo);
			checkListEjecucionReporteSeleccionado
					.setDescripcionReplace(remplazarD(checkListEjecucionReporteSeleccionado));
			iCheckListEjecucionReporteDao.guardarCheckListEjecucionReporte(checkListEjecucionReporteSeleccionado,
					usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarReporte " + " " + e.getMessage());
		}

	}

	public void guardar() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucion, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}

	}

	public void guardarCheckList() {
		String pagina = "";
		try {
			UsuarioEt usuario = appMain.getUsuario();
			if (!checkListEjecucionAdjuntoEliminado.isEmpty()) {
				for (CheckListEjecucionAdjuntoEt checkListEjecucionAdjunto : checkListEjecucionAdjuntoEliminado) {
					iCheckListEjecucionAdjuntoDao.guardarCheckListEjecucionAdjunto(checkListEjecucionAdjunto, usuario);
				}
			}
			guardaArchivo(checkListEjecucion);
			FacesContext contex = FacesContext.getCurrentInstance();
			checkListEjecucion.setEstadoCheckList(EstadoCheckListEnum.EJECUTADO);
			iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucion, usuario);
			iCheckListEjecucionDao.generarActNivelRiesgo(checkListEjecucion.getNivelEvaluacion().getIdNivelEvaluacion(),
					checkListEjecucion.getIdCheckListEjecucion());
			pagina = "/PrimaxEvaluacion/pages/main.xhtml";
			contex.getExternalContext().redirect(pagina);
			showInfo("Grabado con Éxito ", FacesMessage.SEVERITY_INFO);
			if (checkListEjecucion.isModificado()) {
				enviarEmail(checkListEjecucion.getPlanificacion());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarCheckList " + " " + e.getMessage());
		}
	}

	public void validaCompleto() {
		validarGuardar = false;
		try {
			for (CheckListProcesoEjecucionEt checkListProcesoEjecucion : checkListEjecucion
					.getCheckListProcesoEjecucion()) {
				for (CheckListKpiEjecucionEt checkListKpiEjecucion : checkListProcesoEjecucion
						.getCheckListKpiEjecucion()) {
					if (checkListKpiEjecucion.getCriterioEvaluacionDetalle() == null) {
						validarGuardar = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método validaCompleto " + " " + e.getMessage());
		}
	}

	public Long sumarProceso(CheckListProcesoEjecucionEt checkListProcesoEjecucion) {
		Long totalPuntajeD = 0L;
		try {
			for (int i = 0; i < checkListProcesoEjecucion.getCheckListKpiEjecucion().size(); i++) {
				if (checkListProcesoEjecucion.getCheckListKpiEjecucion().get(i).getkPICritico() == null) {
					totalPuntajeD += checkListProcesoEjecucion.getCheckListKpiEjecucion().get(i).getProcesoDetalle()
							.getPuntaje();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método sumarProceso " + " " + e.getMessage());
		}
		return totalPuntajeD;
	}

	public Long sumarProcesoCalificacion(CheckListProcesoEjecucionEt checkListProcesoEjecucion) {
		Long totalPuntajeD = 0L;
		try {
			for (int i = 0; i < checkListProcesoEjecucion.getCheckListKpiEjecucion().size(); i++) {
				if (checkListProcesoEjecucion.getCheckListKpiEjecucion().get(i).getkPICritico() == null
						&& checkListProcesoEjecucion.getCheckListKpiEjecucion().get(i).isSumar()) {
					totalPuntajeD += checkListProcesoEjecucion.getCheckListKpiEjecucion().get(i).getPuntajeEjecucion();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método sumarProceso " + " " + e.getMessage());
		}
		return totalPuntajeD;
	}

	public void mostrarTotal(CheckListEjecucionEt checkListEjecucion) {
		totalPuntaje = 0L;
		Long totalKpi = 0L;
		try {
			for (CheckListProcesoEjecucionEt checkListProceso : checkListEjecucion.getCheckListProcesoEjecucion()) {
				for (int i = 0; i < checkListProceso.getCheckListKpiEjecucion().size(); i++) {
					if (checkListProceso.getCheckListKpiEjecucion().get(i).getkPICritico() == null
							&& checkListProceso.getCheckListKpiEjecucion().get(i).isSumar()) {
						totalKpi += checkListProceso.getCheckListKpiEjecucion().get(i).getProcesoDetalle().getPuntaje();
					}
				}
			}
			totalPuntaje = totalKpi;
			System.out.println("Total Puntaje" + " " + totalPuntaje);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método mostrarTotal " + " " + e.getMessage());
		}
	}

	public void mostrarTotalCalificacion(CheckListEjecucionEt checkListEjecucion) {
		totalPuntajeCalificacion = 0L;
		Long totalKpi = 0L;
		try {
			for (CheckListProcesoEjecucionEt checkListProcesoEjecucion : checkListEjecucion
					.getCheckListProcesoEjecucion()) {
				totalKpi += checkListProcesoEjecucion.getCheckListKpiEjecucion().stream().filter(p -> p.isSumar())
						.mapToLong(p -> p.getPuntajeEjecucion()).sum();
			}
			totalPuntajeCalificacion = totalKpi;
			System.out.println("Total Puntaje Calificación" + " " + totalPuntajeCalificacion);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método mostrarTotal " + " " + e.getMessage());
		}
	}

	public void guardarSeleccion(CheckListKpiEjecucionEt checkListKpiEjecucion) {
		Long puntaje = 0L;
		KPICriticoEt kPICritico = null;
		boolean condicionCritica = false;
		boolean condicionCriticaFoco = false;
		try {
			UsuarioEt usuario = appMain.getUsuario();
			if (checkListKpiEjecucion.getkPICritico() == null) {
				if (checkListKpiEjecucion.getCriterioEvaluacionDetalle() != null) {
					if (checkListKpiEjecucion.getCriterioEvaluacionDetalle().getNombre().equals("Cumple")) {
						puntaje = checkListKpiEjecucion.getPuntaje();
					}
				}
				checkListKpiEjecucion.setPuntajeEjecucion(puntaje);
				iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpiEjecucion, usuario);
			} else {
				iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpiEjecucion, usuario);
				iCheckListKpiEjecucionDao.clear();
				condicionCritica = verificacionCondicionEstrella(usuario);
				kPICritico = checkListKpiEjecucion.getkPICritico();
				if (!checkListKpiEjecucion.getCriterioEvaluacionDetalle().getNombre().equals("Cumple")) {
					if (kPICritico.getNombre().equals("Foco")) {
						CheckListProcesoEjecucionEt checkListProcesos = checkListKpiEjecucion
								.getCheckListProcesoEjecucion();
						for (CheckListKpiEjecucionEt checkListKpi : checkListProcesos.getCheckListKpiEjecucion()) {
							checkListKpi.setSumar(false);
							iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpi, usuario);
						}
					} else if (kPICritico.getNombre().equals("Estrella")) {
						for (CheckListProcesoEjecucionEt checkListProcesos : checkListEjecucion
								.getCheckListProcesoEjecucion()) {
							for (CheckListKpiEjecucionEt checkListKpi : checkListProcesos.getCheckListKpiEjecucion()) {
								checkListKpi.setSumar(false);
								iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpi, usuario);
							}
						}
					}
				} else if (checkListKpiEjecucion.getCriterioEvaluacionDetalle().getNombre().equals("Cumple")
						&& !condicionCritica) {
					if (kPICritico.getNombre().equals("Foco")) {
						CheckListProcesoEjecucionEt checkListProcesos = checkListKpiEjecucion
								.getCheckListProcesoEjecucion();
						for (CheckListKpiEjecucionEt checkListKpi : checkListProcesos.getCheckListKpiEjecucion()) {
							checkListKpi.setSumar(true);
							iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpi, usuario);
						}
					} else if (kPICritico.getNombre().equals("Estrella") && !condicionCritica) {
						for (CheckListProcesoEjecucionEt checkListProcesos : checkListEjecucion
								.getCheckListProcesoEjecucion()) {
							condicionCriticaFoco = verificacionCondicionFoco(checkListProcesos, usuario);
							if (!condicionCriticaFoco) {
								for (CheckListKpiEjecucionEt checkListKpi : checkListProcesos
										.getCheckListKpiEjecucion()) {
									checkListKpi.setSumar(true);
									iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpi, usuario);
								}
							}
						}
					}
				}
			}
			sumarProcesoCalificacion(checkListKpiEjecucion.getCheckListProcesoEjecucion());
			mostrarTotalCalificacion(checkListEjecucion);
			validaCompleto();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarSeleccion " + " " + e.getMessage());
		}
	}

	public boolean verificacionCondicionEstrella(UsuarioEt usuario) {
		boolean condicionCritica = false;
		try {
			// jema
			KPICriticoEt kPICritico = iKPICriticoDao.getKPICritico(2L);
			CriterioEvaluacionDetalleEt criterio = iCriterioEvaluacionDetalleDao.getCriterioEvaluacionDetalle(23);
			condicionCritica = iCheckListKpiEjecucionDao.getExisteCondicionCritica(checkListEjecucion, kPICritico,
					criterio);
			if (condicionCritica) {
				for (CheckListProcesoEjecucionEt checkListProcesos : checkListEjecucion
						.getCheckListProcesoEjecucion()) {
					for (CheckListKpiEjecucionEt checkListKpi : checkListProcesos.getCheckListKpiEjecucion()) {
						checkListKpi.setSumar(false);
						iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpi, usuario);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método verificacionCondicion " + " " + e.getMessage());
		}
		return condicionCritica;
	}

	public boolean verificacionCondicionFoco(CheckListProcesoEjecucionEt checkListProcesoEjecucion, UsuarioEt usuario) {
		boolean condicionCritica = false;
		try {
			KPICriticoEt kPICritico = iKPICriticoDao.getKPICritico(1L);
			CriterioEvaluacionDetalleEt criterio = iCriterioEvaluacionDetalleDao.getCriterioEvaluacionDetalle(23);
			condicionCritica = iCheckListKpiEjecucionDao.getExisteCondicionCriticaFoco(checkListProcesoEjecucion,
					kPICritico, criterio);
			if (condicionCritica) {
				for (CheckListKpiEjecucionEt checkListKpi : checkListProcesoEjecucion.getCheckListKpiEjecucion()) {
					checkListKpi.setSumar(false);
					iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpi, usuario);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método verificacionCondicion " + " " + e.getMessage());
		}
		return condicionCritica;
	}

	public void upload(FileUploadEvent event) {
		String ruta;
		String nombreArchivo = "";
		String nombreArchivoR = "";
		try {
			iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucion, appMain.getUsuario());
			CheckListEjecucionAdjuntoEt reg = new CheckListEjecucionAdjuntoEt();
			reg.setCheckListEjecucion(checkListEjecucion);
			reg.setFile(event.getFile().getInputstream());
			nombreArchivoR = moveChar(event.getFile().getFileName());
			nombreArchivo = nombreArchivoR.replace(" ", "_");
			reg.setNombreAdjunto(nombreArchivo);
			for (CheckListEjecucionAdjuntoEt doc : checkListEjecucion.getCheckListEjecucionAdjunto()) {
				if (doc.getNombreAdjunto().equals(reg.getNombreAdjunto())) {
					showInfo("" + Mensajes._ERROR_UPLOAD_DOCUMENTO.getDescripcion(), FacesMessage.SEVERITY_ERROR);
					return;
				}
			}
			if (nombreArchivo.toLowerCase().contains(".png") || nombreArchivo.toLowerCase().contains(".jpg")) {
				ruta = iGeneralUtilsDao.creaRuta(checkListEjecucion.getIdCheckListEjecucion(),
						RutaFileEnum.RUTA_CONTROL_INTERNO.getDescripcion());
			} else {
				ruta = iGeneralUtilsDao.creaRuta(checkListEjecucion.getIdCheckListEjecucion(),
						RutaFileEnum.RUTA_CONTROL_INTERNO.getDescripcion());
			}
			reg.setImagenPath(ruta);
			iGeneralUtilsDao.copyFile(reg.getNombreAdjunto(), reg.getFile(), ruta);
			checkListEjecucion.getCheckListEjecucionAdjunto().add(reg);
			iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucion, appMain.getUsuario());
			FacesMessage msg = new FacesMessage("Satisfactorio! ", nombreArchivo + "  " + "Esta subido Correctamente.");
			FacesContext.getCurrentInstance().addMessage(null, msg);

		} catch (IOException e) {
			FacesMessage msg = new FacesMessage("Error! ", nombreArchivo + "  " + "El archivo no se subio.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método quitarAdjunto " + " " + e.getMessage());
		}

	}

	public static String moveChar(String input) {
		String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
		String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
		String output = input;
		for (int i = 0; i < original.length(); i++) {
			output = output.replace(original.charAt(i), ascii.charAt(i));
		}
		return output;
	}

	public void quitarAdjunto(CheckListEjecucionAdjuntoEt checkListEjecucionAdjunto) {
		try {
			Date fechaRegistro = UtilEnum.FECHA_REGISTRO.getValue();
			checkListEjecucionAdjunto.setFechaModificacion(fechaRegistro);
			checkListEjecucionAdjunto.setEstado(EstadoEnum.INA);
			checkListEjecucionAdjuntoEliminado.add(checkListEjecucionAdjunto);
			iCheckListEjecucionAdjuntoDao.guardarCheckListEjecucionAdjunto(checkListEjecucionAdjunto,
					appMain.getUsuario());
			checkListEjecucion.getCheckListEjecucionAdjunto().remove(checkListEjecucionAdjunto);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método quitarAdjunto " + " " + e.getMessage());
		}
	}

	public void guardaArchivo(CheckListEjecucionEt checkListEjecucion) {
		try {
			String ruta;
			ruta = iGeneralUtilsDao.creaRuta(checkListEjecucion.getIdCheckListEjecucion(),
					RutaFileEnum.RUTA_CONTROL_INTERNO.getDescripcion());
			for (int i = 0; i < checkListEjecucion.getCheckListEjecucionAdjunto().size(); i++) {
				iGeneralUtilsDao.copyFile(checkListEjecucion.getCheckListEjecucionAdjunto().get(i).getNombreAdjunto(),
						checkListEjecucion.getCheckListEjecucionAdjunto().get(i).getFile(), ruta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void enviarEmail(PlanificacionEt planificacion) {
		// String email = "";
		// String email0 = "";
		// String email1 = "";
		// StringBuilder recipient = new StringBuilder();
		try {
			CorreoEt config = iCorreoDao.getCorreoExiste(1L);
			MessageFactory msg = new MessageFactory(MessageType.MAIL);
			MessageCenter msc = msg.getMessenger();
			msc.setSubject(planificacion.getAgencia().getNombreAgencia() + " :Visita de Control Interno");
			msc.setFrom("notificacionControlInterno@atimasa.com.ec");
			msc.setMessage("Se ha modificado CheckList Prueba");
			// email0 = appMain.getUsuario().getPersonaUsuario().getEmail();
			// email1 = recipient.toString() + email0;
			// System.out.println(email1);
			msc.setRecipient("acorrea@accorp.com.ec,jeffersonmaji@hotmail.com");
			// msc.setRecipient(email1);
			msc.setConfig(config);
			msc.sendMessage();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método enviarEmail " + " " + e.getMessage());
		}
	}

	public String remplazarD(CheckListEjecucionReporteEt checkListEjecucionReporte) {
		String dia = "";
		String mesS = "";
		String anio = "";
		String horaI = "";
		String horaF = "";
		String descripcion = "";
		try {
			Calendar calendar = Calendar.getInstance();
			Calendar calendarI = Calendar.getInstance();
			Calendar calendarF = Calendar.getInstance();
			DateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.UK);
			calendar.setTime(checkListEjecucionReporte.getFechaVerificacion());
			calendarI.setTime(checkListEjecucionReporte.getHoraInicio());
			calendarF.setTime(checkListEjecucionReporte.getHoraFin());
			dia = "" + calendar.get(Calendar.DAY_OF_MONTH);
			Month mes = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
					calendar.get(Calendar.DAY_OF_MONTH)).getMonth();
			mesS = mes.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
			anio = "" + calendar.get(Calendar.YEAR);
			horaI = dateFormat.format(calendarI.getTime()).replace(":", "h");
			horaF = dateFormat.format(calendarF.getTime()).replace(":", "h");
			descripcion = checkListEjecucionReporte.getDescripcion().replace("{dia}", dia);
			descripcion = descripcion.replace("{mes}", mesS);
			descripcion = descripcion.replace("{anio}", anio);
			descripcion = descripcion.replace("{hora_inicio}", horaI);
			descripcion = descripcion.replace("{hora_fin}", horaF);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método remplazarD " + " " + e.getMessage());
		}
		return descripcion;
	}

	public CheckListEjecucionEt getCheckListEjecucion() {
		return checkListEjecucion;
	}

	public void setCheckListEjecucion(CheckListEjecucionEt checkListEjecucion) {
		this.checkListEjecucion = checkListEjecucion;
	}

	public Long getTotalPuntaje() {
		return totalPuntaje;
	}

	public void setTotalPuntaje(Long totalPuntaje) {
		this.totalPuntaje = totalPuntaje;
	}

	public AppMain getAppMain() {
		return appMain;
	}

	public void setAppMain(AppMain appMain) {
		this.appMain = appMain;
	}

	public CriterioEvaluacionDetalleEt getCriterioEvaluacionDetalleSeleccionado() {
		return criterioEvaluacionDetalleSeleccionado;
	}

	public void setCriterioEvaluacionDetalleSeleccionado(
			CriterioEvaluacionDetalleEt criterioEvaluacionDetalleSeleccionado) {
		this.criterioEvaluacionDetalleSeleccionado = criterioEvaluacionDetalleSeleccionado;
	}

	public Long getTotalPuntajeCalificacion() {
		return totalPuntajeCalificacion;
	}

	public void setTotalPuntajeCalificacion(Long totalPuntajeCalificacion) {
		this.totalPuntajeCalificacion = totalPuntajeCalificacion;
	}

	public boolean isValidarGuardar() {
		return validarGuardar;
	}

	public void setValidarGuardar(boolean validarGuardar) {
		this.validarGuardar = validarGuardar;
	}

	public CheckListEjecucionReporteEt getCheckListEjecucionReporteSeleccionado() {
		return checkListEjecucionReporteSeleccionado;
	}

	public void setCheckListEjecucionReporteSeleccionado(
			CheckListEjecucionReporteEt checkListEjecucionReporteSeleccionado) {
		this.checkListEjecucionReporteSeleccionado = checkListEjecucionReporteSeleccionado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	protected void onDestroy() {
		iCorreoDao.remove();
		iKPICriticoDao.remove();
		iCheckListEjecucionDao.remove();
		iCheckListKpiEjecucionDao.remove();
		iCriterioEvaluacionDetalleDao.remove();
		iCheckListEjecucionAdjuntoDao.remove();
		iCheckListEjecucionReporteDao.remove();
	}
}
