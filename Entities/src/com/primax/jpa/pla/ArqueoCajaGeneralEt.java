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
@Table(name = "ARQUEO_CAJA_GENERAL_ET")
@Audited
public class ArqueoCajaGeneralEt extends EntityBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "sec_arqueo_caja_general_et", sequenceName = "seq_arqueo_caja_general_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_arqueo_caja_general_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_arqueo_caja_general")
	private Long idArqueoCajaGeneral;

	@Column(name = "orden")
	private Long orden;

	@Column(name = "sub_descripcion", length = 100)
	private String subDescripcion;

	@Column(name = "descripcion", length = 100)
	private String descripcion;

	@Column(name = "denominacion")
	private Double denominacion;

	@Column(name = "caja_0")
	private Double caja0;

	@Column(name = "cantidad")
	private Long cantidad;

	@Column(name = "sub_total")
	private Double subTotal;

	@Column(name = "bloqueado")
	private boolean bloqueado;

	@ManyToOne
	@JoinColumn(name = "id_parametro_rubro")
	private ParametrosGeneralesEt parametroRubro;

	@ManyToOne
	@JoinColumn(name = "id_check_list_kpi_ejecucion")
	private CheckListKpiEjecucionEt checkListKpiEjecucion;

	public ArqueoCajaGeneralEt() {
		this.orden = 0L;
		this.caja0 = 0.0D;
		this.cantidad = 0L;
		this.descripcion = "";
		this.bloqueado = false;
		this.denominacion = 0.0D;
	}

	public Long getIdArqueoCajaGeneral() {
		return idArqueoCajaGeneral;
	}

	public void setIdArqueoCajaGeneral(Long idArqueoCajaGeneral) {
		this.idArqueoCajaGeneral = idArqueoCajaGeneral;
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

	public Double getCaja0() {
		return caja0;
	}

	public void setCaja0(Double caja0) {
		this.caja0 = caja0;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public CheckListKpiEjecucionEt getCheckListKpiEjecucion() {
		return checkListKpiEjecucion;
	}

	public void setCheckListKpiEjecucion(CheckListKpiEjecucionEt checkListKpiEjecucion) {
		this.checkListKpiEjecucion = checkListKpiEjecucion;
	}

	public String getSubDescripcion() {
		return subDescripcion;
	}

	public void setSubDescripcion(String subDescripcion) {
		this.subDescripcion = subDescripcion;
	}

	public Double getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(Double denominacion) {
		this.denominacion = denominacion;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public boolean isBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	public ParametrosGeneralesEt getParametroRubro() {
		return parametroRubro;
	}

	public void setParametroRubro(ParametrosGeneralesEt parametroRubro) {
		this.parametroRubro = parametroRubro;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof ArqueoCajaGeneralEt) {

			ArqueoCajaGeneralEt other = (ArqueoCajaGeneralEt) obj;
			if (this.idArqueoCajaGeneral == null)
				return this == other;

			return this.idArqueoCajaGeneral.equals(other.idArqueoCajaGeneral);
		}
		return false;
	}

}
