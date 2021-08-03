package com.babble.api.request.room;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class MessageReq {
    Long chatroomId;
    String content;
    String nickname;

    @Builder
    public MessageReq (long chatroomId, String content, String nickname) {
        super();
        this.chatroomId = chatroomId;
        this.content = content;
        this.nickname = nickname;
    }
}