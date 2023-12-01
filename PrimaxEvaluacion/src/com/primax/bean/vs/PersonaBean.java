package com.primax.bean.vs;

import java.io.File;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.enm.gen.PersonaEnum;
import com.primax.enm.gen.RutaFileEnum;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.gen.PersonaEt;
import com.primax.jpa.sec.PersonaImagenEt;
import com.primax.srv.idao.IGeneralUtilsDao;
import com.primax.srv.idao.IPersonaDao;
import com.primax.srv.idao.IResponsableDao;

@Named("personaBn")
@ViewScoped
public class PersonaBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = -73098689965679881L;

	@EJB
	private IPersonaDao personaDao;
	@EJB
	private IResponsableDao iResponsableDao;
	@EJB
	private IGeneralUtilsDao iGeneralUtilsDao;

	@Inject
	private AppMain appMain;

	private PersonaEt personaSeleccionada;

	private String textoBusqueda;

	private List<PersonaEt> personas;

	@Override
	public void init() {
		personaSeleccionada = null;
		buscar();
	}

	public void buscar() {
		personas = personaDao.getPersonasCondicion(textoBusqueda);
	}

	public void nuevo() {
		personaSeleccionada = new PersonaEt();
	}

	public void validarPersona() {
		if (personaSeleccionada.getIdentificacionPersona() != null
				&& !personaSeleccionada.getIdentificacionPersona().isEmpty()) {
			PersonaEt per = personaDao.getPersonasPorCedula(personaSeleccionada.getIdentificacionPersona());
			if (per != null) {
				personaSeleccionada = per;
			}
			cargarImagen();
		}
	}

	public void guardar() {
		try {
			personaDao.guardarPersona(personaSeleccionada, appMain.getUsuario());
			showInfo("Notificación", FacesMessage.SEVERITY_INFO, null, "Dato de persona guardado");
			getRequestContext().execute("PF('dlg_per_01').hide()");
			nuevo();
			buscar();
		} catch (EntidadNoGrabadaException e) {
			showInfo("Alerta", FacesMessage.SEVERITY_ERROR, "msg", "Ocurrio un error al guardar el usuario");
		}
	}

	public String estacion(PersonaEt persona) {
		String estacion = "";
		try {
			estacion = iResponsableDao.getResponsableEstacionS(persona);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método estacion " + " " + e.getMessage());
		}
		return estacion;
	}

	public void modificar(PersonaEt persona) {
		personaSeleccionada = persona;
		cargarImagen();
	}

	@Override
	public void onDestroy() {
		personaDao.remove();
		iResponsableDao.remove();
	}

	public List<PersonaEt> getPersonas() {
		return personas == null ? Collections.emptyList() : personas;
	}

	public PersonaEt getPersonaSeleccionada() {
		return personaSeleccionada;
	}

	public void setPersonaSeleccionada(PersonaEt personaSeleccionada) {
		this.personaSeleccionada = personaSeleccionada;
	}

	public String getTextoBusqueda() {
		return textoBusqueda;
	}

	public void setTextoBusqueda(String textoBusqueda) {
		this.textoBusqueda = textoBusqueda;
	}

	public AppMain getAppMain() {
		return appMain;
	}

	public void setAppMain(AppMain appMain) {
		this.appMain = appMain;
	}

	// =================IMAGEN======================
	public void subirImagen(FileUploadEvent event) {
		try {
			String pathProyecto = RutaFileEnum.RUTA_PROYECTO_DEPLOYED.getDescripcion();
			String pathImagenTemp = RutaFileEnum.RUTA_IMAGEN_TEMPORAL.getDescripcion();

			if (personaSeleccionada.getPersonaImagen() == null) {
				PersonaImagenEt imagen = new PersonaImagenEt();
				imagen.setPersona(personaSeleccionada);
				personaSeleccionada.setPersonaImagen(imagen);

			}
			personaSeleccionada.getPersonaImagen().setImgUsuario(event.getFile().getContents());
			personaSeleccionada.getPersonaImagen().setNombreImagen(event.getFile().getFileName());
			iGeneralUtilsDao.creaRuta(personaSeleccionada.getIdPersona(), pathProyecto + File.separatorChar
					+ pathImagenTemp + File.separatorChar + PersonaEnum.USUARIO.getDescripcion());
			String rutaTmp = iGeneralUtilsDao.guardaImagenTemporal(event.getFile().getContents(),
					RutaFileEnum.RUTA_PROYECTO_DEPLOYED.getDescripcion(), PersonaEnum.USUARIO.getDescripcion(),
					personaSeleccionada.getIdPersona(), event.getFile().getFileName(), true);
			System.out.println("rutaTmp " + rutaTmp);
			personaSeleccionada.getPersonaImagen().setImgPath(rutaTmp);
		} catch (Exception e) {
			showInfo("Alerta", FacesMessage.SEVERITY_ERROR, null, "Ocurrió un problema al subir la imagen");
		}
	}

	public void cargarImagen() {
		if (personaSeleccionada != null && personaSeleccionada.getPersonaImagen() != null) {
			String pathProyecto = RutaFileEnum.RUTA_PROYECTO_DEPLOYED.getDescripcion();
			String pathImagenTemp = RutaFileEnum.RUTA_IMAGEN_TEMPORAL.getDescripcion();

			iGeneralUtilsDao.creaRuta(personaSeleccionada.getIdPersona(), pathProyecto + File.separatorChar
					+ pathImagenTemp + File.separatorChar + PersonaEnum.USUARIO.getDescripcion());

			personaSeleccionada.getPersonaImagen()
					.setImgPath(iGeneralUtilsDao.guardaImagenTemporal(
							personaSeleccionada.getPersonaImagen().getImgUsuario(), pathProyecto,
							PersonaEnum.USUARIO.getDescripcion(), personaSeleccionada.getIdPersona(),
							personaSeleccionada.getPersonaImagen().getNombreImagen(), true));
		}
	}

}
