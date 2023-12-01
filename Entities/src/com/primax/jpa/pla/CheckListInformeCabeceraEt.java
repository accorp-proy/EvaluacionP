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
@Table(name = "CHECK_LIST_INFORME_CABECERA_ET")
@Audited
public class CheckListInformeCabeceraEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -4838501690442140136L;

	@Id
	@SequenceGenerator(name = "sec_check_list_informe_cabecera_et", sequenceName = "seq_check_list_informe_cabecera_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_check_list_informe_cabecera_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_check_list_informe_cabecera")
	private Long idCheckListInformeCabecera;

	@ManyToOne
	@JoinColumn(name = "id_check_list")
	private CheckListEt checkList;

	@Column(name = "orden")
	private Long orden;

	@Column(name = "titulo", length = 100)
	private String titulo;

	@Column(name = "titulo_reemplazo", length = 100)
	private String tituloReemplazo;

	@Column(name = "contenido", length = 200)
	private String contenido;

	@Column(name = "contenido_reemplazo", length = 200)
	private String contenidoReemplazo;

	public CheckListInformeCabeceraEt() {
		this.orden = 1L;
		this.titulo = "";
		this.tituloReemplazo = "";
		this.contenidoReemplazo = "";
	}

	public Long getIdCheckListInformeCabecera() {
		return idCheckListInformeCabecera;
	}

	public void setIdCheckListInformeCabecera(Long idCheckListInformeCabecera) {
		this.idCheckListInformeCabecera = idCheckListInformeCabecera;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public CheckListEt getCheckList() {
		return checkList;
	}

	public void setCheckList(CheckListEt checkList) {
		this.checkList = checkList;
	}

	public String getTituloReemplazo() {
		return tituloReemplazo;
	}

	public void setTituloReemplazo(String tituloReemplazo) {
		this.tituloReemplazo = tituloReemplazo;
	}

	public String getContenidoReemplazo() {
		return contenidoReemplazo;
	}

	public void setContenidoReemplazo(String contenidoReemplazo) {
		this.contenidoReemplazo = contenidoReemplazo;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof CheckListInformeCabeceraEt) {

			CheckListInformeCabeceraEt other = (CheckListInformeCabeceraEt) obj;
			if (this.idCheckListInformeCabecera == null)
				return this == other;

			return this.idCheckListInformeCabecera.equals(other.idCheckListInformeCabecera);
		}
		return false;
	}

}
