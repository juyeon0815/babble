package com.babble.api.controller;

import com.babble.api.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.babble.api.request.UserRegisterPostReq;
import com.babble.api.service.UserService;
import com.babble.common.model.response.BaseResponseBody;
import com.babble.db.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
			@RequestBody @ApiParam(value="이메일정보 정보", required = true) String email) throws Exception {

		System.out.println("이메일 : "+email);
		String confirm = emailService.sendSimpleMessage(email);

		return ResponseEntity.status(200).body(BaseResponseBody.of(200, confirm));
	}
	
//	@GetMapping("/me")
//	@ApiOperation(value = "회원 본인 정보 조회", notes = "로그인한 회원 본인의 정보를 응답한다.")
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "성공"),
//        @ApiResponse(code = 401, message = "인증 실패"),
//        @ApiResponse(code = 404, message = "사용자 없음"),
//        @ApiResponse(code = 500, message = "서버 오류")
//    })
//	public ResponseEntity<UserRes> getUserInfo(@ApiIgnore Authentication authentication) { //@apiignore : 명세서에 표시 x
//		/**
//		 * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
//		 * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
//		 */
//		System.out.println("헤더어떻게넘어와?");
//		System.out.println("제발");
//		System.out.println("gggggggggggg "+authentication);
//		BabbleUserDetails babbleUserDetails = (BabbleUserDetails)authentication.getDetails();
//		System.out.println("gggg");
//		String userId = babbleUserDetails.getUsername();
//		User user = userService.getUserByUserId(userId);
//		System.out.println("user!!!!!!!!!!! "+user);
//		return ResponseEntity.status(200).body(UserRes.of(user));
//	}
//
//	@GetMapping("/{id}")
//	@ApiOperation(value = "아이디 중복 체크", notes = "회원가입시 사용가능한 아이디인지 중복 체크를 한다.")
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "성공"),
//        @ApiResponse(code = 401, message = "인증 실패"),
//        @ApiResponse(code = 404, message = "사용자 없음"),
//        @ApiResponse(code = 500, message = "서버 오류")
//    })
//	public ResponseEntity getUserCheckId(
//			@PathVariable("id") @ApiParam(value="회원가입 정보", required = true) String id) {
//		System.out.println(id);
//		boolean flag = userService.checkId(id);
//		System.out.println(flag);
//		if(flag) { //중복된 아이디 있음
//			return new ResponseEntity<>("success", HttpStatus.NO_CONTENT);
//		}
//		else return new ResponseEntity<>("success", HttpStatus.OK);
//
//	}
//
//	@Transactional
//	@PatchMapping("/{id}")
//	@ApiOperation(value = "유저 정보 수정", notes = "회원 정보 수정하기")
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "성공"),
//        @ApiResponse(code = 401, message = "인증 실패"),
//        @ApiResponse(code = 404, message = "사용자 없음"),
//        @ApiResponse(code = 500, message = "서버 오류")
//    })
//	public ResponseEntity UpdateUserInfo(
//			@RequestBody @ApiParam(value="회원가입 정보", required = true) UserUpdatePatchReq updateInfo) {
//		System.out.println("내가 수정해볼게~");
//		userService.updateUser(updateInfo);
//		return new ResponseEntity<>("success", HttpStatus.OK);
//	}
//
//	@Transactional
//	@DeleteMapping("/{id}")
//	@ApiOperation(value = "유저 정보 삭제", notes = "회원 정보 삭제하기")
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "성공"),
//        @ApiResponse(code = 401, message = "인증 실패"),
//        @ApiResponse(code = 404, message = "사용자 없음"),
//        @ApiResponse(code = 500, message = "서버 오류")
//    })
//	public ResponseEntity deleteUser(@PathVariable("id") @ApiParam(value="회원 정보", required = true) String id) {
//		System.out.println("일단여기들어온건맞지");
//		userService.deleteUser(id);
//		return new ResponseEntity<>("success", HttpStatus.OK);
//	}
}
