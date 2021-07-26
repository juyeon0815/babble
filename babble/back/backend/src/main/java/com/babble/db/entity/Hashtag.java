package com.babble.db.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * 해시태그 모델 정의.
 */
@Entity
@Getter
@Setter
public class Hashtag extends BaseEntity {
    String name;

    @OneToMany(mappedBy ="hashtag")
    List<UserHashtag> userHashtags = new ArrayList<>();

    @OneToMany(mappedBy = "hashtag")
    List<RoomHashtag> roomHashtags = new ArrayList<>();
}
