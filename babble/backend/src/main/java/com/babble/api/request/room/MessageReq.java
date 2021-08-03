package com.babble.api.request.room;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class MessageReq {
    Long roomId;
    String content;

    @Builder
    public MessageReq (long roomId, String content) {
        super();
        this.roomId = roomId;
        this.content = content;
    }
}
