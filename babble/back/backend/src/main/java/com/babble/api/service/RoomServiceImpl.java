package com.babble.api.service;

import com.babble.api.request.room.RoomCreateReq;
import com.babble.api.request.room.RoomReq;
import com.babble.api.response.RoomRes;
import com.babble.db.entity.*;
import com.babble.db.repository.RoomRepository;
import com.babble.db.repository.RoomRepositorySupport;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    ImageService imageService;



    QRoom qRoom = QRoom.room;
    QCategory qCategory = QCategory.category;
    QUserRoom qUserRoom = QUserRoom.userRoom;


    @Override
    public Room createRoom(RoomReq roomReq, User user, Category category, String thumbnail) {

        Date now = new Date();
        Room room = new Room();
        room.setTitle(roomReq.getRoomCreateReq().getTitle());
        room.setContent(roomReq.getRoomCreateReq().getContent());
        room.setThumbnailUrl(thumbnail);
        room.setCategory(category);
        room.setActivate(true);
        room.setSpeak(roomReq.getRoomCreateReq().isSpeak());
        room.setUser(user);
        room.setCreateTime(now);
        room.setMaxView(0L);
        return roomRepository.save(room);
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
        room.setActivate(false);
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
            RoomRes roomRes = new RoomRes();
            roomRes.setId(id);
            roomRes.setTitle(title);
            roomRes.setThumbnailUrl("d://images/room/"+thumbnail);
            roomRes.setCategory(category);
            roomRes.setViewers(count);
            roomRes.setHashtag(hashtags);

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
