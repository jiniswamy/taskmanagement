package com.example.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.model.Stats;
import com.example.todo.service.CacheService;

@RestController
public class StatsController {

	@Autowired
	CacheService cacheService;

	@GetMapping("/stats")
	public Stats getStatsFromCache() {
		return Stats.builder()
		.totalNotificationsSentToday(cacheService.getTasksForToday().size())
		.totalNumberOfTasks(cacheService.getTaskCount())
		.totalNumberOfUsers(cacheService.getUsersCount())
		.build();
	}

}
