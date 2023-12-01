package com.primax.jpa.param;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OrderBy;
import javax.persistence.ParameterMode;
import javax.persistence.SequenceGenerator;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "KPI_CRITICO_ET")
@Audited
@NamedStoredProcedureQuery(name = "getReporteEvaluacionVariacion", procedureName = "fun_limpiar_rpt_evaluacion_variacion", resultClasses = ProcesoDetalleEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })
public class KPICriticoEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -4838501690442140136L;

	@Id
	@SequenceGenerator(name = "sec_kpi_critico_et", sequenceName = "seq_kpi_critico_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_kpi_critico_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_kpi_critico")
	private Long idkpiCritico;

	@Column(name = "nombre", length = 100)
	@OrderBy("ORDER BY nombre")
	private String nombre;

	@Column(name = "descripcion", length = 200)
	private String descripcion;

	@Column(name = "nombre_imagen", length = 50)
	private String nombreImagen;

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

	public String getNombreImagen() {
		return nombreImagen;
	}

	public void setNombreImagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
	}

	public Long getIdkpiCritico() {
		return idkpiCritico;
	}

	public void setIdkpiCritico(Long idkpiCritico) {
		this.idkpiCritico = idkpiCritico;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof KPICriticoEt) {

			KPICriticoEt other = (KPICriticoEt) obj;
			if (this.idkpiCritico == null)
				return this == other;

			return this.idkpiCritico.equals(other.idkpiCritico);
		}
		return false;
	}

}
