package com.babble.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.babble.api.request.UserLoginPostReq;
import com.babble.api.response.UserLoginPostRes;
import com.babble.api.service.UserService;
import com.babble.common.model.response.BaseResponseBody;
import com.babble.common.util.JwtTokenUtil;
import com.babble.db.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

/**
 * 인증 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "인증 API", tags = {"Auth."})
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	@Autowired
	UserService userService;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@PostMapping("/login")
	@ApiOperation(value = "로그인", notes = "<strong>이메일과 패스워드</strong>를 통해 로그인 한다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공", response = UserLoginPostRes.class),
        @ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
        @ApiResponse(code = 404, message = "사용자 없음", response = BaseResponseBody.class),
        @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
    })
	public ResponseEntity<UserLoginPostRes> login(@RequestBody @ApiParam(value="로그인 정보", required = true) UserLoginPostReq loginInfo) {
		String email = loginInfo.getEmail();
		String password = loginInfo.getPassword();

		User user = userService.getUserByUserEmail(email);

		// 사용자가 없는경우
		if(user==null) return ResponseEntity.status(404).body(UserLoginPostRes.of(404, "존재하지 않는 계정입니다.","fail"));

		// 로그인 요청한 유저로부터 입력된 패스워드 와 디비에 저장된 유저의 암호화된 패스워드가 같은지 확인.(유효한 패스워드인지 여부 확인)
		else if(passwordEncoder.matches(password, user.getPassword())) {
			// 유효한 패스워드가 맞는 경우, 로그인 성공으로 응답.(액세스 토큰을 포함하여 응답값 전달) (만료시간 및 secret값)
			return ResponseEntity.ok(UserLoginPostRes.of(200, "Success", JwtTokenUtil.getToken(email)));
		}else return ResponseEntity.status(401).body(UserLoginPostRes.of(401, "잘못된 비밀번호 입니다.", "fail"));
	}
}
