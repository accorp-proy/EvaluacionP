package com.primax.ejb.lkp;

public enum EnumNaming {

	IRolDao("RolEtDao"),
	IZonaDao("ZonaDao"),
	IDbHandler("DbHandler"),
	ICantonDao("CantonDao"),
	IRegionDao("RegionDao"),
	IMenuEtDao("MenuEtDao"),
	
	IAgenciaDao("AgenciaDao"),
	IMensajeDao("MensajeDao"),
	IOficinaDao("OficinaDao"),
	IEmpresaDao("EmpresaDao"),
	IUsuarioDao("UsuarioDao"),
	IPersonaDao("PersonaDao"),
	IClienteDao("ClienteDao"),
	IProcesoDao("ProcesoDao"),
	IServidorDao("ServidorDao"),
	ITipoCargoDao("TipoCargoDao"),
	IProvinciaDao("ProvinciaDao"),
	INivelColorDao("NivelColorDao"),
	IKPICriticoDao("KPICriticoDao"),
	IClienteJdeDao("ClienteJdeDao"),
	IRolUsuarioDao("RolUsuarioDao"),
	IEvaluacionDao("EvaluacionDao"),
	IResponsableDao("ResponsableDao"),
	IGeneralUtilsDao("GeneralUtilsDao"),
	ITipoEstacionDao("TipoEstacionDao"),
	ITipoChecListDao("TipoChecKListDao"),
	IParametroDao("ParametroGeneralDao"),
	IRolMenuAccesoDao("RolMenuAccesoDao"),
	INivelEsfuerzoDao("NivelEsfuerzoDao"),
	ITipoInventarioDao("TipoInventarioDao"),
	IProcesoDetalleDao("ProcesoDetalleDao"),
	INotificacionService("SrvNotificacion"),
	INivelEvaluacionDao("NivelEvaluacionDao"),
	IFrecuenciaVisitaDao("FrecuenciaVisitaDao"),
	ICheckListProcesoDao("CheckListProcesoDao"),
	IDimensionDetalleDao("DimensionDetalleDao"),
	ICategoriaEstacionDao("CategoriaEstacionDao"),
	IPoliticaSeguridadDao("PoliticaSeguridadDao"),
	ICriterioEvaluacionDao("CriterioEvaluacionDao"),
	ICheckListEjecucionDao("CheckListEjecucionDao"),
	ISeguimientoPlanAccionDao("SeguimientoPlanAccionDao"),
	ICheckListKpiEjecucionDao("CheckListKpiEjecucionDao"),
	INivelEvaluacionDetalleDao("NivelEvaluacionDetalleDao"),
	ICheckListEjecucionFirmaDao("CheckListEjecucionFirmaDao"),
	ICheckListEjecucionAdjuntoDao("CheckListEjecucionAdjuntoDao"),
	ICriterioEvaluacionDetalleDao("CriterioEvaluacionDetalleDao"),
	ICheckListProcesoEjecucionDao("CheckListProcesoEjecucionDao");

	private String naming;
	private String ruta = "java:global/PrimaxEvaluacionEAR/DataService/";

	EnumNaming(String name) {
		this.naming = ruta + name;
	}

	public String getNaming() {
		return naming;
	}

}
