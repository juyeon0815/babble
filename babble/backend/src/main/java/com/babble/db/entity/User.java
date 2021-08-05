package com.babble.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
public class User extends BaseEntity{
    String email;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

    String picture;

    boolean alarm;

    @OneToMany (mappedBy ="user", cascade = CascadeType.REMOVE)
    List<UserHashtag> userHashtag = new ArrayList<>();

    @OneToMany (mappedBy = "user" ,cascade = CascadeType.REMOVE)
    List<RoomHistory> roomHistory = new ArrayList<>();

    //객체 생성시 Builder 사용
    @Builder
    public User(String email, String password){
        this.email = email;
        this.password = password;
        this.picture = "default";
        this.alarm = false;
    }

    public User() { }

    public void updateAlarm(boolean alarm){
        this.alarm= !alarm;
    }

    public void updatePicture(String picture){
        this.picture = picture;
    }

    public void updatePassword(String password){
        this.password = password;
    }
}
