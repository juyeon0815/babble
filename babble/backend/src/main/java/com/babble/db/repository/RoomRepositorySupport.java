package com.babble.db.repository;

import com.babble.db.entity.*;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * room 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class RoomRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QRoom qRoom = QRoom.room;
    QCategory qCategory = QCategory.category;
    QUserRoom qUserRoom = QUserRoom.userRoom;
    QRoomHashtag qRoomHashtag = QRoomHashtag.roomHashtag;
    QHashtag qHashtag = QHashtag.hashtag;

    public Room findRoomByRoomId(Long roomId){
        Room room = jpaQueryFactory.select(qRoom).from(qRoom)
                .where(qRoom.id.eq(roomId)).fetchOne();

        if(room==null) return null;
        return room;
    }


    public List<Tuple> bestRoomInfo(int pageNum){
        List<Tuple> list = jpaQueryFactory.select(qRoom.id, qRoom.title, qRoom.thumbnailUrl, qCategory.name, qUserRoom.room.id.count())
                .from(qRoom).leftJoin(qCategory).on(qRoom.category.id.eq(qCategory.id))
                .leftJoin(qUserRoom).on(qRoom.id.eq(qUserRoom.room.id))
                .where(qRoom.isActivate.eq(true)).limit(10).offset(pageNum)
                .groupBy(qRoom.id)
                .orderBy(qUserRoom.room.id.count().desc())
                .fetch();
        return list;
    }

    public List<Tuple> recentRoomInfo(int pageNum){
        List<Tuple> list = jpaQueryFactory.select(qRoom.id, qRoom.title, qRoom.thumbnailUrl, qCategory.name, qUserRoom.room.id.count())
                .from(qRoom).leftJoin(qCategory).on(qRoom.category.id.eq(qCategory.id))
                .leftJoin(qUserRoom).on(qRoom.id.eq(qUserRoom.room.id))
                .where(qRoom.isActivate.eq(true)).limit(10).offset(pageNum)
                .groupBy(qRoom.id)
                .orderBy(qRoom.createTime.desc())
                .fetch();
        return list;
    }

    public List<Tuple> categoryBestRoomInfo(String categoryName, int pageNum){
        List<Tuple> list = jpaQueryFactory.select(qRoom.id, qRoom.title, qRoom.thumbnailUrl, qCategory.name, qUserRoom.room.id.count())
                .from(qRoom).leftJoin(qCategory).on(qRoom.category.id.eq(qCategory.id))
                .leftJoin(qUserRoom).on(qRoom.id.eq(qUserRoom.room.id))
                .where(qRoom.isActivate.eq(true))
                .where(qCategory.name.eq(categoryName)).limit(10).offset(pageNum)
                .groupBy(qRoom.id)
                .orderBy(qUserRoom.room.id.count().desc())
                .fetch();

        return list;
    }

    public List<Tuple> categoryRecentRoomInfo(String categoryName, int pageNum){
        List<Tuple> list = jpaQueryFactory.select(qRoom.id, qRoom.title, qRoom.thumbnailUrl, qCategory.name, qUserRoom.room.id.count())
                .from(qRoom).leftJoin(qCategory).on(qRoom.category.id.eq(qCategory.id))
                .leftJoin(qUserRoom).on(qRoom.id.eq(qUserRoom.room.id))
                .where(qRoom.isActivate.eq(true))
                .where(qCategory.name.eq(categoryName)).limit(10).offset(pageNum)
                .groupBy(qRoom.id)
                .orderBy(qRoom.createTime.desc())
                .fetch();

        return list;
    }

    public List<Tuple> searchRoomList(String searchName, int pageNum){
        List<Tuple>  list = jpaQueryFactory.select(qRoom.id, qRoom.title, qRoom.thumbnailUrl, qCategory.name,qUserRoom.room.id.count())
                .from(qRoom).leftJoin(qRoomHashtag).on(qRoom.id.eq(qRoomHashtag.room.id))
                .leftJoin(qUserRoom).on(qRoom.id.eq(qUserRoom.room.id))
                .leftJoin(qCategory).on(qRoom.category.id.eq(qCategory.id))
                .leftJoin(qHashtag).on(qRoomHashtag.hashtag.id.eq(qHashtag.id))
                .where(qRoom.title.contains(searchName).or(qHashtag.name.contains(searchName)))
                .where(qRoom.isActivate.eq(true)).limit(10).offset(pageNum)
                .groupBy(qRoom.id)
                .orderBy(qUserRoom.room.id.count().desc())
                .fetch();

        return list;
    }
}
