package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.User;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 회원 본인 정보 조회 API ([GET] /api/v1/users/me) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ToString
@ApiModel("UserResponse")
public class UserRes{

	@ApiModelProperty(name="User department")
	String department;
	@ApiModelProperty(name="User position")
	String position;
	@ApiModelProperty(name="User name")
	String name;
	@ApiModelProperty(name="User ID")
	String userId;

	public static UserRes of(User user) {
		UserRes res = new UserRes();
		res.setDepartment(user.getDepartment());
		res.setPosition(user.getPosition());
		res.setName(user.getName());
		res.setUserId(user.getUserId());
		return res;
	}
}
