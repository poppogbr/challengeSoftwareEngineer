package org.challenge.tasklist.server.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.challenge.tasklist.model.dto.TaskDTO;
import org.challenge.tasklist.server.businesslogic.TaskEJB;

@Path("task")
public class TaskService {

	private TaskEJB taskEJB;
	
	protected TaskService() {}
	
	@Inject
	public TaskService(TaskEJB taskEJB) {
		this.taskEJB = taskEJB;
	}
	
	
	@GET
	@Path("all/open/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<TaskDTO> getAllOpenTaskToSpecificUser(@PathParam("userId") Long userId) throws Exception {
		return taskEJB.allOpenTaskForSpecificUser(userId);
	}
	
	@POST
	@Path("new")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createNewTask(TaskDTO newTask) throws Exception {
		
		TaskDTO taskDTO = taskEJB.createNewTask(newTask);
		
		UriBuilder builder = UriBuilder.fromPath("task/detail/{taskId}");
        return Response.created(builder.build(String.valueOf(taskDTO.getUniqueId()))).build();
	}
	
	@GET
	@Path("detail/{taskId}")
	@Produces(MediaType.APPLICATION_JSON)
	public TaskDTO getDetailsOfTask(@PathParam("taskId") Long taskId) throws Exception {
		return taskEJB.retrieveDetails(taskId);
	}
	
	@PUT
	@Path("close/{taskId}")
	public Response closeTask(@PathParam("taskId") Long taskId) throws Exception {
		taskEJB.closeTask(taskId);
		return Response.noContent().build();
	}
	
}
