package com.example.todo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todo.model.Task;
import com.example.todo.model.User;
import com.example.todo.repo.UserRepo;

import lombok.Data;

@Service
@Data
public class UserService {
	
	@Autowired
	UserRepo userRepo;
	
	public Iterable<User> getUsers() {
		return userRepo.findAll();
	}
	
	public Optional<User> getUser(Integer userId) {
		return userRepo.findById(userId);
	}
	
	public User saveUser(User user) {
		return userRepo.save(user);
	}
	

	public void deleteUser(User user) {
		userRepo.delete(user);
	}

}
