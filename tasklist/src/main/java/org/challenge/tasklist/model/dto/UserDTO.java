package org.challenge.tasklist.model.dto;

import java.io.Serializable;

import org.challenge.tasklist.model.enumeration.GenderEnum;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long userId;
	private String firstName;
	private String lastName;
	private GenderEnum gender;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public GenderEnum getGender() {
		return gender;
	}
	public void setGender(GenderEnum gender) {
		this.gender = gender;
	}
}
