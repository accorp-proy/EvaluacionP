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
import com.primax.jpa.param.ParametrosGeneralesEt;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "CHECK_LIST_ETIQUETA_ET")
@Audited
public class CheckListEtiquetaEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -4838501690442140136L;

	@Id
	@SequenceGenerator(name = "sec_check_list_etiqueta_et", sequenceName = "seq_check_list_etiqueta_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_check_list_etiqueta_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_check_list_etiqueta")
	private Long idCheckListEtiqueta;

	@Column(name = "orden")
	private Long orden;

	@Column(name = "nombre", length = 50)
	private String nombre;

	@Column(name = "descripcion", length = 100)
	private String descripcion;

	@ManyToOne
	@JoinColumn(name = "id_par_seccion")
	private ParametrosGeneralesEt parSeccion;

	public CheckListEtiquetaEt() {
		this.orden = 1L;
		this.nombre = "";
		this.descripcion = "";
	}

	public Long getIdCheckListEtiqueta() {
		return idCheckListEtiqueta;
	}

	public void setIdCheckListEtiqueta(Long idCheckListEtiqueta) {
		this.idCheckListEtiqueta = idCheckListEtiqueta;
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ParametrosGeneralesEt getParSeccion() {
		return parSeccion;
	}

	public void setParSeccion(ParametrosGeneralesEt parSeccion) {
		this.parSeccion = parSeccion;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof CheckListEtiquetaEt) {

			CheckListEtiquetaEt other = (CheckListEtiquetaEt) obj;
			if (this.idCheckListEtiqueta == null)
				return this == other;

			return this.idCheckListEtiqueta.equals(other.idCheckListEtiqueta);
		}
		return false;
	}

}
