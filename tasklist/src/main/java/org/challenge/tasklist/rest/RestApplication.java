package org.challenge.tasklist.rest;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

/**
 * Initializes the JAX-RS
 */
public class RestApplication extends Application {
	
	private final Set<Class<?>> classes;
	
	public RestApplication(){
		final Set<Class<?>> s = new HashSet<Class<?>>();
        classes = Collections.unmodifiableSet(s);
        
        
  	}
  	
  	@Override
  	public Set<Class<?>> getClasses() {
  		return classes;
  	}

}
