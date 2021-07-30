package com.babble.api.service;

import com.babble.db.entity.Room;
import com.babble.db.entity.User;
import com.babble.db.entity.UserRoom;
import com.babble.db.repository.UserRoomRepository;
import com.babble.db.repository.UserRoomRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *	현재 참여 유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userRoomService")
public class UserRoomServiceImpl implements UserRoomService {


    @Autowired
    UserRoomRepository userRoomRepository;
    @Autowired
    UserRoomRepositorySupport userRoomRepositorySupport;
    @Override
    public UserRoom createUserRoom(User user, Room room) {
        UserRoom userRoom = UserRoom.builder()
                .room(room)
                .user(user)
                .build();
        return userRoomRepository.save(userRoom);
    }

    @Override
    public void deleteUserRoom(User user) {
        UserRoom userRoom = userRoomRepositorySupport.findUserRoomByUserId(user);
        userRoomRepository.delete(userRoom);
    }

    @Override
    public void deleteUserRoom(Long roomId) {
        List<UserRoom> userRoom = userRoomRepositorySupport.findUserRoomByRoomId(roomId);
        for(int i=0;i<userRoom.size();i++){
            userRoomRepository.delete(userRoom.get(i));
        }
    }
}
