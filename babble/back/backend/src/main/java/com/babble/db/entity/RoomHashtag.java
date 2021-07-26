package com.babble.db.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * room_해시태그 모델 정의.
 */
@Entity
@Getter
@Setter
public class RoomHashtag extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="roomId")
    Room room;

    @ManyToOne
    @JoinColumn(name="hashtagId")
    Hashtag hashtag;
}
