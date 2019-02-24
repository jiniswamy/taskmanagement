package com.example.todo.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.todo.model.User;

@Repository
public interface UserRepo extends CrudRepository<User, Integer>{

	
}
