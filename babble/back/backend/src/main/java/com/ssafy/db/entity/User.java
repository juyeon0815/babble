package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * 유저 모델 정의.
 */
@Entity
@Getter
@Setter
public class User extends BaseEntity{
    String position;
    String department;
    String name;
    String userId;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

    
	@OneToMany(mappedBy="user" ,cascade = CascadeType.REMOVE)
	List<UserConference> userConference = new ArrayList<>();
	
	@OneToMany(mappedBy="user", cascade = CascadeType.REMOVE)
	List<ConferenceHistory> conferenceHistory = new ArrayList<>();
	
	@OneToMany(mappedBy="user")
	List<Conference> conference = new ArrayList<>();
    
    
}
