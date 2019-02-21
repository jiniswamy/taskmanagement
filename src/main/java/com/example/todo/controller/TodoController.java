package com.example.todo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.model.Task;
import com.example.todo.repo.TaskRepo;
import com.exmple.todo.exceptionhandlers.NotFoundException;

import lombok.Data;

@RestController
@Data
public class TodoController {
	private Map<Integer, Task> tasksMap = new HashMap<Integer, Task>();

	@Autowired
	private TaskRepo taskRepo;
	
	
	@GetMapping("/task/all")
	public Iterable<Task> getAllTasks(){
		Iterable<Task> tasks = taskRepo.findAll();
		
		return tasks;
		
	}
	@ResponseStatus(HttpStatus.FOUND)
	@GetMapping(value="/task/{taskId}")
	public Optional<Task> getTask(@PathVariable("taskId") Integer taskId) {
		Optional<Task> task = taskRepo.findById(taskId);
		if(!task.isPresent()) {
			throw new NotFoundException("Task not found for this id"+ taskId);
		}
		//java 8
		  /*return Optional
		            .ofNullable(taskRepo.findById(taskId))
		            .map( task -> ResponseEntity.ok().body(task) )          //200 OK
		            .orElseGet( () -> ResponseEntity.notFound().build() ); */
		return task;
	}
	
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value="/task/")
	public Task getTask(@RequestBody Task task) {;
		taskRepo.save(task);
		return task;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping(value="/task/{taskId}")
	public Task updateTask(@PathVariable("taskId") Integer taskId, @RequestBody Task updateTask){
		Optional<Task> optional = taskRepo.findById(taskId);
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
		Optional<Task> optional = taskRepo.findById(taskId);
		Task task = optional.get();
		taskRepo.delete(task);
	}

}
