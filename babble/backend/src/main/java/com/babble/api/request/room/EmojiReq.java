package com.babble.api.request.room;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class EmojiReq {
    Long chatroomId;
    int emojiType;

    @Builder
    public EmojiReq(Long chatroomId, int emojiType) {
        this.chatroomId = chatroomId;
        this.emojiType = emojiType;
    }
}
