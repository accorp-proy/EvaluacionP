package com.primax.jpa.pla;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.enums.EstadoCheckListEnum;
import com.primax.jpa.enums.EstadoPlanAccionEnum;
import com.primax.jpa.enums.TipoCheckListEnum;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.param.NivelEvaluacionDetalleEt;
import com.primax.jpa.param.NivelEvaluacionEt;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "CHECK_LIST_EJECUCION_ET")
@Audited

@NamedStoredProcedureQuery(name = "getActNivelRiego", procedureName = "fun_actualiza_nivel_riego", resultClasses = CheckListEjecucionEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idNivelEvaluacion"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idCheckListEjecucion"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class CheckListEjecucionEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -4838501690442140136L;

	@Id
	@SequenceGenerator(name = "sec_check_list_ejecucion_et", sequenceName = "seq_check_list_ejecucion_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_check_list_ejecucion_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_check_list_ejecucion")
	private Long idCheckListEjecucion;

	@Enumerated(EnumType.STRING)
	@Column(name = "estado_check_List")
	private EstadoCheckListEnum estadoCheckList;

	@Enumerated(EnumType.STRING)
	@Column(name = "estado_plan_accion")
	private EstadoPlanAccionEnum estadoPlanAccion;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_check_List")
	private TipoCheckListEnum tipoCheckList;

	@ManyToOne
	@JoinColumn(name = "id_nivel_evaluacion")
	private NivelEvaluacionEt nivelEvaluacion;

	@ManyToOne
	@JoinColumn(name = "id_nivel_evaluacion_detalle")
	private NivelEvaluacionDetalleEt nivelEvaluacionDetalle;

	@Column(name = "color_nivel", length = 20)
	private String colorNivel;

	@ManyToOne
	@JoinColumn(name = "id_evaluacion")
	private EvaluacionEt evaluacion;

	@ManyToOne
	@JoinColumn(name = "id_tipo_check_list")
	private TipoChecKListEt tipoChecKList;

	@ManyToOne
	@JoinColumn(name = "id_usuario_asignado")
	private UsuarioEt usuarioAsignado;

	@Column(name = "orden")
	private Long orden;

	@Column(name = "descripcion", length = 1000)
	@OrderBy("order by descripcion")
	private String descripcion;

	@Column(name = "codigo", length = 20)
	private String codigo;

	@Column(name = "visualizar_comentario")
	private boolean visualizarComentario;

	@Column(name = "visualizar_peso")
	private boolean visualizarPeso;

	@Column(name = "ejecutando")
	private boolean ejecutando;

	@Column(name = "modificado")
	private boolean modificado;

	@Column(name = "plan_accion")
	private boolean planAccion;

	@Column(name = "nivel_riesgo")
	private Long nivelRiesgo;

	@Column(name = "nivel_riesgo_s", length = 50)
	private String nivelRiesgoS;

	@Column(name = "calificacion")
	private Long calificacion;

	@ManyToOne
	@JoinColumn(name = "id_planificacion")
	private PlanificacionEt planificacion;

	@Transient
	private Long idCheckList;

	@Column(name = "fecha_ejecucion")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaEjecucion;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "checkListEjecucion", fetch = FetchType.LAZY)
	@OrderBy("orden asc")
	@Where(clause = "estado = 'ACT' and visualizar='true' ")
	private List<CheckListProcesoEjecucionEt> checkListProcesoEjecucion;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "checkListEjecucion", fetch = FetchType.LAZY)
	@OrderBy("nombreAdjunto asc")
	@Where(clause = "estado = 'ACT'")
	private List<CheckListEjecucionAdjuntoEt> checkListEjecucionAdjunto;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "checkListEjecucion", fetch = FetchType.LAZY)
	@OrderBy("identificacion asc")
	@Where(clause = "estado = 'ACT'")
	private List<CheckListEjecucionFirmaEt> checkListEjecucionFirma;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "checkListEjecucion", fetch = FetchType.LAZY)
	@Where(clause = "estado = 'ACT'")
	private List<CheckListEjecucionReporteEt> checkListEjecucionReporte;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "checkListEjecucion", fetch = FetchType.LAZY)
	@OrderBy("nombreAdjunto asc")
	@Where(clause = "estado = 'ACT'")
	private List<CheckListEjecucionPlnAdjuntoEt> checkListEjecucionPlnAdjunto;

	public CheckListEjecucionEt() {
		this.nivelRiesgo = 0L;
		this.calificacion = 0L;
		this.modificado = false;
		this.planAccion = false;
		this.ejecutando = false;
		this.nivelRiesgoS = null;
		this.colorNivel = "#FFFFFF";
		this.visualizarPeso = false;
		this.visualizarComentario = false;
		this.estadoPlanAccion = EstadoPlanAccionEnum.PENDIENTE;
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public List<CheckListProcesoEjecucionEt> getCheckListProcesoEjecucion() {
		return checkListProcesoEjecucion;
	}

	public void setCheckListProcesoEjecucion(List<CheckListProcesoEjecucionEt> checkListProcesoEjecucion) {
		this.checkListProcesoEjecucion = checkListProcesoEjecucion;
	}

	public EstadoCheckListEnum getEstadoCheckList() {
		return estadoCheckList;
	}

	public void setEstadoCheckList(EstadoCheckListEnum estadoCheckList) {
		this.estadoCheckList = estadoCheckList;
	}

	public TipoCheckListEnum getTipoCheckList() {
		return tipoCheckList;
	}

	public void setTipoCheckList(TipoCheckListEnum tipoCheckList) {
		this.tipoCheckList = tipoCheckList;
	}

	public EvaluacionEt getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(EvaluacionEt evaluacion) {
		this.evaluacion = evaluacion;
	}

	public TipoChecKListEt getTipoChecKList() {
		return tipoChecKList;
	}

	public void setTipoChecKList(TipoChecKListEt tipoChecKList) {
		this.tipoChecKList = tipoChecKList;
	}

	public boolean isVisualizarComentario() {
		return visualizarComentario;
	}

	public void setVisualizarComentario(boolean visualizarComentario) {
		this.visualizarComentario = visualizarComentario;
	}

	public boolean isVisualizarPeso() {
		return visualizarPeso;
	}

	public void setVisualizarPeso(boolean visualizarPeso) {
		this.visualizarPeso = visualizarPeso;
	}

	public Long getIdCheckListEjecucion() {
		return idCheckListEjecucion;
	}

	public void setIdCheckListEjecucion(Long idCheckListEjecucion) {
		this.idCheckListEjecucion = idCheckListEjecucion;
	}

	public PlanificacionEt getPlanificacion() {
		return planificacion;
	}

	public void setPlanificacion(PlanificacionEt planificacion) {
		this.planificacion = planificacion;
	}

	public UsuarioEt getUsuarioAsignado() {
		return usuarioAsignado;
	}

	public void setUsuarioAsignado(UsuarioEt usuarioAsignado) {
		this.usuarioAsignado = usuarioAsignado;
	}

	public boolean isEjecutando() {
		return ejecutando;
	}

	public void setEjecutando(boolean ejecutando) {
		this.ejecutando = ejecutando;
	}

	public Long getNivelRiesgo() {
		return nivelRiesgo;
	}

	public void setNivelRiesgo(Long nivelRiesgo) {
		this.nivelRiesgo = nivelRiesgo;
	}

	public Long getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(Long calificacion) {
		this.calificacion = calificacion;
	}

	public Long getIdCheckList() {
		return idCheckList;
	}

	public void setIdCheckList(Long idCheckList) {
		this.idCheckList = idCheckList;
	}

	public List<CheckListEjecucionAdjuntoEt> getCheckListEjecucionAdjunto() {
		return checkListEjecucionAdjunto;
	}

	public void setCheckListEjecucionAdjunto(List<CheckListEjecucionAdjuntoEt> checkListEjecucionAdjunto) {
		this.checkListEjecucionAdjunto = checkListEjecucionAdjunto;
	}

	public String getNivelRiesgoS() {
		return nivelRiesgoS;
	}

	public void setNivelRiesgoS(String nivelRiesgoS) {
		this.nivelRiesgoS = nivelRiesgoS;
	}

	public Date getFechaEjecucion() {
		return fechaEjecucion;
	}

	public void setFechaEjecucion(Date fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}

	public boolean isPlanAccion() {
		return planAccion;
	}

	public void setPlanAccion(boolean planAccion) {
		this.planAccion = planAccion;
	}

	public EstadoPlanAccionEnum getEstadoPlanAccion() {
		return estadoPlanAccion;
	}

	public void setEstadoPlanAccion(EstadoPlanAccionEnum estadoPlanAccion) {
		this.estadoPlanAccion = estadoPlanAccion;
	}

	public List<CheckListEjecucionFirmaEt> getCheckListEjecucionFirma() {
		return checkListEjecucionFirma;
	}

	public void setCheckListEjecucionFirma(List<CheckListEjecucionFirmaEt> checkListEjecucionFirma) {
		this.checkListEjecucionFirma = checkListEjecucionFirma;
	}

	public boolean isModificado() {
		return modificado;
	}

	public void setModificado(boolean modificado) {
		this.modificado = modificado;
	}

	public NivelEvaluacionEt getNivelEvaluacion() {
		return nivelEvaluacion;
	}

	public void setNivelEvaluacion(NivelEvaluacionEt nivelEvaluacion) {
		this.nivelEvaluacion = nivelEvaluacion;
	}

	public NivelEvaluacionDetalleEt getNivelEvaluacionDetalle() {
		return nivelEvaluacionDetalle;
	}

	public void setNivelEvaluacionDetalle(NivelEvaluacionDetalleEt nivelEvaluacionDetalle) {
		this.nivelEvaluacionDetalle = nivelEvaluacionDetalle;
	}

	public String getColorNivel() {
		return colorNivel;
	}

	public void setColorNivel(String colorNivel) {
		this.colorNivel = colorNivel;
	}

	public List<CheckListEjecucionReporteEt> getCheckListEjecucionReporte() {
		return checkListEjecucionReporte;
	}

	public void setCheckListEjecucionReporte(List<CheckListEjecucionReporteEt> checkListEjecucionReporte) {
		this.checkListEjecucionReporte = checkListEjecucionReporte;
	}

	public List<CheckListEjecucionPlnAdjuntoEt> getCheckListEjecucionPlnAdjunto() {
		return checkListEjecucionPlnAdjunto;
	}

	public void setCheckListEjecucionPlnAdjunto(List<CheckListEjecucionPlnAdjuntoEt> checkListEjecucionPlnAdjunto) {
		this.checkListEjecucionPlnAdjunto = checkListEjecucionPlnAdjunto;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof CheckListEjecucionEt) {

			CheckListEjecucionEt other = (CheckListEjecucionEt) obj;
			if (this.idCheckListEjecucion == null)
				return this == other;

			return this.idCheckListEjecucion.equals(other.idCheckListEjecucion);
		}
		return false;
	}

}
