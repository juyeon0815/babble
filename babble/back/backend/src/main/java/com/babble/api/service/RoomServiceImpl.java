package com.babble.api.service;

import com.babble.api.request.room.RoomCreateReq;
import com.babble.db.entity.Category;
import com.babble.db.entity.Room;
import com.babble.db.entity.User;
import com.babble.db.repository.RoomRepository;
import com.babble.db.repository.RoomRepositorySupport;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 *	ROOM 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("roomService")
public class RoomServiceImpl implements RoomService {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    RoomRepositorySupport roomRepositorySupport;


    @Override
    public Room createRoom(RoomCreateReq roomCreateReq, User user, Category category) {

        Date now = new Date();

        Room room = new Room();
        room.setTitle(roomCreateReq.getTitle());
        room.setContent(roomCreateReq.getContent());
        room.setThumbnailUrl(roomCreateReq.getThumbnailUrl());
        room.setCategory(category);
        room.setActivate(true);
        room.setSpeak(roomCreateReq.isSpeak());
        room.setUser(user);
        room.setCreateTime(now);
        room.setMaxView(0L);
        return roomRepository.save(room);
    }

    @Override
    public Room getRoomByRoomTitle(String title) {
        Room room = roomRepositorySupport.findRoomByRoomTitle(title);
        return room;
    }

    @Override
    public List<Tuple> getRoomInfo() {
        List<Tuple> roomRes = roomRepositorySupport.roomInfo();
        return roomRes;
    }

    @Override
    public List<Tuple> getBestRoomInfo() {
        List<Tuple> roomRes = roomRepositorySupport.bestRoomInfo();
        return roomRes;
    }

    @Override
    public List<Tuple> getRecentRoomInfo() {
        List<Tuple> roomRes = roomRepositorySupport.recentRoomInfo();
        return  roomRes;
    }

    @Override
    public List<Tuple> getCategoryBestRoomInfo(String categoryName) {
        List<Tuple> roomRes = roomRepositorySupport.categoryBestRoomInfo(categoryName);
        return roomRes;
    }

    @Override
    public List<Tuple> getCategoryRecentRoomInfo(String categoryName) {
        List<Tuple> roomRes = roomRepositorySupport.categoryRecentRoomInfo(categoryName);
        return roomRes;
    }
}
