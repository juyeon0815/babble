package com.babble.api.service;

import com.babble.db.entity.Hashtag;
import com.babble.db.entity.Room;
import com.babble.db.entity.RoomHashtag;
import com.babble.db.entity.User;
import com.babble.db.repository.RoomHashtagRepository;
import com.babble.db.repository.RoomHashtagRepositorySupport;
import com.babble.db.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *	룸해시태그 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("roomHashtagService")
public class RoomHashtagServiceImpl implements RoomHashtagService {

    @Autowired
    RoomHashtagRepository roomHashtagRepository;

    @Autowired
    RoomHashtagRepositorySupport roomHashtagRepositorySupport;
    @Override
    public RoomHashtag createRoomHashtag(Hashtag hashtag, Room room) {
        RoomHashtag roomHashtag = new RoomHashtag();
        roomHashtag.setRoom(room);
        roomHashtag.setHashtag(hashtag);
        roomHashtagRepository.save(roomHashtag);

        return roomHashtag;
    }

    @Override
    public List<Hashtag> findHashtagByRoomHashtagRoomId(Long roomId) {
       List<Hashtag> Hashtag = roomHashtagRepositorySupport.findHashtagByRoomId(roomId);
       return Hashtag;
    }

    @Override
    public void deleteRoomHashtag(Long roomId) {
        List<RoomHashtag> roomHashtag = roomHashtagRepositorySupport.findRoomHashtagByRoomId(roomId);
        for(int i=0;i<roomHashtag.size();i++){
            roomHashtagRepository.delete(roomHashtag.get(i));
        }
    }
}
