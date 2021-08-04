package com.babble.api.response.user;

import com.babble.db.entity.User;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원 본인 정보 조회 API ([GET] /api/v1/users/me) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("UserResponse")
public class UserRes{
	@ApiModelProperty(name="User Email")
	String email;
	@ApiModelProperty(name="User Picture")
	String picture;
	@ApiModelProperty(name="User Alarm")
	boolean alarm;

	
	public static UserRes of(User user) {
		UserRes res = new UserRes();
		res.setEmail(user.getEmail());
		res.setPicture(user.getPicture());
		res.setAlarm(user.isAlarm());
		return res;
	}
}
