package com.babble.api.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 회원가입 API ([POST] /api/v1/users) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@ApiModel("UserRegisterPostRequest")
public class UserRegisterReq {
	@ApiModelProperty(name="유저 Email", example="your_email")
	String email;
	@ApiModelProperty(name="유저 Password", example="your_password")
	String password;

}
