package com.example.todo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todo.model.Notification;
import com.example.todo.model.Task;
import com.example.todo.model.User;
import com.example.todo.repo.NotificationRepo;
import com.example.todo.repo.TaskRepo;
import com.example.todo.repo.UserRepo;

import lombok.Data;

@Service
@Data
public class TaskService {

	@Autowired
	private TaskRepo taskRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	NotificationRepo notificationRepo;
	
	@Autowired
	CacheService cacheService;

	public Iterable<Task> getTasks() {
		return taskRepo.findAll();
	}

	public Optional<Task> getTask(Integer taskId) {
		return taskRepo.findById(taskId);
	}

	public Task saveTask(Task task) {
		return taskRepo.save(task);
	}

	public void deleteTask(Task task) {
		taskRepo.delete(task);
	}

	public List<Task> taskByUserId(Integer userId) {
		Optional<User> user = userRepo.findById(userId);
		if (user.isPresent()) {
			return taskRepo.findByUser(user.get());
		}
		return new ArrayList<>();
	}

	public List<Task> getTasksForToday() {
		return cacheService.getTasksForToday();
	}

	public List<Task> createPocTasks() {
		List<Task> tasks = new ArrayList<>();
		List<User> users = Arrays.asList(new User("user1"), new User("user2"), new User("user3"));
		List<LocalDate> dates = Arrays.asList(LocalDate.now().minusDays(1), LocalDate.now(),
				LocalDate.now().plusDays(1));
		List<String> subscribedUsers = Arrays.asList("user1", "user3");

		users.forEach(user -> {
			userRepo.save(user);
			dates.forEach(date -> {
				Task task = new Task(user, "Task1", new Date(), java.sql.Date.valueOf(date), 0, false);
				taskRepo.save(task);
				tasks.add(task);
			});
		});
		users.stream().filter((user) -> subscribedUsers.contains(user.getUserName())).forEach(user -> {
			dates.forEach(date -> {
				Notification notification = new Notification(user, true);
				notificationRepo.save(notification);
			});
		});

		return tasks;
	}

}
