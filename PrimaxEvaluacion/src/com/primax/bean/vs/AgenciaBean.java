package com.primax.bean.vs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.bean.vs.base.BusquedaCheckBean;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.gen.PersonaEt;
import com.primax.jpa.gen.UbicacionEt;
import com.primax.jpa.param.AgenciaCategoriaEt;
import com.primax.jpa.param.AgenciaCheckListEt;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.CantonEt;
import com.primax.jpa.param.CategoriaEstacionEt;
import com.primax.jpa.param.FrecuenciaVisitaEt;
import com.primax.jpa.param.ProvinciaEt;
import com.primax.jpa.param.RegionEt;
import com.primax.jpa.param.ResponsableEt;
import com.primax.jpa.param.TipoCargoEt;
import com.primax.jpa.param.TipoCategoriaEstacionEt;
import com.primax.jpa.param.TipoEstacionEt;
import com.primax.jpa.pla.CheckListEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IAgenciaCategoriaDao;
import com.primax.srv.idao.IAgenciaCheckListDao;
import com.primax.srv.idao.IAgenciaDao;
import com.primax.srv.idao.ICategoriaEstacionDao;
import com.primax.srv.idao.IFrecuenciaVisitaDao;
import com.primax.srv.idao.IPersonaDao;
import com.primax.srv.idao.IRegionDao;
import com.primax.srv.idao.IResponsableDao;
import com.primax.srv.idao.IRolEtDao;
import com.primax.srv.idao.ITipoCargoDao;
import com.primax.srv.idao.ITipoEstacionDao;

@Named("agenciaBn")
@ViewScoped
public class AgenciaBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IRolEtDao iRolEtDao;
	@EJB
	private IRegionDao iRegionDao;
	@EJB
	private IPersonaDao iPersonaDao;
	@EJB
	private IAgenciaDao iAgenciaDao;
	@EJB
	private ITipoCargoDao iTipoCargoDao;
	@EJB
	private IResponsableDao iResponsableDao;
	@EJB
	private ITipoEstacionDao iTipoEstacionDao;
	@EJB
	private IFrecuenciaVisitaDao iFrecuenciaVisitaDao;
	@EJB
	private IAgenciaCheckListDao iAgenciaCheckListDao;
	@EJB
	private IAgenciaCategoriaDao iAgenciaCategoriaDao;
	@EJB
	private ICategoriaEstacionDao iCategoriaEstacionDao;

	private String textoBusqueda;
	private List<AgenciaEt> agencias;
	private RegionEt regionSeleccionada;
	private CantonEt cantonSeleccionado;
	private AgenciaEt agenciaSeleccionada;
	private ProvinciaEt provinciaSeleccionada;
	private List<ResponsableEt> responsables;
	private TipoEstacionEt tipoEstacionSeleccionado;
	private FrecuenciaVisitaEt frecuenciaVisitaSeleccionado;
	private List<CategoriaEstacionEt> categoriaEstacionSeleccionados;

	@Inject
	private AppMain appMain;
	@Inject
	private BusquedaCheckBean busquedaCheckBn;

	@Override
	protected void init() {
		inicializarObj();
		buscar();
	}

	public void inicializarObj() {
		agenciaSeleccionada = new AgenciaEt();
		busquedaCheckBn.setTarget(1);
		responsables = new ArrayList<>();
		tipoEstacionSeleccionado = null;
		frecuenciaVisitaSeleccionado = null;
	}

	public void nuevo() {
		regionSeleccionada = null;
		cantonSeleccionado = null;
		provinciaSeleccionada = null;
		agenciaSeleccionada = new AgenciaEt();
		agenciaSeleccionada.setAgenciaCategoria(new ArrayList<>());
		agenciaSeleccionada.setAgenciaCheckList(new ArrayList<>());
	}

	public void buscar() {
		agencias = iAgenciaDao.getAgenciasCondicion(textoBusqueda);
	}

	public void guardar() {
		String mensaje = "";
		AgenciaCategoriaEt agenciaCategoria = null;
		List<AgenciaCategoriaEt> agenciaCategoriaN = new ArrayList<>();
		try {
			agenciaSeleccionada.setCanton(cantonSeleccionado);
			agenciaSeleccionada.setTipoEstacion(tipoEstacionSeleccionado);
			agenciaSeleccionada.setFrecuenciaVisita(frecuenciaVisitaSeleccionado);
			mensaje = validacionGuardar(agenciaSeleccionada);
			if (!mensaje.equals("")) {
				showInfo(mensaje, FacesMessage.SEVERITY_ERROR, null, null);
				return;
			}
			UsuarioEt usuario = appMain.getUsuario();
			if (!categoriaEstacionSeleccionados.isEmpty()) {
				List<AgenciaCategoriaEt> agenciaCategorias = agenciaSeleccionada.getAgenciaCategoria();
				if (!agenciaCategorias.isEmpty()) {
					Long totalS = (long) agenciaCategorias.size();
					Long totalI = (long) categoriaEstacionSeleccionados.size();
					if (totalI < totalS) {
						inactivarSubFormato(agenciaSeleccionada.getTipoEstacion().getIdTipoEstacion(),
								agenciaCategorias, usuario);
					} else {
						for (AgenciaCategoriaEt agenciaSubformatoss : agenciaCategorias) {
							Long idIdCategoriaEstacion = agenciaSubformatoss.getCategoriaEstacion()
									.getIdCategoriaEstacion();
							for (CategoriaEstacionEt categoriaEstacion : categoriaEstacionSeleccionados) {
								if (categoriaEstacion.getIdCategoriaEstacion() != idIdCategoriaEstacion) {
									agenciaCategoria = new AgenciaCategoriaEt();
									agenciaCategoria.setAgencia(agenciaSeleccionada);
									agenciaCategoria.setCategoriaEstacion(categoriaEstacion);
									agenciaCategoria.setTipoEstacion(tipoEstacionSeleccionado);
									agenciaCategoriaN.add(agenciaCategoria);
								}
							}
						}
					}
				} else {
					for (CategoriaEstacionEt categoriaEstacion : categoriaEstacionSeleccionados) {
						agenciaCategoria = new AgenciaCategoriaEt();
						agenciaCategoria.setAgencia(agenciaSeleccionada);
						agenciaCategoria.setCategoriaEstacion(categoriaEstacion);
						agenciaCategoria.setTipoEstacion(tipoEstacionSeleccionado);
						agenciaSeleccionada.getAgenciaCategoria().add(agenciaCategoria);
					}
				}
			}
			if (!agenciaCategoriaN.isEmpty()) {
				agenciaSeleccionada.getAgenciaCategoria().addAll(agenciaCategoriaN);
			}

			guardarResponsable(usuario);
			iAgenciaDao.guardarAgencia(agenciaSeleccionada, appMain.getUsuario());
			showInfo("Dato Guardado", FacesMessage.SEVERITY_INFO, null, null);
			RequestContext.getCurrentInstance().execute("PF('dialog_06_1').hide();");
			nuevo();
			buscar();
		} catch (EntidadNoGrabadaException e) {
			showInfo("Error al Guardar", FacesMessage.SEVERITY_ERROR, null, null);
		}
	}

	public String validacionGuardar(AgenciaEt agencia) {
		String mensaje = "";
		try {
			if (agencia.getNombreAgencia() == null || agencia.getNombreAgencia().equals("")) {
				mensaje = "Por favor ingresar nombre";
				return mensaje;
			}
			if (agencia.getCodigoAgencia() == null || agencia.getCodigoAgencia().isEmpty()) {
				mensaje = "Por favor ingresar código";
				return mensaje;
			}
			AgenciaEt agenciaC = iAgenciaDao.getAgenciaCodigo(agencia.getCodigoAgencia());
			if (agenciaC != null && !agenciaC.equals(agenciaSeleccionada)) {
				showInfo("Ya existe una agencia con el código ingresado", FacesMessage.SEVERITY_INFO, null, null);
				return mensaje;
			}
			if (agencia.getCanton() == null) {
				mensaje = "Por favor ingresar canton";
				return mensaje;
			}
			if (agencia.getCanton().getProvincia() == null) {
				mensaje = "Por favor ingresar provincia";
				return mensaje;
			}
			if (agencia.getCanton().getProvincia().getRegion() == null) {
				mensaje = "Por favor ingresar región";
				return mensaje;
			}
			if (agencia.getZona() == null) {
				mensaje = "Por favor ingresar zona";
				return mensaje;
			}
			if (agencia.getTipoEstacion() == null) {
				mensaje = "Por favor ingresar tipo estación";
				return mensaje;
			}
			if (agencia.getFrecuenciaVisita() == null) {
				mensaje = "Por favor ingresar frecuencia visita";
				return mensaje;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método validacionGuardar " + " " + e.getMessage());
		}
		return mensaje;
	}

	public void guardarResponsable(UsuarioEt usuario) {
		try {
			if (!responsables.isEmpty()) {
				for (ResponsableEt responsable : responsables) {
					String ide = responsable.getPersona().getIdentificacionPersona().replace("-", "");
					responsable.getPersona().setIdentificacionPersona(ide);
					iPersonaDao.guardarPersona(responsable.getPersona(), usuario);
					iResponsableDao.guardarResponsable(responsable, usuario);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarResponsable " + " " + e.getMessage());
		}

	}

	public void modificar(AgenciaEt agencia) {
		try {
			agenciaSeleccionada = agencia;
			cantonSeleccionado = agencia.getCanton();
			tipoEstacionSeleccionado = agencia.getTipoEstacion();
			provinciaSeleccionada = agencia.getCanton().getProvincia();
			frecuenciaVisitaSeleccionado = agencia.getFrecuenciaVisita();
			regionSeleccionada = agencia.getCanton().getProvincia().getRegion();
			responsables = iResponsableDao.getResponsableByAgencia1List(agencia);
			List<AgenciaCategoriaEt> agenciaCategorias = agencia.getAgenciaCategoria();
			if (!agenciaCategorias.isEmpty()) {
				categoriaEstacionSeleccionados = new ArrayList<>();
				for (AgenciaCategoriaEt agenciaCategoria : agenciaCategorias) {
					categoriaEstacionSeleccionados.add(agenciaCategoria.getCategoriaEstacion());
				}
			}
			if (agenciaSeleccionada.getAgenciaCheckList().isEmpty()) {
				agenciaSeleccionada.setAgenciaCheckList(new ArrayList<>());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método modificar " + " " + e.getMessage());
		}
	}

	public void anadirResponsable() {
		ResponsableEt responsable = new ResponsableEt();
		responsable.setAgencia(agenciaSeleccionada);
		responsable.setPersona(new PersonaEt());
		responsable.getPersona().setPrimerNombre("N/A");
		responsable.getPersona().setSegundoNombre("N/A");
		responsable.getPersona().setPrimerApellido("N/A");
		responsable.getPersona().setSegundoApellido("N/A");
		responsable.getPersona().setFechaNacimiento(new Date());
		responsable.getPersona().setUbicacion(new UbicacionEt());
		responsable.getPersona().getUbicacion().setCanton(cantonSeleccionado);
		responsable.getPersona().getUbicacion().setProvincia(provinciaSeleccionada);
		responsables.add(responsable);
	}

	public void quitarResponsable(ResponsableEt responsable) {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			responsable.setEstado(EstadoEnum.INA);
			responsable.getPersona().setEstado(EstadoEnum.INA);
			iResponsableDao.guardarResponsable(responsable, usuario);
			responsables = iResponsableDao.getResponsableByAgencia1List(responsable.getAgencia());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método quitarResponsable " + " " + e.getMessage());
		}

	}

	public void quitarCheckList(AgenciaCheckListEt agenciaCheckList) {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			agenciaCheckList.setEstado(EstadoEnum.INA);
			agenciaSeleccionada.getAgenciaCheckList().remove(agenciaCheckList);
			iAgenciaCheckListDao.guardaAgenciaCheckList(agenciaCheckList, usuario);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método quitarCheckList " + " " + e.getMessage());
		}

	}

	public void inactivarSubFormato(Long idTipoEstacion, List<AgenciaCategoriaEt> agenciaSubformatos,
			UsuarioEt usuario) {
		Long idtip0 = 0L;
		Long idCat0 = 0L;
		Long idCat1 = 0L;
		try {
			for (CategoriaEstacionEt categoriaEstacion : categoriaEstacionSeleccionados) {
				for (AgenciaCategoriaEt agenciaCategoria : agenciaSubformatos) {
					idtip0 = agenciaCategoria.getTipoEstacion().getIdTipoEstacion();
					idCat0 = categoriaEstacion.getIdCategoriaEstacion();
					idCat1 = agenciaCategoria.getCategoriaEstacion().getIdCategoriaEstacion();
					if (idtip0.equals(idTipoEstacion) && !idCat0.equals(idCat1)) {
						agenciaCategoria.setEstado(EstadoEnum.INA);
						iAgenciaCategoriaDao.guardarAgenciaCategoria(agenciaCategoria, usuario);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método inactivarSubFormato " + " " + e.getMessage());
		}

	}

	public void closeEventCheckList() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			if (!busquedaCheckBn.getCheckListSeleccionados().isEmpty()) {
				for (CheckListEt checkList : busquedaCheckBn.getCheckListSeleccionados()) {
					if (agenciaSeleccionada.getAgenciaCheckList().isEmpty()) {
						AgenciaCheckListEt agenciaCheckList = new AgenciaCheckListEt();
						agenciaCheckList.setCheckList(checkList);
						agenciaCheckList.setAgencia(agenciaSeleccionada);
						iAgenciaCheckListDao.guardaAgenciaCheckList(agenciaCheckList, usuario);
					} else {
						if (!containsCheckList(agenciaSeleccionada.getAgenciaCheckList(), checkList)) {
							AgenciaCheckListEt agenciaCheckList = new AgenciaCheckListEt();
							agenciaCheckList.setCheckList(checkList);
							agenciaCheckList.setAgencia(agenciaSeleccionada);
							iAgenciaCheckListDao.guardaAgenciaCheckList(agenciaCheckList, usuario);
						}
					}
				}
				agenciaSeleccionada.setAgenciaCheckList(new ArrayList<>());
				List<AgenciaCheckListEt> agenciaCheckListB = iAgenciaCheckListDao
						.getAgenciaCheckListByAgenciaList(agenciaSeleccionada);
				agenciaSeleccionada.getAgenciaCheckList().addAll(agenciaCheckListB);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método closeEventCheckList " + " " + e.getMessage());
		}

	}

	public boolean containsCheckList(final List<AgenciaCheckListEt> list, final CheckListEt checkList) {
		return list.stream().filter(o -> o.getCheckList().equals(checkList)).findFirst().isPresent();
	}

	public SelectItem[] getEstadosActIna() {
		SelectItem[] items = new SelectItem[2];
		items[0] = new SelectItem(EstadoEnum.ACT, EstadoEnum.ACT.getDescripcion());
		items[1] = new SelectItem(EstadoEnum.INA, EstadoEnum.INA.getDescripcion());
		return items;
	}

	public List<RegionEt> getRegiones() {
		List<RegionEt> regiones = iRegionDao.getRegiones(EstadoEnum.ACT);
		return regiones;
	}

	public List<ProvinciaEt> getProvincias() {
		if (regionSeleccionada != null) {
			return regionSeleccionada.getProvincias();
		}
		return Collections.emptyList();
	}

	public List<CantonEt> getCantones() {
		if (provinciaSeleccionada != null) {
			return provinciaSeleccionada.getCantones();
		}
		return Collections.emptyList();
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

	public List<FrecuenciaVisitaEt> getFrecuenciaVisitaList() {
		List<FrecuenciaVisitaEt> frecuenciaVisitas = new ArrayList<>();
		try {
			frecuenciaVisitas = iFrecuenciaVisitaDao.getFrecuenciaVisitaList(null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getFrecuenciaVisitaList " + " " + e.getMessage());
		}
		return frecuenciaVisitas;
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

	public List<CategoriaEstacionEt> getCategoriaEstacionList() {
		List<CategoriaEstacionEt> categoriasEstacion = new ArrayList<>();
		try {
			if (tipoEstacionSeleccionado != null) {
				for (TipoCategoriaEstacionEt tipoCategoriaEstacion : tipoEstacionSeleccionado
						.getTipoCategoriaEstacion()) {
					categoriasEstacion.add(tipoCategoriaEstacion.getCategoriaEstacion());
				}
				return categoriasEstacion;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getCategoriaEstacionList " + " " + e.getMessage());
		}
		return categoriasEstacion;
	}

	public void validarCedula(String identificacion) {
		boolean condicion = false;
		try {
			PersonaEt persona = iPersonaDao.getPersonasPorCedulaR(identificacion);
			condicion = valida(identificacion);
			if (!condicion) {
				showInfo("Número de cédula no es valido", FacesMessage.SEVERITY_WARN);
				return;
			} else if (persona != null) {
				showInfo("Ya existe responsable con cédula " + identificacion, FacesMessage.SEVERITY_WARN);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método validarCedula " + " " + e.getMessage());
		}
	}

	public static boolean valida(String x) {
		int suma = 0;
		if (x.length() == 9) {
			System.out.println("Ingrese su cedula de 10 digitos");
			return false;
		} else {
			int a[] = new int[x.length() / 2];
			int b[] = new int[(x.length() / 2)];
			int c = 0;
			int d = 1;
			for (int i = 0; i < x.length() / 2; i++) {
				a[i] = Integer.parseInt(String.valueOf(x.charAt(c)));
				c = c + 2;
				if (i < (x.length() / 2) - 1) {
					b[i] = Integer.parseInt(String.valueOf(x.charAt(d)));
					d = d + 2;
				}
			}
			for (int i = 0; i < a.length; i++) {
				a[i] = a[i] * 2;
				if (a[i] > 9) {
					a[i] = a[i] - 9;
				}
				suma = suma + a[i] + b[i];
			}
			int aux = suma / 10;
			int dec = (aux + 1) * 10;
			if ((dec - suma) == Integer.parseInt(String.valueOf(x.charAt(x.length() - 1))))
				return true;
			else if (suma % 10 == 0 && x.charAt(x.length() - 1) == '0') {
				return true;
			} else {
				return false;
			}
		}

	}

	public String getTextoBusqueda() {
		return textoBusqueda;
	}

	public void setTextoBusqueda(String textoBusqueda) {
		this.textoBusqueda = textoBusqueda;
	}

	public RegionEt getRegionSeleccionada() {
		return regionSeleccionada;
	}

	public void setRegionSeleccionada(RegionEt regionSeleccionada) {
		this.regionSeleccionada = regionSeleccionada;
	}

	public CantonEt getCantonSeleccionado() {
		return cantonSeleccionado;
	}

	public void setCantonSeleccionado(CantonEt cantonSeleccionado) {
		this.cantonSeleccionado = cantonSeleccionado;
	}

	public AgenciaEt getAgenciaSeleccionada() {
		return agenciaSeleccionada;
	}

	public void setAgenciaSeleccionada(AgenciaEt agenciaSeleccionada) {
		this.agenciaSeleccionada = agenciaSeleccionada;
	}

	public ProvinciaEt getProvinciaSeleccionada() {
		return provinciaSeleccionada;
	}

	public void setProvinciaSeleccionada(ProvinciaEt provinciaSeleccionada) {
		this.provinciaSeleccionada = provinciaSeleccionada;
	}

	public List<AgenciaEt> getAgencias() {
		return agencias;
	}

	public void setAgencias(List<AgenciaEt> agencias) {
		this.agencias = agencias;
	}

	public AppMain getAppMain() {
		return appMain;
	}

	public void setAppMain(AppMain appMain) {
		this.appMain = appMain;
	}

	public List<ResponsableEt> getResponsables() {
		return responsables;
	}

	public void setResponsables(List<ResponsableEt> responsables) {
		this.responsables = responsables;
	}

	public TipoEstacionEt getTipoEstacionSeleccionado() {
		return tipoEstacionSeleccionado;
	}

	public void setTipoEstacionSeleccionado(TipoEstacionEt tipoEstacionSeleccionado) {
		this.tipoEstacionSeleccionado = tipoEstacionSeleccionado;
	}

	public List<CategoriaEstacionEt> getCategoriaEstacionSeleccionados() {
		return categoriaEstacionSeleccionados;
	}

	public void setCategoriaEstacionSeleccionados(List<CategoriaEstacionEt> categoriaEstacionSeleccionados) {
		this.categoriaEstacionSeleccionados = categoriaEstacionSeleccionados;
	}

	public BusquedaCheckBean getBusquedaCheckBn() {
		return busquedaCheckBn;
	}

	public void setBusquedaCheckBn(BusquedaCheckBean busquedaCheckBn) {
		this.busquedaCheckBn = busquedaCheckBn;
	}

	public FrecuenciaVisitaEt getFrecuenciaVisitaSeleccionado() {
		return frecuenciaVisitaSeleccionado;
	}

	public void setFrecuenciaVisitaSeleccionado(FrecuenciaVisitaEt frecuenciaVisitaSeleccionado) {
		this.frecuenciaVisitaSeleccionado = frecuenciaVisitaSeleccionado;
	}

	@Override
	protected void onDestroy() {
		iRolEtDao.remove();
		iRegionDao.remove();
		iPersonaDao.remove();
		iAgenciaDao.remove();
		iTipoCargoDao.remove();
		iResponsableDao.remove();
		iTipoEstacionDao.remove();
		iFrecuenciaVisitaDao.remove();
		iAgenciaCheckListDao.remove();
		iAgenciaCategoriaDao.remove();
		iCategoriaEstacionDao.remove();
	}

}
