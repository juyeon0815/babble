package com.ssafy.db.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

/**
 * 컨퍼런스모델 정의.
 */
@Entity
@Getter
@Setter
public class Conference extends BaseEntity {
	
	@ManyToOne
	@JoinColumn(name = "onwerId")
	User user;
	
	@ManyToOne
	@JoinColumn(name ="conferenceCategory")
	ConferenceCategory conferenceCategory;
	
	Date callStartTime;
	Date callEndTime;
	String thumbnailUrl;
	String title;
	String description;
	boolean isActive;
	
	@OneToMany(mappedBy = "conference")
	List<UserConference> userConference = new ArrayList<>();
	
	@OneToMany(mappedBy = "conference")
	List<UserConference> conferenceHistory = new ArrayList<>();
}
