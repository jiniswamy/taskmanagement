package com.example.todo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todo.model.Task;
import com.example.todo.repo.TaskRepo;

import lombok.Data;

@Service
@Data
public class TaskService {
	
	@Autowired
	private TaskRepo taskRepo;
	
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

}
