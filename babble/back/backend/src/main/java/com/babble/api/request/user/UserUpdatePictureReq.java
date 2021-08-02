package com.babble.api.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@ApiModel("UserUpdatePicturePatchRequest")
public class UserUpdatePictureReq {
    @ApiModelProperty(name="유저 Email", example="your_email")
    String email;
    @ApiModelProperty(name="유저 Picture", example="your_picture")
    String picture;
}
