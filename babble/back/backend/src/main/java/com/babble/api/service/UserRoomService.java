package com.babble.api.service;

import com.babble.db.entity.Room;
import com.babble.db.entity.User;
import com.babble.db.entity.UserRoom;

public interface UserRoomService {
    UserRoom createUserRoom(User user, Room room);
}
