package com.babble.api.service;

import com.babble.db.entity.Room;
import com.babble.db.entity.RoomHistory;
import com.babble.db.entity.User;
import com.babble.db.repository.RoomHashtagRepositorySupport;
import com.babble.db.repository.RoomHistoryRepository;
import com.babble.db.repository.RoomHistoryRepositorySupport;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *	시청기록 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("roomHistoryService")
public class RoomHistoryServiceImpl implements RoomHistoryService {

    @Autowired
    RoomHistoryRepository roomHistoryRepository;
    @Autowired
    RoomHistoryRepositorySupport roomHistoryRepositorySupport;

    Date date = new Date();

    @Override
    public RoomHistory createRoomHistory(User user, Room room) {

        RoomHistory roomHistory = new RoomHistory();
        roomHistory.setRoom(room);
        roomHistory.setUser(user);
        roomHistory.setStartTime(date);
        return roomHistoryRepository.save(roomHistory);
    }

    @Override
    public void roomExit(User user, Room room) {
        RoomHistory roomHistory = roomHistoryRepositorySupport.findRoomHistoryByUserEmail(user,room);
        roomHistory.setEndTime(date);
        roomHistoryRepository.save(roomHistory);
    }

    @Override
    public List<Tuple> getUserViewHistory(User user) {
        List<Tuple> userHistory = roomHistoryRepositorySupport.getUserViewHistory(user);
        return userHistory;
    }

    @Override
    public List<Tuple> getUserCreateRoomHistory(User user) {
        List<Tuple> createRoomHistory = roomHistoryRepositorySupport.getCreateRoomHistory(user);
        return createRoomHistory;
    }
}
