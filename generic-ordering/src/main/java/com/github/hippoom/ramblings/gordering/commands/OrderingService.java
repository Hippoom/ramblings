package com.github.hippoom.ramblings.gordering.commands;

public interface OrderingService {

	<R> R send(Object command);
}
