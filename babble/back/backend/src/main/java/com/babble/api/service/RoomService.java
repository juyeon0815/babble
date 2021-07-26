package com.babble.api.service;

import com.babble.api.request.room.RoomCreateReq;
import com.babble.db.entity.Category;
import com.babble.db.entity.Room;
import com.babble.db.entity.User;


public interface RoomService {
    Room createRoom(RoomCreateReq roomCreateReq, User user, Category category);
}
