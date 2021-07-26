package com.babble.db.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
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

    @OneToOne
    @JoinColumn(name ="categoryId")
    Category category;

    @OneToMany(mappedBy = "room")
    List<RoomHashtag> roomHashtag = new ArrayList<>();

    @OneToMany(mappedBy = "room")
    List<RoomHistory> roomHistories = new ArrayList<>();
}
