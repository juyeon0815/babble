package com.babble.api.service;

import com.babble.api.request.room.RoomReq;
import com.babble.api.response.RoomRes;
import com.babble.db.entity.Category;
import com.babble.db.entity.Room;
import com.babble.db.entity.User;
import com.querydsl.core.Tuple;

import java.io.IOException;
import java.util.List;


public interface RoomService {
    Room createRoom(RoomReq roomReq, User user, Category category, String thumbnail);
    Room getRoomByRoomId(Long roomId);
    List<Tuple> getRoomInfo();
    List<Tuple> getBestRoomInfo(int pageNum);
    List<Tuple> getRecentRoomInfo(int pageNum);
    List<Tuple> getCategoryBestRoomInfo(String categoryName,int pageNum);
    List<Tuple> getCategoryRecentRoomInfo(String categoryName, int pageNum);
    void roomClose(Long roomId);
    List<RoomRes> roomList(List<Tuple> roomInfo);
    List<Tuple> searchRoomList(String searchName, int pageNum);
}
