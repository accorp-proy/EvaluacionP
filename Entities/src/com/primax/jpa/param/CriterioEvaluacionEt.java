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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "CRITERIO_EVALUACION_ET")
@Audited
public class CriterioEvaluacionEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -4838501690442140136L;

	@Id
	@SequenceGenerator(name = "sec_criterio_evaluacion_et", sequenceName = "seq_criterio_evaluacion_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_criterio_evaluacion_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_criterio_evaluacion")
	private Long idCriterioEvaluacion;

	@Column(name = "nombre", length = 100)
	@OrderBy("ORDER BY nombre")
	private String nombre;

	@Column(name = "arqueo")
	private boolean arqueo;

	@ManyToOne
	@JoinColumn(name = "id_evaluacion")
	private EvaluacionEt evaluacion;

	@ManyToOne
	@JoinColumn(name = "id_tipo_check_list")
	private TipoChecKListEt tipoChecKList;

	@ManyToOne
	@JoinColumn(name = "id_proceso_detalle")
	private ProcesoDetalleEt procesoDetalle;

	/**
	 * Atributo creado para diferenciar tipos de arqueos N --> No es un kpi de
	 * Arqueo C --> Arqueo en visita de Control Interno F --> Disponibilidad
	 * Fondo de Suelto Tienda en el área de Caja E --> Arqueo de caja chica de
	 * estación de servicio
	 */
	@Column(name = "tipo", length = 1)
	private String tipo;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "criterioEvaluacion", fetch = FetchType.LAZY)
	@OrderBy("orden asc")
	@Where(clause = "estado = 'ACT'")
	private List<CriterioEvaluacionDetalleEt> criterioEvaluacionDetalle;

	public CriterioEvaluacionEt() {
		this.arqueo = false;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getIdCriterioEvaluacion() {
		return idCriterioEvaluacion;
	}

	public void setIdCriterioEvaluacion(Long idCriterioEvaluacion) {
		this.idCriterioEvaluacion = idCriterioEvaluacion;
	}

	public EvaluacionEt getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(EvaluacionEt evaluacion) {
		this.evaluacion = evaluacion;
	}

	public List<CriterioEvaluacionDetalleEt> getCriterioEvaluacionDetalle() {
		return criterioEvaluacionDetalle;
	}

	public void setCriterioEvaluacionDetalle(List<CriterioEvaluacionDetalleEt> criterioEvaluacionDetalle) {
		this.criterioEvaluacionDetalle = criterioEvaluacionDetalle;
	}

	public TipoChecKListEt getTipoChecKList() {
		return tipoChecKList;
	}

	public void setTipoChecKList(TipoChecKListEt tipoChecKList) {
		this.tipoChecKList = tipoChecKList;
	}

	public ProcesoDetalleEt getProcesoDetalle() {
		return procesoDetalle;
	}

	public void setProcesoDetalle(ProcesoDetalleEt procesoDetalle) {
		this.procesoDetalle = procesoDetalle;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public boolean isArqueo() {
		return arqueo;
	}

	public void setArqueo(boolean arqueo) {
		this.arqueo = arqueo;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof CriterioEvaluacionEt) {

			CriterioEvaluacionEt other = (CriterioEvaluacionEt) obj;
			if (this.idCriterioEvaluacion == null)
				return this == other;

			return this.idCriterioEvaluacion.equals(other.idCriterioEvaluacion);
		}
		return false;
	}

}
