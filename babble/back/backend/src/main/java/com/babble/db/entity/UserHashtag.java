package com.babble.db.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 유저_해시태그 모델 정의.
 */
@Entity
@Getter
@Setter
public class UserHashtag extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="userId")
    User user;

    @ManyToOne
    @JoinColumn(name="hashtagId")
    Hashtag hashtag;


}
