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
import com.primax.enm.gen.UtilEnum;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.FrecuenciaVisitaDetalleEt;
import com.primax.jpa.param.FrecuenciaVisitaEt;
import com.primax.jpa.param.NivelColorEt;
import com.primax.jpa.param.TipoEstacionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IFrecuenciaVisitaDao;
import com.primax.srv.idao.IFrecuenciaVisitaDetalleDao;
import com.primax.srv.idao.INivelColorDao;
import com.primax.srv.idao.ITipoEstacionDao;

@Named("FrecuenciaVisitaBn")
@ViewScoped
public class FrecuenciaVisitaBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private INivelColorDao iNivelColorDao;
	@EJB
	private ITipoEstacionDao iTipoEstacionDao;
	@EJB
	private IFrecuenciaVisitaDao iFrecuenciaVisitaDao;
	@EJB
	private IFrecuenciaVisitaDetalleDao iFrecuenciaVisitaDetalleDao;

	@Inject
	private AppMain appMain;

	private String condicion;

	private NivelColorEt nivelColorSeleccionado;
	private List<FrecuenciaVisitaEt> frecuenciaVisitas;
	private FrecuenciaVisitaEt frecuenciaVisitaSeleccionado;
	private FrecuenciaVisitaDetalleEt frecuenciaVisitaDetalleSeleccionado;

	@Override
	protected void init() {
		inicializarObj();
		buscar();

	}

	public void inicializarObj() {
		frecuenciaVisitaSeleccionado = new FrecuenciaVisitaEt();
		frecuenciaVisitaSeleccionado.setFrecuenciaVisitaDetalle(new ArrayList<>());
	}

	public void buscar() {
		try {
			frecuenciaVisitas = iFrecuenciaVisitaDao.getFrecuenciaVisitaList(condicion);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}
	}

	public void guardar() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			iFrecuenciaVisitaDao.guardarFrecuenciaVisita(frecuenciaVisitaSeleccionado, usuario);
			showInfo("Información Grabada con Éxito ", FacesMessage.SEVERITY_INFO);
			RequestContext.getCurrentInstance().execute("PF('dialog_17_2').hide();");
			buscar();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}
	}

	public void guardarDetalle() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			if (nivelColorSeleccionado != null) {
				frecuenciaVisitaDetalleSeleccionado.setColor(nivelColorSeleccionado.getColor());
			}
			iFrecuenciaVisitaDetalleDao.guardarFrecuenciaVisitaDetalle(frecuenciaVisitaDetalleSeleccionado, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarDetalle " + " " + e.getMessage());
		}
	}

	public void nuevo() {
		frecuenciaVisitaSeleccionado = new FrecuenciaVisitaEt();
	}

	public void modificar(FrecuenciaVisitaEt frecuenciaVisita) {
		frecuenciaVisitaSeleccionado = frecuenciaVisita;
		if (frecuenciaVisitaSeleccionado.getFrecuenciaVisitaDetalle() == null) {
			frecuenciaVisitaSeleccionado.setFrecuenciaVisitaDetalle(new ArrayList<>());
		}

	}

	public void modificarNivelEvaluacionDetalle(FrecuenciaVisitaDetalleEt frecuenciaVisitaDetalle) {
		frecuenciaVisitaDetalleSeleccionado = frecuenciaVisitaDetalle;
	}

	public void anadirProcesoDetalle() {
		Long orden = 1L;
		Date fechaRegistro = UtilEnum.FECHA_REGISTRO.getValue();
		if (!frecuenciaVisitaSeleccionado.getFrecuenciaVisitaDetalle().isEmpty()) {
			orden = (long) frecuenciaVisitaSeleccionado.getFrecuenciaVisitaDetalle().size() + 1;
		}
		FrecuenciaVisitaDetalleEt frecuenciaVisitaDetalle = new FrecuenciaVisitaDetalleEt();
		frecuenciaVisitaDetalle.setOrden(orden);
		frecuenciaVisitaDetalle.setFechaRegistro(fechaRegistro);
		frecuenciaVisitaDetalle.setFrecuenciaVisita(frecuenciaVisitaSeleccionado);
		frecuenciaVisitaSeleccionado.getFrecuenciaVisitaDetalle().add(frecuenciaVisitaDetalle);

	}

	public void quitarProcesoDetalle(FrecuenciaVisitaDetalleEt frecuenciaVisitaDetalle) {
		Date fechaRegistro = UtilEnum.FECHA_REGISTRO.getValue();
		frecuenciaVisitaDetalle.setFechaModificacion(fechaRegistro);
		frecuenciaVisitaDetalle.setEstado(EstadoEnum.INA);
		frecuenciaVisitaSeleccionado.getFrecuenciaVisitaDetalle().remove(frecuenciaVisitaDetalle);
	}

	public SelectItem[] getEstadosActIna() {
		SelectItem[] items = new SelectItem[2];
		items[0] = new SelectItem(EstadoEnum.ACT, EstadoEnum.ACT.getDescripcion());
		items[1] = new SelectItem(EstadoEnum.INA, EstadoEnum.INA.getDescripcion());
		return items;
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

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public List<FrecuenciaVisitaEt> getFrecuenciaVisitas() {
		return frecuenciaVisitas;
	}

	public void setFrecuenciaVisitas(List<FrecuenciaVisitaEt> frecuenciaVisitas) {
		this.frecuenciaVisitas = frecuenciaVisitas;
	}

	public FrecuenciaVisitaEt getFrecuenciaVisitaSeleccionado() {
		return frecuenciaVisitaSeleccionado;
	}

	public void setFrecuenciaVisitaSeleccionado(FrecuenciaVisitaEt frecuenciaVisitaSeleccionado) {
		this.frecuenciaVisitaSeleccionado = frecuenciaVisitaSeleccionado;
	}

	public FrecuenciaVisitaDetalleEt getFrecuenciaVisitaDetalleSeleccionado() {
		return frecuenciaVisitaDetalleSeleccionado;
	}

	public void setFrecuenciaVisitaDetalleSeleccionado(FrecuenciaVisitaDetalleEt frecuenciaVisitaDetalleSeleccionado) {
		this.frecuenciaVisitaDetalleSeleccionado = frecuenciaVisitaDetalleSeleccionado;
	}

	public NivelColorEt getNivelColorSeleccionado() {
		return nivelColorSeleccionado;
	}

	public void setNivelColorSeleccionado(NivelColorEt nivelColorSeleccionado) {
		this.nivelColorSeleccionado = nivelColorSeleccionado;
	}

	@Override
	protected void onDestroy() {
		iNivelColorDao.remove();
		iTipoEstacionDao.remove();
		iFrecuenciaVisitaDao.remove();
		iFrecuenciaVisitaDetalleDao.remove();
	}

}
