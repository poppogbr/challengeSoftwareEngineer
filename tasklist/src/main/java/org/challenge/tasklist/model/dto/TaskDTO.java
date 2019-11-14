package org.challenge.tasklist.model.dto;

import java.io.Serializable;
import java.util.List;

import org.challenge.tasklist.model.enumeration.StatusEnum;

public class TaskDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long uniqueId;
	private String name;
	private String description;
	private StatusEnum status;
	private List<UserDTO> usersEnabled;
	
	public Long getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(Long uniqueId) {
		this.uniqueId = uniqueId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public StatusEnum getStatus() {
		return status;
	}
	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	public List<UserDTO> getUsersEnabled() {
		return usersEnabled;
	}
	public void setUsersEnabled(List<UserDTO> usersEnabled) {
		this.usersEnabled = usersEnabled;
	}
	
	
}
