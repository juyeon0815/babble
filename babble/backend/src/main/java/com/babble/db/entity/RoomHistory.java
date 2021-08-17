package com.babble.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
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

    LocalDateTime startTime;
    LocalDateTime endTime;

    public void createRoomHistory(Room room, User user, LocalDateTime date){
        this.room = room;
        this.user = user;
        this.startTime = date;
    }

    public void roomExit(){
        LocalDateTime date = LocalDateTime.now();
        this.endTime = date;
    }
}