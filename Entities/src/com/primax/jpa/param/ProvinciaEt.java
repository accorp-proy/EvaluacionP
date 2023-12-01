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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import com.primax.jpa.base.EntityBase;

@Entity
@Table(name = "PROVINCIA_ET")
@Audited

@NamedStoredProcedureQuery(name = "getReporte", procedureName = "fun_limpiar_rpt_evaluacion_puntaje", resultClasses = ProcesoDetalleEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class ProvinciaEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -4838501690442140136L;

	@Id
	@SequenceGenerator(name = "sec_provincia_et", sequenceName = "seq_provincia_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_provincia_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_provincia")
	private Long idProvincia;

	@Column(name = "nombre_provincia", length = 80)
	@OrderBy("order by nombreProvincia")
	private String nombreProvincia;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "provincia", fetch = FetchType.LAZY)
	@OrderBy("nombreCanton asc")
	@Where(clause = "estado = 'ACT'")
	private List<CantonEt> cantones;

	@Column(name = "inec_provincia")
	private String inecProvincia;

	@ManyToOne
	@JoinColumn(name = "id_region")
	private RegionEt region;

	public Long getIdProvincia() {
		return idProvincia;
	}

	@XmlElement
	public void setIdProvincia(Long idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getNombreProvincia() {
		return nombreProvincia;
	}

	@XmlElement
	public void setNombreProvincia(String nombreProvincia) {
		this.nombreProvincia = nombreProvincia.toUpperCase();
	}

	public List<CantonEt> getCantones() {
		return cantones;
	}

	@XmlElementWrapper(name = "cantones")
	@XmlElement(name = "canton")
	public void setCantones(List<CantonEt> cantones) {
		this.cantones = cantones;
	}

	public String getInecProvincia() {
		return inecProvincia;
	}

	public RegionEt getRegion() {
		return region;
	}

	public void setRegion(RegionEt region) {
		this.region = region;
	}

	@XmlTransient
	public void setInecProvincia(String inecProvincia) {
		this.inecProvincia = inecProvincia;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof ProvinciaEt) {

			ProvinciaEt other = (ProvinciaEt) obj;
			if (this.idProvincia == null)
				return this == other;

			return this.idProvincia.equals(other.idProvincia);
		}
		return false;
	}

}
