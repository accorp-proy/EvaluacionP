package com.primax.web.sec;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.jpa.sec.PoliticaSeguridadEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IPoliticaSeguridadDao;
import com.primax.srv.idao.IUsuarioDao;
import com.primax.util.cons.StaticParameter;

@Named("seguridadCambioContrasena")
@SessionScoped
public class CambioClaveBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = -2918734848250516096L;

	@Inject
	private AppMain appMain;

	@EJB
	private IUsuarioDao iUsuarioDao;

	@EJB
	private IPoliticaSeguridadDao iPoliticaSeguridadDao;

	private UsuarioEt usuario;
	private UsuarioEt cambioUsuario;
	private String contrasenaNueva;
	private String contrasenaConfirma;
	private String llaveInicial;
	private boolean isPwdValida;
	private String llaveComprobacion;
	private PoliticaSeguridadEt politicaSeg;
	private String msgRx;
	private String PolRegex;

	@Override
	public void init() {
		usuario = appMain.getUsuario();
		cambioUsuario = new UsuarioEt();
		politicaSeg = iPoliticaSeguridadDao.getpoliticaSeguridad();
		setPolicy(politicaSeg);
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

	public void guardar() {
		if (llaveComprobacion.equals(llaveInicial)) {

			try {
				iUsuarioDao.guardarNuevaContrasena(usuario);
				showInfo("Exito al guardar", FacesMessage.SEVERITY_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			setPwdValida(false);
			usuario.setPwdUsuario(null);
			showInfo("Claves no coinciden", FacesMessage.SEVERITY_ERROR);
		}

	}

	public void setSaltedPwd(String pwd) {
		this.llaveInicial = pwd;
		usuario.setPwdUsuario(Encoder.encriptar(Encoder.strLlaveCifrado, pwd));
	}

	public String getSaltedPwd() {
		if (usuario == null)
			return null;
		llaveInicial = Encoder.desencriptar(Encoder.strLlaveCifrado, usuario.getPwdUsuario());
		return llaveInicial;
	}

	public void validatePwd(AjaxBehaviorEvent event) {
		if (!llaveComprobacion.equals(llaveInicial)) {
			setPwdValida(false);
			usuario.setPwdUsuario(null);
			showInfo("Claves no coinciden", FacesMessage.SEVERITY_ERROR);
		} else {
			showInfo("Exito al coincidir las claves", FacesMessage.SEVERITY_INFO);
		}
	}

	public String getContrasenaNueva() {
		return contrasenaNueva;
	}

	public void setContrasenaNueva(String contrasenaNueva) {
		this.contrasenaNueva = contrasenaNueva;
	}

	public String getContrasenaConfirma() {
		return contrasenaConfirma;
	}

	public void setContrasenaConfirma(String contrasenaConfirma) {
		this.contrasenaConfirma = contrasenaConfirma;
	}

	public UsuarioEt getCambioUsuario() {
		return cambioUsuario;
	}

	public String getLlaveInicial() {
		return llaveInicial;
	}

	public void setLlaveInicial(String llaveInicial) {
		this.llaveInicial = llaveInicial;
	}

	public void setCambioUsuario(UsuarioEt cambioUsuario) {
		this.cambioUsuario = cambioUsuario;
	}

	public UsuarioEt getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioEt usuario) {
		this.usuario = usuario;
	}

	public AppMain getAppMain() {
		return appMain;
	}

	public void setAppMain(AppMain appMain) {
		this.appMain = appMain;
	}

	public boolean isPwdValida() {
		return isPwdValida;
	}

	public void setPwdValida(boolean isPwdValida) {
		this.isPwdValida = isPwdValida;
	}

	public String getLlaveComprobacion() {
		return llaveComprobacion;
	}

	public void setLlaveComprobacion(String llaveComprobacion) {
		this.llaveComprobacion = llaveComprobacion;
	}

	@Override
	public void onDestroy() {

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

	public String getMsgRx() {
		return msgRx;
	}

	public void setMsgRx(String msgRx) {
		this.msgRx = msgRx;
	}

}
