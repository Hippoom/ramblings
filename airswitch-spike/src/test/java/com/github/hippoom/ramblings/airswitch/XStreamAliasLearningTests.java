package com.github.hippoom.ramblings.airswitch;

import org.junit.Test;

import com.github.hippoom.ramblings.airswitch.command.ticket.AirTicket.Status;
import com.github.hippoom.ramblings.airswitch.command.ticket.AirTicketCreatedEvent;
import com.thoughtworks.xstream.XStream;

public class XStreamAliasLearningTests {

	@Test
	public void rune() {
		XStream stream = new XStream();
		stream.processAnnotations(AirTicketCreatedEvent.class);
		AirTicketCreatedEvent tktCreated = new AirTicketCreatedEvent(1L, 2L,
				Status.NEW, 15);
		System.out.println(stream.toXML(tktCreated));
	}

}
