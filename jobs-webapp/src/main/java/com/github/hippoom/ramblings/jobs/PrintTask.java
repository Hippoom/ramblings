package com.github.hippoom.ramblings.jobs;

public class PrintTask implements Runnable {
	@Override
	public void run() {
		String threadName = Thread.currentThread().getName();

		if (threadName.indexOf("-1") > 0) {
			throw new IllegalArgumentException(threadName + " Fail");
		} else {
			System.err.println(threadName + " Hello world");
		}
	}

}
