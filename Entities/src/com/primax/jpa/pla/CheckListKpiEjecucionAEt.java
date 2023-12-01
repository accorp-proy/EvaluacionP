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

import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.param.ParametrosGeneralesEt;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "CHECK_LIST_KPI_EJECUCION_A_ET")
@Audited
public class CheckListKpiEjecucionAEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_check_list_kpi_ejecucion_a_et", sequenceName = "seq_check_list_kpi_ejecucion_a_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_check_list_kpi_ejecucion_a_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_check_list_kpi_ejecucion_a")
	private Long idCheckListKpiEjecucionA;

	@Column(name = "orden")
	private Long orden;

	@Column(name = "descripcion", length = 500)
	private String descripcion;

	@Column(name = "observacion", length = 500)
	private String observacion;

	@ManyToOne
	@JoinColumn(name = "id_parametro_kpi")
	private ParametrosGeneralesEt parametroKpi;

	@ManyToOne
	@JoinColumn(name = "id_parametro_criterio")
	private ParametrosGeneralesEt parametroCriterio;

	@ManyToOne
	@JoinColumn(name = "id_check_list_proceso_ejecucion")
	private CheckListProcesoEjecucionEt checkListProcesoEjecucion;

	@Column(name = "cumple")
	private boolean cumple;

	public CheckListKpiEjecucionAEt() {
		this.orden = 0L;
		this.cumple = false;
		this.observacion = "";
	}

	public Long getIdCheckListKpiEjecucionA() {
		return idCheckListKpiEjecucionA;
	}

	public void setIdCheckListKpiEjecucionA(Long idCheckListKpiEjecucionA) {
		this.idCheckListKpiEjecucionA = idCheckListKpiEjecucionA;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public ParametrosGeneralesEt getParametroKpi() {
		return parametroKpi;
	}

	public void setParametroKpi(ParametrosGeneralesEt parametroKpi) {
		this.parametroKpi = parametroKpi;
	}

	public boolean isCumple() {
		return cumple;
	}

	public void setCumple(boolean cumple) {
		this.cumple = cumple;
	}

	public CheckListProcesoEjecucionEt getCheckListProcesoEjecucion() {
		return checkListProcesoEjecucion;
	}

	public void setCheckListProcesoEjecucion(CheckListProcesoEjecucionEt checkListProcesoEjecucion) {
		this.checkListProcesoEjecucion = checkListProcesoEjecucion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public ParametrosGeneralesEt getParametroCriterio() {
		return parametroCriterio;
	}

	public void setParametroCriterio(ParametrosGeneralesEt parametroCriterio) {
		this.parametroCriterio = parametroCriterio;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CheckListKpiEjecucionAEt) {
			CheckListKpiEjecucionAEt other = (CheckListKpiEjecucionAEt) obj;
			if (this.idCheckListKpiEjecucionA == null)
				return this == other;

			if (this.idCheckListKpiEjecucionA.equals(other.idCheckListKpiEjecucionA))
				return true;
		}
		return false;

	}

}
