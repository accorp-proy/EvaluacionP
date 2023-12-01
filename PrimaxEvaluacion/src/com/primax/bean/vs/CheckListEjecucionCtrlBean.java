package com.primax.bean.vs;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.enm.gen.MessageType;
import com.primax.enm.gen.RutaFileEnum;
import com.primax.enm.gen.UtilEnum;
import com.primax.enm.msg.Mensajes;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoCheckListEnum;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.enums.TipoCheckListEnum;
import com.primax.jpa.gen.PersonaEt;
import com.primax.jpa.param.CorreoEt;
import com.primax.jpa.param.CriterioEvaluacionDetalleEt;
import com.primax.jpa.param.ParametrosGeneralesEt;
import com.primax.jpa.param.ResponsableEt;
import com.primax.jpa.param.TipoEstacionEt;
import com.primax.jpa.pla.ArqueoCajaEt;
import com.primax.jpa.pla.ArqueoCajaFuerteEt;
import com.primax.jpa.pla.ArqueoCajaGeneralEt;
import com.primax.jpa.pla.ArqueoCajaPromotorEt;
import com.primax.jpa.pla.ArqueoFondoSueltoEt;
import com.primax.jpa.pla.CheckListEjecucionAdjuntoEt;
import com.primax.jpa.pla.CheckListEjecucionEt;
import com.primax.jpa.pla.CheckListEjecucionFirmaEt;
import com.primax.jpa.pla.CheckListKpiEjecucionAEt;
import com.primax.jpa.pla.CheckListKpiEjecucionBEt;
import com.primax.jpa.pla.CheckListKpiEjecucionCEt;
import com.primax.jpa.pla.CheckListKpiEjecucionEt;
import com.primax.jpa.pla.CheckListKpiEjecucionFirmaEt;
import com.primax.jpa.pla.CheckListProcesoEjecucionEt;
import com.primax.jpa.pla.PlanificacionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IArqueoCajaDao;
import com.primax.srv.idao.IArqueoCajaFuerteDao;
import com.primax.srv.idao.IArqueoCajaGeneralDao;
import com.primax.srv.idao.IArqueoCajaPromotorDao;
import com.primax.srv.idao.IArqueoFondoSueltoDao;
import com.primax.srv.idao.ICheckListEjecucionAdjuntoDao;
import com.primax.srv.idao.ICheckListEjecucionDao;
import com.primax.srv.idao.ICheckListEjecucionFirmaDao;
import com.primax.srv.idao.ICheckListKpiEjecucionADao;
import com.primax.srv.idao.ICheckListKpiEjecucionBDao;
import com.primax.srv.idao.ICheckListKpiEjecucionCDao;
import com.primax.srv.idao.ICheckListKpiEjecucionDao;
import com.primax.srv.idao.ICheckListKpiEjecucionFirmaDao;
import com.primax.srv.idao.ICheckListProcesoEjecucionDao;
import com.primax.srv.idao.ICorreoDao;
import com.primax.srv.idao.IGeneralUtilsDao;
import com.primax.srv.idao.IParametrolGeneralDao;
import com.primax.srv.idao.IPersonaDao;
import com.primax.srv.idao.IResponsableDao;
import com.primax.srv.idao.ITipoEstacionDao;
import com.primax.srv.idao.IUsuarioDao;
import com.primax.srv.util.msg.MessageCenter;
import com.primax.srv.util.msg.MessageFactory;
import com.primax.util.POIReader;
import com.primax.util.RowPoi;

@Named("CheckListEjecucionCtrlBn")
@ViewScoped
public class CheckListEjecucionCtrlBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private ICorreoDao iCorreoDao;
	@EJB
	private IPersonaDao iPersonaDao;
	@EJB
	private IUsuarioDao iUsuarioDao;
	@EJB
	private IArqueoCajaDao iArqueoCajaDao;
	@EJB
	private IResponsableDao iResponsableDao;
	@EJB
	private IGeneralUtilsDao iGeneralUtilsDao;
	@EJB
	private ITipoEstacionDao iTipoEstacionDao;
	@EJB
	private IArqueoCajaFuerteDao iArqueoCajaFuerteDao;
	@EJB
	private IArqueoCajaGeneralDao iArqueoCajaGeneralDao;
	@EJB
	private IArqueoFondoSueltoDao iArqueoFondoSueltoDao;
	@EJB
	private IParametrolGeneralDao iParametrolGeneralDao;
	@EJB
	private ICheckListEjecucionDao iCheckListEjecucionDao;
	@EJB
	private IArqueoCajaPromotorDao iArqueoCajaPromotorDao;
	@EJB
	private ICheckListKpiEjecucionDao iCheckListKpiEjecucionDao;
	@EJB
	private ICheckListKpiEjecucionADao iCheckListKpiEjecucionADao;
	@EJB
	private ICheckListKpiEjecucionCDao iCheckListKpiEjecucionCDao;
	@EJB
	private ICheckListKpiEjecucionBDao iCheckListKpiEjecucionBDao;
	@EJB
	private ICheckListEjecucionFirmaDao iCheckListEjecucionFirmaDao;
	@EJB
	private ICheckListEjecucionAdjuntoDao iCheckListEjecucionAdjuntoDao;
	@EJB
	private ICheckListProcesoEjecucionDao iCheckListProcesoEjecucionDao;
	@EJB
	private ICheckListKpiEjecucionFirmaDao iCheckListKpiEjecucionFirmaDao;

	private String color = "black";
	private Long totalPuntaje = 0L;
	private Double valorRubro = 0.0;
	private Double valorVenta = 0.0;
	private Double valorBlindado = 0.0;
	private Double valorPromotor = 0.0;
	private boolean bloqueoTipo = false;
	private Double valorDiferencia = 0.0;
	private boolean validarGuardar = false;
	private String formulario = ":frm_eje_006";
	private Long totalPuntajeCalificacion = 0L;
	private String descripcionN = "Archivo Novedad";
	private CheckListEjecucionEt checkListEjecucion;
	private ParametrosGeneralesEt tipoRubroSeleccionado;
	private CheckListKpiEjecucionEt checkListKpiEjecucionSeleccionado;
	private CheckListProcesoEjecucionEt checkListProcesoEjecucionTienda;
	private CheckListProcesoEjecucionEt checkListProcesoEjecucionEfectivo;
	private CheckListEjecucionFirmaEt checkListEjecucionFirmaSeleccionado;
	private CheckListProcesoEjecucionEt checkListProcesoEjecucionCombustible;
	private CriterioEvaluacionDetalleEt criterioEvaluacionDetalleSeleccionado;
	private CheckListKpiEjecucionFirmaEt checkListKpiEjecucionFirmaSeleccionado;
	private CheckListProcesoEjecucionEt checkListProcesoEjecucionProcedimientoS;
	private List<CheckListEjecucionAdjuntoEt> checkListEjecucionAdjuntoEliminado;

	@Inject
	private AppMain appMain;

	@Override
	protected void init() {
		inicializarObj();
		buscar();
	}

	public void revisionFirmante() {
		CheckListEjecucionFirmaEt checkList = new CheckListEjecucionFirmaEt();
		try {
			UsuarioEt usuario = appMain.getUsuario();
			if (checkListEjecucion != null) {
				List<CheckListEjecucionFirmaEt> checkListEjecucionFirmas = iCheckListEjecucionFirmaDao
						.getCheckListEjecucionFirmaByChekListList(checkListEjecucion);
				for (CheckListEjecucionFirmaEt checkListEjecucionFirma : checkListEjecucionFirmas) {
					if (checkListEjecucionFirma.getOrden() == 0L) {
						iCheckListEjecucionFirmaDao.eliminarCheckListEjecucionFirmaIndividual(checkListEjecucionFirma,
								usuario);
						iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucion, usuario);
						PersonaEt personaR = checkListEjecucion.getUsuarioAsignado().getPersonaUsuario();
						checkList.setOrden(0L);
						checkList.setFirmado(false);
						checkList.setIdPersona(personaR.getIdPersona());
						checkList.setCargo("Verificador/Control Interno");
						checkList.setCheckListEjecucion(checkListEjecucion);
						checkList.setNombre(personaR.getNombreCompleto());
						checkList.setIdentificacion(personaR.getIdentificacionPersona());
						if (personaR.getFirma() != null) {
							checkList.setContieneFirma(true);
							checkList.setFirma(personaR.getFirma());
						}
						// checkListEjecucion.getCheckListEjecucionFirma().add(checkList);
						// iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucion,
						// usuario);
						checkList.setCheckListEjecucion(checkListEjecucion);
						iCheckListEjecucionFirmaDao.guardarCheckListEjecucionFirma(checkList, usuario);

					}
					if (checkListEjecucionFirma.getOrden() == 1L) {
						iCheckListEjecucionFirmaDao.eliminarCheckListEjecucionFirmaIndividual(checkListEjecucionFirma,
								usuario);
						ResponsableEt responsable = iResponsableDao
								.getResponsableByCargo(checkListEjecucion.getPlanificacion().getAgencia(), 3L);
						checkList = new CheckListEjecucionFirmaEt();
						checkList.setOrden(1L);
						checkList.setFirmado(false);
						checkList.setCheckListEjecucion(checkListEjecucion);
						checkList.setIdPersona(responsable.getPersona().getIdPersona());
						checkList.setCargo(responsable.getTipoCargo().getDescripcion());
						checkList.setNombre(responsable.getPersona().getNombreCompleto());
						checkList.setIdentificacion(responsable.getPersona().getIdentificacionPersona());
						if (responsable.getPersona().getFirma() != null) {
							checkList.setContieneFirma(true);
							checkList.setFirma(responsable.getPersona().getFirma());
						}
						// checkListEjecucion.getCheckListEjecucionFirma().add(checkList);
						// iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucion,
						// usuario);
						checkList.setCheckListEjecucion(checkListEjecucion);
						iCheckListEjecucionFirmaDao.guardarCheckListEjecucionFirma(checkList, usuario);
					}
					if (checkListEjecucionFirma.getOrden() == 2L) {
						iCheckListEjecucionFirmaDao.eliminarCheckListEjecucionFirmaIndividual(checkListEjecucionFirma,
								usuario);
						ResponsableEt responsable = iResponsableDao
								.getResponsableByCargo(checkListEjecucion.getPlanificacion().getAgencia(), 1L);
						checkList = new CheckListEjecucionFirmaEt();
						checkList.setOrden(2L);
						checkList.setFirmado(false);
						checkList.setCheckListEjecucion(checkListEjecucion);
						checkList.setIdPersona(responsable.getPersona().getIdPersona());
						checkList.setCargo(responsable.getTipoCargo().getDescripcion());
						checkList.setNombre(responsable.getPersona().getNombreCompleto());
						checkList.setIdentificacion(responsable.getPersona().getIdentificacionPersona());
						if (responsable.getPersona().getFirma() != null) {
							checkList.setContieneFirma(true);
							checkList.setFirma(responsable.getPersona().getFirma());
						}
						// checkListEjecucion.getCheckListEjecucionFirma().add(checkList);
						// iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucion,
						// usuario);
						checkList.setCheckListEjecucion(checkListEjecucion);
						iCheckListEjecucionFirmaDao.guardarCheckListEjecucionFirma(checkList, usuario);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método revisionFirmante " + " " + e.getMessage());
		}
	}

	public void buscar() {
		String proceso = "";
		try {
			UsuarioEt usuario = appMain.getUsuario();
			checkListEjecucion = iCheckListEjecucionDao.getCheckListEjecucion(TipoCheckListEnum.CRITERIO_ESPECIFICO,
					usuario);
			if (checkListEjecucion != null) {
				if (checkListEjecucion.getCheckListEjecucionAdjunto() == null
						|| checkListEjecucion.getCheckListEjecucionAdjunto().isEmpty()) {
					checkListEjecucion.setCheckListEjecucionAdjunto(new ArrayList<>());
				}
				for (CheckListProcesoEjecucionEt checkListProceso : checkListEjecucion.getCheckListProcesoEjecucion()) {
					checkListProceso.getCheckListKpiEjecucionA().size();
					proceso = checkListProceso.getDescripcion().toLowerCase();

					if (proceso.contains("combustible")) {
						if (checkListProceso.getCheckListKpiEjecucionA().isEmpty()) {
							checkListProceso.setCheckListKpiEjecucionA(new ArrayList<>());
							procesoCombustible(checkListProceso);
						} else {
							checkListProcesoEjecucionCombustible = checkListProceso;
						}
					}
					if (proceso.contains("efectivo") && proceso.contains("control")) {
						if (checkListProceso.getCheckListKpiEjecucionA().isEmpty()) {
							checkListProceso.setCheckListKpiEjecucionA(new ArrayList<>());
							procesoEfectivo(checkListProceso);
						} else {
							checkListProcesoEjecucionEfectivo = checkListProceso;
						}

					}
					if (proceso.contains("efectivo") && proceso.contains("conteo")) {
						if (checkListProceso.getCheckListKpiEjecucionA().isEmpty()) {
							checkListProceso.setCheckListKpiEjecucionA(new ArrayList<>());
							procesoConciliacion(checkListProceso);
						} else {
							checkListProcesoEjecucionEfectivo = checkListProceso;
						}
					}

					if (proceso.contains("tienda")) {
						if (checkListProceso.getCheckListKpiEjecucionA().isEmpty()) {
							checkListProceso.setCheckListKpiEjecucionA(new ArrayList<>());
							procesoTienda(checkListProceso);
						} else {
							checkListProcesoEjecucionTienda = checkListProceso;
						}
					}
					if (proceso.contains("servicio a promotores")) {
						if (checkListProceso.getCheckListKpiEjecucionA().isEmpty()) {
							checkListProceso.setCheckListKpiEjecucionA(new ArrayList<>());
							procesoControlPromotores(checkListProceso);
						} else {
							if (!checkListProceso.getCheckListKpiEjecucionC().isEmpty()) {
								mostrarDescripcion(checkListProceso.getCheckListKpiEjecucionC());
							}
							checkListProcesoEjecucionTienda = checkListProceso;
						}
					}
					if (proceso.contains("procedimiento")) {
						if (checkListProceso.getCheckListKpiEjecucionA().isEmpty()
								&& checkListProceso.getCheckListKpiEjecucionB().isEmpty()) {
							checkListProceso.setCheckListKpiEjecucionA(new ArrayList<>());
							checkListProceso.setCheckListKpiEjecucionB(new ArrayList<>());
							procesoAdminstrativo(checkListProceso);
						} else {
							checkListProcesoEjecucionProcedimientoS = checkListProceso;
						}
					}
				}
				if (!checkListEjecucion.getEstadoCheckList().equals(EstadoCheckListEnum.AGENDADA)) {
					iCheckListEjecucionDao.clear();
					checkListEjecucion = iCheckListEjecucionDao
							.getCheckListEjecucion(TipoCheckListEnum.CRITERIO_ESPECIFICO, usuario);
					revisionFirmante();
				}
				iCheckListEjecucionDao.clear();
				checkListEjecucion = iCheckListEjecucionDao.getCheckListEjecucion(TipoCheckListEnum.CRITERIO_ESPECIFICO,
						usuario);
				validaCompleto();

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}

	}

	public void inicializarObj() {
		valorRubro = 0.0D;
		tipoRubroSeleccionado = null;
		criterioEvaluacionDetalleSeleccionado = null;
		checkListEjecucionAdjuntoEliminado = new ArrayList<>();
		checkListProcesoEjecucionTienda = null;

	}

	public void guardar() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucion, usuario);
			iCheckListEjecucionDao.generarActNivelRiesgo(checkListEjecucion.getNivelEvaluacion().getIdNivelEvaluacion(),
					checkListEjecucion.getIdCheckListEjecucion());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}

	}

	public void guardarCheckList() {
		String pagina = "";
		try {
			UsuarioEt usuario = appMain.getUsuario();
			if (!checkListEjecucion.getCheckListEjecucionAdjunto().isEmpty()) {
				for (CheckListEjecucionAdjuntoEt adjunto : checkListEjecucion.getCheckListEjecucionAdjunto()) {
					if (adjunto.getParametroCategoria() == null) {
						showInfo("Por favor Seleccionar una categoría en adjuntos.", FacesMessage.SEVERITY_INFO, null,
								"");
						return;
					}
				}
			}
			if (!checkListEjecucionAdjuntoEliminado.isEmpty()) {
				for (CheckListEjecucionAdjuntoEt checkListEjecucionAdjunto : checkListEjecucionAdjuntoEliminado) {
					iCheckListEjecucionAdjuntoDao.guardarCheckListEjecucionAdjunto(checkListEjecucionAdjunto, usuario);
				}
			}
			FacesContext contex = FacesContext.getCurrentInstance();
			checkListEjecucion.setFechaEjecucion(new Date());
			checkListEjecucion.setEstadoCheckList(EstadoCheckListEnum.EJECUTADO);
			iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucion, usuario);
			iCheckListEjecucionDao.generarActNivelRiesgo(checkListEjecucion.getNivelEvaluacion().getIdNivelEvaluacion(),
					checkListEjecucion.getIdCheckListEjecucion());
			pagina = "/PrimaxEvaluacion/pages/main.xhtml";
			contex.getExternalContext().redirect(pagina);
			if (checkListEjecucion.isModificado()) {
				enviarEmail(checkListEjecucion.getPlanificacion());
			}
			showInfo("Grabado con Éxito ", FacesMessage.SEVERITY_INFO);
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

	public void checkListKpiSeleccionadoArqueoCaja(CheckListKpiEjecucionEt checkListKpiEjecucion) {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			if (checkListKpiEjecucion.getDescripcion().toLowerCase().contains("arqueos pista")) {
				bloqueoTipo = true;
				checkListKpiEjecucion.setTipoEstacion(iTipoEstacionDao.getTipoEstacion(2L));
			} else if (checkListKpiEjecucion.getDescripcion().toLowerCase().contains("arqueos tienda")) {
				bloqueoTipo = true;
				checkListKpiEjecucion.setTipoEstacion(iTipoEstacionDao.getTipoEstacion(3L));
			}
			iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpiEjecucion, usuario);
			iCheckListKpiEjecucionDao.clear();
			iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucion, usuario);
			checkListKpiEjecucion = iCheckListKpiEjecucionDao
					.getCheckListKpiEjecucion(checkListKpiEjecucion.getIdCheckListKpiEjecucion());
			if (checkListKpiEjecucion.getArqueoCaja() == null || checkListKpiEjecucion.getArqueoCaja().isEmpty()) {
				checkListKpiEjecucion.setArqueoCaja(new ArrayList<>());
				generarDetalleArqueoCaja(checkListKpiEjecucion, usuario);
				iCheckListKpiEjecucionDao.clear();
				checkListKpiEjecucion = iCheckListKpiEjecucionDao
						.getCheckListKpiEjecucion(checkListKpiEjecucion.getIdCheckListKpiEjecucion());
			}
			checkListKpiEjecucion.setFechaArqueo(new Date());
			iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpiEjecucion, usuario);
			checkListKpiEjecucionSeleccionado = checkListKpiEjecucion;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método checkListKpiSeleccionado " + " " + e.getMessage());
		}
	}

	public void checkListKpiSeleccionadoArqueoCajaFuerte(CheckListKpiEjecucionEt checkListKpiEjecucion) {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpiEjecucion, usuario);
			iCheckListKpiEjecucionDao.clear();
			iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucion, usuario);
			checkListKpiEjecucion = iCheckListKpiEjecucionDao
					.getCheckListKpiEjecucion(checkListKpiEjecucion.getIdCheckListKpiEjecucion());
			if (checkListKpiEjecucion.getArqueoCajaFuerte() == null
					|| checkListKpiEjecucion.getArqueoCajaFuerte().isEmpty()) {
				checkListKpiEjecucion.setArqueoCajaFuerte(new ArrayList<>());
				generarDetalleArqueoCajaFuerte(checkListKpiEjecucion, usuario);
				iCheckListKpiEjecucionDao.clear();
				checkListKpiEjecucion = iCheckListKpiEjecucionDao
						.getCheckListKpiEjecucion(checkListKpiEjecucion.getIdCheckListKpiEjecucion());
			}
			iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpiEjecucion, usuario);
			checkListKpiEjecucionSeleccionado = checkListKpiEjecucion;
			sumarTabla0();
			sumarTabla1();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método checkListKpiSeleccionado " + " " + e.getMessage());
		}
	}

	public void checkListKpiSeleccionadoArqueoFondoSuelto(CheckListKpiEjecucionEt checkListKpiEjecucion) {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpiEjecucion, usuario);
			iCheckListKpiEjecucionDao.clear();
			iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucion, usuario);
			checkListKpiEjecucion = iCheckListKpiEjecucionDao
					.getCheckListKpiEjecucion(checkListKpiEjecucion.getIdCheckListKpiEjecucion());
			if (checkListKpiEjecucion.getArqueoFondoSuelto().isEmpty()) {
				checkListKpiEjecucion.setArqueoFondoSuelto(new ArrayList<>());
				guardarCheckListKpiEjecucionFirmaCaja(checkListKpiEjecucion);
				generarDetalleArqueoFondoSuelto(checkListKpiEjecucion, usuario);
				iCheckListKpiEjecucionDao.clear();
				checkListKpiEjecucion = iCheckListKpiEjecucionDao
						.getCheckListKpiEjecucion(checkListKpiEjecucion.getIdCheckListKpiEjecucion());
			}
			checkListKpiEjecucion.setFechaArqueo(new Date());
			checkListKpiEjecucionSeleccionado = checkListKpiEjecucion;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método checkListKpiSeleccionado " + " " + e.getMessage());
		}
	}

	public void checkListKpiSeleccionadoArqueoCajaChica(CheckListKpiEjecucionEt checkListKpiEjecucion) {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpiEjecucion, usuario);
			iCheckListKpiEjecucionDao.clear();
			iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucion, usuario);
			checkListKpiEjecucion = iCheckListKpiEjecucionDao
					.getCheckListKpiEjecucion(checkListKpiEjecucion.getIdCheckListKpiEjecucion());
			if (checkListKpiEjecucion.getArqueoCajaGeneral().isEmpty()) {
				checkListKpiEjecucion.setArqueoCajaGeneral(new ArrayList<>());
				guardarCheckListKpiEjecucionFirmaCaja(checkListKpiEjecucion);
				generarDetalleArqueoCajaChicaGeneral(checkListKpiEjecucion, usuario);
				iCheckListKpiEjecucionDao.clear();
				checkListKpiEjecucion = iCheckListKpiEjecucionDao
						.getCheckListKpiEjecucion(checkListKpiEjecucion.getIdCheckListKpiEjecucion());
			}
			checkListKpiEjecucion.setFechaArqueo(new Date());
			checkListKpiEjecucionSeleccionado = checkListKpiEjecucion;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método checkListKpiSeleccionadoArqueoCajaChica " + " " + e.getMessage());
		}
	}

	public void generarDetalleArqueoCaja(CheckListKpiEjecucionEt checkListKpiEjecucion, UsuarioEt usuario) {
		ArqueoCajaEt arqueoCaja = null;
		List<ArqueoCajaEt> arqueoCajas = new ArrayList<>();
		try {
			ParametrosGeneralesEt parametroParcialB = iParametrolGeneralDao.getObjPadre("43");
			ParametrosGeneralesEt parametroParcialM = iParametrolGeneralDao.getObjPadre("45");
			ParametrosGeneralesEt parcialB = iParametrolGeneralDao.getListaHIjosUnique(parametroParcialB);
			ParametrosGeneralesEt parcialM = iParametrolGeneralDao.getListaHIjosUnique(parametroParcialM);
			arqueoCaja = new ArqueoCajaEt();
			arqueoCaja.setOrden(0L);
			arqueoCaja.setCantidad(1L);
			arqueoCaja.setOrdenReporte(2L);
			arqueoCaja.setOrdenReporte1("B");
			arqueoCaja.setSubDescripcion("Efectivo USD(Billetes)");
			arqueoCaja.setDescripcion("Parcial");
			arqueoCaja.setCheckListKpiEjecucion(checkListKpiEjecucion);
			arqueoCaja.setValorSubTotal(Double.parseDouble(parcialB.getValorLista()));
			arqueoCaja.setValorTotal(Double.parseDouble(parcialB.getValorLista()));
			arqueoCajas.add(arqueoCaja);
			arqueoCaja = new ArqueoCajaEt();
			arqueoCaja.setOrden(0L);
			arqueoCaja.setCantidad(1L);
			arqueoCaja.setOrdenReporte(3L);
			arqueoCaja.setOrdenReporte1("B");
			arqueoCaja.setSubDescripcion("Efectivo USD(Billetes)");
			arqueoCaja.setDescripcion("Pico");
			arqueoCaja.setCheckListKpiEjecucion(checkListKpiEjecucion);
			arqueoCajas.add(arqueoCaja);
			arqueoCaja = new ArqueoCajaEt();
			arqueoCaja.setOrden(1L);
			arqueoCaja.setCantidad(1L);
			arqueoCaja.setOrdenReporte(4L);
			arqueoCaja.setOrdenReporte1("C");
			arqueoCaja.setSubDescripcion("Efectivo USD(Monedas)");
			arqueoCaja.setDescripcion("Parcial");
			arqueoCaja.setCheckListKpiEjecucion(checkListKpiEjecucion);
			arqueoCaja.setValorSubTotal(Double.parseDouble(parcialM.getValorLista()));
			arqueoCaja.setValorTotal(Double.parseDouble(parcialM.getValorLista()));
			arqueoCajas.add(arqueoCaja);
			arqueoCaja = new ArqueoCajaEt();
			arqueoCaja.setOrden(1L);
			arqueoCaja.setCantidad(1L);
			arqueoCaja.setOrdenReporte(5L);
			arqueoCaja.setOrdenReporte1("C");
			arqueoCaja.setSubDescripcion("Efectivo USD(Monedas)");
			arqueoCaja.setDescripcion("Pico");
			arqueoCaja.setCheckListKpiEjecucion(checkListKpiEjecucion);
			arqueoCajas.add(arqueoCaja);
			arqueoCaja = new ArqueoCajaEt();
			arqueoCaja.setOrden(2L);
			arqueoCaja.setCantidad(0L);
			arqueoCaja.setOrdenReporte(6L);
			arqueoCaja.setOrdenReporte1("D");
			arqueoCaja.setSubDescripcion("Tarjetas de Crédito");
			arqueoCaja.setDescripcion("Número de Vouchers");
			arqueoCaja.setCheckListKpiEjecucion(checkListKpiEjecucion);
			arqueoCajas.add(arqueoCaja);
			arqueoCaja = new ArqueoCajaEt();
			arqueoCaja.setOrden(3L);
			arqueoCaja.setCantidad(0L);
			arqueoCaja.setOrdenReporte(7L);
			arqueoCaja.setOrdenReporte1("E");
			arqueoCaja.setSubDescripcion("Créditos");
			arqueoCaja.setDescripcion("Número de Vales");
			arqueoCaja.setCheckListKpiEjecucion(checkListKpiEjecucion);
			arqueoCajas.add(arqueoCaja);
			arqueoCaja = new ArqueoCajaEt();
			arqueoCaja.setOrden(4L);
			arqueoCaja.setCantidad(0L);
			arqueoCaja.setOrdenReporte(8L);
			arqueoCaja.setOrdenReporte1("F");
			arqueoCaja.setSubDescripcion("Retención");
			arqueoCaja.setDescripcion("Número de Retención");
			arqueoCaja.setCheckListKpiEjecucion(checkListKpiEjecucion);
			arqueoCajas.add(arqueoCaja);
			for (ArqueoCajaEt arqueoCajaI : arqueoCajas) {
				iArqueoCajaDao.guardarArqueoCajaEt(arqueoCajaI, usuario);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método generarDetalleArqueoCaja " + " " + e.getMessage());
		}
	}

	public void generarDetalleArqueoCajaFuerte(CheckListKpiEjecucionEt checkListKpiEjecucion, UsuarioEt usuario) {
		ArqueoCajaFuerteEt arqueoCaja = null;
		List<ArqueoCajaFuerteEt> arqueoCajas = new ArrayList<>();
		try {
			arqueoCaja = new ArqueoCajaFuerteEt();
			arqueoCaja.setOrden(0L);
			arqueoCaja.setCantidad(1L);
			arqueoCaja.setOrdenReporte(2L);
			arqueoCaja.setOrdenReporte1("B");
			arqueoCaja.setSubDescripcion("Efectivo USD(Billetes)");
			arqueoCaja.setDescripcion("Parcial");
			arqueoCaja.setDescripcionReporte("Parciales Billetes");
			arqueoCaja.setCheckListKpiEjecucion(checkListKpiEjecucion);
			arqueoCaja.setValorBlindado(0D);
			arqueoCaja.setValorTotal(0D);
			arqueoCajas.add(arqueoCaja);
			arqueoCaja = new ArqueoCajaFuerteEt();
			arqueoCaja.setOrden(0L);
			arqueoCaja.setCantidad(1L);
			arqueoCaja.setOrdenReporte(3L);
			arqueoCaja.setOrdenReporte1("B");
			arqueoCaja.setSubDescripcion("Efectivo USD(Billetes)");
			arqueoCaja.setDescripcion("Pico");
			arqueoCaja.setDescripcionReporte("Picos Billetes");
			arqueoCaja.setCheckListKpiEjecucion(checkListKpiEjecucion);
			arqueoCajas.add(arqueoCaja);
			arqueoCaja = new ArqueoCajaFuerteEt();
			arqueoCaja.setOrden(1L);
			arqueoCaja.setCantidad(1L);
			arqueoCaja.setOrdenReporte(4L);
			arqueoCaja.setOrdenReporte1("C");
			arqueoCaja.setSubDescripcion("Efectivo USD(Monedas)");
			arqueoCaja.setDescripcion("Parcial");
			arqueoCaja.setDescripcionReporte("Parciales Monedas");
			arqueoCaja.setCheckListKpiEjecucion(checkListKpiEjecucion);
			arqueoCaja.setValorBlindado(0D);
			arqueoCaja.setValorTotal(0D);
			arqueoCajas.add(arqueoCaja);
			arqueoCaja = new ArqueoCajaFuerteEt();
			arqueoCaja.setOrden(1L);
			arqueoCaja.setCantidad(1L);
			arqueoCaja.setOrdenReporte(5L);
			arqueoCaja.setOrdenReporte1("C");
			arqueoCaja.setSubDescripcion("Efectivo USD(Monedas)");
			arqueoCaja.setDescripcion("Pico");
			arqueoCaja.setDescripcionReporte("Picos Monedas");
			arqueoCaja.setCheckListKpiEjecucion(checkListKpiEjecucion);
			arqueoCajas.add(arqueoCaja);
			arqueoCaja = new ArqueoCajaFuerteEt();
			arqueoCaja.setOrden(2L);
			arqueoCaja.setCantidad(0L);
			arqueoCaja.setOrdenReporte(6L);
			arqueoCaja.setOrdenReporte1("D");
			arqueoCaja.setSubDescripcion("Cheques");
			arqueoCaja.setDescripcionReporte("Cheque");
			arqueoCaja.setDescripcion("Número de Cheques");
			arqueoCaja.setCheckListKpiEjecucion(checkListKpiEjecucion);
			arqueoCajas.add(arqueoCaja);
			for (ArqueoCajaFuerteEt arqueoCajaI : arqueoCajas) {
				iArqueoCajaFuerteDao.guardarArqueoCajaFuerte(arqueoCajaI, usuario);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método generarDetalleArqueoCaja " + " " + e.getMessage());
		}
	}

	public void generarDetalleArqueoFondoSuelto(CheckListKpiEjecucionEt checkListKpiEjecucion, UsuarioEt usuario) {
		ArqueoFondoSueltoEt arqueoFondoSuelto = null;
		List<ArqueoFondoSueltoEt> arqueoFondoSueltos = new ArrayList<>();
		List<ParametrosGeneralesEt> billetes = new ArrayList<>();
		List<ParametrosGeneralesEt> monedas = new ArrayList<>();
		try {
			ParametrosGeneralesEt parametroB = iParametrolGeneralDao.getObjPadre("30");
			ParametrosGeneralesEt parametroM = iParametrolGeneralDao.getObjPadre("36");
			billetes = iParametrolGeneralDao.getListaHIjos(parametroB);
			monedas = iParametrolGeneralDao.getListaHIjos(parametroM);
			for (ParametrosGeneralesEt parametroBilletes : billetes) {
				arqueoFondoSuelto = new ArqueoFondoSueltoEt();
				arqueoFondoSuelto.setOrden(0L);
				arqueoFondoSuelto.setCaja0(0L);
				arqueoFondoSuelto.setCaja1(0L);
				arqueoFondoSuelto.setCaja2(0L);
				arqueoFondoSuelto.setValorTotal(0.0D);
				arqueoFondoSuelto.setCheckListKpiEjecucion(checkListKpiEjecucion);
				arqueoFondoSuelto.setDescripcion(parametroBilletes.getNombreLista());
				arqueoFondoSuelto.setDenominacion(Double.parseDouble(parametroBilletes.getValorLista()));
				arqueoFondoSuelto.setSubDescripcion(parametroBilletes.getParametroPadre().getNombreLista());
				arqueoFondoSueltos.add(arqueoFondoSuelto);
			}
			for (ParametrosGeneralesEt parametroMonedas : monedas) {
				arqueoFondoSuelto = new ArqueoFondoSueltoEt();
				arqueoFondoSuelto.setOrden(1L);
				arqueoFondoSuelto.setCaja0(0L);
				arqueoFondoSuelto.setCaja1(0L);
				arqueoFondoSuelto.setCaja2(0L);
				arqueoFondoSuelto.setValorTotal(0.0D);
				arqueoFondoSuelto.setCheckListKpiEjecucion(checkListKpiEjecucion);
				arqueoFondoSuelto.setDescripcion(parametroMonedas.getNombreLista());
				arqueoFondoSuelto.setDenominacion(Double.parseDouble(parametroMonedas.getValorLista()));
				arqueoFondoSuelto.setSubDescripcion(parametroMonedas.getParametroPadre().getNombreLista());
				arqueoFondoSueltos.add(arqueoFondoSuelto);

			}
			for (ArqueoFondoSueltoEt arqueoFondoSueltoI : arqueoFondoSueltos) {
				iArqueoFondoSueltoDao.guardarArqueoFondoSuelto(arqueoFondoSueltoI, usuario);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método generarDetalleArqueoCaja " + " " + e.getMessage());
		}
	}

	public void generarDetalleArqueoCajaChicaGeneral(CheckListKpiEjecucionEt checkListKpiEjecucion, UsuarioEt usuario) {
		ArqueoCajaGeneralEt arqueoCajaGeneral = null;
		List<ParametrosGeneralesEt> monedas = new ArrayList<>();
		List<ParametrosGeneralesEt> billetes = new ArrayList<>();
		List<ArqueoCajaGeneralEt> arqueoCajaGenerales = new ArrayList<>();
		try {
			ParametrosGeneralesEt parametroB = iParametrolGeneralDao.getObjPadre("30");
			ParametrosGeneralesEt parametroM = iParametrolGeneralDao.getObjPadre("36");
			billetes = iParametrolGeneralDao.getListaHIjos(parametroB);
			monedas = iParametrolGeneralDao.getListaHIjos(parametroM);
			for (ParametrosGeneralesEt parametroBilletes : billetes) {
				arqueoCajaGeneral = new ArqueoCajaGeneralEt();
				arqueoCajaGeneral.setOrden(1L);
				arqueoCajaGeneral.setCaja0(0.0D);
				arqueoCajaGeneral.setSubTotal(0.0D);
				arqueoCajaGeneral.setCheckListKpiEjecucion(checkListKpiEjecucion);
				arqueoCajaGeneral.setDescripcion(parametroBilletes.getNombreLista());
				arqueoCajaGeneral.setDenominacion(Double.parseDouble(parametroBilletes.getValorLista()));
				arqueoCajaGeneral.setSubDescripcion(parametroBilletes.getParametroPadre().getNombreLista());
				arqueoCajaGenerales.add(arqueoCajaGeneral);
			}
			for (ParametrosGeneralesEt parametroMonedas : monedas) {
				arqueoCajaGeneral = new ArqueoCajaGeneralEt();
				arqueoCajaGeneral.setOrden(2L);
				arqueoCajaGeneral.setCaja0(0.0D);
				arqueoCajaGeneral.setSubTotal(0.0D);
				arqueoCajaGeneral.setCheckListKpiEjecucion(checkListKpiEjecucion);
				arqueoCajaGeneral.setDescripcion(parametroMonedas.getNombreLista());
				arqueoCajaGeneral.setDenominacion(Double.parseDouble(parametroMonedas.getValorLista()));
				arqueoCajaGeneral.setSubDescripcion(parametroMonedas.getParametroPadre().getNombreLista());
				arqueoCajaGenerales.add(arqueoCajaGeneral);
			}
			arqueoCajaGeneral = new ArqueoCajaGeneralEt();
			arqueoCajaGeneral.setOrden(3L);
			arqueoCajaGeneral.setCaja0(0.0D);
			arqueoCajaGeneral.setSubTotal(0.0D);
			arqueoCajaGeneral.setDescripcion("Reposición de caja chica");
			arqueoCajaGeneral.setSubDescripcion("Solicitud");
			arqueoCajaGeneral.setCheckListKpiEjecucion(checkListKpiEjecucion);
			arqueoCajaGenerales.add(arqueoCajaGeneral);
			for (ArqueoCajaGeneralEt arqueoCajaGeneralI : arqueoCajaGenerales) {
				iArqueoCajaGeneralDao.guardarArqueoCajaGeneral(arqueoCajaGeneralI, usuario);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método generarDetalleArqueoCajaChicaGeneral " + " " + e.getMessage());
		}
	}

	public void generarDetalleArqueoCajaChicaRubro() {
		String mensaje = "";
		Double totalRubro = 0.0D;
		boolean condicion = false;
		ArqueoCajaGeneralEt arqueoCajaGeneral = null;
		try {
			mensaje = validacionAgregarRubro();
			if (!mensaje.equals("")) {
				showInfo(mensaje, FacesMessage.SEVERITY_INFO);
				return;
			}
			UsuarioEt usuario = appMain.getUsuario();
			List<ArqueoCajaGeneralEt> result = checkListKpiEjecucionSeleccionado.getArqueoCajaGeneral().stream()
					.filter(x -> x.getParametroRubro() != null).collect(Collectors.toList());
			if (result != null) {
				for (ArqueoCajaGeneralEt arqueoCajaGeneralC : result) {
					if (arqueoCajaGeneralC.getDescripcion().equals(tipoRubroSeleccionado.getNombreLista())) {
						totalRubro = arqueoCajaGeneralC.getSubTotal() + valorRubro;
						arqueoCajaGeneral = new ArqueoCajaGeneralEt();
						arqueoCajaGeneral.setOrden(0L);
						arqueoCajaGeneral.setBloqueado(true);
						arqueoCajaGeneral.setDenominacion(0.0);
						arqueoCajaGeneral.setSubTotal(totalRubro);
						arqueoCajaGeneral.setUsuarioRegistra(usuario);
						arqueoCajaGeneral.setParametroRubro(tipoRubroSeleccionado);
						arqueoCajaGeneral.setDescripcion(tipoRubroSeleccionado.getNombreLista());
						arqueoCajaGeneral.setCheckListKpiEjecucion(checkListKpiEjecucionSeleccionado);
						arqueoCajaGeneral.setSubDescripcion(tipoRubroSeleccionado.getParametroPadre().getNombreLista());
						arqueoCajaGeneralC.setEstado(EstadoEnum.INA);
						iArqueoCajaGeneralDao.guardarArqueoCajaGeneral(arqueoCajaGeneralC, usuario);
						checkListKpiEjecucionSeleccionado.getArqueoCajaGeneral().remove(arqueoCajaGeneralC);
						checkListKpiEjecucionSeleccionado.getArqueoCajaGeneral().add(arqueoCajaGeneral);
						iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpiEjecucionSeleccionado,
								usuario);
						sumarArqueoCajaChica(arqueoCajaGeneral);
						condicion = true;
						break;
					}
				}
			}
			if (!condicion) {
				arqueoCajaGeneral = new ArqueoCajaGeneralEt();
				arqueoCajaGeneral.setOrden(0L);
				arqueoCajaGeneral.setCaja0(0.0D);
				arqueoCajaGeneral.setBloqueado(true);
				arqueoCajaGeneral.setDenominacion(0.0);
				arqueoCajaGeneral.setSubTotal(valorRubro);
				arqueoCajaGeneral.setUsuarioRegistra(usuario);
				arqueoCajaGeneral.setParametroRubro(tipoRubroSeleccionado);
				arqueoCajaGeneral.setDescripcion(tipoRubroSeleccionado.getNombreLista());
				arqueoCajaGeneral.setCheckListKpiEjecucion(checkListKpiEjecucionSeleccionado);
				arqueoCajaGeneral.setSubDescripcion(tipoRubroSeleccionado.getParametroPadre().getNombreLista());
				iArqueoCajaGeneralDao.guardarArqueoCajaGeneral(arqueoCajaGeneral, usuario);
				checkListKpiEjecucionSeleccionado.getArqueoCajaGeneral().add(arqueoCajaGeneral);
				iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpiEjecucionSeleccionado, usuario);
				sumarArqueoCajaChica(arqueoCajaGeneral);
			}
			tipoRubroSeleccionado = null;
			valorRubro = 0.0D;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método generarDetalleArqueoCajaChicaRubro " + " " + e.getMessage());
		}
	}

	public String validacionAgregarRubro() {
		String mensaje = "";
		try {
			if (checkListKpiEjecucionSeleccionado.getValorVenta() == 0.0D) {
				mensaje = "Por favor ingresar valor de caja chica para continuar";
				return mensaje;
			}
			if (tipoRubroSeleccionado == null) {
				mensaje = "Por favor Seleccionar un tipo de rubro para continuar";
				return mensaje;
			}
			if (valorRubro == 0.0D) {
				mensaje = "Por favor ingresar valor de rubro para continuar";
				return mensaje;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método validarAgregarRubro " + " " + e.getMessage());
		}
		return mensaje;
	}

	public String validacionGuardarArqueoCajaC() {
		String mensaje = "";
		try {
			if (checkListKpiEjecucionSeleccionado.getNombreVendedor() == null
					|| checkListKpiEjecucionSeleccionado.getNombreVendedor().equals("")) {
				mensaje = "Por favor ingresar nombre de vendedor para continuar";
				return mensaje;
			}

			if (checkListKpiEjecucionSeleccionado.getTurno() == null
					|| checkListKpiEjecucionSeleccionado.getTurno() == 0L) {
				mensaje = "Por favor Seleccionar un turno para continuar";
				return mensaje;
			}

			if (checkListKpiEjecucionSeleccionado.getTipoEstacion() == null) {
				mensaje = "Por favor Seleccionar un tipo de estación para continuar";
				return mensaje;
			}
			if (checkListKpiEjecucionSeleccionado.getAuditor() == null) {
				mensaje = "Por favor Seleccionar un auditor para continuar";
				return mensaje;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método validacionGuardarArqueoCajaC " + " " + e.getMessage());
		}
		return mensaje;
	}

	public void nuevoArqueoCaja() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucion, usuario);
			CheckListKpiEjecucionEt checkListKpiEjecucion = new CheckListKpiEjecucionEt();
			checkListKpiEjecucion.setVisualizar(false);
			checkListKpiEjecucion.setOrden(checkListKpiEjecucionSeleccionado.getOrden());
			checkListKpiEjecucion.setPuntaje(checkListKpiEjecucionSeleccionado.getPuntaje());
			checkListKpiEjecucion.setEsfuerzo(checkListKpiEjecucionSeleccionado.getEsfuerzo());
			checkListKpiEjecucion.setDescripcion(checkListKpiEjecucionSeleccionado.getDescripcion());
			checkListKpiEjecucion.setProcesoDetalle(checkListKpiEjecucionSeleccionado.getProcesoDetalle());
			checkListKpiEjecucion.setCriterioEvaluacion(checkListKpiEjecucionSeleccionado.getCriterioEvaluacion());
			checkListKpiEjecucion
					.setCheckListProcesoEjecucion(checkListKpiEjecucionSeleccionado.getCheckListProcesoEjecucion());
			iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpiEjecucion, usuario);
			showInfo("Arqueo de Caja agregado ", FacesMessage.SEVERITY_INFO);
			RequestContext.getCurrentInstance().execute("PF('dlg_eje_003').hide();");
			iCheckListEjecucionDao.clear();
			checkListEjecucion = iCheckListEjecucionDao.getCheckListEjecucion(TipoCheckListEnum.CRITERIO_ESPECIFICO,
					usuario);
			checkListProcesoEjecucionEfectivo = null;
			checkListProcesoEjecucionEfectivo = iCheckListProcesoEjecucionDao.getCheckListProcesoE(
					checkListKpiEjecucionSeleccionado.getCheckListProcesoEjecucion().getIdCheckListProcesoEjecucion());
			checkListKpiEjecucionSeleccionado = null;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método nuevoArqueoCaja " + " " + e.getMessage());
		}
	}

	public void eliminarArqueoCaja() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			checkListKpiEjecucionSeleccionado.setEstado(EstadoEnum.ELI);
			iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpiEjecucionSeleccionado, usuario);
			RequestContext.getCurrentInstance().execute("PF('dlg_eje_003').hide();");
			iCheckListEjecucionDao.clear();
			buscar();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método eliminarArqueoCaja " + " " + e.getMessage());
		}
	}

	public void listenerNovedadCantidad(FileUploadEvent event) {
		try {
			iCheckListKpiEjecucionCDao.limpiarReporte(checkListProcesoEjecucionTienda.getIdCheckListProcesoEjecucion());
			InputStream is2 = event.getFile().getInputstream();
			List<RowPoi> listCols2;
			listCols2 = POIReader.getColumsFromXLSXFile(is2, 0, false, "");
			if (checkListProcesoEjecucionTienda.getCheckListKpiEjecucionC() != null
					|| checkListProcesoEjecucionTienda.getCheckListKpiEjecucionC().isEmpty()) {
				checkListProcesoEjecucionTienda.setCheckListKpiEjecucionC(new ArrayList<>());
				checkListProcesoEjecucionTienda.setCheckListKpiEjecucionC(
						procesarRegistrosNovedad(listCols2, checkListProcesoEjecucionTienda));
				reemplazarNovedad(checkListProcesoEjecucionTienda,
						checkListProcesoEjecucionTienda.getCheckListKpiEjecucionA());
				if (!checkListProcesoEjecucionTienda.getCheckListKpiEjecucionC().isEmpty()) {
					mostrarDescripcion(checkListProcesoEjecucionTienda.getCheckListKpiEjecucionC());
				}
			}
			iCheckListProcesoEjecucionDao.guardarCheckListProcesoEjecucion(checkListProcesoEjecucionTienda,
					appMain.getUsuario());
			iCheckListProcesoEjecucionDao.clear();
			checkListProcesoEjecucionTienda = iCheckListProcesoEjecucionDao
					.getCheckListProcesoE(checkListProcesoEjecucionTienda.getIdCheckListProcesoEjecucion());
			showInfo("Archivo cargado con Éxito", FacesMessage.SEVERITY_INFO);
			RequestContext.getCurrentInstance().execute("PF('dlg_eje_008_1').hide();");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método listenerNovedadCantidad " + " " + e.getMessage());
		}
	}

	public void mostrarDescripcion(List<CheckListKpiEjecucionCEt> checkListKpiEjecucionC) {
		Long cantidad = 0L;
		Double valor = 0.0D;
		Double valorF = 0.0D;
		try {
			valor += checkListKpiEjecucionC.stream()
					.filter(p -> p.getTipo() == 2 && p.getDescripcion().contains("Total"))
					.mapToDouble(p -> p.getTotal()).sum();
			cantidad = (long) checkListKpiEjecucionC.stream()
					.filter(p -> p.getTipo() == 1 && p.getDescripcion().contains("Total"))
					.mapToDouble(p -> p.getTotal()).sum();
			valorF = new BigDecimal(valor).setScale(2, RoundingMode.HALF_UP).doubleValue();
			if (valorF != null && cantidad != null) {
				descripcionN = "";
				descripcionN = "Archivo cargado con " + cantidad + " " + "diferencias en cantidad y " + " " + valorF
						+ " " + "diferencias en valor.";
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método mostrarDescripcion " + " " + e.getMessage());
		}
	}

	public String reemplazarNovedad(CheckListProcesoEjecucionEt CheckListP, List<CheckListKpiEjecucionAEt> checkList) {
		String des = "";
		Long cantidad = 0L;
		Double valor = 0.0D;
		String descripcion = "";
		try {
			if (CheckListP.getCheckListKpiEjecucionC() != null && !CheckListP.getCheckListKpiEjecucionC().isEmpty()) {
				cantidad = (long) checkListProcesoEjecucionTienda.getCheckListKpiEjecucionC().stream()
						.filter(p -> p.getTipo() == 1 && p.getDescripcion().contains("Total"))
						.mapToDouble(p -> p.getTotal()).sum();
				valor = new BigDecimal(checkListProcesoEjecucionTienda.getCheckListKpiEjecucionC().stream()
						.filter(p -> p.getTipo() == 2 && p.getDescripcion().contains("Total"))
						.mapToDouble(p -> p.getTotal()).sum()).setScale(2, RoundingMode.HALF_UP).doubleValue();
			}
			for (CheckListKpiEjecucionAEt checkListKpi : checkList) {
				descripcion = checkListKpi.getDescripcion().replace("{cantidad}", "" + cantidad);
				des = descripcion.replace("{valor}", "" + valor);
				checkListKpi.setDescripcion(des);
				iCheckListKpiEjecucionADao.guardaCheckListKpiEjecucion(checkListKpi, appMain.getUsuario());
			}
			System.out.println("Texto" + " " + des);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método reemplazarNovedad " + " " + e.getMessage());
		}
		return des;
	}

	private List<CheckListKpiEjecucionCEt> procesarRegistrosNovedad(List<RowPoi> list, CheckListProcesoEjecucionEt cab)
			throws ParseException {

		Long tipo = 0L;
		Long columnaT = 0L;
		Long columnaFin = 0L;
		boolean tipoC = false;
		List<CheckListKpiEjecucionCEt> registros = new ArrayList<>();
		for (int i = 1; i <= list.size(); i++) {
			System.out.println(i);
			if (columnaFin == 0L) {
				columnaFin = (long) list.get(i).getCells().size() - 1;
				columnaT = columnaFin - 1;
			}
			try {
				CheckListKpiEjecucionCEt registro = new CheckListKpiEjecucionCEt();
				registro.setOrden(Long.parseLong("" + i));
				registro.setCheckListProcesoEjecucion(cab);
				registro.setDescripcion(list.get(i).getCells().get(0).getValue() == null ? null
						: list.get(i).getCells().get(0).getValue().toString());
				if ((registro.getDescripcion().toLowerCase().contains("usuario"))) {
					tipo += 1L;
				}
				// System.out.println("Nombre Usuario" + " " +
				// registro.getDescripcion());
				registro.setTipo(tipo);
				if (1 < columnaFin && columnaT == 1L) {
					tipoC = list.get(i).getCells().get(1).getValue().toString().toLowerCase().contains("total");
					registro.setTotal(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(1).getValue().toString()));
				} else if (1 < columnaFin) {
					tipoC = list.get(i).getCells().get(1).getValue() == null ? true
							: list.get(i).getCells().get(1).getValue().toString().toLowerCase().contains("total");
					registro.setDia1(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(1).getValue().toString()));
				}
				if (2 < columnaFin && columnaT == 2L) {
					tipoC = list.get(i).getCells().get(2).getValue().toString().toLowerCase().contains("total");
					registro.setTotal(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(2).getValue().toString()));
				} else if (2 < columnaFin) {
					tipoC = list.get(i).getCells().get(2).getValue() == null ? true
							: list.get(i).getCells().get(2).getValue().toString().toLowerCase().contains("total");
					registro.setDia2(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(2).getValue().toString()));
				}
				if (3 < columnaFin && columnaT == 3L) {
					tipoC = list.get(i).getCells().get(3).getValue().toString().toLowerCase().contains("total");
					registro.setTotal(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(3).getValue().toString()));
				} else if (3 < columnaFin) {
					tipoC = list.get(i).getCells().get(3).getValue() == null ? true
							: list.get(i).getCells().get(3).getValue().toString().toLowerCase().contains("total");
					registro.setDia3(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(3).getValue().toString()));
				}
				if (4 < columnaFin && columnaT == 4L) {
					tipoC = list.get(i).getCells().get(4).getValue().toString().toLowerCase().contains("total");
					registro.setTotal(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(4).getValue().toString()));
				} else if (4 < columnaFin) {
					tipoC = list.get(i).getCells().get(4).getValue() == null ? true
							: list.get(i).getCells().get(4).getValue().toString().toLowerCase().contains("total");
					registro.setDia4(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(4).getValue().toString()));
				}
				if (5 < columnaFin && columnaT == 5L) {
					tipoC = list.get(i).getCells().get(5).getValue().toString().toLowerCase().contains("total");
					registro.setTotal(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(5).getValue().toString()));
				} else if (5 < columnaFin) {
					tipoC = list.get(i).getCells().get(5).getValue() == null ? true
							: list.get(i).getCells().get(5).getValue().toString().toLowerCase().contains("total");
					registro.setDia5(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(5).getValue().toString()));
				}
				if (6 < columnaFin && columnaT == 6L) {
					tipoC = list.get(i).getCells().get(6).getValue().toString().toLowerCase().contains("total");
					registro.setTotal(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(6).getValue().toString()));
				} else if (6 < columnaFin) {
					tipoC = list.get(i).getCells().get(6).getValue() == null ? true
							: list.get(i).getCells().get(6).getValue().toString().toLowerCase().contains("total");
					registro.setDia6(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(6).getValue().toString()));
				}
				if (7 < columnaFin && columnaT == 7L) {
					tipoC = list.get(i).getCells().get(7).getValue().toString().toLowerCase().contains("total");
					registro.setTotal(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(7).getValue().toString()));
				} else if (7 < columnaFin) {
					tipoC = list.get(i).getCells().get(7).getValue() == null ? true
							: list.get(i).getCells().get(7).getValue().toString().toLowerCase().contains("total");
					registro.setDia7(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(7).getValue().toString()));
				}
				if (8 < columnaFin && columnaT == 8L) {
					tipoC = list.get(i).getCells().get(8).getValue().toString().toLowerCase().contains("total");
					registro.setTotal(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(8).getValue().toString()));
				} else if (8 < columnaFin) {
					tipoC = list.get(i).getCells().get(8).getValue() == null ? true
							: list.get(i).getCells().get(8).getValue().toString().toLowerCase().contains("total");
					registro.setDia8(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(8).getValue().toString()));
				}
				if (9 < columnaFin && columnaT == 9L) {
					tipoC = list.get(i).getCells().get(9).getValue().toString().toLowerCase().contains("total");
					registro.setTotal(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(9).getValue().toString()));
				} else if (9 < columnaFin) {
					tipoC = list.get(i).getCells().get(9).getValue() == null ? true
							: list.get(i).getCells().get(9).getValue().toString().toLowerCase().contains("total");
					registro.setDia9(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(9).getValue().toString()));
				}
				if (10 < columnaFin && columnaT == 10L) {
					tipoC = list.get(i).getCells().get(10).getValue().toString().toLowerCase().contains("total");
					registro.setTotal(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(10).getValue().toString()));
				} else if (10 < columnaFin) {
					tipoC = list.get(i).getCells().get(10).getValue() == null ? true
							: list.get(i).getCells().get(10).getValue().toString().toLowerCase().contains("total");
					registro.setDia10(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(10).getValue().toString()));
				}
				if (11 < columnaFin && columnaT == 11L) {
					tipoC = list.get(i).getCells().get(11).getValue().toString().toLowerCase().contains("total");
					registro.setTotal(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(11).getValue().toString()));
				} else if (11 < columnaFin) {
					tipoC = list.get(i).getCells().get(11).getValue() == null ? true
							: list.get(i).getCells().get(11).getValue().toString().toLowerCase().contains("total");
					registro.setDia11(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(11).getValue().toString()));
				}
				if (12 < columnaFin && columnaT == 12L) {
					tipoC = list.get(i).getCells().get(12).getValue().toString().toLowerCase().contains("total");
					registro.setTotal(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(12).getValue().toString()));
				} else if (12 < columnaFin) {
					if (list.get(i).getCells().get(12).getValue() != null) {
						tipoC = list.get(i).getCells().get(12).getValue().toString().toLowerCase().contains("total");
						registro.setDia12(Double.parseDouble(list.get(i).getCells().get(12).getValue().toString()));
					}
				}
				if (13 < columnaFin && columnaT == 13L) {
					tipoC = list.get(i).getCells().get(13).getValue().toString().toLowerCase().contains("total");
					registro.setTotal(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(13).getValue().toString()));
				} else if (13 < columnaFin) {
					tipoC = list.get(i).getCells().get(13).getValue() == null ? true
							: list.get(i).getCells().get(13).getValue().toString().toLowerCase().contains("total");
					registro.setDia13(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(13).getValue().toString()));
				}
				if (14 < columnaFin && columnaT == 14L) {
					tipoC = list.get(i).getCells().get(14).getValue().toString().toLowerCase().contains("total");
					registro.setTotal(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(14).getValue().toString()));
				} else if (14 < columnaFin) {
					tipoC = list.get(i).getCells().get(14).getValue() == null ? true
							: list.get(i).getCells().get(14).getValue().toString().toLowerCase().contains("total");
					registro.setDia14(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(14).getValue().toString()));
				}
				if (15 < columnaFin && columnaT == 15L) {
					tipoC = list.get(i).getCells().get(15).getValue().toString().toLowerCase().contains("total");
					registro.setTotal(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(15).getValue().toString()));
				} else if (15 < columnaFin) {
					tipoC = list.get(i).getCells().get(15).getValue() == null ? true
							: list.get(i).getCells().get(15).getValue().toString().toLowerCase().contains("total");
					registro.setDia15(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(15).getValue().toString()));
				}
				if (16 < columnaFin && columnaT == 16L) {
					tipoC = list.get(i).getCells().get(16).getValue().toString().toLowerCase().contains("total");
					registro.setTotal(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(16).getValue().toString()));
				} else if (16 < columnaFin) {
					tipoC = list.get(i).getCells().get(16).getValue() == null ? true
							: list.get(i).getCells().get(16).getValue().toString().toLowerCase().contains("total");
					registro.setDia16(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(16).getValue().toString()));
				}
				if (17 < columnaFin && columnaT == 17L) {
					tipoC = list.get(i).getCells().get(17).getValue().toString().toLowerCase().contains("total");
					registro.setTotal(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(17).getValue().toString()));
				} else if (17 < columnaFin) {
					tipoC = list.get(i).getCells().get(17).getValue() == null ? true
							: list.get(i).getCells().get(17).getValue().toString().toLowerCase().contains("total");
					registro.setDia17(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(17).getValue().toString()));
				}
				if (18 < columnaFin && columnaT == 18L) {
					tipoC = list.get(i).getCells().get(18).getValue().toString().toLowerCase().contains("total");
					registro.setTotal(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(18).getValue().toString()));
				} else if (18 < columnaFin) {
					tipoC = list.get(i).getCells().get(18).getValue() == null ? true
							: list.get(i).getCells().get(18).getValue().toString().toLowerCase().contains("total");
					registro.setDia18(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(18).getValue().toString()));
				}
				if (19 < columnaFin && columnaT == 19L) {
					tipoC = list.get(i).getCells().get(19).getValue().toString().toLowerCase().contains("total");
					registro.setTotal(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(19).getValue().toString()));
				} else if (19 < columnaFin) {
					tipoC = list.get(i).getCells().get(19).getValue() == null ? true
							: list.get(i).getCells().get(19).getValue().toString().toLowerCase().contains("total");
					registro.setDia19(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(19).getValue().toString()));
				}
				if (20 < columnaFin && columnaT == 20L) {
					tipoC = list.get(i).getCells().get(20).getValue().toString().toLowerCase().contains("total");
					registro.setTotal(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(20).getValue().toString()));
				} else if (20 < columnaFin) {
					tipoC = list.get(i).getCells().get(20).getValue() == null ? true
							: list.get(i).getCells().get(20).getValue().toString().toLowerCase().contains("total");
					registro.setDia20(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(20).getValue().toString()));
				}
				if (21 < columnaFin && columnaT == 21L) {
					tipoC = list.get(i).getCells().get(21).getValue().toString().toLowerCase().contains("total");
					registro.setTotal(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(21).getValue().toString()));
				} else if (21 < columnaFin) {
					tipoC = list.get(i).getCells().get(21).getValue() == null ? true
							: list.get(i).getCells().get(21).getValue().toString().toLowerCase().contains("total");
					registro.setDia21(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(21).getValue().toString()));
				}
				if (22 < columnaFin && columnaT == 22L) {
					tipoC = list.get(i).getCells().get(22).getValue().toString().toLowerCase().contains("total");
					registro.setTotal(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(22).getValue().toString()));
				} else if (22 < columnaFin) {
					tipoC = list.get(i).getCells().get(22).getValue() == null ? true
							: list.get(i).getCells().get(22).getValue().toString().toLowerCase().contains("total");
					registro.setDia22(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(22).getValue().toString()));
				}
				if (23 < columnaFin && columnaT == 23L) {
					tipoC = list.get(i).getCells().get(23).getValue().toString().toLowerCase().contains("total");
					registro.setTotal(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(23).getValue().toString()));
				} else if (23 < columnaFin) {
					tipoC = list.get(i).getCells().get(23).getValue() == null ? true
							: list.get(i).getCells().get(23).getValue().toString().toLowerCase().contains("total");
					registro.setDia23(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(23).getValue().toString()));
				}
				if (24 < columnaFin && columnaT == 24L) {
					tipoC = list.get(i).getCells().get(24).getValue().toString().toLowerCase().contains("total");
					registro.setTotal(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(24).getValue().toString()));
				} else if (24 < columnaFin) {
					tipoC = list.get(i).getCells().get(24).getValue() == null ? true
							: list.get(i).getCells().get(24).getValue().toString().toLowerCase().contains("total");
					registro.setDia24(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(24).getValue().toString()));
				}
				if (25 < columnaFin && columnaT == 25L) {
					tipoC = list.get(i).getCells().get(25).getValue().toString().toLowerCase().contains("total");
					registro.setTotal(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(25).getValue().toString()));
				} else if (25 < columnaFin) {
					tipoC = list.get(i).getCells().get(25).getValue() == null ? true
							: list.get(i).getCells().get(25).getValue().toString().toLowerCase().contains("total");
					registro.setDia25(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(25).getValue().toString()));
				}
				if (26 < columnaFin && columnaT == 26L) {
					tipoC = list.get(i).getCells().get(26).getValue().toString().toLowerCase().contains("total");
					registro.setTotal(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(26).getValue().toString()));
				} else if (26 < columnaFin) {
					tipoC = list.get(i).getCells().get(26).getValue() == null ? true
							: list.get(i).getCells().get(26).getValue().toString().toLowerCase().contains("total");
					registro.setDia26(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(26).getValue().toString()));
				}
				if (27 < columnaFin && columnaT == 27L) {
					tipoC = list.get(i).getCells().get(27).getValue().toString().toLowerCase().contains("total");
					registro.setTotal(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(27).getValue().toString()));
				} else if (27 < columnaFin) {
					tipoC = list.get(i).getCells().get(27).getValue() == null ? true
							: list.get(i).getCells().get(27).getValue().toString().toLowerCase().contains("total");
					registro.setDia27(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(27).getValue().toString()));
				}
				if (28 < columnaFin && columnaT == 28L) {
					tipoC = list.get(i).getCells().get(28).getValue().toString().toLowerCase().contains("total");
					registro.setTotal(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(28).getValue().toString()));
				} else if (28 < columnaFin) {
					tipoC = list.get(i).getCells().get(28).getValue() == null ? true
							: list.get(i).getCells().get(28).getValue().toString().toLowerCase().contains("total");
					registro.setDia28(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(28).getValue().toString()));
				}
				if (29 < columnaFin && columnaT == 29L) {
					tipoC = list.get(i).getCells().get(29).getValue().toString().toLowerCase().contains("total");
					registro.setTotal(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(29).getValue().toString()));
				} else if (29 < columnaFin) {
					tipoC = list.get(i).getCells().get(29).getValue() == null ? true
							: list.get(i).getCells().get(29).getValue().toString().toLowerCase().contains("total");
					registro.setDia29(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(29).getValue().toString()));
				}
				if (30 < columnaFin && columnaT == 30L) {
					tipoC = list.get(i).getCells().get(30).getValue().toString().toLowerCase().contains("total");
					registro.setTotal(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(30).getValue().toString()));
				} else if (30 < columnaFin) {
					tipoC = list.get(i).getCells().get(30).getValue() == null ? true
							: list.get(i).getCells().get(30).getValue().toString().toLowerCase().contains("total");
					registro.setDia30(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(30).getValue().toString()));
				}
				if (31 < columnaFin && columnaT == 31L) {
					tipoC = list.get(i).getCells().get(31).getValue().toString().toLowerCase().contains("total");
					registro.setTotal(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(31).getValue().toString()));
				} else if (31 < columnaFin) {
					tipoC = list.get(i).getCells().get(31).getValue() == null ? true
							: list.get(i).getCells().get(31).getValue().toString().toLowerCase().contains("total");
					registro.setDia31(
							tipoC ? 0D : Double.parseDouble(list.get(i).getCells().get(31).getValue().toString()));
				}
				registros.add(registro);
			} catch (Exception e) {
				System.out.println(i);
			}
		}
		return registros;
	}

	public void listenerNovedadValor(FileUploadEvent event) {
		try {

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método listenerNovedadValor " + " " + e.getMessage());
		}
	}

	public void sumarTabla0() {
		try {
			valorVenta = 0D;
			valorBlindado = 0D;
			valorDiferencia = 0D;
			valorBlindado += checkListKpiEjecucionSeleccionado.getArqueoCajaFuerte().stream()
					.mapToDouble(p -> p.getValorBlindado()).sum();
			valorVenta += checkListKpiEjecucionSeleccionado.getArqueoCajaFuerte().stream()
					.mapToDouble(p -> p.getValorVendedor()).sum();
			valorDiferencia += checkListKpiEjecucionSeleccionado.getArqueoCajaFuerte().stream()
					.mapToDouble(p -> p.getValorDiferencia()).sum();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método sumarTabla0 " + " " + e.getMessage());
		}
	}

	public void sumarTabla1() {

		try {
			valorPromotor = 0D;
			valorPromotor += checkListKpiEjecucionSeleccionado.getArqueoCajaPromotor().stream()
					.mapToDouble(p -> p.getValorFaltante()).sum();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método sumarTabla0 " + " " + e.getMessage());
		}
	}

	public Double sumarArqueoCaja(Long orden) {
		Double totalS = 0.0D;
		Double total = 0.0D;
		try {
			totalS += checkListKpiEjecucionSeleccionado.getArqueoCajaGeneral().stream()
					.filter(p -> p.getOrden() == orden).mapToDouble(p -> p.getSubTotal()).sum();
			total = new BigDecimal(totalS).setScale(2, RoundingMode.HALF_UP).doubleValue();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método sumarArqueoCaja " + " " + e.getMessage());
		}
		return total;
	}

	public Double sumarArqueoCajaFuerte0(Long orden) {
		Double total = 0.0D;
		Double totalS = 0.0D;
		try {
			totalS += checkListKpiEjecucionSeleccionado.getArqueoCajaFuerte().stream()
					.filter(p -> p.getOrden() == orden).mapToDouble(p -> p.getValorBlindado()).sum();
			total = new BigDecimal(totalS).setScale(2, RoundingMode.HALF_UP).doubleValue();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método sumarArqueoCajaFuerte0 " + " " + e.getMessage());
		}
		return total;
	}

	public Double sumarArqueoCajaFuerte1(Long orden) {
		Double total = 0.0D;
		Double totalS = 0.0D;
		try {
			totalS += checkListKpiEjecucionSeleccionado.getArqueoCajaFuerte().stream()
					.filter(p -> p.getOrden() == orden).mapToDouble(p -> p.getValorVendedor()).sum();
			total = new BigDecimal(totalS).setScale(2, RoundingMode.HALF_UP).doubleValue();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método sumarArqueoCajaFuerte1 " + " " + e.getMessage());
		}
		return total;
	}

	public Double sumarArqueoCajaFuerte2(Long orden) {
		Double total = 0.0D;
		Double totalS = 0.0D;
		try {
			totalS += checkListKpiEjecucionSeleccionado.getArqueoCajaFuerte().stream()
					.filter(p -> p.getOrden() == orden).mapToDouble(p -> p.getValorDiferencia()).sum();
			total = new BigDecimal(totalS).setScale(2, RoundingMode.HALF_UP).doubleValue();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método sumarArqueoCajaFuerte2 " + " " + e.getMessage());
		}
		return total;
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

	public void sumarArqueoCaja(ArqueoCajaEt arqueoCaja) {
		Long cantidad = 0L;
		Double total = 0.0D;
		String medioPago = "";
		Double totalVenta = 0.0D;
		Double totalArqueo = 0.0D;
		Double totalDiferencia = 0.0D;
		try {
			medioPago = arqueoCaja.getSubDescripcion();
			if (!medioPago.equals("Tarjetas de Crédito") && !medioPago.equals("Créditos")
					&& !medioPago.equals("Retención") && !medioPago.equals("Pico")) {
				cantidad = arqueoCaja.getCantidad();
				totalVenta = checkListKpiEjecucionSeleccionado.getValorVenta();
				total = cantidad * arqueoCaja.getValorSubTotal();
				arqueoCaja.setValorTotal(new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue());
				totalArqueo = checkListKpiEjecucionSeleccionado.getArqueoCaja().stream()
						.mapToDouble(p -> p.getValorTotal()).sum();
				totalDiferencia = totalArqueo - totalVenta;
				checkListKpiEjecucionSeleccionado.setValorArqueo(totalArqueo);
				checkListKpiEjecucionSeleccionado.setValorDiferencia(totalDiferencia);
			} else {
				totalVenta = checkListKpiEjecucionSeleccionado.getValorVenta();
				arqueoCaja.setValorTotal(arqueoCaja.getValorSubTotal());
				totalArqueo = checkListKpiEjecucionSeleccionado.getArqueoCaja().stream()
						.mapToDouble(p -> p.getValorTotal()).sum();
				totalDiferencia = totalArqueo - totalVenta;
				checkListKpiEjecucionSeleccionado.setValorArqueo(totalArqueo);
				checkListKpiEjecucionSeleccionado.setValorDiferencia(totalDiferencia);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método sumarArqueoCaja " + " " + e.getMessage());
		}
	}

	public void sumarArqueoCajaFuerte(ArqueoCajaFuerteEt arqueoCaja) {
		Long cantidad = 0L;
		Double total = 0.0D;
		Double totalB = 0.0D;
		Double totalV = 0.0D;
		Double totalD = 0.0D;
		Double totalBlindado = 0.0D;
		Double totalDiferencia = 0.0D;

		try {
			if (arqueoCaja.getSubDescripcion().equals("Efectivo USD(Billetes)")
					&& arqueoCaja.getDescripcion().equals("Parcial")) {
				cantidad = arqueoCaja.getCantidad();
				if (cantidad != 0L) {
					totalB = (double) (cantidad * 100);
					arqueoCaja.setValorBlindado(totalB);
				}
			} else {
				if (arqueoCaja.getValorBlindado() != null) {
					totalB = arqueoCaja.getValorBlindado();
				}
			}
			totalBlindado = checkListKpiEjecucionSeleccionado.getArqueoCajaFuerte().stream()
					.mapToDouble(p -> p.getValorBlindado() == null ? 0D : p.getValorBlindado()).sum();
			totalDiferencia = checkListKpiEjecucionSeleccionado.getArqueoCajaFuerte().stream()
					.mapToDouble(p -> p.getValorDiferencia() == null ? 0D : p.getValorDiferencia()).sum();
			if (arqueoCaja.getValorVendedor() != null) {
				totalV = arqueoCaja.getValorVendedor();
			}
			total = totalB + totalV;
			totalD = totalB - totalV;
			arqueoCaja.setValorDiferencia(totalD);
			arqueoCaja.setValorTotal(new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue());
			checkListKpiEjecucionSeleccionado.setValorArqueo(totalBlindado);
			checkListKpiEjecucionSeleccionado.setValorDiferencia(totalDiferencia);
			sumarTabla0();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método sumarArqueoCajaFuerte " + " " + e.getMessage());
		}
	}

	public void sumarArqueoCajaPromotor() {
		try {
			valorPromotor = checkListKpiEjecucionSeleccionado.getArqueoCajaPromotor().stream()
					.mapToDouble(p -> p.getValorFaltante()).sum();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método sumarArqueoCajaPromotor " + " " + e.getMessage());
		}
	}

	public void sumarArqueoFondoSuelto(ArqueoFondoSueltoEt arqueoFondoSuelto) {
		Long total = 0L;
		Double totalVenta = 0.0D;
		Double totalArqueo = 0.0D;
		Double totalDiferencia = 0.0D;
		try {
			totalVenta = checkListKpiEjecucionSeleccionado.getValorVenta();
			total = arqueoFondoSuelto.getCaja0() + arqueoFondoSuelto.getCaja1() + arqueoFondoSuelto.getCaja2();
			arqueoFondoSuelto.setValorTotal(new BigDecimal(total * arqueoFondoSuelto.getDenominacion())
					.setScale(2, RoundingMode.HALF_UP).doubleValue());
			totalArqueo = checkListKpiEjecucionSeleccionado.getArqueoFondoSuelto().stream()
					.mapToDouble(p -> p.getValorTotal()).sum();
			totalDiferencia = totalArqueo - totalVenta;
			checkListKpiEjecucionSeleccionado.setValorArqueo(totalArqueo);
			checkListKpiEjecucionSeleccionado.setValorDiferencia(totalDiferencia);
			RequestContext.getCurrentInstance().update("frm_eje_004:inputTotalArqueo");
			RequestContext.getCurrentInstance().update("frm_eje_004:outputTotalDiferencia");
			RequestContext.getCurrentInstance().update("frm_eje_004:dtbArqueoFondoSuelto");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método sumarArqueoCaja " + " " + e.getMessage());
		}
	}

	public void sumarArqueoFondoSuelto() {
		Double totalVenta = 0.0D;
		Double totalArqueo = 0.0D;
		Double totalDiferencia = 0.0D;
		try {
			totalVenta = checkListKpiEjecucionSeleccionado.getValorVenta();
			totalArqueo = checkListKpiEjecucionSeleccionado.getArqueoFondoSuelto().stream()
					.mapToDouble(p -> p.getValorTotal()).sum();
			totalDiferencia = totalArqueo - totalVenta;
			checkListKpiEjecucionSeleccionado.setValorArqueo(totalArqueo);
			checkListKpiEjecucionSeleccionado.setValorDiferencia(totalDiferencia);
			RequestContext.getCurrentInstance().update("frm_eje_004:inputTotalArqueo");
			RequestContext.getCurrentInstance().update("frm_eje_004:outputTotalDiferencia");
			RequestContext.getCurrentInstance().update("frm_eje_004:dtbArqueoFondoSuelto");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método sumarArqueoCaja " + " " + e.getMessage());
		}
	}

	public void sumarArqueoCajaChica(ArqueoCajaGeneralEt arqueoCajaGeneral) {
		Double total = 0.0D;
		Double porcentaje = 0.0D;
		Double totalRubro = 0.0D;
		Double totalVenta = 0.0D;
		Double totalArqueo = 0.0D;
		Double totalDiferencia = 0.0D;
		Double porcentajeArqueo = 0.0D;
		try {
			UsuarioEt usuario = appMain.getUsuario();
			ParametrosGeneralesEt parametroPArqueo = iParametrolGeneralDao.getObjPadre("63");
			ParametrosGeneralesEt porcentajeA = iParametrolGeneralDao.getListaHIjosUnique(parametroPArqueo);
			porcentaje = Double.parseDouble(porcentajeA.getValorLista());
			totalRubro += checkListKpiEjecucionSeleccionado.getArqueoCajaGeneral().stream()
					.filter(p -> p.getOrden() == 0L).mapToDouble(p -> p.getSubTotal()).sum();

			totalVenta = checkListKpiEjecucionSeleccionado.getValorVenta();
			if (arqueoCajaGeneral.getSubDescripcion().equals("Solicitud")) {
				arqueoCajaGeneral.setSubTotal(new BigDecimal(arqueoCajaGeneral.getSubTotal())
						.setScale(2, RoundingMode.HALF_UP).doubleValue());
			}
			if (arqueoCajaGeneral != null && arqueoCajaGeneral.getParametroRubro() == null
					&& !arqueoCajaGeneral.getSubDescripcion().equals("Solicitud")) {
				total = arqueoCajaGeneral.getCaja0();
				arqueoCajaGeneral.setSubTotal(new BigDecimal(total * arqueoCajaGeneral.getDenominacion())
						.setScale(2, RoundingMode.HALF_UP).doubleValue());
				sumarArqueoCaja(arqueoCajaGeneral.getOrden());
				iArqueoCajaGeneralDao.guardarArqueoCajaGeneral(arqueoCajaGeneral, usuario);
			}
			totalArqueo = checkListKpiEjecucionSeleccionado.getArqueoCajaGeneral().stream()
					.mapToDouble(p -> p.getSubTotal()).sum();
			totalDiferencia = totalArqueo - totalVenta;
			porcentajeArqueo = new BigDecimal(totalRubro / totalVenta * 100).setScale(2, RoundingMode.HALF_UP)
					.doubleValue();
			if (totalRubro != 0.0D && porcentajeArqueo >= porcentaje) {
				checkListKpiEjecucionSeleccionado.setPorcentaje(porcentajeArqueo / 100);
				color = "red";
			} else {
				checkListKpiEjecucionSeleccionado.setPorcentaje(porcentajeArqueo / 100);
				color = "black";
			}
			checkListKpiEjecucionSeleccionado.setValorArqueo(totalArqueo);
			checkListKpiEjecucionSeleccionado.setValorDiferencia(totalDiferencia);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método sumarArqueoCajaChica " + " " + e.getMessage());
		}
	}

	public void sumarArqueoCajaChica() {
		Double porcentaje = 0.0D;
		Double totalRubro = 0.0D;
		Double totalVenta = 0.0D;
		Double totalArqueo = 0.0D;
		Double totalDiferencia = 0.0D;
		Double porcentajeArqueo = 0.0D;
		try {
			ParametrosGeneralesEt parametroPArqueo = iParametrolGeneralDao.getObjPadre("63");
			ParametrosGeneralesEt porcentajeA = iParametrolGeneralDao.getListaHIjosUnique(parametroPArqueo);
			porcentaje = Double.parseDouble(porcentajeA.getValorLista());
			totalRubro += checkListKpiEjecucionSeleccionado.getArqueoCajaGeneral().stream()
					.filter(p -> p.getOrden() == 0L).mapToDouble(p -> p.getSubTotal()).sum();
			totalVenta = checkListKpiEjecucionSeleccionado.getValorVenta();
			totalArqueo = checkListKpiEjecucionSeleccionado.getArqueoCajaGeneral().stream()
					.mapToDouble(p -> p.getSubTotal()).sum();
			totalDiferencia = totalArqueo - totalVenta;
			porcentajeArqueo = new BigDecimal(totalRubro / totalVenta * 100).setScale(2, RoundingMode.HALF_UP)
					.doubleValue();
			if (totalRubro != 0.0D && porcentajeArqueo >= porcentaje) {
				checkListKpiEjecucionSeleccionado.setPorcentaje(porcentajeArqueo / 100);
				color = "red";
			} else {
				checkListKpiEjecucionSeleccionado.setPorcentaje(porcentajeArqueo / 100);
				color = "black";
			}
			checkListKpiEjecucionSeleccionado.setValorArqueo(totalArqueo);
			checkListKpiEjecucionSeleccionado.setValorDiferencia(totalDiferencia);
			RequestContext.getCurrentInstance().update("frm_eje_005:inputTotalArqueo");
			RequestContext.getCurrentInstance().update("frm_eje_005:outputTotalDiferencia");
			RequestContext.getCurrentInstance().update("frm_eje_005:dtbArqueoCajaChica");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método sumarArqueoCajaChica " + " " + e.getMessage());
		}
	}

	public void guardarArqueoCaja() {
		String mensaje = "";
		try {
			mensaje = validacionGuardarArqueoCajaC();
			if (!mensaje.equals("")) {
				showInfo(mensaje, FacesMessage.SEVERITY_INFO);
				return;
			}
			UsuarioEt usuario = appMain.getUsuario();
			iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpiEjecucionSeleccionado, usuario);
			// iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucion,
			// usuario);
			RequestContext.getCurrentInstance().execute("PF('dlg_eje_003').hide();");
			iCheckListEjecucionDao.clear();
			checkListEjecucion = iCheckListEjecucionDao.getCheckListEjecucion(TipoCheckListEnum.CRITERIO_ESPECIFICO,
					usuario);
			checkListProcesoEjecucionEfectivo = null;
			checkListProcesoEjecucionEfectivo = iCheckListProcesoEjecucionDao.getCheckListProcesoE(
					checkListKpiEjecucionSeleccionado.getCheckListProcesoEjecucion().getIdCheckListProcesoEjecucion());
			checkListKpiEjecucionSeleccionado = null;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarArqueoCaja " + " " + e.getMessage());
		}
	}

	public void guardarArqueoCajaFuerte() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpiEjecucionSeleccionado, usuario);
			// iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucion,
			// usuario);
			RequestContext.getCurrentInstance().execute("PF('dlg_eje_003_1').hide();");
			iCheckListEjecucionDao.clear();
			checkListEjecucion = iCheckListEjecucionDao.getCheckListEjecucion(TipoCheckListEnum.CRITERIO_ESPECIFICO,
					usuario);
			checkListProcesoEjecucionEfectivo = null;
			checkListProcesoEjecucionEfectivo = iCheckListProcesoEjecucionDao.getCheckListProcesoE(
					checkListKpiEjecucionSeleccionado.getCheckListProcesoEjecucion().getIdCheckListProcesoEjecucion());
			checkListKpiEjecucionSeleccionado = null;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarArqueoCaja " + " " + e.getMessage());
		}
	}

	public void guardarArqueoCajaFondoSuelto() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpiEjecucionSeleccionado, usuario);
			// iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucion,
			// usuario);
			RequestContext.getCurrentInstance().execute("PF('dlg_eje_004').hide();");
			iCheckListEjecucionDao.clear();
			checkListEjecucion = iCheckListEjecucionDao.getCheckListEjecucion(TipoCheckListEnum.CRITERIO_ESPECIFICO,
					usuario);
			checkListKpiEjecucionSeleccionado = null;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarArqueoCajaFondoSuelto " + " " + e.getMessage());
		}
	}

	public void guardarArqueoCajaChica() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpiEjecucionSeleccionado, usuario);
			// iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucion,
			// usuario);
			RequestContext.getCurrentInstance().execute("PF('dlg_eje_005').hide();");
			iCheckListEjecucionDao.clear();
			checkListEjecucion = iCheckListEjecucionDao.getCheckListEjecucion(TipoCheckListEnum.CRITERIO_ESPECIFICO,
					usuario);
			checkListKpiEjecucionSeleccionado = null;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarArqueoCajaChica " + " " + e.getMessage());
		}
	}

	public void eliminarRubroArqueo(ArqueoCajaGeneralEt arqueoCajaGeneral) {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			arqueoCajaGeneral.setEstado(EstadoEnum.INA);
			checkListKpiEjecucionSeleccionado.getArqueoCajaGeneral().remove(arqueoCajaGeneral);
			iArqueoCajaGeneralDao.guardarArqueoCajaGeneral(arqueoCajaGeneral, usuario);
			sumarArqueoCajaChica(null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método eliminarRubroArqueo " + " " + e.getMessage());
		}
	}

	public void eliminarDetallePromotor(ArqueoCajaPromotorEt arqueoCajaPromotor) {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			arqueoCajaPromotor.setEstado(EstadoEnum.INA);
			checkListKpiEjecucionSeleccionado.getArqueoCajaPromotor().remove(arqueoCajaPromotor);
			iArqueoCajaPromotorDao.guardarArqueoCajaPromotor(arqueoCajaPromotor, usuario);
			sumarArqueoCajaPromotor();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método eliminarRubroArqueo " + " " + e.getMessage());
		}
	}

	public void guardarSeleccion(CheckListKpiEjecucionEt checkListKpiEjecucion) {

		try {
			UsuarioEt usuario = appMain.getUsuario();
			if (checkListKpiEjecucion.getCriterioEvaluacionDetalle() != null) {
				checkListKpiEjecucion
						.setPuntajeEjecucion(checkListKpiEjecucion.getCriterioEvaluacionDetalle().getPuntaje());
				iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucion, usuario);
				iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpiEjecucion, usuario);
				iCheckListEjecucionDao.generarActNivelRiesgo(
						checkListEjecucion.getNivelEvaluacion().getIdNivelEvaluacion(),
						checkListEjecucion.getIdCheckListEjecucion());
				validaCompleto();
			} else {
				checkListKpiEjecucion.setCriterioEvaluacionDetalle(null);
				checkListKpiEjecucion.setPuntajeEjecucion(0L);
				iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpiEjecucion, usuario);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarSeleccion " + " " + e.getMessage());
		}
	}

	public void upload(FileUploadEvent event) throws EntidadNoGrabadaException {
		String ruta;
		String nombreArchivo = "";
		try {
			nombreArchivo = event.getFile().getFileName();
			CheckListEjecucionAdjuntoEt reg = new CheckListEjecucionAdjuntoEt();
			reg.setCheckListEjecucion(checkListEjecucion);
			reg.setNombreAdjunto(nombreArchivo);
			reg.setFile(event.getFile().getInputstream());
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
			iGeneralUtilsDao.copyFile(reg.getNombreAdjunto(), reg.getFile(), ruta);
			checkListEjecucion.getCheckListEjecucionAdjunto().add(reg);
			FacesMessage msg = new FacesMessage("Satisfactorio! ",
					event.getFile().getFileName() + "  " + "Esta subido Correctamente.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (IOException e) {
			FacesMessage msg = new FacesMessage("Error! ",
					event.getFile().getFileName() + "  " + "El archivo no se subio.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			e.printStackTrace();
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

	public void quitarAdjunto(CheckListEjecucionAdjuntoEt checkListEjecucionAdjunto) {
		try {
			Date fechaRegistro = UtilEnum.FECHA_REGISTRO.getValue();
			checkListEjecucionAdjunto.setFechaModificacion(fechaRegistro);
			checkListEjecucionAdjunto.setEstado(EstadoEnum.INA);
			checkListEjecucionAdjuntoEliminado.add(checkListEjecucionAdjunto);
			checkListEjecucion.getCheckListEjecucionAdjunto().remove(checkListEjecucionAdjunto);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método quitarAdjunto " + " " + e.getMessage());
		}

	}

	public void procesoConciliacion(CheckListProcesoEjecucionEt checkListProcesoEjecucion) {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			checkListProcesoEjecucion.setCheckListKpiEjecucionA(new ArrayList<>());
			ParametrosGeneralesEt parametroP = iParametrolGeneralDao.getObjPadre("157");
			List<ParametrosGeneralesEt> parametrosGenerales = iParametrolGeneralDao.getListaHIjos(parametroP);
			for (ParametrosGeneralesEt parametrosGeneral : parametrosGenerales) {
				CheckListKpiEjecucionAEt checkListKpiEjecucionA = new CheckListKpiEjecucionAEt();
				checkListKpiEjecucionA.setOrden(parametrosGeneral.getOrden());
				checkListKpiEjecucionA.setCheckListProcesoEjecucion(checkListProcesoEjecucion);
				checkListKpiEjecucionA.setDescripcion(reemplazarText(parametrosGeneral.getNombreLista()));
				checkListProcesoEjecucion.getCheckListKpiEjecucionA().add(checkListKpiEjecucionA);
			}
			iCheckListProcesoEjecucionDao.guardarCheckListProcesoEjecucion(checkListProcesoEjecucion, usuario);
			iCheckListProcesoEjecucionDao.clear();
			checkListProcesoEjecucionEfectivo = iCheckListProcesoEjecucionDao
					.getCheckListProcesoE(checkListProcesoEjecucion.getIdCheckListProcesoEjecucion());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método procesoConciliacion " + " " + e.getMessage());
		}
	}

	public String reemplazarText(String textoO) {
		String texto = "";
		String texto1 = "";
		String descripcion = "";
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			String dia = "" + calendar.get(Calendar.DAY_OF_MONTH);
			String anio = "" + calendar.get(Calendar.YEAR);
			Month mes = LocalDate.now().getMonth();
			String nombreM = mes.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
			texto = textoO.replace("mes", nombreM);
			texto1 = texto.replace("anio", anio);
			descripcion = texto1.replace("dia", dia);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método reemplazarText " + " " + e.getMessage());
		}
		return descripcion;
	}

	public void procesoCombustible(CheckListProcesoEjecucionEt checkListProcesoEjecucion) {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			checkListProcesoEjecucion.setCheckListKpiEjecucionA(new ArrayList<>());
			ParametrosGeneralesEt parametroP = iParametrolGeneralDao.getObjPadre("70");
			List<ParametrosGeneralesEt> parametrosGenerales = iParametrolGeneralDao.getListaHIjos(parametroP);
			for (ParametrosGeneralesEt parametrosGeneral : parametrosGenerales) {
				CheckListKpiEjecucionAEt checkListKpiEjecucionA = new CheckListKpiEjecucionAEt();
				checkListKpiEjecucionA.setOrden(parametrosGeneral.getOrden());
				checkListKpiEjecucionA.setDescripcion(parametrosGeneral.getNombreLista());
				checkListKpiEjecucionA.setCheckListProcesoEjecucion(checkListProcesoEjecucion);
				checkListProcesoEjecucion.getCheckListKpiEjecucionA().add(checkListKpiEjecucionA);
			}
			iCheckListProcesoEjecucionDao.guardarCheckListProcesoEjecucion(checkListProcesoEjecucion, usuario);
			iCheckListProcesoEjecucionDao.clear();
			checkListProcesoEjecucionCombustible = iCheckListProcesoEjecucionDao
					.getCheckListProcesoE(checkListProcesoEjecucion.getIdCheckListProcesoEjecucion());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método procesoCombustible " + " " + e.getMessage());
		}
	}

	public void procesoEfectivo(CheckListProcesoEjecucionEt checkListProcesoEjecucion) {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			checkListProcesoEjecucion.setCheckListKpiEjecucionA(new ArrayList<>());
			ParametrosGeneralesEt parametroP = iParametrolGeneralDao.getObjPadre("75");
			List<ParametrosGeneralesEt> parametrosGenerales = iParametrolGeneralDao.getListaHIjos(parametroP);
			for (ParametrosGeneralesEt parametrosGeneral : parametrosGenerales) {
				CheckListKpiEjecucionAEt checkListKpiEjecucionA = new CheckListKpiEjecucionAEt();
				checkListKpiEjecucionA.setOrden(parametrosGeneral.getOrden());
				checkListKpiEjecucionA.setDescripcion(parametrosGeneral.getNombreLista());
				checkListKpiEjecucionA.setCheckListProcesoEjecucion(checkListProcesoEjecucion);
				checkListProcesoEjecucion.getCheckListKpiEjecucionA().add(checkListKpiEjecucionA);
			}
			iCheckListProcesoEjecucionDao.guardarCheckListProcesoEjecucion(checkListProcesoEjecucion, usuario);
			iCheckListProcesoEjecucionDao.clear();
			checkListProcesoEjecucionEfectivo = iCheckListProcesoEjecucionDao
					.getCheckListProcesoE(checkListProcesoEjecucion.getIdCheckListProcesoEjecucion());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método procesoEfectivo " + " " + e.getMessage());
		}
	}

	public void procesoTienda(CheckListProcesoEjecucionEt checkListProcesoEjecucion) {
		try {
			if (checkListProcesoEjecucion.getCheckListKpiEjecucionA() == null
					|| checkListProcesoEjecucion.getCheckListKpiEjecucionA().isEmpty()) {
				UsuarioEt usuario = appMain.getUsuario();
				ParametrosGeneralesEt parametroP = iParametrolGeneralDao.getObjPadre("78");
				List<ParametrosGeneralesEt> parametrosGenerales = iParametrolGeneralDao.getListaHIjos(parametroP);
				for (ParametrosGeneralesEt parametrosGeneral : parametrosGenerales) {
					if (!parametrosGeneral.getValorLista().contains("No existen")) {
						CheckListKpiEjecucionAEt checkListKpiEjecucionA = new CheckListKpiEjecucionAEt();
						checkListKpiEjecucionA.setParametroKpi(parametrosGeneral);
						checkListKpiEjecucionA.setOrden(parametrosGeneral.getOrden());
						checkListKpiEjecucionA.setObservacion(parametrosGeneral.getValorLista());
						checkListKpiEjecucionA.setCheckListProcesoEjecucion(checkListProcesoEjecucion);
						checkListProcesoEjecucion.getCheckListKpiEjecucionA().add(checkListKpiEjecucionA);
					}
				}
				iCheckListProcesoEjecucionDao.guardarCheckListProcesoEjecucion(checkListProcesoEjecucion, usuario);
			}
			iCheckListProcesoEjecucionDao.clear();
			checkListProcesoEjecucionTienda = iCheckListProcesoEjecucionDao
					.getCheckListProcesoE(checkListProcesoEjecucion.getIdCheckListProcesoEjecucion());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método procesoTienda " + " " + e.getMessage());
		}
	}

	public void procesoControlPromotores(CheckListProcesoEjecucionEt checkListProcesoEjecucion) {
		try {
			if (checkListProcesoEjecucion.getCheckListKpiEjecucionA() == null
					|| checkListProcesoEjecucion.getCheckListKpiEjecucionA().isEmpty()) {
				UsuarioEt usuario = appMain.getUsuario();
				ParametrosGeneralesEt parametroP = iParametrolGeneralDao.getObjPadre("164");
				List<ParametrosGeneralesEt> parametrosGenerales = iParametrolGeneralDao.getListaHIjos(parametroP);
				for (ParametrosGeneralesEt parametrosGeneral : parametrosGenerales) {
					CheckListKpiEjecucionAEt checkListKpiEjecucionA = new CheckListKpiEjecucionAEt();
					checkListKpiEjecucionA.setParametroKpi(parametrosGeneral);
					checkListKpiEjecucionA.setOrden(parametrosGeneral.getOrden());
					checkListKpiEjecucionA.setDescripcion(parametrosGeneral.getValorLista());
					checkListKpiEjecucionA.setCheckListProcesoEjecucion(checkListProcesoEjecucion);
					checkListProcesoEjecucion.getCheckListKpiEjecucionA().add(checkListKpiEjecucionA);
				}
				iCheckListProcesoEjecucionDao.guardarCheckListProcesoEjecucion(checkListProcesoEjecucion, usuario);
			}
			iCheckListProcesoEjecucionDao.clear();
			checkListProcesoEjecucionTienda = iCheckListProcesoEjecucionDao
					.getCheckListProcesoE(checkListProcesoEjecucion.getIdCheckListProcesoEjecucion());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método procesoTienda " + " " + e.getMessage());
		}
	}

	public void procesoAdminstrativo(CheckListProcesoEjecucionEt checkListProcesoEjecucion) {
		ParametrosGeneralesEt parametroP = null;
		List<ParametrosGeneralesEt> parametrosGenerales = null;
		try {
			UsuarioEt usuario = appMain.getUsuario();
			checkListProcesoEjecucion.setCheckListKpiEjecucionA(new ArrayList<>());
			parametroP = iParametrolGeneralDao.getObjPadre("89");
			parametrosGenerales = iParametrolGeneralDao.getListaHIjos(parametroP);
			for (ParametrosGeneralesEt parametrosGeneral : parametrosGenerales) {
				CheckListKpiEjecucionAEt checkListKpiEjecucionA = new CheckListKpiEjecucionAEt();
				checkListKpiEjecucionA.setOrden(parametrosGeneral.getOrden());
				checkListKpiEjecucionA.setDescripcion(parametrosGeneral.getNombreLista());
				checkListKpiEjecucionA.setCheckListProcesoEjecucion(checkListProcesoEjecucion);
				checkListProcesoEjecucion.getCheckListKpiEjecucionA().add(checkListKpiEjecucionA);
			}
			parametroP = iParametrolGeneralDao.getObjPadre("92");
			parametrosGenerales = iParametrolGeneralDao.getListaHIjos(parametroP);
			for (ParametrosGeneralesEt parametrosGeneral : parametrosGenerales) {
				CheckListKpiEjecucionBEt checkListKpiEjecucionB = new CheckListKpiEjecucionBEt();
				checkListKpiEjecucionB.setFechaCaducidad(new Date());
				checkListKpiEjecucionB.setOrden(parametrosGeneral.getOrden());
				checkListKpiEjecucionB.setDescripcion(parametrosGeneral.getNombreLista());
				checkListKpiEjecucionB.setCheckListProcesoEjecucion(checkListProcesoEjecucion);
				checkListProcesoEjecucion.getCheckListKpiEjecucionB().add(checkListKpiEjecucionB);
			}
			iCheckListProcesoEjecucionDao.guardarCheckListProcesoEjecucion(checkListProcesoEjecucion, usuario);
			iCheckListProcesoEjecucionDao.clear();
			checkListProcesoEjecucionProcedimientoS = iCheckListProcesoEjecucionDao
					.getCheckListProcesoE(checkListProcesoEjecucion.getIdCheckListProcesoEjecucion());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método procesoAdminstrativo " + " " + e.getMessage());
		}
	}

	public void guardarMaterial() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			iCheckListProcesoEjecucionDao.guardarCheckListProcesoEjecucion(checkListProcesoEjecucionCombustible,
					usuario);
			RequestContext.getCurrentInstance().execute("PF('dlg_eje_006').hide();");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarMaterial " + " " + e.getMessage());
		}

	}

	public void guardarConciliacion() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			iCheckListProcesoEjecucionDao.guardarCheckListProcesoEjecucion(checkListProcesoEjecucionEfectivo, usuario);
			RequestContext.getCurrentInstance().execute("PF('dlg_eje_007_1').hide();");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarConciliacion " + " " + e.getMessage());
		}

	}

	public void guardarProcesoEfectivo() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			Long idProceso = checkListProcesoEjecucionEfectivo.getIdCheckListProcesoEjecucion();
			iCheckListProcesoEjecucionDao.guardarCheckListProcesoEjecucion(checkListProcesoEjecucionEfectivo, usuario);
			iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucion, usuario);
			RequestContext.getCurrentInstance().execute("PF('dlg_eje_007').hide();");
			checkListProcesoEjecucionEfectivo = null;
			checkListProcesoEjecucionEfectivo = iCheckListProcesoEjecucionDao.getCheckListProcesoE(idProceso);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarProcesoEfectivo " + " " + e.getMessage());
		}

	}

	public void guardarCierreTurno() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			Long idProceso = checkListProcesoEjecucionTienda.getIdCheckListProcesoEjecucion();
			iCheckListProcesoEjecucionDao.guardarCheckListProcesoEjecucion(checkListProcesoEjecucionTienda, usuario);
			RequestContext.getCurrentInstance().execute("PF('dlg_eje_008_1').hide();");
			checkListProcesoEjecucionTienda = null;
			checkListProcesoEjecucionTienda = iCheckListProcesoEjecucionDao.getCheckListProcesoE(idProceso);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarMaterial " + " " + e.getMessage());
		}

	}

	public void guardarDocPendiente() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			Long idProceso = checkListProcesoEjecucionTienda.getIdCheckListProcesoEjecucion();
			iCheckListProcesoEjecucionDao.guardarCheckListProcesoEjecucion(checkListProcesoEjecucionTienda, usuario);
			RequestContext.getCurrentInstance().execute("PF('dlg_eje_008').hide();");
			checkListProcesoEjecucionTienda = null;
			checkListProcesoEjecucionTienda = iCheckListProcesoEjecucionDao.getCheckListProcesoE(idProceso);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarMaterial " + " " + e.getMessage());
		}

	}

	public void guardarControlAdministrativo() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			iCheckListProcesoEjecucionDao.guardarCheckListProcesoEjecucion(checkListProcesoEjecucionProcedimientoS,
					usuario);
			RequestContext.getCurrentInstance().execute("PF('dlg_eje_009').hide();");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarControlAdministrativo " + " " + e.getMessage());
		}

	}

	public void nuevoFirmaArqueoCaja() {
		String mensaje = "";
		try {
			mensaje = validacionFirmaArqueoCajaC();
			if (!mensaje.equals("")) {
				showInfo(mensaje, FacesMessage.SEVERITY_INFO);
				return;
			}
			UsuarioEt usuario = appMain.getUsuario();
			if (checkListKpiEjecucionSeleccionado.getCheckListKpiEjecucionFirma() == null
					|| checkListKpiEjecucionSeleccionado.getCheckListKpiEjecucionFirma().isEmpty()) {
				guardarCheckListKpiEjecucionFirmaCajaGeneral(checkListKpiEjecucionSeleccionado);
			} else {
				actualizarCheckListKpiEjecucionFirmaCajaGeneral(checkListKpiEjecucionSeleccionado);
			}
			iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpiEjecucionSeleccionado, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método nuevoFirmaArqueoCaja " + " " + e.getMessage());
		}
	}

	public void guardarCheckListKpiEjecucionFirmaCajaGeneral(CheckListKpiEjecucionEt checkListKpiEjecucion) {
		try {
			checkListKpiEjecucion.setCheckListKpiEjecucionFirma(new ArrayList<>());
			CheckListKpiEjecucionFirmaEt checkListKpiEjecucionFirma = new CheckListKpiEjecucionFirmaEt();
			checkListKpiEjecucionFirma.setCheckListKpiEjecucion(checkListKpiEjecucion);
			checkListKpiEjecucionFirma.setOrden(0L);
			checkListKpiEjecucionFirma.setCargo("VENDEDOR");
			checkListKpiEjecucionFirma.setIdentificacion("");
			checkListKpiEjecucionFirma.setNombre(checkListKpiEjecucion.getNombreVendedor());
			checkListKpiEjecucion.getCheckListKpiEjecucionFirma().add(checkListKpiEjecucionFirma);
			checkListKpiEjecucionFirma = new CheckListKpiEjecucionFirmaEt();
			PersonaEt personaR = checkListEjecucion.getUsuarioAsignado().getPersonaUsuario();
			checkListKpiEjecucionFirma.setCheckListKpiEjecucion(checkListKpiEjecucion);
			checkListKpiEjecucionFirma.setOrden(1L);
			checkListKpiEjecucionFirma.setCargo("RESPONSABLE DE ARQUEO");
			if (personaR.getFirma() != null) {
				checkListKpiEjecucionFirma.setContieneFirma(true);
				checkListKpiEjecucionFirma.setFirma(personaR.getFirma());
			}
			checkListKpiEjecucionFirma.setIdPersona(personaR.getIdPersona());
			checkListKpiEjecucionFirma.setNombre(personaR.getNombreCompleto());
			checkListKpiEjecucionFirma.setIdentificacion(personaR.getIdentificacionPersona());
			checkListKpiEjecucion.getCheckListKpiEjecucionFirma().add(checkListKpiEjecucionFirma);
			PersonaEt persona = iPersonaDao.getResponsableByAgencia(checkListEjecucion.getPlanificacion().getAgencia());
			if (persona != null) {
				checkListKpiEjecucionFirma = new CheckListKpiEjecucionFirmaEt();
				checkListKpiEjecucionFirma.setOrden(2L);
				checkListKpiEjecucionFirma.setCargo("GERENTE");
				if (persona.getFirma() != null) {
					checkListKpiEjecucionFirma.setContieneFirma(true);
					checkListKpiEjecucionFirma.setFirma(persona.getFirma());
				}
				checkListKpiEjecucionFirma.setIdPersona(persona.getIdPersona());
				checkListKpiEjecucionFirma.setNombre(persona.getNombreCompleto());
				checkListKpiEjecucionFirma.setCheckListKpiEjecucion(checkListKpiEjecucion);
				checkListKpiEjecucionFirma.setIdentificacion(persona.getIdentificacionPersona());
				checkListKpiEjecucion.getCheckListKpiEjecucionFirma().add(checkListKpiEjecucionFirma);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarCheckListKpiEjecucionFirma " + " " + e.getMessage());
		}
	}

	public void actualizarCheckListKpiEjecucionFirmaCajaGeneral(CheckListKpiEjecucionEt checkListKpiEjecucion) {
		try {
			for (CheckListKpiEjecucionFirmaEt checkListKpiF : checkListKpiEjecucion.getCheckListKpiEjecucionFirma()) {
				if (!checkListKpiF.isFirmado() && checkListKpiF.getOrden() == 0L) {
					checkListKpiF.setNombre(checkListKpiEjecucion.getNombreVendedor());
				}
				if (!checkListKpiF.isFirmado() && checkListKpiF.getOrden() == 1L) {
					checkListKpiF.setIdPersona(checkListKpiEjecucion.getAuditor().getPersonaUsuario().getIdPersona());
					checkListKpiF.setNombre(checkListKpiEjecucion.getAuditor().getPersonaUsuario().getNombreCompleto());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarCheckListKpiEjecucionFirma " + " " + e.getMessage());
		}
	}

	public void guardarCheckListKpiEjecucionFirmaCaja(CheckListKpiEjecucionEt checkListKpiEjecucion) {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			checkListKpiEjecucion.setCheckListKpiEjecucionFirma(new ArrayList<>());
			CheckListKpiEjecucionFirmaEt checkListKpiEjecucionFirma = new CheckListKpiEjecucionFirmaEt();
			checkListKpiEjecucionFirma = new CheckListKpiEjecucionFirmaEt();
			checkListKpiEjecucionFirma.setOrden(0L);
			checkListKpiEjecucionFirma.setCargo("RESPONSABLE DE ARQUEO");
			PersonaEt personaEtR = checkListEjecucion.getUsuarioAsignado().getPersonaUsuario();
			checkListKpiEjecucionFirma.setIdPersona(personaEtR.getIdPersona());
			checkListKpiEjecucionFirma.setCheckListKpiEjecucion(checkListKpiEjecucion);
			if (personaEtR.getFirma() != null) {
				checkListKpiEjecucionFirma.setContieneFirma(true);
				checkListKpiEjecucionFirma.setFirma(personaEtR.getFirma());
			}
			checkListKpiEjecucionFirma.setNombre(personaEtR.getNombreCompleto());
			checkListKpiEjecucionFirma.setIdentificacion(personaEtR.getIdentificacionPersona());
			checkListKpiEjecucion.getCheckListKpiEjecucionFirma().add(checkListKpiEjecucionFirma);
			PersonaEt persona = iPersonaDao.getResponsableByAgencia(checkListEjecucion.getPlanificacion().getAgencia());
			if (persona != null) {
				checkListKpiEjecucionFirma = new CheckListKpiEjecucionFirmaEt();
				checkListKpiEjecucionFirma.setOrden(1L);
				checkListKpiEjecucionFirma.setCargo("GERENTE");
				if (persona.getFirma() != null) {
					checkListKpiEjecucionFirma.setContieneFirma(true);
					checkListKpiEjecucionFirma.setFirma(persona.getFirma());
				}
				checkListKpiEjecucionFirma.setIdPersona(persona.getIdPersona());
				checkListKpiEjecucionFirma.setNombre(persona.getNombreCompleto());
				checkListKpiEjecucionFirma.setCheckListKpiEjecucion(checkListKpiEjecucion);
				checkListKpiEjecucionFirma.setIdentificacion(persona.getIdentificacionPersona());
				checkListKpiEjecucion.getCheckListKpiEjecucionFirma().add(checkListKpiEjecucionFirma);
			}
			iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpiEjecucion, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarCheckListKpiEjecucionFirma " + " " + e.getMessage());
		}
	}

	public String validacionFirmaArqueoCajaC() {
		String mensaje = "";
		try {
			if (checkListKpiEjecucionSeleccionado.getNombreVendedor() == null
					|| checkListKpiEjecucionSeleccionado.getNombreVendedor().equals("")) {
				mensaje = "Por favor ingresar nombre de vendedor para continuar";
				return mensaje;
			}
			if (checkListKpiEjecucionSeleccionado.getAuditor() == null) {
				mensaje = "Por favor Seleccionar un auditor para continuar";
				return mensaje;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método validacionFirmaArqueoCajaC " + " " + e.getMessage());
		}
		return mensaje;
	}

	public void modificarCheckListFirma(CheckListEjecucionFirmaEt checkListEjecucionFirma) {
		this.checkListEjecucionFirmaSeleccionado = checkListEjecucionFirma;
	}

	public void modificarCheckListKpiFirma(CheckListKpiEjecucionFirmaEt checkListKpiEjecucionFirma) {
		this.checkListKpiEjecucionFirmaSeleccionado = checkListKpiEjecucionFirma;
	}

	public void verificarClaveCheckList() {
		PersonaEt persona = null;
		try {
			UsuarioEt usuario = appMain.getUsuario();
			if (checkListEjecucionFirmaSeleccionado.getFirma() != null) {
				if (checkListEjecucionFirmaSeleccionado.getOrden() == 0L) {
					persona = iPersonaDao.getPersonaById(checkListEjecucionFirmaSeleccionado.getIdPersona());
					persona.setFirma(checkListEjecucionFirmaSeleccionado.getFirma());
					iPersonaDao.guardarPersona(persona, usuario);
				}
				if (checkListEjecucionFirmaSeleccionado.getOrden() == 1L) {
					persona = iPersonaDao.getPersonaById(checkListEjecucionFirmaSeleccionado.getIdPersona());
					persona.setFirma(checkListEjecucionFirmaSeleccionado.getFirma());
					iPersonaDao.guardarPersona(persona, usuario);
				}
				if (checkListEjecucionFirmaSeleccionado.getOrden() == 2L) {
					persona = iPersonaDao.getPersonaById(checkListEjecucionFirmaSeleccionado.getIdPersona());
					persona.setFirma(checkListEjecucionFirmaSeleccionado.getFirma());
					iPersonaDao.guardarPersona(persona, usuario);
				}
				checkListEjecucionFirmaSeleccionado.setFirmado(true);
			}
			iCheckListEjecucionFirmaDao.guardarCheckListEjecucionFirma(checkListEjecucionFirmaSeleccionado, usuario);
			showInfo("Firmado con Éxito", FacesMessage.SEVERITY_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método verificarClave " + " " + e.getMessage());
		}
	}

	public void verificarClaveCheckListKpi() {
		PersonaEt persona = null;
		try {
			UsuarioEt usuario = appMain.getUsuario();
			if (checkListKpiEjecucionFirmaSeleccionado.getFirma() != null) {
				if (checkListKpiEjecucionFirmaSeleccionado.getOrden() == 1L) {
					persona = iPersonaDao.getPersonaById(checkListKpiEjecucionFirmaSeleccionado.getIdPersona());
					persona.setFirma(checkListKpiEjecucionFirmaSeleccionado.getFirma());
					iPersonaDao.guardarPersona(persona, usuario);
				}
				if (checkListKpiEjecucionFirmaSeleccionado.getOrden() == 2L) {
					persona = iPersonaDao.getPersonaById(checkListKpiEjecucionFirmaSeleccionado.getIdPersona());
					persona.setFirma(checkListKpiEjecucionFirmaSeleccionado.getFirma());
					iPersonaDao.guardarPersona(persona, usuario);
				}
				checkListKpiEjecucionFirmaSeleccionado.setFirmado(true);
			}
			iCheckListKpiEjecucionFirmaDao.guardarCheckListKpiEjecucionFirma(checkListKpiEjecucionFirmaSeleccionado,
					usuario);
			showInfo("Firmado con Éxito", FacesMessage.SEVERITY_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método verificarClave " + " " + e.getMessage());
		}

	}

	public void anadirPromotor() {
		try {
			if (checkListKpiEjecucionSeleccionado.getArqueoCajaPromotor() == null) {
				checkListKpiEjecucionSeleccionado.setArqueoCajaPromotor(new ArrayList<>());
			}
			ArqueoCajaPromotorEt arqueoCajaPromotor = new ArqueoCajaPromotorEt();
			arqueoCajaPromotor.setCheckListKpiEjecucion(checkListKpiEjecucionSeleccionado);
			checkListKpiEjecucionSeleccionado.getArqueoCajaPromotor().add(arqueoCajaPromotor);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método anadirPromotor " + " " + e.getMessage());
		}
	}

	public void anadirProductoCaducado() {
		try {
			if (checkListProcesoEjecucionTienda.getCheckListKpiEjecucionB() == null) {
				checkListProcesoEjecucionTienda.setCheckListKpiEjecucionB(new ArrayList<>());
			}
			CheckListKpiEjecucionBEt checkListKpiEjecucionB = new CheckListKpiEjecucionBEt();
			checkListKpiEjecucionB.setCheckListProcesoEjecucion(checkListProcesoEjecucionTienda);
			checkListProcesoEjecucionTienda.getCheckListKpiEjecucionB().add(checkListKpiEjecucionB);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método anadirProductoCaducado " + " " + e.getMessage());
		}
	}

	public void quitarProductoCaducado(CheckListKpiEjecucionBEt checkListKpiEjecucionB) {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			Date fechaRegistro = UtilEnum.FECHA_REGISTRO.getValue();
			checkListKpiEjecucionB.setFechaModificacion(fechaRegistro);
			checkListKpiEjecucionB.setEstado(EstadoEnum.INA);
			checkListProcesoEjecucionTienda.getCheckListKpiEjecucionB().remove(checkListKpiEjecucionB);
			iCheckListKpiEjecucionBDao.guardaCheckListKpiEjecucionB(checkListKpiEjecucionB, usuario);
			iCheckListProcesoEjecucionDao.guardarCheckListProcesoEjecucion(checkListProcesoEjecucionTienda, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método quitarProductoCaducado " + " " + e.getMessage());
		}

	}

	public void mostrarTexto(ParametrosGeneralesEt parametro, CheckListKpiEjecucionAEt checkListKpiEjecucionA) {
		try {
			checkListKpiEjecucionA.setObservacion(parametro.getValorLista());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método mostrarTexto " + " " + e.getMessage());
		}
	}

	public void desabilitar(ParametrosGeneralesEt parametro, CheckListKpiEjecucionBEt checkListKpiEjecucionB) {
		try {
			if (parametro != null) {
				if (parametro.getNombreLista().equals("Cumple")) {
					checkListKpiEjecucionB.setBloqueo(false);
				}
			} else {
				checkListKpiEjecucionB.setBloqueo(true);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método desabilitar " + " " + e.getMessage());
		}
	}

	public List<TipoEstacionEt> getTipoEstacionList() {
		List<TipoEstacionEt> tipoEstaciones = new ArrayList<TipoEstacionEt>();
		try {
			if (checkListEjecucion != null) {
				tipoEstaciones = iTipoEstacionDao
						.getTipoEstacionList0(checkListEjecucion.getPlanificacion().getAgencia().getTipoEstacion());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTipoEstacionList " + " " + e.getMessage());
		}
		return tipoEstaciones;
	}

	public List<ParametrosGeneralesEt> getTipoRubroList() {
		List<ParametrosGeneralesEt> parametrosGenerales = new ArrayList<ParametrosGeneralesEt>();
		try {
			ParametrosGeneralesEt parametrosGeneral = iParametrolGeneralDao.getObjPadre("47");
			parametrosGenerales = iParametrolGeneralDao.getListaHIjos(parametrosGeneral);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTipoRubroList " + " " + e.getMessage());
		}
		return parametrosGenerales;
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

	public List<ParametrosGeneralesEt> getTipoCategoriaList() {
		List<ParametrosGeneralesEt> parametrosGenerales = new ArrayList<ParametrosGeneralesEt>();
		try {
			ParametrosGeneralesEt parametrosGeneral = iParametrolGeneralDao.getObjPadre("65");
			parametrosGenerales = iParametrolGeneralDao.getListaHIjos(parametrosGeneral);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTipoCategoriaList " + " " + e.getMessage());
		}
		return parametrosGenerales;
	}

	public List<ParametrosGeneralesEt> getTipoDocPendienteList() {
		List<ParametrosGeneralesEt> parametrosGenerales = new ArrayList<>();
		try {
			ParametrosGeneralesEt parametroP = iParametrolGeneralDao.getObjPadre("78");
			parametrosGenerales = iParametrolGeneralDao.getListaHIjos(parametroP);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTipoDocPendienteList " + " " + e.getMessage());
		}
		return parametrosGenerales;
	}

	public List<ParametrosGeneralesEt> getTipoCriterioList() {
		List<ParametrosGeneralesEt> parametrosGenerales = new ArrayList<>();
		try {
			ParametrosGeneralesEt parametroP = iParametrolGeneralDao.getObjPadre("87");
			parametrosGenerales = iParametrolGeneralDao.getListaHIjos(parametroP);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTipoCriterioList " + " " + e.getMessage());
		}
		return parametrosGenerales;
	}

	public List<ParametrosGeneralesEt> getMedioPagoList() {
		List<ParametrosGeneralesEt> parametrosGenerales = new ArrayList<>();
		try {
			ParametrosGeneralesEt parametroP = iParametrolGeneralDao.getObjPadre("159");
			parametrosGenerales = iParametrolGeneralDao.getListaHIjos(parametroP);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTipoCriterioList " + " " + e.getMessage());
		}
		return parametrosGenerales;
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

	public CheckListKpiEjecucionEt getCheckListKpiEjecucionSeleccionado() {
		return checkListKpiEjecucionSeleccionado;
	}

	public void setCheckListKpiEjecucionSeleccionado(CheckListKpiEjecucionEt checkListKpiEjecucionSeleccionado) {
		this.checkListKpiEjecucionSeleccionado = checkListKpiEjecucionSeleccionado;
	}

	public boolean isValidarGuardar() {
		return validarGuardar;
	}

	public void setValidarGuardar(boolean validarGuardar) {
		this.validarGuardar = validarGuardar;
	}

	public ParametrosGeneralesEt getTipoRubroSeleccionado() {
		return tipoRubroSeleccionado;
	}

	public void setTipoRubroSeleccionado(ParametrosGeneralesEt tipoRubroSeleccionado) {
		this.tipoRubroSeleccionado = tipoRubroSeleccionado;
	}

	public Double getValorRubro() {
		return valorRubro;
	}

	public void setValorRubro(Double valorRubro) {
		this.valorRubro = valorRubro;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public List<CheckListEjecucionAdjuntoEt> getCheckListEjecucionAdjuntoEliminado() {
		return checkListEjecucionAdjuntoEliminado;
	}

	public void setCheckListEjecucionAdjuntoEliminado(
			List<CheckListEjecucionAdjuntoEt> checkListEjecucionAdjuntoEliminado) {
		this.checkListEjecucionAdjuntoEliminado = checkListEjecucionAdjuntoEliminado;
	}

	public CheckListProcesoEjecucionEt getCheckListProcesoEjecucionTienda() {
		return checkListProcesoEjecucionTienda;
	}

	public void setCheckListProcesoEjecucionTienda(CheckListProcesoEjecucionEt checkListProcesoEjecucionTienda) {
		this.checkListProcesoEjecucionTienda = checkListProcesoEjecucionTienda;
	}

	public CheckListProcesoEjecucionEt getCheckListProcesoEjecucionEfectivo() {
		return checkListProcesoEjecucionEfectivo;
	}

	public void setCheckListProcesoEjecucionEfectivo(CheckListProcesoEjecucionEt checkListProcesoEjecucionEfectivo) {
		this.checkListProcesoEjecucionEfectivo = checkListProcesoEjecucionEfectivo;
	}

	public CheckListProcesoEjecucionEt getCheckListProcesoEjecucionCombustible() {
		return checkListProcesoEjecucionCombustible;
	}

	public void setCheckListProcesoEjecucionCombustible(
			CheckListProcesoEjecucionEt checkListProcesoEjecucionCombustible) {
		this.checkListProcesoEjecucionCombustible = checkListProcesoEjecucionCombustible;
	}

	public CheckListProcesoEjecucionEt getCheckListProcesoEjecucionProcedimientoS() {
		return checkListProcesoEjecucionProcedimientoS;
	}

	public void setCheckListProcesoEjecucionProcedimientoS(
			CheckListProcesoEjecucionEt checkListProcesoEjecucionProcedimientoS) {
		this.checkListProcesoEjecucionProcedimientoS = checkListProcesoEjecucionProcedimientoS;
	}

	public String getFormulario() {
		return formulario;
	}

	public void setFormulario(String formulario) {
		this.formulario = formulario;
	}

	public CheckListEjecucionFirmaEt getCheckListEjecucionFirmaSeleccionado() {
		return checkListEjecucionFirmaSeleccionado;
	}

	public void setCheckListEjecucionFirmaSeleccionado(CheckListEjecucionFirmaEt checkListEjecucionFirmaSeleccionado) {
		this.checkListEjecucionFirmaSeleccionado = checkListEjecucionFirmaSeleccionado;
	}

	public CheckListKpiEjecucionFirmaEt getCheckListKpiEjecucionFirmaSeleccionado() {
		return checkListKpiEjecucionFirmaSeleccionado;
	}

	public void setCheckListKpiEjecucionFirmaSeleccionado(
			CheckListKpiEjecucionFirmaEt checkListKpiEjecucionFirmaSeleccionado) {
		this.checkListKpiEjecucionFirmaSeleccionado = checkListKpiEjecucionFirmaSeleccionado;
	}

	public Double getValorVenta() {
		return valorVenta;
	}

	public void setValorVenta(Double valorVenta) {
		this.valorVenta = valorVenta;
	}

	public Double getValorBlindado() {
		return valorBlindado;
	}

	public void setValorBlindado(Double valorBlindado) {
		this.valorBlindado = valorBlindado;
	}

	public Double getValorPromotor() {
		return valorPromotor;
	}

	public void setValorPromotor(Double valorPromotor) {
		this.valorPromotor = valorPromotor;
	}

	public Double getValorDiferencia() {
		return valorDiferencia;
	}

	public void setValorDiferencia(Double valorDiferencia) {
		this.valorDiferencia = valorDiferencia;
	}

	public boolean isBloqueoTipo() {
		return bloqueoTipo;
	}

	public void setBloqueoTipo(boolean bloqueoTipo) {
		this.bloqueoTipo = bloqueoTipo;
	}

	public String getDescripcionN() {
		return descripcionN;
	}

	public void setDescripcionN(String descripcionN) {
		this.descripcionN = descripcionN;
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

	@Override
	protected void onDestroy() {
		iCorreoDao.remove();
		iUsuarioDao.remove();
		iPersonaDao.remove();
		iArqueoCajaDao.remove();
		iResponsableDao.remove();
		iTipoEstacionDao.remove();
		iParametrolGeneralDao.remove();
		iArqueoCajaGeneralDao.remove();
		iArqueoFondoSueltoDao.remove();
		iCheckListEjecucionDao.remove();
		iArqueoCajaFuerteDao.remove();
		iCheckListKpiEjecucionDao.remove();
		iCheckListKpiEjecucionBDao.remove();
		iCheckListKpiEjecucionADao.remove();
		iCheckListEjecucionFirmaDao.remove();
		iCheckListEjecucionAdjuntoDao.remove();
		iCheckListProcesoEjecucionDao.remove();
		iCheckListKpiEjecucionFirmaDao.remove();
	}

}
