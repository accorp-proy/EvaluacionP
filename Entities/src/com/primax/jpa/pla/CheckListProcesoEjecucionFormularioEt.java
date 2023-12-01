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
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "CHECK_LIST_PROCESO_EJECUCION_FORMULARIO_ET")
@Audited
public class CheckListProcesoEjecucionFormularioEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -4838501690442140136L;

	@Id
	@SequenceGenerator(name = "sec_check_list_proceso_ejecucion_formulario_et", sequenceName = "seq_check_list_proceso_ejecucion_formulario_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_check_list_proceso_ejecucion_formulario_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_check_list_proceso_ejecucion_formulario")
	private Long idCheckListProcesoEjecucionFormulario;

	@ManyToOne
	@JoinColumn(name = "id_check_list_proceso_ejecucion")
	private CheckListProcesoEjecucionEt checkListProcesoEjecucion;

	@ManyToOne
	@JoinColumn(name = "id_check_list_proceso_formulario")
	private CheckListProcesoFormularioEt checkListProcesoFormulario;

	@Column(name = "columna_1", length = 50)
	private String columna1;

	@Column(name = "columna_2")
	private Long columna2;

	@Column(name = "columna_3")
	private Double columna3;

	@Column(name = "columna_4")
	@Temporal(TemporalType.TIMESTAMP)
	private Date columna4;

	@Column(name = "columna_5", length = 50)
	private String columna5;

	@Column(name = "columna_6")
	private Long columna6;

	@Column(name = "columna_7")
	private Double columna7;

	@Column(name = "columna_8")
	@Temporal(TemporalType.TIMESTAMP)
	private Date columna8;

	@Column(name = "condicion")
	private boolean condicion;

	public CheckListProcesoEjecucionFormularioEt() {
		this.columna1 = "";
		this.columna2 = 0L;
		this.columna5 = "";
		this.columna6 = 0L;
		this.columna3 = 0.0;
		this.columna7 = 0.0;
		this.condicion = false;
		this.columna4 = new Date();
		this.columna8 = new Date();
	}

	public Long getIdCheckListProcesoEjecucionFormulario() {
		return idCheckListProcesoEjecucionFormulario;
	}

	public void setIdCheckListProcesoEjecucionFormulario(Long idCheckListProcesoEjecucionFormulario) {
		this.idCheckListProcesoEjecucionFormulario = idCheckListProcesoEjecucionFormulario;
	}

	public CheckListProcesoEjecucionEt getCheckListProcesoEjecucion() {
		return checkListProcesoEjecucion;
	}

	public void setCheckListProcesoEjecucion(CheckListProcesoEjecucionEt checkListProcesoEjecucion) {
		this.checkListProcesoEjecucion = checkListProcesoEjecucion;
	}

	public CheckListProcesoFormularioEt getCheckListProcesoFormulario() {
		return checkListProcesoFormulario;
	}

	public void setCheckListProcesoFormulario(CheckListProcesoFormularioEt checkListProcesoFormulario) {
		this.checkListProcesoFormulario = checkListProcesoFormulario;
	}

	public String getColumna1() {
		return columna1;
	}

	public void setColumna1(String columna1) {
		this.columna1 = columna1;
	}

	public Long getColumna2() {
		return columna2;
	}

	public void setColumna2(Long columna2) {
		this.columna2 = columna2;
	}

	public Double getColumna3() {
		return columna3;
	}

	public void setColumna3(Double columna3) {
		this.columna3 = columna3;
	}

	public Date getColumna4() {
		return columna4;
	}

	public void setColumna4(Date columna4) {
		this.columna4 = columna4;
	}

	public String getColumna5() {
		return columna5;
	}

	public void setColumna5(String columna5) {
		this.columna5 = columna5;
	}

	public Long getColumna6() {
		return columna6;
	}

	public void setColumna6(Long columna6) {
		this.columna6 = columna6;
	}

	public Double getColumna7() {
		return columna7;
	}

	public void setColumna7(Double columna7) {
		this.columna7 = columna7;
	}

	public Date getColumna8() {
		return columna8;
	}

	public void setColumna8(Date columna8) {
		this.columna8 = columna8;
	}

	public boolean isCondicion() {
		return condicion;
	}

	public void setCondicion(boolean condicion) {
		this.condicion = condicion;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof CheckListProcesoEjecucionFormularioEt) {

			CheckListProcesoEjecucionFormularioEt other = (CheckListProcesoEjecucionFormularioEt) obj;
			if (this.idCheckListProcesoEjecucionFormulario == null)
				return this == other;

			return this.idCheckListProcesoEjecucionFormulario.equals(other.idCheckListProcesoEjecucionFormulario);
		}
		return false;
	}

}
