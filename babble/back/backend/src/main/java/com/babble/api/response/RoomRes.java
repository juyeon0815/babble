package com.babble.api.response;


import com.babble.db.entity.Room;
import com.babble.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Room 정보 조회관련 API ([GET] /api/v1/room/) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
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
    @ApiModelProperty(name="content")
    String content;
    @ApiModelProperty(name="createTime")
    Date createTime;
//
    public static RoomRes of(Room room) {
        RoomRes res = new RoomRes();
        res.setTitle(room.getTitle());
        res.setContent(room.getContent());
        res.setCreateTime(room.getCreateTime());
        return res;
    }

}
