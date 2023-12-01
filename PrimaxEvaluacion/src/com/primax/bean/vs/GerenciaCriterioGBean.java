package com.primax.bean.vs;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.enm.msg.Mensajes;
import com.primax.jpa.enums.EstadoPlanAccionEnum;
import com.primax.jpa.enums.TipoCheckListEnum;
import com.primax.jpa.param.ResponsableEt;
import com.primax.jpa.pla.CheckListEjecucionAdjuntoEt;
import com.primax.jpa.pla.CheckListEjecucionEt;
import com.primax.jpa.pla.CheckListKpiEjecucionEt;
import com.primax.jpa.pla.CheckListProcesoEjecucionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.ICheckListEjecucionDao;
import com.primax.srv.idao.IResponsableDao;

@Named("GerenciaCriterioGBn")
@ViewScoped
public class GerenciaCriterioGBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
					.getCheckListEjecucionPlanAccion(TipoCheckListEnum.CRITERIO_GENERAL, responsable.getAgencia());
			if (checkListEjecucion != null) {
				mostrarTotal(checkListEjecucion);
				mostrarTotalCalificacion(checkListEjecucion);
			}

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
						break;
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
				if (checkListProcesoEjecucion.getCheckListKpiEjecucion().get(i).getkPICritico() == null
						&& checkListProcesoEjecucion.getCheckListKpiEjecucion().get(i).isSumar()) {
					totalPuntajeD += checkListProcesoEjecucion.getCheckListKpiEjecucion().get(i).getPuntajeEjecucion();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método sumarProceso " + " " + e.getMessage());
		}
		return totalPuntajeD;
	}

	public void mostrarTotal(CheckListEjecucionEt checkListEjecucion) {
		totalPuntaje = 0L;
		Long totalKpi = 0L;
		try {
			for (CheckListProcesoEjecucionEt checkListProceso : checkListEjecucion.getCheckListProcesoEjecucion()) {
				for (int i = 0; i < checkListProceso.getCheckListKpiEjecucion().size(); i++) {
					if (checkListProceso.getCheckListKpiEjecucion().get(i).getkPICritico() == null
							&& checkListProceso.getCheckListKpiEjecucion().get(i).isSumar()) {
						totalKpi += checkListProceso.getCheckListKpiEjecucion().get(i).getProcesoDetalle().getPuntaje();
					}
				}
			}
			totalPuntaje = totalKpi;
			System.out.println("Total Puntaje" + " " + totalPuntaje);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método mostrarTotal " + " " + e.getMessage());
		}
	}

	public void mostrarTotalCalificacion(CheckListEjecucionEt checkListEjecucion) {
		totalPuntajeCalificacion = 0L;
		Long totalKpi = 0L;
		try {
			for (CheckListProcesoEjecucionEt checkListProcesoEjecucion : checkListEjecucion
					.getCheckListProcesoEjecucion()) {
				totalKpi += checkListProcesoEjecucion.getCheckListKpiEjecucion().stream().filter(p -> p.isSumar())
						.mapToLong(p -> p.getPuntajeEjecucion()).sum();
			}
			totalPuntajeCalificacion = totalKpi;
			System.out.println("Total Puntaje Calificación" + " " + totalPuntajeCalificacion);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método mostrarTotal " + " " + e.getMessage());
		}
	}

	public void upload(FileUploadEvent event) {
		try {
			CheckListEjecucionAdjuntoEt reg = new CheckListEjecucionAdjuntoEt();
			reg.setCheckListEjecucion(checkListEjecucion);
			reg.setNombreAdjunto(event.getFile().getFileName());
			reg.setFile(event.getFile().getInputstream());

			for (CheckListEjecucionAdjuntoEt doc : checkListEjecucion.getCheckListEjecucionAdjunto()) {
				if (doc.getNombreAdjunto().equals(reg.getNombreAdjunto())) {
					showInfo("" + Mensajes._ERROR_UPLOAD_DOCUMENTO.getDescripcion(), FacesMessage.SEVERITY_ERROR);
					return;
				}
			}
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
		iResponsableDao.remove();
		iCheckListEjecucionDao.remove();
	}
}
