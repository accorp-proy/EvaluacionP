package com.primax.srv.idao;

import java.util.List;

import com.primax.jpa.param.ProvinciaEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IProvinciaDao extends IGenericDao<ProvinciaEt, Long> {

	public void remove();

	public void clear();

	public ProvinciaEt getProvincia(long id);

	public String limpiarReporte(Long idUsuario);

	public ProvinciaEt getInecProvincia(String cod);

	public ProvinciaEt getProvincia(String nombre);

	public List<ProvinciaEt> getProvinciasAsociadasAgencia();

	public List<ProvinciaEt> getProvinciasByCondicion(String condicion);

}
