package com.primax.bean.vs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.enm.gen.RutaFileEnum;
import com.primax.enm.gen.UtilEnum;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.NivelColorEt;
import com.primax.jpa.param.NivelEvaluacionDetalleEt;
import com.primax.jpa.param.NivelEvaluacionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IGeneralUtilsDao;
import com.primax.srv.idao.INivelColorDao;
import com.primax.srv.idao.INivelEvaluacionDao;
import com.primax.srv.idao.INivelEvaluacionDetalleDao;

@Named("NivelEvaluacionBn")
@ViewScoped
public class NivelEvaluacionBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private INivelColorDao iNivelColorDao;
	@EJB
	private IGeneralUtilsDao iGeneralUtils0Dao;
	@EJB
	private IGeneralUtilsDao iGeneralUtils1Dao;
	@EJB
	private INivelEvaluacionDao iNivelEvaluacionDao;
	@EJB
	private INivelEvaluacionDetalleDao iNivelEvaluacionDetalleDao;

	@Inject
	private AppMain appMain;

	private String condicion;

	private NivelColorEt nivelColorSeleccionado;
	private List<NivelEvaluacionEt> nivelEvaluaciones;
	private NivelEvaluacionEt nivelEvaluacionSeleccionado;
	private NivelEvaluacionDetalleEt nivelEvaluacionDetalleSeleccionado;

	@Override
	protected void init() {
		inicializarObj();
		buscar();

	}

	public void inicializarObj() {
		nivelEvaluacionSeleccionado = new NivelEvaluacionEt();
		nivelEvaluacionSeleccionado.setNivelEvaluacionDetalle(new ArrayList<>());
	}

	public void buscar() {
		try {
			nivelEvaluaciones = iNivelEvaluacionDao.getNivelEvaluacionList(condicion);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}
	}

	public void guardar() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			iNivelEvaluacionDao.guardarNivelEvaluacion(nivelEvaluacionSeleccionado, usuario);
			showInfo("Información Grabada con Éxito ", FacesMessage.SEVERITY_INFO);
			RequestContext.getCurrentInstance().execute("PF('dialog_16_2').hide();");
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
				nivelEvaluacionDetalleSeleccionado.setColor(nivelColorSeleccionado.getColor());
			}
			iNivelEvaluacionDetalleDao.guardaNivelEvaluacionDetalle(nivelEvaluacionDetalleSeleccionado, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarDetalle " + " " + e.getMessage());
		}
	}

	public void nuevo() {
		nivelEvaluacionSeleccionado = new NivelEvaluacionEt();
	}

	public void modificar(NivelEvaluacionEt nivelEvaluacion) {
		nivelEvaluacionSeleccionado = nivelEvaluacion;
		if (nivelEvaluacionSeleccionado.getNivelEvaluacionDetalle() == null) {
			nivelEvaluacionSeleccionado.setNivelEvaluacionDetalle(new ArrayList<>());
		}
		if (nivelEvaluacion.getNivelEvaluacionDetalle() != null
				&& !nivelEvaluacion.getNivelEvaluacionDetalle().isEmpty()) {
			guardarImagenes(nivelEvaluacion);
		}
	}

	public void modificarNivelEvaluacionDetalle(NivelEvaluacionDetalleEt nivelEvaluacionDetalle) {
		nivelEvaluacionDetalleSeleccionado = nivelEvaluacionDetalle;
	}

	public void anadirProcesoDetalle() {
		Long orden = 1L;
		Date fechaRegistro = UtilEnum.FECHA_REGISTRO.getValue();
		if (!nivelEvaluacionSeleccionado.getNivelEvaluacionDetalle().isEmpty()) {
			orden = (long) nivelEvaluacionSeleccionado.getNivelEvaluacionDetalle().size() + 1;
		}
		NivelEvaluacionDetalleEt nivelEvaluacionDetalle = new NivelEvaluacionDetalleEt();
		nivelEvaluacionDetalle.setOrden(orden);
		nivelEvaluacionDetalle.setFechaRegistro(fechaRegistro);
		nivelEvaluacionDetalle.setNivelEvaluacion(nivelEvaluacionSeleccionado);
		nivelEvaluacionSeleccionado.getNivelEvaluacionDetalle().add(nivelEvaluacionDetalle);

	}

	public void quitarProcesoDetalle(NivelEvaluacionDetalleEt nivelEvaluacionDetalle) {
		Date fechaRegistro = UtilEnum.FECHA_REGISTRO.getValue();
		nivelEvaluacionDetalle.setFechaModificacion(fechaRegistro);
		nivelEvaluacionDetalle.setEstado(EstadoEnum.INA);
		nivelEvaluacionSeleccionado.getNivelEvaluacionDetalle().remove(nivelEvaluacionDetalle);
	}

	public SelectItem[] getEstadosActIna() {
		SelectItem[] items = new SelectItem[2];
		items[0] = new SelectItem(EstadoEnum.ACT, EstadoEnum.ACT.getDescripcion());
		items[1] = new SelectItem(EstadoEnum.INA, EstadoEnum.INA.getDescripcion());
		return items;
	}

	public void upload(FileUploadEvent event) throws EntidadNoGrabadaException {
		String ruta = "";
		String rutaServer = "";
		String nombreArchivo = "";
		try {
			UsuarioEt usuario = appMain.getUsuario();
			Long idNivelEvauacion = nivelEvaluacionSeleccionado.getIdNivelEvaluacion();
			nombreArchivo = idNivelEvauacion + "_" + event.getFile().getFileName();
			nivelEvaluacionDetalleSeleccionado.setImgNombre(nombreArchivo);
			nivelEvaluacionDetalleSeleccionado.setFile(event.getFile().getInputstream());
			if (nombreArchivo.toLowerCase().contains(".png") || nombreArchivo.toLowerCase().contains(".jpg")) {
				rutaServer = iGeneralUtils0Dao.creaRuta(idNivelEvauacion,
						RutaFileEnum.RUTA_NIVEL_EVALUACION.getDescripcion());
				ruta = iGeneralUtils1Dao.creaRuta(idNivelEvauacion,
						RutaFileEnum.RUTA_PROYECTO_DEPLOYED.getDescripcion() + File.separator + "resources"
								+ File.separator + "images" + File.separator + "nivelEvaluacion");
			} else {
				rutaServer = iGeneralUtils0Dao.creaRuta(idNivelEvauacion,
						RutaFileEnum.RUTA_NIVEL_EVALUACION.getDescripcion());
			}
			InputStream inputStreamImg = nivelEvaluacionDetalleSeleccionado.getFile();
			iGeneralUtils0Dao.copyFile(nombreArchivo, inputStreamImg, ruta);
			inputStreamImg = event.getFile().getInputstream();
			iGeneralUtils1Dao.copyFile(nombreArchivo, inputStreamImg, rutaServer);
			FacesMessage msg = new FacesMessage("Satisfactorio! ",
					event.getFile().getFileName() + "  " + "Esta subido Correctamente.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			nivelEvaluacionDetalleSeleccionado.setImgPathServer(rutaServer);
			nivelEvaluacionDetalleSeleccionado.setImgPath(ruta + nombreArchivo);
			iNivelEvaluacionDetalleDao.guardaNivelEvaluacionDetalle(nivelEvaluacionDetalleSeleccionado, usuario);
		} catch (IOException e) {
			FacesMessage msg = new FacesMessage("Error! ",
					event.getFile().getFileName() + "  " + "El archivo no se subio.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			e.printStackTrace();
		}
	}

	public void guardarImagenes(NivelEvaluacionEt nivelEvaluacion) {
		try {
			for (NivelEvaluacionDetalleEt nivelEvaluacionD : nivelEvaluacion.getNivelEvaluacionDetalle()) {
				if (nivelEvaluacionD.getImgPathServer() != null) {
					String pathServerImg = nivelEvaluacionD.getImgPathServer() + nivelEvaluacionD.getImgNombre();
					InputStream inputStreamImg = getImg(pathServerImg);
					String ruta = iGeneralUtils1Dao.creaRuta(
							nivelEvaluacionD.getNivelEvaluacion().getIdNivelEvaluacion(),
							RutaFileEnum.RUTA_PROYECTO_DEPLOYED.getDescripcion() + File.separator + "resources"
									+ File.separator + "images" + File.separator + "nivelEvaluacion");
					iGeneralUtils0Dao.copyFile(nivelEvaluacionD.getImgNombre(), inputStreamImg, ruta);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarImagenes " + " " + e.getMessage());
		}
	}

	public InputStream getImg(String pathImg) {
		String path = pathImg;
		System.out.println(path);
		File arch = new File(path);
		InputStream img = null;
		try {
			img = new FileInputStream(arch);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return img;
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

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public List<NivelEvaluacionEt> getNivelEvaluaciones() {
		return nivelEvaluaciones;
	}

	public void setNivelEvaluaciones(List<NivelEvaluacionEt> nivelEvaluaciones) {
		this.nivelEvaluaciones = nivelEvaluaciones;
	}

	public NivelEvaluacionEt getNivelEvaluacionSeleccionado() {
		return nivelEvaluacionSeleccionado;
	}

	public void setNivelEvaluacionSeleccionado(NivelEvaluacionEt nivelEvaluacionSeleccionado) {
		this.nivelEvaluacionSeleccionado = nivelEvaluacionSeleccionado;
	}

	public NivelEvaluacionDetalleEt getNivelEvaluacionDetalleSeleccionado() {
		return nivelEvaluacionDetalleSeleccionado;
	}

	public void setNivelEvaluacionDetalleSeleccionado(NivelEvaluacionDetalleEt nivelEvaluacionDetalleSeleccionado) {
		this.nivelEvaluacionDetalleSeleccionado = nivelEvaluacionDetalleSeleccionado;
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
		iNivelEvaluacionDao.remove();
		iNivelEvaluacionDetalleDao.remove();
	}

}
