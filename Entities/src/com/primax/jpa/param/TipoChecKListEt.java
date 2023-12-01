package com.primax.jpa.param;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OrderBy;
import javax.persistence.ParameterMode;
import javax.persistence.SequenceGenerator;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.pla.ReporteUltimaVisitaEt;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "TIPO_CHECK_LIST_ET")
@Audited
@NamedStoredProcedureQuery(name = "getGenerarReporteUltimaVisita0", procedureName = "fun_generar_rpt_ultima_visita_0", resultClasses = ReporteUltimaVisitaEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idZona"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idTipoCheckList"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idEvaluacion"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idProceso"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class TipoChecKListEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -4838501690442140136L;

	@Id
	@SequenceGenerator(name = "sec_tipo_check_list_et", sequenceName = "seq_tipo_check_list_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_tipo_check_list_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_tipo_check_list")
	private Long idTipoCheckList;

	@ManyToOne
	@JoinColumn(name = "id_evaluacion")
	private EvaluacionEt evaluacion;

	@Column(name = "descripcion", length = 100)
	@OrderBy("ORDER BY descripcion")
	private String descripcion;

	@Column(name = "codigo", length = 100)
	private String codigo;

	@Column(name = "nombre", length = 100)
	private String nombre;

	@Column(name = "sub_titulo", length = 100)
	private String subTitulo;

	@Column(name = "detalle", length = 500)
	private String detalle;

	@Column(name = "genera_matriz")
	private boolean generaMatriz;

	@Column(name = "genera_reporte")
	private boolean generaReporte;

	public TipoChecKListEt() {
		this.codigo = "";
		this.nombre = "";
		this.detalle = "";
		this.subTitulo = "";
		this.generaMatriz = false;
		this.generaReporte = false;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getIdTipoCheckList() {
		return idTipoCheckList;
	}

	public void setIdTipoCheckList(Long idTipoCheckList) {
		this.idTipoCheckList = idTipoCheckList;
	}

	public EvaluacionEt getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(EvaluacionEt evaluacion) {
		this.evaluacion = evaluacion;
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

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public boolean isGeneraMatriz() {
		return generaMatriz;
	}

	public void setGeneraMatriz(boolean generaMatriz) {
		this.generaMatriz = generaMatriz;
	}

	public boolean isGeneraReporte() {
		return generaReporte;
	}

	public void setGeneraReporte(boolean generaReporte) {
		this.generaReporte = generaReporte;
	}

	public String getSubTitulo() {
		return subTitulo;
	}

	public void setSubTitulo(String subTitulo) {
		this.subTitulo = subTitulo;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof TipoChecKListEt) {

			TipoChecKListEt other = (TipoChecKListEt) obj;
			if (this.idTipoCheckList == null)
				return this == other;

			return this.idTipoCheckList.equals(other.idTipoCheckList);
		}
		return false;
	}

}
