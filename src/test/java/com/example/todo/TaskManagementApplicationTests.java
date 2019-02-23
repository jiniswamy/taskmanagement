package com.example.todo;

import static org.mockito.Mockito.times;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import com.example.todo.controller.TodoController;
import com.example.todo.model.Task;
import com.example.todo.repo.TaskRepo;
import com.example.todo.service.TaskService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TaskManagementApplicationTests {
	@Mock
	Task task;
	
	@Mock
	List<Task> tasks;
	
	@Mock
	TaskRepo taskRepo;

	@Autowired
	@InjectMocks
	TaskService taskService;
	
	@LocalServerPort
	private int port;
	
	
	@Test
	public void testGetTasks() {
		Task task = Mockito.mock(Task.class);
		tasks.add(task);
		Mockito.when(taskRepo.findAll()).thenReturn(tasks);
		taskService.getTasks();
	}
	
	@Test
	public void testSaveTask() {
		
		Mockito.when(taskRepo.save(task)).thenReturn(task);
		taskService.saveTask(task);
		Assert.assertNotNull(task);
		Mockito.verify(taskRepo).save(task);
		// verify number of interactions with mock
		 Mockito.verify(taskRepo, times(1)).save(task);
		 Mockito.verifyZeroInteractions(taskRepo);
	}
	
	@Test
	public void testTaskDelete() {
		taskService.deleteTask(task);
		Mockito.verify(taskRepo, times(1)).delete(task);
	}
	

}
