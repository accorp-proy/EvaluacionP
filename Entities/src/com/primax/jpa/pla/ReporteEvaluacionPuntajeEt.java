package com.primax.jpa.pla;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.NamedStoredProcedureQuery;

@Entity
@Table(name = "REPORTE_EVALUACION_PUNTAJE_ET")
@Audited

@NamedStoredProcedureQuery(name = "getGenerarReportePuntaje", procedureName = "fun_generar_rpt_evaluacion_puntaje", resultClasses = ReporteEvaluacionPuntajeEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "fechaDesde"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "fechaHasta"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idZona"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idEvaluacion"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idNivelEvaluacion"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idNivelEvaluacionD"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class ReporteEvaluacionPuntajeEt extends EntityBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "sec_reporte_evaluacion_puntaje_et", sequenceName = "seq_reporte_evaluacion_puntaje_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_reporte_evaluacion_puntaje_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_reporte_evaluacion_puntaje")
	private Long idReporteEvaluacionPuntaje;

	@Column(name = "fecha_ejecucion")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaEjecucion;

	@Column(name = "id_check_list_ejecucion")
	private Long idCheckListEjecucion;

	@Column(name = "tipo", length = 50)
	private String tipo;

	@Column(name = "ciudad", length = 100)
	private String ciudad;

	@Column(name = "zona", length = 50)
	private String zona;

	@Column(name = "id_agencia")
	private Long idAgencia;

	@Column(name = "codigo_agencia", length = 20)
	private String codigoAgencia;

	@Column(name = "nombre_agencia", length = 100)
	private String nombreAgencia;

	@Column(name = "mes_01", length = 20)
	private String mes01;

	@Column(name = "mes_02", length = 20)
	private String mes02;

	@Column(name = "mes_03", length = 20)
	private String mes03;

	@Column(name = "mes_04", length = 20)
	private String mes04;

	@Column(name = "mes_05", length = 20)
	private String mes05;

	@Column(name = "mes_06", length = 20)
	private String mes06;

	@Column(name = "mes_07", length = 20)
	private String mes07;

	@Column(name = "mes_08", length = 20)
	private String mes08;

	@Column(name = "mes_09", length = 20)
	private String mes09;

	@Column(name = "mes_10", length = 20)
	private String mes10;

	@Column(name = "mes_11", length = 20)
	private String mes11;

	@Column(name = "mes_12", length = 20)
	private String mes12;

	@Column(name = "mes_01_c", length = 10)
	private String mes01C;

	@Column(name = "mes_02_c", length = 10)
	private String mes02C;

	@Column(name = "mes_03_c", length = 10)
	private String mes03C;

	@Column(name = "mes_04_c", length = 10)
	private String mes04C;

	@Column(name = "mes_05_c", length = 10)
	private String mes05C;

	@Column(name = "mes_06_c", length = 10)
	private String mes06C;

	@Column(name = "mes_07_c", length = 10)
	private String mes07C;

	@Column(name = "mes_08_c", length = 10)
	private String mes08C;

	@Column(name = "mes_09_c", length = 10)
	private String mes09C;

	@Column(name = "mes_10_c", length = 10)
	private String mes10C;

	@Column(name = "mes_11_c", length = 10)
	private String mes11C;

	@Column(name = "mes_12_c", length = 10)
	private String mes12C;

	@Column(name = "id_nivel_evaluacion")
	private Long idNivelEvaluacion;

	@Column(name = "id_nivel_evaluacion_detalle")
	private Long idNivelEvaluacionDetalle;

	public ReporteEvaluacionPuntajeEt() {
		this.idAgencia = 0L;
		this.idCheckListEjecucion = 0L;
	}

	public Long getIdReporteEvaluacionPuntaje() {
		return idReporteEvaluacionPuntaje;
	}

	public void setIdReporteEvaluacionPuntaje(Long idReporteEvaluacionPuntaje) {
		this.idReporteEvaluacionPuntaje = idReporteEvaluacionPuntaje;
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

	public Long getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(Long idAgencia) {
		this.idAgencia = idAgencia;
	}

	public String getMes01() {
		return mes01;
	}

	public void setMes01(String mes01) {
		this.mes01 = mes01;
	}

	public String getMes02() {
		return mes02;
	}

	public void setMes02(String mes02) {
		this.mes02 = mes02;
	}

	public String getMes03() {
		return mes03;
	}

	public void setMes03(String mes03) {
		this.mes03 = mes03;
	}

	public String getMes04() {
		return mes04;
	}

	public void setMes04(String mes04) {
		this.mes04 = mes04;
	}

	public String getMes05() {
		return mes05;
	}

	public void setMes05(String mes05) {
		this.mes05 = mes05;
	}

	public String getMes06() {
		return mes06;
	}

	public void setMes06(String mes06) {
		this.mes06 = mes06;
	}

	public String getMes07() {
		return mes07;
	}

	public void setMes07(String mes07) {
		this.mes07 = mes07;
	}

	public String getMes08() {
		return mes08;
	}

	public void setMes08(String mes08) {
		this.mes08 = mes08;
	}

	public String getMes09() {
		return mes09;
	}

	public void setMes09(String mes09) {
		this.mes09 = mes09;
	}

	public String getMes10() {
		return mes10;
	}

	public void setMes10(String mes10) {
		this.mes10 = mes10;
	}

	public String getMes11() {
		return mes11;
	}

	public void setMes11(String mes11) {
		this.mes11 = mes11;
	}

	public String getMes12() {
		return mes12;
	}

	public void setMes12(String mes12) {
		this.mes12 = mes12;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public String getCodigoAgencia() {
		return codigoAgencia;
	}

	public void setCodigoAgencia(String codigoAgencia) {
		this.codigoAgencia = codigoAgencia;
	}

	public String getNombreAgencia() {
		return nombreAgencia;
	}

	public void setNombreAgencia(String nombreAgencia) {
		this.nombreAgencia = nombreAgencia;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getMes01C() {
		return mes01C;
	}

	public void setMes01C(String mes01c) {
		mes01C = mes01c;
	}

	public String getMes02C() {
		return mes02C;
	}

	public void setMes02C(String mes02c) {
		mes02C = mes02c;
	}

	public String getMes03C() {
		return mes03C;
	}

	public void setMes03C(String mes03c) {
		mes03C = mes03c;
	}

	public String getMes04C() {
		return mes04C;
	}

	public void setMes04C(String mes04c) {
		mes04C = mes04c;
	}

	public String getMes05C() {
		return mes05C;
	}

	public void setMes05C(String mes05c) {
		mes05C = mes05c;
	}

	public String getMes06C() {
		return mes06C;
	}

	public void setMes06C(String mes06c) {
		mes06C = mes06c;
	}

	public String getMes07C() {
		return mes07C;
	}

	public void setMes07C(String mes07c) {
		mes07C = mes07c;
	}

	public String getMes08C() {
		return mes08C;
	}

	public void setMes08C(String mes08c) {
		mes08C = mes08c;
	}

	public String getMes09C() {
		return mes09C;
	}

	public void setMes09C(String mes09c) {
		mes09C = mes09c;
	}

	public String getMes10C() {
		return mes10C;
	}

	public void setMes10C(String mes10c) {
		mes10C = mes10c;
	}

	public String getMes11C() {
		return mes11C;
	}

	public void setMes11C(String mes11c) {
		mes11C = mes11c;
	}

	public String getMes12C() {
		return mes12C;
	}

	public void setMes12C(String mes12c) {
		mes12C = mes12c;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getIdNivelEvaluacionDetalle() {
		return idNivelEvaluacionDetalle;
	}

	public void setIdNivelEvaluacionDetalle(Long idNivelEvaluacionDetalle) {
		this.idNivelEvaluacionDetalle = idNivelEvaluacionDetalle;
	}

	public Long getIdNivelEvaluacion() {
		return idNivelEvaluacion;
	}

	public void setIdNivelEvaluacion(Long idNivelEvaluacion) {
		this.idNivelEvaluacion = idNivelEvaluacion;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof ReporteEvaluacionPuntajeEt) {

			ReporteEvaluacionPuntajeEt other = (ReporteEvaluacionPuntajeEt) obj;
			if (this.idReporteEvaluacionPuntaje == null)
				return this == other;

			return this.idReporteEvaluacionPuntaje.equals(other.idReporteEvaluacionPuntaje);
		}
		return false;
	}

}
