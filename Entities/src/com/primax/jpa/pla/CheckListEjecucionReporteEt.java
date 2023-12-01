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
@Table(name = "CHECK_LIST_EJECUCION_REPORTE_ET")
@Audited
public class CheckListEjecucionReporteEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_check_list_ejecucion_reporte_et", sequenceName = "seq_check_list_ejecucion_reporte_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_check_list_ejecucion_reporte_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_check_list_ejecucion_reporte")
	private Long idCheckListEjecucionReporte;

	@ManyToOne
	@JoinColumn(name = "id_check_list_ejecucion")
	private CheckListEjecucionEt checkListEjecucion;

	@Column(name = "descripcion", length = 500)
	private String descripcion;

	@Column(name = "descripcion_replace", length = 500)
	private String descripcionReplace;

	@Column(name = "codigo", length = 100)
	private String codigo;

	@Column(name = "codigo_replace", length = 100)
	private String codigoReplace;

	@Column(name = "secuencial")
	private Long secuencial;

	@Column(name = "nombre", length = 100)
	private String nombre;

	@Column(name = "sub_titulo", length = 100)
	private String subTitulo;

	@Column(name = "fecha_verificacion")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaVerificacion;

	@Column(name = "fecha_reporte")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaReporte;

	@Column(name = "hora_inicio")
	@Temporal(TemporalType.TIMESTAMP)
	private Date horaInicio;

	@Column(name = "hora_fin")
	@Temporal(TemporalType.TIMESTAMP)
	private Date horaFin;

	public CheckListEjecucionReporteEt() {
		this.codigo = "";
		this.nombre = "";
		this.subTitulo = "";
		this.secuencial = 1L;
		this.descripcion = "";
		this.horaFin = new Date();
		this.horaInicio = new Date();
	}

	public Long getIdCheckListEjecucionReporte() {
		return idCheckListEjecucionReporte;
	}

	public void setIdCheckListEjecucionReporte(Long idCheckListEjecucionReporte) {
		this.idCheckListEjecucionReporte = idCheckListEjecucionReporte;
	}

	public CheckListEjecucionEt getCheckListEjecucion() {
		return checkListEjecucion;
	}

	public void setCheckListEjecucion(CheckListEjecucionEt checkListEjecucion) {
		this.checkListEjecucion = checkListEjecucion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaVerificacion() {
		return fechaVerificacion;
	}

	public void setFechaVerificacion(Date fechaVerificacion) {
		this.fechaVerificacion = fechaVerificacion;
	}

	public Date getFechaReporte() {
		return fechaReporte;
	}

	public void setFechaReporte(Date fechaReporte) {
		this.fechaReporte = fechaReporte;
	}

	public Date getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Date getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(Date horaFin) {
		this.horaFin = horaFin;
	}

	public Long getSecuencial() {
		return secuencial;
	}

	public void setSecuencial(Long secuencial) {
		this.secuencial = secuencial;
	}

	public String getDescripcionReplace() {
		return descripcionReplace;
	}

	public void setDescripcionReplace(String descripcionReplace) {
		this.descripcionReplace = descripcionReplace;
	}

	public String getSubTitulo() {
		return subTitulo;
	}

	public void setSubTitulo(String subTitulo) {
		this.subTitulo = subTitulo;
	}

	public String getCodigoReplace() {
		return codigoReplace;
	}

	public void setCodigoReplace(String codigoReplace) {
		this.codigoReplace = codigoReplace;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CheckListEjecucionReporteEt) {
			CheckListEjecucionReporteEt other = (CheckListEjecucionReporteEt) obj;

			if (this.idCheckListEjecucionReporte == null)
				return this == other;

			if (this.idCheckListEjecucionReporte.equals(other.idCheckListEjecucionReporte))
				return true;
		}
		return false;

	}

}
