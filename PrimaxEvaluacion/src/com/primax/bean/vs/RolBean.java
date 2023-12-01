package com.primax.bean.vs;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.jpa.sec.RolEt;
import com.primax.srv.idao.IRolEtDao;

@Named("RolBn")
@ViewScoped
public class RolBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = -7267873838799765165L;

	@EJB
	private IRolEtDao iRolDao;

	private List<RolEt> roles;

	@Inject
	private AppMain appMain;

	@Override
	public void init() {
		roles = iRolDao.obtenerTodos();
	}

	@Override
	protected void onDestroy() {
		iRolDao.remove();
	}

	public List<RolEt> getRoles() {
		return roles;
	}

	public void setRoles(List<RolEt> roles) {
		this.roles = roles;
	}

	public AppMain getAppMain() {
		return appMain;
	}

	public void setAppMain(AppMain appMain) {
		this.appMain = appMain;
	}

}
