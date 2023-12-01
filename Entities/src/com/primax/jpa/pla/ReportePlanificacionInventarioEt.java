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
@Table(name = "REPORTE_PLANIFICACION_INVENTARIO_ET")
@Audited

@NamedStoredProcedureQuery(name = "getGenerarReportePlanificacionInv", procedureName = "fun_generar_rpt_planificacion_inventario", resultClasses = ReportePlanificacionInventarioEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "fechaDesde"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "fechaHasta"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idZona"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idEstacion"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idTipoInv"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class ReportePlanificacionInventarioEt extends EntityBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "sec_reporte_planificacion_inventario_et", sequenceName = "seq_reporte_planificacion_inventario_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_reporte_planificacion_inventario_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_reporte_planificacion_inventario")
	private Long idReportePlanificacionInventario;

	@Column(name = "fecha_ejecucion")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaEjecucion;

	@Column(name = "id_check_list_ejecucion")
	private Long idCheckListEjecucion;

	@Column(name = "tipo", length = 50)
	private String tipo;

	@Column(name = "evaluacion", length = 50)
	private String evaluacion;

	@Column(name = "ciudad", length = 100)
	private String ciudad;

	@Column(name = "zona", length = 50)
	private String zona;

	@Column(name = "id_agencia")
	private Long idAgencia;

	@Column(name = "nro_visita")
	private Long nroVisita;

	@Column(name = "nro_visita_estacion")
	private Long nroVisitaEstacion;

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

	@Column(name = "mes_01_c", length = 20)
	private String mes01C;

	@Column(name = "mes_02_c", length = 20)
	private String mes02C;

	@Column(name = "mes_03_c", length = 20)
	private String mes03C;

	@Column(name = "mes_04_c", length = 20)
	private String mes04C;

	@Column(name = "mes_05_c", length = 20)
	private String mes05C;

	@Column(name = "mes_06_c", length = 20)
	private String mes06C;

	@Column(name = "mes_07_c", length = 20)
	private String mes07C;

	@Column(name = "mes_08_c", length = 20)
	private String mes08C;

	@Column(name = "mes_09_c", length = 20)
	private String mes09C;

	@Column(name = "mes_10_c", length = 20)
	private String mes10C;

	@Column(name = "mes_11_c", length = 20)
	private String mes11C;

	@Column(name = "mes_12_c", length = 20)
	private String mes12C;

	@Column(name = "mes_01_t")
	private Long mes01T;

	@Column(name = "mes_02_t")
	private Long mes02T;

	@Column(name = "mes_03_t")
	private Long mes03T;

	@Column(name = "mes_04_t")
	private Long mes04T;

	@Column(name = "mes_05_t")
	private Long mes05T;

	@Column(name = "mes_06_t")
	private Long mes06T;

	@Column(name = "mes_07_t")
	private Long mes07T;

	@Column(name = "mes_08_t")
	private Long mes08T;

	@Column(name = "mes_09_t")
	private Long mes09T;

	@Column(name = "mes_10_t")
	private Long mes10T;

	@Column(name = "mes_11_t")
	private Long mes11T;

	@Column(name = "mes_12_t")
	private Long mes12T;

	@Column(name = "mes_01_v")
	private Long mes01V;

	@Column(name = "mes_02_v")
	private Long mes02V;

	@Column(name = "mes_03_v")
	private Long mes03V;

	@Column(name = "mes_04_v")
	private Long mes04V;

	@Column(name = "mes_05_v")
	private Long mes05V;

	@Column(name = "mes_06_v")
	private Long mes06V;

	@Column(name = "mes_07_v")
	private Long mes07V;

	@Column(name = "mes_08_v")
	private Long mes08V;

	@Column(name = "mes_09_v")
	private Long mes09V;

	@Column(name = "mes_10_v")
	private Long mes10V;

	@Column(name = "mes_11_v")
	private Long mes11V;

	@Column(name = "mes_12_v")
	private Long mes12V;

	@Column(name = "dias")
	private Long dias;

	@Column(name = "color", length = 20)
	private String color;

	public ReportePlanificacionInventarioEt() {
		this.dias = 0L;
		this.nroVisita = 0L;
		this.idAgencia = 0L;
		this.color = "#FFFFFF";
		this.idCheckListEjecucion = 0L;
	}

	public Long getIdReportePlanificacionInventario() {
		return idReportePlanificacionInventario;
	}

	public void setIdReportePlanificacionInventario(Long idReportePlanificacionInventario) {
		this.idReportePlanificacionInventario = idReportePlanificacionInventario;
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

	public String getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(String evaluacion) {
		this.evaluacion = evaluacion;
	}

	public Long getNroVisita() {
		return nroVisita;
	}

	public void setNroVisita(Long nroVisita) {
		this.nroVisita = nroVisita;
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

	public Long getMes01T() {
		return mes01T;
	}

	public void setMes01T(Long mes01t) {
		mes01T = mes01t;
	}

	public Long getMes02T() {
		return mes02T;
	}

	public void setMes02T(Long mes02t) {
		mes02T = mes02t;
	}

	public Long getMes03T() {
		return mes03T;
	}

	public void setMes03T(Long mes03t) {
		mes03T = mes03t;
	}

	public Long getMes04T() {
		return mes04T;
	}

	public void setMes04T(Long mes04t) {
		mes04T = mes04t;
	}

	public Long getMes05T() {
		return mes05T;
	}

	public void setMes05T(Long mes05t) {
		mes05T = mes05t;
	}

	public Long getMes06T() {
		return mes06T;
	}

	public void setMes06T(Long mes06t) {
		mes06T = mes06t;
	}

	public Long getMes07T() {
		return mes07T;
	}

	public void setMes07T(Long mes07t) {
		mes07T = mes07t;
	}

	public Long getMes08T() {
		return mes08T;
	}

	public void setMes08T(Long mes08t) {
		mes08T = mes08t;
	}

	public Long getMes09T() {
		return mes09T;
	}

	public void setMes09T(Long mes09t) {
		mes09T = mes09t;
	}

	public Long getMes10T() {
		return mes10T;
	}

	public void setMes10T(Long mes10t) {
		mes10T = mes10t;
	}

	public Long getMes11T() {
		return mes11T;
	}

	public void setMes11T(Long mes11t) {
		mes11T = mes11t;
	}

	public Long getMes12T() {
		return mes12T;
	}

	public void setMes12T(Long mes12t) {
		mes12T = mes12t;
	}

	public Long getMes01V() {
		return mes01V;
	}

	public void setMes01V(Long mes01v) {
		mes01V = mes01v;
	}

	public Long getMes02V() {
		return mes02V;
	}

	public void setMes02V(Long mes02v) {
		mes02V = mes02v;
	}

	public Long getMes03V() {
		return mes03V;
	}

	public void setMes03V(Long mes03v) {
		mes03V = mes03v;
	}

	public Long getMes04V() {
		return mes04V;
	}

	public void setMes04V(Long mes04v) {
		mes04V = mes04v;
	}

	public Long getMes05V() {
		return mes05V;
	}

	public void setMes05V(Long mes05v) {
		mes05V = mes05v;
	}

	public Long getMes06V() {
		return mes06V;
	}

	public void setMes06V(Long mes06v) {
		mes06V = mes06v;
	}

	public Long getMes07V() {
		return mes07V;
	}

	public void setMes07V(Long mes07v) {
		mes07V = mes07v;
	}

	public Long getMes08V() {
		return mes08V;
	}

	public void setMes08V(Long mes08v) {
		mes08V = mes08v;
	}

	public Long getMes09V() {
		return mes09V;
	}

	public void setMes09V(Long mes09v) {
		mes09V = mes09v;
	}

	public Long getMes10V() {
		return mes10V;
	}

	public void setMes10V(Long mes10v) {
		mes10V = mes10v;
	}

	public Long getMes11V() {
		return mes11V;
	}

	public void setMes11V(Long mes11v) {
		mes11V = mes11v;
	}

	public Long getMes12V() {
		return mes12V;
	}

	public void setMes12V(Long mes12v) {
		mes12V = mes12v;
	}

	public Long getDias() {
		return dias;
	}

	public void setDias(Long dias) {
		this.dias = dias;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Long getNroVisitaEstacion() {
		return nroVisitaEstacion;
	}

	public void setNroVisitaEstacion(Long nroVisitaEstacion) {
		this.nroVisitaEstacion = nroVisitaEstacion;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof ReportePlanificacionInventarioEt) {

			ReportePlanificacionInventarioEt other = (ReportePlanificacionInventarioEt) obj;
			if (this.idReportePlanificacionInventario == null)
				return this == other;

			return this.idReportePlanificacionInventario.equals(other.idReportePlanificacionInventario);
		}
		return false;
	}

}
