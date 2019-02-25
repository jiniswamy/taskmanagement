package com.example.todo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Task {

	public Task(User user, @NotNull @Size(min = 1) String taskName, Date createdDate, Date dueDate, Integer priority,
			Boolean complete) {
		super();
		this.user = user;
		this.taskName = taskName;
		this.createdDate = createdDate;
		this.dueDate = dueDate;
		this.priority = priority;
		this.complete = complete;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;
	
	@Column
	@NotNull
	@Size(min=1)
	private String taskName;
	
	@Column
	private Date createdDate;
	
	@Column
	private Date dueDate;
	
	@Column
	private Integer priority;
	
	@Column
	private Boolean complete;
	
}
