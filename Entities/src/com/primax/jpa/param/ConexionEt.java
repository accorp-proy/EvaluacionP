package com.primax.jpa.param;


import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.primax.enm.gen.DBTypeLib;
import com.primax.enm.gen.DbTypeEnum;
import com.primax.jpa.base.EntityBase;


public class ConexionEt extends EntityBase {

	@Id
	@Column(name = "id_conexion")
	@GeneratedValue(generator = "sec_conexion", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "sec_conexion", sequenceName = "seq_conexion", allocationSize = 1, initialValue = 1)
	private Long idConexion;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_db")
	protected DbTypeEnum tipoDb;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_lib_db")
	protected DBTypeLib tipoLibDb;
	
	@Column(name = "ip")
	private String ip;

	@Column(name = "puerto")
	private String puerto;

	@Column(name = "nombre_base")
	private String nombreBase;		
	
	@Column(name = "usuario")
	private String usuario;
	
	@Column(name = "contrasenia")
	private String contrasenia;
		
	public Long getIdConexion() {
		return idConexion;
	}

	public void setIdConexion(Long idConexion) {
		this.idConexion = idConexion;
	}

	public DbTypeEnum getTipoDb() {
		return tipoDb;
	}

	public void setTipoDb(DbTypeEnum tipoDb) {
		this.tipoDb = tipoDb;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPuerto() {
		return puerto;
	}

	public void setPuerto(String puerto) {
		this.puerto = puerto;
	}	

	public String getNombreBase() {
		return nombreBase;
	}

	public void setNombreBase(String nombreBase) {
		this.nombreBase = nombreBase;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public DBTypeLib getTipoLibDb() {
		return tipoLibDb;
	}

	public void setTipoLibDb(DBTypeLib tipoLibDb) {
		this.tipoLibDb = tipoLibDb;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ConexionEt) {
			ConexionEt other = (ConexionEt) obj;
			if (this.idConexion == null)
				return this == other;
			return this.idConexion.equals(other.idConexion);
		}
		return false;
	}
}
