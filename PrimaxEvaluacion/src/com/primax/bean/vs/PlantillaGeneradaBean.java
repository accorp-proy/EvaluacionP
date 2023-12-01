package com.primax.bean.vs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.OptionalLong;

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
import com.primax.jpa.enums.EstadoCheckListEnum;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.AgenciaCheckListEt;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.CategoriaEstacionEt;
import com.primax.jpa.param.CriterioEvaluacionDetalleEt;
import com.primax.jpa.param.CriterioEvaluacionEt;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.param.NivelEvaluacionEt;
import com.primax.jpa.param.TipoCategoriaEstacionEt;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.param.TipoEstacionEt;
import com.primax.jpa.pla.CheckListCriterioDetalleEt;
import com.primax.jpa.pla.CheckListCriterioEt;
import com.primax.jpa.pla.CheckListEt;
import com.primax.jpa.pla.CheckListKpiEt;
import com.primax.jpa.pla.CheckListProcesoEt;
import com.primax.jpa.sec.RolEt;
import com.primax.jpa.sec.RolUsuarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IAgenciaCheckListDao;
import com.primax.srv.idao.IAgenciaDao;
import com.primax.srv.idao.ICheckListCriterioDao;
import com.primax.srv.idao.ICheckListDao;
import com.primax.srv.idao.ICheckListKpiDao;
import com.primax.srv.idao.ICheckListProcesoDao;
import com.primax.srv.idao.IEvaluacionDao;
import com.primax.srv.idao.INivelEvaluacionDao;
import com.primax.srv.idao.IRolEtDao;
import com.primax.srv.idao.ITipoChecKListDao;
import com.primax.srv.idao.ITipoEstacionDao;

@Named("PlantillaGeneradaBn")
@ViewScoped
public class PlantillaGeneradaBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IRolEtDao iRolEtDao;
	@EJB
	private IAgenciaDao iAgenciaDao;
	@EJB
	private ICheckListDao iCheckListDao;
	@EJB
	private IEvaluacionDao iEvaluacionDao;
	@EJB
	private ITipoEstacionDao iTipoEstacionDao;
	@EJB
	private ICheckListKpiDao iCheckListKpiDao;
	@EJB
	private ITipoChecKListDao iTipoChecListDao;
	@EJB
	private INivelEvaluacionDao iNivelEvaluacionDao;
	@EJB
	private IAgenciaCheckListDao iAgenciaCheckListDao;
	@EJB
	private ICheckListProcesoDao iCheckListProcesoDao;
	@EJB
	private ICheckListCriterioDao iCheckListCriterioDao;

	@Inject
	private AppMain appMain;

	private String condicion;

	private Long cantidadA = 0L;
	private Long cantidadC = 0L;
	private boolean userADM = false;
	private Date fDesde = new Date();
	private Date fHasta = new Date();
	private List<CheckListEt> checkList;
	private CheckListEt checkListSeleccionado;
	private EvaluacionEt evaluacionSeleccionado;
	private TipoEstacionEt tipoEstacionSeleccionada;
	private TipoChecKListEt tipoChecKListSeleccionado;
	private DualListModel<AgenciaEt> dualListAgencias;
	private NivelEvaluacionEt nivelEvaluacionSeleccionado;
	private EstadoCheckListEnum estadoCheckListSeleccionado;
	private CategoriaEstacionEt categoriaEstacionSeleccionada;

	@Override
	protected void init() {
		inicializarObj();
		validaUsuario();
		buscar();
	}

	public void validaUsuario() {
		List<RolUsuarioEt> rolUsuario = null;
		try {
			RolEt rol = iRolEtDao.getRolbyId(8L);
			UsuarioEt usuario = appMain.getUsuario();
			rolUsuario = usuario.getRolesUsario();
			if (containsRol(rolUsuario, rol)) {
				userADM = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}
	}

	public void inicializarObj() {
		evaluacionSeleccionado = null;
		tipoEstacionSeleccionada = null;
		tipoChecKListSeleccionado = null;
		nivelEvaluacionSeleccionado = null;
		categoriaEstacionSeleccionada = null;
	}

	public void buscar() {
		try {
			checkList = iCheckListDao.getCheckList(nivelEvaluacionSeleccionado, evaluacionSeleccionado,
					tipoChecKListSeleccionado, fDesde, fHasta, estadoCheckListSeleccionado);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}
	}

	public boolean containsRol(final List<RolUsuarioEt> list, final RolEt rol) {
		return list.stream().filter(o -> o.getRol().equals(rol)).findFirst().isPresent();
	}

	public void guardar() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			for (CheckListProcesoEt checkListProceso : checkListSeleccionado.getCheckListProceso()) {
				checkListProceso.setDescripcion(checkListProceso.getProceso().getNombreProceso());
				iCheckListProcesoDao.guardarCheckListProceso(checkListProceso, usuario);
				for (CheckListKpiEt checkListKpi : checkListProceso.getCheckListKpi()) {
					checkListKpi.setPuntaje(checkListKpi.getProcesoDetalle().getPuntaje());
					checkListKpi.setDescripcion(checkListKpi.getProcesoDetalle().getNombreProcesoDetalle());
					iCheckListKpiDao.guardarCheckListKpi(checkListKpi, usuario);
				}
			}
			String descripcion = checkListSeleccionado.getCodigo() + " - "
					+ checkListSeleccionado.getEvaluacion().getDescripcion() + " - "
					+ checkListSeleccionado.getTipoChecKList().getDescripcion();
			checkListSeleccionado.setDescripcion(descripcion);
			checkListSeleccionado.setEstadoCheckList(EstadoCheckListEnum.APROBADO);
			iCheckListDao.guardarCheckList(checkListSeleccionado, usuario);
			showInfo("Información Grabada con Éxito ", FacesMessage.SEVERITY_INFO);
			buscar();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}
	}

	public void guardarEliminar() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			checkListSeleccionado.setEstado(EstadoEnum.ELI);
			iCheckListDao.guardarCheckList(checkListSeleccionado, usuario);
			showInfo("Información Grabada con Éxito ", FacesMessage.SEVERITY_INFO);
			buscar();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}
	}

	public void guardarInactivar() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			checkListSeleccionado.setEstado(EstadoEnum.INA);
			iCheckListDao.guardarCheckList(checkListSeleccionado, usuario);
			showInfo("Información Grabada con Éxito ", FacesMessage.SEVERITY_INFO);
			buscar();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Mótodo guardar " + " " + e.getMessage());
		}
	}

	public void guardarAsignacion() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			for (AgenciaEt agencia : dualListAgencias.getTarget()) {
				List<AgenciaCheckListEt> agenciasCheckList = agencia.getAgenciaCheckList();
				if (agenciasCheckList.isEmpty()) {
					if (!containsCheckLIst(agenciasCheckList, checkListSeleccionado)) {
						AgenciaCheckListEt agenciaCheckListN = new AgenciaCheckListEt();
						agenciaCheckListN.setAgencia(agencia);
						agenciaCheckListN.setCheckList(checkListSeleccionado);
						iAgenciaCheckListDao.guardaAgenciaCheckList(agenciaCheckListN, usuario);
					}
				} else {
					if (!containsCheckLIst(agenciasCheckList, checkListSeleccionado)) {
						AgenciaCheckListEt agenciaCheckListN = new AgenciaCheckListEt();
						agenciaCheckListN.setAgencia(agencia);
						agenciaCheckListN.setCheckList(checkListSeleccionado);
						iAgenciaCheckListDao.guardaAgenciaCheckList(agenciaCheckListN, usuario);
					}
				}
			}
			showInfo("Información Grabada con Éxito ", FacesMessage.SEVERITY_INFO);
			RequestContext.getCurrentInstance().execute("PF('dialog_03_1').hide();");
			buscar();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarAsignacion " + " " + e.getMessage());
		}
	}

	public boolean containsCheckLIst(final List<AgenciaCheckListEt> list, final CheckListEt checkList) {
		return list.stream().filter(o -> o.getCheckList().equals(checkList)).findFirst().isPresent();
	}

	public void modificar(CheckListEt checkList) {
		checkListSeleccionado = checkList;
	}

	public void modificarAsignacion(CheckListEt checkList) {
		checkListSeleccionado = checkList;
		mostrarTotal();
	}

	public void validadorCriterio() {
		List<String> mensajes = new ArrayList<>();
		try {
			CheckListEt checkList = checkListSeleccionado;
			for (CheckListProcesoEt checkListProceso : checkList.getCheckListProceso()) {
				for (CheckListKpiEt checkListKpi : checkListProceso.getCheckListKpi()) {
					String mensaje = valdiacionC(checkListKpi.getPuntaje(), checkListKpi,
							checkListKpi.getCriterioEvaluacion());
					if (!mensaje.equals("")) {
						mensajes.add(mensaje);
					}
				}
			}
			if (mensajes.isEmpty()) {
				guardarCriterio(checkList);
				checkList.setValidarCriterio(true);
				iCheckListDao.guardarCheckList(checkList, appMain.getUsuario());
				buscar();
				showInfo("Validación de criterios de evaluación fue realizada con Éxito ", FacesMessage.SEVERITY_INFO);
			} else {
				for (String mensajeE : mensajes) {
					showInfo(mensajeE, FacesMessage.SEVERITY_ERROR);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método validadorCriterio " + " " + e.getMessage());
		}
	}

	public String valdiacionC(Long puntajeM, CheckListKpiEt checkListKpi, CriterioEvaluacionEt criterioEvaluacion) {
		String mensaje = "";
		try {
			OptionalLong optionalLong = criterioEvaluacion.getCriterioEvaluacionDetalle().stream()
					.mapToLong(p -> p.getPuntaje()).max();
			Long puntaje = optionalLong.getAsLong();
			if (puntaje < puntajeM || puntaje > puntajeM) {
				mensaje = "Criterio no cumple con el puntaje máximo que requeire el KPI" + " "
						+ checkListKpi.getDescripcion();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método valdiacionC " + " " + e.getMessage());
		}
		return mensaje;
	}

	public void guardarCriterio(CheckListEt checkList) {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			for (CheckListProcesoEt checkListProceso : checkList.getCheckListProceso()) {
				for (CheckListKpiEt checkListKpi : checkListProceso.getCheckListKpi()) {
					CheckListCriterioEt checkListC = new CheckListCriterioEt();
					checkListC.setNombre(checkListKpi.getCriterioEvaluacion().getNombre());
					checkListC.setCheckListCriterioDetalle(new ArrayList<>());
					for (CriterioEvaluacionDetalleEt checkListD : checkListKpi.getCriterioEvaluacion()
							.getCriterioEvaluacionDetalle()) {
						CheckListCriterioDetalleEt checkListCriterioD = new CheckListCriterioDetalleEt();
						checkListCriterioD.setOrden(checkListD.getOrden());
						checkListCriterioD.setColor(checkListD.getColor());
						checkListCriterioD.setCheckListCriterio(checkListC);
						checkListCriterioD.setNombre(checkListD.getNombre());
						checkListCriterioD.setPuntaje(checkListD.getPuntaje());
						checkListC.getCheckListCriterioDetalle().add(checkListCriterioD);
					}
					iCheckListCriterioDao.guardarCheckListCriterio(checkListC, usuario);
					iCheckListCriterioDao.clear();
					CheckListCriterioEt checkListCriterioC = iCheckListCriterioDao
							.getCheckListCriterioById(checkListC.getIdCheckListCriterio());
					guardarCheckListKpi(usuario, checkListKpi, checkListCriterioC);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método valdiacionC " + " " + e.getMessage());
		}
	}

	public void guardarCheckListKpi(UsuarioEt usuario, CheckListKpiEt checkListKpi, CheckListCriterioEt checkListC) {
		try {
			CheckListKpiEt checkListKpiC = iCheckListKpiDao.getCheckListKpi(checkListKpi.getIdCheckListKpi());
			checkListKpiC.setCheckListCriterio(new CheckListCriterioEt());
			checkListC.getCheckListCriterioDetalle().size();
			checkListKpiC.setCheckListCriterio(checkListC);
			iCheckListKpiDao.guardarCheckListKpi(checkListKpiC, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarCheckListKpi " + " " + e.getMessage());
		}
	}

	public void mostrarTotal() {
		cantidadC = (long) dualListAgencias.getSource().size();
		cantidadA = (long) dualListAgencias.getTarget().size();
	}

	public SelectItem[] getEstadosActIna() {
		SelectItem[] items = new SelectItem[2];
		items[0] = new SelectItem(EstadoEnum.ACT, EstadoEnum.ACT.getDescripcion());
		items[1] = new SelectItem(EstadoEnum.INA, EstadoEnum.INA.getDescripcion());
		return items;
	}

	public List<TipoEstacionEt> getTipoEstacionList() {
		List<TipoEstacionEt> tipoEstaciones = new ArrayList<TipoEstacionEt>();
		try {
			tipoEstaciones = iTipoEstacionDao.getTipoEstacionList(null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTipoEstacionList " + " " + e.getMessage());
		}
		return tipoEstaciones;
	}

	public List<CategoriaEstacionEt> getCategoriaEstacionList() {
		List<CategoriaEstacionEt> categoriaEstaciones = new ArrayList<CategoriaEstacionEt>();
		try {
			if (tipoEstacionSeleccionada != null) {
				for (TipoCategoriaEstacionEt tipoCategoriaEstacion : tipoEstacionSeleccionada
						.getTipoCategoriaEstacion()) {
					categoriaEstaciones.add(tipoCategoriaEstacion.getCategoriaEstacion());
				}
				return categoriaEstaciones;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getCategoriaEstacionList " + " " + e.getMessage());
		}
		return categoriaEstaciones;
	}

	public List<NivelEvaluacionEt> getNivelEvaluacionList() {
		List<NivelEvaluacionEt> nivelEvaluaciones = new ArrayList<NivelEvaluacionEt>();
		try {
			nivelEvaluaciones = iNivelEvaluacionDao.getNivelEvaluacionList(null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Méodo getNivelEvaluacionList " + " " + e.getMessage());
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
			if (evaluacionSeleccionado != null) {
				tipoChecList = iTipoChecListDao.getTipoCheckListByEvaluacion(evaluacionSeleccionado);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTiposChecKList " + " " + e.getMessage());
		}
		return tipoChecList;
	}

	public DualListModel<AgenciaEt> getDualListAgencias() {
		List<AgenciaEt> themesTarget = null;
		dualListAgencias = new DualListModel<>();
		List<AgenciaEt> agencias = new ArrayList<AgenciaEt>();
		List<AgenciaEt> agenciasA = new ArrayList<AgenciaEt>();
		try {
			agencias = iAgenciaDao.getAgenciaByTipoList(tipoEstacionSeleccionada, categoriaEstacionSeleccionada);
			agenciasA = iAgenciaDao.getAgenciaByCheckList(checkListSeleccionado);
			if (agenciasA.isEmpty()) {
				themesTarget = new ArrayList<AgenciaEt>();
			} else {
				agencias = new ArrayList<>();
				agencias = iAgenciaDao.getAgenciaByTipoAList(tipoEstacionSeleccionada, categoriaEstacionSeleccionada,
						agenciasA);
				themesTarget = agenciasA;
				cantidadA = (long) agenciasA.size();
			}
			List<AgenciaEt> themesSource = agencias;
			dualListAgencias = new DualListModel<AgenciaEt>(themesSource, themesTarget);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getDualListAgencias " + " " + e.getMessage());
		}
		return dualListAgencias;
	}

	public SelectItem[] getEstadoCheckList() {
		SelectItem[] items = new SelectItem[4];
		items[0] = new SelectItem(EstadoCheckListEnum.ESTADO_CHECK, EstadoCheckListEnum.ESTADO_CHECK.getDescripcion());
		items[1] = new SelectItem(EstadoCheckListEnum.PLANTILLA, EstadoCheckListEnum.PLANTILLA.getDescripcion());
		items[2] = new SelectItem(EstadoCheckListEnum.GENERADO, EstadoCheckListEnum.GENERADO.getDescripcion());
		items[3] = new SelectItem(EstadoCheckListEnum.APROBADO, EstadoCheckListEnum.APROBADO.getDescripcion());
		return items;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public List<CheckListEt> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<CheckListEt> checkList) {
		this.checkList = checkList;
	}

	public EvaluacionEt getEvaluacionSeleccionado() {
		return evaluacionSeleccionado;
	}

	public void setEvaluacionSeleccionado(EvaluacionEt evaluacionSeleccionado) {
		this.evaluacionSeleccionado = evaluacionSeleccionado;
	}

	public TipoChecKListEt getTipoChecKListSeleccionado() {
		return tipoChecKListSeleccionado;
	}

	public void setTipoChecKListSeleccionado(TipoChecKListEt tipoChecKListSeleccionado) {
		this.tipoChecKListSeleccionado = tipoChecKListSeleccionado;
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

	public CheckListEt getCheckListSeleccionado() {
		return checkListSeleccionado;
	}

	public void setCheckListSeleccionado(CheckListEt checkListSeleccionado) {
		this.checkListSeleccionado = checkListSeleccionado;
	}

	public TipoEstacionEt getTipoEstacionSeleccionada() {
		return tipoEstacionSeleccionada;
	}

	public void setTipoEstacionSeleccionada(TipoEstacionEt tipoEstacionSeleccionada) {
		this.tipoEstacionSeleccionada = tipoEstacionSeleccionada;
	}

	public CategoriaEstacionEt getCategoriaEstacionSeleccionada() {
		return categoriaEstacionSeleccionada;
	}

	public void setCategoriaEstacionSeleccionada(CategoriaEstacionEt categoriaEstacionSeleccionada) {
		this.categoriaEstacionSeleccionada = categoriaEstacionSeleccionada;
	}

	public void setDualListAgencias(DualListModel<AgenciaEt> dualListAgencias) {
		this.dualListAgencias = dualListAgencias;
	}

	public Long getCantidadA() {
		return cantidadA;
	}

	public void setCantidadA(Long cantidadA) {
		this.cantidadA = cantidadA;
	}

	public Long getCantidadC() {
		return cantidadC;
	}

	public void setCantidadC(Long cantidadC) {
		this.cantidadC = cantidadC;
	}

	public EstadoCheckListEnum getEstadoCheckListSeleccionado() {
		return estadoCheckListSeleccionado;
	}

	public void setEstadoCheckListSeleccionado(EstadoCheckListEnum estadoCheckListSeleccionado) {
		this.estadoCheckListSeleccionado = estadoCheckListSeleccionado;
	}

	public boolean isUserADM() {
		return userADM;
	}

	public void setUserADM(boolean userADM) {
		this.userADM = userADM;
	}

	public NivelEvaluacionEt getNivelEvaluacionSeleccionado() {
		return nivelEvaluacionSeleccionado;
	}

	public void setNivelEvaluacionSeleccionado(NivelEvaluacionEt nivelEvaluacionSeleccionado) {
		this.nivelEvaluacionSeleccionado = nivelEvaluacionSeleccionado;
	}

	@Override
	protected void onDestroy() {
		iRolEtDao.remove();
		iAgenciaDao.remove();
		iCheckListDao.remove();
		iEvaluacionDao.remove();
		iTipoChecListDao.remove();
		iTipoEstacionDao.remove();
		iCheckListKpiDao.remove();
		iNivelEvaluacionDao.remove();
		iCheckListProcesoDao.remove();
		iAgenciaCheckListDao.remove();
		iCheckListCriterioDao.remove();
	}

}
