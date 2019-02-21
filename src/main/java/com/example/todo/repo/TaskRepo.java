package com.example.todo.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.todo.model.Task;

@Repository
public interface TaskRepo extends CrudRepository<Task, Integer>{
	

}
