package com.github.hippoom.ramblings.jobs;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;

public class DynamicTriggerUnitTests {
	@Rule
	public JUnitRuleMockery context = new JUnitRuleMockery();

	@Mock
	private Trigger delegate;
	@Mock
	private TriggerContext triggerContext;

	private DynamicTrigger trigger;

	@Before
	public void fixture() {
		trigger = new DynamicTrigger(delegate);
	}

	@Test
	public void returnsDelegatesNextExecutionTime() throws Throwable {

		final Date expected = new Date();

		context.checking(new Expectations() {
			{
				allowing(delegate).nextExecutionTime(triggerContext);
				will(returnValue(expected));
			}
		});

		final Date nextExecutionTime = trigger
				.nextExecutionTime(triggerContext);

		assertThat(nextExecutionTime, sameInstance(expected));
	}

	@Test
	public void returnsNullIfTriggerIsDisabled() throws Throwable {

		trigger.disable();

		final Date nextExecutionTime = trigger
				.nextExecutionTime(triggerContext);

		assertThat(nextExecutionTime, nullValue());
	}

}
