package com.babble.api.service;

import com.babble.api.request.user.UserRegisterReq;
import com.babble.api.request.user.UserUpdatePasswordReq;
import com.babble.api.request.user.UserUpdatePictureReq;
import com.babble.db.entity.User;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {
	User createUser(UserRegisterReq userRegisterInfo);
	User getUserByUserEmail(String email);
	User checkEmail(String email);
	void updatePicture(UserUpdatePictureReq userInfo);
	void updatePassword(UserUpdatePasswordReq userInfo);
	void deleteUser(String email);
	void updateAlarm(String email);
}
