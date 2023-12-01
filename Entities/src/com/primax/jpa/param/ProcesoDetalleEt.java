package com.primax.jpa.param;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.pla.CheckListProcesoEt;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "PROCESO_DETALLE_ET")
@Audited
public class ProcesoDetalleEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_proceso_detalle_et", sequenceName = "seq_proceso_detalle_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_proceso_detalle_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_proceso_detalle")
	private Long idProcesoDetalle;

	@Column(name = "nombre_proceso_detalle", length = 1000)
	@OrderBy("order by nombreProcesoDetalle")
	private String nombreProcesoDetalle;

	@Column(name = "codigo_proceso_detalle", length = 10)
	private String codigoProcesoDetalle;

	@Column(name = "orden")
	private Long orden;

	@Column(name = "puntaje")
	private Long puntaje;

	@ManyToOne
	@JoinColumn(name = "id_nivel_esfuerzo")
	private NivelEsfuerzoEt nivelEsfuerzo;

	@Column(name = "riesgo_latente", length = 500)
	private String riesgoLatente;

	@ManyToOne
	@JoinColumn(name = "id_proceso")
	private ProcesoEt proceso;

	@ManyToOne
	@JoinColumn(name = "id_check_list_detalle")
	private CheckListProcesoEt checkListDetalle;

	@Column(name = "visualizar_matriz")
	private boolean visualizarMatriz;

	@Column(name = "arqueo")
	private boolean arqueo;

	/**
	 * Reporte Novedades Control Interno
	 */

	@Column(name = "orden_reporte", length = 10)
	private String ordenReporte;

	@Column(name = "visualizar_reporte")
	private boolean visualizarReporte;

	@Column(name = "orden_seccion_0", length = 10)
	private String ordenSeccion0;

	@Column(name = "seccion_0", length = 500)
	private String seccion0;

	public ProcesoDetalleEt() {
		this.orden = 0L;
		this.puntaje = 1L;
		this.arqueo = false;
		this.visualizarMatriz = true;
		this.visualizarReporte = false;
		this.nombreProcesoDetalle = "";
		this.codigoProcesoDetalle = "";
	}

	public Long getIdProcesoDetalle() {
		return idProcesoDetalle;
	}

	public void setIdProcesoDetalle(Long idProcesoDetalle) {
		this.idProcesoDetalle = idProcesoDetalle;
	}

	public String getNombreProcesoDetalle() {
		return nombreProcesoDetalle;
	}

	public void setNombreProcesoDetalle(String nombreProcesoDetalle) {
		this.nombreProcesoDetalle = nombreProcesoDetalle;
	}

	public String getCodigoProcesoDetalle() {
		return codigoProcesoDetalle;
	}

	public void setCodigoProcesoDetalle(String codigoProcesoDetalle) {
		this.codigoProcesoDetalle = codigoProcesoDetalle;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public Long getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(Long puntaje) {
		this.puntaje = puntaje;
	}

	public NivelEsfuerzoEt getNivelEsfuerzo() {
		return nivelEsfuerzo;
	}

	public void setNivelEsfuerzo(NivelEsfuerzoEt nivelEsfuerzo) {
		this.nivelEsfuerzo = nivelEsfuerzo;
	}

	public ProcesoEt getProceso() {
		return proceso;
	}

	public void setProceso(ProcesoEt proceso) {
		this.proceso = proceso;
	}

	public CheckListProcesoEt getCheckListDetalle() {
		return checkListDetalle;
	}

	public void setCheckListDetalle(CheckListProcesoEt checkListDetalle) {
		this.checkListDetalle = checkListDetalle;
	}

	public String getRiesgoLatente() {
		return riesgoLatente;
	}

	public void setRiesgoLatente(String riesgoLatente) {
		this.riesgoLatente = riesgoLatente;
	}

	public boolean isVisualizarMatriz() {
		return visualizarMatriz;
	}

	public void setVisualizarMatriz(boolean visualizarMatriz) {
		this.visualizarMatriz = visualizarMatriz;
	}

	public boolean isVisualizarReporte() {
		return visualizarReporte;
	}

	public void setVisualizarReporte(boolean visualizarReporte) {
		this.visualizarReporte = visualizarReporte;
	}

	public String getOrdenReporte() {
		return ordenReporte;
	}

	public void setOrdenReporte(String ordenReporte) {
		this.ordenReporte = ordenReporte;
	}

	public String getSeccion0() {
		return seccion0;
	}

	public void setSeccion0(String seccion0) {
		this.seccion0 = seccion0;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	public String getOrdenSeccion0() {
		return ordenSeccion0;
	}

	public void setOrdenSeccion0(String ordenSeccion0) {
		this.ordenSeccion0 = ordenSeccion0;
	}

	public boolean isArqueo() {
		return arqueo;
	}

	public void setArqueo(boolean arqueo) {
		this.arqueo = arqueo;
	}

	@Override
	public String toString() {
		return this.nombreProcesoDetalle;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ProcesoDetalleEt) {
			ProcesoDetalleEt other = (ProcesoDetalleEt) obj;
			if (this.idProcesoDetalle == null)
				return this == other;

			if (this.idProcesoDetalle.equals(other.idProcesoDetalle))
				return true;
		}
		return false;

	}

}
