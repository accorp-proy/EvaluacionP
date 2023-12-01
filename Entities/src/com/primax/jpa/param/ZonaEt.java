package com.primax.jpa.param;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.SequenceGenerator;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.pla.ReporteTipoEvaluacionEt;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "ZONA_ET")
@Audited

@NamedStoredProcedureQuery(name = "getLimpiarReporte", procedureName = "fun_limpiar_rpt", resultClasses = ReporteTipoEvaluacionEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class ZonaEt extends EntityBase {

	@Id
	@SequenceGenerator(name = "sec_zona_et", sequenceName = "seq_zona_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_zona_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_zona")
	private Long idZona;

	@Column(name = "orden")
	private Long orden;

	@Column(name = "nombre_zona")
	private String nombreZona;

	public ZonaEt() {
		this.orden = 0L;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public Long getIdZona() {
		return idZona;
	}

	public void setIdZona(Long idZona) {
		this.idZona = idZona;
	}

	public String getNombreZona() {
		return nombreZona;
	}

	public void setNombreZona(String nombreZona) {
		this.nombreZona = nombreZona;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ZonaEt) {
			ZonaEt other = (ZonaEt) obj;

			if (this.idZona == null)
				return this == other;

			return this.idZona.equals(other.idZona) ? true : false;
		} else
			return false;
	}

}
