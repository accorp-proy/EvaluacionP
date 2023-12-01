package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.RegionEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IRegionDao extends IGenericDao<RegionEt, Long> {

	public void remove();

	public RegionEt getRegionById(long id);

	public List<RegionEt> getRegiones(EstadoEnum estado);

	public List<RegionEt> getRegionesCondicion(String condicion);

	public void guardar(RegionEt region) throws EntidadNoGrabadaException;

}
