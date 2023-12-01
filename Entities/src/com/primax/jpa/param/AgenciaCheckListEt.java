package com.primax.jpa.param;

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
import com.primax.jpa.pla.CheckListEt;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "AGENCIA_CHECK_LIST_ET")
@Audited
public class AgenciaCheckListEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_agencia_check_list_et", sequenceName = "seq_agencia_check_list_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_agencia_check_list_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_agencia_check_list")
	private Long idAgenciaCheckList;

	@ManyToOne
	@JoinColumn(name = "id_check_list")
	private CheckListEt checkList;

	@ManyToOne
	@JoinColumn(name = "id_agencia")
	private AgenciaEt agencia;

	public AgenciaEt getAgencia() {
		return agencia;
	}

	public void setAgencia(AgenciaEt agencia) {
		this.agencia = agencia;
	}

	public Long getIdAgenciaCheckList() {
		return idAgenciaCheckList;
	}

	public void setIdAgenciaCheckList(Long idAgenciaCheckList) {
		this.idAgenciaCheckList = idAgenciaCheckList;
	}

	public CheckListEt getCheckList() {
		return checkList;
	}

	public void setCheckList(CheckListEt checkList) {
		this.checkList = checkList;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AgenciaCheckListEt) {
			AgenciaCheckListEt other = (AgenciaCheckListEt) obj;

			if (this.idAgenciaCheckList == null)
				return this == other;

			if (this.idAgenciaCheckList.equals(other.idAgenciaCheckList))
				return true;
		}
		return false;

	}

}
