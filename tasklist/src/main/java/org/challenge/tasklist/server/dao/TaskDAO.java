package org.challenge.tasklist.server.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import org.challenge.tasklist.model.enumeration.StatusEnum;
import org.challenge.tasklist.model.jpa.Task;
import org.challenge.tasklist.server.rest.exception.TaskNotFoundException;
import org.slf4j.Logger;

public class TaskDAO {

	private EntityManager em;
	private Logger logger;

	private static final String FIND_ALL_TASK_FOR_USER = "Task.findAllTasksForSpecificUser";
	private static final String RETRIEVE_DETAILS = "Task.retrieveDetails";
	
	protected TaskDAO() {}
	
	@Inject
	public TaskDAO(EntityManager em, Logger logger) {
		this.em = em;
		this.logger = logger;
	}
	
	public List<Task> allOpenTaskForSpecificUser(Long userId) {
		
		TypedQuery<Task> query = em.createNamedQuery(FIND_ALL_TASK_FOR_USER, Task.class);
		
		query.setParameter("userId", userId);
		query.setParameter("status", StatusEnum.OPEN);
		
		return query.getResultList();
	}
	
	public Task retrieveDetails(Long uniqueId) throws Exception {
		TypedQuery<Task> query = em.createNamedQuery(RETRIEVE_DETAILS, Task.class);
		
		query.setParameter("uniqueId", uniqueId);
		
		Task task = null;
		
		try {
			task = query.getSingleResult();
		} catch (NoResultException e) {
			logger.error("Task with uniqueId {} not founded", uniqueId, e);
		} catch (NonUniqueResultException e) {
			logger.error("Exist more task with uniqueId {}", uniqueId);
			throw new Exception("Exist more task with uniqueId " + uniqueId);
		}
		
		return task;
	}
	
	public void closeTask(Long uniqueId) throws Exception {
		Task task = em.find(Task.class, uniqueId);
		
		if(task == null) {
			throw new TaskNotFoundException("Task with uniqueId "+uniqueId+" not founded");
		}
		
		task.setStatus(StatusEnum.CLOSE);
		
		em.merge(task);
		logger.info("Task {} closed", uniqueId);
	}
	
	public Task createNewTask(Task task) {
		em.persist(task);
		logger.info("Task {} created", task.getUniqueId());
		return task;
	}
}
