package com.babble.api.request.room;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Room 생성 API ([POST] /api/v1/room) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@ApiModel("RoomCreatePostRequest")
@ToString
public class RoomCreateReq {

    @ApiModelProperty(name="유저 Email", example="your_email")
    String email;
    @ApiModelProperty(name="room title", example="room_title")
    String title;
    @ApiModelProperty(name="room content", example="room_content")
    String content;
    @ApiModelProperty(name="thumbnailurl", example="room_thumbnailurl")
    String thumbnailUrl;
    @ApiModelProperty(name="category", example="room_category")
    String category;
    @ApiModelProperty(name="hashtag", example="room_hashtag")
    String hashtag;
    @ApiModelProperty(name="room speakOption", example="room_speakOption")
    boolean speak;

}
