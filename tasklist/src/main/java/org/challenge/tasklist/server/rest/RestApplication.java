package org.challenge.tasklist.server.rest;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.challenge.tasklist.server.rest.exception.GenericExceptionMapper;

public class RestApplication extends Application {
	
	private final Set<Class<?>> classes;
	
	public RestApplication(){
		final Set<Class<?>> s = new HashSet<Class<?>>();
        classes = Collections.unmodifiableSet(s);
        
        s.add(TaskService.class);
        
        s.add(GenericExceptionMapper.class);
  	}
  	
  	@Override
  	public Set<Class<?>> getClasses() {
  		return classes;
  	}

}
