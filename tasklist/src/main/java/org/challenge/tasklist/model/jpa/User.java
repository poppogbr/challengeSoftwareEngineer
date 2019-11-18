package org.challenge.tasklist.model.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="\"USER\"")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USER_USERID_GENERATOR", sequenceName="SQ_USER")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_USERID_GENERATOR")
	@Column(name="USER_ID", unique=true, nullable=false, precision=15)
	private Long userId;

	@Column(name="FIRST_NAME", nullable=false, length=100)
	private String firstName;

	@Column(name="LAST_NAME", nullable=false, length=100)
	private String lastName;

	@ManyToMany(mappedBy="users", fetch=FetchType.LAZY)
	private List<Task> tasks;

	public User() {
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Task> getTasks() {
		return this.tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

}