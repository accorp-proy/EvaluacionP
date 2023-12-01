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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "CHECK_LIST_CRITERIO_ET")
@Audited
public class CheckListCriterioEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -4838501690442140136L;

	@Id
	@SequenceGenerator(name = "sec_check_list_criterio_et", sequenceName = "seq_check_list_criterio_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_check_list_criterio_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_check_list_criterio")
	private Long idCheckListCriterio;

	@Column(name = "nombre", length = 100)
	@OrderBy("ORDER BY nombre")
	private String nombre;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "checkListCriterio", fetch = FetchType.LAZY)
	@OrderBy("orden asc")
	@Where(clause = "estado = 'ACT'")
	private List<CheckListCriterioDetalleEt> checkListCriterioDetalle;

	public CheckListCriterioEt() {

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getIdCheckListCriterio() {
		return idCheckListCriterio;
	}

	public void setIdCheckListCriterio(Long idCheckListCriterio) {
		this.idCheckListCriterio = idCheckListCriterio;
	}

	public List<CheckListCriterioDetalleEt> getCheckListCriterioDetalle() {
		return checkListCriterioDetalle;
	}

	public void setCheckListCriterioDetalle(List<CheckListCriterioDetalleEt> checkListCriterioDetalle) {
		this.checkListCriterioDetalle = checkListCriterioDetalle;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof CheckListCriterioEt) {

			CheckListCriterioEt other = (CheckListCriterioEt) obj;
			if (this.idCheckListCriterio == null)
				return this == other;

			return this.idCheckListCriterio.equals(other.idCheckListCriterio);
		}
		return false;
	}

}
