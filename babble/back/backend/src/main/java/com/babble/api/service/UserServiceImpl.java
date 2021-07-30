package com.babble.api.service;

import com.babble.api.request.user.UserRegisterReq;
import com.babble.api.request.user.UserUpdatePasswordReq;
import com.babble.api.request.user.UserUpdatePictureReq;
import com.babble.api.response.UserHistoryRes;
import com.babble.db.entity.QCategory;
import com.babble.db.entity.QRoom;
import com.babble.db.entity.QRoomHistory;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.babble.db.entity.User;
import com.babble.db.repository.UserRepository;
import com.babble.db.repository.UserRepositorySupport;

import java.util.ArrayList;
import java.util.Date;
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
		User user = User.builder()
				.email(userRegisterInfo.getEmail())
				.password(passwordEncoder.encode(userRegisterInfo.getPassword()))
				.build();
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
		user.updateAlarm(user.isAlarm());
		userRepository.save(user);
	}

	@Override
	public List<UserHistoryRes> historyList(List<Tuple> historyInfo, String command) {
		QRoom qRoom = QRoom.room;
		QCategory qCategory = QCategory.category;
		QRoomHistory qRoomHistory = QRoomHistory.roomHistory;

		List<UserHistoryRes> list = new ArrayList<>();

		for(int i=0;i<historyInfo.size();i++){
			String title = historyInfo.get(i).get(qRoom.title);
			String category = historyInfo.get(i).get(qCategory.name);
			Date create = historyInfo.get(i).get(qRoom.createTime);
			Long maxView = historyInfo.get(i).get(qRoom.maxView);

			UserHistoryRes userHistoryRes = new UserHistoryRes();
			userHistoryRes.setTitle(title);
			userHistoryRes.setCategory(category);
			userHistoryRes.setViewDate(create);
			userHistoryRes.setMaxView(maxView);

			if(command.equals("view")){
				Date start = historyInfo.get(i).get(qRoomHistory.startTime);
				Date end = historyInfo.get(i).get(qRoomHistory.endTime);
				userHistoryRes.setViewStart(start);
				userHistoryRes.setViewEnd(end);
			}

			list.add(userHistoryRes);
		}
		return list;
	}


	@Override
	public void updatePicture(String email, String fileName) {
		User user = userRepositorySupport.findUserByUserEmail(email);

		user.updatePicture(fileName);
		userRepository.save(user);
	}

	@Override
	public void updatePassword(UserUpdatePasswordReq userInfo) {
		User user = userRepositorySupport.findUserByUserEmail(userInfo.getEmail());
		user.updatePassword(passwordEncoder.encode(userInfo.getPassword()));
		userRepository.save(user);
	}
}
