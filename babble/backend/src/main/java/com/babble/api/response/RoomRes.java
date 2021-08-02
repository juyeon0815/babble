package com.babble.api.response;


import com.babble.db.entity.Room;
import com.babble.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Room 정보 조회관련 API ([GET] /api/v1/room/) 요청에 대한 응답값 정의.
 */
@Getter
@ApiModel("RoomResponse")
public class RoomRes {
    // 방이름, 썸네일 이미지, 카테고리, 태그, 시청자수
    @ApiModelProperty(name="room_Id")
    Long id;
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

    @Builder
    public RoomRes(Long id, String title, String thumbnailUrl, String category, Long count, List<String> list){
        this.id = id;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.category = category;
        this.viewers = count;
        this.hashtag = list;
    }

}
