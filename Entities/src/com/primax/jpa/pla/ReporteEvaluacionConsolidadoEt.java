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
@Table(name = "REPORTE_EVALUACION_CONSOLIDADO_ET")
@Audited

@NamedStoredProcedureQuery(name = "getGenerarReporteConsolidado", procedureName = "fun_generar_rpt_evaluacion_consolidado", resultClasses = ReporteEvaluacionConsolidadoEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "fechaDesde"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "fechaHasta"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idZona"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idEvaluacion"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idNivelEvaluacion"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idNivelEvaluacionD"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class ReporteEvaluacionConsolidadoEt extends EntityBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "sec_reporte_evaluacion_consolidado_et", sequenceName = "seq_reporte_evaluacion_consolidado_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_reporte_evaluacion_consolidado_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_reporte_evaluacion_consolidado")
	private Long idReporteEvaluacionConsolidado;

	@Column(name = "fecha_ejecucion")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaEjecucion;

	@Column(name = "id_check_list_ejecucion")
	private Long idCheckListEjecucion;

	@Column(name = "id_agencia")
	private Long idAgencia;

	@Column(name = "id_zona")
	private Long idZona;

	@Column(name = "id_nivel_riesgo")
	private Long idNivelRiesgo;

	@Column(name = "id_nivel_evaluacion")
	private Long idNivelEvaluacion;

	@Column(name = "id_nivel_evaluacion_detalle")
	private Long idNivelEvaluacionDetalle;

	@Column(name = "zona", length = 50)
	private String zona;

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

	public ReporteEvaluacionConsolidadoEt() {
		this.idZona = 0L;
		this.idAgencia = 0L;
		this.idNivelRiesgo = 0L;
		this.idNivelEvaluacion = 0L;
		this.idCheckListEjecucion = 0L;
		this.idNivelEvaluacionDetalle = 0L;

	}

	public Long getIdReporteEvaluacionConsolidado() {
		return idReporteEvaluacionConsolidado;
	}

	public void setIdReporteEvaluacionConsolidado(Long idReporteEvaluacionConsolidado) {
		this.idReporteEvaluacionConsolidado = idReporteEvaluacionConsolidado;
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

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
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

	public Long getIdZona() {
		return idZona;
	}

	public void setIdZona(Long idZona) {
		this.idZona = idZona;
	}

	public Long getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(Long idAgencia) {
		this.idAgencia = idAgencia;
	}

	public Long getIdNivelRiesgo() {
		return idNivelRiesgo;
	}

	public void setIdNivelRiesgo(Long idNivelRiesgo) {
		this.idNivelRiesgo = idNivelRiesgo;
	}

	public Long getIdNivelEvaluacionDetalle() {
		return idNivelEvaluacionDetalle;
	}

	public void setIdNivelEvaluacionDetalle(Long idNivelEvaluacionDetalle) {
		this.idNivelEvaluacionDetalle = idNivelEvaluacionDetalle;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof ReporteEvaluacionConsolidadoEt) {

			ReporteEvaluacionConsolidadoEt other = (ReporteEvaluacionConsolidadoEt) obj;
			if (this.idReporteEvaluacionConsolidado == null)
				return this == other;

			return this.idReporteEvaluacionConsolidado.equals(other.idReporteEvaluacionConsolidado);
		}
		return false;
	}

}
