package com.babble.api.service;

import com.babble.api.request.room.RoomCreateReq;
import com.babble.db.entity.Category;
import com.babble.db.entity.Room;
import com.babble.db.entity.User;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;

import java.util.List;


public interface RoomService {
    Room createRoom(RoomCreateReq roomCreateReq, User user, Category category);
    Room getRoomByRoomTitle(String title);
    List<Tuple> getRoomInfo();
}
