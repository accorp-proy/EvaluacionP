package com.primax.jpa.pla;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "TABLERO_CABECERA_ET")
public class TableroCabeceraEt extends EntityBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "sec_tablero_cabecera_et", sequenceName = "seq_tablero_cabecera_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_tablero_cabecera_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_tablero_cabecera")
	private Long idTableroCabecera;

	@Column(name = "evaluacion_01", length = 100)
	private String evaluacion01;

	@Column(name = "evaluacion_02", length = 100)
	private String evaluacion02;

	@Column(name = "evaluacion_03", length = 100)
	private String evaluacion03;

	@Column(name = "evaluacion_04", length = 100)
	private String evaluacion04;

	@Column(name = "evaluacion_05", length = 100)
	private String evaluacion05;

	@Column(name = "evaluacion_06", length = 100)
	private String evaluacion06;

	@Column(name = "evaluacion_07", length = 100)
	private String evaluacion07;

	@Column(name = "evaluacion_08", length = 100)
	private String evaluacion08;

	@Column(name = "puntaje_01")
	private Long puntaje01;

	@Column(name = "puntaje_02")
	private Long puntaje02;

	@Column(name = "puntaje_03")
	private Long puntaje03;

	@Column(name = "puntaje_04")
	private Long puntaje04;

	@Column(name = "puntaje_05")
	private Long puntaje05;

	@Column(name = "puntaje_06")
	private Long puntaje06;

	@Column(name = "puntaje_07")
	private Long puntaje07;

	@Column(name = "puntaje_08")
	private Long puntaje08;

	@Column(name = "color_01", length = 20)
	private String color01;

	@Column(name = "color_02", length = 20)
	private String color02;

	@Column(name = "color_03", length = 20)
	private String color03;

	@Column(name = "color_04", length = 20)
	private String color04;

	@Column(name = "color_05", length = 20)
	private String color05;

	@Column(name = "color_06", length = 20)
	private String color06;

	@Column(name = "color_07", length = 20)
	private String color07;

	@Column(name = "color_08", length = 20)
	private String color08;

	@Column(name = "color_letra_01", length = 20)
	private String colorLetra01;

	@Column(name = "color_letra_02", length = 20)
	private String colorLetra02;

	@Column(name = "color_letra_03", length = 20)
	private String colorLetra03;

	@Column(name = "color_letra_04", length = 20)
	private String colorLetra04;

	@Column(name = "color_letra_05", length = 20)
	private String colorLetra05;

	@Column(name = "color_letra_06", length = 20)
	private String colorLetra06;

	@Column(name = "color_letra_07", length = 20)
	private String colorLetra07;

	@Column(name = "color_letra_08", length = 20)
	private String colorLetra08;

	@Column(name = "nivel_01", length = 50)
	private String nivel01;

	@Column(name = "nivel_02", length = 50)
	private String nivel02;

	@Column(name = "nivel_03", length = 50)
	private String nivel03;

	@Column(name = "nivel_04", length = 50)
	private String nivel04;

	@Column(name = "nivel_05", length = 50)
	private String nivel05;

	@Column(name = "nivel_06", length = 50)
	private String nivel06;

	@Column(name = "nivel_07", length = 50)
	private String nivel07;

	@Column(name = "nivel_08", length = 50)
	private String nivel08;

	@Column(name = "numero_tienda")
	private Long numeroTienda;

	public TableroCabeceraEt() {
		this.puntaje01 = 0L;
		this.puntaje02 = 0L;
		this.puntaje03 = 0L;
		this.puntaje04 = 0L;
		this.puntaje05 = 0L;
		this.puntaje06 = 0L;
		this.puntaje07 = 0L;
		this.puntaje08 = 0L;
		this.numeroTienda = 0L;
		this.evaluacion01 = "0";
		this.evaluacion02 = "0";
		this.evaluacion03 = "0";
		this.evaluacion04 = "0";
		this.evaluacion05 = "0";
		this.evaluacion06 = "0";
		this.evaluacion07 = "0";
	}

	public Long getIdTableroCabecera() {
		return idTableroCabecera;
	}

	public void setIdTableroCabecera(Long idTableroCabecera) {
		this.idTableroCabecera = idTableroCabecera;
	}

	public String getEvaluacion01() {
		return evaluacion01;
	}

	public void setEvaluacion01(String evaluacion01) {
		this.evaluacion01 = evaluacion01;
	}

	public String getEvaluacion02() {
		return evaluacion02;
	}

	public void setEvaluacion02(String evaluacion02) {
		this.evaluacion02 = evaluacion02;
	}

	public String getEvaluacion03() {
		return evaluacion03;
	}

	public void setEvaluacion03(String evaluacion03) {
		this.evaluacion03 = evaluacion03;
	}

	public String getEvaluacion04() {
		return evaluacion04;
	}

	public void setEvaluacion04(String evaluacion04) {
		this.evaluacion04 = evaluacion04;
	}

	public String getEvaluacion05() {
		return evaluacion05;
	}

	public void setEvaluacion05(String evaluacion05) {
		this.evaluacion05 = evaluacion05;
	}

	public String getEvaluacion06() {
		return evaluacion06;
	}

	public void setEvaluacion06(String evaluacion06) {
		this.evaluacion06 = evaluacion06;
	}

	public String getEvaluacion07() {
		return evaluacion07;
	}

	public void setEvaluacion07(String evaluacion07) {
		this.evaluacion07 = evaluacion07;
	}

	public String getEvaluacion08() {
		return evaluacion08;
	}

	public void setEvaluacion08(String evaluacion08) {
		this.evaluacion08 = evaluacion08;
	}

	public Long getPuntaje01() {
		return puntaje01;
	}

	public void setPuntaje01(Long puntaje01) {
		this.puntaje01 = puntaje01;
	}

	public Long getPuntaje02() {
		return puntaje02;
	}

	public void setPuntaje02(Long puntaje02) {
		this.puntaje02 = puntaje02;
	}

	public Long getPuntaje03() {
		return puntaje03;
	}

	public void setPuntaje03(Long puntaje03) {
		this.puntaje03 = puntaje03;
	}

	public Long getPuntaje04() {
		return puntaje04;
	}

	public void setPuntaje04(Long puntaje04) {
		this.puntaje04 = puntaje04;
	}

	public Long getPuntaje05() {
		return puntaje05;
	}

	public void setPuntaje05(Long puntaje05) {
		this.puntaje05 = puntaje05;
	}

	public Long getPuntaje06() {
		return puntaje06;
	}

	public void setPuntaje06(Long puntaje06) {
		this.puntaje06 = puntaje06;
	}

	public Long getPuntaje07() {
		return puntaje07;
	}

	public void setPuntaje07(Long puntaje07) {
		this.puntaje07 = puntaje07;
	}

	public Long getPuntaje08() {
		return puntaje08;
	}

	public void setPuntaje08(Long puntaje08) {
		this.puntaje08 = puntaje08;
	}

	public String getColor01() {
		return color01;
	}

	public void setColor01(String color01) {
		this.color01 = color01;
	}

	public String getColor02() {
		return color02;
	}

	public void setColor02(String color02) {
		this.color02 = color02;
	}

	public String getColor03() {
		return color03;
	}

	public void setColor03(String color03) {
		this.color03 = color03;
	}

	public String getColor04() {
		return color04;
	}

	public void setColor04(String color04) {
		this.color04 = color04;
	}

	public String getColor05() {
		return color05;
	}

	public void setColor05(String color05) {
		this.color05 = color05;
	}

	public String getColor06() {
		return color06;
	}

	public void setColor06(String color06) {
		this.color06 = color06;
	}

	public String getColor07() {
		return color07;
	}

	public void setColor07(String color07) {
		this.color07 = color07;
	}

	public String getColor08() {
		return color08;
	}

	public void setColor08(String color08) {
		this.color08 = color08;
	}

	public String getNivel01() {
		return nivel01;
	}

	public void setNivel01(String nivel01) {
		this.nivel01 = nivel01;
	}

	public String getNivel02() {
		return nivel02;
	}

	public void setNivel02(String nivel02) {
		this.nivel02 = nivel02;
	}

	public String getNivel03() {
		return nivel03;
	}

	public void setNivel03(String nivel03) {
		this.nivel03 = nivel03;
	}

	public String getNivel04() {
		return nivel04;
	}

	public void setNivel04(String nivel04) {
		this.nivel04 = nivel04;
	}

	public String getNivel05() {
		return nivel05;
	}

	public void setNivel05(String nivel05) {
		this.nivel05 = nivel05;
	}

	public String getNivel06() {
		return nivel06;
	}

	public void setNivel06(String nivel06) {
		this.nivel06 = nivel06;
	}

	public String getNivel07() {
		return nivel07;
	}

	public void setNivel07(String nivel07) {
		this.nivel07 = nivel07;
	}

	public String getNivel08() {
		return nivel08;
	}

	public void setNivel08(String nivel08) {
		this.nivel08 = nivel08;
	}

	public Long getNumeroTienda() {
		return numeroTienda;
	}

	public void setNumeroTienda(Long numeroTienda) {
		this.numeroTienda = numeroTienda;
	}

	public String getColorLetra01() {
		return colorLetra01;
	}

	public void setColorLetra01(String colorLetra01) {
		this.colorLetra01 = colorLetra01;
	}

	public String getColorLetra02() {
		return colorLetra02;
	}

	public void setColorLetra02(String colorLetra02) {
		this.colorLetra02 = colorLetra02;
	}

	public String getColorLetra03() {
		return colorLetra03;
	}

	public void setColorLetra03(String colorLetra03) {
		this.colorLetra03 = colorLetra03;
	}

	public String getColorLetra04() {
		return colorLetra04;
	}

	public void setColorLetra04(String colorLetra04) {
		this.colorLetra04 = colorLetra04;
	}

	public String getColorLetra05() {
		return colorLetra05;
	}

	public void setColorLetra05(String colorLetra05) {
		this.colorLetra05 = colorLetra05;
	}

	public String getColorLetra06() {
		return colorLetra06;
	}

	public void setColorLetra06(String colorLetra06) {
		this.colorLetra06 = colorLetra06;
	}

	public String getColorLetra07() {
		return colorLetra07;
	}

	public void setColorLetra07(String colorLetra07) {
		this.colorLetra07 = colorLetra07;
	}

	public String getColorLetra08() {
		return colorLetra08;
	}

	public void setColorLetra08(String colorLetra08) {
		this.colorLetra08 = colorLetra08;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof TableroCabeceraEt) {

			TableroCabeceraEt other = (TableroCabeceraEt) obj;
			if (this.idTableroCabecera == null)
				return this == other;

			return this.idTableroCabecera.equals(other.idTableroCabecera);
		}
		return false;
	}

}
