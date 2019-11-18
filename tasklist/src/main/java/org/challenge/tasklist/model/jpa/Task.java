package org.challenge.tasklist.model.jpa;

import java.io.Serializable;
import javax.persistence.*;

import org.challenge.tasklist.model.enumeration.StatusEnum;

import java.util.List;


@Entity
@Table(name="TASK")
@NamedQueries({
	@NamedQuery(name="Task.findAllTasksForSpecificUser", query="SELECT t FROM Task t join t.users u where t.status = :status and u.userId = :userId"),
	@NamedQuery(name="Task.retrieveDetails", query="SELECT t FROM Task t where t.uniqueId = :uniqueId"),
})
public class Task implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TASK_UNIQUEID_GENERATOR", sequenceName="SQ_TASK")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TASK_UNIQUEID_GENERATOR")
	@Column(name="UNIQUE_ID", unique=true, nullable=false, precision=15)
	private Long uniqueId;

	@Column(name="DETAIL_DESCRIPTION", length=4000)
	private String detailDescription;

	@Column(nullable=false, length=100)
	private String name;

	@Column(nullable=false, length=100)
	@Enumerated(EnumType.STRING)
	private StatusEnum status;

	@ManyToMany(cascade= {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
	@JoinTable(
		name="USER_TASK_ENABLED"
		, inverseJoinColumns={
			@JoinColumn(name="USER_ID", nullable=false)
			}
		, joinColumns={
			@JoinColumn(name="TASK_ID", nullable=false)
			}
		)
	private List<User> users;

	public Task() {
	}

	public Long getUniqueId() {
		return this.uniqueId;
	}

	public void setUniqueId(Long uniqueId) {
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