package com.babble.api.controller;

import com.babble.api.request.UserUpdatePasswordReq;
import com.babble.api.request.UserUpdatePictureReq;
import com.babble.api.response.UserRes;
import com.babble.api.service.EmailService;
import com.babble.common.auth.BabbleUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.babble.api.request.UserRegisterPostReq;
import com.babble.api.service.UserService;
import com.babble.common.model.response.BaseResponseBody;
import com.babble.db.entity.User;

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

	@Autowired
	EmailService emailService;
	
	@PostMapping("/join")
	@ApiOperation(value = "회원 가입", notes = "<strong>이메일과 패스워드</strong>를 통해 회원가입 한다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseBody> register(
			@RequestBody @ApiParam(value="회원가입 정보", required = true) UserRegisterPostReq registerInfo) {
		
		//임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
		User user = userService.createUser(registerInfo);
		System.out.println(user);
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}

	@PostMapping("/emailConfirm")
	@ApiOperation(value = "회원 가입시 이메인 인증", notes = "기존사용하고 있는 이메일을 통해 인증")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> emailConfirm(
			@RequestParam @ApiParam(value="이메일정보 정보", required = true) String email) throws Exception {

		System.out.println("이메일 : "+email);
		String confirm = emailService.sendSimpleMessage(email);

		return ResponseEntity.status(200).body(BaseResponseBody.of(200, confirm));
	}
	
	@GetMapping("/me")
	@ApiOperation(value = "회원 본인 정보 조회", notes = "로그인한 회원 본인의 정보를 응답한다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<UserRes> getUserInfo(@ApiIgnore Authentication authentication) { //@apiignore : 명세서에 표시 x
		/**
		 * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
		 * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
		 */
		BabbleUserDetails babbleUserDetails = (BabbleUserDetails)authentication.getDetails();
		String email = babbleUserDetails.getUsername();
		User user = userService.getUserByUserEmail(email);
		return ResponseEntity.status(200).body(UserRes.of(user));
	}
//
	@GetMapping("/{email}")
	@ApiOperation(value = "이메일 중복 체크", notes = "회원가입시 사용가능한 이메일인지 중복 체크를 한다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity getUserCheckEmail(
			@PathVariable("email") @ApiParam(value="회원가입 정보", required = true) String email) {
		User user = userService.checkEmail(email);
		if(user!=null) { //중복된 아이디 있음
			return ResponseEntity.status(409).body(BaseResponseBody.of(409,"이미 존재하는 사용자 ID 입니다." ));
		}
		else return ResponseEntity.status(200).body(BaseResponseBody.of(200,"사용가능한 ID 입니다." ));

	}
//
	@Transactional
	@PatchMapping("/updatePicture")
	@ApiOperation(value = "유저 프로필사진 변경", notes = "프로필 변경하기")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity updatePicture(
			@RequestBody @ApiParam(value="이메일", required = true) UserUpdatePictureReq userInfo) {
		userService.updatePicture(userInfo);
		return new ResponseEntity<>("success", HttpStatus.OK);
	}

	@Transactional
	@PatchMapping("/updatePassword")
	@ApiOperation(value = "유저 비밀번호 변경", notes = "비밀번호 변경하기")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity updatePassword(
			@RequestBody @ApiParam(value="이메일", required = true) UserUpdatePasswordReq userInfo) {
		userService.updatePassword(userInfo);
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
//
	@Transactional
	@DeleteMapping("/{email}")
	@ApiOperation(value = "유저 정보 삭제", notes = "회원 정보 삭제하기")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity deleteUser(@PathVariable("email") @ApiParam(value="회원 정보", required = true) String email) {
		System.out.println("여기는들와?");
		userService.deleteUser(email);
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
}
