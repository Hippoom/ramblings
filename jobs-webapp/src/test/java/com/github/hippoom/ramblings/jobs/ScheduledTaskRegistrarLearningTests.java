package com.github.hippoom.ramblings.jobs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:root.xml")
public class ScheduledTaskRegistrarLearningTests {

	@Test
	public void inspectTaskScheduling() throws Throwable {
		System.err.println(Thread.currentThread().getName() + " Fall asleep");

		Thread.sleep(10000);// sleep 10 seconds

		System.err.println(Thread.currentThread().getName() + " Wake up");
	}
}
