package com.primax.bean.rs;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.UsuarioBean;
import com.primax.bean.vs.base.BaseBean;
import com.primax.enm.gen.PersonaEnum;
import com.primax.enm.gen.RutaFileEnum;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.gen.PersonaEt;
import com.primax.jpa.param.ZonaUsuarioEt;
import com.primax.jpa.sec.RolEt;
import com.primax.jpa.sec.RolUsuarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IGeneralUtilsDao;
import com.primax.web.sec.Encoder;

@ManagedBean(name = "usuarioCtrl")
@ViewScoped
public class UsuarioController extends BaseBean implements Serializable {

	private static final long serialVersionUID = -2539898989257877194L;

	@EJB
	private IGeneralUtilsDao iGeneralUtilsDao;

	@Inject
	private UsuarioBean usuarioBean;

	@Inject
	private AppMain appMain;

	private String textoBusquedaUsuario;

	public AppMain getAppMain() {
		return appMain;
	}

	public void setAppMain(AppMain appMain) {
		this.appMain = appMain;
	}

	public UsuarioBean getUsuarioBean() {
		return usuarioBean;
	}

	public void setUsuarioBean(UsuarioBean usuarioBean) {
		this.usuarioBean = usuarioBean;
	}

	public String getTextoBusquedaUsuario() {
		return textoBusquedaUsuario;
	}

	public void setTextoBusquedaUsuario(String textoBusquedaUsuario) {
		this.textoBusquedaUsuario = textoBusquedaUsuario;
	}

	public void buscar() {
		usuarioBean.getUsuarioBusqueda(textoBusquedaUsuario);
	}

	public void usuarioRol(UsuarioEt usuarioSeleccionado) {
		this.usuarioBean.setUsuarioSeleccionado(usuarioSeleccionado);
		usuarioBean.setRolesSeleccionados(new ArrayList<RolEt>());
		usuarioBean.setLlaveComprobacion(
				Encoder.desencriptar(Encoder.strLlaveCifrado, usuarioSeleccionado.getPwdUsuario()));
		for (RolUsuarioEt roles : usuarioSeleccionado.getRolesUsario()) {
			usuarioBean.getRolesSeleccionados().add(roles.getRol());
		}
		usuarioBean.setOkPolSeg(true);
		if (usuarioSeleccionado.getPersonaUsuario().getPersonaImagen() != null) {

			String pathProyecto = RutaFileEnum.RUTA_PROYECTO_DEPLOYED.getDescripcion();
			String pathImagenTemp = RutaFileEnum.RUTA_IMAGEN_TEMPORAL.getDescripcion();

			iGeneralUtilsDao.creaRuta(usuarioSeleccionado.getIdUsuario(), pathProyecto + File.separatorChar
					+ pathImagenTemp + File.separatorChar + PersonaEnum.USUARIO.getDescripcion());

			usuarioSeleccionado.getPersonaUsuario().getPersonaImagen()
					.setImgPath(iGeneralUtilsDao.guardaImagenTemporal(
							usuarioSeleccionado.getPersonaUsuario().getPersonaImagen().getImgUsuario(), pathProyecto,
							PersonaEnum.USUARIO.getDescripcion(), usuarioSeleccionado.getIdUsuario(),
							usuarioSeleccionado.getPersonaUsuario().getPersonaImagen().getNombreImagen(), true));

		}
		if (usuarioSeleccionado.getZonaUsuario() != null && !usuarioSeleccionado.getZonaUsuario().isEmpty()) {
			usuarioBean.setZonaSeleccionadas(new ArrayList<>());
			for (ZonaUsuarioEt zonaU : usuarioSeleccionado.getZonaUsuario()) {
				usuarioBean.getZonaSeleccionadas().add(zonaU.getZona());
			}
		}
	}

	public void nuevoUsuario() {
		usuarioBean.setUsuarioSeleccionado(new UsuarioEt());
		usuarioBean.getUsuarioSeleccionado().setPersonaUsuario(new PersonaEt());
		usuarioBean.getUsuarioSeleccionado().setRolesUsario(new ArrayList<RolUsuarioEt>());
	}

	public void validatePwd(AjaxBehaviorEvent event) {

		validarPwd(event);

		if (!usuarioBean.getLlaveComprobacion().equals(usuarioBean.getLlaveInicial())) {
			usuarioBean.setPwdValida(false);
			usuarioBean.getUsuarioSeleccionado().setPwdUsuario(null);
			showInfo("Claves no coinciden", FacesMessage.SEVERITY_ERROR);
		}
	}

	public void validarPwd(AjaxBehaviorEvent event) {
		if (usuarioBean.getLlaveInicial().length() >= usuarioBean.getPoliticaSeg().getLongitudMinContrasena()
				&& usuarioBean.getLlaveInicial().length() <= usuarioBean.getPoliticaSeg().getLongitudMaxContrasena()) {
			usuarioBean.setOkPolSeg(true);
		} else {
			usuarioBean.setOkPolSeg(false);
			showInfo("La clave no cumple con la longitud necesaria", FacesMessage.SEVERITY_ERROR);
			return;
		}

		Pattern r = Pattern.compile(usuarioBean.getPolRegex());
		Matcher m = r.matcher(usuarioBean.getLlaveInicial());
		if (!m.find()) {
			showInfo("La clave no cumple con las condiciones de seguridad m�nima necesaria",
					FacesMessage.SEVERITY_ERROR);
			usuarioBean.setOkPolSeg(false);
			return;
		} else {
			usuarioBean.setOkPolSeg(true);
		}
	}

	public void validateUser(AjaxBehaviorEvent event) {
		usuarioBean.validateUser(usuarioBean.getUsuarioSeleccionado().getNombreUsuario());
		if (usuarioBean.getUsuarioSeleccionado().getNombreUsuario().length() >= usuarioBean.getPoliticaSeg()
				.getLongitudNombreUsuario()) {
			usuarioBean.setOkPolSeg(true);
		} else {
			usuarioBean.setOkPolSeg(false);
			showInfo("Notificaci�n", FacesMessage.SEVERITY_ERROR, null,
					"El nombre de usuario no cumple con la longitud m�nima de seguridad");
		}
	}

	public void guardar() {

		if (!usuarioBean.isOkPolSeg()) {
			showInfo("Notificaci�n", FacesMessage.SEVERITY_ERROR, null,
					"El usuario no puede ser creado, no cumple con los datos requeridos");
			return;
		}

		if (usuarioBean.getUsuarioSeleccionado().getPersonaUsuario().getIdPersona() == null) {
			showInfo("Notificaci�n", FacesMessage.SEVERITY_ERROR, null,
					"No se puede crear el usuario sin seleccionar la persona");
			return;
		}

		if (usuarioBean.getUsuarioSeleccionado().getRolesUsario().isEmpty()) {
			showInfo("Notificaci�n", FacesMessage.SEVERITY_ERROR, null, "Debe seleccionar al menos un rol");
			return;
		}
		try {
			usuarioBean.guardarUsuario();
		} catch (EntidadNoEncontradaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		RequestContext.getCurrentInstance().execute("PF('dialog_04').hide()");
	}

	public void eliminaRol(RolUsuarioEt rolUsuario) {
		rolUsuario.setEstado(EstadoEnum.INA);
		usuarioBean.getUsuarioSeleccionado().getRolesUsario().remove(rolUsuario);
		usuarioBean.getRolesEliminados().remove(rolUsuario);
		usuarioBean.getRolesEliminados().add(rolUsuario);
	}

	public void guardarCambio() {
		usuarioBean.addToUserRoles();
	}

	public void limpiarListaSeleccionados() {
		usuarioBean.setRolesSeleccionados(new ArrayList<RolEt>());
	}

	@Override
	protected void onDestroy() {

	}

	@Override
	public void init() {

	}
}
