package com.babble.db.entity;


import com.babble.api.request.room.RoomReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

/**
 * Room 모델 정의.
 */
@Entity
@Getter
public class Room extends BaseEntity{

    String title;
    String content;

//    @OneToOne
//    @JoinColumn(name = "hostId")
//    User user;

    Long hostId;

    boolean isActivate;
    String thumbnailUrl;
    boolean speak;

    Date createTime;

    Long maxView;


    @OneToOne
    @JoinColumn(name ="categoryId")
    Category category;

    @OneToMany(mappedBy = "room")
    List<RoomHashtag> roomHashtag = new ArrayList<>();

    @OneToMany(mappedBy = "room")
    List<RoomHistory> roomHistories = new ArrayList<>();

    @Builder
    public Room(RoomReq roomReq, User user, Category category, String thumbnail ){
        Date now = new Date();
        this.title = roomReq.getRoomCreateReq().getTitle();
        this.content= roomReq.getRoomCreateReq().getContent();
        this.thumbnailUrl = thumbnail;
        this.category = category;
        this.isActivate = true;
        this.speak = roomReq.getRoomCreateReq().isSpeak();
        this.hostId = user.id;
        this.createTime = now;
        this.maxView =0l;
    }

    public Room(){}

    public void roomClose(){
        this.isActivate = false;
    }

}
