package com.babble.api.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Room 정보 조회 API ([GET] /api/v1/room/list) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("RoomResponse")
public class RoomRes {
    // 방이름, 썸네일 이미지, 카테고리, 태그, 시청자수
    @ApiModelProperty(name="room_title")
    String title;
    @ApiModelProperty(name="room_thumbnail")
    String thumbnailUrl;
    @ApiModelProperty(name="room_category")
    String category;
    @ApiModelProperty(name="room_hashtag")
    List<String> hashtag;
    @ApiModelProperty(name="viewers")
    Long viewers;

}
