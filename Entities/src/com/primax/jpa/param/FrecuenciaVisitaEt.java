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
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.ParameterMode;
import javax.persistence.SequenceGenerator;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "FRECUENCIA_VISITA_ET")
@Audited

@NamedStoredProcedureQuery(name = "getReporteOrgPlnAnio", procedureName = "fun_limpiar_rpt_org_pla", resultClasses = FrecuenciaVisitaEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class FrecuenciaVisitaEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -4838501690442140136L;

	@Id
	@SequenceGenerator(name = "sec_frecuencia_visita_et", sequenceName = "seq_frecuencia_visita_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_frecuencia_visita_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_frecuencia_visita")
	private Long idFrecuenciaVisita;

	@Column(name = "orden")
	private Long orden;

	@Column(name = "descripcion", length = 100)
	private String descripcion;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "frecuenciaVisita", fetch = FetchType.LAZY)
	@OrderBy("idFrecuenciaVisitaDetalle ")
	@Where(clause = "estado = 'ACT'")
	private List<FrecuenciaVisitaDetalleEt> frecuenciaVisitaDetalle;

	public Long getIdFrecuenciaVisita() {
		return idFrecuenciaVisita;
	}

	public void setIdFrecuenciaVisita(Long idFrecuenciaVisita) {
		this.idFrecuenciaVisita = idFrecuenciaVisita;
	}

	public List<FrecuenciaVisitaDetalleEt> getFrecuenciaVisitaDetalle() {
		return frecuenciaVisitaDetalle;
	}

	public void setFrecuenciaVisitaDetalle(List<FrecuenciaVisitaDetalleEt> frecuenciaVisitaDetalle) {
		this.frecuenciaVisitaDetalle = frecuenciaVisitaDetalle;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof FrecuenciaVisitaEt) {

			FrecuenciaVisitaEt other = (FrecuenciaVisitaEt) obj;
			if (this.idFrecuenciaVisita == null)
				return this == other;

			return this.idFrecuenciaVisita.equals(other.idFrecuenciaVisita);
		}
		return false;
	}

}
