package com.babble.db.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Category 모델 정의.
 */
@Entity
@Getter
@Setter
public class Category extends BaseEntity {

    String name;
}