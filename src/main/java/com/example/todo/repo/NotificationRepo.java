package com.example.todo.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.todo.model.Notification;
import com.example.todo.model.User;

public interface NotificationRepo extends CrudRepository<Notification, Integer> {

	public List<Notification> findByUser(User user);
}
