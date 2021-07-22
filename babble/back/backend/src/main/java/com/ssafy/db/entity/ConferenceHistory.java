package com.ssafy.db.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

/**
 * 컨퍼런스히스토리 모델 정의.
 */
@Entity
@Getter
@Setter
public class ConferenceHistory extends BaseEntity {
	
	@ManyToOne
	@JoinColumn(name ="conferenceId")
	Conference conference;
	
	@ManyToOne
	@JoinColumn(name ="userId")
	User user;
	
	
	int action;
	Date insertedTime;
}
