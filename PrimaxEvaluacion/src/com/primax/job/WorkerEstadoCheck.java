package com.primax.job;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.enums.EstadoCheckListEnum;
import com.primax.jpa.pla.CheckListEjecucionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.ICheckListEjecucionDao;
import com.primax.srv.idao.IUsuarioDao;

public class WorkerEstadoCheck extends BaseJobs implements Job {

	private static final Logger log = Logger.getLogger(WorkerEstadoCheck.class.getName());

	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("Inicio JOB Actualización estado CheckList");
		synchronized (lockAuth) {
			IUsuarioDao iUsuarioDao = EJB(EnumNaming.IUsuarioDao);
			ICheckListEjecucionDao iCheckListDao = EJB(EnumNaming.ICheckListEjecucionDao);

			try {
				Date fechaA = new Date();
				UsuarioEt usuario = iUsuarioDao.getUsuarioId(1L);

				List<CheckListEjecucionEt> checkList = iCheckListDao
						.getCheckListCambioEstadoList(EstadoCheckListEnum.AGENDADA);

				for (CheckListEjecucionEt check : checkList) {
					if (check.getPlanificacion().getFechaPlanificacion().before(fechaA)) {
						check.setEstadoCheckList(EstadoCheckListEnum.NO_EJECUTADO);
						iCheckListDao.guardarCheckListEjecucion(check, usuario);
					}
				}
				List<CheckListEjecucionEt> checkListE = iCheckListDao
						.getCheckListCambioEstadoList(EstadoCheckListEnum.EN_EJECUCION);

				for (CheckListEjecucionEt check : checkListE) {
					if (check.getPlanificacion().getFechaPlanificacion().before(fechaA)) {
						check.setEstadoCheckList(EstadoCheckListEnum.INCONCLUSO);
						iCheckListDao.guardarCheckListEjecucion(check, usuario);
					}
				}

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage() + " ERROR EN AUTORIZACION  :WorkerEstadoCheck ");
			} finally {
				iUsuarioDao.remove();
				iCheckListDao.remove();
			}

		}
		System.out.println("Fin JOB Actualización estado CheckList");
	}
}
