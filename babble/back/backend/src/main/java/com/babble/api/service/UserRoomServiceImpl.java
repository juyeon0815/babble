package com.babble.api.service;

import com.babble.db.entity.Room;
import com.babble.db.entity.User;
import com.babble.db.entity.UserRoom;
import com.babble.db.repository.UserRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *	유저룸 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userRoomService")
public class UserRoomServiceImpl implements UserRoomService {


    @Autowired
    UserRoomRepository userRoomRepository;
    @Override
    public UserRoom createUserRoom(User user, Room room) {
        UserRoom userRoom = new UserRoom();
        userRoom.setRoom(room);
        userRoom.setUser(user);
        userRoomRepository.save(userRoom);
        return userRoom;
    }
}
