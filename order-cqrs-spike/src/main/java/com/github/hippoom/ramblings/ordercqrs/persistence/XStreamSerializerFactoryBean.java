package com.github.hippoom.ramblings.ordercqrs.persistence;

import org.axonframework.serializer.xml.XStreamSerializer;
import org.springframework.beans.factory.config.AbstractFactoryBean;

public class XStreamSerializerFactoryBean extends
		AbstractFactoryBean<XStreamSerializer> {

	@Override
	protected XStreamSerializer createInstance() throws Exception {
		XStreamSerializer object = new XStreamSerializer();
		object.getXStream().aliasPackage("order",
				"com.github.hippoom.ramblings.ordercqrs.event");
		return object;
	}

	@Override
	public Class<?> getObjectType() {
		return XStreamSerializer.class;
	}

}
