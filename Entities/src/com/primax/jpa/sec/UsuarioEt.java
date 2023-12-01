package com.primax.jpa.sec;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import com.primax.jpa.base.EntityBase;
import com.primax.jpa.gen.PersonaEt;
import com.primax.jpa.param.ZonaUsuarioEt;

@Entity
@Table(name = "USUARIO_ET")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement(name = "usuario")
@Audited
public class UsuarioEt extends EntityBase {

	public UsuarioEt() {
		this.accesoZona = false;
		rolesUsario = new ArrayList<>();
	}

	@Id
	@SequenceGenerator(name = "sec_usuario_et", sequenceName = "seq_usuario_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_usuario_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_usuario")
	private Long idUsuario;

	@Column(name = "nombre_usuario", length = 100)
	private String nombreUsuario;

	@Column(name = "password", length = 30)
	private String pwdUsuario;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "usuario")
	@Where(clause = "estado='ACT'")
	@OrderBy(" zona.idZona ")
	private List<ZonaUsuarioEt> zonaUsuario;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "usuario")
	@Where(clause = "estado='ACT'")
	private List<RolUsuarioEt> rolesUsario;

	@ManyToOne
	@JoinColumn(name = "id_persona")
	private PersonaEt personaUsuario;

	@Column(name = "ultimo_acceso")
	@Temporal(TemporalType.TIMESTAMP)
	private Date ultimoAcceso;

	@Column(name = "vigencia_usuario")
	@Temporal(TemporalType.TIMESTAMP)
	private Date vigenciaUsuario;

	@Column(name = "intentos_login", length = 3)
	private Integer intentosLog;

	@Column(name = "acceso_zona")
	private boolean accesoZona;

	public List<ZonaUsuarioEt> getZonaUsuario() {
		return zonaUsuario;
	}

	public void setZonaUsuario(List<ZonaUsuarioEt> zonaUsuario) {
		this.zonaUsuario = zonaUsuario;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	@XmlTransient
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	@XmlElement
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getPwdUsuario() {
		return pwdUsuario;
	}

	@XmlElement
	public void setPwdUsuario(String pwdUsuario) {
		this.pwdUsuario = pwdUsuario;
	}

	public List<RolUsuarioEt> getRolesUsario() {
		return rolesUsario;
	}

	@XmlTransient
	public void setRolesUsario(List<RolUsuarioEt> rolesUsario) {
		this.rolesUsario = rolesUsario;
	}

	public PersonaEt getPersonaUsuario() {
		return personaUsuario;
	}

	@XmlTransient
	public void setPersonaUsuario(PersonaEt personaUsuario) {
		this.personaUsuario = personaUsuario;
	}

	public Date getUltimoAcceso() {
		return ultimoAcceso;
	}

	@XmlTransient
	public void setUltimoAcceso(Date ultimoAcceso) {
		this.ultimoAcceso = ultimoAcceso;
	}

	public Date getVigenciaUsuario() {
		return vigenciaUsuario;
	}

	@XmlTransient
	public void setVigenciaUsuario(Date vigenciaUsuario) {
		this.vigenciaUsuario = vigenciaUsuario;
	}

	public boolean isAccesoZona() {
		return accesoZona;
	}

	public void setAccesoZona(boolean accesoZona) {
		this.accesoZona = accesoZona;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UsuarioEt) {
			UsuarioEt other = (UsuarioEt) obj;
			if (this.idUsuario == null)
				return this == other;
			return this.idUsuario.equals(other.idUsuario);
		} else
			return false;
	}

	public Integer getIntentosLog() {
		return intentosLog;
	}

	@XmlTransient
	public void setIntentosLog(Integer intentosLog) {
		this.intentosLog = intentosLog;
	}

	public static void main(String... input) {
		Pattern pat = java.util.regex.Pattern.compile(".+");
		Matcher matcher = pat.matcher("Aa99**??");
		System.out.println(matcher.matches());
	}

}
