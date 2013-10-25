package com.github.hippoom.ramblings.jobs;

import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.PeriodicTrigger;

public class DynamicTaskRegistar extends ScheduledTaskRegistrar {

	@Override
	public void afterPropertiesSet() {
		super.addTriggerTask(new PrintTask(), new PeriodicTrigger(1000));
		super.afterPropertiesSet();
	}
}
