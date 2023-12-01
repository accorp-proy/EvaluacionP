package com.primax.jpa.pla;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.param.ParametrosGeneralesEt;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "CHECK_LIST_KPI_EJECUCION_B_ET")
@Audited
public class CheckListKpiEjecucionBEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_check_list_kpi_ejecucion_b_et", sequenceName = "seq_check_list_kpi_ejecucion_b_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_check_list_kpi_ejecucion_b_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_check_list_kpi_ejecucion_b")
	private Long idCheckListKpiEjecucionB;

	@Column(name = "orden")
	private Long orden;

	@Column(name = "descripcion", length = 500)
	private String descripcion;

	@ManyToOne
	@JoinColumn(name = "id_parametro_criterio")
	private ParametrosGeneralesEt parametroCriterio;

	@ManyToOne
	@JoinColumn(name = "id_check_list_proceso_ejecucion")
	private CheckListProcesoEjecucionEt checkListProcesoEjecucion;

	@Column(name = "percha", length = 100)
	private String percha;

	@Column(name = "producto", length = 100)
	private String producto;

	@Column(name = "cantidad")
	private Long cantidad;

	@Column(name = "fecha_caducidad")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCaducidad;

	@Column(name = "bloqueo")
	private boolean bloqueo;

	public CheckListKpiEjecucionBEt() {
		this.orden = 0L;
		this.producto = "";
		this.cantidad = 0L;
		this.bloqueo = true;
		this.fechaCaducidad = new Date();
	}

	public Long getIdCheckListKpiEjecucionB() {
		return idCheckListKpiEjecucionB;
	}

	public void setIdCheckListKpiEjecucionB(Long idCheckListKpiEjecucionB) {
		this.idCheckListKpiEjecucionB = idCheckListKpiEjecucionB;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public CheckListProcesoEjecucionEt getCheckListProcesoEjecucion() {
		return checkListProcesoEjecucion;
	}

	public void setCheckListProcesoEjecucion(CheckListProcesoEjecucionEt checkListProcesoEjecucion) {
		this.checkListProcesoEjecucion = checkListProcesoEjecucion;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	public String getPercha() {
		return percha;
	}

	public void setPercha(String percha) {
		this.percha = percha;
	}

	public ParametrosGeneralesEt getParametroCriterio() {
		return parametroCriterio;
	}

	public void setParametroCriterio(ParametrosGeneralesEt parametroCriterio) {
		this.parametroCriterio = parametroCriterio;
	}

	public boolean isBloqueo() {
		return bloqueo;
	}

	public void setBloqueo(boolean bloqueo) {
		this.bloqueo = bloqueo;
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
		if (obj instanceof CheckListKpiEjecucionBEt) {
			CheckListKpiEjecucionBEt other = (CheckListKpiEjecucionBEt) obj;
			if (this.idCheckListKpiEjecucionB == null)
				return this == other;

			if (this.idCheckListKpiEjecucionB.equals(other.idCheckListKpiEjecucionB))
				return true;
		}
		return false;

	}

}
