package com.example.todo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer taskId;
	
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
