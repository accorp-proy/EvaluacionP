package com.primax.jpa.param;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
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
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "AGENCIA_CATEGORIA_ET")
@Audited
public class AgenciaCategoriaEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_agencia_categoria_et", sequenceName = "seq_agencia_categoria_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_agencia_categoria_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_agencia_categoria")
	private Long idAgenciaCategoria;

	@ManyToOne
	@JoinColumn(name = "id_tipo_estacion")
	private TipoEstacionEt tipoEstacion;

	@ManyToOne
	@JoinColumn(name = "id_categoria_estacion")
	private CategoriaEstacionEt categoriaEstacion;

	@ManyToOne
	@JoinColumn(name = "id_agencia")
	private AgenciaEt agencia;

	public Long getIdAgenciaCategoria() {
		return idAgenciaCategoria;
	}

	public void setIdAgenciaCategoria(Long idAgenciaCategoria) {
		this.idAgenciaCategoria = idAgenciaCategoria;
	}

	public CategoriaEstacionEt getCategoriaEstacion() {
		return categoriaEstacion;
	}

	public void setCategoriaEstacion(CategoriaEstacionEt categoriaEstacion) {
		this.categoriaEstacion = categoriaEstacion;
	}

	public AgenciaEt getAgencia() {
		return agencia;
	}

	public void setAgencia(AgenciaEt agencia) {
		this.agencia = agencia;
	}

	public TipoEstacionEt getTipoEstacion() {
		return tipoEstacion;
	}

	public void setTipoEstacion(TipoEstacionEt tipoEstacion) {
		this.tipoEstacion = tipoEstacion;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AgenciaCategoriaEt) {
			AgenciaCategoriaEt other = (AgenciaCategoriaEt) obj;

			if (this.idAgenciaCategoria == null)
				return this == other;

			if (this.idAgenciaCategoria.equals(other.idAgenciaCategoria))
				return true;
		}
		return false;

	}

}
