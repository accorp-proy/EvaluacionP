package com.primax.jpa.base;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.envers.Audited;

import com.primax.enm.ann.ListTarget;
import com.primax.enm.ann.PlainTarget;
import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.sec.UsuarioEt;

@MappedSuperclass
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@Audited
public abstract class EntityBase {

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true, name = "fecha_registro")
	protected Date fechaRegistro;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_modificacion")
	protected Date fechaModificacion;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, name = "estado", columnDefinition = "VARCHAR(3) default 'ACT'")
	protected EstadoEnum estado;

	@ManyToOne
	@JoinColumn(name = "aud_usuario")
	protected UsuarioEt usuarioRegistra;

	{
		if (estado == null) {
			estado = EstadoEnum.ACT;
		}

		if (fechaRegistro == null) {
			fechaRegistro = new Date();
		}
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	@XmlTransient
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	@XmlTransient
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public EstadoEnum getEstado() {
		return estado;
	}

	@XmlElement
	public void setEstado(EstadoEnum estado) {
		this.estado = estado;
	}

	public UsuarioEt getUsuarioRegistra() {
		return usuarioRegistra;
	}

	@XmlTransient
	public void setUsuarioRegistra(UsuarioEt usuarioRegistra) {
		this.usuarioRegistra = usuarioRegistra;
	}

	protected <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		fillPlain(this, user, act);
		Class<?> clazz = this.getClass();
		try {
			for (Field fld : clazz.getDeclaredFields()) {
				if (fld.isAnnotationPresent(PlainTarget.class)) {
					Annotation annotation = fld.getAnnotation(PlainTarget.class);
					PlainTarget target = (PlainTarget) annotation;
					if (target.audited()) {
						fld.setAccessible(true);
						Object ob_0 = fld.get(this);
						if (ob_0 != null)
							audit(ob_0, user, act);
					}
				}
				if (fld.isAnnotationPresent(ListTarget.class)) {
					Annotation annotation = fld.getAnnotation(ListTarget.class);
					ListTarget target = (ListTarget) annotation;
					if (target.audited()) {
						fld.setAccessible(true);
						List<?> ob_0 = (List<?>) fld.get(this);
						if (ob_0 != null)
							fillCollection(ob_0, user, act);
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private <T> void audit(Object obj, UsuarioEt user, ActionAuditedEnum act) {
		fillPlain(this, user, act);
		Class<?> clazz = obj.getClass();
		try {
			for (Field fld : clazz.getDeclaredFields()) {
				if (fld.isAnnotationPresent(PlainTarget.class)) {
					Annotation annotation = fld.getAnnotation(PlainTarget.class);
					PlainTarget target = (PlainTarget) annotation;
					if (target.audited()) {
						fld.setAccessible(true);
						Object ob_0 = fld.get(obj);
						if (ob_0 != null)
							fillPlain(ob_0, user, act);
					}
				}

				if (fld.isAnnotationPresent(ListTarget.class)) {
					Annotation annotation = fld.getAnnotation(ListTarget.class);
					ListTarget target = (ListTarget) annotation;
					if (target.audited()) {
						fld.setAccessible(true);
						List<?> ob_0 = (List<?>) fld.get(obj);
						if (ob_0 != null)
							fillCollection(ob_0, user, act);
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private void fillCollection(List<?> l_value, UsuarioEt usuario, ActionAuditedEnum action) {
		for (int i = 0; i < l_value.size(); i++) {
			fillPlain(l_value.get(i), usuario, action);
			audit(l_value.get(i), usuario, action);
		}
	}

	private void fillPlain(Object value, UsuarioEt usuario, ActionAuditedEnum action) {
		Class<?> c = value.getClass();
		Field field;

		try {

			field = c.getSuperclass().getDeclaredField("fechaRegistro");
			field.setAccessible(true);

			if (action.equals(ActionAuditedEnum.NEW))
				field.set(value, new Date());

			field = c.getSuperclass().getDeclaredField("fechaModificacion");
			field.setAccessible(true);

			if (action.equals(ActionAuditedEnum.UPD))
				field.set(value, new Date());

			field = c.getSuperclass().getDeclaredField("usuarioRegistra");
			field.setAccessible(true);
			field.set(value, usuario);

		} catch (Exception e) {
			System.err.print("** SIN ATRIBUTO IDENTIFICADO PARA AUDITAR **");
			return;
		}
	}

}
