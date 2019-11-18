package org.challenge.tasklist;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.challenge.tasklist.model.dto.TaskDTO;
import org.challenge.tasklist.model.dto.UserDTO;
import org.challenge.tasklist.model.jpa.Task;
import org.challenge.tasklist.model.jpa.User;
import org.challenge.tasklist.server.businesslogic.DozerMapperEJB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

class DozerMapperTest extends AbstractTest {

	private DozerMapperEJB dozerMapper;
	
	@BeforeEach
	public void setUp() {
		dozerMapper = new DozerMapperEJB(LoggerFactory.getLogger(DozerMapperEJB.class));
		
	}
	
	@Test
	void testDozerMapperNotNull() {
		assertNotNull(dozerMapper);
	}
	
	@Test
	void testInitializeMapper() {
		assertDoesNotThrow(dozerMapper::initializeMapper);
	}

	@Test
	void testMapUser() {
		dozerMapper.initializeMapper();
		
		User user = createUser();
		UserDTO userDTO = dozerMapper.map(user, UserDTO.class);
		
		assertEquals(user.getFirstName(), userDTO.getFirstName());
		assertEquals(user.getLastName(), userDTO.getLastName());
		assertEquals(user.getUserId(), userDTO.getUserId());
		assertNull(user.getTasks());
	}
	
	@Test
	void testMapTask() {
		dozerMapper.initializeMapper();
		
		Task task = createTask();
		TaskDTO taskDTO = dozerMapper.map(task, TaskDTO.class);
		
		assertEquals(task.getName(), taskDTO.getName());
		assertEquals(task.getDetailDescription(), taskDTO.getDescription());
		assertEquals(task.getStatus(), taskDTO.getStatus());
		assertNull(task.getUsers());
	}
	
	@Test
	void testMapTaskWithUser() {
		dozerMapper.initializeMapper();
		
		Task task = createTask();
		
		List<User> users = new ArrayList<>();
		users.add(createUser());
		task.setUsers(users);
		TaskDTO taskDTO = dozerMapper.map(task, TaskDTO.class);
		
		assertNotNull(taskDTO.getUsersEnabled());
		assertEquals(taskDTO.getUsersEnabled().size(), 1);
	}
	
	@Test
	void testMapUserNull() {
		dozerMapper.initializeMapper();
		UserDTO userDTO = dozerMapper.map(null, UserDTO.class);
		assertNull(userDTO);
	}
}
