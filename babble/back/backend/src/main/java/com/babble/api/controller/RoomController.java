package com.babble.api.controller;


import com.babble.api.request.room.RoomCreateReq;
import com.babble.api.request.room.RoomRelationReq;
import com.babble.api.response.RoomRes;
import com.babble.api.service.*;
import com.babble.common.model.response.BaseResponseBody;
import com.babble.db.entity.*;
import com.querydsl.core.Tuple;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Room 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "Room API", tags = {"Room"})
@RestController
@RequestMapping("/api/v1/room")
public class RoomController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    HashtagService hashtagService;
    @Autowired
    UserService userService;
    @Autowired
    RoomHashtagService roomHashtagService;
    @Autowired
    RoomService roomService;
    @Autowired
    UserRoomService userRoomService;
    @Autowired
    RoomHistoryService roomHistoryService;


    @PostMapping("/create")
    @ApiOperation(value = "방 생성", notes = "방에 대한 정보를 입력한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> roomCreate(
            @RequestBody @ApiParam(value="생성할 방 정보", required = true) RoomCreateReq roomCreateReq) {


            //방 생성 시 email, title, desc, thumbnail_url, category, hastag, speak 정보 넘어옴
            //category 테이블에서 category_name과 일치한 id 가져와 저장
            Category category =  categoryService.getCategoryByCategoryName(roomCreateReq.getName());
            //hostId는 현재 로그인된 유저 id
            User user = userService.getUserByUserEmail(roomCreateReq.getEmail());
            //isActivate default값 true

            //room create
            Room room = roomService.createRoom(roomCreateReq, user, category);

            //설정한 해시태그가 해시태그 테이블에 없을 경우, 추가 후 room_hashtag테이블에 roomId 와 hashtagId 함께 저장
            String [] tagList = roomCreateReq.getHashtag().split(" ");
            for(int i=0;i< tagList.length; i++) {
                Hashtag tag = hashtagService.getHashtagByHashtagName(tagList[i]);
                if (tag == null) { //해당 해시태그 없음 -> 해시태그 테이블에 넣고, 룸해시 테이블에 넣고
                    Hashtag hashtag = hashtagService.createHashtag(tagList[i]);
                    RoomHashtag roomHashtag = roomHashtagService.createRoomHashtag(hashtag, room);
                } else { //해당 해시태그 있을 경우
                    RoomHashtag roomHashtag = roomHashtagService.createRoomHashtag(tag, room);
                }
            }

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @PostMapping("/enter")
    @ApiOperation(value = "방 입장", notes = "방에 입장한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> roomEnter(
            @RequestBody @ApiParam(value="입장", required = true)RoomRelationReq roomRelationReq) {

            User user = userService.getUserByUserEmail(roomRelationReq.getEmail());
            Room room = roomService.getRoomByRoomId(roomRelationReq.getRoomId());
            UserRoom userRoom = userRoomService.createUserRoom(user, room);
            RoomHistory roomHistory = roomHistoryService.createRoomHistory(user, room);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @PatchMapping("/exit")
    @ApiOperation(value = "방 나가기", notes = "현재참여하고있는 방에서 퇴장.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> roomExit(@RequestBody @ApiParam(value="퇴장", required = true)RoomRelationReq roomRelationReq) {
            User user = userService.getUserByUserEmail(roomRelationReq.getEmail());
            Room room = roomService.getRoomByRoomId(roomRelationReq.getRoomId());
            roomHistoryService.roomExit(user,room);

            //방나기면 user_room 데이터 삭제
            userRoomService.deleteUserRoom(user);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @GetMapping("/list")
    @ApiOperation(value = "방 정보", notes = "모든 방의 정보를 보여준다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity roomList() {

        List<Tuple> roomInfo = roomService.getRoomInfo();
        List<RoomRes> roomList = roomService.roomList(roomInfo);
        return ResponseEntity.status(200).body(roomList);
    }


    @GetMapping("/all/best/{pageNum}")
    @ApiOperation(value = "인기있는 방 정보", notes = "인기있는 방의 정보를 보여준다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity roomBestList(@PathVariable("pageNum") @ApiParam(value="인기순", required = true) int pageNum) {
        pageNum= (pageNum-1)*10;
        List<Tuple> roomInfo = roomService.getBestRoomInfo(pageNum);
        List<RoomRes> bestList = roomService.roomList(roomInfo);
        return ResponseEntity.status(200).body(bestList);
    }

    @GetMapping("/all/recent/{pageNum}")
    @ApiOperation(value = "최신 방 정보", notes = "새로생긴 방의 정보를 보여준다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity roomRecentList(@PathVariable("pageNum") @ApiParam(value="최신순", required = true) int pageNum) {
        pageNum= (pageNum-1)*10;
        List<Tuple> roomInfo = roomService.getRecentRoomInfo(pageNum);
        List<RoomRes> recentList = roomService.roomList(roomInfo);
        return ResponseEntity.status(200).body(recentList);
    }

    @GetMapping("/{categoryName}/best/{pageNum}")
    @ApiOperation(value = "카테고리별 인기 방 정보", notes = "카테고리별 인기순으로 방의 정보를 보여준다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity categoryBestList(@PathVariable("categoryName") @ApiParam(value="카테고리명", required = true)String categoryName,
                                           @PathVariable("pageNum") @ApiParam(value="페이지번호", required = true)int pageNum) {
        pageNum= (pageNum-1)*10;
        List<Tuple> roomInfo = roomService.getCategoryBestRoomInfo(categoryName,pageNum);
        List<RoomRes> categoryList = roomService.roomList(roomInfo);
        return ResponseEntity.status(200).body(categoryList);
    }

    @GetMapping("/{searchName}/{pageNum}")
    @ApiOperation(value = "검색한 방 정보", notes = "검색어가 포함된 방의 정보를 보여준다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity searchList(@PathVariable("searchName") @ApiParam(value="검색할 단어", required = true)String searchName,
                                             @PathVariable("pageNum") @ApiParam(value="페이지번호", required = true)int pageNum) {
        pageNum= (pageNum-1)*10;
        List<Tuple> roomInfo = roomService.searchRoomList(searchName, pageNum);
        List<RoomRes> searchRoomList = roomService.roomList(roomInfo);
        return ResponseEntity.status(200).body(searchRoomList);
    }

    @GetMapping("/{categoryName}/recent/{pageNum}")
    @ApiOperation(value = "카테고리별 최신 방 정보", notes = "카테고리별 최신순으로 방의 정보를 보여준다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity categoryRecentList(@PathVariable("categoryName") @ApiParam(value="카테고리명", required = true)String categoryName,
                                             @PathVariable("pageNum") @ApiParam(value="페이지번호", required = true)int pageNum) {
        pageNum= (pageNum-1)*10;
        List<Tuple> roomInfo = roomService.getCategoryRecentRoomInfo(categoryName,pageNum);
        List<RoomRes> categoryList = roomService.roomList(roomInfo);
        return ResponseEntity.status(200).body(categoryList);
    }


    @Transactional
    @DeleteMapping("/{roomId}")
    @ApiOperation(value = "방 종료", notes = "화상회의 방 종료하기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity roomClose(@PathVariable("roomId") @ApiParam(value="roomId", required = true) Long roomId) {
        roomService.roomClose(roomId);
        userRoomService.deleteUserRoom(roomId);
        roomHashtagService.deleteRoomHashtag(roomId);
        roomHistoryService.updateEndTime(roomId);
        return ResponseEntity.status(200).body("success");
    }
}
