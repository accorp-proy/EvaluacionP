package com.primax.bean.vs;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.jpa.enums.EstadoCheckListEnum;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.param.NivelEvaluacionEt;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.param.ZonaEt;
import com.primax.jpa.pla.CheckListEjecucionEt;
import com.primax.jpa.sec.RolEt;
import com.primax.jpa.sec.RolUsuarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.jpa.param.ZonaUsuarioEt;
import com.primax.srv.idao.IAgenciaDao;
import com.primax.srv.idao.ICheckListEjecucionDao;
import com.primax.srv.idao.IEvaluacionDao;
import com.primax.srv.idao.INivelEvaluacionDao;
import com.primax.srv.idao.IRolEtDao;
import com.primax.srv.idao.ITipoChecKListDao;
import com.primax.srv.idao.IUsuarioDao;
import com.primax.srv.idao.IZonaDao;

@Named("CheckListEjecutadoBn")
@ViewScoped
public class CheckListEjecutadoBean extends BaseBean implements Serializable {

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
	private ITipoChecKListDao iTipoChecKListDao;
	@EJB
	private INivelEvaluacionDao iNivelEvaluacionDao;
	@EJB
	private ICheckListEjecucionDao iCheckListEjecucionDao;

	@Inject
	private AppMain appMain;

	private ZonaEt zonaSeleccionada;
	private Date fDesde = new Date();
	private Date fHasta = new Date();
	private boolean condicionA = false;
	private AgenciaEt estacionSeleccionada;
	private EvaluacionEt evaluacionSeleccionada;
	private UsuarioEt usuarioSeleccionado = null;
	private TipoChecKListEt tipoChecKListSeleccionado;
	private NivelEvaluacionEt nivelEvaluacionSeleccionado;
	private List<CheckListEjecucionEt> checkListEjecuciones;
	private EstadoCheckListEnum estadoCheckListSeleccionado;

	@Override
	protected void init() {
		inicializarObj();
		buscar();
	}

	public void inicializarObj() {
		fDesde = primerDiaMes();
		fHasta = ultimoDiaMes();
		zonaSeleccionada = null;
		usuarioSeleccionado = null;
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
					condicionA = true;
					checkListEjecuciones = iCheckListEjecucionDao.getCheckListEjecucionAccesoZonaList(zonaSeleccionada,
							estacionSeleccionada, evaluacionSeleccionada, tipoChecKListSeleccionado,
							nivelEvaluacionSeleccionado, fDesde, fHasta, usuarioSeleccionado,
							estadoCheckListSeleccionado);
				} else {

					checkListEjecuciones = iCheckListEjecucionDao.getCheckListEjecucionAccesoZonaList(zonaSeleccionada,
							estacionSeleccionada, evaluacionSeleccionada, tipoChecKListSeleccionado,
							nivelEvaluacionSeleccionado, fDesde, fHasta, usuario, estadoCheckListSeleccionado);
				}
			} else {
				if (containsRol(rolUsuario, rol)) {
					condicionA = true;
					checkListEjecuciones = iCheckListEjecucionDao.getCheckListEjecucionList(zonaSeleccionada,
							estacionSeleccionada, evaluacionSeleccionada, tipoChecKListSeleccionado,
							nivelEvaluacionSeleccionado, fDesde, fHasta, usuarioSeleccionado,
							estadoCheckListSeleccionado);
				} else {
					checkListEjecuciones = iCheckListEjecucionDao.getCheckListEjecucionList(zonaSeleccionada,
							estacionSeleccionada, evaluacionSeleccionada, tipoChecKListSeleccionado,
							nivelEvaluacionSeleccionado, fDesde, fHasta, usuario, estadoCheckListSeleccionado);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}
	}

	public List<UsuarioEt> getListUsuario() {
		List<UsuarioEt> usuarios = new ArrayList<UsuarioEt>();
		try {
			usuarios = iUsuarioDao.getUsuarioAuditorList();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getListUsuario" + " " + e.getMessage());
		}
		return usuarios;
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

	public SelectItem[] getEstadoCheckList() {
		SelectItem[] items = new SelectItem[5];
		items[0] = new SelectItem(EstadoCheckListEnum.ESTADO_CHECK, EstadoCheckListEnum.ESTADO_CHECK.getDescripcion());
		items[1] = new SelectItem(EstadoCheckListEnum.AGENDADA, EstadoCheckListEnum.AGENDADA.getDescripcion());
		items[2] = new SelectItem(EstadoCheckListEnum.EN_EJECUCION, EstadoCheckListEnum.EN_EJECUCION.getDescripcion());
		items[3] = new SelectItem(EstadoCheckListEnum.EJECUTADO, EstadoCheckListEnum.EJECUTADO.getDescripcion());
		items[4] = new SelectItem(EstadoCheckListEnum.INCONCLUSO, EstadoCheckListEnum.INCONCLUSO.getDescripcion());
		return items;
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

	public EstadoCheckListEnum getEstadoCheckListSeleccionado() {
		return estadoCheckListSeleccionado;
	}

	public void setEstadoCheckListSeleccionado(EstadoCheckListEnum estadoCheckListSeleccionado) {
		this.estadoCheckListSeleccionado = estadoCheckListSeleccionado;
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

	public boolean isCondicionA() {
		return condicionA;
	}

	public void setCondicionA(boolean condicionA) {
		this.condicionA = condicionA;
	}

	public UsuarioEt getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	public void setUsuarioSeleccionado(UsuarioEt usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
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
		iCheckListEjecucionDao.remove();
	}

}
