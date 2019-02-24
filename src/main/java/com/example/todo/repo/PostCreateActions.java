package com.example.todo.repo;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.example.todo.model.Task;
import com.example.todo.service.TaskService;

import lombok.extern.slf4j.Slf4j;

@Component("PostCreateActions")
@Slf4j
@Profile("!dev")
public class PostCreateActions {

	@Autowired
	TaskService taskService;
	
	@PostConstruct
	public void postConstruct() {
		log.info("EXECUTING POST CONSTRUCT");
		List<Task> tasks = taskService.createPocTasks();
	}
}
