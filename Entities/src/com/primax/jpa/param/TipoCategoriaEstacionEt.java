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
@Table(name = "TIPO_CATEGORIA_ESTACION_ET")
@Audited
public class TipoCategoriaEstacionEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_tipo_categoria_estacion_et", sequenceName = "seq_tipo_categoria_estacion_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_tipo_categoria_estacion_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_tipo_categoria_estacion")
	private Long idTipoCategoriaEstacion;

	@ManyToOne
	@JoinColumn(name = "id_categoria_estacion")
	private CategoriaEstacionEt categoriaEstacion;

	@ManyToOne
	@JoinColumn(name = "id_tipo_estacion")
	private TipoEstacionEt tipoEstacion;

	public Long getIdTipoCategoriaEstacion() {
		return idTipoCategoriaEstacion;
	}

	public void setIdTipoCategoriaEstacion(Long idTipoCategoriaEstacion) {
		this.idTipoCategoriaEstacion = idTipoCategoriaEstacion;
	}

	public CategoriaEstacionEt getCategoriaEstacion() {
		return categoriaEstacion;
	}

	public void setCategoriaEstacion(CategoriaEstacionEt categoriaEstacion) {
		this.categoriaEstacion = categoriaEstacion;
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
		if (obj instanceof TipoCategoriaEstacionEt) {
			TipoCategoriaEstacionEt other = (TipoCategoriaEstacionEt) obj;

			if (this.idTipoCategoriaEstacion == null)
				return this == other;

			if (this.idTipoCategoriaEstacion.equals(other.idTipoCategoriaEstacion))
				return true;
		}
		return false;

	}

}
