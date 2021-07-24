package com.babble.api.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 해시태그 추가 API ([POST] /api/v1/users/hashtag) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("UserHashtagPostRequest")
public class UserHashtagPostReq {

    @ApiModelProperty(name="유저 Email", example="your_email")
    String email;
    @ApiModelProperty(name="Hashtag", example="add_hashtag")
    String name;
}
