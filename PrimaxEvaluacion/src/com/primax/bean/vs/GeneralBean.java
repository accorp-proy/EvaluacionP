package com.primax.bean.vs;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.jpa.param.ZonaEt;
import com.primax.srv.idao.IParametrolGeneralDao;
import com.primax.srv.idao.IZonaDao;

@Named("generalBn")
@ViewScoped
public class GeneralBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = -605684918431664639L;

	@EJB
	private IZonaDao iZonaDao;
	@EJB
	private IParametrolGeneralDao iparametroGeneral;

	@Inject
	private AppMain appMain;

	private List<ZonaEt> zonas;

	public void init() {

	}

	public List<ZonaEt> getZonas() {
		try {
			zonas = iZonaDao.getZonaList(null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getZonas " + " " + e.getMessage());
		}
		return zonas;
	}

	public AppMain getAppMain() {
		return appMain;
	}

	public void setAppMain(AppMain appMain) {
		this.appMain = appMain;
	}

	public void setZonas(List<ZonaEt> zonas) {
		this.zonas = zonas;
	}

	@Override
	public void onDestroy() {
		iZonaDao.remove();
		iparametroGeneral.remove();
	}
}
