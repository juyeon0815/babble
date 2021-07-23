package com.babble.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 정보 수정 API ([PUT] /api/v1/users) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("UserUpdatePutRequest")
public class UserUpdatePatchReq {
	@ApiModelProperty(name="유저 department", example="your_department")
	String department;
	@ApiModelProperty(name="유저 position", example="your_position")
	String position;
	@ApiModelProperty(name="유저 name", example="your_name")
	String name;
	@ApiModelProperty(name="유저 ID", example="your_id")
	String id;
}
