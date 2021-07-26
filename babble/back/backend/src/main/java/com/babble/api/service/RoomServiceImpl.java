package com.babble.api.service;

import com.babble.api.request.room.RoomCreateReq;
import com.babble.db.entity.Category;
import com.babble.db.entity.Room;
import com.babble.db.entity.User;
import com.babble.db.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *	ROOM 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("roomService")
public class RoomServiceImpl implements RoomService {

    @Autowired
    RoomRepository roomRepository;

    @Override
    public Room createRoom(RoomCreateReq roomCreateReq, User user, Category category) {
        Room room = new Room();
        room.setTitle(roomCreateReq.getTitle());
        room.setContent(roomCreateReq.getContent());
        room.setThumbnailUrl(roomCreateReq.getThumbnailUrl());
        room.setCategory(category);
        room.setActivate(true);
        room.setSpeak(roomCreateReq.isSpeak());
        room.setUser(user);

        return roomRepository.save(room);
    }
}
