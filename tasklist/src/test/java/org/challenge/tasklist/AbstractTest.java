package org.challenge.tasklist;

import org.challenge.tasklist.model.dto.TaskDTO;
import org.challenge.tasklist.model.dto.UserDTO;
import org.challenge.tasklist.model.enumeration.StatusEnum;
import org.challenge.tasklist.model.jpa.Task;
import org.challenge.tasklist.model.jpa.User;

public abstract class AbstractTest {
	
	protected User createUser() {
		User user = new User();
		user.setUserId(1L);
		user.setFirstName("firstName");
		user.setLastName("lastName");
		return user;
	}
	
	protected UserDTO createUserDTO() {
		UserDTO user = new UserDTO();
		user.setUserId(1L);
		user.setFirstName("firstName");
		user.setLastName("lastName");
		return user;
	}
	
	protected Task createTask() {
		Task task = new Task();
		task.setUniqueId(1L);
		task.setName("taskName");
		task.setDetailDescription("detailDescription");
		task.setStatus(StatusEnum.OPEN);
		return task;
	}
	
	protected TaskDTO createTaskDTO() {
		TaskDTO task = new TaskDTO();
		task.setUniqueId(1L);
		task.setName("taskName");
		task.setDescription("detailDescription");
		task.setStatus(StatusEnum.OPEN);
		return task;
	}
}
