package org.challenge.tasklist.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.challenge.tasklist.model.dto.TaskDTO;

@Path("task")
public class TaskService {

	@GET
	@Path("all/open/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<TaskDTO> getAllOpenTaskToSpecificUser(@PathParam("userId") Long userId) {
		return null;
	}
	
	@POST
	@Path("new")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public TaskDTO createNewTask(TaskDTO newTask) {
		return null;
	}
	//On successful creation, return HTTP status 201, returning a Location header with a link to the newly-created resource with the 201 HTTP status.
	
	@GET
	@Path("detail/{taskId}")
	@Produces(MediaType.APPLICATION_JSON)
	public TaskDTO getDetailsOfTask(@PathParam("taskId") Long taskId) {
		return null;
	}
	
	@PUT
	@Path("close/{taskId}")
	public Response closeTask(@PathParam("taskId") Long taskId) {
		return null;
	}
	//On successful update it returns 200 (or 204 if not returning any content in the body) from a PUT.
	
}
