package com.github.hippoom.ramblings.airswitch.command;

import org.axonframework.serializer.xml.XStreamSerializer;
import org.springframework.beans.factory.config.AbstractFactoryBean;

import com.github.hippoom.ramblings.airswitch.command.ticket.AirTicketCreatedEvent;

public class XStreamSerializerFactoryBean extends
		AbstractFactoryBean<XStreamSerializer> {

	@Override
	protected XStreamSerializer createInstance() throws Exception {
		XStreamSerializer object = new XStreamSerializer();
		object.getXStream().aliasPackage("ticket", "com.github.hippoom.ramblings.airswitch.command.ticket");
		object.getXStream().aliasPackage("reservation", "com.github.hippoom.ramblings.airswitch.command.reservation");
		//object.getXStream().processAnnotations(AirTicketCreatedEvent.class);
		return object;
	}

	@Override
	public Class<?> getObjectType() {
		return XStreamSerializer.class;
	}

}
