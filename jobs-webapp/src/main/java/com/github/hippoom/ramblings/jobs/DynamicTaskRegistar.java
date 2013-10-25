package com.github.hippoom.ramblings.jobs;

import java.util.HashMap;
import java.util.Map;

import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.PeriodicTrigger;

public class DynamicTaskRegistar extends ScheduledTaskRegistrar {

	private final Map<String, DynamicTrigger> triggers = new HashMap<String, DynamicTrigger>();

	@Override
	public void afterPropertiesSet() {
		final DynamicTrigger trigger = new DynamicTrigger(new PeriodicTrigger(
				1000));
		super.addTriggerTask(new PrintTask(), trigger);

		this.triggers.put("1", trigger);
		super.afterPropertiesSet();
	}

	public void disable(String triggerId) {
		DynamicTrigger trigger = this.triggers.get(triggerId);
		if (trigger != null) {
			trigger.disable();
		}
	}
	
	/**
	 * does not work as the ScheduledFuture is null
	 * @param triggerId
	 */
	public void restart(String triggerId) {
		DynamicTrigger trigger = this.triggers.get(triggerId);
		if (trigger != null) {
			trigger.enable();
		}
	}
}
