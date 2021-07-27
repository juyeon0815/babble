package com.babble.api.service;

import com.babble.db.entity.Room;
import com.babble.db.entity.RoomHistory;
import com.babble.db.entity.User;
import com.querydsl.core.Tuple;

import java.util.List;

public interface RoomHistoryService {
    RoomHistory createRoomHistory(User user, Room room);
    void roomExit(User user, Room room);
    List<Tuple> getUserViewHistory(User user);
    List<Tuple> getUserCreateRoomHistory(User user);
    void updateEndTime(Long roomId);
}
