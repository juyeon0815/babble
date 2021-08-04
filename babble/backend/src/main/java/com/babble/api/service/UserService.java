package com.babble.api.service;

import com.babble.api.request.user.UserRegisterReq;
import com.babble.api.request.user.UserUpdatePasswordReq;
import com.babble.api.request.user.UserUpdatePictureReq;
import com.babble.api.response.UserHistoryRes;
import com.babble.db.entity.User;
import com.querydsl.core.Tuple;

import java.util.List;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {
	User createUser(UserRegisterReq userRegisterInfo);
	User getUserByUserEmail(String email);
	User checkEmail(String email);
	void updatePicture(UserUpdatePictureReq userUpdatePictureReq);
	void updatePassword(UserUpdatePasswordReq userInfo);
	void deleteUser(String email);
	void updateAlarm(String email);
	List<UserHistoryRes> historyList(List<Tuple> historyInfo, String command);
}
