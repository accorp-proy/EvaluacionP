package com.primax.jpa.pla;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.param.CriterioEvaluacionEt;
import com.primax.jpa.param.KPICriticoEt;
import com.primax.jpa.param.ProcesoDetalleEt;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "CHECK_LIST_KPI_ET")
@Audited
public class CheckListKpiEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_check_list_kpi_et", sequenceName = "seq_check_list_kpi_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_check_list_kpi_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_check_list_kpi")
	private Long idCheckListKpi;

	@Column(name = "orden")
	private Long orden;

	@Column(name = "puntaje")
	private Long puntaje;

	@Column(name = "esfuerzo")
	private Long esfuerzo;

	@Column(name = "descripcion", length = 1000)
	private String descripcion;

	@ManyToOne
	@JoinColumn(name = "id_proceso_detalle")
	private ProcesoDetalleEt procesoDetalle;

	@ManyToOne
	@JoinColumn(name = "id_check_list_proceso")
	private CheckListProcesoEt checkListProceso;

	@ManyToOne
	@JoinColumn(name = "id_kpi_critico")
	private KPICriticoEt kPICritico;

	@ManyToOne
	@JoinColumn(name = "id_criterio_evaluacion")
	private CriterioEvaluacionEt criterioEvaluacion;

	@ManyToOne
	@JoinColumn(name = "id_check_list_criterio")
	private CheckListCriterioEt checkListCriterio;

	@Column(name = "nivel_riego")
	private Long nivelRiesgo;

	@Transient
	private String nombreAuditor;

	/**
	 * Reporte Informe Dinámicos
	 */

	@Column(name = "orden_reporte", length = 10)
	private String ordenReporte;

	@Column(name = "orden_reporte_0", length = 10)
	private String ordenReporte0;

	@Column(name = "visualizar_reporte")
	private boolean visualizarReporte;

	@Column(name = "visualizar_tabla")
	private boolean visualizarTabla;

	@Column(name = "seccion", length = 500)
	private String seccion;

	@Column(name = "seccion_reemplazo", length = 500)
	private String seccionReemplazo;

	@Column(name = "seccion_0", length = 500)
	private String seccion0;

	@Column(name = "seccion_0_reemplazo", length = 500)
	private String seccion0Reemplazo;

	public CheckListKpiEt() {
		this.seccion = "";
		this.seccion0 = "";
		this.nivelRiesgo = 1L;
		this.ordenReporte = "";
		this.ordenReporte0 = "";
		this.seccionReemplazo = "";
		this.seccion0Reemplazo = "";
		this.visualizarTabla = false;
		this.visualizarReporte = false;
	}

	public Long getIdCheckListKpi() {
		return idCheckListKpi;
	}

	public void setIdCheckListKpi(Long idCheckListKpi) {
		this.idCheckListKpi = idCheckListKpi;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public ProcesoDetalleEt getProcesoDetalle() {
		return procesoDetalle;
	}

	public void setProcesoDetalle(ProcesoDetalleEt procesoDetalle) {
		this.procesoDetalle = procesoDetalle;
	}

	public CheckListProcesoEt getCheckListProceso() {
		return checkListProceso;
	}

	public void setCheckListProceso(CheckListProcesoEt checkListProceso) {
		this.checkListProceso = checkListProceso;
	}

	public KPICriticoEt getkPICritico() {
		return kPICritico;
	}

	public void setkPICritico(KPICriticoEt kPICritico) {
		this.kPICritico = kPICritico;
	}

	public CriterioEvaluacionEt getCriterioEvaluacion() {
		return criterioEvaluacion;
	}

	public void setCriterioEvaluacion(CriterioEvaluacionEt criterioEvaluacion) {
		this.criterioEvaluacion = criterioEvaluacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(Long puntaje) {
		this.puntaje = puntaje;
	}

	public Long getEsfuerzo() {
		return esfuerzo;
	}

	public void setEsfuerzo(Long esfuerzo) {
		this.esfuerzo = esfuerzo;
	}

	public Long getNivelRiesgo() {
		return nivelRiesgo;
	}

	public void setNivelRiesgo(Long nivelRiesgo) {
		this.nivelRiesgo = nivelRiesgo;
	}

	public String getNombreAuditor() {
		return nombreAuditor;
	}

	public void setNombreAuditor(String nombreAuditor) {
		this.nombreAuditor = nombreAuditor;
	}

	public CheckListCriterioEt getCheckListCriterio() {
		return checkListCriterio;
	}

	public void setCheckListCriterio(CheckListCriterioEt checkListCriterio) {
		this.checkListCriterio = checkListCriterio;
	}

	public String getOrdenReporte() {
		return ordenReporte;
	}

	public void setOrdenReporte(String ordenReporte) {
		this.ordenReporte = ordenReporte;
	}

	public String getOrdenReporte0() {
		return ordenReporte0;
	}

	public void setOrdenReporte0(String ordenReporte0) {
		this.ordenReporte0 = ordenReporte0;
	}

	public boolean isVisualizarReporte() {
		return visualizarReporte;
	}

	public void setVisualizarReporte(boolean visualizarReporte) {
		this.visualizarReporte = visualizarReporte;
	}

	public boolean isVisualizarTabla() {
		return visualizarTabla;
	}

	public void setVisualizarTabla(boolean visualizarTabla) {
		this.visualizarTabla = visualizarTabla;
	}

	public String getSeccion() {
		return seccion;
	}

	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}

	public String getSeccionReemplazo() {
		return seccionReemplazo;
	}

	public void setSeccionReemplazo(String seccionReemplazo) {
		this.seccionReemplazo = seccionReemplazo;
	}

	public String getSeccion0() {
		return seccion0;
	}

	public void setSeccion0(String seccion0) {
		this.seccion0 = seccion0;
	}

	public String getSeccion0Reemplazo() {
		return seccion0Reemplazo;
	}

	public void setSeccion0Reemplazo(String seccion0Reemplazo) {
		this.seccion0Reemplazo = seccion0Reemplazo;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CheckListKpiEt) {
			CheckListKpiEt other = (CheckListKpiEt) obj;
			if (this.idCheckListKpi == null)
				return this == other;

			if (this.idCheckListKpi.equals(other.idCheckListKpi))
				return true;
		}
		return false;

	}

}
