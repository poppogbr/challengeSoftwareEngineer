package org.challenge.tasklist;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.challenge.tasklist.model.dto.TaskDTO;
import org.challenge.tasklist.model.jpa.Task;
import org.challenge.tasklist.server.businesslogic.DozerMapperEJB;
import org.challenge.tasklist.server.businesslogic.TaskEJB;
import org.challenge.tasklist.server.businesslogic.UserEJB;
import org.challenge.tasklist.server.dao.TaskDAO;
import org.challenge.tasklist.server.rest.exception.FieldRequiredException;
import org.challenge.tasklist.server.rest.exception.TaskNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;

class TaskEJBTest extends AbstractTest {

	private TaskEJB taskEJB;
	private TaskDAO taskDAO;
	private UserEJB userEJB;
	
	@BeforeEach
	void setUp() throws Exception {
		DozerMapperEJB dozerMapper = new DozerMapperEJB(LoggerFactory.getLogger(DozerMapperEJB.class));
		
		taskDAO = Mockito.mock(TaskDAO.class);
		userEJB = Mockito.mock(UserEJB.class);
		taskEJB = new TaskEJB(
				LoggerFactory.getLogger(DozerMapperEJB.class), taskDAO, dozerMapper, userEJB);
	}

	@Test
	void testAllOpenTaskForSpecificUserReturnNoRecord() {
		List<Task> tasksMock = new ArrayList<>();
		
		Mockito.when(taskDAO.allOpenTaskForSpecificUser(Mockito.anyLong())).thenReturn(tasksMock);
		
		try {
			List<TaskDTO> tasksDTO = taskEJB.allOpenTaskForSpecificUser(1L);
			assertEquals(tasksDTO.size(), 0);
		} catch (Exception e) {}
		
	}

	@Test
	void testAllOpenTaskForSpecificUserReturnMoreRecord() {
		List<Task> tasksMock = new ArrayList<>();
		tasksMock.add(createTask());
		
		Mockito.when(taskDAO.allOpenTaskForSpecificUser(Mockito.anyLong())).thenReturn(tasksMock);
		
		try {
			List<TaskDTO> tasksDTO = taskEJB.allOpenTaskForSpecificUser(1L);
			assertNotEquals(tasksDTO.size(), 0);
		} catch (Exception e) {}
	}
	
	@Test
	void testAllOpenTaskForSpecificUserNullUserId() {
		Mockito.when(taskDAO.allOpenTaskForSpecificUser(null)).thenReturn(null);
		
		assertThrows(FieldRequiredException.class, () -> taskEJB.allOpenTaskForSpecificUser(null));
	}
	
	@Test
	void testRetrieveDetailsNullUserId() {
		assertThrows(FieldRequiredException.class, () -> taskEJB.allOpenTaskForSpecificUser(null));
	}
	
	@Test
	void testCloseTaskNullUniqueId() {
		assertThrows(FieldRequiredException.class, () -> taskEJB.closeTask(null));
	}
	
	@Test
	void testCloseTaskNotFound() {
		assertThrows(TaskNotFoundException.class, () -> {
			Mockito.doThrow(new TaskNotFoundException()).when(taskDAO).closeTask(1L);
			taskEJB.closeTask(1L);
		});
	}
	
	@Test
	void testCreateNewTaskNull() {
		assertThrows(FieldRequiredException.class, () -> taskEJB.createNewTask(null));
	}
	
	@Test
	void testCreateNewTaskNullNameOrStatus() {
		final TaskDTO taskDTONoName = createTaskDTO();
		taskDTONoName.setName(null);
		assertThrows(FieldRequiredException.class, () -> taskEJB.createNewTask(taskDTONoName));
		
		final TaskDTO taskDTONoStatus = createTaskDTO();
		taskDTONoStatus.setStatus(null);
		assertThrows(FieldRequiredException.class, () -> taskEJB.createNewTask(taskDTONoStatus));
	}
	
	
	
}
