package com.babble.db.entity;


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
@Setter
public class UserRoom extends BaseEntity{

    @OneToOne
    @JoinColumn(name = "userId")
    User user;

    @OneToOne
    @JoinColumn(name = "roomId")
    Room room;
}
