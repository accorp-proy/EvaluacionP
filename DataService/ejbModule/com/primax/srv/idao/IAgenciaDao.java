package com.primax.srv.idao;

import java.util.List;

import com.primax.enm.pcs.LugarEnum;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.CantonEt;
import com.primax.jpa.param.CategoriaEstacionEt;
import com.primax.jpa.param.ParametrosGeneralesEt;
import com.primax.jpa.param.ProvinciaEt;
import com.primax.jpa.param.TipoEstacionEt;
import com.primax.jpa.param.ZonaEt;
import com.primax.jpa.pla.CheckListEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IAgenciaDao extends IGenericDao<AgenciaEt, Long> {

	public void remove();

	public AgenciaEt getAgenciaById(long id);

	public AgenciaEt getAgenciaCodigo(String codigo);

	public List<AgenciaEt> getAgenciasByZona(ZonaEt zona);

	public List<AgenciaEt> getAgencias(EstadoEnum estado);

	public List<AgenciaEt> getAgenciasCondicion(String txt);

	public List<AgenciaEt> getAgenciasCanton(CantonEt canton);

	public List<AgenciaEt> getAgenciasCodigos(List<String> codigos);

	public List<AgenciaEt> getAgenciasProvincia(ProvinciaEt provincia);

	public AgenciaEt getAgeciaCodigoLugar(Long codigo, LugarEnum lugar);

	public List<AgenciaEt> getAgenciaByCheckList(CheckListEt checkList);

	public List<AgenciaEt> getAgenciasPeores(ParametrosGeneralesEt zona);

	public List<AgenciaEt> getAgenciasMejores(ParametrosGeneralesEt zona);

	public List<AgenciaEt> getAgenciasPorZonas(ParametrosGeneralesEt zona);

	public List<AgenciaEt> getAgenciaAccesoZona(UsuarioEt usuario, ZonaEt zona);

	public List<AgenciaEt> getAgenciasPorZonas(List<ParametrosGeneralesEt> zonas);

	public List<AgenciaEt> getAgenciasPorZonas(ZonaEt zona, TipoEstacionEt tipoEstacion);

	public void guardarAgencia(AgenciaEt agencia, UsuarioEt usuario) throws EntidadNoGrabadaException;

	public List<AgenciaEt> getAgenciasSinAsignacionCanton(CantonEt ciudad, List<ParametrosGeneralesEt> zonas);

	public List<AgenciaEt> getAgenciaByTipoList(TipoEstacionEt tipoEstacion, CategoriaEstacionEt categoriaEstacion);

	public List<AgenciaEt> getAgenciaByTipoAList(TipoEstacionEt tipoEstacion, CategoriaEstacionEt categoriaEstacion, List<AgenciaEt> agencias);

}
