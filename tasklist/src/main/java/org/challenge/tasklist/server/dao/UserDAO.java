package org.challenge.tasklist.server.dao;


import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.challenge.tasklist.model.jpa.User;
import org.slf4j.Logger;

public class UserDAO {

	private EntityManager em;
	private Logger logger;

	protected UserDAO() {}
	
	@Inject
	public UserDAO(EntityManager em, Logger logger) {
		this.em = em;
		this.logger = logger;
	}
	
	public User findByUserId(Long userId) {
		return em.find(User.class, userId);
	}
}
