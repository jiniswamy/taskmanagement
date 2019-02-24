package com.example.todo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.hibernate.exception.SQLGrammarException;
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
	
	@GetMapping("/")
	public Map<String,String> getRoot(){
		Map<String,String> map = new HashMap<>();
		map.put("Hello", "Hello World");
		return map;
		
	}
	
	
	@GetMapping("/tasks")
	public Iterable<Task> getAllTasks(){
		Iterable<Task> tasks = taskService.getTasks();
		
		return tasks;
		
	}
	@ResponseStatus(HttpStatus.FOUND)
	@GetMapping(value="/task/{taskId}")
	public Optional<Task> getTask(@PathVariable("taskId") Integer taskId) {
		Optional<Task> task = taskService.getTask(taskId);
		if(!task.isPresent()) {
			throw new NotFoundException("Task not found for this id"+ taskId);
		}
		return task;
	}
	
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value="/task")
	public Task createTask(@Valid @RequestBody Task task) {
		try {
			taskService.saveTask(task);
		} catch (SQLGrammarException e) {
			log.error("Received SQL Grammar Exception: messge=" + e.getMessage(), e);
		}
		return task;
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value="/user/{userId}/task")
	public Task createTaskByUserId(@PathVariable("userId") Integer userId, @Valid @RequestBody Task task) {
		try {
			Optional optional = userService.getUser(userId);
			User user = (User)optional.get();
			task.setUser(user);
			taskService.saveTask(task);
		} catch (SQLGrammarException e) {
			log.error("Received SQL Grammar Exception: messge=" + e.getMessage(), e);
		}
		return task;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping(value="/task/{taskId}")
	public Task updateTask(@PathVariable("taskId") Integer taskId, @Valid @RequestBody Task updateTask){
		Optional<Task> optional = taskService.getTask(taskId);
		Task task = null;
		if(optional.isPresent()) {
			task = optional.get();
			task.setTaskName(updateTask.getTaskName());
			task.setCreatedDate(updateTask.getCreatedDate());
			task.setDueDate(updateTask.getDueDate());
			
		}else {
			throw new NotFoundException("Task with this id not found:"+ taskId);
		}
		
		return task;
		
	}
	
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping(value="/task/{taskId}")
	public void deleteTask(@PathVariable("taskId") Integer taskId) {
		Optional<Task> optional = taskService.getTask(taskId);
		Task task = optional.get();
		taskService.deleteTask(task);
	}
	
	@ResponseStatus(HttpStatus.FOUND)
	@GetMapping("/user/{userId}/tasks")
	public List<Task> getTaskByUserId(@PathVariable("userId") Integer userId){
		return taskService.taskByUserId(userId);
	}
	
	@PostMapping("/user")
	public User createUser(@Valid @RequestBody User user) {
		return userService.saveUser(user);
	}
	
	@GetMapping("/users")
	public Iterable<User> getUsers() {
		return userService.getUsers();
	}
	
	@GetMapping("/user/{userId}")
	public Optional<User> getUsers(@PathVariable("userId") Integer userId) {
		return userService.getUser(userId);
	}
	
	@PostMapping("/user/{userId}/notice")
	public Notification createNotification(@Valid @PathVariable("userId") Integer userId, @RequestBody Notification notification) {
		Optional optional = userService.getUser(userId);
		User user = (User)optional.get();
		notification.setUser(user);
		return notificationService.saveNotification(notification);
	}
	
	@GetMapping("/notices")
	public Iterable<Notification> getNotification() {
		return notificationService.getNotifications();
	}
	
	@GetMapping("/user/{userId}/notification")
	public List<Notification> getNotification(@PathVariable("userId") Integer userId) {
		return notificationService.notificationByUserId(userId);
	}
	
	@PostMapping("/tasks/poc")
	public List<Task> createPOCTask(){
		
		List<Task> tasks = taskService.createPocTasks();
		
		return tasks;
		
	}
	
	@GetMapping("/tasks/today")
	public List<Task> getTasksForToday(){
		return cacheService.getTasksForToday();
	}


}
