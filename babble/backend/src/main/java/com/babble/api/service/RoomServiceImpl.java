package com.babble.api.service;

import com.babble.api.request.room.RoomCreateReq;
import com.babble.api.response.RoomRes;
import com.babble.db.entity.*;
import com.babble.db.repository.RoomRepository;
import com.babble.db.repository.RoomRepositorySupport;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    RoomHashtagService roomHashtagService;




    QRoom qRoom = QRoom.room;
    QCategory qCategory = QCategory.category;
    QUserRoom qUserRoom = QUserRoom.userRoom;


    @Override
    public Room createRoom(Category category, User user, RoomCreateReq roomCreateReq) {

        Room room = Room.builder()
                .category(category)
                .user(user)
                .roomCreateReq(roomCreateReq)
                .build();
        roomRepository.save(room);
        return room;
    }

    @Override
    public Room getRoomByRoomId(Long roomId) {
        Room room = roomRepositorySupport.findRoomByRoomId(roomId);
        return room;
    }

    @Override
    public List<Tuple> getRoomInfo() {
        List<Tuple> roomRes = roomRepositorySupport.roomInfo();
        return roomRes;
    }

    @Override
    public List<Tuple> getBestRoomInfo(int pageNum) {
        List<Tuple> roomRes = roomRepositorySupport.bestRoomInfo(pageNum);
        return roomRes;
    }


    @Override
    public List<Tuple> getRecentRoomInfo(int pageNum) {
        List<Tuple> roomRes = roomRepositorySupport.recentRoomInfo(pageNum);
        return roomRes;
    }

    @Override
    public List<Tuple> getCategoryBestRoomInfo(String categoryName, int pageNum) {
        List<Tuple> roomRes = roomRepositorySupport.categoryBestRoomInfo(categoryName,pageNum);
        return roomRes;
    }

    @Override
    public List<Tuple> getCategoryRecentRoomInfo(String categoryName, int pageNum) {
        List<Tuple> roomRes = roomRepositorySupport.categoryRecentRoomInfo(categoryName,pageNum);
        return roomRes;
    }

    @Override
    public void roomClose(Long roomId) {
        Room room = roomRepositorySupport.findRoomByRoomId(roomId);
        room.roomClose();
        roomRepository.save(room);
    }

    @Override
    public List<RoomRes> roomList(List<Tuple> roomInfo){

        List<RoomRes> result = new ArrayList<>();
        for(int i=0;i<roomInfo.size();i++){
            Long id = roomInfo.get(i).get(qRoom.id);
            String title = roomInfo.get(i).get(qRoom.title);
            String thumbnail = roomInfo.get(i).get(qRoom.thumbnailUrl);
            String category = roomInfo.get(i).get(qCategory.name);
            Long count = roomInfo.get(i).get(qUserRoom.room.id.count());


            List<String> hashtags = new ArrayList<>();
            List<Hashtag> list = roomHashtagService.findHashtagByRoomHashtagRoomId(roomInfo.get(i).get(qRoom.id));
            for(int j=0;j<list.size();j++){
                hashtags.add(list.get(j).getName());
            }
            RoomRes roomRes = RoomRes.builder()
                    .id(roomInfo.get(i).get(qRoom.id))
                    .title(roomInfo.get(i).get(qRoom.title))
                    .thumbnailUrl(roomInfo.get(i).get(qRoom.thumbnailUrl))
                    .category(roomInfo.get(i).get(qCategory.name))
                    .count(roomInfo.get(i).get(qUserRoom.room.id.count()))
                    .list(hashtags)
                    .build();
            result.add(roomRes);
        }
        return result;
    }

    @Override
    public List<Tuple> searchRoomList(String searchName, int pageNum) {
        List<Tuple> searchRoomList = roomRepositorySupport.searchRoomList(searchName,pageNum);
        return searchRoomList;
    }

}
