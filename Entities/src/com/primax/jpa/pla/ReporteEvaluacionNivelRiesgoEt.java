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
@Table(name = "REPORTE_EVALUACION_NIVEL_RIESGO_ET")
@Audited

@NamedStoredProcedureQuery(name = "getGenerarReporteNivelRiesgo", procedureName = "fun_generar_rpt_evaluacion_nivel_riesgo", resultClasses = ReporteEvaluacionNivelRiesgoEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "fechaDesde"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "fechaHasta"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idZona"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idEvaluacion"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idNivelEvaluacion"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idNivelEvaluacionD"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class ReporteEvaluacionNivelRiesgoEt extends EntityBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "sec_reporte_evaluacion_nivel_riesgo_et", sequenceName = "seq_reporte_evaluacion_nivel_riesgo_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_reporte_evaluacion_nivel_riesgo_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_reporte_evaluacion_nivel_riesgo")
	private Long idReporteEvaluacionNivelRiesgo;

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

	@Column(name = "mes_01_s", length = 20)
	private String mes01S;

	@Column(name = "mes_02_s", length = 20)
	private String mes02S;

	@Column(name = "mes_03_s", length = 20)
	private String mes03S;

	@Column(name = "mes_04_s", length = 20)
	private String mes04S;

	@Column(name = "mes_05_s", length = 20)
	private String mes05S;

	@Column(name = "mes_06_s", length = 20)
	private String mes06S;

	@Column(name = "mes_07_s", length = 20)
	private String mes07S;

	@Column(name = "mes_08_s", length = 20)
	private String mes08S;

	@Column(name = "mes_09_s", length = 20)
	private String mes09S;

	@Column(name = "mes_10_s", length = 20)
	private String mes10S;

	@Column(name = "mes_11_s", length = 20)
	private String mes11S;

	@Column(name = "mes_12_s", length = 20)
	private String mes12S;

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

	public ReporteEvaluacionNivelRiesgoEt() {
		this.idAgencia = 0L;
		this.idNivelEvaluacion = 0L;
		this.idCheckListEjecucion = 0L;
		this.idNivelEvaluacionDetalle = 0L;
	}

	public Long getIdReporteEvaluacionNivelRiesgo() {
		return idReporteEvaluacionNivelRiesgo;
	}

	public void setIdReporteEvaluacionNivelRiesgo(Long idReporteEvaluacionNivelRiesgo) {
		this.idReporteEvaluacionNivelRiesgo = idReporteEvaluacionNivelRiesgo;
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

	public String getMes01S() {
		return mes01S;
	}

	public void setMes01S(String mes01s) {
		mes01S = mes01s;
	}

	public String getMes02S() {
		return mes02S;
	}

	public void setMes02S(String mes02s) {
		mes02S = mes02s;
	}

	public String getMes03S() {
		return mes03S;
	}

	public void setMes03S(String mes03s) {
		mes03S = mes03s;
	}

	public String getMes04S() {
		return mes04S;
	}

	public void setMes04S(String mes04s) {
		mes04S = mes04s;
	}

	public String getMes05S() {
		return mes05S;
	}

	public void setMes05S(String mes05s) {
		mes05S = mes05s;
	}

	public String getMes06S() {
		return mes06S;
	}

	public void setMes06S(String mes06s) {
		mes06S = mes06s;
	}

	public String getMes07S() {
		return mes07S;
	}

	public void setMes07S(String mes07s) {
		mes07S = mes07s;
	}

	public String getMes08S() {
		return mes08S;
	}

	public void setMes08S(String mes08s) {
		mes08S = mes08s;
	}

	public String getMes09S() {
		return mes09S;
	}

	public void setMes09S(String mes09s) {
		mes09S = mes09s;
	}

	public String getMes10S() {
		return mes10S;
	}

	public void setMes10S(String mes10s) {
		mes10S = mes10s;
	}

	public String getMes11S() {
		return mes11S;
	}

	public void setMes11S(String mes11s) {
		mes11S = mes11s;
	}

	public String getMes12S() {
		return mes12S;
	}

	public void setMes12S(String mes12s) {
		mes12S = mes12s;
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

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof ReporteEvaluacionNivelRiesgoEt) {

			ReporteEvaluacionNivelRiesgoEt other = (ReporteEvaluacionNivelRiesgoEt) obj;
			if (this.idReporteEvaluacionNivelRiesgo == null)
				return this == other;

			return this.idReporteEvaluacionNivelRiesgo.equals(other.idReporteEvaluacionNivelRiesgo);
		}
		return false;
	}

}
