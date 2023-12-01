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
@Table(name = "CHECK_LIST_PROCESO_ET")
@Audited
public class CheckListProcesoEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_check_list_proceso_et", sequenceName = "seq_check_list_proceso_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_check_list_proceso_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_check_list_proceso")
	private Long idCheckListProceso;

	@Column(name = "orden")
	private Long orden;

	@ManyToOne
	@JoinColumn(name = "id_proceso")
	private ProcesoEt proceso;

	@Column(name = "descripcion", length = 100)
	private String descripcion;

	@Column(name = "nivel_riego")
	private Long nivelRiesgo;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "checkListProceso", fetch = FetchType.LAZY)
	@OrderBy("orden asc")
	@Where(clause = "estado = 'ACT'")
	private List<CheckListKpiEt> checkListKpi;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "checkListProceso", fetch = FetchType.LAZY)
	@OrderBy("idCheckListProcesoFormulario ")
	@Where(clause = "estado = 'ACT'")
	private List<CheckListProcesoFormularioEt> checkListProcesoFormulario;

	@ManyToOne
	@JoinColumn(name = "id_check_list")
	private CheckListEt checkList;

	@Transient
	private String nombreAuditor;

	/**
	 * Reporte Informe Dinámicos
	 */

	@Column(name = "visualizar_reporte")
	private boolean visualizarReporte;

	@Column(name = "visualizar_tabla")
	private boolean visualizarTabla;

	public CheckListProcesoEt() {
		this.nivelRiesgo = 1L;
		this.visualizarTabla = false;
		this.visualizarReporte = false;
	}

	public Long getIdCheckListProceso() {
		return idCheckListProceso;
	}

	public void setIdCheckListProceso(Long idCheckListProceso) {
		this.idCheckListProceso = idCheckListProceso;
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

	public List<CheckListKpiEt> getCheckListKpi() {
		return checkListKpi;
	}

	public void setCheckListKpi(List<CheckListKpiEt> checkListKpi) {
		this.checkListKpi = checkListKpi;
	}

	public CheckListEt getCheckList() {
		return checkList;
	}

	public void setCheckList(CheckListEt checkList) {
		this.checkList = checkList;
	}

	public ProcesoEt getProceso() {
		return proceso;
	}

	public void setProceso(ProcesoEt proceso) {
		this.proceso = proceso;
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

	public List<CheckListProcesoFormularioEt> getCheckListProcesoFormulario() {
		return checkListProcesoFormulario;
	}

	public void setCheckListProcesoFormulario(List<CheckListProcesoFormularioEt> checkListProcesoFormulario) {
		this.checkListProcesoFormulario = checkListProcesoFormulario;
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

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CheckListProcesoEt) {
			CheckListProcesoEt other = (CheckListProcesoEt) obj;

			if (this.idCheckListProceso == null)
				return this == other;

			if (this.idCheckListProceso.equals(other.idCheckListProceso))
				return true;
		}
		return false;

	}

}
