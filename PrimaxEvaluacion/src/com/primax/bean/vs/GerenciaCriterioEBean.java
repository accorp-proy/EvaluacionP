package com.primax.bean.vs;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.jpa.enums.EstadoPlanAccionEnum;
import com.primax.jpa.enums.TipoCheckListEnum;
import com.primax.jpa.param.ResponsableEt;
import com.primax.jpa.pla.CheckListEjecucionEt;
import com.primax.jpa.pla.CheckListKpiEjecucionEt;
import com.primax.jpa.pla.CheckListProcesoEjecucionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.ICheckListEjecucionDao;
import com.primax.srv.idao.IResponsableDao;
import com.primax.srv.idao.IUsuarioDao;

@Named("GerenciaCriterioEBn")
@ViewScoped
public class GerenciaCriterioEBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IUsuarioDao iUsuarioDao;
	@EJB
	private IResponsableDao iResponsableDao;
	@EJB
	private ICheckListEjecucionDao iCheckListEjecucionDao;

	private Long totalPuntaje = 0L;
	private Long totalPuntajeCalificacion = 0L;
	private CheckListEjecucionEt checkListEjecucion;

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
			ResponsableEt responsable = iResponsableDao.getResponsableEstacion(usuario.getPersonaUsuario());
			checkListEjecucion = iCheckListEjecucionDao
					.getCheckListEjecucionPlanAccion(TipoCheckListEnum.CRITERIO_ESPECIFICO, responsable.getAgencia());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}

	}

	public void inicializarObj() {

	}

	public void guardar() {
		String pagina = "";
		String mensaje = "";
		try {
			mensaje = validarguardar();
			if (!mensaje.equals("")) {
				showInfo(mensaje, FacesMessage.SEVERITY_WARN);
				return;
			}
			UsuarioEt usuario = appMain.getUsuario();
			FacesContext contex = FacesContext.getCurrentInstance();
			checkListEjecucion.setEstadoPlanAccion(EstadoPlanAccionEnum.INGRESADO);
			iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucion, usuario);
			pagina = "/PrimaxEvaluacion/pages/main.jsf";
			contex.getExternalContext().redirect(pagina);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}
	}

	public String validarguardar() {
		String mensaje = "";
		try {
			for (CheckListProcesoEjecucionEt checkListProceso : checkListEjecucion.getCheckListProcesoEjecucion()) {
				for (CheckListKpiEjecucionEt checkListKpi : checkListProceso.getCheckListKpiEjecucion()) {
					if (checkListKpi.getComentarioPlanAccion() == null) {
						mensaje = "Por favor ingresar plan de acción del KPI " + " " + checkListKpi.getDescripcion();
						return mensaje;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método mensaje " + " " + e.getMessage());
		}
		return mensaje;
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

	public Long getTotalPuntajeCalificacion() {
		return totalPuntajeCalificacion;
	}

	public void setTotalPuntajeCalificacion(Long totalPuntajeCalificacion) {
		this.totalPuntajeCalificacion = totalPuntajeCalificacion;
	}

	@Override
	protected void onDestroy() {
		iUsuarioDao.remove();
		iResponsableDao.remove();
		iCheckListEjecucionDao.remove();
	}

}
