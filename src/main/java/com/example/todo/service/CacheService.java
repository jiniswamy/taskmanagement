package com.example.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.todo.model.Task;
import com.example.todo.repo.NotificationRepo;
import com.example.todo.repo.TaskRepo;
import com.example.todo.repo.UserRepo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Service
public class CacheService {
	@Autowired
	TaskRepo taskRepo;

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	NotificationRepo notificationRepo;

	@Cacheable("taskCount")
	public long getTaskCount() {
		log.info("Getting task count");
		return taskRepo.count();
	}

	@Cacheable("userCount")
	public long getUsersCount() {
		log.info("Getting user count");
		return userRepo.count();
	}

	@Cacheable("tasksForToday")
	public List<Task> getTasksForToday() {
		log.info("Getting tasks for today count");
		return taskRepo.getTasksForToday();
	}

}
