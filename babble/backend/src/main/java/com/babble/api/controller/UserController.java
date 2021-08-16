package com.babble.api.controller;

import com.babble.api.request.user.*;
import com.babble.api.response.user.UserHistoryRes;
import com.babble.api.response.user.UserLoginPostRes;
import com.babble.api.response.user.UserRes;
import com.babble.api.service.*;
import com.babble.common.auth.BabbleUserDetails;
import com.babble.db.entity.*;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.babble.common.model.response.BaseResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;
import java.util.List;

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
	@Autowired
	HashtagService hashtagService;
	@Autowired
	UserHashtagService userHashtagService;
	@Autowired
	RoomHistoryService roomHistoryService;
	@Autowired
	PasswordEncoder passwordEncoder;


	@PostMapping("/join")
	@ApiOperation(value = "회원 가입", notes = "<strong>이메일과 패스워드</strong>를 통해 회원가입 한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> register(
			@RequestBody @ApiParam(value = "회원가입 정보", required = true) UserRegisterReq registerInfo) {

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
			@RequestBody @ApiParam(value = "이메일정보 정보", required = true) String email) throws Exception {

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
		System.out.println("USERME 입장!");
		System.out.println(authentication.getDetails());
		BabbleUserDetails babbleUserDetails = (BabbleUserDetails) authentication.getDetails();
		String email = babbleUserDetails.getUsername();
		User user = userService.getUserByUserEmail(email);
		UserRes userRes = UserRes.builder()
				.user(user)
				.build();
		return ResponseEntity.status(200).body(userRes);
	}

	@GetMapping("/{email}")
	@ApiOperation(value = "이메일 중복 체크", notes = "회원가입시 사용가능한 이메일인지 중복 체크를 한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity getUserCheckEmail(
			@PathVariable("email") @ApiParam(value = "회원가입 정보", required = true) String email) {
		User user = userService.checkEmail(email);
		if (user != null) { //중복된 아이디 있음
			return ResponseEntity.status(403).body(BaseResponseBody.of(403, "이미 존재하는 사용자 ID 입니다."));
		} else return ResponseEntity.status(200).body(BaseResponseBody.of(200, "사용가능한 ID 입니다."));

	}

	@PostMapping("/passwordCheck")
	@ApiOperation(value = "유저 비밀번호 변경 시 현재 비밀번호 일치여부 확인", notes = "비밀번호 변경 시 현재 비밀번호가 일치한지 확인한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> passwordCheck(
			@RequestBody @ApiParam(value = "이메일정보 정보", required = true) UserUpdatePasswordReq userInfo) throws Exception {

		User user = userService.getUserByUserEmail(userInfo.getEmail());
		if (passwordEncoder.matches(userInfo.getPassword(), user.getPassword())) { //비밀번호 일치
			return ResponseEntity.ok(UserLoginPostRes.of(200, "Success"));
		} else return ResponseEntity.status(200).body(BaseResponseBody.of(401, "fail"));
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
			@RequestBody @ApiParam(value = "이메일", required = true) UserUpdatePasswordReq userInfo) {
		userService.updatePassword(userInfo);
		return new ResponseEntity<>("success", HttpStatus.OK);
	}

	@Transactional
	@DeleteMapping("/{email}")
	@ApiOperation(value = "유저 정보 삭제", notes = "회원 정보 삭제하기")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity deleteUser(@PathVariable("email") @ApiParam(value = "회원 정보", required = true) String email) {
		// 유저 삭제시 관련된 user_hashtag , user_history삭제
		User user = userService.getUserByUserEmail(email);
		userHashtagService.deleteUserHashtag(user);
		roomHistoryService.deleteUserHistory(user);
		userService.deleteUser(email);
		return ResponseEntity.status(200).body("success");
	}

	// 이메일로 임시 비밀번호 발급
	@PatchMapping("/findPassword")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity findPassword(@RequestBody @ApiParam(value = "이메일", required = true) UserFindPasswordReq userFindPasswordReq) throws Exception {
		System.out.println(">>> email " + userFindPasswordReq.getEmail());
		// 존재하는 아이디인지 확인 후 임시 비밀번호 변경
		User user = userService.checkEmail(userFindPasswordReq.getEmail());
		if (user == null) { // 존재하는 ID가 아님
			return ResponseEntity.status(404).body(BaseResponseBody.of(404, "가입되어있지 않은 e-mail 입니다."));
		}

		String tempPassword = emailService.sendTempPassword(userFindPasswordReq.getEmail());

		// 임시 비밀번호로 변경
		UserUpdatePasswordReq userUpdateTempPasswordReq = UserUpdatePasswordReq.builder()
				.email(userFindPasswordReq.getEmail())
				.password(tempPassword)
				.build();
		userService.updatePassword(userUpdateTempPasswordReq );
		return new ResponseEntity<>("success", HttpStatus.OK);
	}

	@PostMapping("/hashtag")
	@ApiOperation(value = "해시태그 추가", notes = "관심 해시태그 추가")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> hashtag(
			@RequestBody @ApiParam(value = "해시태그", required = true) UserHashtagReq userHashtagAdd) throws Exception {

		//유저가 해시태그를 추가하면 해시태그테이블에서 해당 해시태그가 존재하는 지 확인 후, 만약 존재하지 않는다면
		//해시태그 테이블에 추가하고 유저해시테이블에 관련 정보 저장, 반대로 해시태그 테이블에 추가한 해시태그가 존재한다면
		//해당 해시태크 pk번호와 유저 pk번호를 유저해시태그 테이블에 저장
		Hashtag hashtag = hashtagService.getHashtagByHashtagName(userHashtagAdd.getName());
		User user = userService.getUserByUserEmail(userHashtagAdd.getEmail());
		if (hashtag == null) { //해당 해시태그 없음 -> 해시태그 테이블에 넣고, 유저해시 테이블에 넣고
			Hashtag tag = hashtagService.createHashtag(userHashtagAdd.getName());
			UserHashtag userHashtag = userHashtagService.createUserHashtag(user, tag);
		} else { //해당 해시태그 있을 경우
			UserHashtag userHashtag = userHashtagService.createUserHashtag(user, hashtag);
		}
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success"));
	}

	@Transactional
	@DeleteMapping("/hashtag")
	@ApiOperation(value = "해시태그 정보 삭제", notes = "해시태그 정보 삭제하기")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity deleteUser(@RequestBody @ApiParam(value = "해시태그", required = true) UserHashtagReq userHashtag) {
		//userhashtag 테이블에 있는 데이터를 지워야하니까 userId와 hashtagId를 알아야한다.
		User user = userService.getUserByUserEmail(userHashtag.getEmail());
		Hashtag hashtag = hashtagService.getHashtagByHashtagName(userHashtag.getName());
		userHashtagService.deleteHashtag(user, hashtag);
		return new ResponseEntity<>("success", HttpStatus.OK);
	}

	@Transactional
	@PatchMapping("/hashtag")
	@ApiOperation(value = "유저 해시태그 알림 설정", notes = "알림설정 변경하기")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity updateAlarm(
			@RequestBody @ApiParam(value = "이메일정보 정보", required = true) String email) {
		userService.updateAlarm(email);
		return new ResponseEntity<>("success", HttpStatus.OK);
	}

	@GetMapping("/viewHistory/{email}")
	@ApiOperation(value = "유저 시청이력 확인", notes = "해당 유저의 시청이력을 확인한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity getUserViewHistory(
			@PathVariable("email") @ApiParam(value = "유저이메일", required = true) String email) {

		User user = userService.getUserByUserEmail(email);
		List<Tuple> historyInfo = roomHistoryService.getUserViewHistory(user);
		List<UserHistoryRes> list = userService.historyList(historyInfo, "view");

		return ResponseEntity.status(200).body(list);

	}

	@GetMapping("/createRoomHistory/{email}")
	@ApiOperation(value = "유저 방생성이력 확인", notes = "해당 유저의 방생성이력을 확인한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity getUserCreateRoomHistory(
			@PathVariable("email") @ApiParam(value = "유저이메일", required = true) String email) {

		User user = userService.getUserByUserEmail(email);
		List<Tuple> historyInfo = roomHistoryService.getUserCreateRoomHistory(user);
		List<UserHistoryRes> list = userService.historyList(historyInfo, "");
		return ResponseEntity.status(200).body(list);
	}

	@GetMapping("/hashtag/{email}")
	@ApiOperation(value = "유저가 설정한 해시태크 확인", notes = "해당 유저의 해시태그를 확인한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity getUserHashtag(@PathVariable("email") @ApiParam(value = "유저이메일", required = true) String email) {
		User user = userService.getUserByUserEmail(email);
		List<String> list = userHashtagService.getUserHashtag(user.getId());
		return ResponseEntity.status(200).body(list);
	}

	@PatchMapping("/updatePicture")
	@ApiOperation(value = "유저 프로필 사진 업로드", notes = "유저 프로필 사진을 업로드한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> userImageUpload(
			@RequestBody @ApiParam(value = "유저프로필 변경 정보", required = true) UserUpdatePictureReq userUpdatePictureReq) {
		userService.updatePicture(userUpdatePictureReq);
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success"));
	}

	@GetMapping("/socialInfo/{email}")
	@ApiOperation(value = "회원 본인 정보 조회", notes = "카카오톡/구글로 로그인한 회원 본인의 정보를 응답한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<UserRes> getSocialUserInfo(@PathVariable("email") @ApiParam(value = "유저이메일", required = true) String email) {
		User user = userService.getUserByUserEmail(email);
		UserRes userRes = UserRes.builder()
				.user(user)
				.build();
		return ResponseEntity.status(200).body(userRes);
	}
}
