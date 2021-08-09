package com.babble.api.controller;


import com.babble.api.request.room.RoomCreateReq;
import com.babble.api.request.room.RoomRelationReq;
import com.babble.api.response.room.RoomRes;
import com.babble.api.response.room.RoomWaitRes;
import com.babble.api.service.*;
import com.babble.common.model.response.BaseResponseBody;
import com.babble.db.entity.*;
import com.querydsl.core.Tuple;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
    @Autowired
    UserHashtagService userHashtagService;
    @Autowired
    EmailService emailService;

    @PostMapping(value = "/create" )
    @ApiOperation(value = "방 생성", notes = "방에 대한 정보를 입력한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> roomCreate(@RequestBody RoomCreateReq roomCreateReq) throws Exception {
        //방 생성 시 email, title, content, thumbnail_url, category, hashtag, speak 정보 넘어옴

        //category 테이블에서 category_name과 일치한 id 가져와 저장
        Category category = categoryService.getCategoryByCategoryName(roomCreateReq.getCategory());

        //hostId는 현재 로그인된 유저 id
        User user = userService.getUserByUserEmail(roomCreateReq.getEmail());

        //room create
        Room room = roomService.createRoom(category, user, roomCreateReq);

        HashMap<String, String> userHashtagMap = new HashMap<String, String>();

        //설정한 해시태그가 해시태그 테이블에 없을 경우, 추가 후 room_hashtag테이블에 roomId 와 hashtagId 함께 저장
        String[] tagList = roomCreateReq.getHashtag().split(" ");
        for (int i = 0; i < tagList.length; i++) {
            Hashtag tag = hashtagService.getHashtagByHashtagName(tagList[i]);
            if (tag == null) { //해당 해시태그 없음 -> 해시태그 테이블에 넣고, 룸해시 테이블에 넣고
                Hashtag hashtag = hashtagService.createHashtag(tagList[i]);
                RoomHashtag roomHashtag = roomHashtagService.createRoomHashtag(hashtag, room);
            } else { //해당 해시태그 있을 경우
                RoomHashtag roomHashtag = roomHashtagService.createRoomHashtag(tag, room);
            }

            // 해당 해시테그를 가지고 있으면서 이메일 알림을 설정한 유저들을 조회
            List<String> userList = userHashtagService.getUserByHashtag(tagList[i]);
            System.out.println(" >>>>> userList: " + userList);
            if(!userList.isEmpty() && userList.size()>0 && userList.get(0)!=null) {
                for(String userEmail: userList) {
                    if(userHashtagMap.containsKey(userEmail)) {
                        String hastagValue = userHashtagMap.get(userEmail);
                        userHashtagMap.put(userEmail, hastagValue + ", " + tagList[i]);
                    } else {
                        userHashtagMap.put(userEmail, tagList[i]);
                    }
                }
            }
        } //for

        System.out.println(">> 생성된 방 " + room.getId());
        // HashMap에 담긴 정보로 이메일 전송
        if(!userHashtagMap.isEmpty() && userHashtagMap.size()>0) {
            for(String userEmail: userHashtagMap.keySet()) {
                System.out.println(userEmail + " "  + userHashtagMap.get(userEmail) + " 이메일 전송");
                emailService.sendHashtagMessage(userEmail, userHashtagMap.get(userEmail), room.getId());
            }
        }

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, room.getId().toString()));
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

    @GetMapping(value = "/list")
    @ApiOperation(value = "방 정보", notes = "모든 방의 정보를 보여준다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity roomList() {

        List<Tuple> roomInfo = roomService.getRoomInfo();
        System.out.println(roomInfo.size());
        List<RoomRes> roomList = roomService.roomList(roomInfo);
        System.out.println(roomList.size());
        return ResponseEntity.status(200).body(roomList);
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
                                           @PathVariable("pageNum") @ApiParam(value="페이지번호", required = true)int pageNum) throws IOException {
        pageNum= (pageNum-1)*10;
        List<Tuple> roomInfo;
        if(categoryName.equals("all")) roomInfo = roomService.getBestRoomInfo(pageNum);
        else roomInfo = roomService.getCategoryBestRoomInfo(categoryName,pageNum);
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
                                     @PathVariable("pageNum") @ApiParam(value="페이지번호", required = true)int pageNum) throws IOException {
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
                                             @PathVariable("pageNum") @ApiParam(value="페이지번호", required = true)int pageNum) throws IOException {
        pageNum= (pageNum-1)*10;
        List<Tuple> roomInfo;
        if(categoryName.equals("all")) roomInfo = roomService.getRecentRoomInfo(pageNum);
        else roomInfo = roomService.getCategoryRecentRoomInfo(categoryName,pageNum);
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

    @GetMapping("/{roomId}")
    @ApiOperation(value = "대기실 방정보", notes = "대기실 방 정보 보여주기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity roomInfo(@PathVariable("roomId") @ApiParam(value="roomId", required = true) Long roomId) {
        Room room = roomService.getRoomByRoomId(roomId);
        RoomWaitRes roomWaitRes = RoomWaitRes.builder()
                .id(room.getId())
                .title(room.getTitle())
                .content(room.getContent())
                .createTime(room.getCreateTime())
                .build();

        return ResponseEntity.status(200).body(roomWaitRes);
    }

    @GetMapping("/random")
    public String callRandomApi() throws IOException{
        StringBuilder result = new StringBuilder();
        String urlStr = "https://nickname.hwanmoo.kr/?format=text&count=1";
        URL url = new URL(urlStr);

        HttpURLConnection urlConnection = (HttpURLConnection)  url.openConnection();
        urlConnection.setRequestMethod("GET");

        BufferedReader br;

        br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));

        String returnLine;

        while((returnLine = br.readLine()) != null){
            result.append(returnLine + "\n\r");
        }

        urlConnection.disconnect();

        return result.toString();
    }
}
