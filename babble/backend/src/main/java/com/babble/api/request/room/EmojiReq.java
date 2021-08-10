package com.babble.api.request.room;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class EmojiReq {
    Long roomId;
    String img;
    String nickname;

    @Builder
    public EmojiReq(Long roomId, String img, String nickname) {
        this.roomId = roomId;
        this.img = img;
        this.nickname = nickname;
    }
}
