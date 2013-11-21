package com.github.hippoom.ramblings.jobs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:root.xml")
public class ScheduledTaskRegistrarLearningTests {

	@Autowired
	private DynamicTaskRegistar registar;

	@Test
	public void inspectTaskScheduling() throws Throwable {
		System.err.println(Thread.currentThread().getName() + " Fall asleep");

		Thread.sleep(5000);// sleep 5 seconds
		registar.disable("1");
		System.err.println(Thread.currentThread().getName() + " Disable task");
		Thread.sleep(3000);// sleep 3 seconds
		registar.restart("1");
		System.err.println(Thread.currentThread().getName() + " Restart task");
		Thread.sleep(5000);// sleep 5 seconds
		
		System.err.println(Thread.currentThread().getName() + " Wake up");
	}
}
