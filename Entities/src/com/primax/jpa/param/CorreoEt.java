package com.primax.jpa.param;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;



@Entity
@Table(name = "CORREO_ET")
@Audited
public class CorreoEt extends EntityBase {

	@Id
	@Column(name = "id_correo")
	@GeneratedValue(generator = "sec_correo", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "sec_correo", sequenceName = "seq_correo", allocationSize = 1, initialValue = 1)
	private Long idCorreo;

	@Column(name = "mail_host")
	private String mailHost;

	@Column(name = "mail_port")
	private String mailPort;

	@Column(name = "mail_tls")
	private String mailTls;

	@Column(name = "mail_smtp_user")
	private String mailSmtpUser;

	@Column(name = "mail_auth")
	private String mailAuth;

	@Column(name = "mail_trust_tls")
	private String mailTrustTls;

	@Column(name = "mail_smtp_pwd")
	private String mailSmtpPwd;

	@Column(name = "mail_from")
	private String mailFrom;

	@Column(name = "habilitado")
	private boolean habilitado;

	public Long getIdCorreo() {
		return idCorreo;
	}

	public void setIdCorreo(Long idCorreo) {
		this.idCorreo = idCorreo;
	}

	public String getMailHost() {
		return mailHost;
	}

	public void setMailHost(String mailHost) {
		this.mailHost = mailHost;
	}

	public String getMailPort() {
		return mailPort;
	}

	public void setMailPort(String mailPort) {
		this.mailPort = mailPort;
	}

	public String getMailTls() {
		return mailTls;
	}

	public void setMailTls(String mailTls) {
		this.mailTls = mailTls;
	}

	public String getMailSmtpUser() {
		return mailSmtpUser;
	}

	public void setMailSmtpUser(String mailSmtpUser) {
		this.mailSmtpUser = mailSmtpUser;
	}

	public String getMailAuth() {
		return mailAuth;
	}

	public void setMailAuth(String mailAuth) {
		this.mailAuth = mailAuth;
	}

	public String getMailTrustTls() {
		return mailTrustTls;
	}

	public void setMailTrustTls(String mailTrustTls) {
		this.mailTrustTls = mailTrustTls;
	}

	public String getMailSmtpPwd() {
		return mailSmtpPwd;
	}

	public void setMailSmtpPwd(String mailSmtpPwd) {
		this.mailSmtpPwd = mailSmtpPwd;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CorreoEt) {
			CorreoEt other = (CorreoEt) obj;
			if (this.idCorreo == null)
				return this == other;
			return this.idCorreo.equals(other.idCorreo);
		}
		return false;
	}
}
