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
@Table(name = "ARQUEO_FONDO_SUELTO_ET")
@Audited
public class ArqueoFondoSueltoEt extends EntityBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "sec_arqueo_fondo_suelto_et", sequenceName = "seq_arqueo_fondo_suelto_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_arqueo_fondo_suelto_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_arqueo_fondo_suelto")
	private Long idArqueoFondoSuelto;

	@Column(name = "orden")
	private Long orden;

	@Column(name = "sub_descripcion", length = 100)
	private String subDescripcion;

	@Column(name = "descripcion", length = 100)
	private String descripcion;

	@Column(name = "denominacion")
	private Double denominacion;

	@Column(name = "caja_0")
	private Long caja0;

	@Column(name = "caja_1")
	private Long caja1;

	@Column(name = "caja_2")
	private Long caja2;

	@Column(name = "valor_total")
	private Double valorTotal;

	@ManyToOne
	@JoinColumn(name = "id_check_list_kpi_ejecucion")
	private CheckListKpiEjecucionEt checkListKpiEjecucion;

	public ArqueoFondoSueltoEt() {
		this.orden = 0L;
		this.caja0 = 0L;
		this.caja1 = 0L;
		this.caja2 = 0L;
		this.descripcion = "";
		this.valorTotal = 0.0D;
		this.denominacion = 0.0D;
	}

	public Long getIdArqueoFondoSuelto() {
		return idArqueoFondoSuelto;
	}

	public void setIdArqueoFondoSuelto(Long idArqueoFondoSuelto) {
		this.idArqueoFondoSuelto = idArqueoFondoSuelto;
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

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
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

	public Long getCaja0() {
		return caja0;
	}

	public void setCaja0(Long caja0) {
		this.caja0 = caja0;
	}

	public Long getCaja1() {
		return caja1;
	}

	public void setCaja1(Long caja1) {
		this.caja1 = caja1;
	}

	public Long getCaja2() {
		return caja2;
	}

	public void setCaja2(Long caja2) {
		this.caja2 = caja2;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof ArqueoFondoSueltoEt) {
			ArqueoFondoSueltoEt other = (ArqueoFondoSueltoEt) obj;
			if (this.idArqueoFondoSuelto == null)
				return this == other;

			return this.idArqueoFondoSuelto.equals(other.idArqueoFondoSuelto);
		}
		return false;
	}

}
