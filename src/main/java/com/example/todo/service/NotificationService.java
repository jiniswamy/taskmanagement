package com.example.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todo.model.Notification;
import com.example.todo.model.Task;
import com.example.todo.model.User;
import com.example.todo.repo.NotificationRepo;
import com.example.todo.repo.UserRepo;

import lombok.Data;

@Service
@Data
public class NotificationService {
	
	@Autowired
	private NotificationRepo notificationRepo;

	@Autowired
	UserRepo userRepo;
	
	public Iterable<Notification> getNotifications() {
		return notificationRepo.findAll();
	}
	
	public Optional<Notification> getNotification(Integer notificationId) {
		return notificationRepo.findById(notificationId);
	}
	
	public Notification saveNotification(Notification notification) {
		return notificationRepo.save(notification);
	}
	

	public void deleteNotification(Notification notification) {
		notificationRepo.delete(notification);
	}
	
	public List<Notification> notificationByUserId(Integer userId) {
		Optional optional = userRepo.findById(userId);
		User user = (User) optional.get();
		return notificationRepo.findByUser(user);
	}

}
