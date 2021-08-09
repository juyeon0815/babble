package com.babble.api.response.user;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;

/**
 * 유저 카카오로그인 API ([POST] /api/v1/auth) 요청에 대한 응답값 정의.
 */
@Getter
@ApiModel("KakaoResponse")
public class KakaoLoginPostRes {
    @ApiModelProperty(name="User email", example="example@example.com")
    String email;
    @ApiModelProperty(name="JWT 인증 토큰", example="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN...")
    String accessToken;

    @Builder
    public KakaoLoginPostRes(HashMap<String, String> userInfo, String access_Token){
        this.email = userInfo.get("email");
        this.accessToken = access_Token;
    }

}
