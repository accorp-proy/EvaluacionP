package com.primax.jpa.pla;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.param.CriterioEvaluacionDetalleEt;
import com.primax.jpa.param.CriterioEvaluacionEt;
import com.primax.jpa.param.KPICriticoEt;
import com.primax.jpa.param.ProcesoDetalleEt;
import com.primax.jpa.param.TipoEstacionEt;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "CHECK_LIST_KPI_EJECUCION_ET")
@Audited
public class CheckListKpiEjecucionEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_check_list_kpi_ejecucion_et", sequenceName = "seq_check_list_kpi_ejecucion_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_check_list_kpi_ejecucion_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_check_list_kpi_ejecucion")
	private Long idCheckListKpiEjecucion;

	@Column(name = "orden")
	private Long orden;

	@Column(name = "puntaje")
	private Long puntaje;

	@Column(name = "puntaje_ejecucion")
	private Long puntajeEjecucion;

	@Column(name = "esfuerzo")
	private Long esfuerzo;

	@Column(name = "descripcion", length = 1000)
	private String descripcion;

	@Column(name = "comentario_control", length = 500)
	private String comentarioControl;

	@Column(name = "comentario_arqueo", length = 500)
	private String comentarioArqueo;

	@Column(name = "comentario_estacion", length = 500)
	private String comentarioEstacion;

	@Column(name = "comentario_plan_accion", length = 500)
	private String comentarioPlanAccion;

	@ManyToOne
	@JoinColumn(name = "id_proceso_detalle")
	private ProcesoDetalleEt procesoDetalle;

	@ManyToOne
	@JoinColumn(name = "id_check_list_proceso_ejecucion")
	private CheckListProcesoEjecucionEt checkListProcesoEjecucion;

	@ManyToOne
	@JoinColumn(name = "id_kpi_critico")
	private KPICriticoEt kPICritico;

	@ManyToOne
	@JoinColumn(name = "id_criterio_evaluacion")
	private CriterioEvaluacionEt criterioEvaluacion;

	@ManyToOne
	@JoinColumn(name = "id_criterio_evaluacion_detalle_seleccionado")
	private CriterioEvaluacionDetalleEt criterioEvaluacionDetalle;

	@Column(name = "fecha_arqueo")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaArqueo;

	@Column(name = "nombre_vendedor", length = 100)
	private String nombreVendedor;

	@Column(name = "turno")
	private Long turno;

	@Column(name = "valor_venta")
	private Double valorVenta;

	@Column(name = "valor_arqueo")
	private Double valorArqueo;

	@Column(name = "valor_diferencia")
	private Double valorDiferencia;

	@Column(name = "porcentaje")
	private Double porcentaje;

	@ManyToOne
	@JoinColumn(name = "id_tipo_estacion")
	private TipoEstacionEt tipoEstacion;

	@ManyToOne
	@JoinColumn(name = "id_usuario_auditor")
	private UsuarioEt auditor;

	@Column(name = "visualizar")
	private boolean visualizar;

	@Column(name = "sumar")
	private boolean sumar;

	@Column(name = "visualizar_reporte")
	private boolean visualizarReporte;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "checkListKpiEjecucion", fetch = FetchType.LAZY)
	@OrderBy("idArqueoFondoSuelto")
	@Where(clause = "estado = 'ACT'")
	private List<ArqueoFondoSueltoEt> arqueoFondoSuelto;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "checkListKpiEjecucion", fetch = FetchType.LAZY)
	@OrderBy("idArqueoCaja")
	@Where(clause = "estado = 'ACT'")
	private List<ArqueoCajaEt> arqueoCaja;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "checkListKpiEjecucion", fetch = FetchType.LAZY)
	@OrderBy("idArqueoCajaFuerte")
	@Where(clause = "estado = 'ACT'")
	private List<ArqueoCajaFuerteEt> arqueoCajaFuerte;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "checkListKpiEjecucion", fetch = FetchType.LAZY)
	@OrderBy("idArqueoCajaPromotor")
	@Where(clause = "estado = 'ACT'")
	private List<ArqueoCajaPromotorEt> arqueoCajaPromotor;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "checkListKpiEjecucion", fetch = FetchType.LAZY)
	@OrderBy("idArqueoCajaGeneral")
	@Where(clause = "estado = 'ACT'")
	private List<ArqueoCajaGeneralEt> arqueoCajaGeneral;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "checkListKpiEjecucion", fetch = FetchType.LAZY)
	@OrderBy("idCheckListKpiEjecucionFirma")
	@Where(clause = "estado = 'ACT'")
	private List<CheckListKpiEjecucionFirmaEt> checkListKpiEjecucionFirma;

	public CheckListKpiEjecucionEt() {
		this.turno = 0L;
		this.sumar = true;
		this.puntaje = 0L;
		this.esfuerzo = 0L;
		this.porcentaje = 0.0D;
		this.visualizar = true;
		this.valorVenta = 0.0D;
		this.valorArqueo = 0.0D;
		this.nombreVendedor = "";
		this.puntajeEjecucion = 0L;
		this.valorDiferencia = 0.0D;
		this.comentarioControl = "";
		this.comentarioEstacion = "";
		this.fechaArqueo = new Date();
		this.visualizarReporte = false;
		this.comentarioPlanAccion = "";
	}

	public Long getIdCheckListKpiEjecucion() {
		return idCheckListKpiEjecucion;
	}

	public void setIdCheckListKpiEjecucion(Long idCheckListKpiEjecucion) {
		this.idCheckListKpiEjecucion = idCheckListKpiEjecucion;
	}

	public CheckListProcesoEjecucionEt getCheckListProcesoEjecucion() {
		return checkListProcesoEjecucion;
	}

	public void setCheckListProcesoEjecucion(CheckListProcesoEjecucionEt checkListProcesoEjecucion) {
		this.checkListProcesoEjecucion = checkListProcesoEjecucion;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public ProcesoDetalleEt getProcesoDetalle() {
		return procesoDetalle;
	}

	public void setProcesoDetalle(ProcesoDetalleEt procesoDetalle) {
		this.procesoDetalle = procesoDetalle;
	}

	public KPICriticoEt getkPICritico() {
		return kPICritico;
	}

	public void setkPICritico(KPICriticoEt kPICritico) {
		this.kPICritico = kPICritico;
	}

	public CriterioEvaluacionEt getCriterioEvaluacion() {
		return criterioEvaluacion;
	}

	public void setCriterioEvaluacion(CriterioEvaluacionEt criterioEvaluacion) {
		this.criterioEvaluacion = criterioEvaluacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(Long puntaje) {
		this.puntaje = puntaje;
	}

	public Long getEsfuerzo() {
		return esfuerzo;
	}

	public void setEsfuerzo(Long esfuerzo) {
		this.esfuerzo = esfuerzo;
	}

	public CriterioEvaluacionDetalleEt getCriterioEvaluacionDetalle() {
		return criterioEvaluacionDetalle;
	}

	public void setCriterioEvaluacionDetalle(CriterioEvaluacionDetalleEt criterioEvaluacionDetalle) {
		this.criterioEvaluacionDetalle = criterioEvaluacionDetalle;
	}

	public Long getPuntajeEjecucion() {
		return puntajeEjecucion;
	}

	public void setPuntajeEjecucion(Long puntajeEjecucion) {
		this.puntajeEjecucion = puntajeEjecucion;
	}

	public List<ArqueoFondoSueltoEt> getArqueoFondoSuelto() {
		return arqueoFondoSuelto;
	}

	public void setArqueoFondoSuelto(List<ArqueoFondoSueltoEt> arqueoFondoSuelto) {
		this.arqueoFondoSuelto = arqueoFondoSuelto;
	}

	public List<ArqueoCajaEt> getArqueoCaja() {
		return arqueoCaja;
	}

	public void setArqueoCaja(List<ArqueoCajaEt> arqueoCaja) {
		this.arqueoCaja = arqueoCaja;
	}

	public List<ArqueoCajaGeneralEt> getArqueoCajaGeneral() {
		return arqueoCajaGeneral;
	}

	public void setArqueoCajaGeneral(List<ArqueoCajaGeneralEt> arqueoCajaGeneral) {
		this.arqueoCajaGeneral = arqueoCajaGeneral;
	}

	public Date getFechaArqueo() {
		return fechaArqueo;
	}

	public void setFechaArqueo(Date fechaArqueo) {
		this.fechaArqueo = fechaArqueo;
	}

	public String getNombreVendedor() {
		return nombreVendedor;
	}

	public void setNombreVendedor(String nombreVendedor) {
		this.nombreVendedor = nombreVendedor;
	}

	public Long getTurno() {
		return turno;
	}

	public void setTurno(Long turno) {
		this.turno = turno;
	}

	public TipoEstacionEt getTipoEstacion() {
		return tipoEstacion;
	}

	public void setTipoEstacion(TipoEstacionEt tipoEstacion) {
		this.tipoEstacion = tipoEstacion;
	}

	public Double getValorVenta() {
		return valorVenta;
	}

	public void setValorVenta(Double valorVenta) {
		this.valorVenta = valorVenta;
	}

	public Double getValorArqueo() {
		return valorArqueo;
	}

	public void setValorArqueo(Double valorArqueo) {
		this.valorArqueo = valorArqueo;
	}

	public Double getValorDiferencia() {
		return valorDiferencia;
	}

	public void setValorDiferencia(Double valorDiferencia) {
		this.valorDiferencia = valorDiferencia;
	}

	public String getComentarioControl() {
		return comentarioControl;
	}

	public void setComentarioControl(String comentarioControl) {
		this.comentarioControl = comentarioControl;
	}

	public String getComentarioEstacion() {
		return comentarioEstacion;
	}

	public void setComentarioEstacion(String comentarioEstacion) {
		this.comentarioEstacion = comentarioEstacion;
	}

	public boolean isVisualizar() {
		return visualizar;
	}

	public void setVisualizar(boolean visualizar) {
		this.visualizar = visualizar;
	}

	public boolean isSumar() {
		return sumar;
	}

	public void setSumar(boolean sumar) {
		this.sumar = sumar;
	}

	public Double getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(Double porcentaje) {
		this.porcentaje = porcentaje;
	}

	public String getComentarioArqueo() {
		return comentarioArqueo;
	}

	public void setComentarioArqueo(String comentarioArqueo) {
		this.comentarioArqueo = comentarioArqueo;
	}

	public UsuarioEt getAuditor() {
		return auditor;
	}

	public void setAuditor(UsuarioEt auditor) {
		this.auditor = auditor;
	}

	public boolean isVisualizarReporte() {
		return visualizarReporte;
	}

	public void setVisualizarReporte(boolean visualizarReporte) {
		this.visualizarReporte = visualizarReporte;
	}

	public String getComentarioPlanAccion() {
		return comentarioPlanAccion;
	}

	public void setComentarioPlanAccion(String comentarioPlanAccion) {
		this.comentarioPlanAccion = comentarioPlanAccion;
	}

	public List<CheckListKpiEjecucionFirmaEt> getCheckListKpiEjecucionFirma() {
		return checkListKpiEjecucionFirma;
	}

	public void setCheckListKpiEjecucionFirma(List<CheckListKpiEjecucionFirmaEt> checkListKpiEjecucionFirma) {
		this.checkListKpiEjecucionFirma = checkListKpiEjecucionFirma;
	}

	public List<ArqueoCajaFuerteEt> getArqueoCajaFuerte() {
		return arqueoCajaFuerte;
	}

	public void setArqueoCajaFuerte(List<ArqueoCajaFuerteEt> arqueoCajaFuerte) {
		this.arqueoCajaFuerte = arqueoCajaFuerte;
	}

	public List<ArqueoCajaPromotorEt> getArqueoCajaPromotor() {
		return arqueoCajaPromotor;
	}

	public void setArqueoCajaPromotor(List<ArqueoCajaPromotorEt> arqueoCajaPromotor) {
		this.arqueoCajaPromotor = arqueoCajaPromotor;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CheckListKpiEjecucionEt) {
			CheckListKpiEjecucionEt other = (CheckListKpiEjecucionEt) obj;
			if (this.idCheckListKpiEjecucion == null)
				return this == other;

			if (this.idCheckListKpiEjecucion.equals(other.idCheckListKpiEjecucion))
				return true;
		}
		return false;

	}

}
