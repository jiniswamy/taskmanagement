package com.example.todo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

import com.example.todo.model.Task;
import com.example.todo.service.TaskService;
import com.exmple.todo.exceptionhandlers.NotFoundException;

import lombok.Data;

@RestController
@Data
public class TodoController {
	private Map<Integer, Task> tasksMap = new HashMap<Integer, Task>();

	@Autowired
	private TaskService taskService;
	
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
	public Task getTask(@RequestBody Task task) {;
		taskService.saveTask(task);
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

}
