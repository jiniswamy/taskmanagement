package com.example.todo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.exceptionhandlers.NotFoundException;
import com.example.todo.model.Notification;
import com.example.todo.model.Task;
import com.example.todo.model.User;
import com.example.todo.service.CacheService;
import com.example.todo.service.NotificationService;
import com.example.todo.service.TaskService;
import com.example.todo.service.UserService;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@RestController
@Data
@Slf4j
public class TodoController {
	private Map<Integer, Task> tasksMap = new HashMap<Integer, Task>();

	@Autowired
	private UserService userService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private CacheService cacheService;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/users")
	public Iterable<User> getUsers() {
		return userService.getUsers();
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/user/{userId}")
	public User getUsers(@PathVariable("userId") Integer userId) {
		return userService.getUser(userId).orElseThrow(() -> new NotFoundException("User could not be found. userId = " + userId));
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/user")
	public User createUser(@Valid @RequestBody User user) {
		return userService.saveUser(user);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/user/{userId}")
	public void deleteUser(@PathVariable("userId") Integer userId) {
		userService.getUser(userId).map(u -> {
			userService.deleteUser(u);
			return 0;
		}).orElseThrow(() -> new NotFoundException("user could not be found. userId = " + userId));
		
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/tasks")
	public Iterable<Task> getAllTasks() {
		Iterable<Task> tasks = taskService.getTasks();
		return tasks;

	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/task/{taskId}")
	public Task getTask(@PathVariable("taskId") Integer taskId) {
		return taskService.getTask(taskId).orElseThrow(() -> new NotFoundException("task could not be found. taskId = " + taskId));
		
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/user/{userId}/task")
	public Task createTaskByUserId(@PathVariable("userId") Integer userId, @Valid @RequestBody Task task) {
		return userService.getUser(userId).map(u -> {
		    log.info("taskName={}",task.getTaskName());
			task.setUser(u);
			taskService.saveTask(task);
			return task;
		}).orElseThrow(() -> new NotFoundException("user could not be found. userId = " + userId));
	}

	@ResponseStatus(HttpStatus.ACCEPTED)
	@PutMapping(value = "/task/{taskId}")
	public void updateTask(@PathVariable("taskId") Integer taskId, @Valid @RequestBody Task updateTask) {
		taskService.getTask(taskId).map(t -> {
			updateTask.setId(t.getId());
			taskService.saveTask(updateTask);
			return updateTask;
		}).orElseThrow(() -> new NotFoundException("task could not be found. taskId = " + taskId));
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value = "/task/{taskId}")
	public void deleteTask(@PathVariable("taskId") Integer taskId) {
		
		taskService.getTask(taskId).map(t -> {
			taskService.deleteTask(t);
			return 0;
		}).orElseThrow(() -> new NotFoundException("task could not be found. taskId = " + taskId));
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/user/{userId}/tasks")
	public List<Task> getTaskByUserId(@PathVariable("userId") Integer userId) {
		return taskService.taskByUserId(userId);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/user/{userId}/notification")
	public Notification createNotification(@Valid @PathVariable("userId") Integer userId,
			@RequestBody Notification notification) {
		return userService.getUser(userId).map(u -> {
			notification.setUser(u);
			notificationService.saveNotification(notification);
			return notification;
		}).orElseThrow(() -> new NotFoundException("user could not be found. userId = " + userId));
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/notifications")
	public Iterable<Notification> getNotification() {
		return notificationService.getNotifications();
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/user/{userId}/notification")
	public List<Notification> getNotification(@PathVariable("userId") Integer userId) {
		return notificationService.notificationByUserId(userId);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/tasks/poc")
	public List<Task> createPOCTask() {

		List<Task> tasks = taskService.createPocTasks();

		return tasks;

	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/tasks/today")
	public List<Task> getTasksForToday() {
		return cacheService.getTasksForToday();
	}

}
