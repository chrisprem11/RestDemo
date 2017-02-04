package com.digital.service;

import java.util.ArrayList;
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

	public Boolean createNewUsers(ArrayList<User> userList) {
		boolean value = false;
		for (int i = 0; i < userList.size(); i++) {
			User user = new User();
			user.setContact(userList.get(i).getContact());
			user.setEmail(userList.get(i).getEmail());
			user.setFirstName(userList.get(i).getFirstName());
			user.setLastName(userList.get(i).getLastName());
			try {
				userRepository.save(user);
				System.out.println("User with row ( " + i + " ) saved.");
				value = true;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Something went wrong. User not saved.");
			}
		}
		return value;
	}

	public Boolean deleteMultipleUsers(ArrayList<User> deleteList) {
		boolean value = false;
		for (User object : deleteList) {
			long id = object.getpId();
			userRepository.deleteUser(id);
			System.out.println("User with id ( " + id + " ) deleted.");
			value = true;
		}
		return value;
	}

	public void updateUser(User currentUser) {
		
		userRepository.save(currentUser);
	}

	public Boolean udateMultipleUsers(ArrayList<User> updateList) {
		boolean value = false;
		for(User user : updateList){
			long id  = user.getpId();
			User currentUser = userRepository.findByPId(id);
			currentUser.setFirstName(user.getFirstName());
			currentUser.setLastName(user.getLastName());
			try {
				userRepository.save(currentUser);
				value=true;
				System.out.println("User with id ("+id+") is updated.");

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Updation Failed");
			}
		}
		return value;
	}

	public User createOneNewUser(User user) {
		User newUser = new User();
		newUser.setContact(user.getContact());
		newUser.setEmail(user.getEmail());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		return userRepository.save(newUser);
	}

}
