package com.primax.bean.vs;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.enums.EstadoPlanAccionEnum;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.param.ResponsableEt;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.param.ZonaEt;
import com.primax.jpa.pla.CheckListEjecucionEt;
import com.primax.jpa.sec.RolEt;
import com.primax.jpa.sec.RolUsuarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IAgenciaDao;
import com.primax.srv.idao.ICheckListEjecucionDao;
import com.primax.srv.idao.IEvaluacionDao;
import com.primax.srv.idao.IResponsableDao;
import com.primax.srv.idao.IRolEtDao;
import com.primax.srv.idao.ITipoChecKListDao;

@Named("ConsultaPlanAccionGBn")
@ViewScoped
public class ConsultaPlanAccionGBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	private IRolEtDao iRolEtDao;
	@EJB
	private IAgenciaDao iAgenciaDao;
	@EJB
	private IEvaluacionDao iEvaluacionDao;
	@EJB
	private IResponsableDao iResponsableDao;
	@EJB
	private ITipoChecKListDao iTipoChecKListDao;
	@EJB
	private ICheckListEjecucionDao iCheckListEjecucionDao;

	@Inject
	private AppMain appMain;

	private ZonaEt zonaSeleccionada;
	private Date fDesde = new Date();
	private Date fHasta = new Date();
	private EvaluacionEt evaluacionSeleccionada;
	private TipoChecKListEt tipoChecKListSeleccionado;
	private List<CheckListEjecucionEt> checkListEjecuciones;
	private EstadoPlanAccionEnum estadoPlanAccionSeleccionado;
	private CheckListEjecucionEt checkListEjecucionSeleccionado;

	@Override
	protected void init() {
		inicializarObj();
		buscar();
	}

	public void inicializarObj() {
		zonaSeleccionada = null;
		fDesde = primerDiaMes();
		fHasta = ultimoDiaMes();

	}

	public void buscar() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			ResponsableEt responsable = iResponsableDao.getResponsableEstacion(usuario.getPersonaUsuario());
			checkListEjecuciones = iCheckListEjecucionDao.getCheckListEjecucionListPlanAccion(zonaSeleccionada,
					responsable.getAgencia(), evaluacionSeleccionada, tipoChecKListSeleccionado, null, fDesde, fHasta,
					estadoPlanAccionSeleccionado);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}
	}

	public void retroceder() {
		String pagina = "";
		try {
			FacesContext contex = FacesContext.getCurrentInstance();
			pagina = "/PrimaxEvaluacion/pages/main.jsf";
			contex.getExternalContext().redirect(pagina);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método retroceder " + " " + e.getMessage());
		}
	}

	public List<AgenciaEt> getEstacionList() {
		List<AgenciaEt> agencias = new ArrayList<AgenciaEt>();
		try {
			agencias = iAgenciaDao.getAgencias(EstadoEnum.ACT);
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

	@Override
	protected void onDestroy() {
		iRolEtDao.remove();
		iAgenciaDao.remove();
		iEvaluacionDao.remove();
		iResponsableDao.remove();
		iTipoChecKListDao.remove();
		iCheckListEjecucionDao.remove();
	}

}
