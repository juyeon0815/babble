package com.babble.api.service;

import com.babble.api.request.UserRegisterPostReq;
import com.babble.api.request.UserUpdatePatchReq;
import com.babble.db.entity.User;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {
	User createUser(UserRegisterPostReq userRegisterInfo);
	User getUserByUserId(String userId);
//	boolean checkId(String userId);
//	void deleteUser(String userId);
//	void updateUser(UserUpdatePatchReq userUpdateInfo);
}
