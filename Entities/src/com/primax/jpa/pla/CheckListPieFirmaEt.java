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
import com.primax.jpa.param.TipoCargoEt;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "CHECK_LIST_PIE_FIRMA_ET")
@Audited
public class CheckListPieFirmaEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -4838501690442140136L;

	@Id
	@SequenceGenerator(name = "sec_check_list_pie_firma_et", sequenceName = "seq_check_list_pie_firma_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_check_list_pie_firma_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_check_list_pie_firma")
	private Long idCheckListPieFirma;

	@ManyToOne
	@JoinColumn(name = "id_check_list")
	private CheckListEt checkList;

	@Column(name = "orden")
	private Long orden;

	@Column(name = "ejemplo", length = 50)
	private String ejemplo;

	@ManyToOne
	@JoinColumn(name = "id_tipo_cargo")
	private TipoCargoEt tipoCargo;

	public CheckListPieFirmaEt() {
		this.orden = 1L;
		this.ejemplo = "";
	}

	public Long getIdCheckListPieFirma() {
		return idCheckListPieFirma;
	}

	public void setIdCheckListPieFirma(Long idCheckListPieFirma) {
		this.idCheckListPieFirma = idCheckListPieFirma;
	}

	public CheckListEt getCheckList() {
		return checkList;
	}

	public void setCheckList(CheckListEt checkList) {
		this.checkList = checkList;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public String getEjemplo() {
		return ejemplo;
	}

	public void setEjemplo(String ejemplo) {
		this.ejemplo = ejemplo;
	}

	public TipoCargoEt getTipoCargo() {
		return tipoCargo;
	}

	public void setTipoCargo(TipoCargoEt tipoCargo) {
		this.tipoCargo = tipoCargo;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof CheckListPieFirmaEt) {

			CheckListPieFirmaEt other = (CheckListPieFirmaEt) obj;
			if (this.idCheckListPieFirma == null)
				return this == other;

			return this.idCheckListPieFirma.equals(other.idCheckListPieFirma);
		}
		return false;
	}

}
