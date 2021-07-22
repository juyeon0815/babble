package com.ssafy.api.service;

import com.ssafy.api.request.UserUpdatePatchReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserRepositorySupport;

import java.util.Optional;

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

		System.out.println(">>> " + userRegisterInfo.getUserId());
		user.setUserId(userRegisterInfo.getUserId());
		// 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
		user.setPassword(passwordEncoder.encode(userRegisterInfo.getPassword()));

		user.setPosition(userRegisterInfo.getPosition());
		user.setDepartment(userRegisterInfo.getDepartment());
		user.setName(userRegisterInfo.getName());
		return userRepository.save(user);
	}

	@Override
	public User getUserByUserId(String userId) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		User user = userRepositorySupport.findUserByUserId(userId).get();
		return user;
	}

	@Override
	public boolean checkId(String userId) {
		// TODO Auto-generated method stub
		boolean flag = userRepositorySupport.checkId(userId);
		return flag;
	}

	@Override
	public void updateUser(String userId, UserUpdatePatchReq userUpdateInfo) {
		userRepositorySupport.updateUser(userId, userUpdateInfo);
	}
//	@Override	public User updateUser(String userId, UserUpdatePatchReq userModifyInfo) {
//		// 정보 수정하려는 회원
//		User user = getUserByUserId(userId);
//		user.setPosition(userModifyInfo.getPosition());
//		user.setDepartment(userModifyInfo.getDepartment());
//		user.setName(userModifyInfo.getName());
//		return userRepository.save(user);
//	}

	@Override
	public void deleteUser(String userId) {
		userRepositorySupport.deleteUser(userId);
	}
}
