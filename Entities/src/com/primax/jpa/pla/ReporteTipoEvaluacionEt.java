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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "REPORTE_TIPO_EVALUACION_ET")
@Audited

@NamedStoredProcedureQuery(name = "getGenerarReporteTipoEvaluacion", procedureName = "fun_generar_rpt_tipo_evaluacion", resultClasses = ReporteTipoEvaluacionEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "fechaDesde"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "fechaHasta"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idZona"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idTipoCheckList"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idEvaluacion"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idAgencia"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idNivelEvaluacion"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idNivelEvaluacionD"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class ReporteTipoEvaluacionEt extends EntityBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "sec_reporte_tipo_evaluacion_et", sequenceName = "seq_reporte_tipo_evaluacion_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_reporte_tipo_evaluacion_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_reporte_tipo_evaluacion")
	private Long idReporteTipoEvaluacion;

	@Column(name = "fecha_ejecucion")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaEjecucion;

	@Column(name = "id_check_list_ejecucion")
	private Long idCheckListEjecucion;

	@Column(name = "tipo_evaluacion", length = 50)
	private String tipoEvaluacion;

	@Column(name = "tipo_check_list", length = 100)
	private String tipoCheckList;

	@Column(name = "codigo", length = 50)
	private String codigo;

	@Column(name = "nombre_agencia", length = 100)
	private String nombreAgencia;

	@Column(name = "id_nivel_evaluacion")
	private Long idNivelEvaluacion;

	@Column(name = "id_nivel_evaluacion_detalle")
	private Long idNivelEvaluacionDetalle;

	@Column(name = "nivel_riesgo")
	private Long nivelRiesgo;

	@Column(name = "nivel_riesgo_s", length = 50)
	private String nivelRiesgoS;

	@Column(name = "calificacion")
	private Long calificacion;

	@Column(name = "color", length = 20)
	private String color;

	public ReporteTipoEvaluacionEt() {
		this.nivelRiesgo = 0L;
		this.color = "#FFFFFF";
		this.idNivelEvaluacion = 0L;
		this.idCheckListEjecucion = 0L;
		this.idNivelEvaluacionDetalle = 0L;
	}

	public Long getIdReporteTipoEvaluacion() {
		return idReporteTipoEvaluacion;
	}

	public void setIdReporteTipoEvaluacion(Long idReporteTipoEvaluacion) {
		this.idReporteTipoEvaluacion = idReporteTipoEvaluacion;
	}

	public Date getFechaEjecucion() {
		return fechaEjecucion;
	}

	public void setFechaEjecucion(Date fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}

	public Long getIdCheckListEjecucion() {
		return idCheckListEjecucion;
	}

	public void setIdCheckListEjecucion(Long idCheckListEjecucion) {
		this.idCheckListEjecucion = idCheckListEjecucion;
	}

	public String getTipoEvaluacion() {
		return tipoEvaluacion;
	}

	public void setTipoEvaluacion(String tipoEvaluacion) {
		this.tipoEvaluacion = tipoEvaluacion;
	}

	public String getTipoCheckList() {
		return tipoCheckList;
	}

	public void setTipoCheckList(String tipoCheckList) {
		this.tipoCheckList = tipoCheckList;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombreAgencia() {
		return nombreAgencia;
	}

	public void setNombreAgencia(String nombreAgencia) {
		this.nombreAgencia = nombreAgencia;
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

	public Long getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(Long calificacion) {
		this.calificacion = calificacion;
	}

	public Long getIdNivelEvaluacion() {
		return idNivelEvaluacion;
	}

	public void setIdNivelEvaluacion(Long idNivelEvaluacion) {
		this.idNivelEvaluacion = idNivelEvaluacion;
	}

	public Long getIdNivelEvaluacionDetalle() {
		return idNivelEvaluacionDetalle;
	}

	public void setIdNivelEvaluacionDetalle(Long idNivelEvaluacionDetalle) {
		this.idNivelEvaluacionDetalle = idNivelEvaluacionDetalle;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof ReporteTipoEvaluacionEt) {

			ReporteTipoEvaluacionEt other = (ReporteTipoEvaluacionEt) obj;
			if (this.idReporteTipoEvaluacion == null)
				return this == other;

			return this.idReporteTipoEvaluacion.equals(other.idReporteTipoEvaluacion);
		}
		return false;
	}

}
