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
@Table(name = "ARQUEO_CAJA_PROMOTOR_ET")
@Audited
public class ArqueoCajaPromotorEt extends EntityBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "sec_arqueo_caja_promotor_et", sequenceName = "seq_arqueo_caja_promotor_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_arqueo_caja_promotor_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_arqueo_caja_promotor")
	private Long idArqueoCajaPromotor;

	@Column(name = "promotor", length = 100)
	private String promotor;

	@Column(name = "cantidad")
	private Long cantidad;

	@Column(name = "valor_faltante")
	private Double valorFaltante;

	@Column(name = "fecha_revision")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaRevision;

	@ManyToOne
	@JoinColumn(name = "id_parametro_medio_pago")
	private ParametrosGeneralesEt parametroMedioPago;

	@ManyToOne
	@JoinColumn(name = "id_check_list_kpi_ejecucion")
	private CheckListKpiEjecucionEt checkListKpiEjecucion;

	public ArqueoCajaPromotorEt() {
		this.cantidad = 0L;
		this.valorFaltante = 0.0D;
		this.fechaRevision = new Date();
	}

	public Long getIdArqueoCajaPromotor() {
		return idArqueoCajaPromotor;
	}

	public void setIdArqueoCajaPromotor(Long idArqueoCajaPromotor) {
		this.idArqueoCajaPromotor = idArqueoCajaPromotor;
	}

	public String getPromotor() {
		return promotor;
	}

	public void setPromotor(String promotor) {
		this.promotor = promotor;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public Double getValorFaltante() {
		return valorFaltante;
	}

	public void setValorFaltante(Double valorFaltante) {
		this.valorFaltante = valorFaltante;
	}

	public Date getFechaRevision() {
		return fechaRevision;
	}

	public void setFechaRevision(Date fechaRevision) {
		this.fechaRevision = fechaRevision;
	}

	public CheckListKpiEjecucionEt getCheckListKpiEjecucion() {
		return checkListKpiEjecucion;
	}

	public void setCheckListKpiEjecucion(CheckListKpiEjecucionEt checkListKpiEjecucion) {
		this.checkListKpiEjecucion = checkListKpiEjecucion;
	}

	public ParametrosGeneralesEt getParametroMedioPago() {
		return parametroMedioPago;
	}

	public void setParametroMedioPago(ParametrosGeneralesEt parametroMedioPago) {
		this.parametroMedioPago = parametroMedioPago;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof ArqueoCajaPromotorEt) {

			ArqueoCajaPromotorEt other = (ArqueoCajaPromotorEt) obj;
			if (this.idArqueoCajaPromotor == null)
				return this == other;

			return this.idArqueoCajaPromotor.equals(other.idArqueoCajaPromotor);
		}
		return false;
	}

}
