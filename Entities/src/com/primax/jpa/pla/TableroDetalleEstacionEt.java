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
@Table(name = "TABLERO_DETALLE_ESTACION_ET")
@Audited

@NamedStoredProcedureQuery(name = "getGenerarDetalleEstacion", procedureName = "fun_generar_tab_detalle_estacion", resultClasses = TableroDetalleEstacionEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "fechaDesde"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "fechaHasta"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idTipoEstacion"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idZona"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idAgencia"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idEvaluacion"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idNivelEvaluacion"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class TableroDetalleEstacionEt extends EntityBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "sec_tablero_detalle_estacion_et", sequenceName = "seq_tablero_detalle_estacion_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_tablero_detalle_estacion_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_tablero_detalle_estacion")
	private Long idTableroDetalleEstacion;

	@Column(name = "fecha_ejecucion")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaEjecucion;

	@Column(name = "id_check_list_ejecucion")
	private Long idCheckListEjecucion;

	@Column(name = "orden")
	private Long orden;

	@Column(name = "id_zona")
	private Long idZona;

	@Column(name = "zona", length = 50)
	private String zona;

	@Column(name = "id_agencia")
	private Long idAgencia;

	@Column(name = "codigo_agencia", length = 20)
	private String codigoAgencia;

	@Column(name = "agencia", length = 100)
	private String agencia;

	@Column(name = "id_evaluacion")
	private Long idEvaluacion;

	@Column(name = "evaluacion", length = 50)
	private String evaluacion;

	@Column(name = "id_tipo_check_list")
	private Long idTipoCheckList;

	@Column(name = "tipo_check_list", length = 50)
	private String tipoCheckList;

	@Column(name = "id_nivel_evaluacion")
	private Long idNivelEvaluacion;

	@Column(name = "id_nivel_evaluacion_detalle")
	private Long idNivelEvaluacionDetalle;

	@Column(name = "calificacion_color", length = 10)
	private String calificacionColor;

	@Column(name = "nivel_evaluacion", length = 100)
	private String nivelEvaluacion;

	@Column(name = "calificacion")
	private Long calificacion;

	@Column(name = "mes", length = 20)
	private String mes;

	public TableroDetalleEstacionEt() {
		this.orden = 0L;
		this.calificacion = 0L;
	}

	public Long getIdTableroDetalleEstacion() {
		return idTableroDetalleEstacion;
	}

	public void setIdTableroDetalleEstacion(Long idTableroDetalleEstacion) {
		this.idTableroDetalleEstacion = idTableroDetalleEstacion;
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

	public Long getIdZona() {
		return idZona;
	}

	public void setIdZona(Long idZona) {
		this.idZona = idZona;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public Long getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(Long idAgencia) {
		this.idAgencia = idAgencia;
	}

	public String getCodigoAgencia() {
		return codigoAgencia;
	}

	public void setCodigoAgencia(String codigoAgencia) {
		this.codigoAgencia = codigoAgencia;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public Long getIdEvaluacion() {
		return idEvaluacion;
	}

	public void setIdEvaluacion(Long idEvaluacion) {
		this.idEvaluacion = idEvaluacion;
	}

	public String getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(String evaluacion) {
		this.evaluacion = evaluacion;
	}

	public Long getIdTipoCheckList() {
		return idTipoCheckList;
	}

	public void setIdTipoCheckList(Long idTipoCheckList) {
		this.idTipoCheckList = idTipoCheckList;
	}

	public String getTipoCheckList() {
		return tipoCheckList;
	}

	public void setTipoCheckList(String tipoCheckList) {
		this.tipoCheckList = tipoCheckList;
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

	public String getCalificacionColor() {
		return calificacionColor;
	}

	public void setCalificacionColor(String calificacionColor) {
		this.calificacionColor = calificacionColor;
	}

	public String getNivelEvaluacion() {
		return nivelEvaluacion;
	}

	public void setNivelEvaluacion(String nivelEvaluacion) {
		this.nivelEvaluacion = nivelEvaluacion;
	}

	public Long getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(Long calificacion) {
		this.calificacion = calificacion;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof TableroDetalleEstacionEt) {

			TableroDetalleEstacionEt other = (TableroDetalleEstacionEt) obj;
			if (this.idTableroDetalleEstacion == null)
				return this == other;

			return this.idTableroDetalleEstacion.equals(other.idTableroDetalleEstacion);
		}
		return false;
	}

}
