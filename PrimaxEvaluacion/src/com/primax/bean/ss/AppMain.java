package com.primax.bean.ss;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

import com.primax.bean.vs.base.BaseBean;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.enm.gen.PersonaEnum;
import com.primax.enm.gen.RutaFileEnum;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.sec.RolMenuEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IGeneralUtilsDao;
import com.primax.srv.idao.IMenuEtDao;
import com.primax.srv.idao.IUsuarioDao;
import com.primax.web.sec.Encoder;

@Named("appMain")
@SessionScoped
public class AppMain extends BaseBean implements Serializable {

	private static final long serialVersionUID = 2849418405250763181L;

	@EJB
	private IMenuEtDao menuDao;
	@EJB
	private IUsuarioDao usuarioDao;
	@EJB
	private IGeneralUtilsDao iGeneralUtilsDao;

	private UsuarioEt usuario;
	private RolMenuEt menuSeleccionado;
	private RolMenuEt moduloSeleccionado;
	private List<UsuarioEt> usuariosResponsables;
	private List<RolMenuEt> modulos;
	private int errLog;
	private MenuModel model;

	@Override
	public void onDestroy() {
		System.out.println("end :" + this.toString());
		menuDao.remove();
		usuarioDao.remove();
	}

	public void actualizarClave() {
		try {
			usuarioDao.actualizar(usuario);
			showInfo("Contaseña actualizada", FacesMessage.SEVERITY_INFO);
		} catch (EntidadNoGrabadaException e) {
			showInfo("Se produjo un error", FacesMessage.SEVERITY_ERROR);
			e.printStackTrace();
		}
	}

	public void setClave(String clave) {
		String pwd = Encoder.encriptar(Encoder.strLlaveCifrado, clave);
		usuario.setPwdUsuario(pwd);
	}

	public String getClave() {
		String clave = Encoder.desencriptar(Encoder.strLlaveCifrado, usuario.getPwdUsuario());
		return clave;
	}

	@Override
	public void init() {
		System.out.println("init :" + this.toString());
		errLog = 0;
		model = new DefaultMenuModel();
	}

	public void killSession() {
		getContextE().invalidateSession();
	}

	public void logOut() {
		killSession();
		String route = getContextE().getApplicationContextPath();
		route += "/pages/main.jsf";
		try {
			getContextE().redirect(route);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void userIdle() {
		killSession();
		String route = getContextE().getApplicationContextPath();
		route += "/index.html";
		try {
			getContextE().redirect(route);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private StreamedContent download;

	public UsuarioEt getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioEt usuario) {
		this.usuario = usuarioDao.getUsuarioId(usuario.getIdUsuario());

		if (usuario != null) {
			usuario.setUltimoAcceso(new Date());
			if (usuario.getPersonaUsuario().getPersonaImagen() != null) {
				String pathProyecto = RutaFileEnum.RUTA_PROYECTO_DEPLOYED.getDescripcion();
				String pathImagenTemp = RutaFileEnum.RUTA_IMAGEN_TEMPORAL.getDescripcion();

				iGeneralUtilsDao.creaRuta(usuario.getIdUsuario(), pathProyecto + File.separatorChar + pathImagenTemp
						+ File.separatorChar + PersonaEnum.USUARIO.getDescripcion());

				usuario.getPersonaUsuario().getPersonaImagen()
						.setImgPath(iGeneralUtilsDao.guardaImagenTemporal(
								usuario.getPersonaUsuario().getPersonaImagen().getImgUsuario(), pathProyecto,
								PersonaEnum.USUARIO.getDescripcion(), usuario.getIdUsuario(),
								usuario.getPersonaUsuario().getPersonaImagen().getNombreImagen(), true));

			}
			try {
				usuarioDao.actualizar(usuario);
			} catch (EntidadNoGrabadaException e) {
				e.printStackTrace();
			}
		}

	}

	public List<RolMenuEt> getMenues() {
		if (moduloSeleccionado == null)
			getModulos();
		return moduloSeleccionado.getRolMenuRec();
	}

	public List<RolMenuEt> getModulos() {
		if (modulos == null) {
			IMenuEtDao md_2 = EJB(EnumNaming.IMenuEtDao);
			modulos = md_2.getMenuModuloUsuario(usuario);
			md_2.remove();
		}
		if (!modulos.isEmpty() && moduloSeleccionado == null) {
			moduloSeleccionado = modulos.get(0);
		}
		return modulos;
	}

	public RolMenuEt getMenuSeleccionado() {
		return menuSeleccionado;
	}

	public void setMenuSeleccionado(RolMenuEt menuSeleccionado) {
		this.menuSeleccionado = menuSeleccionado;
	}

	public void fijarMenuAccesos(RolMenuEt rolMenu) {
		this.menuSeleccionado = rolMenu;
	}

	public boolean compruebaAccesoMenu(String URI) {
		menuDao = EJB(EnumNaming.IMenuEtDao);
		boolean ok = menuDao.verificaMenu(URI, usuario);
		return ok;
	}

	public StreamedContent getDownload() {
		return download;
	}

	public void setDownload(StreamedContent download) {
		this.download = download;
	}

	public List<UsuarioEt> getUsuariosResponsables() {
		return usuariosResponsables;
	}

	public void setUsuariosResponsables(List<UsuarioEt> usuariosResponsables) {
		this.usuariosResponsables = usuariosResponsables;
	}

	public RolMenuEt getModuloSeleccionado() {
		return moduloSeleccionado;
	}

	public void setModuloSeleccionado(RolMenuEt moduloSeleccionado) {
		this.moduloSeleccionado = moduloSeleccionado;
	}

	public int getErrLog() {
		return errLog;
	}

	public void setErrLog(int errLog) {
		this.errLog = errLog;
	}

	public void fileDownload(InputStream file, String fileName) {
		try {
			byte[] tempFile = IOUtils.toByteArray(file);
			download = new DefaultStreamedContent(new ByteArrayInputStream(tempFile),
					FacesContext.getCurrentInstance().getExternalContext().getMimeType(fileName), fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public void fileDownloadPath(Long idCheckList, String fileName) {
		try {
			String pathServer = RutaFileEnum.RUTA_CONTROL_INTERNO.getDescripcion();
			String pathImg = pathServer + File.separatorChar + idCheckList + File.separatorChar + fileName;
			InputStream file = getImg(pathImg);
			byte[] tempFile = IOUtils.toByteArray(file);
			download = new DefaultStreamedContent(new ByteArrayInputStream(tempFile),
					FacesContext.getCurrentInstance().getExternalContext().getMimeType(fileName), fileName);
		} catch (IOException e) {
			e.printStackTrace();
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

	public void fileDownloadByte(byte[] file, String fileName) {
		download = new DefaultStreamedContent(new ByteArrayInputStream(file),
				FacesContext.getCurrentInstance().getExternalContext().getMimeType(fileName), fileName);
	}

	public String formatoFecha(Date fecha, String formato) {
		return new SimpleDateFormat(formato).format(fecha);
	}

	public MenuModel getModel() {
		return model;
	}

	public void setModel(MenuModel model) {
		this.model = model;
	}

}
