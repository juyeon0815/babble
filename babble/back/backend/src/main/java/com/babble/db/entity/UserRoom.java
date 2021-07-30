package com.babble.db.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * UserRoom 모델 정의.
 */
@Entity
@Getter
public class UserRoom extends BaseEntity{

    @OneToOne
    @JoinColumn(name = "userId")
    User user;

    @OneToOne
    @JoinColumn(name = "roomId")
    Room room;

    @Builder
    public UserRoom(User user, Room room){
        this.user=user;
        this.room=room;
    }

    public UserRoom() { }
}
