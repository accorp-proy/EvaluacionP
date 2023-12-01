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
@Table(name = "CHECK_LIST_PROCESO_FORMULARIO_ET")
@Audited
public class CheckListProcesoFormularioEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -4838501690442140136L;

	@Id
	@SequenceGenerator(name = "sec_check_list_proceso_formulario_et", sequenceName = "seq_check_list_proceso_formulario_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_check_list_proceso_formulario_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_check_list_proceso_formulario")
	private Long idCheckListProcesoFormulario;

	@ManyToOne
	@JoinColumn(name = "id_check_list_proceso")
	private CheckListProcesoEt checkListProceso;

	@Column(name = "nombre_frm", length = 20)
	private String nombreFrm;

	@Column(name = "nomb_frm_0", length = 20)
	private String nombFrm0;

	@Column(name = "frm_0")
	private boolean frm0;

	@Column(name = "nomb_frm_1", length = 20)
	private String nombFrm1;

	@Column(name = "frm_1")
	private boolean frm1;

	@Column(name = "nomb_frm_2", length = 20)
	private String nombFrm2;

	@Column(name = "frm_2")
	private boolean frm2;

	@Column(name = "nomb_frm_3", length = 20)
	private String nombFrm3;

	@Column(name = "frm_3")
	private boolean frm3;

	@Column(name = "nomb_frm_4", length = 20)
	private String nombFrm4;

	@Column(name = "frm_4")
	private boolean frm4;

	@Column(name = "nomb_frm_5", length = 20)
	private String nombFrm5;

	@Column(name = "frm_5")
	private boolean frm5;

	@Column(name = "nomb_frm_6", length = 20)
	private String nombFrm6;

	@Column(name = "frm_6")
	private boolean frm6;

	@Column(name = "nomb_frm_7", length = 20)
	private String nombFrm7;

	@Column(name = "frm_7")
	private boolean frm7;

	/**
	 * Reporte Informe Dinámicos
	 */

	@Column(name = "visualizar")
	private boolean visualizar;

	public CheckListProcesoFormularioEt() {
		this.frm0 = false;
		this.frm1 = false;
		this.frm2 = false;
		this.frm3 = false;
		this.frm4 = false;
		this.frm5 = false;
		this.frm6 = false;
		this.frm7 = false;
		this.nombFrm0 = "";
		this.nombFrm1 = "";
		this.nombFrm2 = "";
		this.nombFrm3 = "";
		this.nombFrm4 = "";
		this.nombFrm5 = "";
		this.nombFrm6 = "";
		this.nombFrm7 = "";
		this.nombreFrm = "";
		this.visualizar = false;

	}

	public String getNombFrm0() {
		return nombFrm0;
	}

	public void setNombFrm0(String nombFrm0) {
		this.nombFrm0 = nombFrm0;
	}

	public boolean isFrm0() {
		return frm0;
	}

	public void setFrm0(boolean frm0) {
		this.frm0 = frm0;
	}

	public String getNombFrm1() {
		return nombFrm1;
	}

	public void setNombFrm1(String nombFrm1) {
		this.nombFrm1 = nombFrm1;
	}

	public boolean isFrm1() {
		return frm1;
	}

	public void setFrm1(boolean frm1) {
		this.frm1 = frm1;
	}

	public String getNombFrm2() {
		return nombFrm2;
	}

	public void setNombFrm2(String nombFrm2) {
		this.nombFrm2 = nombFrm2;
	}

	public boolean isFrm2() {
		return frm2;
	}

	public void setFrm2(boolean frm2) {
		this.frm2 = frm2;
	}

	public String getNombFrm3() {
		return nombFrm3;
	}

	public void setNombFrm3(String nombFrm3) {
		this.nombFrm3 = nombFrm3;
	}

	public boolean isFrm3() {
		return frm3;
	}

	public void setFrm3(boolean frm3) {
		this.frm3 = frm3;
	}

	public String getNombFrm4() {
		return nombFrm4;
	}

	public void setNombFrm4(String nombFrm4) {
		this.nombFrm4 = nombFrm4;
	}

	public boolean isFrm4() {
		return frm4;
	}

	public void setFrm4(boolean frm4) {
		this.frm4 = frm4;
	}

	public String getNombFrm5() {
		return nombFrm5;
	}

	public void setNombFrm5(String nombFrm5) {
		this.nombFrm5 = nombFrm5;
	}

	public boolean isFrm5() {
		return frm5;
	}

	public void setFrm5(boolean frm5) {
		this.frm5 = frm5;
	}

	public String getNombFrm6() {
		return nombFrm6;
	}

	public void setNombFrm6(String nombFrm6) {
		this.nombFrm6 = nombFrm6;
	}

	public boolean isFrm6() {
		return frm6;
	}

	public void setFrm6(boolean frm6) {
		this.frm6 = frm6;
	}

	public String getNombFrm7() {
		return nombFrm7;
	}

	public void setNombFrm7(String nombFrm7) {
		this.nombFrm7 = nombFrm7;
	}

	public boolean isFrm7() {
		return frm7;
	}

	public void setFrm7(boolean frm7) {
		this.frm7 = frm7;
	}

	public Long getIdCheckListProcesoFormulario() {
		return idCheckListProcesoFormulario;
	}

	public void setIdCheckListProcesoFormulario(Long idCheckListProcesoFormulario) {
		this.idCheckListProcesoFormulario = idCheckListProcesoFormulario;
	}

	public CheckListProcesoEt getCheckListProceso() {
		return checkListProceso;
	}

	public void setCheckListProceso(CheckListProcesoEt checkListProceso) {
		this.checkListProceso = checkListProceso;
	}

	public String getNombreFrm() {
		return nombreFrm;
	}

	public void setNombreFrm(String nombreFrm) {
		this.nombreFrm = nombreFrm;
	}

	public boolean isVisualizar() {
		return visualizar;
	}

	public void setVisualizar(boolean visualizar) {
		this.visualizar = visualizar;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof CheckListProcesoFormularioEt) {

			CheckListProcesoFormularioEt other = (CheckListProcesoFormularioEt) obj;
			if (this.idCheckListProcesoFormulario == null)
				return this == other;

			return this.idCheckListProcesoFormulario.equals(other.idCheckListProcesoFormulario);
		}
		return false;
	}

}
