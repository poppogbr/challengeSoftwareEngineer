package org.challenge.tasklist.server.businesslogic;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.challenge.tasklist.model.dto.TaskDTO;
import org.challenge.tasklist.model.jpa.Task;
import org.challenge.tasklist.model.jpa.User;
import org.challenge.tasklist.server.dao.TaskDAO;
import org.challenge.tasklist.server.rest.exception.FieldRequiredException;
import org.slf4j.Logger;

@Stateless
public class TaskEJB {

	private Logger logger;
	private UserEJB userEJB;
	private TaskDAO taskDAO;
	private DozerMapperEJB dozerMapper;
	
	protected TaskEJB() {}
	
	@Inject
	public TaskEJB(Logger logger, TaskDAO taskDAO, DozerMapperEJB dozerMapper, UserEJB userEJB) {
		this.logger = logger;
		this.taskDAO = taskDAO;
		this.dozerMapper = dozerMapper;
		this.userEJB = userEJB;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<TaskDTO> allOpenTaskForSpecificUser(Long userId) throws Exception {
		if(userId == null) {
			logger.error("userId must be not null");
			throw new FieldRequiredException("userId must be not null");
		}
		List<Task> opentasks = taskDAO.allOpenTaskForSpecificUser(userId);
		
		return dozerMapper.mapList(opentasks, TaskDTO.class);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public TaskDTO retrieveDetails(Long uniqueId) throws Exception {
		if(uniqueId == null) {
			logger.error("uniqueId must be not null");
			throw new FieldRequiredException("uniqueId must be not null");
		}
		Task task = taskDAO.retrieveDetails(uniqueId);
		
		return dozerMapper.map(task, TaskDTO.class);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void closeTask(Long uniqueId) throws Exception {
		if(uniqueId == null) {
			logger.error("uniqueId must be not null");
			throw new FieldRequiredException("uniqueId must be not null");
		}
		taskDAO.closeTask(uniqueId);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public TaskDTO createNewTask(TaskDTO taskDTO) throws FieldRequiredException {
		if(taskDTO == null) {
			logger.error("task must be not null");
			throw new FieldRequiredException("task must be not null");
		}
		if(StringUtils.isBlank(taskDTO.getName())) {
			logger.error("task name must be not null");
			throw new FieldRequiredException("task name must be not null");
		}
		if(taskDTO.getStatus() == null) {
			logger.error("task status must be not null");
			throw new FieldRequiredException("task status must be not null");
		}
		userEJB.checkRequiredUserFields(taskDTO.getUsersEnabled());
		
		Task task = dozerMapper.map(taskDTO, Task.class);
		
		List<User> usersToUpdate = userEJB.usersEntityToUpdate(taskDTO.getUsersEnabled()); 
		
		task.setUsers(userEJB.usersToInsert(taskDTO.getUsersEnabled()));
		
		task.getUsers().addAll(usersToUpdate);
		
		taskDAO.createNewTask(task);
		return dozerMapper.map(task, TaskDTO.class);
	}
}
