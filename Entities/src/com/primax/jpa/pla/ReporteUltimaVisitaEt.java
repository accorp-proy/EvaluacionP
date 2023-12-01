package com.primax.jpa.pla;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.SequenceGenerator;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "REPORTE_ULTIMA_VISITA_ET")
@Audited

@NamedStoredProcedureQuery(name = "getGenerarReporteUltimaVisita", procedureName = "fun_generar_rpt_ultima_visita", resultClasses = ReporteUltimaVisitaEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "fechaDesde"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "fechaHasta"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idZona"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idTipoCheckList"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idEvaluacion"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idAgencia"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idProceso"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class ReporteUltimaVisitaEt extends EntityBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "sec_reporte_ultima_visita_et", sequenceName = "seq_reporte_ultima_visita_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_reporte_ultima_visita_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_reporte_ultima_visita")
	private Long idReporteUltimaVisita;

	@Column(name = "descripcion", length = 50)
	private String descripcion;

	@Column(name = "calificacion")
	private Long calificacion;

	@Column(name = "tipo")
	private Long tipo;

	@Column(name = "nivel_riesgo")
	private Long nivelRiesgo;

	@Column(name = "nivel_riesgo_s", length = 50)
	private String nivelRiesgoS;

	public ReporteUltimaVisitaEt() {
		this.tipo = 0L;
		this.descripcion = "";
		this.nivelRiesgo = 0L;
		this.calificacion = 0L;
		this.nivelRiesgoS = "";
	}

	public Long getIdReporteUltimaVisita() {
		return idReporteUltimaVisita;
	}

	public void setIdReporteUltimaVisita(Long idReporteUltimaVisita) {
		this.idReporteUltimaVisita = idReporteUltimaVisita;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(Long calificacion) {
		this.calificacion = calificacion;
	}

	public Long getTipo() {
		return tipo;
	}

	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}

	public Long getNivelRiesgo() {
		return nivelRiesgo;
	}

	public void setNivelRiesgo(Long nivelRiesgo) {
		this.nivelRiesgo = nivelRiesgo;
	}

	public String getNivelRiesgoS() {
		return nivelRiesgoS;
	}

	public void setNivelRiesgoS(String nivelRiesgoS) {
		this.nivelRiesgoS = nivelRiesgoS;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof ReporteUltimaVisitaEt) {

			ReporteUltimaVisitaEt other = (ReporteUltimaVisitaEt) obj;
			if (this.idReporteUltimaVisita == null)
				return this == other;

			return this.idReporteUltimaVisita.equals(other.idReporteUltimaVisita);
		}
		return false;
	}

}
