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
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "ARQUEO_CAJA_ET")
@Audited
public class ArqueoCajaEt extends EntityBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "sec_arqueo_caja_et", sequenceName = "seq_arqueo_caja_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_arqueo_caja_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_arqueo_caja")
	private Long idArqueoCaja;

	@Column(name = "orden")
	private Long orden;

	@Column(name = "orden_reporte")
	private Long ordenReporte;

	@Column(name = "orden_reporte_1")
	private String ordenReporte1;

	@Column(name = "sub_descripcion", length = 100)
	private String subDescripcion;

	@Column(name = "descripcion", length = 100)
	private String descripcion;

	@Column(name = "cantidad")
	private Long cantidad;

	@Column(name = "valor_sub_total")
	private Double valorSubTotal;

	@Column(name = "valor_total")
	private Double valorTotal;

	@ManyToOne
	@JoinColumn(name = "id_check_list_kpi_ejecucion")
	private CheckListKpiEjecucionEt checkListKpiEjecucion;

	public ArqueoCajaEt() {
		this.orden = 0L;
		this.cantidad = 0L;
		this.ordenReporte = 0L;
		this.valorTotal = 0.0D;
		this.valorSubTotal = 0.0D;
	}

	public Long getIdArqueoCaja() {
		return idArqueoCaja;
	}

	public void setIdArqueoCaja(Long idArqueoCaja) {
		this.idArqueoCaja = idArqueoCaja;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CheckListKpiEjecucionEt getCheckListKpiEjecucion() {
		return checkListKpiEjecucion;
	}

	public void setCheckListKpiEjecucion(CheckListKpiEjecucionEt checkListKpiEjecucion) {
		this.checkListKpiEjecucion = checkListKpiEjecucion;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getSubDescripcion() {
		return subDescripcion;
	}

	public void setSubDescripcion(String subDescripcion) {
		this.subDescripcion = subDescripcion;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Double getValorSubTotal() {
		return valorSubTotal;
	}

	public void setValorSubTotal(Double valorSubTotal) {
		this.valorSubTotal = valorSubTotal;
	}

	public Long getOrdenReporte() {
		return ordenReporte;
	}

	public void setOrdenReporte(Long ordenReporte) {
		this.ordenReporte = ordenReporte;
	}

	public String getOrdenReporte1() {
		return ordenReporte1;
	}

	public void setOrdenReporte1(String ordenReporte1) {
		this.ordenReporte1 = ordenReporte1;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof ArqueoCajaEt) {

			ArqueoCajaEt other = (ArqueoCajaEt) obj;
			if (this.idArqueoCaja == null)
				return this == other;

			return this.idArqueoCaja.equals(other.idArqueoCaja);
		}
		return false;
	}

}
