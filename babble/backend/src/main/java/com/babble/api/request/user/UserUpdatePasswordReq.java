package com.babble.api.request.user;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@ApiModel("UserUpdatePasswordPatchRequest")
public class UserUpdatePasswordReq {
    @ApiModelProperty(name="유저 Email", example="your_email")
    String email;
    @ApiModelProperty(name="유저 Password", example="your_password")
    String password;

    @Builder
    public UserUpdatePasswordReq(String email, String password){
        this.email = email;
        this.password = password;
    }
}
