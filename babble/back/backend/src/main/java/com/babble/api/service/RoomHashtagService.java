package com.babble.api.service;

import com.babble.db.entity.Hashtag;
import com.babble.db.entity.Room;
import com.babble.db.entity.RoomHashtag;
import com.babble.db.entity.User;

public interface RoomHashtagService {
    RoomHashtag createRoomHashtag(Hashtag hashtag, Room room);
}
