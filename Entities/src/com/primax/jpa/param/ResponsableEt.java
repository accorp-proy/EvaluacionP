package com.primax.jpa.param;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.gen.PersonaEt;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "RESPONSABLE_ET")
@Audited
public class ResponsableEt extends EntityBase {

	@Id
	@SequenceGenerator(name = "sec_responsable_et", sequenceName = "seq_responsable_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_responsable_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_responsable")
	private Long idResponsable;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_persona")
	private PersonaEt persona;

	@ManyToOne
	@JoinColumn(name = "id_tipo_cargo")
	private TipoCargoEt tipoCargo;

	@ManyToOne
	@JoinColumn(name = "id_agencia")
	private AgenciaEt agencia;

	@Column(name = "usuario_creado")
	private boolean usuarioCreado;

	@ManyToOne
	@JoinColumn(name = "usuario_estacion")
	protected UsuarioEt usuarioEstacion;

	public ResponsableEt() {
		this.usuarioCreado = false;
	}

	public Long getIdResponsable() {
		return idResponsable;
	}

	public void setIdResponsable(Long idResponsable) {
		this.idResponsable = idResponsable;
	}

	public PersonaEt getPersona() {
		return persona;
	}

	public void setPersona(PersonaEt persona) {
		this.persona = persona;
	}

	public TipoCargoEt getTipoCargo() {
		return tipoCargo;
	}

	public void setTipoCargo(TipoCargoEt tipoCargo) {
		this.tipoCargo = tipoCargo;
	}

	public AgenciaEt getAgencia() {
		return agencia;
	}

	public void setAgencia(AgenciaEt agencia) {
		this.agencia = agencia;
	}

	public String informacionItem() {
		return this.persona.getIdentificacionPersona() + "-" + this.persona.getNombreCompleto();
	}

	public boolean isUsuarioCreado() {
		return usuarioCreado;
	}

	public void setUsuarioCreado(boolean usuarioCreado) {
		this.usuarioCreado = usuarioCreado;
	}

	public UsuarioEt getUsuarioEstacion() {
		return usuarioEstacion;
	}

	public void setUsuarioEstacion(UsuarioEt usuarioEstacion) {
		this.usuarioEstacion = usuarioEstacion;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ResponsableEt) {
			ResponsableEt other = (ResponsableEt) obj;
			if (this.idResponsable == null)
				return this == other;
			if (this.idResponsable.equals(other.idResponsable))
				return true;
		}
		return false;
	}
}
