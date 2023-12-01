package com.primax.jpa.param;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.ParameterMode;
import javax.persistence.SequenceGenerator;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "PROCESO_ET")
@Audited
@NamedStoredProcedureQuery(name = "getReporteTipoEvaluacion", procedureName = "fun_limpiar_rpt_tipo_evaluacion", resultClasses = ProcesoDetalleEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })
public class ProcesoEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -4838501690442140136L;

	@Id
	@SequenceGenerator(name = "sec_proceso_et", sequenceName = "seq_proceso_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_proceso_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_proceso")
	private Long idproceso;

	@Column(name = "orden")
	private Long orden;

	@Column(name = "nombre_proceso", length = 80)
	private String nombreProceso;

	@Column(name = "codigo_proceso", length = 10)
	private String codigoProceso;

	@ManyToOne
	@JoinColumn(name = "id_tipo_check_list")
	private TipoChecKListEt tipoChecKList;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", fetch = FetchType.LAZY)
	@OrderBy("idProcesoDetalle ")
	@Where(clause = "estado = 'ACT'")
	private List<ProcesoDetalleEt> procesoDetalle;

	public Long getIdproceso() {
		return idproceso;
	}

	public void setIdproceso(Long idproceso) {
		this.idproceso = idproceso;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public String getNombreProceso() {
		return nombreProceso;
	}

	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
	}

	public String getCodigoProceso() {
		return codigoProceso;
	}

	public void setCodigoProceso(String codigoProceso) {
		this.codigoProceso = codigoProceso;
	}

	public TipoChecKListEt getTipoChecKList() {
		return tipoChecKList;
	}

	public void setTipoChecKList(TipoChecKListEt tipoChecKList) {
		this.tipoChecKList = tipoChecKList;
	}

	public List<ProcesoDetalleEt> getProcesoDetalle() {
		return procesoDetalle;
	}

	public void setProcesoDetalle(List<ProcesoDetalleEt> procesoDetalle) {
		this.procesoDetalle = procesoDetalle;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof ProcesoEt) {

			ProcesoEt other = (ProcesoEt) obj;
			if (this.idproceso == null)
				return this == other;

			return this.idproceso.equals(other.idproceso);
		}
		return false;
	}

}
