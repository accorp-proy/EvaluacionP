package com.primax.bean.vs;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;

import com.primax.bean.as.WebAppUtil;
import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.enm.gen.PersonaEnum;
import com.primax.enm.gen.RutaFileEnum;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.gen.PersonaEt;
import com.primax.jpa.param.ZonaEt;
import com.primax.jpa.sec.PersonaImagenEt;
import com.primax.jpa.sec.PoliticaSeguridadEt;
import com.primax.jpa.sec.RolEt;
import com.primax.jpa.sec.RolUsuarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.jpa.param.ZonaUsuarioEt;
import com.primax.srv.idao.IGeneralUtilsDao;
import com.primax.srv.idao.IPersonaDao;
import com.primax.srv.idao.IPoliticaSeguridadDao;
import com.primax.srv.idao.IResponsableDao;
import com.primax.srv.idao.IUsuarioDao;
import com.primax.srv.idao.IZonaDao;
import com.primax.srv.idao.IZonaUsuarioDao;
import com.primax.util.cons.StaticParameter;
import com.primax.web.sec.Encoder;

@Named("UsuarioBn")
@ViewScoped
public class UsuarioBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 7311247601199842130L;

	@EJB
	private IZonaDao iZonaDao;
	@EJB
	private IUsuarioDao iUsuarioDao;
	@EJB
	private IPersonaDao iPersonaDao;
	@EJB
	private IZonaUsuarioDao iZonaUsuarioDao;
	@EJB
	private IResponsableDao iResponsableDao;
	@EJB
	private IGeneralUtilsDao iGeneralUtilsDao;
	@EJB
	private IPoliticaSeguridadDao iPoliticaSeguridadDao;

	@Inject
	private AppMain appMain;

	private UsuarioEt usuarioSeleccionado;

	private List<UsuarioEt> usuarios;

	private List<RolEt> rolesSeleccionados;

	private List<RolUsuarioEt> rolesEliminados;

	private String llaveComprobacion;

	private String llaveInicial;

	private boolean isPwdValida;

	private PoliticaSeguridadEt politicaSeg;

	private String PolRegex;

	private String msgRx;

	private String busquedaPersona;

	private List<PersonaEt> listaPersonas;

	private List<ZonaEt> zonaSeleccionadas;

	private PersonaEt personaSeleccionada = new PersonaEt();

	private boolean okPolSeg;

	public void init() {
		usuarios = iUsuarioDao.getUsuariosByCondicion(null);
		rolesEliminados = new ArrayList<>();
		usuarioSeleccionado = new UsuarioEt();
		usuarioSeleccionado.setZonaUsuario(new ArrayList<>());
		okPolSeg = false;
		politicaSeg = iPoliticaSeguridadDao.getpoliticaSeguridad();
		setPolicy(politicaSeg);
		zonaSeleccionadas = new ArrayList<>();
	}

	public void setPolicy(PoliticaSeguridadEt policy) {
		StringBuilder msgRegx = new StringBuilder();
		StringBuilder polReg = new StringBuilder("(");
		if (policy.getMayusculas()) {
			polReg.append(StaticParameter.Regex_Upper.getDescription());
			msgRegx.append("Mayusculas ");
		}
		if (policy.getMinusculas()) {
			polReg.append(StaticParameter.Regex_Lower.getDescription());
			msgRegx.append("Minusculas ");
		}
		if (policy.getSimbolos()) {
			polReg.append(StaticParameter.Regex_Symbol.getDescription());
			msgRegx.append("Simbolos ");
		}
		if (policy.getNumeros()) {
			polReg.append(StaticParameter.Regex_Digit.getDescription());
			msgRegx.append("Numeros");
		}
		polReg.append(".{" + policy.getLongitudMinContrasena() + "," + policy.getLongitudMaxContrasena() + "})");

		msgRx = msgRegx.toString().replace(" ", ",");
		msgRegx = new StringBuilder(msgRx);
		msgRegx.append(" con una longitud de: ");
		msgRegx.append(policy.getLongitudMinContrasena());
		msgRegx.append(" a ");
		msgRegx.append(policy.getLongitudMaxContrasena());
		msgRegx.append(" caracteres ");
		msgRx = msgRegx.toString();

		PolRegex = polReg.toString();

		if (!policy.getMayusculas() && !policy.getMinusculas() && !policy.getNumeros() && !policy.getSimbolos()) {
			PolRegex = StaticParameter.Regex_Any.getDescription();
		}

	}

	public void subirImagen(FileUploadEvent event) {
		try {
			String pathProyecto = RutaFileEnum.RUTA_PROYECTO_DEPLOYED.getDescripcion();
			String pathImagenTemp = RutaFileEnum.RUTA_IMAGEN_TEMPORAL.getDescripcion();

			if (usuarioSeleccionado.getPersonaUsuario().getPersonaImagen() == null) {
				PersonaImagenEt imagen = new PersonaImagenEt();
				imagen.setPersona(usuarioSeleccionado.getPersonaUsuario());
				usuarioSeleccionado.getPersonaUsuario().setPersonaImagen(imagen);

			}
			usuarioSeleccionado.getPersonaUsuario().getPersonaImagen().setImgUsuario(event.getFile().getContents());
			usuarioSeleccionado.getPersonaUsuario().getPersonaImagen().setNombreImagen(event.getFile().getFileName());
			iGeneralUtilsDao.creaRuta(usuarioSeleccionado.getIdUsuario(), pathProyecto + File.separatorChar
					+ pathImagenTemp + File.separatorChar + PersonaEnum.USUARIO.getDescripcion());
			String rutaTmp = iGeneralUtilsDao.guardaImagenTemporal(event.getFile().getContents(),
					WebAppUtil.getWebPath(), PersonaEnum.USUARIO.getDescripcion(), usuarioSeleccionado.getIdUsuario(),
					event.getFile().getFileName(), true);
			System.out.println("rutaTmp " + rutaTmp);
			usuarioSeleccionado.getPersonaUsuario().getPersonaImagen().setImgPath(rutaTmp);
		} catch (Exception e) {
			showInfo("Problemas al subir Imagen", FacesMessage.SEVERITY_ERROR);
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

	public List<ZonaEt> getZonaList() {
		List<ZonaEt> zonas = new ArrayList<>();
		try {
			zonas = iZonaDao.getZonaList(null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getZonaList " + " " + e.getMessage());
		}
		return zonas;
	}

	public PersonaEt getPersonaSeleccionada() {
		return personaSeleccionada;
	}

	public void setPersonaSeleccionada(PersonaEt personaSeleccionada) {
		this.personaSeleccionada = personaSeleccionada;
	}

	public List<UsuarioEt> getUsuarioBusqueda(String condicion) {
		usuarios = iUsuarioDao.getUsuariosByCondicion(condicion);
		return usuarios;
	}

	public void modificaUsuario(UsuarioEt usuario) {
		this.usuarioSeleccionado = usuario;
		

	}

	public UsuarioEt getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	public void setUsuarioSeleccionado(UsuarioEt usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
	}

	public List<UsuarioEt> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioEt> usuarios) {
		this.usuarios = usuarios;
	}

	public String getBusquedaPersona() {
		return busquedaPersona;
	}

	public void setBusquedaPersona(String busquedaPersona) {
		this.busquedaPersona = busquedaPersona;
	}

	public List<PersonaEt> getListaPersonas() {
		return listaPersonas;
	}

	public void setListaPersonas(List<PersonaEt> listaPersonas) {
		this.listaPersonas = listaPersonas;
	}

	public void guardarUsuario() throws EntidadNoEncontradaException {
		usuarioSeleccionado.setUsuarioRegistra(appMain.getUsuario());

		if (rolesEliminados != null && !rolesEliminados.isEmpty()) {
			usuarioSeleccionado.getRolesUsario().addAll(rolesEliminados);
		}
		try {
			if (usuarioSeleccionado.getZonaUsuario() != null && !usuarioSeleccionado.getZonaUsuario().isEmpty()) {
				usuarioSeleccionado.setAccesoZona(true);
				for (ZonaUsuarioEt zonaUsuario : usuarioSeleccionado.getZonaUsuario()) {
					zonaUsuario.setEstado(EstadoEnum.INA);
					zonaUsuario.setUsuario(usuarioSeleccionado);
					iZonaUsuarioDao.guardarZonaUsuario(zonaUsuario, usuarioSeleccionado);
				}
			}
			if (usuarioSeleccionado.getZonaUsuario() != null && !zonaSeleccionadas.isEmpty()) {
				usuarioSeleccionado.setAccesoZona(true);
			}
			iUsuarioDao.crearUsuarioSistema(usuarioSeleccionado);
			showInfo("Notificación", FacesMessage.SEVERITY_INFO, null, "Datos del usuario guardado");
			for (ZonaEt zona : zonaSeleccionadas) {
				ZonaUsuarioEt zonaUsuario = null;
				zonaUsuario = iZonaUsuarioDao.getZonaExiste(zona, usuarioSeleccionado);
				if (zonaUsuario == null) {
					zonaUsuario = new ZonaUsuarioEt();
					zonaUsuario.setZona(zona);
					zonaUsuario.setUsuario(usuarioSeleccionado);
				}
				zonaUsuario.setEstado(EstadoEnum.ACT);
				iZonaUsuarioDao.guardarZonaUsuario(zonaUsuario, usuarioSeleccionado);
			}
			init();
		} catch (

		EntidadNoGrabadaException e) {
			e.printStackTrace();
			showInfo("Notificación", FacesMessage.SEVERITY_ERROR, null, "Se produjo un error al guardar el usuario");
		}

	}

	public List<RolEt> getRolesSeleccionados() {
		return rolesSeleccionados;
	}

	public void setRolesSeleccionados(List<RolEt> rolesSeleccionados) {
		this.rolesSeleccionados = rolesSeleccionados;
	}

	public void setSaltedPwd(String pwd) {
		this.llaveInicial = pwd;
		usuarioSeleccionado.setPwdUsuario(Encoder.encriptar(Encoder.strLlaveCifrado, pwd));
	}

	public String getSaltedPwd() {
		if (usuarioSeleccionado == null)
			return null;
		llaveInicial = Encoder.desencriptar(Encoder.strLlaveCifrado, usuarioSeleccionado.getPwdUsuario());
		return llaveInicial;
	}

	public String getLlaveComprobacion() {
		return llaveComprobacion;
	}

	public void setLlaveComprobacion(String llaveComprobacion) {
		this.llaveComprobacion = llaveComprobacion;
	}

	public String getLlaveInicial() {
		return llaveInicial;
	}

	public void setLlaveInicial(String llaveInicial) {
		this.llaveInicial = llaveInicial;
	}

	public boolean isPwdValida() {
		return isPwdValida;
	}

	public void setPwdValida(boolean isPwdValida) {
		this.isPwdValida = isPwdValida;
	}

	public List<RolUsuarioEt> getRolesEliminados() {
		return rolesEliminados;
	}

	public void setRolesEliminados(List<RolUsuarioEt> rolesEliminados) {
		this.rolesEliminados = rolesEliminados;
	}

	public void addToUserRoles() {
		if (usuarioSeleccionado != null) {
			List<RolUsuarioEt> roles = new ArrayList<>();
			for (RolEt rol : rolesSeleccionados) {
				boolean exist = false;
				for (RolUsuarioEt rolesUsuario : usuarioSeleccionado.getRolesUsario()) {
					if (rolesUsuario.getRol().equals(rol)) {
						exist = true;
						break;
					}
				}
				if (!exist) {
					RolUsuarioEt rolNuevo = new RolUsuarioEt();
					rolNuevo.setRol(rol);
					rolNuevo.setUsuario(usuarioSeleccionado);
					roles.add(rolNuevo);
				}
			}
			usuarioSeleccionado.getRolesUsario().removeAll(roles);
			usuarioSeleccionado.getRolesUsario().addAll(roles);
		}
	}

	public PoliticaSeguridadEt getPoliticaSeg() {
		return politicaSeg;
	}

	public void setPoliticaSeg(PoliticaSeguridadEt politicaSeg) {
		this.politicaSeg = politicaSeg;
	}

	public String getPolRegex() {
		return PolRegex;
	}

	public void setPolRegex(String polRegex) {
		PolRegex = polRegex;
	}

	public void validateUser(String userName) {
		UsuarioEt exist = iUsuarioDao.comprobarExisteUsuario(userName);
		if (!usuarioSeleccionado.equals(exist) && exist != null)
			showInfo("El usuario ya se encuentra registrado, ingrese otro", FacesMessage.SEVERITY_INFO);
	}

	public String getMsgRx() {
		return msgRx;
	}

	public void setMsgRx(String msgRx) {
		this.msgRx = msgRx;
	}

	public AppMain getAppMain() {
		return appMain;
	}

	public void setAppMain(AppMain appMain) {
		this.appMain = appMain;
	}

	public void nuevaBusquedaPersona() {
		busquedaPersona = "";
		buscarPersona();
	}

	public void buscarPersona() {
		listaPersonas = iPersonaDao.getPersonasUsuario(busquedaPersona);
	}

	public void cargarPersona() {
		usuarioSeleccionado.setPersonaUsuario(personaSeleccionada);
		// cargarImagen();
	}

	public void cargaPorCedula() {
		if (usuarioSeleccionado.getPersonaUsuario().getIdentificacionPersona() != null
				&& !usuarioSeleccionado.getPersonaUsuario().getIdentificacionPersona().isEmpty()
				&& !usuarioSeleccionado.getPersonaUsuario().getIdentificacionPersona().equals("_________-_")) {
			usuarioSeleccionado.getPersonaUsuario().setIdentificacionPersona(
					usuarioSeleccionado.getPersonaUsuario().getIdentificacionPersona().replace("-", ""));
			usuarioSeleccionado.setPersonaUsuario(iPersonaDao
					.getPersonasPorCedula(usuarioSeleccionado.getPersonaUsuario().getIdentificacionPersona()));
		}
	}

	public boolean isOkPolSeg() {
		return okPolSeg;
	}

	public void setOkPolSeg(boolean okPolSeg) {
		this.okPolSeg = okPolSeg;
	}

	public void cargarImagen() {
		if (usuarioSeleccionado.getPersonaUsuario().getPersonaImagen() != null) {

			String pathProyecto = RutaFileEnum.RUTA_PROYECTO_DEPLOYED.getDescripcion();
			String pathImagenTemp = RutaFileEnum.RUTA_IMAGEN_TEMPORAL.getDescripcion();

			iGeneralUtilsDao.creaRuta(usuarioSeleccionado.getPersonaUsuario().getIdPersona(), pathProyecto
					+ File.separatorChar + pathImagenTemp + File.separatorChar + PersonaEnum.USUARIO.getDescripcion());

			usuarioSeleccionado.getPersonaUsuario().getPersonaImagen().setImgPath(iGeneralUtilsDao.guardaImagenTemporal(
					usuarioSeleccionado.getPersonaUsuario().getPersonaImagen().getImgUsuario(), pathProyecto,
					PersonaEnum.USUARIO.getDescripcion(), usuarioSeleccionado.getPersonaUsuario().getIdPersona(),
					usuarioSeleccionado.getPersonaUsuario().getPersonaImagen().getNombreImagen(), true));

		}
	}

	public List<ZonaEt> getZonaSeleccionadas() {
		return zonaSeleccionadas;
	}

	public void setZonaSeleccionadas(List<ZonaEt> zonaSeleccionadas) {
		this.zonaSeleccionadas = zonaSeleccionadas;
	}

	@Override
	public void onDestroy() {
		iZonaDao.remove();
		iUsuarioDao.remove();
		iPersonaDao.remove();
		iResponsableDao.remove();
		iPoliticaSeguridadDao.remove();
	}

}
