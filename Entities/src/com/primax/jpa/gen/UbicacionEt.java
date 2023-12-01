package com.primax.jpa.gen;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.primax.jpa.base.EntityBase;
import com.primax.jpa.param.CantonEt;
import com.primax.jpa.param.ProvinciaEt;

@Entity
@Table(name = "UBICACION_ET")
@Audited
public class UbicacionEt extends EntityBase implements Serializable{

	private static final long serialVersionUID = -3445333126446300203L;

	@Id
	@SequenceGenerator(name = "sec_per_ubicacion", allocationSize = 1, initialValue = 1, sequenceName = "seq_per_ubicacion")
	@GeneratedValue(generator = "sec_per_ubicacion", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_ubicacion")
	private Long idUbicacion;

	@OneToOne
	@JoinColumn(name = "id_provincia", insertable = true, updatable = true)
	private ProvinciaEt provincia;

	@OneToOne
	@JoinColumn(name = "id_canton", insertable = true, updatable = true)
	private CantonEt canton;

	public Long getIdUbicacion() {
		return idUbicacion;
	}

	public void setIdUbicacion(Long idUbicacion) {
		this.idUbicacion = idUbicacion;
	}

	public ProvinciaEt getProvincia() {
		return provincia;
	}

	public void setProvincia(ProvinciaEt provincia) {
		this.provincia = provincia;
	}

	public CantonEt getCanton() {
		return canton;
	}

	public void setCanton(CantonEt canton) {
		this.canton = canton;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UbicacionEt) {

			UbicacionEt other = (UbicacionEt) obj;

			if (this.idUbicacion == null)
				return this == other;

			return this.idUbicacion.equals(other.idUbicacion);
		} else
			return false;

	}

}
