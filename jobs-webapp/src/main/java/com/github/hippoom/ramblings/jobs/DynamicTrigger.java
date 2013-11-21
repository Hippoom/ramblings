package com.github.hippoom.ramblings.jobs;

import java.util.Date;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;

public class DynamicTrigger implements Trigger {

	private final Trigger delegate;

	private boolean enabled = true;;

	public DynamicTrigger(Trigger delegate) {
		this.delegate = delegate;
		enable();
	}

	@Override
	public Date nextExecutionTime(TriggerContext triggerContext) {
		if (enabled) {
			return delegate.nextExecutionTime(triggerContext);
		} else {
			return null;
		}
	}

	public void disable() {
		this.enabled = false;
	}

	public void enable() {
		this.enabled = true;
	}

}
