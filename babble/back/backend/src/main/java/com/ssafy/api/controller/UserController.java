package com.ssafy.api.controller;

import com.ssafy.api.request.UserUpdatePatchReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.response.UserRes;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping()
	@ApiOperation(value = "회원 가입", notes = "<strong>아이디와 패스워드</strong>를 통해 회원가입 한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
		@ApiResponse(code = 201, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	
	// 1. 회원가입
	public ResponseEntity<? extends BaseResponseBody> register(
			@RequestBody @ApiParam(value="회원가입 정보", required = true) UserRegisterPostReq registerInfo) {

		System.out.println("UserController " + registerInfo);
		//임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
		User user = userService.createUser(registerInfo);
		
//		return ResponseEntity.status(200).boFy(BaseResponseBody.of(200, "Success"));
		return ResponseEntity.status(201).body(BaseResponseBody.of(201, "Success"));
	}
	
	// 2. 내 프로필
	@GetMapping("/me")
	@ApiOperation(value = "회원 본인 정보 조회", notes = "로그인한 회원 본인의 정보를 응답한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<UserRes> getUserInfo(@ApiIgnore Authentication authentication) {
		/**
		 * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
		 * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
		 */
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		System.out.println(">>>>> getUser().getUserId() : " + userDetails.getUser().getUserId());
		System.out.println(">>>> userDetails.isAccountNonExpired() : " + userDetails.isAccountNonExpired());

		String userId = userDetails.getUsername();
		User user = userService.getUserByUserId(userId);

		return ResponseEntity.status(200).body(UserRes.of(user));
	}

	// 3. 유저 정보 (아이디 중복 체크)
	@GetMapping("/{userId}")
	public ResponseEntity<? extends BaseResponseBody> checkUserInfo(@PathVariable String userId) {
		// 로그인한 사용자가 아닌 경우에만 응답 -> 굳이 왜..?
		if(userService.checkId(userId)) { // 중복된 ID 있음
			return ResponseEntity.status(409).body(BaseResponseBody.of(409, "이미 존재하는 사용자 ID입니다"));
		} else {
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "사용 가능한 ID입니다."));
		}
	}

	// 4. 유저 정보 수정
	@PatchMapping("/{userId}")
	public ResponseEntity<? extends BaseResponseBody> modifyUser(@ApiIgnore Authentication authentication, @PathVariable String userId,
																 @RequestBody UserUpdatePatchReq updateInfo) {
		// 다른 회원의 token을 헤더에 담아 보내도 수정 됨
		System.out.println(">>>>>>>>>>>>>>> 수정:  " + updateInfo);
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		System.out.println(">>>>> jwt token의 id : " + userDetails.getUser().getUserId());

		if(userDetails.getUser().getUserId().equals(userId)) { // Spring Security에서 제공하는 기능이 있을 것 같은데..
			// jwt token의 id와 수정하려는 회원 id가 같으니 수정 가능
			userService.updateUser(userId, updateInfo);
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
		} else {
			return ResponseEntity.status(409).body(BaseResponseBody.of(409, "회원 정보를 수정할 수 없습니다."));
		}
	}

	// 5. 유저 정보 삭제 (탈퇴)
	@DeleteMapping("/{userId}")
	public ResponseEntity<? extends BaseResponseBody> deleteUser(@ApiIgnore Authentication authentication, @PathVariable String userId) {

		System.out.println(">>>>>>>>>>>>>>> 탈퇴: " + userId);
		System.out.println(">>>>>>>>>>>>>>> 인증 정보: " + authentication);

		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		System.out.println(">>>>> jwt token의 id : " + userDetails.getUser().getUserId());
		if(userDetails.getUser().getUserId().equals(userId)) {
			// jwt token의 id와 수정하려는 회원 id가 같으니 수정 가능
			userService.deleteUser(userId);
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
		} else {
			return ResponseEntity.status(409).body(BaseResponseBody.of(409, "회원 정보를 삭제할 수 없습니다."));
		}
	}
}
