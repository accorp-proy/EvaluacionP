package com.primax.bean.vs;

import java.io.Serializable;
import java.util.ArrayList;
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
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.CategoriaEstacionEt;
import com.primax.jpa.param.TipoCategoriaEstacionEt;
import com.primax.jpa.param.TipoEstacionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.ICategoriaEstacionDao;
import com.primax.srv.idao.ITipoEstacionDao;

@Named("TipoEstacionBn")
@ViewScoped
public class TipoEstacionBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private ITipoEstacionDao iTipoEstacionDao;
	@EJB
	private ICategoriaEstacionDao iCategoriaEstacionDao;

	@Inject
	private AppMain appMain;

	private String condicion;
	private List<TipoEstacionEt> tipoEstaciones;
	private TipoEstacionEt tipoEstacionSeleccionado;
	private CategoriaEstacionEt categoriaEstacionSeleccionado;
	private CategoriaEstacionEt categoriaEstacionSeleccionadoEliminar;

	@Override
	protected void init() {
		inicializarObj();
		buscar();
	}

	public void inicializarObj() {
		tipoEstacionSeleccionado = new TipoEstacionEt();
		categoriaEstacionSeleccionado = new CategoriaEstacionEt();
		categoriaEstacionSeleccionadoEliminar = new CategoriaEstacionEt();
	}

	public void buscar() {
		try {
			tipoEstaciones = iTipoEstacionDao.getTipoEstacionList(condicion);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}
	}

	public void guardar() {
		String mensaje = "";
		try {
			mensaje = validacionGuardar(tipoEstacionSeleccionado);
			if (!mensaje.equals("")) {
				showInfo(mensaje, FacesMessage.SEVERITY_ERROR, null, null);
				return;
			}
			UsuarioEt usuario = appMain.getUsuario();
			iTipoEstacionDao.guardarTipoEstacion(tipoEstacionSeleccionado, usuario);
			showInfo("Información Grabada con Éxito ", FacesMessage.SEVERITY_INFO);
			RequestContext.getCurrentInstance().execute("PF('dialog_07_1').hide();");
			buscar();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}
	}

	public String validacionGuardar(TipoEstacionEt tipoEstacion) {
		String mensaje = "";
		try {
			if (tipoEstacion.getDescripcion() == null) {
				mensaje = "Por favor ingresar descripción";
				return mensaje;
			}
			if (tipoEstacion.getCodigo() == null) {
				mensaje = "Por favor ingresar código";
				return mensaje;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método validacionGuardar " + " " + e.getMessage());
		}
		return mensaje;
	}

	public void guardarCategoria() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			iTipoEstacionDao.guardarTipoEstacion(tipoEstacionSeleccionado, usuario);
			showInfo("Información Grabada con Éxito ", FacesMessage.SEVERITY_INFO);
			RequestContext.getCurrentInstance().execute("PF('dialog_07_2').hide();");
			buscar();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}
	}

	public void nuevo() {
		tipoEstacionSeleccionado = new TipoEstacionEt();
		tipoEstacionSeleccionado.setTipoCategoriaEstacion(new ArrayList<>());
	}

	public void modificar(TipoEstacionEt tipoEstacion) {
		tipoEstacionSeleccionado = tipoEstacion;
	}

	public List<CategoriaEstacionEt> getCategoriaEstacionList() {
		List<CategoriaEstacionEt> categoriaEstacion = new ArrayList<>();
		try {
			categoriaEstacion = iCategoriaEstacionDao.getCategoriaEstacionList(null);
			return categoriaEstacion;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getCategoriaEstacionList " + " " + e.getMessage());
		}
		return categoriaEstacion;
	}

	public void onTransfer() {
		try {
			if (categoriaEstacionSeleccionado != null) {

				if (!tipoEstacionSeleccionado.getTipoCategoriaEstacion().isEmpty()) {
					for (int i = 0; i < tipoEstacionSeleccionado.getTipoCategoriaEstacion().size(); i++) {
						if (tipoEstacionSeleccionado.getTipoCategoriaEstacion().get(i).getCategoriaEstacion()
								.getCodigo().equals(categoriaEstacionSeleccionado.getCodigo())) {
							showInfo("Se encuentra agregado ", FacesMessage.SEVERITY_INFO);
							return;
						}
					}
				}
				TipoCategoriaEstacionEt tipoCategoriaEstacion = new TipoCategoriaEstacionEt();
				tipoCategoriaEstacion.setCategoriaEstacion(categoriaEstacionSeleccionado);
				tipoCategoriaEstacion.setTipoEstacion(tipoEstacionSeleccionado);
				tipoEstacionSeleccionado.getTipoCategoriaEstacion().add(tipoCategoriaEstacion);
				categoriaEstacionSeleccionado = new CategoriaEstacionEt();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}
	}

	public void onRemove() {
		try {
			if (categoriaEstacionSeleccionadoEliminar != null) {
				for (int i = 0; i < tipoEstacionSeleccionado.getTipoCategoriaEstacion().size(); i++) {
					if (categoriaEstacionSeleccionadoEliminar.getCodigo().equals(
							tipoEstacionSeleccionado.getTipoCategoriaEstacion().get(i).getTipoEstacion().getCodigo())) {
						tipoEstacionSeleccionado.getTipoCategoriaEstacion()
								.remove(tipoEstacionSeleccionado.getTipoCategoriaEstacion().get(i));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
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

	public List<TipoEstacionEt> getTipoEstaciones() {
		return tipoEstaciones;
	}

	public void setTipoEstaciones(List<TipoEstacionEt> tipoEstaciones) {
		this.tipoEstaciones = tipoEstaciones;
	}

	public TipoEstacionEt getTipoEstacionSeleccionado() {
		return tipoEstacionSeleccionado;
	}

	public void setTipoEstacionSeleccionado(TipoEstacionEt tipoEstacionSeleccionado) {
		this.tipoEstacionSeleccionado = tipoEstacionSeleccionado;
	}

	public CategoriaEstacionEt getCategoriaEstacionSeleccionado() {
		return categoriaEstacionSeleccionado;
	}

	public void setCategoriaEstacionSeleccionado(CategoriaEstacionEt categoriaEstacionSeleccionado) {
		this.categoriaEstacionSeleccionado = categoriaEstacionSeleccionado;
	}

	public CategoriaEstacionEt getCategoriaEstacionSeleccionadoEliminar() {
		return categoriaEstacionSeleccionadoEliminar;
	}

	public void setCategoriaEstacionSeleccionadoEliminar(CategoriaEstacionEt categoriaEstacionSeleccionadoEliminar) {
		this.categoriaEstacionSeleccionadoEliminar = categoriaEstacionSeleccionadoEliminar;
	}

	@Override
	protected void onDestroy() {
		iTipoEstacionDao.remove();
		iCategoriaEstacionDao.remove();
	}

}
