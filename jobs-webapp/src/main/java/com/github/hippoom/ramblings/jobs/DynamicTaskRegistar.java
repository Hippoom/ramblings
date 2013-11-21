package com.github.hippoom.ramblings.jobs;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.util.Assert;

public class DynamicTaskRegistar implements InitializingBean, DisposableBean {

	private final Map<String, ScheduledFuture<?>> scheduledFutures = new HashMap<String, ScheduledFuture<?>>();
	private TaskScheduler taskScheduler;

	private Map<String, TriggerTask> triggerTasks = new HashMap<String, TriggerTask>();

	/**
	 * Set the {@link TaskScheduler} to register scheduled tasks with.
	 */
	public void setTaskScheduler(TaskScheduler taskScheduler) {
		Assert.notNull(taskScheduler, "TaskScheduler must not be null");
		this.taskScheduler = taskScheduler;
	}

	/**
	 * Return the {@link TaskScheduler} instance for this registrar (may be
	 * {@code null}).
	 */
	public TaskScheduler getScheduler() {
		return this.taskScheduler;
	}

	/**
	 * Add a Runnable task to be triggered per the given {@link Trigger}.
	 * 
	 * @see TaskScheduler#scheduleAtFixedRate(Runnable, long)
	 */
	public void addTriggerTask(Runnable task, Trigger trigger) {
		this.addTriggerTask(new TriggerTask(task, trigger));
	}

	/**
	 * Add a {@code TriggerTask}.
	 * 
	 * @since 3.2
	 * @see TaskScheduler#scheduleAtFixedRate(Runnable, long)
	 */
	public void addTriggerTask(TriggerTask task) {
		this.triggerTasks.put("1", task);
	}

	/**
	 * Schedule all registered tasks against the underlying
	 * {@linkplain #setTaskScheduler(TaskScheduler) task scheduler}.
	 */
	protected void scheduleTasks() {
		if (this.triggerTasks != null) {
			for (Entry<String, TriggerTask> triggerTask : triggerTasks
					.entrySet()) {
				this.scheduledFutures.put(triggerTask.getKey(),
						this.taskScheduler.schedule(triggerTask.getValue()
								.getRunnable(), triggerTask.getValue()
								.getTrigger()));
			}
		}
	}

	@Override
	public void destroy() {
		for (Entry<String, ScheduledFuture<?>> future : this.scheduledFutures
				.entrySet()) {
			future.getValue().cancel(true);
		}
	}

	/**
	 * Calls {@link #scheduleTasks()} at bean construction time.
	 */
	@Override
	public void afterPropertiesSet() {
		final DynamicTrigger trigger = new DynamicTrigger(new PeriodicTrigger(
				1000));
		addTriggerTask(new PrintTask(), trigger);

		scheduleTasks();
	}

	public void disable(String triggerId) {
		ScheduledFuture<?> future = this.scheduledFutures.get(triggerId);
		if (future != null) {
			future.cancel(true);
		}
	}

	/**
	 * does not work as the ScheduledFuture is null
	 * 
	 * @param triggerId
	 */
	public void restart(String triggerId) {
		TriggerTask triggerTask = this.triggerTasks.get(triggerId);
		if (triggerTask != null) {
			this.scheduledFutures.put(triggerId, this.taskScheduler.schedule(
					triggerTask.getRunnable(), triggerTask.getTrigger()));
		}
	}
}
