package com.babble.api.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel("UserFindPasswordRequest")
public class UserFindPasswordReq {
    @ApiModelProperty(name="유저 Email", example="your_email")
    String email;
}
