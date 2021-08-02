package com.babble.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Room_History모델 정의.
 */
@Entity
@Getter
public class RoomHistory extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "userId")
    User user;

    @ManyToOne
    @JoinColumn(name = "roomId")
    Room room;

    Date startTime;
    Date endTime;

    public void createRoomHistory(Room room, User user, Date date){
        this.room = room;
        this.user = user;
        this.startTime = date;
    }

    public void roomExit(Date date){
        this.endTime = date;
    }
}