package com.babble.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.babble.api.request.UserRegisterPostReq;
import com.babble.api.request.UserUpdatePatchReq;
import com.babble.db.entity.User;
import com.babble.db.repository.UserRepository;
import com.babble.db.repository.UserRepositorySupport;

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
	public User createUser(UserRegisterPostReq userRegisterInfo) {
		User user = new User();
		user.setEmail(userRegisterInfo.getEmail());
		// 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
		user.setPassword(passwordEncoder.encode(userRegisterInfo.getPassword()));

		user.setPicture("default"); //default 사진
		return userRepository.save(user);
	}

	@Override
	public User getUserByUserId(String userId) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		User user = userRepositorySupport.findUserByUserId(userId).get();
		return user;
	}

//	@Override
//	public boolean checkId(String userId) {
//		// TODO Auto-generated method stub
//		boolean flag = userRepositorySupport.checkId(userId);
//		return flag;
//	}
//
//	@Override
//	public void deleteUser(String userId) {
//		userRepositorySupport.deleteUser(userId);
//
//	}
//
//	@Override
//	public void updateUser(UserUpdatePatchReq userUpdateInfo) {
//		userRepositorySupport.updateUser(userUpdateInfo);
//	}
}
