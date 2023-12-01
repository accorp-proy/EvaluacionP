package com.primax.jpa.pla;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.enums.EstadoCheckListEnum;
import com.primax.jpa.enums.TipoCheckListEnum;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.param.NivelEvaluacionEt;
import com.primax.jpa.param.ParametrosGeneralesEt;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "CHECK_LIST_ET")
@Audited
public class CheckListEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -4838501690442140136L;

	@Id
	@SequenceGenerator(name = "sec_check_list_et", sequenceName = "seq_check_list_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_check_list_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_check_list")
	private Long idCheckList;

	@Enumerated(EnumType.STRING)
	@Column(name = "estado_check_List")
	protected EstadoCheckListEnum estadoCheckList;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_check_List")
	protected TipoCheckListEnum tipoCheckList;

	@ManyToOne
	@JoinColumn(name = "id_nivel_evaluacion")
	private NivelEvaluacionEt nivelEvaluacion;

	@ManyToOne
	@JoinColumn(name = "id_evaluacion")
	private EvaluacionEt evaluacion;

	@ManyToOne
	@JoinColumn(name = "id_tipo_check_list")
	private TipoChecKListEt tipoChecKList;

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

	@Column(name = "validar_criterio")
	private boolean validarCriterio;

	@Transient
	private UsuarioEt usuarioAsignado;

	@Transient
	private String nombreAuditor;

	@Column(name = "nivel_riego")
	private Long nivelRiesgo;

	/**
	 * Reporte Informe Dinámico
	 */
	@Column(name = "nombre", length = 100)
	private String nombre;

	@Column(name = "titulo", length = 300)
	private String titulo;

	@Column(name = "nombre_reemplazo", length = 100)
	private String nombreReemplazo;

	@Column(name = "titulo_reemplazo", length = 300)
	private String tituloReemplazo;

	@JoinColumn(name = "id_par_logo")
	private ParametrosGeneralesEt parametroLogo;

	@Column(name = "visualizar_img")
	private boolean visualizarImg;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "checkList", fetch = FetchType.LAZY)
	@OrderBy("orden asc")
	@Where(clause = "estado = 'ACT'")
	private List<CheckListProcesoEt> checkListProceso;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "checkList", fetch = FetchType.LAZY)
	@OrderBy("idCheckListInformeCabecera asc")
	@Where(clause = "estado = 'ACT'")
	private List<CheckListInformeCabeceraEt> checkListInformeCabecera;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "checkList", fetch = FetchType.LAZY)
	@OrderBy("orden asc")
	@Where(clause = "estado = 'ACT'")
	private List<CheckListPieFirmaEt> checkListPieFirma;

	public CheckListEt() {
		this.nivelRiesgo = 1L;
		this.visualizarPeso = false;
		this.validarCriterio = false;
		this.visualizarComentario = false;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public Long getIdCheckList() {
		return idCheckList;
	}

	public void setIdCheckList(Long idCheckList) {
		this.idCheckList = idCheckList;
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

	public List<CheckListProcesoEt> getCheckListProceso() {
		return checkListProceso;
	}

	public void setCheckListProceso(List<CheckListProcesoEt> checkListProceso) {
		this.checkListProceso = checkListProceso;
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

	public UsuarioEt getUsuarioAsignado() {
		return usuarioAsignado;
	}

	@Transient
	public void setUsuarioAsignado(UsuarioEt usuarioAsignado) {
		this.usuarioAsignado = usuarioAsignado;
	}

	public Long getNivelRiesgo() {
		return nivelRiesgo;
	}

	public void setNivelRiesgo(Long nivelRiesgo) {
		this.nivelRiesgo = nivelRiesgo;
	}

	public String getNombreAuditor() {
		return nombreAuditor;
	}

	public void setNombreAuditor(String nombreAuditor) {
		this.nombreAuditor = nombreAuditor;
	}

	public NivelEvaluacionEt getNivelEvaluacion() {
		return nivelEvaluacion;
	}

	public void setNivelEvaluacion(NivelEvaluacionEt nivelEvaluacion) {
		this.nivelEvaluacion = nivelEvaluacion;
	}

	public boolean isValidarCriterio() {
		return validarCriterio;
	}

	public void setValidarCriterio(boolean validarCriterio) {
		this.validarCriterio = validarCriterio;
	}

	public List<CheckListInformeCabeceraEt> getCheckListInformeCabecera() {
		return checkListInformeCabecera;
	}

	public void setCheckListInformeCabecera(List<CheckListInformeCabeceraEt> checkListInformeCabecera) {
		this.checkListInformeCabecera = checkListInformeCabecera;
	}

	public List<CheckListPieFirmaEt> getCheckListPieFirma() {
		return checkListPieFirma;
	}

	public void setCheckListPieFirma(List<CheckListPieFirmaEt> checkListPieFirma) {
		this.checkListPieFirma = checkListPieFirma;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getNombreReemplazo() {
		return nombreReemplazo;
	}

	public void setNombreReemplazo(String nombreReemplazo) {
		this.nombreReemplazo = nombreReemplazo;
	}

	public String getTituloReemplazo() {
		return tituloReemplazo;
	}

	public void setTituloReemplazo(String tituloReemplazo) {
		this.tituloReemplazo = tituloReemplazo;
	}

	public ParametrosGeneralesEt getParametroLogo() {
		return parametroLogo;
	}

	public void setParametroLogo(ParametrosGeneralesEt parametroLogo) {
		this.parametroLogo = parametroLogo;
	}

	public boolean isVisualizarImg() {
		return visualizarImg;
	}

	public void setVisualizarImg(boolean visualizarImg) {
		this.visualizarImg = visualizarImg;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof CheckListEt) {

			CheckListEt other = (CheckListEt) obj;
			if (this.idCheckList == null)
				return this == other;

			return this.idCheckList.equals(other.idCheckList);
		}
		return false;
	}

}
