package com.babble.db.repository;

import com.babble.api.response.RoomRes;
import com.babble.db.entity.QCategory;
import com.babble.db.entity.QRoom;
import com.babble.db.entity.QUserRoom;
import com.babble.db.entity.Room;
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

    public Room findRoomByRoomTitle(String title){
        Room room = jpaQueryFactory.select(qRoom).from(qRoom)
                .where(qRoom.title.eq(title)).fetchOne();

        if(room==null) return null;
        return room;
    }

    public List<Tuple> roomInfo(){
        List<Tuple> list = jpaQueryFactory.select(qRoom.id, qRoom.title, qRoom.thumbnailUrl, qCategory.name, qUserRoom.room.id.count())
                .from(qRoom).join(qCategory).on(qRoom.category.id.eq(qCategory.id))
                .join(qUserRoom).on(qRoom.id.eq(qUserRoom.room.id))
                .where(qRoom.isActivate.eq(true))
                .groupBy(qUserRoom.room.id)
                .fetch();
        return list;
    }

    public List<Tuple> bestRoomInfo(){
        List<Tuple> list = jpaQueryFactory.select(qRoom.id, qRoom.title, qRoom.thumbnailUrl, qCategory.name, qUserRoom.room.id.count())
                .from(qRoom).join(qCategory).on(qRoom.category.id.eq(qCategory.id))
                .join(qUserRoom).on(qRoom.id.eq(qUserRoom.room.id))
                .where(qRoom.isActivate.eq(true)).limit(10)
                .groupBy(qUserRoom.room.id)
                .orderBy(qUserRoom.room.id.count().desc())
                .fetch();
        return list;
    }

    public List<Tuple> recentRoomInfo(){
        List<Tuple> list = jpaQueryFactory.select(qRoom.id, qRoom.title, qRoom.thumbnailUrl, qCategory.name, qUserRoom.room.id.count())
                .from(qRoom).join(qCategory).on(qRoom.category.id.eq(qCategory.id))
                .join(qUserRoom).on(qRoom.id.eq(qUserRoom.room.id))
                .where(qRoom.isActivate.eq(true)).limit(10)
                .groupBy(qUserRoom.room.id)
                .orderBy(qRoom.createTime.desc())
                .fetch();
        return list;
    }

    public List<Tuple> categoryBestRoomInfo(String categoryName){
        List<Tuple> list = jpaQueryFactory.select(qRoom.id, qRoom.title, qRoom.thumbnailUrl, qCategory.name, qUserRoom.room.id.count())
                .from(qRoom).join(qCategory).on(qRoom.category.id.eq(qCategory.id))
                .leftJoin(qUserRoom).on(qRoom.id.eq(qUserRoom.room.id))
                .where(qRoom.isActivate.eq(true))
                .where(qCategory.name.eq(categoryName))
                .groupBy(qUserRoom.room.id)
                .orderBy(qUserRoom.room.id.count().desc())
                .fetch();

        return list;
    }

    public List<Tuple> categoryRecentRoomInfo(String categoryName){
        List<Tuple> list = jpaQueryFactory.select(qRoom.id, qRoom.title, qRoom.thumbnailUrl, qCategory.name, qUserRoom.room.id.count())
                .from(qRoom).join(qCategory).on(qRoom.category.id.eq(qCategory.id))
                .join(qUserRoom).on(qRoom.id.eq(qUserRoom.room.id))
                .where(qRoom.isActivate.eq(true))
                .where(qCategory.name.eq(categoryName))
                .groupBy(qUserRoom.room.id)
                .orderBy(qRoom.createTime.desc())
                .fetch();

        return list;
    }
}
