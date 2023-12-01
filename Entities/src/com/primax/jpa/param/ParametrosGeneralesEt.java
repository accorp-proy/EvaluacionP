package com.primax.jpa.param;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "PARAMETROS_GENERALES_ET")
@Audited
@NamedStoredProcedureQuery(name = "getReporteUltimaVisita", procedureName = "fun_limpiar_rpt_ultima_visita", resultClasses = ProcesoDetalleEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })
public class ParametrosGeneralesEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = 8116182218010504236L;

	public ParametrosGeneralesEt() {
		parametros = new ArrayList<>();
	}

	@Id
	@Column(name = "id_parametro_general")
	@GeneratedValue(generator = "sec_parametro_general", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "sec_parametro_general", sequenceName = "seq_parametro_general", allocationSize = 1, initialValue = 1)
	private Long idParametroGeneral;

	@Column(name = "nombre_lista")
	private String nombreLista;

	@Column(name = "descripcion_lista")
	private String descripcionLista;

	@Column(name = "val_lista")
	private String valorLista;

	@Column(name = "nivel")
	private Long nivel;

	@Column(name = "orden")
	private Long orden;

	@ManyToOne
	@JoinColumn(name = "id_parametro_padre")
	private ParametrosGeneralesEt parametroPadre;

	@OneToMany(mappedBy = "parametroPadre", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy("orden ")
	@Where(clause = "estado='ACT'")
	private List<ParametrosGeneralesEt> parametros;

	@Column(name = "encoded_value")
	private Boolean encodedValue;

	public Long getIdParametroGeneral() {
		return idParametroGeneral;
	}

	public void setIdParametroGeneral(Long idParametroGeneral) {
		this.idParametroGeneral = idParametroGeneral;
	}

	public ParametrosGeneralesEt getParametroPadre() {
		return parametroPadre;
	}

	public void setParametroPadre(ParametrosGeneralesEt parametroPadre) {
		this.parametroPadre = parametroPadre;
	}

	@XmlElement
	public String getNombreLista() {
		return nombreLista;
	}

	public void setNombreLista(String nombreLista) {
		this.nombreLista = nombreLista;
	}

	public String getDescripcionLista() {
		return descripcionLista;
	}

	public void setDescripcionLista(String descripcionLista) {
		this.descripcionLista = descripcionLista;
	}

	public Long getNivel() {
		return nivel;
	}

	@XmlTransient
	public void setNivel(Long nivel) {
		this.nivel = nivel;
	}

	public List<ParametrosGeneralesEt> getParametros() {
		return parametros;
	}

	@XmlTransient
	public void setParametros(List<ParametrosGeneralesEt> parametros) {
		this.parametros = parametros;
	}

	public String getValorLista() {
		return valorLista;
	}

	@XmlElement
	public void setValorLista(String valorLista) {
		this.valorLista = valorLista;
	}

	@XmlElement
	public void setCodGrupoPadre(Long codGrupo) {

	}

	public Long getCodGrupoPadre() {
		if (this.parametroPadre != null) {
			return this.parametroPadre.getIdParametroGeneral();
		}
		return null;
	}

	public Long getOrden() {
		return orden;
	}

	@XmlElement
	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public Boolean getEncodedValue() {
		return encodedValue;
	}

	public void setEncodedValue(Boolean encodedValue) {
		this.encodedValue = encodedValue;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof ParametrosGeneralesEt) {

			ParametrosGeneralesEt other = (ParametrosGeneralesEt) obj;

			if (this.idParametroGeneral == null)
				return this == other;

			return this.idParametroGeneral.equals(other.idParametroGeneral);
		}
		return false;
	}

}
