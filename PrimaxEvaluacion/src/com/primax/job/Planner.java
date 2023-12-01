package com.primax.job;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

@Singleton
@Startup
public class Planner {

	@PostConstruct
	public void init() {
		/**
		 * Actualización Estados CheckList
		 */
		JobDetail jobWorkerEstadoCheck = JobBuilder.newJob(WorkerEstadoCheck.class).withIdentity("JOB-ACL", "GRP-NIGHT")
				.build();
		Trigger triggerWorkerEstadoCheck = TriggerBuilder.newTrigger().withIdentity("TRG-ACL", "GRP-NIGHT")
				.withSchedule(CronScheduleBuilder.cronSchedule("0 10 0 1/1 * ? *")).build();

		// 0 0/1 * 1/1 * ? *
		// 0 5 0 1/1 * ? *

		/*
		 * indica el tiempo de suceso http://www.cronmaker.com/ 0 0/1 * 1/1 * ?
		 * 1 munuto 0 0 0/1 1/1 * ? * 1 hora
		 */
		Scheduler scheduler = null;
		try {
			scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.scheduleJob(jobWorkerEstadoCheck, triggerWorkerEstadoCheck);
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

	}

}
