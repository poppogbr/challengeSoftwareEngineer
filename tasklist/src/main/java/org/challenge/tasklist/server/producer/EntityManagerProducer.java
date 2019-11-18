package org.challenge.tasklist.server.producer;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EntityManagerProducer {
	
	@Produces
	@PersistenceContext(unitName = "TASKLIST_PU")
    private EntityManager em;

}