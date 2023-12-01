package com.primax.bean.vs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.jpa.gen.PlanAccionOrganizacion;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.param.ParametrosGeneralesEt;
import com.primax.jpa.param.ZonaEt;
import com.primax.jpa.param.ZonaUsuarioEt;
import com.primax.jpa.pla.CheckListEjecucionPlnAdjuntoEt;
import com.primax.jpa.pla.PlanAccionAnioEt;
import com.primax.jpa.pla.PlanAccionChekListEt;
import com.primax.jpa.pla.PlanAccionEstacionEt;
import com.primax.jpa.pla.PlanAccionMesEt;
import com.primax.jpa.pla.PlanAccionZonaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IAgenciaDao;
import com.primax.srv.idao.ICheckListEjecucionPlnAdjuntoDao;
import com.primax.srv.idao.IEvaluacionDao;
import com.primax.srv.idao.IFrecuenciaVisitaDao;
import com.primax.srv.idao.IParametrolGeneralDao;
import com.primax.srv.idao.IPlanAccionAnioDao;
import com.primax.srv.idao.IPlanAccionChekListDao;
import com.primax.srv.idao.IPlanAccionEstacionDao;
import com.primax.srv.idao.IPlanAccionMesDao;
import com.primax.srv.idao.IPlanAccionZonaDao;
import com.primax.srv.idao.IUsuarioDao;
import com.primax.srv.idao.IZonaDao;

@Named("TreePlanAccionBn")
@ViewScoped
public class TreePlanAccionBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IZonaDao iZonaDao;
	@EJB
	private IUsuarioDao iUsuarioDao;
	@EJB
	private IAgenciaDao iAgenciaDao;
	@EJB
	private IEvaluacionDao iEvaluacionDao;
	@EJB
	private IPlanAccionMesDao iPlanAccionMesDao;
	@EJB
	private IPlanAccionAnioDao iPlanAccionAnioDao;
	@EJB
	private IPlanAccionZonaDao iPlanAccionZonaDao;
	@EJB
	private IFrecuenciaVisitaDao iFrecuenciaVisitaDao;
	@EJB
	private IParametrolGeneralDao iParametrolGeneralDao;
	@EJB
	private IPlanAccionEstacionDao iPlanAccionEstacionDao;
	@EJB
	private IPlanAccionChekListDao iPlanAccionChekListDao;
	@EJB
	private ICheckListEjecucionPlnAdjuntoDao iCheckListEjecucionPlnAdjuntoDao;

	@Inject
	private AppMain appMain;

	private TreeNode root;
	private TreeNode selectedNode;
	private ZonaEt zonaSeleccionada;
	private AgenciaEt estacionSeleccionada;
	private EvaluacionEt evaluacionSeleccionada;
	private ParametrosGeneralesEt anioSeleccionado;
	private List<ParametrosGeneralesEt> mesesSeleccionados;
	private PlanAccionOrganizacion planAccionOrganizacionSelect;

	@Override
	protected void init() {
		generar();
		disenarTree();

	}

	public void generar() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			if (anioSeleccionado != null) {
				generarTree(Integer.parseInt(anioSeleccionado.getValorLista()), usuario);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método generar " + " " + e.getMessage());
		}

	}

	public void generarTree(int anio, UsuarioEt usuario) {

		Long idZona = 0L;
		Long idEstacion = 0L;
		Long idEvaluacion = 0L;
		try {
			Long idAnio = (long) anio;
			iFrecuenciaVisitaDao.limpiarReporte(usuario.getIdUsuario());
			if (zonaSeleccionada != null) {
				idZona = zonaSeleccionada.getIdZona();
			}
			if (estacionSeleccionada != null) {
				idEstacion = estacionSeleccionada.getIdAgencia();
			}
			if (evaluacionSeleccionada != null) {
				idEvaluacion = evaluacionSeleccionada.getIdEvaluacion();
			}
			for (ParametrosGeneralesEt parametrosGenerales : mesesSeleccionados) {
				int mes = Integer.parseInt(parametrosGenerales.getValorLista());
				Date fechaDesde = getFechaDesde(mes, anio);
				Date fechaHasta = getFechaHasta(mes, anio);
				iPlanAccionAnioDao.generar(idAnio, fechaDesde, fechaHasta, idZona, idEstacion, idEvaluacion,
						usuario.getIdUsuario());
			}
			disenarTree();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método generar " + " " + e.getMessage());
		}
	}

	public void disenarTree() {
		Long idPlanAccion = 0L;
		try {
			root = new DefaultTreeNode("Files", null);
			List<PlanAccionAnioEt> anios = iPlanAccionAnioDao.getPlanAccionAnioList();
			for (PlanAccionAnioEt anio : anios) {
				idPlanAccion = anio.getIdPlanAccionAnio();
				TreeNode node0 = new DefaultTreeNode(
						new PlanAccionOrganizacion(0L, 0L, anio.getAnio().toString(), "Carpeta"), root);

				List<PlanAccionMesEt> meses = iPlanAccionMesDao.getPlanAccionMesList(idPlanAccion, anio.getAnio());
				for (PlanAccionMesEt mes : meses) {
					TreeNode node00 = new DefaultTreeNode(
							new PlanAccionOrganizacion(0L, 0L, mes.getMesLetra().toString(), "Carpeta"), node0);
					List<PlanAccionZonaEt> zonas = iPlanAccionZonaDao.getPlanAccionZonaList(idPlanAccion,
							anio.getAnio(), mes.getMesNumero());
					for (PlanAccionZonaEt zona : zonas) {
						TreeNode node000 = new DefaultTreeNode(
								new PlanAccionOrganizacion(0L, 0L, zona.getZona(), "Carpeta"), node00);

						List<PlanAccionEstacionEt> estaciones = iPlanAccionEstacionDao.getPlanAccionMesList(
								idPlanAccion, anio.getAnio(), mes.getMesNumero(), zona.getIdZona());
						for (PlanAccionEstacionEt estacion : estaciones) {
							TreeNode node0000 = new DefaultTreeNode(
									new PlanAccionOrganizacion(0L, 0L, estacion.getAgencia(), "Carpeta"), node000);

							List<PlanAccionChekListEt> checks = iPlanAccionChekListDao.getPlanAccionChekListList(
									idPlanAccion, anio.getAnio(), mes.getMesNumero(), zona.getIdZona(),
									estacion.getIdAgencia());
							for (PlanAccionChekListEt check : checks) {
								List<CheckListEjecucionPlnAdjuntoEt> adjuntos = iCheckListEjecucionPlnAdjuntoDao
										.getAdjuntoList(check.getIdCheckListEjecucion());

								if (adjuntos.isEmpty()) {
									TreeNode node00000 = new DefaultTreeNode(
											new PlanAccionOrganizacion(check.getIdCheckListEjecucion(), 0L,
													check.getDescripcion(), "Carpeta"),
											node0000);
									CheckListEjecucionPlnAdjuntoEt adj = new CheckListEjecucionPlnAdjuntoEt();
									adj.setIdCheckListEjecucion(check.getIdCheckListEjecucion());
									adj.setNombreAdjunto("PLAN DE ACCIÓN Y SEGUIMIENTO");
									node00000.getChildren()
											.add(new DefaultTreeNode(
													new PlanAccionOrganizacion(adj.getIdCheckListEjecucion(),
															check.getIdAgencia(), adj.getNombreAdjunto(), "Plan")));

								} else {
									TreeNode node00000 = new DefaultTreeNode(
											new PlanAccionOrganizacion(check.getIdCheckListEjecucion(), 0L,
													check.getDescripcion(), "Carpeta"),
											node0000);
									CheckListEjecucionPlnAdjuntoEt adj = new CheckListEjecucionPlnAdjuntoEt();
									adj.setIdCheckListEjecucion(check.getIdCheckListEjecucion());
									adj.setNombreAdjunto("PLAN DE ACCIÓN Y SEGUIMIENTO");
									node00000.getChildren()
											.add(new DefaultTreeNode(
													new PlanAccionOrganizacion(adj.getIdCheckListEjecucion(),
															check.getIdAgencia(), adj.getNombreAdjunto(), "Plan")));
									for (CheckListEjecucionPlnAdjuntoEt adjunto : adjuntos) {
										node00000.getChildren()
												.add(new DefaultTreeNode(new PlanAccionOrganizacion(
														adjunto.getCheckListEjecucion().getIdCheckListEjecucion(), 0L,
														adjunto.getNombreAdjunto(), "Adjunto")));
									}
								}
							}
						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método disenarTree " + " " + e.getMessage());
		}
	}

	public void nodeSelect(PlanAccionOrganizacion planAccionOrganizacion) {
		System.out.println("Información" + " " + planAccionOrganizacion.getNombre());
	}

	public boolean customFilter(TreeNode treeNode, Object filter, Locale locale) {
		if (treeNode.getData() == null || filter == null) {
			return true;
		}
		String filterText = filter.toString().trim().toLowerCase(locale);
		if (filterText.isEmpty()) {
			return true;
		}
		return ((String) treeNode.getData()).toLowerCase(locale).contains(filterText);
	}

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (filterText.isEmpty()) {
			return true;
		}
		PlanAccionOrganizacion customer = (PlanAccionOrganizacion) value;
		return customer.getNombre().toLowerCase().contains(filterText)
				|| customer.getTipo().toLowerCase().contains(filterText);
	}

	public Date getFechaDesde(int mes, int anio) {
		Calendar calDesd = Calendar.getInstance();
		calDesd.setTime(new Date());
		calDesd.set(Calendar.MONTH, mes);
		calDesd.set(Calendar.YEAR, anio);
		calDesd.set(Calendar.DAY_OF_MONTH, 1);
		// System.out.println("Fecha Desde" + " " + calDesd.getTime());
		return calDesd.getTime();

	}

	public Date getFechaHasta(int mes, int anio) {
		Calendar calHast = Calendar.getInstance();
		calHast.setTime(new Date());
		calHast.set(Calendar.MONTH, mes);
		calHast.set(Calendar.YEAR, anio);
		int ultimoDiaMes = calHast.getActualMaximum(Calendar.DAY_OF_MONTH);
		calHast.set(anio, mes, ultimoDiaMes);
		// System.out.println("Fecha Hasta" + " " + calHast.getTime());
		return calHast.getTime();
	}

	public List<ParametrosGeneralesEt> getTipoAnioList() {
		List<ParametrosGeneralesEt> parametrosGenerales = new ArrayList<ParametrosGeneralesEt>();
		try {
			ParametrosGeneralesEt parametrosGeneral = iParametrolGeneralDao.getObjPadre("106");
			parametrosGenerales = iParametrolGeneralDao.getListaHIjos(parametrosGeneral);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTipoAnioList " + " " + e.getMessage());
		}
		return parametrosGenerales;
	}

	public List<ParametrosGeneralesEt> getTipoMesList() {
		List<ParametrosGeneralesEt> parametrosGenerales = new ArrayList<ParametrosGeneralesEt>();
		try {
			ParametrosGeneralesEt parametrosGeneral = iParametrolGeneralDao.getObjPadre("108");
			parametrosGenerales = iParametrolGeneralDao.getListaHIjos(parametrosGeneral);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTipoMesList " + " " + e.getMessage());
		}
		return parametrosGenerales;
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

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public PlanAccionOrganizacion getPlanAccionOrganizacionSelect() {
		return planAccionOrganizacionSelect;
	}

	public void setPlanAccionOrganizacionSelect(PlanAccionOrganizacion planAccionOrganizacionSelect) {
		this.planAccionOrganizacionSelect = planAccionOrganizacionSelect;
	}

	public ZonaEt getZonaSeleccionada() {
		return zonaSeleccionada;
	}

	public void setZonaSeleccionada(ZonaEt zonaSeleccionada) {
		this.zonaSeleccionada = zonaSeleccionada;
	}

	public EvaluacionEt getEvaluacionSeleccionada() {
		return evaluacionSeleccionada;
	}

	public void setEvaluacionSeleccionada(EvaluacionEt evaluacionSeleccionada) {
		this.evaluacionSeleccionada = evaluacionSeleccionada;
	}

	public ParametrosGeneralesEt getAnioSeleccionado() {
		return anioSeleccionado;
	}

	public void setAnioSeleccionado(ParametrosGeneralesEt anioSeleccionado) {
		this.anioSeleccionado = anioSeleccionado;
	}

	public List<ParametrosGeneralesEt> getMesesSeleccionados() {
		return mesesSeleccionados;
	}

	public void setMesesSeleccionados(List<ParametrosGeneralesEt> mesesSeleccionados) {
		this.mesesSeleccionados = mesesSeleccionados;
	}

	public AgenciaEt getEstacionSeleccionada() {
		return estacionSeleccionada;
	}

	public void setEstacionSeleccionada(AgenciaEt estacionSeleccionada) {
		this.estacionSeleccionada = estacionSeleccionada;
	}

	@Override
	protected void onDestroy() {
		iZonaDao.remove();
		iAgenciaDao.remove();
		iEvaluacionDao.remove();
		iPlanAccionMesDao.remove();
		iPlanAccionAnioDao.remove();
		iPlanAccionZonaDao.remove();
		iFrecuenciaVisitaDao.remove();
		iParametrolGeneralDao.remove();
		iPlanAccionChekListDao.remove();
		iPlanAccionEstacionDao.remove();
		iCheckListEjecucionPlnAdjuntoDao.remove();
	}

}
