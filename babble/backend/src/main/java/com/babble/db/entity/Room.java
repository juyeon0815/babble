package com.babble.db.entity;


import com.babble.api.request.room.RoomCreateReq;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

import java.time.LocalDateTime;
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

    Long hostId;

    boolean isActivate;

    String thumbnailUrl;

    LocalDateTime createTime;

    Long maxView;


    @OneToOne
    @JoinColumn(name ="categoryId")
    Category category;

    @OneToMany(mappedBy = "room")
    List<RoomHashtag> roomHashtag = new ArrayList<>();

    @OneToMany(mappedBy = "room")
    List<RoomHistory> roomHistories = new ArrayList<>();

    @Builder
    public Room(Category category, User user, RoomCreateReq roomCreateReq){
        LocalDateTime time = LocalDateTime.now();
        this.title = roomCreateReq.getTitle();
        this.content= roomCreateReq.getContent();
        this.thumbnailUrl = roomCreateReq.getThumbnailUrl();
        this.category = category;
        this.isActivate = true;
        this.hostId = user.id;
        this.createTime = time;
        this.maxView =0l;
    }

    public Room(){}

    public void roomClose(Long maxView){
        this.isActivate = false;
        this.maxView = maxView;
    }

}
