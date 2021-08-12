package com.babble.api.response.user;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GoogleLoginRes {
    String email;
    String idToken;

    @Builder
    public GoogleLoginRes(String email, String idToken){
        this.email = email;
        this.idToken = idToken;
    }
}
