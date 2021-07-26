package com.babble.api.controller;


import com.babble.api.request.room.RoomCreateReq;
import com.babble.api.service.*;
import com.babble.common.model.response.BaseResponseBody;
import com.babble.db.entity.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
