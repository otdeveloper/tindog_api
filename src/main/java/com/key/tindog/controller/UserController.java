package com.key.tindog.controller;

import com.key.tindog.model.User;
import com.key.tindog.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/")
	public boolean addUser(@RequestParam String email,
	                       @RequestParam String userName,
	                       @RequestParam String password) {

		User newUser = new User(email, userName, password);

		return userService.addUser(newUser);
	}
}
