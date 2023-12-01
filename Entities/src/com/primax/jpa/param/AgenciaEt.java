package com.primax.jpa.param;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "AGENCIA_ET")
@Audited
public class AgenciaEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_agencia_et", sequenceName = "seq_agencia_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_agencia_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_agencia")
	private Long idAgencia;

	@Column(name = "codigo_agencia", length = 10)
	private String codigoAgencia;

	@Column(name = "nombre_agencia", length = 50)
	private String nombreAgencia;

	@ManyToOne
	@JoinColumn(name = "id_canton")
	private CantonEt canton;

	@ManyToOne
	@JoinColumn(name = "id_zona")
	private ZonaEt zona;

	@ManyToOne
	@JoinColumn(name = "id_tipo_estacion")
	private TipoEstacionEt tipoEstacion;

	@ManyToOne
	@JoinColumn(name = "id_frecuencia_visita")
	private FrecuenciaVisitaEt frecuenciaVisita;

	@Column(name = "puntaje_optimo")
	private Long puntajeOptimo;

	@Column(name = "posicion_puntaje")
	private Long posicionPuntaje;

	@Column(name = "puntaje_ultima_ejecucion")
	private Long puntajeUltimaEjecucion;

	@Column(name = "descripcion_agencia", length = 200)
	private String descripcionAgencia;

	@Column(name = "fecha_ultima_ejecucion")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaUltimaEjecucion;

	@Column(name = "num_visita")
	private Long numVisita;

	@Column(name = "num_visita_inventario")
	private Long numVisitaInventario;

	@Column(name = "nivel_riesgo")
	private Long nivelRiesgo;

	@Column(name = "nivel_riesgo_s", length = 50)
	private String nivelRiesgoS;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "agencia", fetch = FetchType.LAZY)
	@Where(clause = "estado = 'ACT'")
	private List<AgenciaCategoriaEt> agenciaCategoria;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "agencia", fetch = FetchType.LAZY)
	@Where(clause = "estado = 'ACT'")
	private List<AgenciaCheckListEt> agenciaCheckList;

	public AgenciaEt() {
		this.numVisita = 1L;
		this.nivelRiesgo = 0L;
		this.puntajeOptimo = 0L;
		this.posicionPuntaje = 0L;
		this.nivelRiesgoS = "Bajo";
		this.numVisitaInventario = 1L;
		this.puntajeUltimaEjecucion = 0L;
		this.fechaUltimaEjecucion = new Date();
	}

	public Long getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(Long idAgencia) {
		this.idAgencia = idAgencia;
	}

	public String getCodigoAgencia() {
		return codigoAgencia;
	}

	public void setCodigoAgencia(String codigoAgencia) {
		this.codigoAgencia = codigoAgencia;
	}

	public String getNombreAgencia() {
		return nombreAgencia;
	}

	public void setNombreAgencia(String nombreAgencia) {
		this.nombreAgencia = nombreAgencia;
	}

	public CantonEt getCanton() {
		return canton;
	}

	public void setCanton(CantonEt canton) {
		this.canton = canton;
	}

	public ZonaEt getZona() {
		return zona;
	}

	public void setZona(ZonaEt zona) {
		this.zona = zona;
	}

	public String getDescripcionAgencia() {
		return descripcionAgencia;
	}

	public void setDescripcionAgencia(String descripcionAgencia) {
		this.descripcionAgencia = descripcionAgencia;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TipoEstacionEt getTipoEstacion() {
		return tipoEstacion;
	}

	public void setTipoEstacion(TipoEstacionEt tipoEstacion) {
		this.tipoEstacion = tipoEstacion;
	}

	public List<AgenciaCategoriaEt> getAgenciaCategoria() {
		return agenciaCategoria;
	}

	public void setAgenciaCategoria(List<AgenciaCategoriaEt> agenciaCategoria) {
		this.agenciaCategoria = agenciaCategoria;
	}

	public List<AgenciaCheckListEt> getAgenciaCheckList() {
		return agenciaCheckList;
	}

	public void setAgenciaCheckList(List<AgenciaCheckListEt> agenciaCheckList) {
		this.agenciaCheckList = agenciaCheckList;
	}

	public Long getPosicionPuntaje() {
		return posicionPuntaje;
	}

	public void setPosicionPuntaje(Long posicionPuntaje) {
		this.posicionPuntaje = posicionPuntaje;
	}

	public Date getFechaUltimaEjecucion() {
		return fechaUltimaEjecucion;
	}

	public void setFechaUltimaEjecucion(Date fechaUltimaEjecucion) {
		this.fechaUltimaEjecucion = fechaUltimaEjecucion;
	}

	public Long getPuntajeOptimo() {
		return puntajeOptimo;
	}

	public void setPuntajeOptimo(Long puntajeOptimo) {
		this.puntajeOptimo = puntajeOptimo;
	}

	public Long getPuntajeUltimaEjecucion() {
		return puntajeUltimaEjecucion;
	}

	public void setPuntajeUltimaEjecucion(Long puntajeUltimaEjecucion) {
		this.puntajeUltimaEjecucion = puntajeUltimaEjecucion;
	}

	public Long getNivelRiesgo() {
		return nivelRiesgo;
	}

	public void setNivelRiesgo(Long nivelRiesgo) {
		this.nivelRiesgo = nivelRiesgo;
	}

	public String getNivelRiesgoS() {
		return nivelRiesgoS;
	}

	public void setNivelRiesgoS(String nivelRiesgoS) {
		this.nivelRiesgoS = nivelRiesgoS;
	}

	@Override
	public String toString() {
		return this.nombreAgencia;
	}

	public FrecuenciaVisitaEt getFrecuenciaVisita() {
		return frecuenciaVisita;
	}

	public void setFrecuenciaVisita(FrecuenciaVisitaEt frecuenciaVisita) {
		this.frecuenciaVisita = frecuenciaVisita;
	}

	public Long getNumVisita() {
		return numVisita;
	}

	public void setNumVisita(Long numVisita) {
		this.numVisita = numVisita;
	}

	public Long getNumVisitaInventario() {
		return numVisitaInventario;
	}

	public void setNumVisitaInventario(Long numVisitaInventario) {
		this.numVisitaInventario = numVisitaInventario;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AgenciaEt) {
			AgenciaEt other = (AgenciaEt) obj;

			if (this.idAgencia == null)
				return this == other;

			if (this.idAgencia.equals(other.idAgencia))
				return true;
		}
		return false;

	}

}
