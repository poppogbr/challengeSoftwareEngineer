package org.challenge.tasklist.model.jpa;

import java.io.Serializable;
import javax.persistence.*;

import org.challenge.tasklist.model.enumeration.StatusEnum;

import java.util.List;


@Entity
@Table(name="TASK")
@NamedQuery(name="Task.findAll", query="SELECT t FROM Task t")
public class Task implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TASK_UNIQUEID_GENERATOR", sequenceName="SQ_TASK")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TASK_UNIQUEID_GENERATOR")
	@Column(name="UNIQUE_ID", unique=true, nullable=false, precision=15)
	private long uniqueId;

	@Column(name="DETAIL_DESCRIPTION", length=4000)
	private String detailDescription;

	@Column(nullable=false, length=100)
	private String name;

	@Column(nullable=false, length=100)
	@Enumerated(EnumType.STRING)
	private StatusEnum status;

	@ManyToMany(mappedBy="tasks")
	private List<User> users;

	public Task() {
	}

	public long getUniqueId() {
		return this.uniqueId;
	}

	public void setUniqueId(long uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getDetailDescription() {
		return this.detailDescription;
	}

	public void setDetailDescription(String detailDescription) {
		this.detailDescription = detailDescription;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StatusEnum getStatus() {
		return this.status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}