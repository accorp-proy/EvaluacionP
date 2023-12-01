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
@Table(name = "ARQUEO_CAJA_FUERTE_ET")
@Audited
public class ArqueoCajaFuerteEt extends EntityBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "sec_arqueo_caja_fuerte_et", sequenceName = "seq_arqueo_caja_fuerte_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_arqueo_caja_fuerte_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_arqueo_caja_fuerte")
	private Long idArqueoCajaFuerte;

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

	@Column(name = "descripcion_reporte", length = 100)
	private String descripcionReporte;

	@Column(name = "cantidad")
	private Long cantidad;

	@Column(name = "valor_blindado")
	private Double valorBlindado;

	@Column(name = "valor_vendedor")
	private Double valorVendedor;

	@Column(name = "valor_diferencia")
	private Double valorDiferencia;

	@Column(name = "valor_total")
	private Double valorTotal;

	@ManyToOne
	@JoinColumn(name = "id_check_list_kpi_ejecucion")
	private CheckListKpiEjecucionEt checkListKpiEjecucion;

	public ArqueoCajaFuerteEt() {
		this.orden = 0L;
		this.cantidad = 0L;
		this.ordenReporte = 0L;
		this.valorTotal = 0.0D;
		this.valorBlindado = 0.0D;
		this.valorVendedor = 0.0D;
		this.valorDiferencia = 0.0D;
	}

	public Long getIdArqueoCajaFuerte() {
		return idArqueoCajaFuerte;
	}

	public void setIdArqueoCajaFuerte(Long idArqueoCajaFuerte) {
		this.idArqueoCajaFuerte = idArqueoCajaFuerte;
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

	public Double getValorBlindado() {
		return valorBlindado;
	}

	public void setValorBlindado(Double valorBlindado) {
		this.valorBlindado = valorBlindado;
	}

	public Double getValorVendedor() {
		return valorVendedor;
	}

	public void setValorVendedor(Double valorVendedor) {
		this.valorVendedor = valorVendedor;
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

	public Double getValorDiferencia() {
		return valorDiferencia;
	}

	public void setValorDiferencia(Double valorDiferencia) {
		this.valorDiferencia = valorDiferencia;
	}

	public String getDescripcionReporte() {
		return descripcionReporte;
	}

	public void setDescripcionReporte(String descripcionReporte) {
		this.descripcionReporte = descripcionReporte;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof ArqueoCajaFuerteEt) {

			ArqueoCajaFuerteEt other = (ArqueoCajaFuerteEt) obj;
			if (this.idArqueoCajaFuerte == null)
				return this == other;

			return this.idArqueoCajaFuerte.equals(other.idArqueoCajaFuerte);
		}
		return false;
	}

}
