package com.primax.jpa.pla;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.param.ProcesoEt;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "CHECK_LIST_PROCESO_EJECUCION_ET")
@Audited
public class CheckListProcesoEjecucionEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_check_list_proceso_ejecucion_et", sequenceName = "seq_check_list_proceso_ejecucion_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_check_list_proceso_ejecucion_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_check_list_proceso_ejecucion")
	private Long idCheckListProcesoEjecucion;

	@Column(name = "orden")
	private Long orden;

	@ManyToOne
	@JoinColumn(name = "id_proceso")
	private ProcesoEt proceso;
	
	@ManyToOne
	@JoinColumn(name = "id_check_list_proceso")
	private CheckListProcesoEt checkListProceso;

	@Column(name = "descripcion", length = 100)
	private String descripcion;

	@Column(name = "visualizar")
	private boolean visualizar;

	/**
	 * Reporte Informe Dinámicos
	 */

	@Column(name = "visualizar_reporte")
	private boolean visualizarReporte;

	@Column(name = "visualizar_tabla")
	private boolean visualizarTabla;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "checkListProcesoEjecucion", fetch = FetchType.LAZY)
	@OrderBy("orden,idCheckListKpiEjecucion")
	@Where(clause = "estado = 'ACT'")
	private List<CheckListKpiEjecucionEt> checkListKpiEjecucion;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "checkListProcesoEjecucion", fetch = FetchType.LAZY)
	@OrderBy("idCheckListKpiEjecucionA")
	@Where(clause = "estado = 'ACT'")
	private List<CheckListKpiEjecucionAEt> checkListKpiEjecucionA;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "checkListProcesoEjecucion", fetch = FetchType.LAZY)
	@OrderBy("idCheckListKpiEjecucionB")
	@Where(clause = "estado = 'ACT'")
	private List<CheckListKpiEjecucionBEt> checkListKpiEjecucionB;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "checkListProcesoEjecucion", fetch = FetchType.LAZY)
	@OrderBy("idCheckListKpiEjecucionC")
	@Where(clause = "estado = 'ACT'")
	private List<CheckListKpiEjecucionCEt> checkListKpiEjecucionC;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "checkListProcesoEjecucion", fetch = FetchType.LAZY)
	@OrderBy("idCheckListProcesoEjecucionFormulario")
	@Where(clause = "estado = 'ACT'")
	private List<CheckListProcesoEjecucionFormularioEt> checkListProcesoEjecucionFormulario;

	@ManyToOne
	@JoinColumn(name = "id_check_list_ejecucion")
	private CheckListEjecucionEt checkListEjecucion;

	@Transient
	private Long idCheckListProcesoEjecucionA;

	@Column(name = "formulario")
	private boolean formulario;

	public CheckListProcesoEjecucionEt() {
		this.formulario = false;
		this.visualizar = true;
		this.visualizarTabla = false;
		this.visualizarReporte = false;

	}

	public Long getIdCheckListProcesoEjecucion() {
		return idCheckListProcesoEjecucion;
	}

	public void setIdCheckListProcesoEjecucion(Long idCheckListProcesoEjecucion) {
		this.idCheckListProcesoEjecucion = idCheckListProcesoEjecucion;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<CheckListKpiEjecucionEt> getCheckListKpiEjecucion() {
		return checkListKpiEjecucion;
	}

	public void setCheckListKpiEjecucion(List<CheckListKpiEjecucionEt> checkListKpiEjecucion) {
		this.checkListKpiEjecucion = checkListKpiEjecucion;
	}

	public CheckListEjecucionEt getCheckListEjecucion() {
		return checkListEjecucion;
	}

	public void setCheckListEjecucion(CheckListEjecucionEt checkListEjecucion) {
		this.checkListEjecucion = checkListEjecucion;
	}

	public ProcesoEt getProceso() {
		return proceso;
	}

	public void setProceso(ProcesoEt proceso) {
		this.proceso = proceso;
	}

	public boolean isVisualizar() {
		return visualizar;
	}

	public void setVisualizar(boolean visualizar) {
		this.visualizar = visualizar;
	}

	public Long getIdCheckListProcesoEjecucionA() {
		return idCheckListProcesoEjecucionA;
	}

	public void setIdCheckListProcesoEjecucionA(Long idCheckListProcesoEjecucionA) {
		this.idCheckListProcesoEjecucionA = idCheckListProcesoEjecucionA;
	}

	public List<CheckListKpiEjecucionAEt> getCheckListKpiEjecucionA() {
		return checkListKpiEjecucionA;
	}

	public void setCheckListKpiEjecucionA(List<CheckListKpiEjecucionAEt> checkListKpiEjecucionA) {
		this.checkListKpiEjecucionA = checkListKpiEjecucionA;
	}

	public List<CheckListKpiEjecucionBEt> getCheckListKpiEjecucionB() {
		return checkListKpiEjecucionB;
	}

	public void setCheckListKpiEjecucionB(List<CheckListKpiEjecucionBEt> checkListKpiEjecucionB) {
		this.checkListKpiEjecucionB = checkListKpiEjecucionB;
	}

	public List<CheckListKpiEjecucionCEt> getCheckListKpiEjecucionC() {
		return checkListKpiEjecucionC;
	}

	public void setCheckListKpiEjecucionC(List<CheckListKpiEjecucionCEt> checkListKpiEjecucionC) {
		this.checkListKpiEjecucionC = checkListKpiEjecucionC;
	}

	public List<CheckListProcesoEjecucionFormularioEt> getCheckListProcesoEjecucionFormulario() {
		return checkListProcesoEjecucionFormulario;
	}

	public void setCheckListProcesoEjecucionFormulario(List<CheckListProcesoEjecucionFormularioEt> checkListProcesoEjecucionFormulario) {
		this.checkListProcesoEjecucionFormulario = checkListProcesoEjecucionFormulario;
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

	public boolean isFormulario() {
		return formulario;
	}

	public void setFormulario(boolean formulario) {
		this.formulario = formulario;
	}

	public CheckListProcesoEt getCheckListProceso() {
		return checkListProceso;
	}

	public void setCheckListProceso(CheckListProcesoEt checkListProceso) {
		this.checkListProceso = checkListProceso;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CheckListProcesoEjecucionEt) {
			CheckListProcesoEjecucionEt other = (CheckListProcesoEjecucionEt) obj;

			if (this.idCheckListProcesoEjecucion == null)
				return this == other;

			if (this.idCheckListProcesoEjecucion.equals(other.idCheckListProcesoEjecucion))
				return true;
		}
		return false;

	}

}
