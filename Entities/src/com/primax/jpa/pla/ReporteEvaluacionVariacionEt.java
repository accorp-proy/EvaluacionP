package com.primax.jpa.pla;

import java.io.Serializable;

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
@Table(name = "REPORTE_EVALUACION_VARIACION_ET")
@Audited

@NamedStoredProcedureQuery(name = "getGenerarReporteVariacion", procedureName = "fun_generar_rpt_evaluacion_variacion", resultClasses = ReporteEvaluacionVariacionEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "anio"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class ReporteEvaluacionVariacionEt extends EntityBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "sec_reporte_evaluacion_variacion_et", sequenceName = "seq_reporte_evaluacion_variacion_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_reporte_evaluacion_variacion_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_reporte_evaluacion_variacion")
	private Long idReporteEvaluacionVariacion;

	@Column(name = "anio")
	private Long anio;

	@Column(name = "mostrar")
	private boolean mostrar;

	@Column(name = "nro_visita")
	private Long nroVisita;

	@Column(name = "mes_01")
	private Long mes01;

	@Column(name = "mes_02")
	private Long mes02;

	@Column(name = "mes_03")
	private Long mes03;

	@Column(name = "mes_04")
	private Long mes04;

	@Column(name = "mes_05")
	private Long mes05;

	@Column(name = "mes_06")
	private Long mes06;

	@Column(name = "mes_07")
	private Long mes07;

	@Column(name = "mes_08")
	private Long mes08;

	@Column(name = "mes_09")
	private Long mes09;

	@Column(name = "mes_10")
	private Long mes10;

	@Column(name = "mes_11")
	private Long mes11;

	@Column(name = "mes_12")
	private Long mes12;

	@Column(name = "nro_visita_d")
	private Long nroVisitaD;

	@Column(name = "mes_01_d")
	private Double mes01D;

	@Column(name = "mes_02_d")
	private Double mes02D;

	@Column(name = "mes_03_d")
	private Double mes03D;

	@Column(name = "mes_04_d")
	private Double mes04D;

	@Column(name = "mes_05_d")
	private Double mes05D;

	@Column(name = "mes_06_d")
	private Double mes06D;

	@Column(name = "mes_07_d")
	private Double mes07D;

	@Column(name = "mes_08_d")
	private Double mes08D;

	@Column(name = "mes_09_d")
	private Double mes09D;

	@Column(name = "mes_10_d")
	private Double mes10D;

	@Column(name = "mes_11_d")
	private Double mes11D;

	@Column(name = "mes_12_d")
	private Double mes12D;

	public Long getIdReporteEvaluacionVariacion() {
		return idReporteEvaluacionVariacion;
	}

	public void setIdReporteEvaluacionVariacion(Long idReporteEvaluacionVariacion) {
		this.idReporteEvaluacionVariacion = idReporteEvaluacionVariacion;
	}

	public Long getAnio() {
		return anio;
	}

	public void setAnio(Long anio) {
		this.anio = anio;
	}

	public Long getNroVisita() {
		return nroVisita;
	}

	public void setNroVisita(Long nroVisita) {
		this.nroVisita = nroVisita;
	}

	public Long getMes01() {
		return mes01;
	}

	public void setMes01(Long mes01) {
		this.mes01 = mes01;
	}

	public Long getMes02() {
		return mes02;
	}

	public void setMes02(Long mes02) {
		this.mes02 = mes02;
	}

	public Long getMes03() {
		return mes03;
	}

	public void setMes03(Long mes03) {
		this.mes03 = mes03;
	}

	public Long getMes04() {
		return mes04;
	}

	public void setMes04(Long mes04) {
		this.mes04 = mes04;
	}

	public Long getMes05() {
		return mes05;
	}

	public void setMes05(Long mes05) {
		this.mes05 = mes05;
	}

	public Long getMes06() {
		return mes06;
	}

	public void setMes06(Long mes06) {
		this.mes06 = mes06;
	}

	public Long getMes07() {
		return mes07;
	}

	public void setMes07(Long mes07) {
		this.mes07 = mes07;
	}

	public Long getMes08() {
		return mes08;
	}

	public void setMes08(Long mes08) {
		this.mes08 = mes08;
	}

	public Long getMes09() {
		return mes09;
	}

	public void setMes09(Long mes09) {
		this.mes09 = mes09;
	}

	public Long getMes10() {
		return mes10;
	}

	public void setMes10(Long mes10) {
		this.mes10 = mes10;
	}

	public Long getMes11() {
		return mes11;
	}

	public void setMes11(Long mes11) {
		this.mes11 = mes11;
	}

	public Long getMes12() {
		return mes12;
	}

	public void setMes12(Long mes12) {
		this.mes12 = mes12;
	}

	public boolean isMostrar() {
		return mostrar;
	}

	public void setMostrar(boolean mostrar) {
		this.mostrar = mostrar;
	}

	public Long getNroVisitaD() {
		return nroVisitaD;
	}

	public void setNroVisitaD(Long nroVisitaD) {
		this.nroVisitaD = nroVisitaD;
	}

	public Double getMes01D() {
		return mes01D;
	}

	public void setMes01D(Double mes01d) {
		mes01D = mes01d;
	}

	public Double getMes02D() {
		return mes02D;
	}

	public void setMes02D(Double mes02d) {
		mes02D = mes02d;
	}

	public Double getMes03D() {
		return mes03D;
	}

	public void setMes03D(Double mes03d) {
		mes03D = mes03d;
	}

	public Double getMes04D() {
		return mes04D;
	}

	public void setMes04D(Double mes04d) {
		mes04D = mes04d;
	}

	public Double getMes05D() {
		return mes05D;
	}

	public void setMes05D(Double mes05d) {
		mes05D = mes05d;
	}

	public Double getMes06D() {
		return mes06D;
	}

	public void setMes06D(Double mes06d) {
		mes06D = mes06d;
	}

	public Double getMes07D() {
		return mes07D;
	}

	public void setMes07D(Double mes07d) {
		mes07D = mes07d;
	}

	public Double getMes08D() {
		return mes08D;
	}

	public void setMes08D(Double mes08d) {
		mes08D = mes08d;
	}

	public Double getMes09D() {
		return mes09D;
	}

	public void setMes09D(Double mes09d) {
		mes09D = mes09d;
	}

	public Double getMes10D() {
		return mes10D;
	}

	public void setMes10D(Double mes10d) {
		mes10D = mes10d;
	}

	public Double getMes11D() {
		return mes11D;
	}

	public void setMes11D(Double mes11d) {
		mes11D = mes11d;
	}

	public Double getMes12D() {
		return mes12D;
	}

	public void setMes12D(Double mes12d) {
		mes12D = mes12d;
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

		if (obj instanceof ReporteEvaluacionVariacionEt) {

			ReporteEvaluacionVariacionEt other = (ReporteEvaluacionVariacionEt) obj;
			if (this.idReporteEvaluacionVariacion == null)
				return this == other;

			return this.idReporteEvaluacionVariacion.equals(other.idReporteEvaluacionVariacion);
		}
		return false;
	}

}
