package com.babble.api.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * 시청기록 정보 조회 API ([GET] /api/v1/users) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("userHistoryResponse")
public class UserHistoryRes {
    // 방이름, 카테고리, 시청날짜, 시청시간, 최대 조회수
    @ApiModelProperty(name="room_title")
    String title;
    @ApiModelProperty(name="room_category")
    String category;
    @ApiModelProperty(name="room_viewDate")
    Date viewDate;
    @ApiModelProperty(name="room_viewTime")
    Date viewStart;
    @ApiModelProperty(name="room_viewTime")
    Date viewEnd;
    @ApiModelProperty(name="room_maxView")
    Long maxView;
}
