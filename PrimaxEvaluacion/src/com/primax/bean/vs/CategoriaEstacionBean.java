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
import com.primax.jpa.param.TipoEstacionEt;
import com.primax.jpa.param.CategoriaEstacionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.ITipoEstacionDao;
import com.primax.srv.idao.ICategoriaEstacionDao;

@Named("CategoriaEstacionBn")
@ViewScoped
public class CategoriaEstacionBean extends BaseBean implements Serializable {

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
	private TipoEstacionEt tipoEstacionSeleccionado;
	private List<CategoriaEstacionEt> categoriaEstaciones;
	private CategoriaEstacionEt categoriaEstacionSeleccionado;

	@Override
	protected void init() {
		inicializarObj();
		buscar();
	}

	public void inicializarObj() {
		tipoEstacionSeleccionado = new TipoEstacionEt();
		categoriaEstacionSeleccionado = new CategoriaEstacionEt();
	}

	public void buscar() {
		try {
			categoriaEstaciones = iCategoriaEstacionDao.getCategoriaEstacionList(condicion);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}
	}

	public void guardar() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			iCategoriaEstacionDao.guardarSubformatoNegocio(categoriaEstacionSeleccionado, usuario);
			showInfo("Información Grabada con Éxito ", FacesMessage.SEVERITY_INFO);
			RequestContext.getCurrentInstance().execute("PF('dialog_08_1').hide();");
			buscar();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}
	}

	public void nuevo() {
		categoriaEstacionSeleccionado = new CategoriaEstacionEt();
	}

	public void modificar(CategoriaEstacionEt categoriaEstacion) {
		categoriaEstacionSeleccionado = categoriaEstacion;
	}

	public List<TipoEstacionEt> getTipoEstaciones() {
		List<TipoEstacionEt> tipoEstacion = new ArrayList<TipoEstacionEt>();
		try {
			tipoEstacion = iTipoEstacionDao.getTipoEstacionList(null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTipoEstaciones " + " " + e.getMessage());
		}
		return tipoEstacion;
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

	public TipoEstacionEt getTipoEstacionSeleccionado() {
		return tipoEstacionSeleccionado;
	}

	public void setTipoEstacionSeleccionado(TipoEstacionEt tipoEstacionSeleccionado) {
		this.tipoEstacionSeleccionado = tipoEstacionSeleccionado;
	}

	public List<CategoriaEstacionEt> getCategoriaEstaciones() {
		return categoriaEstaciones;
	}

	public void setCategoriaEstaciones(List<CategoriaEstacionEt> categoriaEstaciones) {
		this.categoriaEstaciones = categoriaEstaciones;
	}

	public CategoriaEstacionEt getCategoriaEstacionSeleccionado() {
		return categoriaEstacionSeleccionado;
	}

	public void setCategoriaEstacionSeleccionado(CategoriaEstacionEt categoriaEstacionSeleccionado) {
		this.categoriaEstacionSeleccionado = categoriaEstacionSeleccionado;
	}

	@Override
	protected void onDestroy() {
		iTipoEstacionDao.remove();
		iCategoriaEstacionDao.remove();
	}

}
