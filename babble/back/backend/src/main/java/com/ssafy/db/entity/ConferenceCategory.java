package com.ssafy.db.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

/**
 * 컨퍼런스카테고리 모델 정의.
 */
@Entity
@Getter
@Setter
public class ConferenceCategory extends BaseEntity {
    String name;

    @OneToMany(mappedBy = "conferenceCategory")
    List<Conference> conference = new ArrayList<>();
}
