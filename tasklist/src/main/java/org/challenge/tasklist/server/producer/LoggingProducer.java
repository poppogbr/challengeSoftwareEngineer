package org.challenge.tasklist.server.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

public class LoggingProducer {
	
	@Produces
	private Logger createLogger( InjectionPoint injectionPoint ) {
		return LoggerFactory.getLogger( injectionPoint.getMember().getDeclaringClass().getName());
	}

}
