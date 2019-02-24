package com.example.todo.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.todo.model.Task;
import com.example.todo.service.TaskService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@DependsOn("PostCreateActions")
public class ScheduleTask {

	@Autowired
	TaskService taskService;

	@Scheduled(cron = "${scheduler.task.cron:0 0/1 * * * ?}")
	public void sendNotification() {
        List<Task> tasks = taskService.getTasksForToday();
		log.info("SendNotification: notification tasks={}", tasks);
	}

}
