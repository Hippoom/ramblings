package com.github.hippoom.ramblings.jobs;

public class PrintTask implements Runnable {
	@Override
	public void run() {
		System.err.println("Hello world");
	}

}
