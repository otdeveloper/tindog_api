package com.key.tindog.service;

import com.key.tindog.config.security.PasswordEncoder;
import com.key.tindog.model.Role;
import com.key.tindog.model.User;
import com.key.tindog.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepositry, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepositry;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(userName);

		if (user != null) {
			return user;
		} else {
			throw new UsernameNotFoundException(userName);
		}
	}

	public boolean addUser(User user) {
		User userFromDb = userRepository.findByUserName(user.getUsername());

		if (userFromDb != null) {
			return false;
		}

		user.setRoles(Collections.singleton(Role.USER));
		user.setPassword(passwordEncoder.encoder().encode(user.getPassword()));

		userRepository.save(user);

		return true;
	}
}
