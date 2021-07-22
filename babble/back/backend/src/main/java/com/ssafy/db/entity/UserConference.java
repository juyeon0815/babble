package com.ssafy.db.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

/**
 * 유저컨퍼런스 모델 정의.
 */
@Entity
@Getter
@Setter
public class UserConference extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "conferenceId")
    Conference conference;

    @ManyToOne
    @JoinColumn(name = "userId")
    User user;
}
