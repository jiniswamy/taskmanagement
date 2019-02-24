package com.example.todo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Stats {
	private long totalNumberOfUsers;
	private long totalNumberOfTasks;
	private int totalNotificationsSentToday;

}
