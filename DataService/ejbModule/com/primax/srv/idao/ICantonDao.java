package com.primax.srv.idao;

import java.util.List;

import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.CantonEt;
import com.primax.jpa.param.ProvinciaEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ICantonDao extends IGenericDao<CantonEt, Long> {

	public String limpiarReporte(Long idUsuario);

	public List<CantonEt> getCantones(EstadoEnum estado);

	public List<CantonEt> getCantonEtByCondicion(String condicion);

	public List<ProvinciaEt> getRetornaProvincia(String condicion);

	public List<CantonEt> getRetornaCanton(String condicon);

	public CantonEt getCantonbyId(long id);

	public CantonEt getCanton(String nombre);

	public CantonEt getInecCanton(Long codProv, String codCanton);

	public List<CantonEt> getCantonesAsociadosAgencia(ProvinciaEt provincia);

	public CantonEt getNombreCanton(String condicion);

	public void remove();
}
