package com.digital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digital.model.User;
import com.digital.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User getUser(Long id) {
		
		User user = userRepository.findByPId(id);
		return user;
	}

	public int deleteUser(Long id) {
		int value = userRepository.deleteUser(id);
		return value;
	}

}
