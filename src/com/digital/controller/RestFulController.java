package com.digital.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.digital.model.User;
import com.digital.service.UserService;

@RestController
public class RestFulController {

	@Autowired
	UserService userService;

	// First try using REST
	@RequestMapping(value = { "/link" }, method = RequestMethod.GET)
	public ResponseEntity<String> doTry() {
		String name = "Prem";
		String task = " is trying rest services";
		String result = (name + task);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	// Retrieving all users from database
	@RequestMapping(value = { "/users" }, method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers() {
		List<User> allUsers = userService.getAllUsers();
		if (allUsers.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<User>>(allUsers, HttpStatus.OK);
	}

	// Retrieving a single user with the given id.
	@RequestMapping(value = { "/user/{id}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> fetchUser(@PathVariable("id") Long id) {
		User user = userService.getUser(id);
		if (user != null) {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} else {
			System.out.println("User with id " + id + " doesn't exist");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
	}

	// deleting a user with the given id.
	@RequestMapping(value = { "/user/delete/{id}" }, method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
		User checkUser = userService.getUser(id);
		if (checkUser == null) {
			System.out.println("User with id :" + id + " not found.");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		int value = userService.deleteUser(id);
		if (value > 0) {
			System.out.println("User with  id : " + id + " has been deleted.");
		}
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

	// creating a new list of users.
	@RequestMapping(value = "/createUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> createMultipleUsers(@RequestBody ArrayList<User> userList) {
		System.out.println(userList.toString());
		if (userService.createNewUsers(userList)) {
			System.out.println("All users saved.");
		}

		List<User> allUsers = userService.getAllUsers();
		return new ResponseEntity<List<User>>(allUsers, HttpStatus.OK);
	}

	// delete multiple or selected users
	@RequestMapping(value = "/deleteUsers", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> deleteMultipleUsers(@RequestBody ArrayList<User> deleteList) {
		if (userService.deleteMultipleUsers(deleteList)) {
			System.out.println("All selected users have been deleted.");
		} else {
			System.out.println("User deletion failed.");
		}

		List<User> allUsers = userService.getAllUsers();
		return new ResponseEntity<List<User>>(allUsers, HttpStatus.OK);
	}

}
