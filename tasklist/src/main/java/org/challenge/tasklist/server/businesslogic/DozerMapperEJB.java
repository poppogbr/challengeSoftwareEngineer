package org.challenge.tasklist.server.businesslogic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;

@Startup
@Singleton
public class DozerMapperEJB {

	public static final String EXCEPTION_READ_DOZER_FILES = "Error during read dozer configuration";
	
	private Logger logger;
	
	protected DozerMapperEJB() {}
	
	@Inject
	public DozerMapperEJB(Logger logger) {
		this.logger = logger;
	}
	
	private DozerBeanMapper mapper;

    public <B> List<B> mapStream2List(Stream<?> sourceStream,
            Class<B> destinationClass) {
        return sourceStream.map((a) -> mapper.map(a, destinationClass))
                .collect(Collectors.toList());
    }

    public <B> List<B> mapList(Collection<?> sourceList,
            Class<B> destinationClass) {
        ArrayList<B> res = null;
        if(sourceList != null) {
        	res = new ArrayList<>(sourceList.size());
        	mapList(sourceList, res, destinationClass);
        }
        return res;
    }

    public <B> void mapList(Collection<?> sourceList,
            Collection<B> destinationCollection,
            Class<B> destinationClass) {
    	if(sourceList != null) {
    		sourceList.stream().map((a) -> mapper.map(a, destinationClass))
                .collect(Collectors.toCollection(() -> destinationCollection));
    	}
    }
    
    public <B, A> B map(A source, Class<B> destinationClass) {
    	return (source == null) ? null : mapper.map(source, destinationClass);
    }
	
	@PostConstruct
	public void initializeMapper() {
		logger.debug("START - initializeMapper");

		mapper = new DozerBeanMapper();
		try (final BufferedReader reader = new BufferedReader(
				new InputStreamReader(getClass().getClassLoader().getResourceAsStream("dozer-mapping/mappers.txt"), "UTF-8"))) {
			String line = reader.readLine();
			while (line != null) {
				if (!line.isEmpty()) {
					mapper.addMapping(getClass().getClassLoader().getResourceAsStream("dozer-mapping/" + line));
				}
				line = reader.readLine();
			}
		} catch (IOException ex) {
			logger.warn("Error during read dozer configuration", ex);
			throw new IllegalStateException("Error during read dozer configuration");
		}
		logger.debug("END - initializeMapper");
	}
}
