package com.primax.bean.vs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.enm.gen.ParametrosGeneralesEnum;
import com.primax.enm.gen.RutaFileEnum;
import com.primax.jpa.enums.EstadoCheckListEnum;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.enums.TipoCheckListEnum;
import com.primax.jpa.param.CriterioEvaluacionEt;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.param.KPICriticoEt;
import com.primax.jpa.param.NivelEvaluacionDetalleEt;
import com.primax.jpa.param.NivelEvaluacionEt;
import com.primax.jpa.param.ParametrosGeneralesEt;
import com.primax.jpa.param.ProcesoDetalleEt;
import com.primax.jpa.param.ProcesoEt;
import com.primax.jpa.param.TipoCargoEt;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.param.TipoEstacionEt;
import com.primax.jpa.pla.CheckListEt;
import com.primax.jpa.pla.CheckListEtiquetaEt;
import com.primax.jpa.pla.CheckListInformeCabeceraEt;
import com.primax.jpa.pla.CheckListKpiEt;
import com.primax.jpa.pla.CheckListPieFirmaEt;
import com.primax.jpa.pla.CheckListProcesoEt;
import com.primax.jpa.pla.CheckListProcesoFormularioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.ICheckListDao;
import com.primax.srv.idao.ICheckListEtiquetaDao;
import com.primax.srv.idao.ICheckListInformeCabeceraDao;
import com.primax.srv.idao.ICheckListKpiDao;
import com.primax.srv.idao.ICheckListPieFirmaDao;
import com.primax.srv.idao.ICheckListProcesoDao;
import com.primax.srv.idao.ICheckListProcesoFormularioDao;
import com.primax.srv.idao.ICriterioEvaluacionDao;
import com.primax.srv.idao.IEvaluacionDao;
import com.primax.srv.idao.IGeneralUtilsDao;
import com.primax.srv.idao.IKPICriticoDao;
import com.primax.srv.idao.INivelEvaluacionDao;
import com.primax.srv.idao.IParametrolGeneralDao;
import com.primax.srv.idao.IProcesoDao;
import com.primax.srv.idao.IProcesoDetalleDao;
import com.primax.srv.idao.ITipoCargoDao;
import com.primax.srv.idao.ITipoChecKListDao;
import com.primax.srv.idao.ITipoEstacionDao;

@Named("PlantillaCriterioEspecificoBn")
@ViewScoped
public class PlantillaCriterioEspecificoBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IProcesoDao iDimensionDao;
	@EJB
	private ICheckListDao iCheckListDao;
	@EJB
	private IKPICriticoDao iKPICriticoDao;
	@EJB
	private IEvaluacionDao iEvaluacionDao;
	@EJB
	private ITipoEstacionDao iTipoEstacionDao;
	@EJB
	private ICheckListKpiDao iCheckListKpiDao;
	@EJB
	private ITipoChecKListDao iTipoChecListDao;
	@EJB
	private IGeneralUtilsDao iGeneralUtils0Dao;
	@EJB
	private INivelEvaluacionDao iNivelEvaluacionDao;
	@EJB
	private IProcesoDetalleDao iDimensionDetalleDao;
	@EJB
	private ICheckListProcesoDao iCheckListProcesoDao;
	@EJB
	private ICriterioEvaluacionDao iCriterioEvaluacionDao;

	@EJB
	private ITipoCargoDao iTipoCargoDao;
	@EJB
	private ICheckListEtiquetaDao iCheckListEtiquetaDao;
	@EJB
	private IParametrolGeneralDao iParametrolGeneralDao;
	@EJB
	private ICheckListPieFirmaDao iCheckListPieFirmaDao;
	@EJB
	private ICheckListInformeCabeceraDao iCheckListInformeCabeceraDao;
	@EJB
	private ICheckListProcesoFormularioDao iCheckListProcesoFormularioDao;

	@Inject
	private AppMain appMain;

	private String condicion;

	private Long totalPuntaje = 0L;
	private String codigoCheckList = "";
	private List<TipoCargoEt> tipoCargos;
	private ProcesoEt procesoSeleccionado;
	private List<TipoEstacionEt> tipoEstacion;
	private KPICriticoEt kPICriticoSeleccionado;
	private EvaluacionEt evaluacionSeleccionado;
	private TipoChecKListEt tipoChecListSeleccionado;
	private CheckListEt checkListSeleccionado = null;
	private List<CheckListProcesoEt> checkListDetalles;
	private NivelEvaluacionEt nivelEvaluacionSeleccionado;
	private DualListModel<ProcesoDetalleEt> procesoDetalles;

	/**
	 * Variables Versión 3
	 */
	private boolean frm1 = false;
	private boolean frm2 = false;
	private boolean frm3 = false;
	private boolean frm0_1 = false;
	private boolean frm1_1 = false;
	private boolean frm2_1 = false;
	private boolean frm3_1 = false;
	private boolean frm4_1 = false;
	private boolean frm5_1 = false;
	private boolean frm6_1 = false;
	private boolean frm7_1 = false;
	private boolean frm0_2 = false;
	private boolean frm1_2 = false;
	private boolean frm2_2 = false;
	private boolean frm3_2 = false;
	private boolean frm4_2 = false;
	private boolean frm5_2 = false;
	private boolean frm6_2 = false;
	private boolean frm7_2 = false;
	private boolean frm0_3 = false;
	private boolean frm1_3 = false;
	private boolean frm2_3 = false;
	private boolean frm3_3 = false;
	private boolean frm4_3 = false;
	private boolean frm5_3 = false;
	private boolean frm6_3 = false;
	private boolean frm7_3 = false;
	private String nombFrm0_1 = "";
	private String nombFrm1_1 = "";
	private String nombFrm2_1 = "";
	private String nombFrm3_1 = "";
	private String nombFrm4_1 = "";
	private String nombFrm5_1 = "";
	private String nombFrm6_1 = "";
	private String nombFrm7_1 = "";
	private String nombFrm0_2 = "";
	private String nombFrm1_2 = "";
	private String nombFrm2_2 = "";
	private String nombFrm3_2 = "";
	private String nombFrm4_2 = "";
	private String nombFrm5_2 = "";
	private String nombFrm6_2 = "";
	private String nombFrm7_2 = "";
	private String nombFrm0_3 = "";
	private String nombFrm1_3 = "";
	private String nombFrm2_3 = "";
	private String nombFrm3_3 = "";
	private String nombFrm4_3 = "";
	private String nombFrm5_3 = "";
	private String nombFrm6_3 = "";
	private String nombFrm7_3 = "";
	private String nombreFrm_1 = "";
	private String nombreFrm_2 = "";
	private String nombreFrm_3 = "";
	private CheckListEt checkListImprimir;
	private CheckListKpiEt checkListKpiSeleccionado;
	private ParametrosGeneralesEt parEmpresaSelecciona;
	private List<CheckListEtiquetaEt> checkListEtiquetas;
	private CheckListProcesoEt checkListProcesoSeleccionado;
	private CheckListProcesoEt checkListProcesoSeleccionadoI;
	private CheckListProcesoEt checkListProcesoSeleccionadoT;
	private CheckListProcesoEt checkListDimensionSeleccionado;
	private CriterioEvaluacionEt criterioEvaluacionSeleccionada;
	private CheckListProcesoFormularioEt checkListFrmSeleccionado;
	private List<CheckListProcesoFormularioEt> procesoFormulario1;
	private List<CheckListProcesoFormularioEt> procesoFormulario2;
	private List<CheckListProcesoFormularioEt> procesoFormulario3;
	private CheckListProcesoFormularioEt checkListFrmSeleccionadoM;

	@Override
	protected void init() {
		inicializarObj();
		buscar();
	}

	public void inicializarObj() {
		procesoSeleccionado = null;
		tipoChecListSeleccionado = null;
		checkListDetalles = new ArrayList<>();
	}

	public void buscar() {
		try {
			checkListSeleccionado = iCheckListDao.getCheckPendiente(TipoCheckListEnum.CRITERIO_ESPECIFICO,
					appMain.getUsuario());
			if (checkListSeleccionado == null) {
				checkListSeleccionado = new CheckListEt();
				inicializarObj();
			} else {
				mostrarTotal(checkListSeleccionado);
				mostrarInformacion(checkListSeleccionado);
				if (checkListSeleccionado.getCheckListProceso() != null) {
					checkListProcesoSeleccionadoI = checkListSeleccionado.getCheckListProceso().get(0);
					checkListProcesoSeleccionadoT = checkListSeleccionado.getCheckListProceso().get(0);
					cargarFormulario(checkListProcesoSeleccionadoT);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}
	}

	public void buscarCheckList() {
		try {
			if (codigoCheckList != null) {
				checkListSeleccionado = iCheckListDao.getCheck(TipoCheckListEnum.CRITERIO_ESPECIFICO, codigoCheckList);
				if (checkListSeleccionado == null) {
					inicializarObj();
					showInfo("No existe Plantilla con código " + " " + codigoCheckList, FacesMessage.SEVERITY_INFO);
				} else {
					inicializarObj();
					mostrarInformacionN(checkListSeleccionado);
					mostrarTotal(checkListSeleccionado);
					codigoCheckList = "";
				}
			} else {
				showInfo("Por favor ingresar código plantilla", FacesMessage.SEVERITY_INFO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}
	}

	public void mostrarInformacion(CheckListEt checkList) {
		try {
			evaluacionSeleccionado = checkList.getEvaluacion();
			tipoChecListSeleccionado = checkList.getTipoChecKList();
			nivelEvaluacionSeleccionado = checkList.getNivelEvaluacion();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método mostrarInformacion " + " " + e.getMessage());
		}
	}

	public void guardar() {
		String codigo = "";
		String mensaje = "";
		String acronimo0 = "";
		try {
			mensaje = validacionGuardar();
			if (!mensaje.equals("")) {
				showInfo(mensaje, FacesMessage.SEVERITY_INFO);
				return;
			}
			UsuarioEt usuario = appMain.getUsuario();
			acronimo0 = evaluacionSeleccionado.getAcronimo() == null ? "PX" : evaluacionSeleccionado.getAcronimo();
			codigo = acronimo0 + "-" + String.format("%04d", checkListSeleccionado.getIdCheckList());
			checkListSeleccionado.setCodigo(codigo);
			checkListSeleccionado.setEvaluacion(evaluacionSeleccionado);
			checkListSeleccionado.setTipoChecKList(tipoChecListSeleccionado);
			checkListSeleccionado.setNivelEvaluacion(nivelEvaluacionSeleccionado);
			checkListSeleccionado.setEstadoCheckList(EstadoCheckListEnum.GENERADO);
			iCheckListDao.guardarCheckList(checkListSeleccionado, usuario);
			showInfo("Información Grabada con Éxito ", FacesMessage.SEVERITY_INFO);
			buscar();
			checkListSeleccionado = null;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}
	}

	public void agregar() {
		String codigo = "";
		String mensaje = "";
		String acronimo0 = "";
		CheckListProcesoEt checkListProceso = null;
		try {
			mensaje = validacionAgregar();
			if (!mensaje.equals("")) {
				showInfo(mensaje, FacesMessage.SEVERITY_INFO);
				return;
			}
			UsuarioEt usuario = appMain.getUsuario();
			if (checkListSeleccionado.getIdCheckList() == null) {
				checkListSeleccionado = new CheckListEt();
				checkListSeleccionado.setCheckListProceso(new ArrayList<>());
				checkListProceso = new CheckListProcesoEt();
				checkListProceso.setProceso(procesoSeleccionado);
				checkListProceso.setCheckListKpi(new ArrayList<>());
				checkListProceso.setCheckList(checkListSeleccionado);
				checkListProceso.setOrden(procesoSeleccionado.getOrden());
				checkListProceso.setDescripcion(procesoSeleccionado.getNombreProceso());
				for (ProcesoDetalleEt procesoDetalle : procesoDetalles.getTarget()) {
					CheckListKpiEt checkListKpi = new CheckListKpiEt();
					checkListKpi.setCriterioEvaluacion(new CriterioEvaluacionEt());
					checkListKpi.setProcesoDetalle(procesoDetalle);
					checkListKpi.setOrden(procesoDetalle.getOrden());
					checkListKpi.setCheckListProceso(checkListProceso);
					CriterioEvaluacionEt criterioEvaluacion = iCriterioEvaluacionDao
							.getCriterioEvaluacionByProcesoDetalle(procesoDetalle);
					if (criterioEvaluacion == null) {
						mensaje = "Se debe asignar un criterio de evaluacion para el KPI ("
								+ procesoDetalle.getNombreProcesoDetalle() + ") correspondiente al proceso ("
								+ procesoSeleccionado.getNombreProceso() + ")";
						showInfo(mensaje, FacesMessage.SEVERITY_INFO);
						return;
					}
					checkListKpi.setCriterioEvaluacion(criterioEvaluacion);
					checkListProceso.getCheckListKpi().add(checkListKpi);
				}
				acronimo0 = evaluacionSeleccionado.getAcronimo() == null ? "PX" : evaluacionSeleccionado.getAcronimo();
				codigo = acronimo0 + "-" + "0000";
				checkListSeleccionado.setCodigo(codigo);
				checkListSeleccionado.setVisualizarPeso(true);
				checkListSeleccionado.setVisualizarComentario(true);
				checkListSeleccionado.setEvaluacion(evaluacionSeleccionado);
				checkListSeleccionado.setTipoChecKList(tipoChecListSeleccionado);
				checkListSeleccionado.getCheckListProceso().add(checkListProceso);
				checkListSeleccionado.setNivelEvaluacion(nivelEvaluacionSeleccionado);
				checkListSeleccionado.setTipoCheckList(TipoCheckListEnum.CRITERIO_ESPECIFICO);
				checkListSeleccionado.setEstadoCheckList(EstadoCheckListEnum.PLANTILLA);
				iCheckListDao.guardarCheckList(checkListSeleccionado, usuario);
			} else {
				if (containsProceso(checkListSeleccionado.getCheckListProceso(), procesoSeleccionado)) {
					CheckListProcesoEt procesoSelected = procesoSelected();
					for (ProcesoDetalleEt procesoDetalle : procesoDetalles.getTarget()) {
						if (!containsProcesoDetalle(procesoSelected.getCheckListKpi(), procesoDetalle)) {
							CheckListKpiEt checkListKpi = new CheckListKpiEt();
							checkListKpi.setCriterioEvaluacion(new CriterioEvaluacionEt());
							checkListKpi.setProcesoDetalle(procesoDetalle);
							checkListKpi.setOrden(procesoDetalle.getOrden());
							checkListKpi.setCheckListProceso(procesoSelected);
							CriterioEvaluacionEt criterioEvaluacion = iCriterioEvaluacionDao
									.getCriterioEvaluacionByProcesoDetalle(procesoDetalle);
							checkListKpi.setCriterioEvaluacion(criterioEvaluacion);
							iCheckListKpiDao.guardarCheckListKpi(checkListKpi, usuario);
						}
					}
				} else {
					checkListProceso = new CheckListProcesoEt();
					checkListProceso.setProceso(procesoSeleccionado);
					checkListProceso.setCheckListKpi(new ArrayList<>());
					checkListProceso.setCheckList(checkListSeleccionado);
					checkListProceso.setOrden(procesoSeleccionado.getOrden());
					checkListProceso.setCheckListProcesoFormulario(new ArrayList<>());
					checkListProceso.setDescripcion(procesoSeleccionado.getNombreProceso());
					for (ProcesoDetalleEt procesoDetalle : procesoDetalles.getTarget()) {
						CheckListKpiEt checkListKpi = new CheckListKpiEt();
						checkListKpi.setCriterioEvaluacion(new CriterioEvaluacionEt());
						checkListKpi.setProcesoDetalle(procesoDetalle);
						checkListKpi.setOrden(procesoDetalle.getOrden());
						checkListKpi.setCheckListProceso(checkListProceso);
						CriterioEvaluacionEt criterioEvaluacion = iCriterioEvaluacionDao
								.getCriterioEvaluacionByProcesoDetalle(procesoDetalle);
						if (criterioEvaluacion == null) {
							mensaje = "Se debe asignar un criterio de evaluacion para el KPI ("
									+ procesoDetalle.getNombreProcesoDetalle() + ") correspondiente al proceso ("
									+ procesoSeleccionado.getNombreProceso() + ")";
							showInfo(mensaje, FacesMessage.SEVERITY_INFO);
							return;
						}
						checkListKpi.setCriterioEvaluacion(criterioEvaluacion);
						checkListProceso.getCheckListKpi().add(checkListKpi);
					}
					if (checkListSeleccionado.getCheckListProceso() == null) {
						checkListSeleccionado.setCheckListProceso(new ArrayList<>());
					}
					checkListSeleccionado.getCheckListProceso().add(checkListProceso);
					// iCheckListProcesoDao.guardarCheckListProceso(checkListProceso, usuario);
				}
				checkListSeleccionado.setNivelEvaluacion(nivelEvaluacionSeleccionado);
				iCheckListDao.guardarCheckList(checkListSeleccionado, usuario);
				iCheckListDao.clear();
				checkListSeleccionado = iCheckListDao.getCheckPendiente(TipoCheckListEnum.CRITERIO_ESPECIFICO,
						appMain.getUsuario());
			}
			mostrarTotal(checkListSeleccionado);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método agregar " + " " + e.getMessage());
		}
	}

	public String validacionAgregar() {
		String mensaje = "";
		try {
			if (nivelEvaluacionSeleccionado == null) {
				mensaje = "Por favor seleccionar nivel de evaluación";
				return mensaje;
			}
			if (evaluacionSeleccionado == null) {
				mensaje = "Por favor seleccionar evaluación";
				return mensaje;
			}
			if (tipoChecListSeleccionado == null) {
				mensaje = "Por favor seleccionar tipo checkList";
				return mensaje;
			}
			if (procesoSeleccionado == null) {
				mensaje = "Por favor seleccionar proceso";
				return mensaje;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método validacionAgregar " + " " + e.getMessage());
		}
		return mensaje;
	}

	public String validacionGuardar() {
		String mensaje = "";
		try {
			if (checkListSeleccionado == null) {
				mensaje = "Por favor agregar procesos a la plantilla";
				return mensaje;
			}
			if (totalPuntaje != 100L) {
				mensaje = "Por favor grabar plantilla con puntaje de 100";
				return mensaje;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método validacionGuardar " + " " + e.getMessage());
		}
		return mensaje;
	}

	public boolean containsProceso(final List<CheckListProcesoEt> list, final ProcesoEt proceso) {
		if (list != null) {
			return list.stream().filter(o -> o.getProceso().equals(proceso)).findFirst().isPresent();
		}
		return false;
	}

	public boolean containsProcesoDetalle(final List<CheckListKpiEt> list, final ProcesoDetalleEt procesoDetalle) {
		return list.stream().filter(o -> o.getProcesoDetalle().equals(procesoDetalle)).findFirst().isPresent();
	}

	public CheckListProcesoEt procesoSelected() {
		CheckListProcesoEt checkListProcesoSelected = null;
		try {
			for (CheckListProcesoEt checkListProceso : checkListSeleccionado.getCheckListProceso()) {
				if (checkListProceso.getProceso().getIdproceso().equals(procesoSeleccionado.getIdproceso())) {
					checkListProcesoSelected = checkListProceso;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método procesoSelected " + " " + e.getMessage());
		}
		return checkListProcesoSelected;
	}

	public void mostrarInformacionN(CheckListEt checkList) {
		String codigo = "";
		String acronimo0 = "";
		try {
			UsuarioEt usuario = appMain.getUsuario();
			CheckListEt checkListN = new CheckListEt();
			checkListN.setEvaluacion(checkList.getEvaluacion());
			checkListN.setTipoChecKList(checkList.getTipoChecKList());
			checkListN.setVisualizarPeso(checkList.isVisualizarPeso());
			checkListN.setEstadoCheckList(EstadoCheckListEnum.PLANTILLA);
			checkListN.setNivelEvaluacion(checkList.getNivelEvaluacion());
			checkListN.setTipoCheckList(TipoCheckListEnum.CRITERIO_ESPECIFICO);
			checkListN.setVisualizarComentario(checkList.isVisualizarComentario());
			checkListN.setCheckListProceso(new ArrayList<>());
			for (CheckListProcesoEt checkListProceso : checkList.getCheckListProceso()) {
				CheckListProcesoEt checkListProcesoN = new CheckListProcesoEt();
				checkListProcesoN.setCheckList(checkListN);
				checkListProcesoN.setOrden(checkListProceso.getOrden());
				checkListProcesoN.setProceso(checkListProceso.getProceso());
				checkListProcesoN.setDescripcion(checkListProceso.getDescripcion());
				checkListProcesoN.setCheckListKpi(new ArrayList<>());
				checkListProcesoN.setCheckListProcesoFormulario(new ArrayList<>());
				for (CheckListKpiEt checkListKpi : checkListProceso.getCheckListKpi()) {
					CheckListKpiEt checkListKpiN = new CheckListKpiEt();
					checkListKpiN.setOrden(checkListKpi.getOrden());
					checkListKpiN.setProcesoDetalle(checkListKpi.getProcesoDetalle());
					checkListKpiN.setCheckListProceso(checkListProcesoN);
					checkListKpiN.setCriterioEvaluacion(checkListKpi.getCriterioEvaluacion());
					checkListProcesoN.getCheckListKpi().add(checkListKpiN);
				}
				for (CheckListProcesoFormularioEt formulario : checkListProceso.getCheckListProcesoFormulario()) {
					CheckListProcesoFormularioEt formularioN = new CheckListProcesoFormularioEt();
					formularioN.setFrm0(formulario.isFrm0());
					formularioN.setFrm1(formulario.isFrm1());
					formularioN.setFrm2(formulario.isFrm2());
					formularioN.setFrm3(formulario.isFrm3());
					formularioN.setFrm4(formulario.isFrm4());
					formularioN.setFrm5(formulario.isFrm5());
					formularioN.setFrm6(formulario.isFrm6());
					formularioN.setFrm7(formulario.isFrm7());
					formularioN.setNombFrm0(formulario.getNombFrm0());
					formularioN.setNombFrm1(formulario.getNombFrm1());
					formularioN.setNombFrm2(formulario.getNombFrm2());
					formularioN.setNombFrm3(formulario.getNombFrm3());
					formularioN.setNombFrm4(formulario.getNombFrm4());
					formularioN.setNombFrm5(formulario.getNombFrm5());
					formularioN.setNombFrm6(formulario.getNombFrm6());
					formularioN.setNombFrm7(formulario.getNombFrm7());
					formularioN.setCheckListProceso(checkListProcesoN);
					formularioN.setNombreFrm(formulario.getNombreFrm());
					checkListProcesoN.getCheckListProcesoFormulario().add(formularioN);
				}
				checkListN.getCheckListProceso().add(checkListProcesoN);
			}
			iCheckListDao.guardarCheckList(checkListN, usuario);
			evaluacionSeleccionado = checkListN.getEvaluacion();
			nivelEvaluacionSeleccionado = checkListN.getNivelEvaluacion();
			acronimo0 = evaluacionSeleccionado.getAcronimo() == null ? "PX" : evaluacionSeleccionado.getAcronimo();
			codigo = acronimo0 + "-" + String.format("%04d", checkListN.getIdCheckList());
			checkListN.setCodigo(codigo);
			iCheckListDao.clear();
			iCheckListDao.guardarCheckList(checkListN, usuario);
			checkListSeleccionado = null;
			checkListSeleccionado = iCheckListDao.getCheckList(checkListN.getIdCheckList());
			evaluacionSeleccionado = checkListSeleccionado.getEvaluacion();
			tipoChecListSeleccionado = checkListSeleccionado.getTipoChecKList();
			nivelEvaluacionSeleccionado = checkListSeleccionado.getNivelEvaluacion();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método mostrarInformacion " + " " + e.getMessage());
		}
	}

	public Long sumarProceso(CheckListProcesoEt checkListProceso) {
		Long totalPuntajeD = 0L;
		try {
			for (int i = 0; i < checkListProceso.getCheckListKpi().size(); i++) {
				if (checkListProceso.getCheckListKpi().get(i).getkPICritico() == null) {
					totalPuntajeD += checkListProceso.getCheckListKpi().get(i).getProcesoDetalle().getPuntaje();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método sumarProceso " + " " + e.getMessage());
		}
		return totalPuntajeD;
	}

	public void mostrarTotal(CheckListEt checkList) {
		totalPuntaje = 0L;
		Long totalKpi = 0L;
		try {
			for (CheckListProcesoEt checkListProceso : checkList.getCheckListProceso()) {
				totalKpi += checkListProceso.getCheckListKpi().stream()
						.mapToLong(p -> p.getProcesoDetalle().getPuntaje()).sum();
			}
			totalPuntaje = totalKpi;
			System.out.println("Total Puntaje" + " " + totalPuntaje);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método mostrarTotal " + " " + e.getMessage());
		}
	}

	public void quitarKpi(CheckListKpiEt checkListKpi) {
		Long conteo = 0L;
		try {
			UsuarioEt usuario = appMain.getUsuario();
			checkListKpi.setEstado(EstadoEnum.INA);
			iCheckListKpiDao.guardarCheckListKpi(checkListKpi, usuario);
			CheckListProcesoEt checkListProceso = checkListKpi.getCheckListProceso();
			for (CheckListKpiEt checkListKpiC : checkListProceso.getCheckListKpi()) {
				if (checkListKpiC.getEstado().equals(EstadoEnum.ACT)) {
					conteo += 1;
				}
			}
			if (conteo == 0) {
				checkListProceso.setEstado(EstadoEnum.INA);
				iCheckListProcesoDao.guardarCheckListProceso(checkListProceso, usuario);
			}
			iCheckListDao.clear();
			checkListSeleccionado = iCheckListDao.getCheckPendiente(TipoCheckListEnum.CRITERIO_ESPECIFICO,
					appMain.getUsuario());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método quitarKpi " + " " + e.getMessage());
		}

	}

	public SelectItem[] getEstadosActIna() {
		SelectItem[] items = new SelectItem[2];
		items[0] = new SelectItem(EstadoEnum.ACT, EstadoEnum.ACT.getDescripcion());
		items[1] = new SelectItem(EstadoEnum.INA, EstadoEnum.INA.getDescripcion());
		return items;
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
					String ruta = iGeneralUtils0Dao.creaRuta(
							nivelEvaluacionD.getNivelEvaluacion().getIdNivelEvaluacion(),
							RutaFileEnum.RUTA_PROYECTO_DEPLOYED.getDescripcion() + File.separator + "resources"
									+ File.separator + "images" + File.separator + "nivelEvaluacion");
					iGeneralUtils0Dao.copyFile(nivelEvaluacionD.getImgNombre(), inputStreamImg, ruta);
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

	public List<CriterioEvaluacionEt> getCriterioEvaluacionList() {
		List<CriterioEvaluacionEt> criterioEvaluaciones = new ArrayList<CriterioEvaluacionEt>();
		try {
			criterioEvaluaciones = iCriterioEvaluacionDao.getCriterioEvaluacionBPMList();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getCriterioEvaluacionList " + " " + e.getMessage());
		}
		return criterioEvaluaciones;
	}

	public List<KPICriticoEt> getKPICriticoList() {
		List<KPICriticoEt> kPICriticos = new ArrayList<KPICriticoEt>();
		try {
			kPICriticos = iKPICriticoDao.getKPICriticoList(null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getKPICriticoList " + " " + e.getMessage());
		}
		return kPICriticos;
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
			evaluaciones = iEvaluacionDao.getEvaluacionByCriterio(false);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTipoControlList " + " " + e.getMessage());
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

	public List<ProcesoEt> getProcesos() {
		List<ProcesoEt> procesos = new ArrayList<ProcesoEt>();
		try {
			if (tipoChecListSeleccionado != null) {
				procesos = iDimensionDao.getProcesoByTipoList(tipoChecListSeleccionado);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getProcesos " + " " + e.getMessage());
		}
		return procesos;
	}

	public DualListModel<ProcesoDetalleEt> getProcesoDetalles() {
		procesoDetalles = new DualListModel<>();
		List<ProcesoDetalleEt> procesoDetalle = new ArrayList<ProcesoDetalleEt>();
		try {
			if (procesoSeleccionado != null) {
				procesoDetalle = procesoSeleccionado.getProcesoDetalle();
			}
			List<ProcesoDetalleEt> themesSource = procesoDetalle;
			List<ProcesoDetalleEt> themesTarget = new ArrayList<ProcesoDetalleEt>();
			procesoDetalles = new DualListModel<ProcesoDetalleEt>(themesSource, themesTarget);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getProcesoDetalles " + " " + e.getMessage());
		}
		return procesoDetalles;
	}

	public void setProcesoDetalles(DualListModel<ProcesoDetalleEt> procesoDetalles) {
		this.procesoDetalles = procesoDetalles;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	public void guardarFormulario() {
		String mensaje = "";
		try {
			mensaje = validacionFrm();
			if (!mensaje.equals("")) {
				showInfo(mensaje, FacesMessage.SEVERITY_INFO);
				return;
			}
			UsuarioEt usuario = appMain.getUsuario();
			checkListFrmSeleccionado.setCheckListProceso(checkListProcesoSeleccionado);
			iCheckListProcesoFormularioDao.guardarCheckListProcesoFormulario(checkListFrmSeleccionado, usuario);
			if (checkListProcesoSeleccionado.getCheckListProcesoFormulario() == null) {
				checkListProcesoSeleccionado.setCheckListProcesoFormulario(new ArrayList<>());
			}
			checkListProcesoSeleccionado.getCheckListProcesoFormulario().add(checkListFrmSeleccionado);
			RequestContext.getCurrentInstance().execute("PF('dialog_005').hide();");
			cargarFormulario(checkListProcesoSeleccionado);
			checkListFrmSeleccionado = null;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método adicional " + " " + e.getMessage());
		}
	}

	public void modificarFormulario() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			iCheckListProcesoFormularioDao.guardarCheckListProcesoFormulario(checkListFrmSeleccionadoM, usuario);
			RequestContext.getCurrentInstance().execute("PF('dialog_006').hide();");
			cargarFormulario(checkListProcesoSeleccionado);
			checkListFrmSeleccionadoM = null;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método modificarF " + " " + e.getMessage());
		}
	}

	public String validacionFrm() {
		String mensaje = "";
		try {
			if (checkListFrmSeleccionado.getNombreFrm() == null) {
				mensaje = "Por favor ingresar nombre de formulario";
				return mensaje;
			}
			if (!checkListFrmSeleccionado.isFrm0() && !checkListFrmSeleccionado.isFrm1()
					&& !checkListFrmSeleccionado.isFrm2() && !checkListFrmSeleccionado.isFrm3()
					&& !checkListFrmSeleccionado.isFrm4() && !checkListFrmSeleccionado.isFrm5()
					&& !checkListFrmSeleccionado.isFrm6() && !checkListFrmSeleccionado.isFrm7()) {
				mensaje = "Por favor Seleccionar mínimo una columna";
				return mensaje;
			}
			if (checkListProcesoSeleccionado.getCheckListProcesoFormulario() != null) {
				if (checkListProcesoSeleccionado.getCheckListProcesoFormulario().size() == 3) {
					mensaje = "Solo se permiten 3 formularios por cada proceso ";
					return mensaje;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método validacionFrm " + " " + e.getMessage());
		}
		return mensaje;
	}

	public void cargarFormulario(CheckListProcesoEt checkProceso) {

		frm1 = false;
		frm2 = false;
		frm3 = false;
		CheckListProcesoFormularioEt frmOjb;
		procesoFormulario1 = new ArrayList<>();
		procesoFormulario2 = new ArrayList<>();
		procesoFormulario3 = new ArrayList<>();

		try {
			List<CheckListProcesoFormularioEt> procesosFormulario = iCheckListProcesoFormularioDao
					.getCheckListFormularioByProceso(checkProceso);

			for (int i = 0; i < procesosFormulario.size(); i++) {

				if (i == 0) {
					frm1 = true;
					frmOjb = procesosFormulario.get(i);
					procesoFormulario1.add(frmOjb);
					nombreFrm_1 = frmOjb.getNombreFrm();
					frm0_1 = frmOjb.isFrm0();
					frm1_1 = frmOjb.isFrm1();
					frm2_1 = frmOjb.isFrm2();
					frm3_1 = frmOjb.isFrm3();
					frm4_1 = frmOjb.isFrm4();
					frm5_1 = frmOjb.isFrm5();
					frm6_1 = frmOjb.isFrm6();
					frm7_1 = frmOjb.isFrm7();
					nombFrm0_1 = frmOjb.getNombFrm0();
					nombFrm1_1 = frmOjb.getNombFrm1();
					nombFrm2_1 = frmOjb.getNombFrm2();
					nombFrm3_1 = frmOjb.getNombFrm3();
					nombFrm4_1 = frmOjb.getNombFrm4();
					nombFrm5_1 = frmOjb.getNombFrm5();
					nombFrm6_1 = frmOjb.getNombFrm6();
					nombFrm7_1 = frmOjb.getNombFrm7();
				}
				if (i == 1) {
					frm2 = true;
					frmOjb = procesosFormulario.get(i);
					procesoFormulario2.add(frmOjb);
					nombreFrm_2 = frmOjb.getNombreFrm();
					frm0_2 = frmOjb.isFrm0();
					frm1_2 = frmOjb.isFrm1();
					frm2_2 = frmOjb.isFrm2();
					frm3_2 = frmOjb.isFrm3();
					frm4_2 = frmOjb.isFrm4();
					frm5_2 = frmOjb.isFrm5();
					frm6_2 = frmOjb.isFrm6();
					frm7_2 = frmOjb.isFrm7();
					nombFrm0_2 = frmOjb.getNombFrm0();
					nombFrm1_2 = frmOjb.getNombFrm1();
					nombFrm2_2 = frmOjb.getNombFrm2();
					nombFrm3_2 = frmOjb.getNombFrm3();
					nombFrm4_2 = frmOjb.getNombFrm4();
					nombFrm5_2 = frmOjb.getNombFrm5();
					nombFrm6_2 = frmOjb.getNombFrm6();
					nombFrm7_2 = frmOjb.getNombFrm7();
				}
				if (i == 2) {
					frm3 = true;
					frmOjb = procesosFormulario.get(i);
					procesoFormulario3.add(frmOjb);
					nombreFrm_3 = frmOjb.getNombreFrm();
					frm0_3 = frmOjb.isFrm0();
					frm1_3 = frmOjb.isFrm1();
					frm2_3 = frmOjb.isFrm2();
					frm3_3 = frmOjb.isFrm3();
					frm4_3 = frmOjb.isFrm4();
					frm5_3 = frmOjb.isFrm5();
					frm6_3 = frmOjb.isFrm6();
					frm7_3 = frmOjb.isFrm7();
					nombFrm0_3 = frmOjb.getNombFrm0();
					nombFrm1_3 = frmOjb.getNombFrm1();
					nombFrm2_3 = frmOjb.getNombFrm2();
					nombFrm3_3 = frmOjb.getNombFrm3();
					nombFrm4_3 = frmOjb.getNombFrm4();
					nombFrm5_3 = frmOjb.getNombFrm5();
					nombFrm6_3 = frmOjb.getNombFrm6();
					nombFrm7_3 = frmOjb.getNombFrm7();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método adicional " + " " + e.getMessage());
		}
	}

	public void modificarE(CheckListProcesoFormularioEt formulario) {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			formulario.setEstado(EstadoEnum.INA);
			iCheckListProcesoFormularioDao.guardarCheckListProcesoFormulario(formulario, usuario);
			cargarFormulario(formulario.getCheckListProceso());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método modificarE " + " " + e.getMessage());
		}
	}

	public void anadirEncabezado() {
		// String mensaje = "";
		try {
			// mensaje = validacionPro01Frm01(1L);
			// if (!mensaje.equals("")) {
			// showInfo(mensaje, FacesMessage.SEVERITY_INFO);
			// return;
			// }
			UsuarioEt usuario = appMain.getUsuario();
			CheckListInformeCabeceraEt checkListInfCab = new CheckListInformeCabeceraEt();
			checkListInfCab.setCheckList(checkListSeleccionado);
			checkListInfCab.setOrden(iCheckListInformeCabeceraDao.getOrdenCab(checkListSeleccionado.getIdCheckList()));
			checkListSeleccionado.getCheckListInformeCabecera().add(checkListInfCab);
			iCheckListInformeCabeceraDao.guardarCheckListInformeCabecera(checkListInfCab, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método anadirEncabezado " + " " + e.getMessage());
		}
	}

	public void eliminarCab(CheckListInformeCabeceraEt cabecera) {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			cabecera.setEstado(EstadoEnum.INA);
			checkListSeleccionado.getCheckListInformeCabecera().remove(cabecera);
			iCheckListInformeCabeceraDao.guardarCheckListInformeCabecera(cabecera, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método modificarE " + " " + e.getMessage());
		}
	}

	public void anadirPieFirma() {
		String mensaje = "";
		try {
			mensaje = validarPieFirma();
			if (!mensaje.equals("")) {
				showInfo(mensaje, FacesMessage.SEVERITY_INFO);
				return;
			}
			UsuarioEt usuario = appMain.getUsuario();
			CheckListPieFirmaEt checkListPieFirma = new CheckListPieFirmaEt();
			checkListPieFirma.setEjemplo("Nombre de responsable");
			checkListPieFirma.setCheckList(checkListSeleccionado);
			checkListPieFirma.setOrden(iCheckListPieFirmaDao.getOrdenPie(checkListSeleccionado.getIdCheckList()));
			checkListSeleccionado.getCheckListPieFirma().add(checkListPieFirma);
			iCheckListPieFirmaDao.guardarCheckListPieFirma(checkListPieFirma, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método anadirPieFirma " + " " + e.getMessage());
		}
	}

	public String validarPieFirma() {
		String mensaje = "";
		try {
			if (checkListSeleccionado.getCheckListPieFirma().size() == 3) {
				mensaje = "Máximo de firmantes que puede ingresar son 3";
				return mensaje;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método validarPieFirma " + " " + e.getMessage());
		}
		return mensaje;
	}

	public void eliminarPie(CheckListPieFirmaEt checkListPieFirma) {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			checkListPieFirma.setEstado(EstadoEnum.INA);
			checkListSeleccionado.getCheckListPieFirma().remove(checkListPieFirma);
			iCheckListPieFirmaDao.guardarCheckListPieFirma(checkListPieFirma, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método modificarE " + " " + e.getMessage());
		}
	}

	public void guardarKPI() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			if (checkListKpiSeleccionado.getSeccion() != null) {
				checkListKpiSeleccionado.setSeccionReemplazo(reemplazarEtiqueta(checkListKpiSeleccionado.getSeccion()));
			}
			if (checkListKpiSeleccionado.getSeccion0() != null) {
				checkListKpiSeleccionado
						.setSeccion0Reemplazo(reemplazarEtiqueta(checkListKpiSeleccionado.getSeccion0()));
			}
			iCheckListKpiDao.guardarCheckListKpi(checkListKpiSeleccionado, usuario);
			CheckListProcesoEt proceso = checkListKpiSeleccionado.getCheckListProceso();
			List<CheckListKpiEt> kpis = iCheckListKpiDao.getCheckListByVisualizar(proceso);
			if (kpis == null || kpis.isEmpty()) {
				proceso.setVisualizarReporte(false);
			} else {
				proceso.setVisualizarReporte(true);
			}
			iCheckListProcesoDao.guardarCheckListProceso(proceso, usuario);
			RequestContext.getCurrentInstance().execute("PF('dialog_002_4').hide();");
			checkListKpiSeleccionado = null;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarKPI " + " " + e.getMessage());
		}

	}

	public void imprimir() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			guardarInformeDinamico();
			checkListImprimir = iCheckListDao.getCheckList(checkListSeleccionado.getIdCheckList());
			checkListImprimir.setTituloReemplazo(reemplazarEtiqueta(checkListSeleccionado.getTitulo()));
			checkListImprimir.setNombreReemplazo(reemplazarEtiqueta(checkListSeleccionado.getNombre()));
			reemplazarEtiquetaCabecera(checkListImprimir);
			checkListImprimir.setParametroLogo(parEmpresaSelecciona);
			iCheckListDao.guardarCheckList(checkListImprimir, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método imprimir" + " " + e.getMessage());
		}
	}

	public String reemplazarEtiqueta(String original) {
		String reemplazo = "";
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		try {
			reemplazo = original;
			if (reemplazo.contains("{") || reemplazo.contains("}")) {

				if (reemplazo.contains("{nivel_evaluacion}")) {
					reemplazo = reemplazo.replace("{nivel_evaluacion}", "Riesgo");
				}
				if (reemplazo.contains("{evaluacion}")) {
					reemplazo = reemplazo.replace("{evaluacion}", "Control Interno");
				}
				if (reemplazo.contains("{tipo_check_list}")) {
					reemplazo = reemplazo.replace("{tipo_check_list}", "Visita Control Interno");
				}
				if (reemplazo.contains("{fecha_auditoria}")) {
					reemplazo = reemplazo.replace("{fecha_auditoria}", format.format(new Date()));
				}
				if (reemplazo.contains("{nombre_estacion}")) {
					reemplazo = reemplazo.replace("{nombre_estacion}", "12 DE NOVIEMBRE");
				}
				if (reemplazo.contains("{gerente}")) {
					reemplazo = reemplazo.replace("{gerente}", "María del Rocío");
				}
				if (reemplazo.contains("{verificador}")) {
					reemplazo = reemplazo.replace("{verificador}", "Juan Piguave");
				}
				if (reemplazo.contains("{soporte_operativo}")) {
					reemplazo = reemplazo.replace("{soporte_operativo}", "Juan Piguave");
				}
				if (reemplazo.contains("{soporte_administrativo}")) {
					reemplazo = reemplazo.replace("{soporte_administrativo}", "Juan Piguave");
				}
				if (reemplazo.contains("{supervisor_efectivo}")) {
					reemplazo = reemplazo.replace("{supervisor_efectivo}", "Juan Piguave");
				}
				if (reemplazo.contains("{jefe_operacion_tienda}")) {
					reemplazo = reemplazo.replace("{jefe_operacion_tienda}", "Juan Piguave");
				}
				if (reemplazo.contains("{jefe_operacion_pista}")) {
					reemplazo = reemplazo.replace("{jefe_operacion_pista}", "Juan Piguave");
				}
				if (reemplazo.contains("{criterio_evaluacion}")) {
					reemplazo = reemplazo.replace("{criterio_evaluacion}", "Criterio Seleccionado");
				}
				if (reemplazo.contains("{comentario_auditor}")) {
					reemplazo = reemplazo.replace("{comentario_auditor}", "Comentario Auditor XXXXXX");
				}
				if (reemplazo.contains("{comentario_adminstrativo}")) {
					reemplazo = reemplazo.replace("{comentario_adminstrativo}", "Comentario Administrador XXXX");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método reemplazarEtiquetaTitulo" + " " + e.getMessage());
		}
		return reemplazo;
	}

	public void reemplazarEtiquetaCabecera(CheckListEt checkList) {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			for (CheckListInformeCabeceraEt cabecera : checkList.getCheckListInformeCabecera()) {
				cabecera.setTituloReemplazo(reemplazarEtiqueta(cabecera.getTitulo()));
				cabecera.setContenidoReemplazo(reemplazarEtiqueta(cabecera.getContenido()));
				iCheckListInformeCabeceraDao.guardarCheckListInformeCabecera(cabecera, usuario);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método reemplazarEtiqueta" + " " + e.getMessage());
		}

	}

	public void guardarInformeDinamico() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			checkListSeleccionado.setParametroLogo(parEmpresaSelecciona);
			iCheckListDao.guardarCheckList(checkListSeleccionado, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarInformeDinamico " + " " + e.getMessage());
		}
	}

	public List<CheckListProcesoEt> getProcesosA() {
		List<CheckListProcesoEt> procesos = new ArrayList<CheckListProcesoEt>();
		try {
			if (checkListSeleccionado != null) {
				procesos = checkListSeleccionado.getCheckListProceso();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getProcesos " + " " + e.getMessage());
		}
		return procesos;
	}

	public void columna() {
		try {
			checkListFrmSeleccionado = new CheckListProcesoFormularioEt();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método adicional " + " " + e.getMessage());
		}
	}

	public void guardarSeleccionI() {
		try {
			System.out.println("Informe");
			UsuarioEt usuario = appMain.getUsuario();
			iCheckListProcesoFormularioDao.guardarCheckListProcesoFormulario(checkListFrmSeleccionadoM, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarSeleccionI " + " " + e.getMessage());
		}
	}

	public void modificarF(CheckListProcesoFormularioEt formulario) {
		try {
			checkListFrmSeleccionadoM = formulario;
			checkListProcesoSeleccionado = formulario.getCheckListProceso();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método modificarF " + " " + e.getMessage());
		}
	}

	public List<ParametrosGeneralesEt> getParEmpresaList() {
		ParametrosGeneralesEt objPadre;
		List<ParametrosGeneralesEt> parEmpresas = new ArrayList<ParametrosGeneralesEt>();
		try {
			objPadre = iParametrolGeneralDao.getObjPadre(ParametrosGeneralesEnum.EMPRESA.getDescripcion());
			parEmpresas = iParametrolGeneralDao.getListaHIjos(objPadre);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getParEmpresaList " + " " + e.getMessage());
		}
		return parEmpresas;
	}

	public List<TipoCargoEt> getTipoCargoList() {
		List<TipoCargoEt> tipoCargos = new ArrayList<>();
		try {
			tipoCargos = iTipoCargoDao.getTipoCargoList(null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTipoCargoList " + " " + e.getMessage());
		}
		return tipoCargos;
	}

	public void cargarEtiqueta(Long orden) {
		ParametrosGeneralesEt par;
		try {
			if (orden == 1L) {
				par = iParametrolGeneralDao.getParametrosGeneralById(171L);
			} else {
				par = iParametrolGeneralDao.getParametrosGeneralById(172L);
			}
			checkListEtiquetas = iCheckListEtiquetaDao.getCheckListEtiquetaList(par);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método cargarEtiqueta " + " " + e.getMessage());
		}
	}

	public void kpiSeleccionadoModificar(CheckListKpiEt kpi) {
		if (kpi.getSeccion() == null || kpi.getSeccion().equals("")) {
			kpi.setSeccion(kpi.getProcesoDetalle().getNombreProcesoDetalle());
		}
		checkListKpiSeleccionado = kpi;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public List<TipoCargoEt> getTipoCargos() {
		return tipoCargos;
	}

	public void setTipoCargos(List<TipoCargoEt> tipoCargos) {
		this.tipoCargos = tipoCargos;
	}

	public TipoChecKListEt getTipoChecListSeleccionado() {
		return tipoChecListSeleccionado;
	}

	public void setTipoChecListSeleccionado(TipoChecKListEt tipoChecListSeleccionado) {
		this.tipoChecListSeleccionado = tipoChecListSeleccionado;
	}

	public List<CheckListProcesoEt> getCheckListDetalles() {
		return checkListDetalles;
	}

	public void setCheckListDetalles(List<CheckListProcesoEt> checkListDetalles) {
		this.checkListDetalles = checkListDetalles;
	}

	public CheckListEt getCheckListSeleccionado() {
		return checkListSeleccionado;
	}

	public void setCheckListSeleccionado(CheckListEt checkListSeleccionado) {
		this.checkListSeleccionado = checkListSeleccionado;
	}

	public ProcesoEt getProcesoSeleccionado() {
		return procesoSeleccionado;
	}

	public void setProcesoSeleccionado(ProcesoEt procesoSeleccionado) {
		this.procesoSeleccionado = procesoSeleccionado;
	}

	public List<TipoEstacionEt> getTipoEstacion() {
		return tipoEstacion;
	}

	public void setTipoEstacion(List<TipoEstacionEt> tipoEstacion) {
		this.tipoEstacion = tipoEstacion;
	}

	public EvaluacionEt getEvaluacionSeleccionado() {
		return evaluacionSeleccionado;
	}

	public void setEvaluacionSeleccionado(EvaluacionEt evaluacionSeleccionado) {
		this.evaluacionSeleccionado = evaluacionSeleccionado;
	}

	public void setDimensionDetalles(DualListModel<ProcesoDetalleEt> procesoDetalles) {
		this.procesoDetalles = procesoDetalles;
	}

	public KPICriticoEt getkPICriticoSeleccionado() {
		return kPICriticoSeleccionado;
	}

	public void setkPICriticoSeleccionado(KPICriticoEt kPICriticoSeleccionado) {
		this.kPICriticoSeleccionado = kPICriticoSeleccionado;
	}

	public Long getTotalPuntaje() {
		return totalPuntaje;
	}

	public void setTotalPuntaje(Long totalPuntaje) {
		this.totalPuntaje = totalPuntaje;
	}

	public String getCodigoCheckList() {
		return codigoCheckList;
	}

	public void setCodigoCheckList(String codigoCheckList) {
		this.codigoCheckList = codigoCheckList;
	}

	public NivelEvaluacionEt getNivelEvaluacionSeleccionado() {
		return nivelEvaluacionSeleccionado;
	}

	public void setNivelEvaluacionSeleccionado(NivelEvaluacionEt nivelEvaluacionSeleccionado) {
		this.nivelEvaluacionSeleccionado = nivelEvaluacionSeleccionado;
	}

	public boolean isFrm1() {
		return frm1;
	}

	public void setFrm1(boolean frm1) {
		this.frm1 = frm1;
	}

	public boolean isFrm2() {
		return frm2;
	}

	public void setFrm2(boolean frm2) {
		this.frm2 = frm2;
	}

	public boolean isFrm3() {
		return frm3;
	}

	public void setFrm3(boolean frm3) {
		this.frm3 = frm3;
	}

	public boolean isFrm0_1() {
		return frm0_1;
	}

	public void setFrm0_1(boolean frm0_1) {
		this.frm0_1 = frm0_1;
	}

	public boolean isFrm1_1() {
		return frm1_1;
	}

	public void setFrm1_1(boolean frm1_1) {
		this.frm1_1 = frm1_1;
	}

	public boolean isFrm2_1() {
		return frm2_1;
	}

	public void setFrm2_1(boolean frm2_1) {
		this.frm2_1 = frm2_1;
	}

	public boolean isFrm3_1() {
		return frm3_1;
	}

	public void setFrm3_1(boolean frm3_1) {
		this.frm3_1 = frm3_1;
	}

	public boolean isFrm4_1() {
		return frm4_1;
	}

	public void setFrm4_1(boolean frm4_1) {
		this.frm4_1 = frm4_1;
	}

	public boolean isFrm5_1() {
		return frm5_1;
	}

	public void setFrm5_1(boolean frm5_1) {
		this.frm5_1 = frm5_1;
	}

	public boolean isFrm6_1() {
		return frm6_1;
	}

	public void setFrm6_1(boolean frm6_1) {
		this.frm6_1 = frm6_1;
	}

	public boolean isFrm7_1() {
		return frm7_1;
	}

	public void setFrm7_1(boolean frm7_1) {
		this.frm7_1 = frm7_1;
	}

	public boolean isFrm0_2() {
		return frm0_2;
	}

	public void setFrm0_2(boolean frm0_2) {
		this.frm0_2 = frm0_2;
	}

	public boolean isFrm1_2() {
		return frm1_2;
	}

	public void setFrm1_2(boolean frm1_2) {
		this.frm1_2 = frm1_2;
	}

	public boolean isFrm2_2() {
		return frm2_2;
	}

	public void setFrm2_2(boolean frm2_2) {
		this.frm2_2 = frm2_2;
	}

	public boolean isFrm3_2() {
		return frm3_2;
	}

	public void setFrm3_2(boolean frm3_2) {
		this.frm3_2 = frm3_2;
	}

	public boolean isFrm4_2() {
		return frm4_2;
	}

	public void setFrm4_2(boolean frm4_2) {
		this.frm4_2 = frm4_2;
	}

	public boolean isFrm5_2() {
		return frm5_2;
	}

	public void setFrm5_2(boolean frm5_2) {
		this.frm5_2 = frm5_2;
	}

	public boolean isFrm6_2() {
		return frm6_2;
	}

	public void setFrm6_2(boolean frm6_2) {
		this.frm6_2 = frm6_2;
	}

	public boolean isFrm7_2() {
		return frm7_2;
	}

	public void setFrm7_2(boolean frm7_2) {
		this.frm7_2 = frm7_2;
	}

	public boolean isFrm0_3() {
		return frm0_3;
	}

	public void setFrm0_3(boolean frm0_3) {
		this.frm0_3 = frm0_3;
	}

	public boolean isFrm1_3() {
		return frm1_3;
	}

	public void setFrm1_3(boolean frm1_3) {
		this.frm1_3 = frm1_3;
	}

	public boolean isFrm2_3() {
		return frm2_3;
	}

	public void setFrm2_3(boolean frm2_3) {
		this.frm2_3 = frm2_3;
	}

	public boolean isFrm3_3() {
		return frm3_3;
	}

	public void setFrm3_3(boolean frm3_3) {
		this.frm3_3 = frm3_3;
	}

	public boolean isFrm4_3() {
		return frm4_3;
	}

	public void setFrm4_3(boolean frm4_3) {
		this.frm4_3 = frm4_3;
	}

	public boolean isFrm5_3() {
		return frm5_3;
	}

	public void setFrm5_3(boolean frm5_3) {
		this.frm5_3 = frm5_3;
	}

	public boolean isFrm6_3() {
		return frm6_3;
	}

	public void setFrm6_3(boolean frm6_3) {
		this.frm6_3 = frm6_3;
	}

	public boolean isFrm7_3() {
		return frm7_3;
	}

	public void setFrm7_3(boolean frm7_3) {
		this.frm7_3 = frm7_3;
	}

	public String getNombFrm0_1() {
		return nombFrm0_1;
	}

	public void setNombFrm0_1(String nombFrm0_1) {
		this.nombFrm0_1 = nombFrm0_1;
	}

	public String getNombFrm1_1() {
		return nombFrm1_1;
	}

	public void setNombFrm1_1(String nombFrm1_1) {
		this.nombFrm1_1 = nombFrm1_1;
	}

	public String getNombFrm2_1() {
		return nombFrm2_1;
	}

	public void setNombFrm2_1(String nombFrm2_1) {
		this.nombFrm2_1 = nombFrm2_1;
	}

	public String getNombFrm3_1() {
		return nombFrm3_1;
	}

	public void setNombFrm3_1(String nombFrm3_1) {
		this.nombFrm3_1 = nombFrm3_1;
	}

	public String getNombFrm4_1() {
		return nombFrm4_1;
	}

	public void setNombFrm4_1(String nombFrm4_1) {
		this.nombFrm4_1 = nombFrm4_1;
	}

	public String getNombFrm5_1() {
		return nombFrm5_1;
	}

	public void setNombFrm5_1(String nombFrm5_1) {
		this.nombFrm5_1 = nombFrm5_1;
	}

	public String getNombFrm6_1() {
		return nombFrm6_1;
	}

	public void setNombFrm6_1(String nombFrm6_1) {
		this.nombFrm6_1 = nombFrm6_1;
	}

	public String getNombFrm7_1() {
		return nombFrm7_1;
	}

	public void setNombFrm7_1(String nombFrm7_1) {
		this.nombFrm7_1 = nombFrm7_1;
	}

	public String getNombFrm0_2() {
		return nombFrm0_2;
	}

	public void setNombFrm0_2(String nombFrm0_2) {
		this.nombFrm0_2 = nombFrm0_2;
	}

	public String getNombFrm1_2() {
		return nombFrm1_2;
	}

	public void setNombFrm1_2(String nombFrm1_2) {
		this.nombFrm1_2 = nombFrm1_2;
	}

	public String getNombFrm2_2() {
		return nombFrm2_2;
	}

	public void setNombFrm2_2(String nombFrm2_2) {
		this.nombFrm2_2 = nombFrm2_2;
	}

	public String getNombFrm3_2() {
		return nombFrm3_2;
	}

	public void setNombFrm3_2(String nombFrm3_2) {
		this.nombFrm3_2 = nombFrm3_2;
	}

	public String getNombFrm4_2() {
		return nombFrm4_2;
	}

	public void setNombFrm4_2(String nombFrm4_2) {
		this.nombFrm4_2 = nombFrm4_2;
	}

	public String getNombFrm5_2() {
		return nombFrm5_2;
	}

	public void setNombFrm5_2(String nombFrm5_2) {
		this.nombFrm5_2 = nombFrm5_2;
	}

	public String getNombFrm6_2() {
		return nombFrm6_2;
	}

	public void setNombFrm6_2(String nombFrm6_2) {
		this.nombFrm6_2 = nombFrm6_2;
	}

	public String getNombFrm7_2() {
		return nombFrm7_2;
	}

	public void setNombFrm7_2(String nombFrm7_2) {
		this.nombFrm7_2 = nombFrm7_2;
	}

	public String getNombFrm0_3() {
		return nombFrm0_3;
	}

	public void setNombFrm0_3(String nombFrm0_3) {
		this.nombFrm0_3 = nombFrm0_3;
	}

	public String getNombFrm1_3() {
		return nombFrm1_3;
	}

	public void setNombFrm1_3(String nombFrm1_3) {
		this.nombFrm1_3 = nombFrm1_3;
	}

	public String getNombFrm2_3() {
		return nombFrm2_3;
	}

	public void setNombFrm2_3(String nombFrm2_3) {
		this.nombFrm2_3 = nombFrm2_3;
	}

	public String getNombFrm3_3() {
		return nombFrm3_3;
	}

	public void setNombFrm3_3(String nombFrm3_3) {
		this.nombFrm3_3 = nombFrm3_3;
	}

	public String getNombFrm4_3() {
		return nombFrm4_3;
	}

	public void setNombFrm4_3(String nombFrm4_3) {
		this.nombFrm4_3 = nombFrm4_3;
	}

	public String getNombFrm5_3() {
		return nombFrm5_3;
	}

	public void setNombFrm5_3(String nombFrm5_3) {
		this.nombFrm5_3 = nombFrm5_3;
	}

	public String getNombFrm6_3() {
		return nombFrm6_3;
	}

	public void setNombFrm6_3(String nombFrm6_3) {
		this.nombFrm6_3 = nombFrm6_3;
	}

	public String getNombFrm7_3() {
		return nombFrm7_3;
	}

	public void setNombFrm7_3(String nombFrm7_3) {
		this.nombFrm7_3 = nombFrm7_3;
	}

	public String getNombreFrm_1() {
		return nombreFrm_1;
	}

	public void setNombreFrm_1(String nombreFrm_1) {
		this.nombreFrm_1 = nombreFrm_1;
	}

	public String getNombreFrm_2() {
		return nombreFrm_2;
	}

	public void setNombreFrm_2(String nombreFrm_2) {
		this.nombreFrm_2 = nombreFrm_2;
	}

	public String getNombreFrm_3() {
		return nombreFrm_3;
	}

	public void setNombreFrm_3(String nombreFrm_3) {
		this.nombreFrm_3 = nombreFrm_3;
	}

	public CheckListProcesoEt getCheckListProcesoSeleccionado() {
		return checkListProcesoSeleccionado;
	}

	public void setCheckListProcesoSeleccionado(CheckListProcesoEt checkListProcesoSeleccionado) {
		cargarFormulario(checkListProcesoSeleccionado);
		this.checkListProcesoSeleccionado = checkListProcesoSeleccionado;
	}

	public CheckListProcesoEt getCheckListProcesoSeleccionadoI() {
		return checkListProcesoSeleccionadoI;
	}

	public void setCheckListProcesoSeleccionadoI(CheckListProcesoEt checkListProcesoSeleccionadoI) {
		this.checkListProcesoSeleccionadoI = checkListProcesoSeleccionadoI;
	}

	public CheckListProcesoEt getCheckListProcesoSeleccionadoT() {
		return checkListProcesoSeleccionadoT;
	}

	public void setCheckListProcesoSeleccionadoT(CheckListProcesoEt checkListProcesoSeleccionadoT) {
		cargarFormulario(checkListProcesoSeleccionadoT);
		this.checkListProcesoSeleccionadoT = checkListProcesoSeleccionadoT;
	}

	public CheckListProcesoEt getCheckListDimensionSeleccionado() {
		return checkListDimensionSeleccionado;
	}

	public void setCheckListDimensionSeleccionado(CheckListProcesoEt checkListDimensionSeleccionado) {
		this.checkListDimensionSeleccionado = checkListDimensionSeleccionado;
	}

	public CriterioEvaluacionEt getCriterioEvaluacionSeleccionada() {
		return criterioEvaluacionSeleccionada;
	}

	public void setCriterioEvaluacionSeleccionada(CriterioEvaluacionEt criterioEvaluacionSeleccionada) {
		this.criterioEvaluacionSeleccionada = criterioEvaluacionSeleccionada;
	}

	public CheckListProcesoFormularioEt getCheckListFrmSeleccionado() {
		return checkListFrmSeleccionado;
	}

	public void setCheckListFrmSeleccionado(CheckListProcesoFormularioEt checkListFrmSeleccionado) {
		this.checkListFrmSeleccionado = checkListFrmSeleccionado;
	}

	public List<CheckListProcesoFormularioEt> getProcesoFormulario1() {
		return procesoFormulario1;
	}

	public void setProcesoFormulario1(List<CheckListProcesoFormularioEt> procesoFormulario1) {
		this.procesoFormulario1 = procesoFormulario1;
	}

	public List<CheckListProcesoFormularioEt> getProcesoFormulario2() {
		return procesoFormulario2;
	}

	public void setProcesoFormulario2(List<CheckListProcesoFormularioEt> procesoFormulario2) {
		this.procesoFormulario2 = procesoFormulario2;
	}

	public List<CheckListProcesoFormularioEt> getProcesoFormulario3() {
		return procesoFormulario3;
	}

	public void setProcesoFormulario3(List<CheckListProcesoFormularioEt> procesoFormulario3) {
		this.procesoFormulario3 = procesoFormulario3;
	}

	public CheckListProcesoFormularioEt getCheckListFrmSeleccionadoM() {
		return checkListFrmSeleccionadoM;
	}

	public void setCheckListFrmSeleccionadoM(CheckListProcesoFormularioEt checkListFrmSeleccionadoM) {
		this.checkListFrmSeleccionadoM = checkListFrmSeleccionadoM;
	}

	public CheckListEt getCheckListImprimir() {
		return checkListImprimir;
	}

	public void setCheckListImprimir(CheckListEt checkListImprimir) {
		this.checkListImprimir = checkListImprimir;
	}

	public List<CheckListEtiquetaEt> getCheckListEtiquetas() {
		return checkListEtiquetas;
	}

	public void setCheckListEtiquetas(List<CheckListEtiquetaEt> checkListEtiquetas) {
		this.checkListEtiquetas = checkListEtiquetas;
	}

	public ParametrosGeneralesEt getParEmpresaSelecciona() {
		return parEmpresaSelecciona;
	}

	public void setParEmpresaSelecciona(ParametrosGeneralesEt parEmpresaSelecciona) {
		this.parEmpresaSelecciona = parEmpresaSelecciona;
	}

	public CheckListKpiEt getCheckListKpiSeleccionado() {
		return checkListKpiSeleccionado;
	}

	public void setCheckListKpiSeleccionado(CheckListKpiEt checkListKpiSeleccionado) {
		this.checkListKpiSeleccionado = checkListKpiSeleccionado;
	}

	@Override
	protected void onDestroy() {
		iTipoCargoDao.remove();
		iCheckListDao.remove();
		iDimensionDao.remove();
		iKPICriticoDao.remove();
		iEvaluacionDao.remove();
		iCheckListKpiDao.remove();
		iTipoChecListDao.remove();
		iTipoEstacionDao.remove();
		iNivelEvaluacionDao.remove();
		iDimensionDetalleDao.remove();
		iCheckListProcesoDao.remove();
		iCheckListEtiquetaDao.remove();
		iParametrolGeneralDao.remove();
		iCriterioEvaluacionDao.remove();
		iCheckListPieFirmaDao.remove();
		iCheckListInformeCabeceraDao.remove();
		iCheckListProcesoFormularioDao.remove();
	}

}
