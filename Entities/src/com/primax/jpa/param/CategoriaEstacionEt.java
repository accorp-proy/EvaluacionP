package com.primax.jpa.param;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "CATEGORIA_ESTACION_ET")
@Audited
public class CategoriaEstacionEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_categoria_estacion_et", sequenceName = "seq_categoria_estacion_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_categoria_estacion_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_categoria_estacion")
	private Long idCategoriaEstacion;

	@Column(name = "descripcion", length = 80)
	@OrderBy("order by descripcion")
	private String descripcion;

	@Column(name = "codigo", length = 10)
	private String codigo;

	public Long getIdCategoriaEstacion() {
		return idCategoriaEstacion;
	}

	public void setIdCategoriaEstacion(Long idCategoriaEstacion) {
		this.idCategoriaEstacion = idCategoriaEstacion;
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

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CategoriaEstacionEt) {
			CategoriaEstacionEt other = (CategoriaEstacionEt) obj;

			if (this.idCategoriaEstacion == null)
				return this == other;

			if (this.idCategoriaEstacion.equals(other.idCategoriaEstacion))
				return true;
		}
		return false;

	}

}
