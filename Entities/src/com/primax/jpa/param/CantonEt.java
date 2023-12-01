package com.primax.jpa.param;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OrderBy;
import javax.persistence.ParameterMode;
import javax.persistence.SequenceGenerator;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.envers.Audited;

import com.primax.jpa.base.EntityBase;

@Entity
@Table(name = "CANTON_ET")
@Audited

@NamedStoredProcedureQuery(name = "getReporteEvaluacionNivelRiesgo", procedureName = "fun_limpiar_rpt_evaluacion_nivel_riesgo", resultClasses = ProcesoDetalleEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class CantonEt extends EntityBase implements Serializable{
	
	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_canton_et", sequenceName = "seq_canton_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_canton_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_canton")
	private Long idCanton;

	@Column(name = "nombre_canton", length = 80)
	@OrderBy("order by nombreCanton")
	private String nombreCanton;

	@ManyToOne
	@JoinColumn(name = "id_provincia")
	private ProvinciaEt provincia;

	@Column(name = "inec_canton")
	private String inecCanton;

	public Long getIdCanton() {
		return idCanton;
	}

	@XmlElement
	public void setIdCanton(Long idCanton) {
		this.idCanton = idCanton;
	}

	public String getNombreCanton() {
		return nombreCanton;
	}

	@XmlElement
	public void setNombreCanton(String nombreCanton) {
		this.nombreCanton = nombreCanton.toUpperCase();
	}

	public ProvinciaEt getProvincia() {
		return provincia;
	}

	@XmlTransient
	public void setProvincia(ProvinciaEt provincia) {
		this.provincia = provincia;
	}

	public String getInecCanton() {
		return inecCanton;
	}

	@XmlTransient
	public void setInecCanton(String inecCanton) {
		this.inecCanton = inecCanton;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CantonEt) {
			CantonEt other = (CantonEt) obj;

			if (this.idCanton == null)
				return this == other;

			if (this.idCanton.equals(other.idCanton))
				return true;
		}
		return false;

	}

}
