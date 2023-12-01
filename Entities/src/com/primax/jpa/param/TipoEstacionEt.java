package com.primax.jpa.param;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "TIPO_ESTACION_ET")
@Audited
public class TipoEstacionEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -4838501690442140136L;

	@Id
	@SequenceGenerator(name = "sec_tipo_estacion_et", sequenceName = "seq_tipo_estacion_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_tipo_estacion_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_tipo_estacion")
	private Long idTipoEstacion;

	@Column(name = "descripcion", length = 80)
	@OrderBy("order by descripcion")
	private String descripcion;

	@Column(name = "codigo", length = 10)
	private String codigo;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoEstacion", fetch = FetchType.LAZY)
	@OrderBy("idTipoCategoriaEstacion")
	@Where(clause = "estado = 'ACT'")
	private List<TipoCategoriaEstacionEt> tipoCategoriaEstacion;

	public Long getIdTipoEstacion() {
		return idTipoEstacion;
	}

	public void setIdTipoEstacion(Long idTipoEstacion) {
		this.idTipoEstacion = idTipoEstacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public List<TipoCategoriaEstacionEt> getTipoCategoriaEstacion() {
		return tipoCategoriaEstacion;
	}

	public void setTipoCategoriaEstacion(List<TipoCategoriaEstacionEt> tipoCategoriaEstacion) {
		this.tipoCategoriaEstacion = tipoCategoriaEstacion;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof TipoEstacionEt) {

			TipoEstacionEt other = (TipoEstacionEt) obj;
			if (this.idTipoEstacion == null)
				return this == other;

			return this.idTipoEstacion.equals(other.idTipoEstacion);
		}
		return false;
	}

}
