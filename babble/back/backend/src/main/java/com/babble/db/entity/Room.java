package com.babble.db.entity;


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
@Setter
public class Room extends BaseEntity{

    String title;
    String content;

    @OneToOne
    @JoinColumn(name = "hostId")
    User user;

    boolean isActivate;
    String thumbnailUrl;
    boolean speak;

    Date createTime;

    Long maxView;

    @Builder
    public Room(String title, String content, Date createTime){
        this.title = title;
        this.content=content;
        this.createTime = createTime;
    }

    public Room(){}

    @OneToOne
    @JoinColumn(name ="categoryId")
    Category category;

    @OneToMany(mappedBy = "room")
    List<RoomHashtag> roomHashtag = new ArrayList<>();

    @OneToMany(mappedBy = "room")
    List<RoomHistory> roomHistories = new ArrayList<>();

}
