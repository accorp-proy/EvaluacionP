package com.primax.jpa.sec;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;

@Entity
@Table(name = "USUARIO_HISTORIAL_ET")
@Audited
public class UsuarioHistorialEt {

	@Id
	@SequenceGenerator(sequenceName = "seq_usuario_historial", name = "sec_usuario_historial", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "sec_usuario_historial", strategy = GenerationType.SEQUENCE)
	private Long idRegistroHistorial;

	@ManyToOne
	@JoinColumn(name = "usuario")
	private UsuarioEt usuario;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_login")
	private Date fechaLogin;

	public Long getIdRegistroHistorial() {
		return idRegistroHistorial;
	}

	public void setIdRegistroHistorial(Long idRegistroHistorial) {
		this.idRegistroHistorial = idRegistroHistorial;
	}

	public UsuarioEt getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioEt usuario) {
		this.usuario = usuario;
	}

	public Date getFechaLogin() {
		return fechaLogin;
	}

	public void setFechaLogin(Date fechaLogin) {
		this.fechaLogin = fechaLogin;
	}

}
