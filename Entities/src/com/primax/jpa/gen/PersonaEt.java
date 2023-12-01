package com.primax.jpa.gen;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hibernate.envers.Audited;

import com.primax.adapters.DateAdapterShortBw;
import com.primax.enm.gen.EstadoCivilEnum;
import com.primax.enm.gen.TipoIdentificacionEnum;
import com.primax.enm.gen.TipoPersonaEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.param.ParametrosGeneralesEt;
import com.primax.jpa.sec.PersonaImagenEt;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "PERSONA_ET")
@Audited
public class PersonaEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = 4907234054041537967L;

	@Id
	@Column(name = "id_persona")
	@SequenceGenerator(name = "sec_persona", allocationSize = 1, initialValue = 1, sequenceName = "seq_persona")
	@GeneratedValue(generator = "sec_persona", strategy = GenerationType.SEQUENCE)
	private Long idPersona;

	@Column(name = "per_identificacion", length = 20)
	private String identificacionPersona;

	@Column(name = "primer_nombre")
	private String primerNombre;

	@Column(name = "segundo_nombre")
	private String segundoNombre;

	@Column(name = "primer_apellido")
	private String primerApellido;

	@Column(name = "segundo_apellido")
	private String segundoApellido;

	@Column(name = "nombre_completo", length = 100)
	private String nombreCompleto;

	@Column(name = "fecha_nacimiento", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;

	@Column(name = "direccion_domicilio")
	private String direccionDomicilio;

	@Column(name = "email")
	private String email;

	@Column(name = "email_alterno")
	private String emailAlterno;

	@Column(name = "email_alterno_1")
	private String emailAlterno1;

	@Column(name = "tipo_persona")
	@Enumerated(EnumType.ORDINAL)
	private TipoPersonaEnum tipoPersona;

	@Column(name = "tipo_identificacion")
	@Enumerated(EnumType.STRING)
	private TipoIdentificacionEnum tipoIdentificacion;

	@Column(name = "per_telf_convencional", length = 50)
	private String telefonoConvencional;

	@Column(name = "per_telf_convencional_alterno", length = 50)
	private String telefonoConvencionalAlterno;

	@Column(name = "per_telf_convencional_alterno1", length = 50)
	private String telefonoConvencionalAlterno1;

	@Column(name = "ext_telf_convencional", length = 4)
	private String extTelefonoConvencional;

	@Column(name = "per_telf_movil", length = 50)
	private String telefonoMovil;

	@Column(name = "per_telf_movil_alterno", length = 50)
	private String telefonoMovilAlterno;

	@Column(name = "per_telf_movil_alterno1", length = 50)
	private String telefonoMovilAlterno1;

	@OneToMany(mappedBy = "personaUsuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<UsuarioEt> usuarios;

	@OneToMany(mappedBy = "personaContacto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<PersonaContactoEt> personaContacto;

	@ManyToOne
	@JoinColumn(name = "id_ubicacion")
	private UbicacionEt ubicacion;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "persona_imagen")
	private PersonaImagenEt personaImagen;

	@ManyToOne
	@JoinColumn(name = "par_tipo_identificacion")
	private ParametrosGeneralesEt parTipoIdentificacion;

	@Column(name = "estado_civil")
	@Enumerated(EnumType.STRING)
	private EstadoCivilEnum estadoCivil;

	@Column(name = "firma", length = 50000)
	private String firma;

	public PersonaEt() {
		this.firma = null;
		this.ubicacion = new UbicacionEt();
	}

	public String getPrimerNombre() {
		return primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public String getSegundoNombre() {
		return segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public PersonaImagenEt getPersonaImagen() {
		return personaImagen;
	}

	public void setPersonaImagen(PersonaImagenEt personaImagen) {
		this.personaImagen = personaImagen;
	}

	@Transient
	private Integer edad;

	public Long getIdPersona() {
		return idPersona;
	}

	@XmlElement
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public String getIdentificacionPersona() {
		return identificacionPersona;
	}

	@XmlElement
	public void setIdentificacionPersona(String identificacionPersona) {
		this.identificacionPersona = identificacionPersona;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	@XmlElement
	@XmlJavaTypeAdapter(DateAdapterShortBw.class)
	public void setFechaNacimiento(Date fechaNacimiento) {
		if (fechaNacimiento != null) {
			Calendar dt = Calendar.getInstance();
			dt.setTime(fechaNacimiento);
			Calendar dact = Calendar.getInstance();
			edad = dact.get(Calendar.YEAR) - dt.get(Calendar.YEAR);
		}
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getDireccionDomicilio() {
		return direccionDomicilio;
	}

	@XmlElement
	public void setDireccionDomicilio(String direccionDomicilio) {
		this.direccionDomicilio = direccionDomicilio;
	}

	public String getEmail() {
		return email;
	}

	@XmlElement
	public void setEmail(String email) {
		this.email = email;
	}

	public TipoPersonaEnum getTipoPersona() {
		return tipoPersona;
	}

	@XmlTransient
	public void setTipoPersona(TipoPersonaEnum tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public int getCodTipPersona() {
		return tipoPersona.ordinal();
	}

	@XmlElement
	public void setCodTipPersona(int codTipPers) {
		for (TipoPersonaEnum tp : TipoPersonaEnum.values()) {
			if (tp.ordinal() == codTipPers) {
				tipoPersona = tp;
				break;
			}
		}
	}

	public String getTelefonoConvencional() {
		return telefonoConvencional;
	}

	@XmlElement
	public void setTelefonoConvencional(String telefonoConvencional) {
		this.telefonoConvencional = telefonoConvencional;
	}

	public String getTelefonoMovil() {
		return telefonoMovil;
	}

	@XmlElement
	public void setTelefonoMovil(String telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
	}

	public List<UsuarioEt> getUsuarios() {
		return usuarios;
	}

	@XmlTransient
	public void setUsuarios(List<UsuarioEt> usuarios) {
		this.usuarios = usuarios;
	}

	public UbicacionEt getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(UbicacionEt ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getExtTelefonoConvencional() {
		return extTelefonoConvencional;
	}

	public void setExtTelefonoConvencional(String extTelefonoConvencional) {
		this.extTelefonoConvencional = extTelefonoConvencional;
	}

	public TipoIdentificacionEnum getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public EstadoCivilEnum getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivilEnum estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public Integer returnEdad() {
		edad = edad(fechaNacimiento);
		return edad;
	}

	public ParametrosGeneralesEt getParTipoIdentificacion() {
		return parTipoIdentificacion;
	}

	public void setParTipoIdentificacion(ParametrosGeneralesEt parTipoIdentificacion) {
		this.parTipoIdentificacion = parTipoIdentificacion;
	}

	public String formatFechaNaciento() {
		String fecha;
		if (this.getFechaNacimiento() != null) {
			fecha = new SimpleDateFormat("yyyy-MM-dd").format(getFechaNacimiento());
			return fecha + " ( " + calcularEdad(fecha) + " )";
		}
		return null;
	}

	private Integer calcularEdad(String fecha) {
		Date fechaNac = null;
		try {

			fechaNac = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
		} catch (Exception ex) {
			System.out.println("Error:" + ex);
		}
		Calendar fechaNacimiento = Calendar.getInstance();
		Calendar fechaActual = Calendar.getInstance();
		fechaNacimiento.setTime(fechaNac);
		int anio = fechaActual.get(Calendar.YEAR) - fechaNacimiento.get(Calendar.YEAR);
		int mes = fechaActual.get(Calendar.MONTH) - fechaNacimiento.get(Calendar.MONTH);
		int dia = fechaActual.get(Calendar.DATE) - fechaNacimiento.get(Calendar.DATE);
		// Se ajusta el a�o dependiendo el mes y el d�a
		if (mes < 0 || (mes == 0 && dia < 0)) {
			anio--;
		}
		return anio;
	}

	public Integer edad(Date fecha_naci) {
		if (fecha_naci != null) {
			DateFormat fechaNacimiento = new SimpleDateFormat("dd/MM/yyyy");
			String fecha_nac = fechaNacimiento.format(fecha_naci);
			Date fechaActual = new Date();

			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			String hoy = formato.format(fechaActual);
			String[] dat1 = fecha_nac.split("/");
			String[] dat2 = hoy.split("/");
			int anos = Integer.parseInt(dat2[2]) - Integer.parseInt(dat1[2]);
			int mes = Integer.parseInt(dat2[1]) - Integer.parseInt(dat1[1]);
			if (mes < 0) {
				anos = anos - 1;
			} else if (mes == 0) {
				int dia = Integer.parseInt(dat2[0]) - Integer.parseInt(dat1[0]);
				if (dia > 0) {
					anos = anos - 1;
				}
			}
			return anos;
		} else
			return null;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public String getEmailAlterno() {
		return emailAlterno;
	}

	public void setEmailAlterno(String emailAlterno) {
		this.emailAlterno = emailAlterno;
	}

	public String getTelefonoConvencionalAlterno() {
		return telefonoConvencionalAlterno;
	}

	public void setTelefonoConvencionalAlterno(String telefonoConvencionalAlterno) {
		this.telefonoConvencionalAlterno = telefonoConvencionalAlterno;
	}

	public List<PersonaContactoEt> getPersonaContacto() {
		return personaContacto;
	}

	public void setPersonaContacto(List<PersonaContactoEt> personaContacto) {
		this.personaContacto = personaContacto;
	}

	public String getEmailAlterno1() {
		return emailAlterno1;
	}

	public void setEmailAlterno1(String emailAlterno1) {
		this.emailAlterno1 = emailAlterno1;
	}

	public String getTelefonoConvencionalAlterno1() {
		return telefonoConvencionalAlterno1;
	}

	public void setTelefonoConvencionalAlterno1(String telefonoConvencionalAlterno1) {
		this.telefonoConvencionalAlterno1 = telefonoConvencionalAlterno1;
	}

	public String getTelefonoMovilAlterno() {
		return telefonoMovilAlterno;
	}

	public void setTelefonoMovilAlterno(String telefonoMovilAlterno) {
		this.telefonoMovilAlterno = telefonoMovilAlterno;
	}

	public String getTelefonoMovilAlterno1() {
		return telefonoMovilAlterno1;
	}

	public void setTelefonoMovilAlterno1(String telefonoMovilAlterno1) {
		this.telefonoMovilAlterno1 = telefonoMovilAlterno1;
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PersonaEt) {
			PersonaEt other = (PersonaEt) obj;
			if (this.idPersona == null)
				return this == other;
			return this.idPersona.equals(other.idPersona);
		} else
			return false;
	}

}
