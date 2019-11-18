package org.challenge.tasklist.server.rest;

import java.util.Properties;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;

@Path("installer")
public class InstallerRestService {

	@Inject
	private Logger log;
	
	@GET
	@Path("/schema")
	public Response createSchema(@QueryParam("token") String token) {

		try {

			Properties map = new Properties();
			map.put("eclipselink.ddl-generation", "create-tables");
			map.put("eclipselink.ddl-generation.output-mode", "database");
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("TASKLIST_PU", map);
			factory.createEntityManager();

			return Response.ok().build();

		} catch (Exception e) {
			log.error("Errore durante la prima installazione del Database", e);
			throw new ProcessingException("Errore durante la prima installazione del Database");
		}

	}

}
