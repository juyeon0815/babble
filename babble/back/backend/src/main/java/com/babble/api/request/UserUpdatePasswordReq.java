package com.babble.api.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserUpdatePasswordPatchRequest")
public class UserUpdatePasswordReq {
    @ApiModelProperty(name="유저 Email", example="your_email")
    String email;
    @ApiModelProperty(name="유저 Password", example="your_password")
    String password;
}
