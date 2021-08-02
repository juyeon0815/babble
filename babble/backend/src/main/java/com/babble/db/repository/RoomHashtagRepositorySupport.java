package com.babble.db.repository;


import com.babble.db.entity.Hashtag;
import com.babble.db.entity.QHashtag;
import com.babble.db.entity.QRoomHashtag;
import com.babble.db.entity.RoomHashtag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * room_hashtag 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class RoomHashtagRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QRoomHashtag qRoomHashtag = QRoomHashtag.roomHashtag;
    QHashtag qHashtag = QHashtag.hashtag;

    public List<Hashtag> findHashtagByRoomId(Long roomId){
        List<Hashtag> list = jpaQueryFactory.select(qHashtag)
                .from(qRoomHashtag)
                .join(qHashtag).on(qHashtag.id.eq(qRoomHashtag.hashtag.id))
                .where(qRoomHashtag.room.id.eq(roomId)).fetch();

        return list;
    }

    public List<RoomHashtag> findRoomHashtagByRoomId(Long roomId){
        List<RoomHashtag> list = jpaQueryFactory.select(qRoomHashtag)
                .from(qRoomHashtag)
                .where(qRoomHashtag.room.id.eq(roomId)).fetch();

        return list;
    }
}
