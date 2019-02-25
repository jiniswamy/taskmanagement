package com.example.todo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Notification {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	public Notification(User user, boolean subscribe) {
		super();
		this.user = user;
		this.subscribe = subscribe;
	}

	@ManyToOne
	@JoinColumn(name="user_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;
	
	@Column
	private Boolean subscribe;
}
