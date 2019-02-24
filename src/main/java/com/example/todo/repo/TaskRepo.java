package com.example.todo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.todo.model.Task;
import com.example.todo.model.User;

@Repository
public interface TaskRepo extends CrudRepository<Task, Integer>{

	public List<Task> findByUser(User user);

	@Query("select distinct t from User u join Notification n on u.id = n.user.id join Task t on t.user.id = u.id where t.dueDate = current_date() and n.subscribe = true")
	public List<Task> getTasksForToday();


}
