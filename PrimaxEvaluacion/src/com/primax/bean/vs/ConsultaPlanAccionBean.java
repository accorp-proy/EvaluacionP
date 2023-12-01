package com.primax.bean.vs;

import java.io.IOException;
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
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.enm.gen.RutaFileEnum;
import com.primax.enm.gen.UtilEnum;
import com.primax.enm.msg.Mensajes;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.enums.EstadoPlanAccionEnum;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.param.NivelEvaluacionEt;
import com.primax.jpa.param.ParametrosGeneralesEt;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.param.ZonaEt;
import com.primax.jpa.pla.CheckListEjecucionEt;
import com.primax.jpa.pla.CheckListEjecucionPlnAdjuntoEt;
import com.primax.jpa.sec.RolEt;
import com.primax.jpa.sec.RolUsuarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.jpa.param.ZonaUsuarioEt;
import com.primax.srv.idao.IAgenciaDao;
import com.primax.srv.idao.ICheckListEjecucionDao;
import com.primax.srv.idao.ICheckListEjecucionPlnAdjuntoDao;
import com.primax.srv.idao.IEvaluacionDao;
import com.primax.srv.idao.IGeneralUtilsDao;
import com.primax.srv.idao.INivelEvaluacionDao;
import com.primax.srv.idao.IParametrolGeneralDao;
import com.primax.srv.idao.IRolEtDao;
import com.primax.srv.idao.ITipoChecKListDao;
import com.primax.srv.idao.IUsuarioDao;
import com.primax.srv.idao.IZonaDao;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

@Named("ConsultaPlanAccionBn")
@ViewScoped
public class ConsultaPlanAccionBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IZonaDao iZonaDao;
	@EJB
	private IRolEtDao iRolEtDao;
	@EJB
	private IAgenciaDao iAgenciaDao;
	@EJB
	private IUsuarioDao iUsuarioDao;
	@EJB
	private IEvaluacionDao iEvaluacionDao;
	@EJB
	private IGeneralUtilsDao iGeneralUtilsDao;
	@EJB
	private ITipoChecKListDao iTipoChecKListDao;
	@EJB
	private INivelEvaluacionDao iNivelEvaluacionDao;
	@EJB
	private IParametrolGeneralDao iParametrolGeneralDao;
	@EJB
	private ICheckListEjecucionDao iCheckListEjecucionDao;
	@EJB
	private ICheckListEjecucionPlnAdjuntoDao iCheckListEjecucionPlnAdjuntoDao;

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
	private EstadoPlanAccionEnum estadoPlanAccionSeleccionado;
	private CheckListEjecucionEt checkListEjecucionSeleccionado;
	private List<CheckListEjecucionPlnAdjuntoEt> checkListEjecucionPlnAdjuntoEliminado;

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
		checkListEjecucionPlnAdjuntoEliminado = new ArrayList<>();
	}

	public void buscar() {
		try {
			UsuarioEt usuario = iUsuarioDao.getUsuarioId(appMain.getUsuario().getIdUsuario());
			if (usuario.isAccesoZona()) {
				checkListEjecuciones = iCheckListEjecucionDao.getCheckListEjecucionAccesoZonaListPlanAccion(
						estacionSeleccionada, evaluacionSeleccionada, tipoChecKListSeleccionado,
						nivelEvaluacionSeleccionado, fDesde, fHasta, estadoPlanAccionSeleccionado, usuario);
			} else {
				checkListEjecuciones = iCheckListEjecucionDao.getCheckListEjecucionListPlanAccion(zonaSeleccionada,
						estacionSeleccionada, evaluacionSeleccionada, tipoChecKListSeleccionado,
						nivelEvaluacionSeleccionado, fDesde, fHasta, estadoPlanAccionSeleccionado);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}
	}

	public void guardar() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			if (!checkListEjecucionPlnAdjuntoEliminado.isEmpty()) {
				for (CheckListEjecucionPlnAdjuntoEt checkListEjecucionPlnAdjunto : checkListEjecucionPlnAdjuntoEliminado) {
					iCheckListEjecucionPlnAdjuntoDao.guardarCheckListEjecucionPlnAdjunto(checkListEjecucionPlnAdjunto,
							usuario);
				}
			}
			iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucionSeleccionado, usuario);
			showInfo("Dato Guardado", FacesMessage.SEVERITY_INFO, null, null);
			RequestContext.getCurrentInstance().execute("PF('dlg_pln_005_1').hide();");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}
	}

	public void upload(FileUploadEvent event) throws EntidadNoGrabadaException {
		String ruta;
		String nombreArchivo = "";
		try {
			CheckListEjecucionEt checkListEjecucion = checkListEjecucionSeleccionado;
			nombreArchivo = event.getFile().getFileName();
			CheckListEjecucionPlnAdjuntoEt reg = new CheckListEjecucionPlnAdjuntoEt();
			reg.setNombreAdjunto(nombreArchivo);
			reg.setCheckListEjecucion(checkListEjecucionSeleccionado);
			reg.setFile(event.getFile().getInputstream());
			for (CheckListEjecucionPlnAdjuntoEt doc : checkListEjecucion.getCheckListEjecucionPlnAdjunto()) {
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
			checkListEjecucion.getCheckListEjecucionPlnAdjunto().add(reg);
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

	public void quitarAdjunto(CheckListEjecucionPlnAdjuntoEt checkListEjecucionPlnAdjunto) {
		try {
			Date fechaRegistro = UtilEnum.FECHA_REGISTRO.getValue();
			checkListEjecucionPlnAdjunto.setFechaModificacion(fechaRegistro);
			checkListEjecucionPlnAdjunto.setEstado(EstadoEnum.INA);
			checkListEjecucionPlnAdjuntoEliminado.add(checkListEjecucionPlnAdjunto);
			checkListEjecucionSeleccionado.getCheckListEjecucionPlnAdjunto().add(checkListEjecucionPlnAdjunto);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método quitarAdjunto " + " " + e.getMessage());
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

	public boolean containsRol(final List<RolUsuarioEt> list, final RolEt rol) {
		return list.stream().filter(o -> o.getRol().equals(rol)).findFirst().isPresent();
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

	public SelectItem[] getEstadoPlanAccion() {
		SelectItem[] items = new SelectItem[3];
		items[0] = new SelectItem(EstadoPlanAccionEnum.ESTADO_PLAN_A,
				EstadoPlanAccionEnum.ESTADO_PLAN_A.getDescripcion());
		items[1] = new SelectItem(EstadoPlanAccionEnum.INGRESADO, EstadoPlanAccionEnum.INGRESADO.getDescripcion());
		items[2] = new SelectItem(EstadoPlanAccionEnum.PENDIENTE, EstadoPlanAccionEnum.PENDIENTE.getDescripcion());
		return items;
	}

	public void modificar(CheckListEjecucionEt checkListEjecucion) {
		checkListEjecucionSeleccionado = checkListEjecucion;
	}

	public AppMain getAppMain() {
		return appMain;
	}

	public void setAppMain(AppMain appMain) {
		this.appMain = appMain;
	}

	public List<CheckListEjecucionEt> getCheckListEjecuciones() {
		return checkListEjecuciones;
	}

	public void setCheckListEjecuciones(List<CheckListEjecucionEt> checkListEjecuciones) {
		this.checkListEjecuciones = checkListEjecuciones;
	}

	public CheckListEjecucionEt getCheckListEjecucionSeleccionado() {
		return checkListEjecucionSeleccionado;
	}

	public void setCheckListEjecucionSeleccionado(CheckListEjecucionEt checkListEjecucionSeleccionado) {
		this.checkListEjecucionSeleccionado = checkListEjecucionSeleccionado;
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

	public EstadoPlanAccionEnum getEstadoPlanAccionSeleccionado() {
		return estadoPlanAccionSeleccionado;
	}

	public void setEstadoPlanAccionSeleccionado(EstadoPlanAccionEnum estadoPlanAccionSeleccionado) {
		this.estadoPlanAccionSeleccionado = estadoPlanAccionSeleccionado;
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
		iEvaluacionDao.remove();
		iTipoChecKListDao.remove();
		iNivelEvaluacionDao.remove();
		iParametrolGeneralDao.remove();
		iCheckListEjecucionDao.remove();
		iCheckListEjecucionPlnAdjuntoDao.remove();
	}

}
