package com.babble.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

/**
 * Room 정보 조회관련 API ([GET] /api/v1/room/) 요청에 대한 응답값 정의.
 */
@Getter
@ApiModel("RoomWaitResponse")
public class RoomWaitRes {

    @ApiModelProperty(name="room_Id")
    Long id;
    @ApiModelProperty(name="room_title")
    String title;
    @ApiModelProperty(name="room_content")
    String content;
    @ApiModelProperty(name="room_createTime")
    Date createTime;

    @Builder
    public RoomWaitRes(Long id, String title, String content, Date createTime){
        this.id = id;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
    }

}