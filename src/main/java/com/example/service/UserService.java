package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.repository.UserRepository;

/**
 * ユーザー情報の業務処理を行うサービス.
 * 
 * @author hayato.saishu
 *
 */
@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * ユーザーを登録する.
	 * 
	 * @param user ユーザー
	 */
	public void signUp(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.signUp(user);
	}

	/**
	 * ログイン処理をする.
	 * 
	 * @param mailAddress メールアドレス
	 * @param password　パスワード
	 * @return　ユーザー情報
	 */
	public User login(String mailAddress, String password) {
		return userRepository.findByMailAddressAndPassword(mailAddress, password);
	}

	/**
	 * メールアドレスからユーザー情報を取得する.
	 * 
	 * @param mailAddress メールアドレス
	 * @return　ユーザー情報
	 */
	public User findByMailAddress(String mailAddress) {
		return userRepository.findByMailAddress(mailAddress);
	}

	/**
	 * ユーザー情報を更新する.
	 * 
	 * @param id ID
	 * @param name 名前
	 * @param profile プロフィール
	 */
	public void updateProfile(Integer id, String name, String profile) {
		User user = userRepository.load(id);
		user.setName(name);
		if (profile.length() != 0) {
			user.setProfile(profile);
		}

		userRepository.updateProfile(user);
	}
	
	public User load(Integer id) {
		return userRepository.load(id);
	}
}
