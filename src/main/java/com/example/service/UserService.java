package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.repository.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public void signUp(User user) {
		userRepository.signUp(user);
	}
	
	public User login(String mailAddress, String password) {
		User user = userRepository.findByMailAddressAndPassword(mailAddress, password);
		return user;
	}
}
