package com.primax.bean.vs;

import java.io.Serializable;
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

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.enm.gen.ParametrosGeneralesEnum;
import com.primax.enm.gen.UtilEnum;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.CriterioEvaluacionDetalleEt;
import com.primax.jpa.param.CriterioEvaluacionEt;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.param.NivelColorEt;
import com.primax.jpa.param.ParametrosGeneralesEt;
import com.primax.jpa.param.ProcesoDetalleEt;
import com.primax.jpa.param.ProcesoEt;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.ICriterioEvaluacionDao;
import com.primax.srv.idao.ICriterioEvaluacionDetalleDao;
import com.primax.srv.idao.IEvaluacionDao;
import com.primax.srv.idao.INivelColorDao;
import com.primax.srv.idao.IParametrolGeneralDao;
import com.primax.srv.idao.IProcesoDao;
import com.primax.srv.idao.IProcesoDetalleDao;
import com.primax.srv.idao.ITipoChecKListDao;

@Named("CriterioEvaluacionBn")
@ViewScoped
public class CriterioEvaluacionBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IProcesoDao iProcesoDao;
	@EJB
	private IEvaluacionDao iEvaluacionDao;
	@EJB
	private INivelColorDao iNivelColorDao;

	@EJB
	private ITipoChecKListDao iTipoChecKListDao;
	@EJB
	private IProcesoDetalleDao iProcesoDetalleDao;
	@EJB
	private IParametrolGeneralDao iParametrolGeneralDao;
	@EJB
	private ICriterioEvaluacionDao iCriterioEvaluacionDao;
	@EJB
	private ICriterioEvaluacionDetalleDao iCriterioEvaluacionDetalleDao;

	@Inject
	private AppMain appMain;

	private Long maximo = 0L;
	private String condicion;
	private List<String> tiposArqueo;
	private String tituloDetlle = "";
	private boolean condicionKpi = true;
	private ProcesoEt procesoSeleccionado;
	private boolean condicionArqueo = true;
	private NivelColorEt nivelColorSeleccionado;
	private EvaluacionEt evaluacionSeleccionado;
	private String tipoArqueoSeleccionado = "N/A";
	private TipoChecKListEt tipoChecKListSeleccionado;
	private List<CriterioEvaluacionEt> criterioEvaluaciones;
	private ProcesoDetalleEt procesoDetalleSeleccionado = null;
	private CriterioEvaluacionEt criterioEvaluacionSeleccionado;
	private CriterioEvaluacionDetalleEt criterioEvaluacionDetalleSeleccionado;

	@Override
	protected void init() {
		inicializarObj();
		buscar();
	}

	public void inicializarObj() {
		tiposArqueo = new ArrayList<String>();
		tiposArqueo.add("Visita Control");
		tiposArqueo.add("Fondo Suelto");
		tiposArqueo.add("Caja Chica");
		tiposArqueo.add("Caja Fuerte");
		evaluacionSeleccionado = null;
		tipoChecKListSeleccionado = null;
		procesoDetalleSeleccionado = null;
		criterioEvaluacionSeleccionado = new CriterioEvaluacionEt();
		criterioEvaluacionSeleccionado.setCriterioEvaluacionDetalle(new ArrayList<CriterioEvaluacionDetalleEt>());
	}

	public void buscar() {
		try {
			criterioEvaluaciones = iCriterioEvaluacionDao.getCriterioEvaluacionList(evaluacionSeleccionado,
					tipoChecKListSeleccionado, condicion);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}
	}

	public void guardar() {
		String tipo = "N";
		String mensaje = "";
		try {
			UsuarioEt usuario = appMain.getUsuario();
			mensaje = validacionGuardar();
			if (!mensaje.equals("")) {
				showInfo(mensaje, FacesMessage.SEVERITY_INFO);
				return;
			}
			if (criterioEvaluacionSeleccionado.isArqueo()) {
				if (tipoArqueoSeleccionado.equals("Visita Control")) {
					tipo = "C";
				} else if (tipoArqueoSeleccionado.equals("Fondo Suelto")) {
					tipo = "F";
				} else if (tipoArqueoSeleccionado.equals("Caja Chica")) {
					tipo = "E";
				} else if (tipoArqueoSeleccionado.equals("Caja Fuerte")) {
					tipo = "T";
				}
			}
			criterioEvaluacionSeleccionado.setTipo(tipo);
			criterioEvaluacionSeleccionado.setEvaluacion(evaluacionSeleccionado);
			criterioEvaluacionSeleccionado.setTipoChecKList(tipoChecKListSeleccionado);
			criterioEvaluacionSeleccionado.setProcesoDetalle(procesoDetalleSeleccionado);
			iCriterioEvaluacionDao.guardarCriterioEvaluacion(criterioEvaluacionSeleccionado, usuario);
			showInfo("Información Grabada con Éxito ", FacesMessage.SEVERITY_INFO);
			RequestContext.getCurrentInstance().execute("PF('dialog_13_2').hide();");
			buscar();
			tipoArqueoSeleccionado = "N/A";
			inicializarObj();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}
	}

	public String validacionGuardar() {
		String mensaje = "";
		try {
			if (criterioEvaluacionSeleccionado.getCriterioEvaluacionDetalle() != null) {
				for (CriterioEvaluacionDetalleEt detalle : criterioEvaluacionSeleccionado
						.getCriterioEvaluacionDetalle()) {
					if (detalle.getColor() == null || detalle.getColor().isEmpty()) {
						mensaje = "Por favor seleccionar color del criterio de evaluación " + "-" + detalle.getNombre();
						break;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método validarGuardar " + " " + e.getMessage());
		}
		return mensaje;
	}

	public String validacion() {
		String mensaje = "";
		Long puntajeKpi = 0L;
		Long puntajeCriterio = 0L;
		try {
			puntajeKpi = procesoDetalleSeleccionado.getPuntaje();
			puntajeCriterio = criterioEvaluacionSeleccionado.getCriterioEvaluacionDetalle().stream()
					.mapToLong(p -> p.getPuntaje()).sum();
			if (puntajeCriterio > puntajeKpi) {
				mensaje = "Total puntaje Criterio no puede ser mayor a puntos de KPI";
				return mensaje;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método validacion " + " " + e.getMessage());
		}
		return mensaje;
	}

	public void guardarDetalle() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			if (nivelColorSeleccionado != null) {
				criterioEvaluacionDetalleSeleccionado.setColor(nivelColorSeleccionado.getColor());
			}
			iCriterioEvaluacionDetalleDao.guardarCriterioEvaluacionDetalle(criterioEvaluacionDetalleSeleccionado,
					usuario);
			showInfo("Información Grabada con Éxito ", FacesMessage.SEVERITY_INFO);
			RequestContext.getCurrentInstance().execute("PF('dialog_13_3').hide();");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}
	}

	public void nuevo() {
		condicionKpi = true;
		procesoSeleccionado = null;
		evaluacionSeleccionado = null;
		tipoArqueoSeleccionado = "N/A";
		tipoChecKListSeleccionado = null;
		procesoDetalleSeleccionado = null;
		criterioEvaluacionSeleccionado = new CriterioEvaluacionEt();

	}

	public void modificar(CriterioEvaluacionEt criterioEvaluacion) {
		maximo = 0l;
		tituloDetlle = "";
		procesoSeleccionado = null;
		evaluacionSeleccionado = null;
		tipoChecKListSeleccionado = null;
		procesoDetalleSeleccionado = null;
		criterioEvaluacionSeleccionado = null;
		criterioEvaluacionSeleccionado = iCriterioEvaluacionDao
				.getCriterioEvaluacion(criterioEvaluacion.getIdCriterioEvaluacion());
		condicionArqueo = criterioEvaluacionSeleccionado.isArqueo();
		if (criterioEvaluacionSeleccionado.getTipo().equals("C")) {
			tipoArqueoSeleccionado = "Visita Control";
		} else if (criterioEvaluacionSeleccionado.getTipo().equals("F")) {
			tipoArqueoSeleccionado = "Fondo Suelto";
		} else if (criterioEvaluacionSeleccionado.getTipo().equals("E")) {
			tipoArqueoSeleccionado = "Caja Chica";
		} else if (criterioEvaluacionSeleccionado.getTipo().equals("T")) {
			tipoArqueoSeleccionado = "Caja Fuerte";
		}
		if (criterioEvaluacionSeleccionado.getEvaluacion() != null) {
			evaluacionSeleccionado = criterioEvaluacionSeleccionado.getEvaluacion();
		}
		if (criterioEvaluacionSeleccionado.getTipoChecKList() != null) {
			tipoChecKListSeleccionado = criterioEvaluacionSeleccionado.getTipoChecKList();
		}
		if (criterioEvaluacionSeleccionado.getProcesoDetalle() != null) {
			procesoDetalleSeleccionado = criterioEvaluacionSeleccionado.getProcesoDetalle();
			procesoSeleccionado = criterioEvaluacionSeleccionado.getProcesoDetalle().getProceso();
		}
		if (tipoChecKListSeleccionado != null) {
			if (criterioEvaluacionSeleccionado.getProcesoDetalle() != null) {
				tituloDetlle = criterioEvaluacionSeleccionado.getNombre() + " - Puntos "
						+ criterioEvaluacionSeleccionado.getProcesoDetalle().getPuntaje();
				maximo = criterioEvaluacionSeleccionado.getProcesoDetalle().getPuntaje();
			} else {
				maximo = 100L;
				tituloDetlle = criterioEvaluacionSeleccionado.getNombre();
			}
		}
		if (criterioEvaluacionSeleccionado.getCriterioEvaluacionDetalle() == null) {
			criterioEvaluacionSeleccionado.setCriterioEvaluacionDetalle(new ArrayList<>());
		}
		habilitarKpi();
	}

	public void modificarDetalle(CriterioEvaluacionDetalleEt criterioEvaluacionDetalle) {
		try {
			criterioEvaluacionDetalleSeleccionado = criterioEvaluacionDetalle;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método modificarDetalle " + " " + e.getMessage());
		}

	}

	public void anadirCriterioEvaluacionDetalle() {
		Long orden = 1L;
		Date fechaRegistro = UtilEnum.FECHA_REGISTRO.getValue();
		if (!criterioEvaluacionSeleccionado.getCriterioEvaluacionDetalle().isEmpty()) {
			orden = (long) criterioEvaluacionSeleccionado.getCriterioEvaluacionDetalle().size() + 1;
		}
		CriterioEvaluacionDetalleEt criterioEvaluacionDetalle = new CriterioEvaluacionDetalleEt();
		criterioEvaluacionDetalle.setOrden(orden);
		criterioEvaluacionDetalle.setFechaRegistro(fechaRegistro);
		criterioEvaluacionDetalle.setCriterioEvaluacion(criterioEvaluacionSeleccionado);
		criterioEvaluacionSeleccionado.getCriterioEvaluacionDetalle().add(criterioEvaluacionDetalle);
	}

	public void quitarCriterioEvaluacionDetalle(CriterioEvaluacionDetalleEt criterioEvaluacionDetalle) {
		Date fechaRegistro = UtilEnum.FECHA_REGISTRO.getValue();
		criterioEvaluacionDetalle.setFechaModificacion(fechaRegistro);
		criterioEvaluacionDetalle.setEstado(EstadoEnum.INA);
		criterioEvaluacionSeleccionado.getCriterioEvaluacionDetalle().remove(criterioEvaluacionDetalle);
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

	public List<TipoChecKListEt> getTipoChecKList() {
		List<TipoChecKListEt> tipoChecKList = new ArrayList<>();
		try {
			if (evaluacionSeleccionado != null) {
				tipoChecKList = iTipoChecKListDao.getTipoCheckListByEvaluacion(evaluacionSeleccionado);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTipoChecKListList " + " " + e.getMessage());
		}
		return tipoChecKList;
	}

	public List<ProcesoEt> getProcesoList() {
		List<ProcesoEt> procesoList = new ArrayList<>();
		try {
			if (evaluacionSeleccionado != null) {
				procesoList = iProcesoDao.getProcesoByTipoList(tipoChecKListSeleccionado);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getProcesoList " + " " + e.getMessage());
		}
		return procesoList;
	}

	public List<ProcesoDetalleEt> getProcesoDetalleList() {
		List<ProcesoDetalleEt> procesoDetalles = new ArrayList<ProcesoDetalleEt>();
		try {
			if (procesoSeleccionado != null) {
				procesoDetalles = iProcesoDetalleDao.getProcesoDetalleByProcesolList(procesoSeleccionado);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getProcesoDetalleList " + " " + e.getMessage());
		}
		return procesoDetalles;
	}

	public List<ParametrosGeneralesEt> getColorList() {
		List<ParametrosGeneralesEt> colores = new ArrayList<>();
		try {
			colores = iParametrolGeneralDao.getListaHIjos(
					iParametrolGeneralDao.getObjPadre(ParametrosGeneralesEnum.COLOR_MATRIZ.getValue().toString()));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getColorList " + " " + e.getMessage());
		}
		return colores;
	}

	public List<NivelColorEt> getNivelColorList() {
		List<NivelColorEt> nivelColores = new ArrayList<NivelColorEt>();
		try {
			nivelColores = iNivelColorDao.getNivelColorList(null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getNivelColorList " + " " + e.getMessage());
		}
		return nivelColores;
	}

	public void habilitarKpi() {
		try {
			if (tipoChecKListSeleccionado != null) {
				if (tipoChecKListSeleccionado.getEvaluacion().isCriterio()) {
					condicionKpi = true;
				} else {
					condicionKpi = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método habilitarKpi " + " " + e.getMessage());
		}
	}

	public void mostrarNombre() {
		try {
			if (procesoDetalleSeleccionado != null) {
				criterioEvaluacionSeleccionado.setNombre(procesoDetalleSeleccionado.getNombreProcesoDetalle());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método mostrarNombre " + " " + e.getMessage());
		}

	}

	public SelectItem[] getEstadosActIna() {
		SelectItem[] items = new SelectItem[2];
		items[0] = new SelectItem(EstadoEnum.ACT, EstadoEnum.ACT.getDescripcion());
		items[1] = new SelectItem(EstadoEnum.INA, EstadoEnum.INA.getDescripcion());
		return items;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
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

	public List<CriterioEvaluacionEt> getCriterioEvaluaciones() {
		return criterioEvaluaciones;
	}

	public void setCriterioEvaluaciones(List<CriterioEvaluacionEt> criterioEvaluaciones) {
		this.criterioEvaluaciones = criterioEvaluaciones;
	}

	public CriterioEvaluacionEt getCriterioEvaluacionSeleccionado() {
		return criterioEvaluacionSeleccionado;
	}

	public void setCriterioEvaluacionSeleccionado(CriterioEvaluacionEt criterioEvaluacionSeleccionado) {
		this.criterioEvaluacionSeleccionado = criterioEvaluacionSeleccionado;
	}

	public ProcesoDetalleEt getProcesoDetalleSeleccionado() {
		return procesoDetalleSeleccionado;
	}

	public void setProcesoDetalleSeleccionado(ProcesoDetalleEt procesoDetalleSeleccionado) {
		this.procesoDetalleSeleccionado = procesoDetalleSeleccionado;
	}

	public boolean isCondicionKpi() {
		return condicionKpi;
	}

	public void setCondicionKpi(boolean condicionKpi) {
		this.condicionKpi = condicionKpi;
	}

	public String getTituloDetlle() {
		return tituloDetlle;
	}

	public void setTituloDetlle(String tituloDetlle) {
		this.tituloDetlle = tituloDetlle;
	}

	public CriterioEvaluacionDetalleEt getCriterioEvaluacionDetalleSeleccionado() {
		return criterioEvaluacionDetalleSeleccionado;
	}

	public void setCriterioEvaluacionDetalleSeleccionado(
			CriterioEvaluacionDetalleEt criterioEvaluacionDetalleSeleccionado) {
		this.criterioEvaluacionDetalleSeleccionado = criterioEvaluacionDetalleSeleccionado;
	}

	public Long getMaximo() {
		return maximo;
	}

	public void setMaximo(Long maximo) {
		this.maximo = maximo;
	}

	public List<String> getTiposArqueo() {
		return tiposArqueo;
	}

	public void setTiposArqueo(List<String> tiposArqueo) {
		this.tiposArqueo = tiposArqueo;
	}

	public String getTipoArqueoSeleccionado() {
		return tipoArqueoSeleccionado;
	}

	public void setTipoArqueoSeleccionado(String tipoArqueoSeleccionado) {
		this.tipoArqueoSeleccionado = tipoArqueoSeleccionado;
	}

	public boolean isCondicionArqueo() {
		return condicionArqueo;
	}

	public void setCondicionArqueo(boolean condicionArqueo) {
		this.condicionArqueo = condicionArqueo;
	}

	public ProcesoEt getProcesoSeleccionado() {
		return procesoSeleccionado;
	}

	public void setProcesoSeleccionado(ProcesoEt procesoSeleccionado) {
		this.procesoSeleccionado = procesoSeleccionado;
	}

	public NivelColorEt getNivelColorSeleccionado() {
		return nivelColorSeleccionado;
	}

	public void setNivelColorSeleccionado(NivelColorEt nivelColorSeleccionado) {
		this.nivelColorSeleccionado = nivelColorSeleccionado;
	}

	@Override
	protected void onDestroy() {
		iProcesoDao.remove();
		iNivelColorDao.remove();
		iEvaluacionDao.remove();
		iTipoChecKListDao.remove();
		iProcesoDetalleDao.remove();
		iParametrolGeneralDao.remove();
		iCriterioEvaluacionDao.remove();
		iCriterioEvaluacionDetalleDao.remove();
	}

}
