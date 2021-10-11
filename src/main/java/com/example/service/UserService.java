package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.repository.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public void signUp(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.signUp(user);
	}

	public User login(String mailAddress, String password) {
		return userRepository.findByMailAddressAndPassword(mailAddress, password);
	}
	
	public User findByMailAddress(String mailAddress) {
		return userRepository.findByMailAddress(mailAddress);
	}
	
	public void updateProfile(Integer id, String name, String profile) {
		User user = userRepository.load(id);
		user.setName(name);
		user.setProfile(profile);
		
		userRepository.updateProfile(user);
	}
}
