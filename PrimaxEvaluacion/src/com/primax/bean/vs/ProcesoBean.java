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
import com.primax.jpa.param.NivelEsfuerzoEt;
import com.primax.jpa.param.ProcesoDetalleEt;
import com.primax.jpa.param.ProcesoEt;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.INivelEsfuerzoDao;
import com.primax.srv.idao.IProcesoDao;
import com.primax.srv.idao.IProcesoDetalleDao;
import com.primax.srv.idao.ITipoChecKListDao;

@Named("ProcesoBn")
@ViewScoped
public class ProcesoBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IProcesoDao iProcesoDao;
	@EJB
	private ITipoChecKListDao iTipoChecListDao;
	@EJB
	private INivelEsfuerzoDao iNivelEsfuerzoDao;
	@EJB
	private IProcesoDetalleDao iProcesoDetalleDao;

	@Inject
	private AppMain appMain;

	private String condicion;
	private List<ProcesoEt> procesos;
	private List<String> condicionMatriz;
	private ProcesoEt procesoSeleccionado;
	private String vizualizaMatrizSeleccionado;
	private TipoChecKListEt tipoChecKListSeleccionado;
	private ProcesoDetalleEt procesoDetalleSeleccionado;
	private TipoChecKListEt tipoChecKListSeleccionadoBusqueda;

	@Override
	protected void init() {
		inicializarObj();
		buscar();
		CheckboxMatriz();
	}

	public void CheckboxMatriz() {
		try {
			condicionMatriz = new ArrayList<String>();
			condicionMatriz.add("SI");
			condicionMatriz.add("NO");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método CheckboxMatriz " + " " + e.getMessage());
		}
	}

	public void inicializarObj() {
		tipoChecKListSeleccionadoBusqueda = null;
		procesoSeleccionado = new ProcesoEt();
		procesoSeleccionado.setProcesoDetalle(new ArrayList<>());
	}

	public void buscar() {
		try {
			procesos = iProcesoDao.getProcesoList(tipoChecKListSeleccionadoBusqueda, condicion);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}
	}

	public void guardar() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			procesoSeleccionado.setTipoChecKList(tipoChecKListSeleccionado);
			iProcesoDao.guardarProceso(procesoSeleccionado, usuario);
			showInfo("Información Grabada con Éxito ", FacesMessage.SEVERITY_INFO);
			RequestContext.getCurrentInstance().execute("PF('dialog_10_2').hide();");
			buscar();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}
	}

	public void guardarComentario() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			boolean visualizarMatriz = vizualizaMatrizSeleccionado.equals("SI") ? true : false;
			procesoDetalleSeleccionado.setVisualizarMatriz(visualizarMatriz);
			iProcesoDetalleDao.guardaProcesoDetalle(procesoDetalleSeleccionado, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarComentario " + " " + e.getMessage());
		}

	}

	public void nuevo() {
		procesoSeleccionado = new ProcesoEt();
	}

	public void modificar(ProcesoEt proceso) {
		procesoSeleccionado = proceso;
		tipoChecKListSeleccionado = proceso.getTipoChecKList();
		if (procesoSeleccionado.getProcesoDetalle() == null) {
			procesoSeleccionado.setProcesoDetalle(new ArrayList<>());
		}
	}

	public void anadirProcesoDetalle() {
		Long orden = 1L;
		Date fechaRegistro = UtilEnum.FECHA_REGISTRO.getValue();
		if (!procesoSeleccionado.getProcesoDetalle().isEmpty()) {
			orden = (long) procesoSeleccionado.getProcesoDetalle().size() + 1;
		}
		ProcesoDetalleEt procesoDetalle = new ProcesoDetalleEt();
		procesoDetalle.setOrden(orden);
		procesoDetalle.setProceso(procesoSeleccionado);
		procesoDetalle.setFechaRegistro(fechaRegistro);
		procesoSeleccionado.getProcesoDetalle().add(procesoDetalle);

	}

	public void quitarProcesoDetalle(ProcesoDetalleEt procesoDetalle) {
		Date fechaRegistro = UtilEnum.FECHA_REGISTRO.getValue();
		procesoDetalle.setFechaModificacion(fechaRegistro);
		procesoDetalle.setEstado(EstadoEnum.INA);
		procesoSeleccionado.getProcesoDetalle().remove(procesoDetalle);
	}

	public void anadirComentario(ProcesoDetalleEt procesoDetalle) {
		procesoDetalleSeleccionado = procesoDetalle;
		if (procesoDetalleSeleccionado.isVisualizarMatriz()) {
			vizualizaMatrizSeleccionado = "SI";
		} else {
			vizualizaMatrizSeleccionado = "NO";
		}
	}

	public SelectItem[] getEstadosActIna() {
		SelectItem[] items = new SelectItem[2];
		items[0] = new SelectItem(EstadoEnum.ACT, EstadoEnum.ACT.getDescripcion());
		items[1] = new SelectItem(EstadoEnum.INA, EstadoEnum.INA.getDescripcion());
		return items;
	}

	public List<TipoChecKListEt> getTiposChecList() {
		List<TipoChecKListEt> tipoChecList = new ArrayList<TipoChecKListEt>();
		try {
			tipoChecList = iTipoChecListDao.getTipoChecList(null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTiposChecList " + " " + e.getMessage());
		}
		return tipoChecList;
	}
	
	public List<NivelEsfuerzoEt> getNivelEsfuerzoList() {
		List<NivelEsfuerzoEt> nivelesEsfuerzo = new ArrayList<NivelEsfuerzoEt>();
		try {
			nivelesEsfuerzo = iNivelEsfuerzoDao.getNivelEsfuerzoList(null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getNivelEsfuerzoList " + " " + e.getMessage());
		}
		return nivelesEsfuerzo;
	}

	public List<ProcesoEt> getProcesos() {
		return procesos;
	}

	public void setProcesos(List<ProcesoEt> procesos) {
		this.procesos = procesos;
	}

	public ProcesoEt getProcesoSeleccionado() {
		return procesoSeleccionado;
	}

	public void setProcesoSeleccionado(ProcesoEt procesoSeleccionado) {
		this.procesoSeleccionado = procesoSeleccionado;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public TipoChecKListEt getTipoChecKListSeleccionado() {
		return tipoChecKListSeleccionado;
	}

	public void setTipoChecKListSeleccionado(TipoChecKListEt tipoChecKListSeleccionado) {
		this.tipoChecKListSeleccionado = tipoChecKListSeleccionado;
	}

	public TipoChecKListEt getTipoChecKListSeleccionadoBusqueda() {
		return tipoChecKListSeleccionadoBusqueda;
	}

	public void setTipoChecKListSeleccionadoBusqueda(TipoChecKListEt tipoChecKListSeleccionadoBusqueda) {
		this.tipoChecKListSeleccionadoBusqueda = tipoChecKListSeleccionadoBusqueda;
	}

	public ProcesoDetalleEt getProcesoDetalleSeleccionado() {
		return procesoDetalleSeleccionado;
	}

	public void setProcesoDetalleSeleccionado(ProcesoDetalleEt procesoDetalleSeleccionado) {
		this.procesoDetalleSeleccionado = procesoDetalleSeleccionado;
	}

	public List<String> getCondicionMatriz() {
		return condicionMatriz;
	}

	public void setCondicionMatriz(List<String> condicionMatriz) {
		this.condicionMatriz = condicionMatriz;
	}

	public String getVizualizaMatrizSeleccionado() {
		return vizualizaMatrizSeleccionado;
	}

	public void setVizualizaMatrizSeleccionado(String vizualizaMatrizSeleccionado) {
		this.vizualizaMatrizSeleccionado = vizualizaMatrizSeleccionado;
	}

	@Override
	protected void onDestroy() {
		iProcesoDao.remove();
		iTipoChecListDao.remove();
		iNivelEsfuerzoDao.remove();
		iProcesoDetalleDao.remove();
	}

}
