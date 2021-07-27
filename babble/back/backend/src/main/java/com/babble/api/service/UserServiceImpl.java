package com.babble.api.service;

import com.babble.api.request.user.UserRegisterReq;
import com.babble.api.request.user.UserUpdatePasswordReq;
import com.babble.api.request.user.UserUpdatePictureReq;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.babble.db.entity.User;
import com.babble.db.repository.UserRepository;
import com.babble.db.repository.UserRepositorySupport;

import java.util.List;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserRepositorySupport userRepositorySupport;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public User createUser(UserRegisterReq userRegisterInfo) {
		User user = new User();
		user.setEmail(userRegisterInfo.getEmail());
		// 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
		user.setPassword(passwordEncoder.encode(userRegisterInfo.getPassword()));

		user.setPicture("default"); //default 사진
		user.setAlarm(false);
		return userRepository.save(user);
	}

	@Override
	public User getUserByUserEmail(String email) {
		// 디비에 유저 정보 조회 (email 를 통한 조회).
		User user = userRepositorySupport.findUserByUserEmail(email);
		return user;
	}

	@Override
	public User checkEmail(String email) {
		User user = userRepositorySupport.findUserByUserEmail(email);
		return  user;
	}

	@Override
	public void deleteUser(String email) {
		User user = userRepositorySupport.findUserByUserEmail(email);
		userRepository.delete(user);
	}

	@Override
	public void updateAlarm(String email) {
		User user = userRepositorySupport.findUserByUserEmail(email);
		boolean flag = !user.isAlarm();
		System.out.println(flag);
		user.setAlarm(flag);
		userRepository.save(user);
	}


	@Override
	public void updatePicture(UserUpdatePictureReq userInfo) {
		User user = userRepositorySupport.findUserByUserEmail(userInfo.getEmail());

		user.setPicture(userInfo.getPicture());
		userRepository.save(user);
	}

	@Override
	public void updatePassword(UserUpdatePasswordReq userInfo) {
		User user = userRepositorySupport.findUserByUserEmail(userInfo.getEmail());
		user.setPassword(passwordEncoder.encode(userInfo.getPassword()));
		userRepository.save(user);
	}
}
