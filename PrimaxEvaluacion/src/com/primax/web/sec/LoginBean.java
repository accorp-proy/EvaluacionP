package com.primax.web.sec;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.sec.PoliticaSeguridadEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IPoliticaSeguridadDao;
import com.primax.srv.idao.IUsuarioDao;
import com.primax.srv.imsg.ISrvNotificacion;

@Named("loginBean")
@RequestScoped
public class LoginBean extends BaseBean {

	private String user;

	private String pass;

	private String passRecover;

	@Inject
	private AppMain appMain;

	@EJB
	private ISrvNotificacion notificacion;

	@Override
	public void init() {
		switch (appMain.getErrLog()) {
		case 1:
			getRequestContext().execute("doGrowl('Debe ingresar usuario y contraseña')");
			break;
		case 2:
			getRequestContext().execute("doGrowl('Verifique que su nombre de usuario y contraseña sea el correcto')");
			break;
		}
		appMain.setErrLog(0);
	}

	public void login() {
		try {
			IUsuarioDao iUsuarioDao = EJB(EnumNaming.IUsuarioDao);
			UsuarioEt usuario = iUsuarioDao.validarUsuario(user, Encoder.encriptar(Encoder.strLlaveCifrado, pass));
			if (user == null || pass == null) {
				user = null;
				pass = null;
				appMain.setErrLog(1);
				String route = getContextE().getApplicationContextPath();
				route += "/login.jsf";
				getContextE().redirect(route);
			} else if (usuario != null) {
				HttpSession session = getSession();
				session.setAttribute("username", usuario.getNombreUsuario());
				appMain.setUsuario(usuario);
				usuario.setIntentosLog(0);
				iUsuarioDao.actualizar(usuario);
				String route = getContextE().getApplicationContextPath();
				route += "/pages/main.jsf";
				appMain.setErrLog(0);
				getContextE().redirect(route);
			} else {
				usuario = iUsuarioDao.getUsuarioById(user);
				if (usuario != null) {
					usuario.setIntentosLog(usuario.getIntentosLog() != null ? usuario.getIntentosLog() + 1 : 0);
					IPoliticaSeguridadDao iPoliticaSeguriad = EJB(EnumNaming.IPoliticaSeguridadDao);
					PoliticaSeguridadEt seguridad = iPoliticaSeguriad.getpoliticaSeguridad();
					iPoliticaSeguriad.remove();
					if (seguridad != null) {
						if (seguridad.getIntentosSesion().intValue() == usuario.getIntentosLog().intValue()) {
							usuario.setEstado(EstadoEnum.SUS);
						}
					}
					iUsuarioDao.actualizar(usuario);
				}
				appMain.setErrLog(2);
				user = null;
				pass = null;
				String route = getContextE().getApplicationContextPath();
				route += "/login.jsf";
				getContextE().redirect(route);
			}
			iUsuarioDao.remove();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroy() {

	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public AppMain getAppMain() {
		return appMain;
	}

	public void setAppMain(AppMain appMain) {
		this.appMain = appMain;
	}

	public String getPassRecover() {
		return passRecover;
	}

	public void setPassRecover(String passRecover) {
		this.passRecover = passRecover;
	}

}
