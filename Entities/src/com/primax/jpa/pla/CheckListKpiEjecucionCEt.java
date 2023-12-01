package com.primax.jpa.pla;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.SequenceGenerator;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "CHECK_LIST_KPI_EJECUCION_C_ET")
@Audited
@NamedStoredProcedureQuery(name = "getLimpiarKpiEjecucion", procedureName = "fun_limpiar_kpi_ejecucion_c", resultClasses = CheckListKpiEjecucionCEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idCheckListProcesoEjecucion"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class CheckListKpiEjecucionCEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_check_list_kpi_ejecucion_c_et", sequenceName = "seq_check_list_kpi_ejecucion_c_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_check_list_kpi_ejecucion_c_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_check_list_kpi_ejecucion_c")
	private Long idCheckListKpiEjecucionC;

	@Column(name = "orden")
	private Long orden;

	@Column(name = "descripcion", length = 500)
	private String descripcion;

	@Column(name = "dia_01")
	private Double dia1;

	@Column(name = "dia_02")
	private Double dia2;

	@Column(name = "dia_03")
	private Double dia3;

	@Column(name = "dia_04")
	private Double dia4;

	@Column(name = "dia_05")
	private Double dia5;

	@Column(name = "dia_06")
	private Double dia6;

	@Column(name = "dia_07")
	private Double dia7;

	@Column(name = "dia_08")
	private Double dia8;

	@Column(name = "dia_09")
	private Double dia9;

	@Column(name = "dia_10")
	private Double dia10;

	@Column(name = "dia_11")
	private Double dia11;

	@Column(name = "dia_12")
	private Double dia12;

	@Column(name = "dia_13")
	private Double dia13;

	@Column(name = "dia_14")
	private Double dia14;

	@Column(name = "dia_15")
	private Double dia15;

	@Column(name = "dia_16")
	private Double dia16;

	@Column(name = "dia_17")
	private Double dia17;

	@Column(name = "dia_18")
	private Double dia18;

	@Column(name = "dia_19")
	private Double dia19;

	@Column(name = "dia_20")
	private Double dia20;

	@Column(name = "dia_21")
	private Double dia21;

	@Column(name = "dia_22")
	private Double dia22;

	@Column(name = "dia_23")
	private Double dia23;

	@Column(name = "dia_24")
	private Double dia24;

	@Column(name = "dia_25")
	private Double dia25;

	@Column(name = "dia_26")
	private Double dia26;

	@Column(name = "dia_27")
	private Double dia27;

	@Column(name = "dia_28")
	private Double dia28;

	@Column(name = "dia_29")
	private Double dia29;

	@Column(name = "dia_30")
	private Double dia30;

	@Column(name = "dia_31")
	private Double dia31;

	@ManyToOne
	@JoinColumn(name = "id_check_list_proceso_ejecucion")
	private CheckListProcesoEjecucionEt checkListProcesoEjecucion;

	@Column(name = "total")
	private Double total;

	@Column(name = "tipo")
	private Long tipo;

	public CheckListKpiEjecucionCEt() {
		this.dia1 = 0D;
		this.dia2 = 0D;
		this.dia3 = 0D;
		this.dia4 = 0D;
		this.dia5 = 0D;
		this.dia6 = 0D;
		this.dia7 = 0D;
		this.dia8 = 0D;
		this.dia9 = 0D;
		this.dia10 = 0D;
		this.dia11 = 0D;
		this.dia12 = 0D;
		this.dia13 = 0D;
		this.dia14 = 0D;
		this.dia15 = 0D;
		this.dia16 = 0D;
		this.dia17 = 0D;
		this.dia18 = 0D;
		this.dia19 = 0D;
		this.dia20 = 0D;
		this.dia21 = 0D;
		this.dia22 = 0D;
		this.dia23 = 0D;
		this.dia24 = 0D;
		this.dia25 = 0D;
		this.dia26 = 0D;
		this.dia27 = 0D;
		this.dia28 = 0D;
		this.dia29 = 0D;
		this.dia30 = 0D;
		this.dia31 = 0D;
		this.orden = 0L;
		this.total = 0D;
	}

	public Long getIdCheckListKpiEjecucionC() {
		return idCheckListKpiEjecucionC;
	}

	public void setIdCheckListKpiEjecucionC(Long idCheckListKpiEjecucionC) {
		this.idCheckListKpiEjecucionC = idCheckListKpiEjecucionC;
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

	public Double getDia1() {
		return dia1;
	}

	public void setDia1(Double dia1) {
		this.dia1 = dia1;
	}

	public Double getDia2() {
		return dia2;
	}

	public void setDia2(Double dia2) {
		this.dia2 = dia2;
	}

	public Double getDia3() {
		return dia3;
	}

	public void setDia3(Double dia3) {
		this.dia3 = dia3;
	}

	public Double getDia4() {
		return dia4;
	}

	public void setDia4(Double dia4) {
		this.dia4 = dia4;
	}

	public Double getDia5() {
		return dia5;
	}

	public void setDia5(Double dia5) {
		this.dia5 = dia5;
	}

	public Double getDia6() {
		return dia6;
	}

	public void setDia6(Double dia6) {
		this.dia6 = dia6;
	}

	public Double getDia7() {
		return dia7;
	}

	public void setDia7(Double dia7) {
		this.dia7 = dia7;
	}

	public Double getDia8() {
		return dia8;
	}

	public void setDia8(Double dia8) {
		this.dia8 = dia8;
	}

	public Double getDia9() {
		return dia9;
	}

	public void setDia9(Double dia9) {
		this.dia9 = dia9;
	}

	public Double getDia10() {
		return dia10;
	}

	public void setDia10(Double dia10) {
		this.dia10 = dia10;
	}

	public Double getDia11() {
		return dia11;
	}

	public void setDia11(Double dia11) {
		this.dia11 = dia11;
	}

	public Double getDia12() {
		return dia12;
	}

	public void setDia12(Double dia12) {
		this.dia12 = dia12;
	}

	public Double getDia13() {
		return dia13;
	}

	public void setDia13(Double dia13) {
		this.dia13 = dia13;
	}

	public Double getDia14() {
		return dia14;
	}

	public void setDia14(Double dia14) {
		this.dia14 = dia14;
	}

	public Double getDia15() {
		return dia15;
	}

	public void setDia15(Double dia15) {
		this.dia15 = dia15;
	}

	public Double getDia16() {
		return dia16;
	}

	public void setDia16(Double dia16) {
		this.dia16 = dia16;
	}

	public Double getDia17() {
		return dia17;
	}

	public void setDia17(Double dia17) {
		this.dia17 = dia17;
	}

	public Double getDia18() {
		return dia18;
	}

	public void setDia18(Double dia18) {
		this.dia18 = dia18;
	}

	public Double getDia19() {
		return dia19;
	}

	public void setDia19(Double dia19) {
		this.dia19 = dia19;
	}

	public Double getDia20() {
		return dia20;
	}

	public void setDia20(Double dia20) {
		this.dia20 = dia20;
	}

	public Double getDia21() {
		return dia21;
	}

	public void setDia21(Double dia21) {
		this.dia21 = dia21;
	}

	public Double getDia22() {
		return dia22;
	}

	public void setDia22(Double dia22) {
		this.dia22 = dia22;
	}

	public Double getDia23() {
		return dia23;
	}

	public void setDia23(Double dia23) {
		this.dia23 = dia23;
	}

	public Double getDia24() {
		return dia24;
	}

	public void setDia24(Double dia24) {
		this.dia24 = dia24;
	}

	public Double getDia25() {
		return dia25;
	}

	public void setDia25(Double dia25) {
		this.dia25 = dia25;
	}

	public Double getDia26() {
		return dia26;
	}

	public void setDia26(Double dia26) {
		this.dia26 = dia26;
	}

	public Double getDia27() {
		return dia27;
	}

	public void setDia27(Double dia27) {
		this.dia27 = dia27;
	}

	public Double getDia28() {
		return dia28;
	}

	public void setDia28(Double dia28) {
		this.dia28 = dia28;
	}

	public Double getDia29() {
		return dia29;
	}

	public void setDia29(Double dia29) {
		this.dia29 = dia29;
	}

	public Double getDia30() {
		return dia30;
	}

	public void setDia30(Double dia30) {
		this.dia30 = dia30;
	}

	public Double getDia31() {
		return dia31;
	}

	public void setDia31(Double dia31) {
		this.dia31 = dia31;
	}

	public CheckListProcesoEjecucionEt getCheckListProcesoEjecucion() {
		return checkListProcesoEjecucion;
	}

	public void setCheckListProcesoEjecucion(CheckListProcesoEjecucionEt checkListProcesoEjecucion) {
		this.checkListProcesoEjecucion = checkListProcesoEjecucion;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Long getTipo() {
		return tipo;
	}

	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CheckListKpiEjecucionCEt) {
			CheckListKpiEjecucionCEt other = (CheckListKpiEjecucionCEt) obj;
			if (this.idCheckListKpiEjecucionC == null)
				return this == other;

			if (this.idCheckListKpiEjecucionC.equals(other.idCheckListKpiEjecucionC))
				return true;
		}
		return false;

	}

}
