package com.primax.srv.idao;

import java.util.Date;
import java.util.List;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.gen.PersonaEt;
import com.primax.jpa.param.ProvinciaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IUsuarioDao extends IGenericDao<UsuarioEt, Long> {

	public List<UsuarioEt> getUsuarioAuditorList();

	public UsuarioEt getUsuarioPorEmailNombre(String user);

	public UsuarioEt getUsuarioByIdPersona(PersonaEt persona);

	public void guardar(UsuarioEt usuario) throws EntidadNoGrabadaException;

	public UsuarioEt getUsuarioById(String nombre);

	public List<UsuarioEt> getUsuariosByCondicion(String condicion);

	public void crearUsuarioSistema(UsuarioEt usuario) throws EntidadNoGrabadaException;

	public UsuarioEt comprobarExisteUsuario(String nombre);

	public List<UsuarioEt> getUsuariosbyProvincia(ProvinciaEt provincia, String nombre);

	public UsuarioEt getUsuarioId(long id);

	public void remove();

	public List<UsuarioEt> returnUltimoAcceso(Date fecha);

	public UsuarioEt getUsuarioByNombreAll(String nombre);

	public void guardarNuevaContrasena(UsuarioEt usuario) throws Exception;

	public String getUsuarioActividadMensual(UsuarioEt usuario);

	public List<UsuarioEt> getUsuariosOrdenados();

	public UsuarioEt validarUsuario(String user, String pass);

	public boolean existeUsuarioCodigo(Long codigo);

}
