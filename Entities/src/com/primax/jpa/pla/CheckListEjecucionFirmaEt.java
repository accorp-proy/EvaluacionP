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
@Table(name = "CHECK_LIST_EJECUCION_FIRMA_ET")
@Audited
public class CheckListEjecucionFirmaEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_check_list_ejecucion_firma_et", sequenceName = "seq_check_list_ejecucion_firma_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_check_list_ejecucion_firma_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_check_list_ejecucion_firma")
	private Long idCheckListEjecucionFirma;

	@ManyToOne
	@JoinColumn(name = "id_check_list_ejecucion")
	private CheckListEjecucionEt checkListEjecucion;

	@Column(name = "cargo", length = 100)
	private String cargo;

	@Column(name = "nombre", length = 100)
	private String nombre;

	@Column(name = "identificacion", length = 50)
	private String identificacion;

	@Column(name = "firma", length = 50000)
	private String firma;

	@Column(name = "imagen_path", length = 1000)
	private String imagenPath;

	@Column(name = "firmado")
	private boolean firmado;

	@Column(name = "contiene_firma")
	private boolean contieneFirma;

	@Column(name = "orden")
	private Long orden;

	@Column(name = "id_persona")
	private Long idPersona;

	public CheckListEjecucionFirmaEt() {
		this.orden = 0L;
		this.cargo = "";
		this.nombre = "";
		this.firma = null;
		this.idPersona = 0L;
		this.firmado = false;
		this.contieneFirma = false;
	}

	public Long getIdCheckListEjecucionFirma() {
		return idCheckListEjecucionFirma;
	}

	public void setIdCheckListEjecucionFirma(Long idCheckListEjecucionFirma) {
		this.idCheckListEjecucionFirma = idCheckListEjecucionFirma;
	}

	public CheckListEjecucionEt getCheckListEjecucion() {
		return checkListEjecucion;
	}

	public void setCheckListEjecucion(CheckListEjecucionEt checkListEjecucion) {
		this.checkListEjecucion = checkListEjecucion;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	public String getImagenPath() {
		return imagenPath;
	}

	public void setImagenPath(String imagenPath) {
		this.imagenPath = imagenPath;
	}

	public boolean isFirmado() {
		return firmado;
	}

	public void setFirmado(boolean firmado) {
		this.firmado = firmado;
	}

	public boolean isContieneFirma() {
		return contieneFirma;
	}

	public void setContieneFirma(boolean contieneFirma) {
		this.contieneFirma = contieneFirma;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CheckListEjecucionFirmaEt) {
			CheckListEjecucionFirmaEt other = (CheckListEjecucionFirmaEt) obj;

			if (this.idCheckListEjecucionFirma == null)
				return this == other;

			if (this.idCheckListEjecucionFirma.equals(other.idCheckListEjecucionFirma))
				return true;
		}
		return false;

	}

}
