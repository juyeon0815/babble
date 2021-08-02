package com.babble.api.request.room;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Room 입장 API ([POST] /api/v1/room) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("RoomRequest")
public class RoomRelationReq {
    @ApiModelProperty(name="유저 Email", example="your_email")
    String email;
    @ApiModelProperty(name="roomId", example="roomId")
    Long roomId;
}
