package com.babble.api.response.room;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * Room 정보 조회관련 API ([GET] /api/v1/room/) 요청에 대한 응답값 정의.
 */
@Getter
@ApiModel("RoomHostResponse")
public class RoomHostRes {

    // 방이름, 호스트 id
    @ApiModelProperty(name="host_Id")
    Long hostId;
    @ApiModelProperty(name="room_title")
    String title;

    @Builder
    public RoomHostRes(Long hostId, String title){
        this.hostId = hostId;
        this.title =title;
    }
}

