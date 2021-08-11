package com.babble.api.response.user;

import com.babble.db.entity.User;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원 본인 정보 조회 API ([GET] /api/v1/users/me) 요청에 대한 응답값 정의.
 */
@Getter
@ApiModel("UserResponse")
public class UserRes{
	@ApiModelProperty(name="Id")
	Long id;
	@ApiModelProperty(name="User Email")
	String email;
	@ApiModelProperty(name="User Picture")
	String picture;
	@ApiModelProperty(name="User Alarm")
	boolean alarm;

	@Builder
	public UserRes(User user){
		this.id= user.getId();
		this.email = user.getEmail();
		this.picture = user.getPicture();
		this.alarm = user.isAlarm();
	}
}
